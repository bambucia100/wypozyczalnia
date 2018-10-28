package szbd.projekt.projektbazy.rentalOffice;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class RentalOffice {

	
	
	@Id
	@GeneratedValue(
	    strategy= GenerationType.AUTO, 
	    generator="native"
	)
	@GenericGenerator(
	    name = "native", 
	    strategy = "native"
	)
	@Column(name = "id_rental_office", unique = true)
	private int idRentalOffice;
	@Column(name = "rental_office_name", unique = true)
	private String rentalOfficeName;

	public RentalOffice() {
	}
	
	public RentalOffice(int idRentalOffice, String rentalOfficeName) {
		super();
		this.idRentalOffice = idRentalOffice;
		this.rentalOfficeName = rentalOfficeName;
	}
	
	public int getId_rental_office() {
		return idRentalOffice;
	}

	public void setId_rental_office(int idRentalOffice) {
		this.idRentalOffice = idRentalOffice;
	}

	public String getRentalOfficeName() {
		return rentalOfficeName;
	}

	public void setRentalOfficeName(String rentalOfficeName) {
		this.rentalOfficeName = rentalOfficeName;
	}
	
	
	
}
