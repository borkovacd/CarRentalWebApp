package borkovacd.carrentalapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import borkovacd.carrentalapp.model.VehicleBodyType;
import borkovacd.carrentalapp.repository.VehicleBodyTypeRepository;

@Service
public class VehicleBodyTypeService {
	
	@Autowired
	VehicleBodyTypeRepository vehicleBodyTypeRepository;

	public VehicleBodyType findVehicleBodyTypeByName(String bodyTypeName) {
		return vehicleBodyTypeRepository.findVehicleBodyTypeByName(bodyTypeName);
	}

	public List<VehicleBodyType> findAll() {
		return vehicleBodyTypeRepository.findAll();
	}

}
