package borkovacd.carrentalapp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import borkovacd.carrentalapp.dto.PageDTO;
import borkovacd.carrentalapp.dto.VehicleDTO;
import borkovacd.carrentalapp.enums.DriveType;
import borkovacd.carrentalapp.enums.TransmissionType;
import borkovacd.carrentalapp.model.ImageModel;
import borkovacd.carrentalapp.model.Vehicle;
import borkovacd.carrentalapp.model.VehicleBodyType;
import borkovacd.carrentalapp.model.VehicleBrand;
import borkovacd.carrentalapp.model.VehicleFuelType;
import borkovacd.carrentalapp.model.VehicleType;
import borkovacd.carrentalapp.repository.ImageRepository;
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
	@Autowired
	ImageRepository imageRepository;

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<PageDTO<VehicleDTO>> getAllVehicles(
			@RequestParam(required = false, defaultValue = "") String type,
			@RequestParam(required = false, defaultValue = "") String brand,
			@RequestParam(required = false, defaultValue = "") String model,
			@RequestParam(required = false) BigDecimal lowestPrice, 
			@RequestParam(required = false) BigDecimal highestPrice,
			Pageable pageable) {
		List<VehicleDTO> vehicles = new ArrayList<VehicleDTO>();
		Page<Vehicle> page = vehicleService.getVehicles(type, brand, model, lowestPrice, highestPrice, pageable);
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

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VehicleDTO> addVehicle(@RequestBody VehicleDTO vehicleDTO) throws IOException {
		VehicleType type = vehicleTypeService.findVehicleTypeByName(vehicleDTO.getTypeName());
		VehicleBrand brand = vehicleBrandService.findVehicleBrandByName(vehicleDTO.getBrandName());
		VehicleBodyType bodyType = vehicleBodyTypeService.findVehicleBodyTypeByName(vehicleDTO.getBodyTypeName());
		List<VehicleFuelType> fuelTypes = new ArrayList<VehicleFuelType>();
		for(String fuelTypeName : vehicleDTO.getFuelTypes()) {
			VehicleFuelType fuelType = vehicleFuelTypeService.findVehicleFuelTypeByName(fuelTypeName);
			fuelTypes.add(fuelType);
		}
		
		String imageName = vehicleDTO.getImageName();
		System.out.println("**************************");
		System.out.println(imageName);
		ImageModel retrievedImage = imageRepository.findByName(imageName).get();


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
		vehicle.setImage(retrievedImage);

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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO) {
		VehicleType type = vehicleTypeService.findVehicleTypeByName(vehicleDTO.getTypeName());
		VehicleBrand brand = vehicleBrandService.findVehicleBrandByName(vehicleDTO.getBrandName());
		VehicleBodyType bodyType = vehicleBodyTypeService.findVehicleBodyTypeByName(vehicleDTO.getBodyTypeName());
		List<VehicleFuelType> fuelTypes = new ArrayList<VehicleFuelType>();
		for(String fuelTypeName : vehicleDTO.getFuelTypes()) {
			VehicleFuelType fuelType = vehicleFuelTypeService.findVehicleFuelTypeByName(fuelTypeName);
			fuelTypes.add(fuelType);
		}
		
		String imageName = vehicleDTO.getImageName();
		System.out.println("**************************");
		System.out.println(imageName);
		ImageModel retrievedImage = imageRepository.findByName(imageName).get();

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
			vehicle.setImage(retrievedImage);
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

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}	




}
