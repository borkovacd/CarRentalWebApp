package borkovacd.carrentalapp.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDTO {
	
	private Long id;
	
	@Valid
	private UserDTO user;
	
	@Valid
	private VehicleDTO vehicle;
	
	@NotBlank
	private String startDate;
	
	@NotBlank
	private String endDate;
	
	private BigDecimal rentalPrice;
	
	private boolean deleted;
	
	

}
