package szbd.projekt.projektbazy.moviesWarehouse;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import org.hibernate.annotations.NaturalId;
import org.springframework.context.annotation.Primary;
import szbd.projekt.projektbazy.movie.Movie;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@Entity
@Table(name = "movies_warehouse")
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
	private int idWarehouse;
	@Column(name = "quantity", nullable = false)
	private int quantity;
	@Column(name = "charge", nullable = false, scale = 2)
	private double charge;

	@NaturalId
	@ManyToOne()
	@JoinColumn(name = "id_rental_office", nullable = false)
	private RentalOffice rentalOffice;

	@NaturalId
	@ManyToOne()
	@JoinColumn(name = "id_movie", nullable = false)
	private Movie movie;

	public MoviesWarehouse() {

	}

	public MoviesWarehouse(int idWarehouse, int quantity, double charge, int idRentalOffice, int idMovie) {
		super();
		this.idWarehouse = idWarehouse;
		this.quantity = quantity;
		this.charge = charge;
		this.rentalOffice = new RentalOffice(idRentalOffice, "", 0);
		this.movie = new Movie(idMovie, "", 0, "", 0, "", "");
	}

	public int getIdMovieWarehouse() {
		return idWarehouse;
	}

	public void setIdMovieWarehouse(int idMovieWarehouse) {
		this.idWarehouse = idMovieWarehouse;
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