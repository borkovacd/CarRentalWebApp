package borkovacd.carrentalapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import org.springframework.web.bind.annotation.PathVariable;
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
		if(!startDate.equals("") && isDateValid(startDate, formatter)) {
			d1 = LocalDate.parse(startDate, formatter);
		} else {
			d1 = null;
		}
		if(!endDate.equals("") && isDateValid(endDate, formatter)) {
			d2 = LocalDate.parse(endDate, formatter);
		} else {
			d2 = null;
		}

		Page<Rental> page = rentalService.getRentals(user, vehicleType, vehicleBrand, vehicleModel, d1, d2, pageable);

		for(Rental rental : page.getContent()) {
			ModelMapper modelMapper = new ModelMapper();
			// *** CHECK IF IT'S GOOD PRACTICE ***
			modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
				mapper.map(src -> src.getFuelTypesNames(),
						VehicleDTO::setFuelTypes);
			});	
			RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
			rentals.add(rentalDTO);
		}

		PageDTO<RentalDTO> pageDTO = new PageDTO<RentalDTO>(pageable.getPageNumber(), pageable.getPageSize(), 
				page.getTotalElements(), rentals);
		return new ResponseEntity<PageDTO<RentalDTO>> (pageDTO, HttpStatus.OK);		
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<RentalDTO> getRental(@PathVariable Long id) {
		Rental rental = rentalService.findById(id);
		if(rental != null) {
			ModelMapper modelMapper = new ModelMapper();
			// *** CHECK IF IT'S GOOD PRACTICE ***
			modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
				mapper.map(src -> src.getFuelTypesNames(),
						VehicleDTO::setFuelTypes);
			});	
			RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
			return new ResponseEntity<RentalDTO>(rentalDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}

	//Provereni uslovi u Postmanu!
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

		if(isDateValid(rentalDTO.getStartDate(), formatter) && isDateValid(rentalDTO.getEndDate(), formatter)) {
			startDate = LocalDate.parse(rentalDTO.getStartDate(),formatter);
			endDate = LocalDate.parse(rentalDTO.getEndDate(),formatter);
		} else {
			//Nije moguce parsirati u LocalDate
			System.out.println("-------------------");
			System.out.println("Nije moguce parsirati datum");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if(endDate.isBefore(startDate)) {
			//Krajnji datum ne moze biti pre pocetnog
			System.out.println("-------------------");
			System.out.println("Krajnji datum ne moze biti pre pocetnog");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		LocalDate currentDate = LocalDate.now();
		System.out.println("Danasnji datum: " + currentDate);
		if(startDate.isBefore(currentDate)) {
			//Pocetni datum ne moze biti pre danasnjeg
			System.out.println("-------------------");
			System.out.println("Pocetni datum ne moze biti pre danasnjeg");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}

		if(rentalService.isVehicleAvailable(vehicle.getId(), startDate, endDate, null)) {
			Rental rental = new Rental(user, vehicle, startDate, endDate, null, false);
			rental = rentalService.saveRental(rental);
			ModelMapper modelMapper = new ModelMapper();
			// *** CHECK IF IT'S GOOD PRACTICE ***
			modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
				mapper.map(src -> src.getFuelTypesNames(),
						VehicleDTO::setFuelTypes);
			});	
			RentalDTO createdRentalDTO = modelMapper.map(rental, RentalDTO.class);
			return new ResponseEntity<RentalDTO>(createdRentalDTO, HttpStatus.CREATED);
		} else {
			//Zeljeno vozilo nije na raspologanju u izabranom periodu
			System.out.println("-------------------");
			System.out.println("Zeljeno vozilo nije na raspologanju u izabranom periodu");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id, @Valid @RequestBody RentalDTO rentalDTO) {

		Vehicle vehicle = vehicleService.findById(rentalDTO.getVehicle().getId());
		if(vehicle == null) 
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		User user = userService.findById(rentalDTO.getUser().getId());
		if(user == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate,endDate;

		if(isDateValid(rentalDTO.getStartDate(), formatter) && isDateValid(rentalDTO.getEndDate(), formatter)) {
			startDate = LocalDate.parse(rentalDTO.getStartDate(),formatter);
			endDate = LocalDate.parse(rentalDTO.getEndDate(),formatter);
		} else {
			//Nije moguce parsirati u LocalDate
			System.out.println("-------------------");
			System.out.println("Nije moguce parsirati datum");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if(endDate.isBefore(startDate)) {
			//Krajnji datum ne moze biti pre pocetnog
			System.out.println("-------------------");
			System.out.println("Krajnji datum ne moze biti pre pocetnog");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		LocalDate currentDate = LocalDate.now();
		System.out.println("Danasnji datum: " + currentDate);
		if(startDate.isBefore(currentDate)) {
			//Pocetni datum ne moze biti pre danasnjeg
			System.out.println("-------------------");
			System.out.println("Pocetni datum ne moze biti pre danasnjeg");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}
		
		Rental rental = rentalService.findById(id);
		if(rental != null) {
			if(rentalService.isVehicleAvailable(vehicle.getId(), startDate, endDate, rental.getId())) {
				rental.setVehicle(vehicle);
				rental.setUser(user);
				rental.setStartDate(startDate);
				rental.setEndDate(endDate);
				rental = rentalService.saveRental(rental);
				ModelMapper modelMapper = new ModelMapper();
				// *** CHECK IF IT'S GOOD PRACTICE ***
				modelMapper.typeMap(Vehicle.class, VehicleDTO.class).addMappings(mapper -> {
					mapper.map(src -> src.getFuelTypesNames(),
							VehicleDTO::setFuelTypes);
				});	
				RentalDTO updatedRentalDTO = modelMapper.map(rental, RentalDTO.class);
				return new ResponseEntity<RentalDTO>(updatedRentalDTO, HttpStatus.CREATED);
			} else {
				//Zeljeno vozilo nije na raspologanju u izabranom periodu
				System.out.println("-------------------");
				System.out.println("Zeljeno vozilo nije na raspologanju u izabranom periodu");
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteRental(@PathVariable Long id) {
		Rental rental = rentalService.findById(id); 
		if(rental != null) { //Ne treba dodatna provera isDeleted, jer ukoliko je vec izbrisano nece biti ni pronadjeno iznajmljivanje
			rental.setDeleted(true);
			rental = rentalService.saveRental(rental);
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}


	public boolean isDateValid(String dateStr, DateTimeFormatter formatter) {
		try {
			LocalDate.parse(dateStr, formatter);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

}
