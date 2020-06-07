package borkovacd.carrentalapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import borkovacd.carrentalapp.model.Rental;
import borkovacd.carrentalapp.model.Vehicle;

public interface RentalRepository extends JpaRepository<Rental, Long> {

	@Query("SELECT r FROM Rental r WHERE "
			+ "(:user is '' or r.user.username LIKE %:user%) AND "
			+ "(:vehicleType is '' or r.vehicle.type.name = :vehicleType) AND "
			+ "(:vehicleBrand is '' or r.vehicle.brand.name = :vehicleBrand) AND " 
			+ "(:vehicleModel is '' or r.vehicle.model  LIKE %:vehicleModel%) AND "
			+ "(:startDate is null or r.startDate >= :startDate) AND "
			+ "(:endDate is null or r.endDate <= :endDate)")
	Page<Rental> findAllRentals(
			@Param("user") String user, 
			@Param("vehicleType") String vehicleType,
			@Param("vehicleBrand") String vehicleBrand,
			@Param("vehicleModel") String vehicleModel,
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate,
			Pageable pageable);

	@Query("SELECT r FROM Rental r WHERE "
			+ "(r.vehicle.id = :vehicleId) "
			+ " AND "
			+ "( ((:startDate >= r.startDate) AND ( (:endDate <= r.endDate) OR (:startDate <= r.endDate) ) ) "
			+ " OR "
			+ "((:startDate <= r.startDate) AND (:endDate >= r.startDate))) "
			+ " AND "
			+ "(:rentalId is null or r.id != :rentalId)")
	List<Rental> findAllRentalsBetweenDates(
			@Param("vehicleId") Long vehicleId, 
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate,
			@Param("rentalId") Long rentalId);

}
