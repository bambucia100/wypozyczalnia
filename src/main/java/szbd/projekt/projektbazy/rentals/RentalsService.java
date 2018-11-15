package szbd.projekt.projektbazy.rentals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RentalsService {

	@Autowired
	private RentalsRepository rentalsRepository;
	

	public List<Rentals> getAllRentals() {
		
		List<Rentals> rentals = new ArrayList<>();
		rentalsRepository.findAll()
		.forEach(rentals::add);
		return rentals;
	}
	
	
	public Optional<Rentals> getRental(Integer idRental) {
		
		return rentalsRepository.findById(idRental);
	}
	
	public void addRental(Rentals rental) {
		
		rentalsRepository.save(rental);
	}

	public void updateRental(Integer idRental, Rentals rental) {
		
		rentalsRepository.save(rental);
	}
	
	public void deleteRental(Integer idRental) {
		
		rentalsRepository.deleteById(idRental);
	}
	
}
