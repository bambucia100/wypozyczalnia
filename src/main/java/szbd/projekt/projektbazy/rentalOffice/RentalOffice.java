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

	public int getId_rental_office() {
		return idRentalOffice;
	}

	public void setId_rental_office(int id_rental_office) {
		this.idRentalOffice = id_rental_office;
	}
	
	public RentalOffice() {
	}
	
	public RentalOffice(int id_rental_office) {
		super();
		this.idRentalOffice = id_rental_office;
	}
	
}
