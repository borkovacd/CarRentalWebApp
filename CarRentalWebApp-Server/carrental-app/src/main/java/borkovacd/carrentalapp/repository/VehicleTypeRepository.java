package borkovacd.carrentalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import borkovacd.carrentalapp.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

	VehicleType findVehicleTypeByName(String typeName);

}
