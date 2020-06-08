package borkovacd.carrentalapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import borkovacd.carrentalapp.enums.ERole;
import borkovacd.carrentalapp.model.Role;
import borkovacd.carrentalapp.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	public Optional<Role> findByName(ERole role) {
		return roleRepository.findByName(role);
	}

}
