package szbd.projekt.projektbazy.rentals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;


@Service
public class RentalsService {

	@Autowired
	private RentalsRepository rentalsRepository;
	@PersistenceContext
	private EntityManager entityManager ;

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

		if(rental.getRentalDate() != null && rental.getReturnDate() != null &&
				rental.getReturnDate().getTime() < rental.getRentalDate().getTime()) {
			throw new NoResultException();
		} else {
			rentalsRepository.save(rental);
		}
	}
	
	public void deleteRental(Integer idRental) {

		try {
			rentalsRepository.deleteById(idRental);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist.", ex);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is a foreign key in RENTAL ELEMENT " +
					"table. Delete records in RENTAL ELEMENT first.", ex);
		}
	}

	public double getCharge(Integer idRental) {

		double fullCharge = 0;
		List<Object[]> lOb = rentalsRepository.getCharge(idRental);
		for (Object o[] : lOb) {
			double tempCharge;
			BigDecimal charge = (BigDecimal) o[0];
			Integer amount = (Integer) o[1];
			Timestamp rentalDate = (Timestamp) o[2];
			Timestamp returnDate = (Timestamp) o[3];

			long diff = returnDate.getTime() - rentalDate.getTime();
			int diffDays = (int) (diff / (24*60*60*1000));
			if(diffDays == 0)
				diffDays = 1;
			tempCharge = charge.doubleValue() * amount * diffDays;
			fullCharge = fullCharge + tempCharge;
		}
		return fullCharge;
	}


	public void increaseAmount(Integer idRental) {

		List<Object[]> lst = rentalsRepository.getWarehouseAndAmount(idRental);

		for (Object o[] : lst) {
			Integer idWarehouse = (Integer) o[0];
			Integer amount = (Integer) o[1];

			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("increment_quantity");
			query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
			query.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);

			query.setParameter(1, idWarehouse);
			query.setParameter(2, amount);
			query.setParameter(3, idRental);

			query.execute();
		}
	}

	public List<String> getTitlesByIdRental(Integer idRental) {

		return rentalsRepository.getTitleMoviesByIdRental(idRental);
	}

	public List<Rentals> getRentalsNotReturnedByIdRentalOffice(Integer idRentalOffice) {

		return rentalsRepository.getAllRentalsNullReturnedByRentalOffice(idRentalOffice);
	}
	
}
