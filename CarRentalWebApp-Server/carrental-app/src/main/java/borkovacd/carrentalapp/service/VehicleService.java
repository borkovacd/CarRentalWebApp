package borkovacd.carrentalapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import borkovacd.carrentalapp.model.Vehicle;
import borkovacd.carrentalapp.repository.VehicleRepository;

@Service
public class VehicleService {
	
	@Autowired
	VehicleRepository vehicleRepository;

	public Page<Vehicle> getVehicles(Pageable pageable) {
		return vehicleRepository.findAll(pageable);
	}

	public Vehicle findById(Long id) {
		return vehicleRepository.findById(id).orElse(null);
	}

	public Vehicle save(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}



}
