package borkovacd.carrentalapp.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import borkovacd.carrentalapp.enums.ERole;
import borkovacd.carrentalapp.model.User;
import borkovacd.carrentalapp.model.Vehicle;
import borkovacd.carrentalapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public List<User> getAllUsers() {
		return userRepository.findAllUsers();
	}
	
	public Page<User> getAllUsers(String firstName, String lastName, String username, boolean isBlocked, Pageable pageable) {
		return userRepository.findAllUsers(firstName, lastName, username, isBlocked, pageable);
	}

	public User findById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	

}
