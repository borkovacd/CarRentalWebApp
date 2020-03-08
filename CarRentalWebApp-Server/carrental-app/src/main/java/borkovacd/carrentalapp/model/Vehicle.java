package borkovacd.carrentalapp.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import borkovacd.carrentalapp.enums.DriveType;
import borkovacd.carrentalapp.enums.TransmissionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter	
@NoArgsConstructor
@AllArgsConstructor
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
	@JoinTable(
	        name = "vehicle_fuel_types", 
	        joinColumns = { @JoinColumn(name = "vehicle_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "fuel_type_id") }
	)
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
	
	// *** CHECK IF IT'S GOOD PRACTICE ***
	public List<String> getFuelTypesNames() {
		List<String> fuelTypesNames = new ArrayList<String>();
		for(VehicleFuelType ft : getFuelTypes()) {
			fuelTypesNames.add(ft.getName());
		}
		return fuelTypesNames;
	}
	
	
	

}
