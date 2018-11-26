package szbd.projekt.projektbazy.moviesWarehouse;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import szbd.projekt.projektbazy.movie.Movie;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@Entity
public class MoviesWarehouse {
	
	@PrePersist
	   private void prePersiste() {
	       if (getId() == null) {
	           MoviesWarehouseId pk = new MoviesWarehouseId();
	           pk.setMovie(getMovie());
	           pk.setRentalOffice(getRentalOffice());
	           setId(pk);
	       }
	   }
	
	
	@EmbeddedId
	private MoviesWarehouseId id;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	@Column(name = "charge", nullable = false, scale = 2)
	private double charge;
	
	@ManyToOne()
	@JoinColumn(name = "id_rental_office", nullable = false, insertable = false, updatable = false)
	private RentalOffice rentalOffice;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "id_movie", nullable = false, insertable = false, updatable = false)
	private Movie movie;
	
	
	public MoviesWarehouse() {
		
	}
	
	public MoviesWarehouse(int quantity, double charge, int idRentalOffice, int idMovie) {
		super();
		this.quantity = quantity;
		this.charge = charge;
		this.rentalOffice = new RentalOffice(idRentalOffice, "", 0);
		this.movie = new Movie(idMovie, "", 0, "", 0, "", "");
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

	public MoviesWarehouseId getId() {
		return id;
	}

	public void setId(MoviesWarehouseId id) {
		this.id = id;
	}

	
	
	
	
}
