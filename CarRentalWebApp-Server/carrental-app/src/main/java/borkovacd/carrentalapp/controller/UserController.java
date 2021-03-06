package borkovacd.carrentalapp.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import borkovacd.carrentalapp.dto.PageDTO;
import borkovacd.carrentalapp.dto.UserDTO;
import borkovacd.carrentalapp.enums.ERole;
import borkovacd.carrentalapp.enums.UserType;
import borkovacd.carrentalapp.model.Role;
import borkovacd.carrentalapp.model.User;
import borkovacd.carrentalapp.security.services.UserDetailsImpl;
import borkovacd.carrentalapp.service.RoleService;
import borkovacd.carrentalapp.service.UserService;

@Controller
@Validated
@RequestMapping(path = "/api/users")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	PasswordEncoder encoder;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		List<User> users = userService.getAllUsers();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
		
		for(User user: users) {
			//Check if this user is currently logged in
			if(user.getId() != userDetails.getId()) {
				ModelMapper modelMapper = new ModelMapper();
				modelMapper.typeMap(User.class, UserDTO.class);
				UserDTO userDTO = modelMapper.map(user, UserDTO.class);
				usersDTO.add(userDTO);
			}
		}
		return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
	}
	 
	@RequestMapping(method = RequestMethod.GET, params = "page")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PageDTO<UserDTO>> getAllUsers(
			@RequestParam(required = false, defaultValue = "") String firstName,
			@RequestParam(required = false, defaultValue = "") String lastName,
			@RequestParam(required = false, defaultValue = "") String username,
			@RequestParam(required = false) boolean isBlocked, 
			Pageable pageable) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	

		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		Page<User> page = userService.getAllUsers(firstName, lastName, username, isBlocked, pageable);
		for(User user : page.getContent()) {
			//Check if this user is currently logged in
			if(user.getId() != userDetails.getId()) {
				ModelMapper modelMapper = new ModelMapper();
				modelMapper.typeMap(User.class, UserDTO.class);
				UserDTO userDTO = modelMapper.map(user, UserDTO.class);
				usersDTO.add(userDTO);
			}
		}
		PageDTO<UserDTO> pageDTO = new PageDTO<UserDTO>(pageable.getPageNumber(), pageable.getPageSize(), 
				page.getTotalElements(), usersDTO);
		return new ResponseEntity<PageDTO<UserDTO>> (pageDTO, HttpStatus.OK);		
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
		User user = userService.findById(id);
		if(user != null && user.isDeleted() == false) {
			ModelMapper modelMapper = new ModelMapper();
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO userDTO) { //provera za validan userDTO
		User user = new User();
		if (userService.existsByUsername(userDTO.getUsername())) { //ukoliko vec postoji user sa tim username-om
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		} else {
			user.setUsername(userDTO.getUsername());
		}
		if (userService.existsByEmail(userDTO.getEmail())) { //ukoliko vec postoji user sa tim email-om
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		} else {
			user.setEmail(userDTO.getEmail());
		}
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		Set<Role> roles = new HashSet<>();
		if(userDTO.getUserType().equals("USER")) {
			user.setUserType(UserType.USER);
			Role userRole = roleService.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			user.setUserType(UserType.ADMIN);
			Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(adminRole);
			Role userRole = roleService.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}
		user.setRoles(roles);
		
		if(userDTO.getUserType().equals("USER")) {
			if(userDTO.getAddress().equals("")) {
				return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
			} else {
				user.setAddress(userDTO.getAddress());
			}
			if(userDTO.getPhoneNumber().equals("")) {
				return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
			} else {
				user.setPhoneNumber(userDTO.getPhoneNumber());
			}
			if(userDTO.getDateOfBirth()==null) {
				return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
			} else {
				user.setDateOfBirth(userDTO.getDateOfBirth());
			}
		} else {
			user.setAddress("");
			user.setPhoneNumber("");
			user.setDateOfBirth(null);
		}
		
		user.setPassword(encoder.encode(userDTO.getPassword()));

		user.setBlocked(false);
		user.setDeleted(false);

		user = userService.save(user);

		ModelMapper modelMapper = new ModelMapper();
		UserDTO createdUserDTO = modelMapper.map(user, UserDTO.class);
		return new ResponseEntity<UserDTO>(createdUserDTO, HttpStatus.CREATED);		
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
		User user = userService.findById(id);
		if(user != null && !user.isBlocked() && !user.isBlocked()) {
			if (!userService.existsByUsername(userDTO.getUsername())) { //ukoliko NE postoji user sa tim username-om
				return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
			}
			if (!userService.existsByEmail(userDTO.getEmail())) { //ukoliko NE postoji user sa tim email-om
				return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
			}
			Set<Role> roles = new HashSet<>();
			if(userDTO.getUserType().equals("USER")) {
				user.setUserType(UserType.USER);
				Role userRole = roleService.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			} else {
				user.setUserType(UserType.ADMIN);
				Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);
				Role userRole = roleService.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			}
			user.setRoles(roles);
			if(userDTO.getUserType().equals("USER")) {
				if(userDTO.getAddress().equals("")) {
					return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
				} else {
					user.setAddress(userDTO.getAddress());
				}
				if(userDTO.getPhoneNumber().equals("")) {
					return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
				} else {
					user.setPhoneNumber(userDTO.getPhoneNumber());
				}
				if(userDTO.getDateOfBirth()==null) {
					return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
				} else {
					user.setDateOfBirth(userDTO.getDateOfBirth());
				}
			} else {
				user.setAddress("");
				user.setPhoneNumber("");
				user.setDateOfBirth(null);
			}

			user = userService.save(user);

			ModelMapper modelMapper = new ModelMapper();
			UserDTO updatedUserDTO = modelMapper.map(user, UserDTO.class);
			return new ResponseEntity<UserDTO>(updatedUserDTO, HttpStatus.OK);		

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		User user = userService.findById(id);
		if(user != null && user.isDeleted() == false) {
			user.setDeleted(true);
			user = userService.save(user);
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} else {	
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
	// parametar se mora naznaciti i u params ako dve metode sa istim URL-om
	// imaju razlicite parametre
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, params = "blocked")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> blockUser(@PathVariable Long id,
			@RequestParam(required = true) boolean blocked,
			@RequestBody UserDTO userDTO) {
		User user = userService.findById(id);
		if(user != null && user.isDeleted() == false) {
			user.setBlocked(true);
			user = userService.save(user);
			return new ResponseEntity<Object>(HttpStatus.OK);	
		} else {	
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}

	/*
		In contrast to request body validation a failed path variable validation will trigger a ConstraintViolationException 
		 instead of a MethodArgumentNotValidException. Spring does not register a default exception handler for this exception, 
		 so it will by default cause a response with HTTP status 500 (Internal Server Error).

		If we want to return a HTTP status 400 instead (which makes sense, since the client provided an invalid parameter, making it a bad request), 
		 we can add a custom exception handler to our contoller:
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
		return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	}


}
