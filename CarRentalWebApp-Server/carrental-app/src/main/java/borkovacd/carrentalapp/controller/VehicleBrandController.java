package borkovacd.carrentalapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import borkovacd.carrentalapp.dto.VehicleBrandDTO;
import borkovacd.carrentalapp.model.VehicleBrand;
import borkovacd.carrentalapp.service.VehicleBrandService;

@Controller
@RequestMapping(path = "/api/brands")
public class VehicleBrandController {
	
	@Autowired
	VehicleBrandService vehicleBrandService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<VehicleBrandDTO>> getAllVehicleBrands() {
		List<VehicleBrand> brands = vehicleBrandService.findAll();
		List<VehicleBrandDTO> brandsDTO = new ArrayList<VehicleBrandDTO>();
		for(VehicleBrand brand : brands) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.typeMap(VehicleBrand.class, VehicleBrandDTO.class);
			VehicleBrandDTO brandDTO = modelMapper.map(brand, VehicleBrandDTO.class);
			brandsDTO.add(brandDTO);
		}
		return new ResponseEntity<List<VehicleBrandDTO>>(brandsDTO, HttpStatus.OK);
	}

}
