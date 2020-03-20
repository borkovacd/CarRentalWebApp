package borkovacd.carrentalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import borkovacd.carrentalapp.model.VehicleBrand;

public interface VehicleBrandRepository extends JpaRepository<VehicleBrand, Long> {

	VehicleBrand findVehicleBrandByName(String brandName);

}
