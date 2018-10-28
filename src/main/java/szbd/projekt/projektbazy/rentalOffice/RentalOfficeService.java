package szbd.projekt.projektbazy.rentalOffice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RentalOfficeService {
	
	@Autowired
	private RentalOfficeRepository rentalOfficeRepository;
	
	public List<RentalOffice> getAllRentalOffices() {
		List<RentalOffice> rentalOffices = new ArrayList<>();
		rentalOfficeRepository.findAll()
		.forEach(rentalOffices::add);
		return rentalOffices;
	}
	
	
	public Optional<RentalOffice> getRentalOffice(String idRentalOffice) {
		return rentalOfficeRepository.findById(idRentalOffice);
	}
	
	public void addRentalOffice(RentalOffice rentalOffice) {
		rentalOfficeRepository.save(rentalOffice);
	}

	public void updateRentalOffice(String idRentalOffice, RentalOffice rentalOffice) {
		rentalOfficeRepository.save(rentalOffice);
	}
	
	public void deleteRentalOffice(String idRentalOffice) {
		rentalOfficeRepository.deleteById(idRentalOffice);
	}
	
	

}
