package borkovacd.carrentalapp.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {
	
	private Long id;
	
	@NotBlank
	private String typeName;
	
	@NotBlank
	private String brandName;
	
	@NotBlank
	private String model;
	private String bodyTypeName;
	private List<String> fuelTypes = new ArrayList<String>();
	private int engineVolume;
	private double enginePower;
	private String transmissionType;
	private int gearNumber;
	private String driveType;
	private int numberOfSeats;
	private int numberOfDoors;
	private BigDecimal rentalPrice;
	private String name;
	private String description;
	private String imageName;

}
