package borkovacd.carrentalapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import borkovacd.carrentalapp.model.ImageModel;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
	
	Optional<ImageModel> findByName(String name);

}
