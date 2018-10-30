package szbd.projekt.projektbazy.moviesWarehouse;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@Entity
public class MoviesWarehouse {
	
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	@Column(name = "charge", nullable = false, scale = 2)
	private double charge;
	
	@OneToOne( optional = false)
	@JoinColumn(name = "id_rental_office", nullable = false)
	private RentalOffice idRentalOffice;
	@Id
	@Column(name = "id_movie", nullable = true)
	private int idMovie;
	
	public MoviesWarehouse() {
		
	}
	
	public MoviesWarehouse(int quantity, double charge, int idRentalOffice, int idMovie) {
		this.quantity = quantity;
		this.charge = charge;
		this.idRentalOffice = new RentalOffice(idRentalOffice, "");
		this.idMovie = idMovie;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getCharge() {
		return charge;
	}

	public void setCharge(double charge) {
		this.charge = charge;
	}

	public RentalOffice getRentalOffice() {
		return idRentalOffice;
	}

	public void setRentalOffice(RentalOffice rentalOffice) {
		this.idRentalOffice = rentalOffice;
	}

	public int getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}
	
	
	
}
