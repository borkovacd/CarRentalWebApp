package borkovacd.carrentalapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import borkovacd.carrentalapp.dto.PageDTO;
import borkovacd.carrentalapp.dto.VehicleDTO;
import borkovacd.carrentalapp.model.Vehicle;
import borkovacd.carrentalapp.service.VehicleService;

@Controller
@RequestMapping(path = "/api/vehicles")
public class VehicleController {
	
	@Autowired
	VehicleService vehicleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PageDTO<VehicleDTO>> getAllVehicles(Pageable pageable) {
		List<VehicleDTO> vehicles = new ArrayList<VehicleDTO>();
		Page<Vehicle> page = vehicleService.getVehicles(pageable);
		for(Vehicle vehicle : page.getContent()) {
			ModelMapper modelMapper = new ModelMapper();
			// *** CHECK IF IT'S GOOD PRACTICE ***
			modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
				  mapper.map(src -> src.getFuelTypesNames(),
				      VehicleDTO::setFuelTypes);
				});
			VehicleDTO vehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
			vehicles.add(vehicleDTO);
		}
		PageDTO<VehicleDTO> pageDTO = new PageDTO<VehicleDTO>(pageable.getPageNumber(), pageable.getPageSize(), 
				page.getTotalElements(), vehicles);
		return new ResponseEntity<PageDTO<VehicleDTO>> (pageDTO, HttpStatus.OK);		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
		Vehicle vehicle = vehicleService.findById(id);
		if(vehicle != null) {
			ModelMapper modelMapper = new ModelMapper();
			// *** CHECK IF IT'S GOOD PRACTICE ***
			modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
				  mapper.map(src -> src.getFuelTypesNames(),
				      VehicleDTO::setFuelTypes);
				});
			VehicleDTO vehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
			return new ResponseEntity<VehicleDTO>(vehicleDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	
	
	

}
