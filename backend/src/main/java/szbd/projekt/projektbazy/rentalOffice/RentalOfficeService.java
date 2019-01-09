package szbd.projekt.projektbazy.rentalOffice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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

	public Optional<RentalOffice> getRentalOffice(Integer idRentalOffice) {
		return rentalOfficeRepository.findById(idRentalOffice);
	}
	
	public void addRentalOffice(RentalOffice rentalOffice) {
		rentalOfficeRepository.save(rentalOffice);
	}

	public void updateRentalOffice(Integer idRentalOffice, RentalOffice rentalOffice) {

			rentalOfficeRepository.save(rentalOffice);
	}
	
	public void deleteRentalOffice(Integer idRentalOffice) {

		try {
			rentalOfficeRepository.deleteById(idRentalOffice);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist.", ex);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is a foreign key in EMPLOYEE and " +
					"MOVIES WAREHOUSE and CLIENT tables. Delete records in EMPLOYEE and MOVIES WAREHOUSE and CLIENT first.", ex);
		}

	}
	
	

}
