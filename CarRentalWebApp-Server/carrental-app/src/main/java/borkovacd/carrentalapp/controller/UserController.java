package borkovacd.carrentalapp.controller;

import java.security.Principal;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping(path = "/api/user")
public class UserController {
	
	@RequestMapping(method = RequestMethod.GET)
	public Principal user(Principal user) {
	   return user;
	}

}
