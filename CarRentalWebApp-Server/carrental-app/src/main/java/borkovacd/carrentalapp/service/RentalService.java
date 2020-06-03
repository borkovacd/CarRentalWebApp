package borkovacd.carrentalapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import borkovacd.carrentalapp.model.Rental;
import borkovacd.carrentalapp.repository.RentalRepository;

@Service
public class RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;

	public Page<Rental> getRentals(String user, String vehicleType, String vehicleBrand, String vehicleModel,
			LocalDate startDate, LocalDate endDate, Pageable pageable) {
		return rentalRepository.findAllRentals(user, vehicleType, vehicleBrand, vehicleModel, startDate, endDate, pageable);
	}

	public boolean isVehicleAvailable(Long vehicleId, LocalDate startDate, LocalDate endDate) {
		List<Rental> rentals = getRentalsBetweenDates(vehicleId, startDate, endDate);
		if(rentals.isEmpty())
			return true;
		else {
			System.out.println("*****************");
			for(Rental r : rentals) {
				System.out.println(r.getVehicle().getName() + " " + r.getStartDate() + " " + r.getEndDate());
			}
			return false;
		}
	}

	private List<Rental> getRentalsBetweenDates(Long vehicleId, LocalDate startDate, LocalDate endDate) {
		List<Rental> rentals = new ArrayList<Rental>();
		return rentalRepository.findAllRentalsBetweenDates(vehicleId, startDate, endDate);
	}

	public Rental saveRental(Rental rental) {
		return rentalRepository.save(rental);
	}
	
	

}
