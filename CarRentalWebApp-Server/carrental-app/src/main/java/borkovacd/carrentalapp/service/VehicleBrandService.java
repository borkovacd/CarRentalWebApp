package borkovacd.carrentalapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import borkovacd.carrentalapp.model.VehicleBrand;
import borkovacd.carrentalapp.repository.VehicleBrandRepository;

@Service
public class VehicleBrandService {

	@Autowired
	VehicleBrandRepository vehicleBrandRepository;

	public VehicleBrand findVehicleBrandByName(String brandName) {
		return vehicleBrandRepository.findVehicleBrandByName(brandName);
	}

	public List<VehicleBrand> findAll() {
		return vehicleBrandRepository.findAll();
	}


}
