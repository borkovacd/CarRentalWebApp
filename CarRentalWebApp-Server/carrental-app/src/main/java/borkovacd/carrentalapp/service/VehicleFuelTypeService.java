package borkovacd.carrentalapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import borkovacd.carrentalapp.model.VehicleFuelType;
import borkovacd.carrentalapp.repository.VehicleFuelTypeRepository;

@Service
public class VehicleFuelTypeService {
	
	@Autowired
	VehicleFuelTypeRepository vehicleFuelTypeRepository;

	public VehicleFuelType findVehicleFuelTypeByName(String fuelTypeName) {
		return vehicleFuelTypeRepository.findVehicleFuelTypeByName(fuelTypeName);
	}

	public List<VehicleFuelType> findAll() {
		return vehicleFuelTypeRepository.findAll();
	}

}
