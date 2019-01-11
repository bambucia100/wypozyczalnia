package szbd.projekt.projektbazy.rentalOffice;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import szbd.projekt.projektbazy.adress.Adress;

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
	@OneToOne
	@JoinColumn(name = "id_adress", nullable = false)
	private Adress adress;

	public RentalOffice() {
	}
	
	public RentalOffice(int idRentalOffice, String rentalOfficeName, int idAdress) {
		super();
		this.adress = new Adress(idAdress, "", "", "", "", "");
		this.idRentalOffice = idRentalOffice;
		this.rentalOfficeName = rentalOfficeName;
	}

	public String getRentalOfficeName() {
		return rentalOfficeName;
	}

	public void setRentalOfficeName(String rentalOfficeName) {
		this.rentalOfficeName = rentalOfficeName;
	}

	public int getIdRentalOffice() {
		return idRentalOffice;
	}

	public void setIdRentalOffice(int idRentalOffice) {
		this.idRentalOffice = idRentalOffice;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	
}
