package borkovacd.carrentalapp.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

	public Page<Vehicle> getVehicles(String type, String brand, String model,BigDecimal lowestPrice, BigDecimal highestPrice, Pageable pageable) {
		return vehicleRepository.findAllVehicles(type, brand, model, lowestPrice, highestPrice, pageable);
	}
	
	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}

	public Vehicle findById(Long id) {
		return vehicleRepository.findById(id).orElse(null);
	}

	public Vehicle save(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}

	



}
