package szbd.projekt.projektbazy.adress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.client.Client;
import szbd.projekt.projektbazy.employee.Employee;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@Entity
public class Adress {
	
	@Id
	@GeneratedValue(
		    strategy= GenerationType.AUTO, 
		    generator="native"
		)
		@GenericGenerator(
		    name = "native", 
		    strategy = "native"
		)
	@Column(name = "id_adress", nullable = false)
	private int idAdress;
	private String country;
	private String province;
	private String city;
	private String street;
	@Column(name = "house_number", nullable = false)
	private String houseNumber;
	@OneToOne()
	@JoinColumn(name = "id_rental_office", nullable = true)
	private RentalOffice rentalOffice;
	@OneToOne()
	@JoinColumn(name = "id_employee", nullable = true)
	private Employee employee;
	@OneToOne()
	@JoinColumn(name = "id_client", nullable = true)
	private Client client;
	
	public Adress() {
		
	}
	
	public Adress(Integer idAdress, String country, String province, String city, String street, String houseNumber,
			Integer idRentalOffice, Integer idEmployee, Integer idClient) {
		super();
		this.idAdress = idAdress;
		this.country = country;
		this.province = province;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
		this.rentalOffice = new RentalOffice(idRentalOffice, "");
		this.employee = new Employee(idEmployee, "", "", "", "", "", 0);
		this.client = new Client(idClient, "", "", null, "", 0, 0);
	}

	public int getIdAdress() {
		return idAdress;
	}

	public void setIdAdress(int idAdress) {
		this.idAdress = idAdress;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public RentalOffice getRentalOffice() {
		return rentalOffice;
	}

	public void setRentalOffice(RentalOffice rentalOffice) {
		this.rentalOffice = rentalOffice;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
		
	
}
