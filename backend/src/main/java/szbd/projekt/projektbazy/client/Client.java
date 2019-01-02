package szbd.projekt.projektbazy.client;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.adress.Adress;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@Entity
public class Client {

	@Id
	@GeneratedValue(
	    strategy= GenerationType.AUTO, 
	    generator="native"
	)
	@GenericGenerator(
	    name = "native", 
	    strategy = "native"
	)
	@Column(name = "id_client", unique = true)
	private int idClient;
	@Column(name = "client_first_name", nullable = false)
	private String clientFirstName;
	@Column(name = "client_last_name", nullable = false)
	private String clientLastName;
	@Column(name = "registration_date", nullable = false)
	private Date registrationDate;
	private String email;
	private int number;
	
	@ManyToOne
	@JoinColumn(name = "id_rental_office", nullable = false)
	private RentalOffice rentalOffice;
	@OneToOne
	@JoinColumn(name = "id_adress", nullable = false)
	private Adress adress;
	
	public Client() {
		
	}
	
	public Client(int idClient, String clientFirstName, String clientLastName, Date registrationDate, 
			String email, int number, int idRentalOffice, int idAdress) {
		super();
		this.idClient = idClient;
		this.clientFirstName = clientFirstName;
		this.clientLastName = clientLastName;
		this.registrationDate = registrationDate;
		this.email = email;
		this.number = number;
		this.rentalOffice = new RentalOffice(idRentalOffice, "", 0);
		this.adress = new Adress(idAdress, "", "", "", "", "");
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getClientFirstName() {
		return clientFirstName;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public String getClientLastName() {
		return clientLastName;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public RentalOffice getRentalOffice() {
		return rentalOffice;
	}

	public void setRentalOffice(RentalOffice rentalOffice) {
		this.rentalOffice = rentalOffice;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	
	
	
}
