package szbd.projekt.projektbazy.adress;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

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
	
	public Adress() { }
	
	public Adress(Integer idAdress, String country, String province, String city, String street, String houseNumber) {
		super();
		this.idAdress = idAdress;
		this.country = country;
		this.province = province;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;	
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
	
}
