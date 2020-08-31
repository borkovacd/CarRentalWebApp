package borkovacd.carrentalapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import borkovacd.carrentalapp.enums.UserType;
import borkovacd.carrentalapp.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private UserRepository userRepository;
	
	// WRITE TEST CASES HERE
	
	@Test
	public void whenFindByUsername_thenReturnUser() {
		
		// given
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateOfBirth = LocalDate.parse("1996-05-05",formatter);
	    User user = new User("Samuel", "Stivens", "sstivens@gmail.com", "samuel",
	    		"$10$YkSmPRsXKjQVJ1y1OMl3vOte7DmU9gUKT7T.pnwMZ7oR.39aaRBom", "777 Brockton Avenue, Abington MA 2351", 
	    		dateOfBirth, "202-555-0194", UserType.USER, false, false);
	    entityManager.persist(user);
	    entityManager.flush();
	 
	    // when
	    User found = userRepository.findByUsername(user.getUsername()).orElse(null);
	 
	    // then
	    assertThat(found.getUsername())
	      .isEqualTo(user.getUsername());
	}

}
