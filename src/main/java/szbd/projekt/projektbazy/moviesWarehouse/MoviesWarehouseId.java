package szbd.projekt.projektbazy.moviesWarehouse;

import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import szbd.projekt.projektbazy.movie.Movie;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

public class MoviesWarehouseId implements Serializable {

	private static final long   serialVersionUID    = 1L;
	
	@ManyToOne()
	@JoinColumn(name = "id_rental_office", nullable = false, insertable = false, updatable = false)
	private RentalOffice rentalOffice;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "id_movie", nullable = false, insertable = false, updatable = false)
	private Movie movie;
	
	public MoviesWarehouseId() {
		
	}
	
	public MoviesWarehouseId(int idRentalOffice, int idMovie) {
		super();
		this.rentalOffice = new RentalOffice(idRentalOffice, "");
		this.movie = new Movie(idMovie, "", 0, "", 0, "", 0);
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
