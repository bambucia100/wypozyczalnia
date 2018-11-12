package szbd.projekt.projektbazy.moviesWarehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.movie.Movie;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@Entity
public class MoviesWarehouse {
	
	@Id
	@GeneratedValue(
		    strategy= GenerationType.AUTO, 
		    generator="native"
		)
		@GenericGenerator(
		    name = "native", 
		    strategy = "native"
		)
	@Column(name = "id_warehouse", nullable = false)
	private int idMovieWarehouse;
	@Column(name = "quantity", nullable = false)
	private int quantity;
	@Column(name = "charge", nullable = false, scale = 2)
	private double charge;
	
	@OneToOne()
	@JoinColumn(name = "id_rental_office", nullable = false)
	private RentalOffice rentalOffice;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "id_movie", nullable = true)
	private Movie movie;
	
	public MoviesWarehouse() {
		
	}
	
	public MoviesWarehouse(int idMovieWarehouse, int quantity, double charge, int idRentalOffice, int idMovie) {
		this.idMovieWarehouse = idMovieWarehouse;
		this.quantity = quantity;
		this.charge = charge;
		this.rentalOffice = new RentalOffice(idRentalOffice, "");
		this.movie = new Movie(idMovie, "", 0, "", 0, "", 0);
	}
	
	public int getIdMovieWarehouse() {
		return idMovieWarehouse;
	}

	public void setIdMovieWarehouse(int idMovieWarehouse) {
		this.idMovieWarehouse = idMovieWarehouse;
	}

	public RentalOffice getIdRentalOffice() {
		return rentalOffice;
	}

	public void setIdRentalOffice(RentalOffice idRentalOffice) {
		this.rentalOffice = idRentalOffice;
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
		return rentalOffice;
	}

	public void setRentalOffice(RentalOffice rentalOffice) {
		this.rentalOffice = rentalOffice;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	
	
	
	
}
