package borkovacd.carrentalapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import borkovacd.carrentalapp.model.VehicleType;
import borkovacd.carrentalapp.repository.VehicleTypeRepository;

@Service
public class VehicleTypeService {
	
	@Autowired
	VehicleTypeRepository vehicleTypeRepository;

	public VehicleType findVehicleTypeByName(String typeName) {
		return vehicleTypeRepository.findVehicleTypeByName(typeName);
	}

	public List<VehicleType> findAll() {
		return vehicleTypeRepository.findAll();
	}

}
