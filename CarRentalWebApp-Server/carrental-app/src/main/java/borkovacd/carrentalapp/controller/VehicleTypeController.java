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

import borkovacd.carrentalapp.dto.VehicleTypeDTO;
import borkovacd.carrentalapp.model.VehicleType;
import borkovacd.carrentalapp.service.VehicleTypeService;

@Controller
@RequestMapping(path = "/api/types")
public class VehicleTypeController {
	
	@Autowired
	VehicleTypeService vehicleTypeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<VehicleTypeDTO>> getAllVehicleTypes() {
		List<VehicleType> types = vehicleTypeService.findAll();
		List<VehicleTypeDTO> typesDTO = new ArrayList<VehicleTypeDTO>();
		for(VehicleType type : types) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.typeMap(VehicleType.class, VehicleTypeDTO.class);
			VehicleTypeDTO typeDTO = modelMapper.map(type, VehicleTypeDTO.class);
			typesDTO.add(typeDTO);
		}
		return new ResponseEntity<List<VehicleTypeDTO>>(typesDTO, HttpStatus.OK);
	}

}
