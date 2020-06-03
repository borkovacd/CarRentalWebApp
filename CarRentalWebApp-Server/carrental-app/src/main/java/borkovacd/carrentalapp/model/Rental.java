package borkovacd.carrentalapp.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(catalog = "db_carrental", name = "rentals")
@Where(clause="deleted=0")
public class Rental {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private User user;
	
	@OneToOne
	private Vehicle vehicle;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private BigDecimal rentalPrice;
	
	private boolean deleted;

	public Rental(User user, Vehicle vehicle, LocalDate startDate, LocalDate endDate, BigDecimal rentalPrice,
			boolean deleted) {
		super();
		this.user = user;
		this.vehicle = vehicle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rentalPrice = rentalPrice;
		this.deleted = deleted;
	}
	
	
	
}
