package szbd.projekt.projektbazy.rentals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;


@Service
public class RentalsService {

	@Autowired
	private RentalsRepository rentalsRepository;

	@PersistenceContext
	private EntityManager entityManager ;

	public void changeQuantity(Integer idMovie, Integer idRentalOffice) {

		StoredProcedureQuery query = entityManager.createStoredProcedureQuery("change_quantity");
		query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
		query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);

		query.setParameter(1, idMovie);
		query.setParameter(2, idRentalOffice);

		query.execute();
	}


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
