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

import borkovacd.carrentalapp.dto.VehicleBodyTypeDTO;
import borkovacd.carrentalapp.model.VehicleBodyType;
import borkovacd.carrentalapp.service.VehicleBodyTypeService;

@Controller
@RequestMapping(path = "/api/bodyTypes")
public class VehicleBodyTypeController {
	
	@Autowired
	VehicleBodyTypeService vehicleBodyTypeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<VehicleBodyTypeDTO>> getAllVehicleBodyTypes() {
		List<VehicleBodyType> bodyTypes = vehicleBodyTypeService.findAll();
		List<VehicleBodyTypeDTO> bodyTypesDTO = new ArrayList<VehicleBodyTypeDTO>();
		for(VehicleBodyType bodyType : bodyTypes) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.typeMap(VehicleBodyType.class, VehicleBodyTypeDTO.class);
			VehicleBodyTypeDTO typeDTO = modelMapper.map(bodyType, VehicleBodyTypeDTO.class);
			bodyTypesDTO.add(typeDTO);
		}
		return new ResponseEntity<List<VehicleBodyTypeDTO>>(bodyTypesDTO, HttpStatus.OK);
	}

}
