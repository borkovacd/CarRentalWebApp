package borkovacd.carrentalapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import borkovacd.carrentalapp.enums.DriveType;
import borkovacd.carrentalapp.enums.TransmissionType;

@Entity
@Table(catalog = "db_carrental", name = "vehicle")
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private VehicleType type;
	
	@ManyToOne
	private VehicleBrand brand;
	
	private String model;
	
	@ManyToOne
	private VehicleBodyType bodyType;
	
	@ManyToMany
	private List<VehicleFuelType> fuelTypes = new ArrayList<VehicleFuelType>();
	
	private int engineVolume;
	
	private double enginePower;
	
	private TransmissionType transmissionType;
	
	private int gearNumber;
	
	private DriveType driveType;
	
	private int numberOfSeats;
	
	private int numberOfDoors;
	
	private BigDecimal rentalPrice; //price per day
	
	private String name;
	
	private String description;
	
	
	

}
