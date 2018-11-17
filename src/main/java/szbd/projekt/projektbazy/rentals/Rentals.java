package szbd.projekt.projektbazy.rentals;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.client.Client;
import szbd.projekt.projektbazy.movie.Movie;

@Entity
public class Rentals {
	
	@Id
	@GeneratedValue(
	    strategy= GenerationType.AUTO, 
	    generator="native"
	)
	@GenericGenerator(
	    name = "native", 
	    strategy = "native"
	)
	@Column(name = "id_rental", unique = true, nullable = false)
	private int idRental;
	@Column(name= "rental_date", unique = false, nullable = false)
	private Date rentalDate;
	@Column(name = "return_date", unique = false, nullable = true)
	private Date returnDate;
	private double payment;
	@ManyToOne
	@JoinColumn(name = "id_client", nullable = false)
	private Client client;
	@ManyToOne
	@JoinColumn(name = "id_movie", nullable = false)
	private Movie movie;
	
	Rentals() {
		
	}
	
	Rentals(int idRental, Date rentalDate, Date returnDate, double payment, Integer idClient, Integer idMovie) {
		this.idRental = idRental;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.payment = payment;
		this.client = new Client(idMovie, "", "", null, "",0 , 0, 0);
		this.movie = new Movie(idMovie, "", 0, "", 0, "", 0);
	}
	
	public int getIdRentals() {
		return idRental;
	}
	public void setIdRentals(int idRentals) {
		this.idRental = idRentals;
	}
	public Date getRentalDate() {
		return rentalDate;
	}
	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	
}
