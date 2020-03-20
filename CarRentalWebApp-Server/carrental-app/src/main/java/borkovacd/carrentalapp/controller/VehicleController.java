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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import borkovacd.carrentalapp.dto.PageDTO;
import borkovacd.carrentalapp.dto.VehicleDTO;
import borkovacd.carrentalapp.enums.DriveType;
import borkovacd.carrentalapp.enums.TransmissionType;
import borkovacd.carrentalapp.model.Vehicle;
import borkovacd.carrentalapp.model.VehicleBodyType;
import borkovacd.carrentalapp.model.VehicleBrand;
import borkovacd.carrentalapp.model.VehicleFuelType;
import borkovacd.carrentalapp.model.VehicleType;
import borkovacd.carrentalapp.service.VehicleBodyTypeService;
import borkovacd.carrentalapp.service.VehicleBrandService;
import borkovacd.carrentalapp.service.VehicleFuelTypeService;
import borkovacd.carrentalapp.service.VehicleService;
import borkovacd.carrentalapp.service.VehicleTypeService;

@Controller
@RequestMapping(path = "/api/vehicles")
public class VehicleController {
	
	@Autowired
	VehicleService vehicleService;
	@Autowired
	VehicleTypeService vehicleTypeService;
	@Autowired
	VehicleBrandService vehicleBrandService;
	@Autowired
	VehicleBodyTypeService vehicleBodyTypeService;
	@Autowired
	VehicleFuelTypeService vehicleFuelTypeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<PageDTO<VehicleDTO>> getAllVehicles(Pageable pageable) {
		List<VehicleDTO> vehicles = new ArrayList<VehicleDTO>();
		Page<Vehicle> page = vehicleService.getVehicles(pageable);
		for(Vehicle vehicle : page.getContent()) {
			if(vehicle.isDeleted() == false) {
				ModelMapper modelMapper = new ModelMapper();
				// *** CHECK IF IT'S GOOD PRACTICE ***
				modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
					mapper.map(src -> src.getFuelTypesNames(),
							VehicleDTO::setFuelTypes);
					});
				VehicleDTO vehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
				vehicles.add(vehicleDTO);
			}
		}
		PageDTO<VehicleDTO> pageDTO = new PageDTO<VehicleDTO>(pageable.getPageNumber(), pageable.getPageSize(), 
				page.getTotalElements(), vehicles);
		return new ResponseEntity<PageDTO<VehicleDTO>> (pageDTO, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
		Vehicle vehicle = vehicleService.findById(id);
		if(vehicle != null && vehicle.isDeleted() == false) {
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
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<VehicleDTO> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
		VehicleType type = vehicleTypeService.findVehicleTypeByName(vehicleDTO.getTypeName());
		VehicleBrand brand = vehicleBrandService.findVehicleBrandByName(vehicleDTO.getBrandName());
		VehicleBodyType bodyType = vehicleBodyTypeService.findVehicleBodyTypeByName(vehicleDTO.getBodyTypeName());
		List<VehicleFuelType> fuelTypes = new ArrayList<VehicleFuelType>();
		for(String fuelTypeName : vehicleDTO.getFuelTypes()) {
			VehicleFuelType fuelType = vehicleFuelTypeService.findVehicleFuelTypeByName(fuelTypeName);
			fuelTypes.add(fuelType);
		}
		
		Vehicle vehicle = new Vehicle();
		vehicle.setType(type);
		vehicle.setBrand(brand);
		vehicle.setModel(vehicleDTO.getModel());
		vehicle.setBodyType(bodyType);
		vehicle.setFuelTypes(fuelTypes);
		vehicle.setEngineVolume(vehicleDTO.getEngineVolume());
		vehicle.setEnginePower(vehicleDTO.getEnginePower());
		if(vehicleDTO.getTransmissionType().equals("MANUAL"))
			vehicle.setTransmissionType(TransmissionType.MANUAL);
		else if(vehicleDTO.getTransmissionType().equals("AUTOMATIC"))
			vehicle.setTransmissionType(TransmissionType.AUTOMATIC);
		else 
			vehicle.setTransmissionType(TransmissionType.CVT);
		vehicle.setGearNumber(vehicleDTO.getGearNumber());
		if(vehicleDTO.getDriveType().equals("AWD"))
			vehicle.setDriveType(DriveType.AWD);
		else if(vehicleDTO.getDriveType().equals("FWD"))
			vehicle.setDriveType(DriveType.FWD);
		else
			vehicle.setDriveType(DriveType.RWD);
		vehicle.setNumberOfDoors(vehicleDTO.getNumberOfDoors());
		vehicle.setNumberOfSeats(vehicleDTO.getNumberOfSeats());
		vehicle.setRentalPrice(vehicleDTO.getRentalPrice());
		vehicle.setName(vehicleDTO.getName());
		vehicle.setDescription(vehicleDTO.getDescription());
		vehicle.setDeleted(false);
		
		vehicle = vehicleService.save(vehicle);
		ModelMapper modelMapper = new ModelMapper();
		// *** CHECK IF IT'S GOOD PRACTICE ***
		modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
			  mapper.map(src -> src.getFuelTypesNames(),
			      VehicleDTO::setFuelTypes);
			});
		VehicleDTO createdVehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
		return new ResponseEntity<VehicleDTO>(createdVehicleDTO, HttpStatus.CREATED);		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO) {
		VehicleType type = vehicleTypeService.findVehicleTypeByName(vehicleDTO.getTypeName());
		VehicleBrand brand = vehicleBrandService.findVehicleBrandByName(vehicleDTO.getBrandName());
		VehicleBodyType bodyType = vehicleBodyTypeService.findVehicleBodyTypeByName(vehicleDTO.getBodyTypeName());
		List<VehicleFuelType> fuelTypes = new ArrayList<VehicleFuelType>();
		for(String fuelTypeName : vehicleDTO.getFuelTypes()) {
			VehicleFuelType fuelType = vehicleFuelTypeService.findVehicleFuelTypeByName(fuelTypeName);
			fuelTypes.add(fuelType);
		}
		
		Vehicle vehicle = vehicleService.findById(id);
		if(vehicle != null && vehicle.isDeleted() == false) {
			vehicle.setType(type);
			vehicle.setBrand(brand);
			vehicle.setModel(vehicleDTO.getModel());
			vehicle.setBodyType(bodyType);
			vehicle.setFuelTypes(fuelTypes);
			vehicle.setEngineVolume(vehicleDTO.getEngineVolume());
			vehicle.setEnginePower(vehicleDTO.getEnginePower());
			if(vehicleDTO.getTransmissionType().equals("MANUAL"))
				vehicle.setTransmissionType(TransmissionType.MANUAL);
			else if(vehicleDTO.getTransmissionType().equals("AUTOMATIC"))
				vehicle.setTransmissionType(TransmissionType.AUTOMATIC);
			else 
				vehicle.setTransmissionType(TransmissionType.CVT);
			vehicle.setGearNumber(vehicleDTO.getGearNumber());
			if(vehicleDTO.getDriveType().equals("AWD"))
				vehicle.setDriveType(DriveType.AWD);
			else if(vehicleDTO.getDriveType().equals("FWD"))
				vehicle.setDriveType(DriveType.FWD);
			else
				vehicle.setDriveType(DriveType.RWD);
			vehicle.setNumberOfDoors(vehicleDTO.getNumberOfDoors());
			vehicle.setNumberOfSeats(vehicleDTO.getNumberOfSeats());
			vehicle.setRentalPrice(vehicleDTO.getRentalPrice());
			vehicle.setName(vehicleDTO.getName());
			vehicle.setDescription(vehicleDTO.getDescription());
			vehicle = vehicleService.save(vehicle);
			ModelMapper modelMapper = new ModelMapper();
			// *** CHECK IF IT'S GOOD PRACTICE ***
			modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
				  mapper.map(src -> src.getFuelTypesNames(),
				      VehicleDTO::setFuelTypes);
				});
			VehicleDTO createdVehicleDTO = modelMapper.map(vehicle, VehicleDTO.class);
			return new ResponseEntity<VehicleDTO>(createdVehicleDTO, HttpStatus.OK);		
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteVehicle(@PathVariable Long id) {
		Vehicle vehicle = vehicleService.findById(id);
		if(vehicle != null && vehicle.isDeleted() == false) {
			vehicle.setDeleted(true);
			vehicle = vehicleService.save(vehicle);
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	 
	
	

}
