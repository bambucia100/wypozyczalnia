package szbd.projekt.projektbazy.employee;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.adress.Adress;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@Entity
public class Employee {

	@Id
	@GeneratedValue(
	    strategy= GenerationType.AUTO, 
	    generator="native"
	)
	@GenericGenerator(
	    name = "native", 
	    strategy = "native"
	)
	@Column(name = "id_employee", unique = true)
	private int idEmployee;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	private String position;
	private String email;
	private String phone;

	@ManyToOne
	@JoinColumn(name = "id_rental_office", nullable = false)
	private RentalOffice rentalOffice;
	@OneToOne
	@JoinColumn(name = "id_adress", nullable = false)
	private Adress adress;
	
	public Employee() {
		
	}
	public Employee(int idEmployee, String firstName, String lastName,
			String position, String email, String phone, int idRentalOffice, int idAdress) {
		super();
		this.idEmployee = idEmployee;
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.email = email;
		this.phone = phone;
		this.rentalOffice = new RentalOffice(idRentalOffice, "", 0);
		this.adress = new Adress(idAdress, "", "", "", "", "");
	}
	
	public int getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(int idEmployee) {
		this.idEmployee = idEmployee;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
