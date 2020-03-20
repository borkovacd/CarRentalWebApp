package borkovacd.carrentalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import borkovacd.carrentalapp.model.VehicleBodyType;

public interface VehicleBodyTypeRepository extends JpaRepository<VehicleBodyType, Long> {

	VehicleBodyType findVehicleBodyTypeByName(String bodyTypeName);

}
