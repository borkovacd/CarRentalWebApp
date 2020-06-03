package borkovacd.carrentalapp.repository;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import borkovacd.carrentalapp.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	
	Page<Vehicle> findVehicleByModelContains(String model, Pageable pageable);

	@Query("SELECT v FROM Vehicle v WHERE (:type is '' or v.type.name = :type) AND (:brand is '' or v.brand.name = :brand) AND " + 
			" (:model is '' or v.model  LIKE %:model%) AND (:lowestPrice is null or v.rentalPrice >= :lowestPrice) " + 
			" AND (:lowestPrice is null or v.rentalPrice <= :highestPrice)")
	Page<Vehicle> findAllVehicles(
			@Param("type") String type,
			@Param("brand") String brand,
			@Param("model") String model, 
			@Param("lowestPrice") BigDecimal lowestPrice, 
			@Param("highestPrice") BigDecimal highestPrice, 
			Pageable pageable);
}
