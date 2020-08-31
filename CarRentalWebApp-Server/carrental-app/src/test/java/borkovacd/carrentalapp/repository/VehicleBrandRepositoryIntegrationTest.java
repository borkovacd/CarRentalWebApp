package borkovacd.carrentalapp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import borkovacd.carrentalapp.model.VehicleBrand;

@RunWith(SpringRunner.class)
@DataJpaTest
public class VehicleBrandRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private VehicleBrandRepository vehicleBrandRepository;
	
	// WRITE TEST CASES HERE
	
	@Test
	public void whenFindByName_thenReturnBrand() {
		
		// given
		VehicleBrand brand = new VehicleBrand();
		brand.setName("Mazda");
	    entityManager.persist(brand);
	    entityManager.flush();
	 
	    // when
	    VehicleBrand found = vehicleBrandRepository.findVehicleBrandByName(brand.getName());
	 
	    // then
	    assertThat(found.getName())
	      .isEqualTo(brand.getName());
	}


}
