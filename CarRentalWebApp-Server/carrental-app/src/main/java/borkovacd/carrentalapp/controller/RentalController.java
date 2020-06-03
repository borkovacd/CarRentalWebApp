package borkovacd.carrentalapp.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import borkovacd.carrentalapp.dto.PageDTO;
import borkovacd.carrentalapp.dto.RentalDTO;
import borkovacd.carrentalapp.dto.VehicleDTO;
import borkovacd.carrentalapp.model.Rental;
import borkovacd.carrentalapp.model.User;
import borkovacd.carrentalapp.model.Vehicle;
import borkovacd.carrentalapp.repository.RentalRepository;
import borkovacd.carrentalapp.service.RentalService;
import borkovacd.carrentalapp.service.UserService;
import borkovacd.carrentalapp.service.VehicleService;

@RestController
@RequestMapping(path = "/api/rentals")
public class RentalController {
	
	@Autowired
	private RentalService rentalService;
	@Autowired
	private UserService userService;
	@Autowired
	private VehicleService vehicleService;
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<PageDTO<RentalDTO>> getAllRentals(
			@RequestParam(required = false, defaultValue = "") String user, 
			@RequestParam(required = false, defaultValue = "") String vehicleType,
			@RequestParam(required = false, defaultValue = "") String vehicleBrand,
			@RequestParam(required = false, defaultValue = "") String vehicleModel,
			@RequestParam(required = false, defaultValue = "") String startDate, 
			@RequestParam(required = false, defaultValue = "") String endDate,
			Pageable pageable) {
		
		List<RentalDTO> rentals = new ArrayList<RentalDTO>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		LocalDate d1,d2;
		if(!startDate.equals("")) {
			d1 = LocalDate.parse(startDate, formatter);
		} else {
			d1 = null;
		}
		if(!endDate.equals("")) {
			d2 = LocalDate.parse(endDate, formatter);
		} else {
			d2 = null;
		}

		Page<Rental> page = rentalService.getRentals(user, vehicleType, vehicleBrand, vehicleModel, d1, d2, pageable);
		
		for(Rental rental : page.getContent()) {
			ModelMapper modelMapper = new ModelMapper();
			RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
			rentals.add(rentalDTO);
		}
		
		PageDTO<RentalDTO> pageDTO = new PageDTO<RentalDTO>(pageable.getPageNumber(), pageable.getPageSize(), 
				page.getTotalElements(), rentals);
		return new ResponseEntity<PageDTO<RentalDTO>> (pageDTO, HttpStatus.OK);		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<RentalDTO> addNewRental(@Valid @RequestBody RentalDTO rentalDTO) {
		
		Vehicle vehicle = vehicleService.findById(rentalDTO.getVehicle().getId());
		if(vehicle == null) 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		User user = userService.findById(rentalDTO.getUser().getId());
		if(user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate,endDate;
		startDate = LocalDate.parse(rentalDTO.getStartDate(),formatter);
		endDate = LocalDate.parse(rentalDTO.getEndDate(),formatter);
		System.out.println("Pocetni datum: " + startDate + ", krajnji datum: " + endDate);
		
		if(rentalService.isVehicleAvailable(vehicle.getId(), startDate, endDate)) {
			Rental rental = new Rental(user, vehicle, startDate, endDate, null, false);
			rental = rentalService.saveRental(rental);
			ModelMapper modelMapper = new ModelMapper();
			RentalDTO createdRentalDTO = modelMapper.map(rental, RentalDTO.class);
			return new ResponseEntity<RentalDTO>(createdRentalDTO, HttpStatus.CREATED);
		} else {
			//Zeljeno vozilo nije na raspologanju u izabranom periodu
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
