package borkovacd.carrentalapp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import borkovacd.carrentalapp.dto.PageDTO;
import borkovacd.carrentalapp.dto.UserDTO;
import borkovacd.carrentalapp.dto.VehicleDTO;
import borkovacd.carrentalapp.model.User;
import borkovacd.carrentalapp.model.Vehicle;
import borkovacd.carrentalapp.service.UserService;

@Controller
@RequestMapping(path = "/api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/*@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		List<User> users = userService.getAllUsers();
		for(User user: users) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.typeMap(User.class, UserDTO.class);
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			usersDTO.add(userDTO);
		}
		return new ResponseEntity<List<UserDTO>>(usersDTO, HttpStatus.OK);
	}
	*/
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PageDTO<UserDTO>> getAllUsers(
			@RequestParam(required = false, defaultValue = "") String firstName,
			@RequestParam(required = false, defaultValue = "") String lastName,
			@RequestParam(required = false, defaultValue = "") String username,
			@RequestParam(required = false) boolean isBlocked, 
			Pageable pageable) {
		List<UserDTO> usersDTO = new ArrayList<UserDTO>();
		Page<User> page = userService.getAllUsers(firstName, lastName, username, isBlocked, pageable);
		for(User user : page.getContent()) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.typeMap(User.class, UserDTO.class);
			UserDTO userDTO = modelMapper.map(user, UserDTO.class);
			usersDTO.add(userDTO);
		}
		PageDTO<UserDTO> pageDTO = new PageDTO<UserDTO>(pageable.getPageNumber(), pageable.getPageSize(), 
				page.getTotalElements(), usersDTO);
		return new ResponseEntity<PageDTO<UserDTO>> (pageDTO, HttpStatus.OK);		
	}
	
	

}
