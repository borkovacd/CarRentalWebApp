package borkovacd.carrentalapp.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String address;
	private LocalDate dateOfBirth;
	private String phoneNumber;
	private boolean blocked;
}
