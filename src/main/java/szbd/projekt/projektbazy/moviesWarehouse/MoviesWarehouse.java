package szbd.projekt.projektbazy.moviesWarehouse;

import javax.persistence.Column;
import javax.persistence.Entity;

//@Entity
public class MoviesWarehouse {

	@Column(name = "quantity", nullable = false)
	private int quantity;
	@Column(name = "charge", nullable = false, scale = 2)
	private double charge;
	
	
}
