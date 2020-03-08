package borkovacd.carrentalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import borkovacd.carrentalapp.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

}
