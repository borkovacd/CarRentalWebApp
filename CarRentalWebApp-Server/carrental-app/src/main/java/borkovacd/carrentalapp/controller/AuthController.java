package borkovacd.carrentalapp.controller;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import borkovacd.carrentalapp.enums.ERole;
import borkovacd.carrentalapp.enums.UserType;
import borkovacd.carrentalapp.model.Role;
import borkovacd.carrentalapp.model.User;
import borkovacd.carrentalapp.payload.request.LoginRequest;
import borkovacd.carrentalapp.payload.request.SignupRequest;
import borkovacd.carrentalapp.payload.response.JwtResponse;
import borkovacd.carrentalapp.payload.response.MessageResponse;
import borkovacd.carrentalapp.security.jwt.JwtUtils;
import borkovacd.carrentalapp.security.services.UserDetailsImpl;
import borkovacd.carrentalapp.service.RoleService;
import borkovacd.carrentalapp.service.UserService;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		//Da li je potrebno staviti try-catch blok i bacati exception?
		//Sta se desi ako nije uspesna autentifikacija?
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		//Zasto JwtResponse ne sadrzi samo token? 
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		
		if (userService.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Greška: Korisničko ime već postoji!"));
		}

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Greška: Email adresa već postoji!"));
		}

		// Create new user's account
		User user = new User(
				signUpRequest.getFirstName(),
				signUpRequest.getLastName(),
				signUpRequest.getEmail(),
				signUpRequest.getUsername(),
				encoder.encode(signUpRequest.getPassword()),
				signUpRequest.getAddress(),
				signUpRequest.getDateOfBirth(),
				signUpRequest.getPhoneNumber(),
				UserType.USER,
				false,
				false);

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleService.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleService.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleService.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		for(Role role : roles) {
			if(role.getName().equals(ERole.ROLE_ADMIN))
				user.setUserType(UserType.ADMIN);
		}
		
		userService.save(user);

		return ResponseEntity.ok(new MessageResponse("Uspešno ste se registrovali!"));
	}
}