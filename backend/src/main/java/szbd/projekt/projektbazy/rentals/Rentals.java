package szbd.projekt.projektbazy.rentals;

import java.sql.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.adress.Adress;
import szbd.projekt.projektbazy.client.Client;
import szbd.projekt.projektbazy.employee.Employee;
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
	@Column(name = "id_rental")
	private int idRental;
	@Column(name= "rental_date", unique = false, nullable = false)
	private Date rentalDate;
	@Column(name = "return_date", unique = false, nullable = true)
	private Date returnDate;
	@ManyToOne
	@JoinColumn(name = "id_client", nullable = false)
	private Client client;
	@OneToOne
	@JoinColumn(name = "id_employee", nullable = false)
	private Employee employee;


	public Rentals() {
		
	}
	
	public Rentals(int idRental, Date rentalDate, Date returnDate, Integer idClient, Integer idEmployee) {

		this.idRental = idRental;
		this.rentalDate = rentalDate;
		this.returnDate = returnDate;
		this.client = new Client(idClient, "", "", null, "",0 , 0, 0);
		this.employee = new Employee(idEmployee, "", "", "", "", "", 0, 0);
	}


	public int getIdRental() {
		return idRental;
	}
	public void setIdRental(int idRental) {
		this.idRental = idRental;
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

	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
