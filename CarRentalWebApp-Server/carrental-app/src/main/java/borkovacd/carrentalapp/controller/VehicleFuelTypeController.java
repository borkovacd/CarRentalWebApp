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

import borkovacd.carrentalapp.dto.VehicleFuelTypeDTO;
import borkovacd.carrentalapp.model.VehicleFuelType;
import borkovacd.carrentalapp.service.VehicleFuelTypeService;

@Controller
@RequestMapping(path = "/api/fuelTypes")
public class VehicleFuelTypeController {
	
	@Autowired
	VehicleFuelTypeService vehicleFuelTypeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<VehicleFuelTypeDTO>> getAllVehicleFuelTypes() {
		List<VehicleFuelTypeDTO> fuelTypesDTO = new ArrayList<VehicleFuelTypeDTO>();
		for(VehicleFuelType fuelType : vehicleFuelTypeService.findAll()) {
			ModelMapper modelMapper = new ModelMapper();
			modelMapper.typeMap(VehicleFuelType.class, VehicleFuelTypeDTO.class);
			VehicleFuelTypeDTO fuelTypeDTO = modelMapper.map(fuelType, VehicleFuelTypeDTO.class);
			fuelTypesDTO.add(fuelTypeDTO);
		}
		return new ResponseEntity<List<VehicleFuelTypeDTO>>(fuelTypesDTO, HttpStatus.OK);
	}

}
