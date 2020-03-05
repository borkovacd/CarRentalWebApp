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
	private VehicleType type; // tip vozila
	
	@ManyToOne
	private VehicleBrand brand; // proizvodjac
	
	private String model; // model
	
	@ManyToOne
	private VehicleBodyType bodyType; // tip karoserije
	
	@ManyToMany
	private List<VehicleFuelType> fuelTypes = new ArrayList<VehicleFuelType>(); //tip goriva
	
	private int engineVolume; // zapremina motora
	
	private double enginePower; // snaga motora
	
	private TransmissionType transmissionType; // tip menjaca
	
	private int gearNumber; //broj brzina
	
	private DriveType driveType; // tip pogona
	
	private int numberOfSeats; // broj sedista
	
	private int numberOfDoors; // broj vrata
	
	private BigDecimal rentalPrice; //price per day
	
	private String name;
	
	private String description;
	
	private boolean deleted;
	
	
	

}
