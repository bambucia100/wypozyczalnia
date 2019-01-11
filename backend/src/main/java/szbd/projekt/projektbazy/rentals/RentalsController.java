package szbd.projekt.projektbazy.rentals;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import szbd.projekt.projektbazy.adress.Adress;
import szbd.projekt.projektbazy.client.Client;
import szbd.projekt.projektbazy.employee.Employee;
import szbd.projekt.projektbazy.movie.Movie;
import szbd.projekt.projektbazy.moviesWarehouse.MoviesWarehouse;

import javax.persistence.*;

@RestController
public class RentalsController {

	@Autowired
	RentalsService rentalService;
	@Autowired
	RentalsRepository rentalsRepository;

	@RequestMapping(method=RequestMethod.GET,value="/rentals/all")
	public List<Rentals> getAllRentals() {
		
		return rentalService.getAllRentals();
	}
	@RequestMapping(method=RequestMethod.GET,value="/rentals/notReturned")
	public List<Rentals> getAllRentalsNotNull() {

		return rentalsRepository.getAllRentalsByNullReturnDate();
	}

	@RequestMapping(method=RequestMethod.GET,value="/rentals/{idRental}")
	public Optional<Rentals> getRental(@PathVariable Integer idRental) {
		
		return rentalService.getRental(idRental);
	}

	@RequestMapping(method=RequestMethod.POST,value="/rental/client/{idClient}/employee/{idEmployee}")
	public Integer addRentals(@RequestBody Rentals rental, @PathVariable Integer idClient,
							  @PathVariable Integer idEmployee) {

		rental.setClient(new Client(idClient, "", "", null, "", 0 , 0, 0));
		rental.setEmployee(new Employee(idEmployee, "", "", "", "", "", 0, 0));
		rentalService.addRental(rental);
		return rental.getIdRental();
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/rental/{idRental}/client/{idClient}/employee/{idEmployee}")
	public void updateRentals(@RequestBody Rentals rental, @PathVariable Integer idClient,
							  @PathVariable Integer idRental, @PathVariable Integer idEmployee) {

		try {
			rental.setClient(new Client(idClient, "", "", null, "",0 , 0, 0));
			rental.setEmployee(new Employee(idEmployee, "", "", "", "", "", 0, 0));
			rentalService.updateRental(idRental, rental);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist", ex);
		} catch (NoResultException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "RETURN DATE has to be later than RENTAL DATE", ex);
		}


	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/rental/{idRental}")
	public void deleteRental(@PathVariable Integer idRental) {
		
		rentalService.deleteRental(idRental);
	}

	@RequestMapping(method=RequestMethod.PUT, value="/rental/{idRental}/return")
	public double returnRental(@RequestBody Rentals rental, @PathVariable Integer idRental) {


		rental.setRentalDate(new Date(rentalsRepository.getRentalDateByIdRental(idRental).getTime()));
		rental.setClient(new Client(rentalsRepository.getIdClientByIdRental(idRental), "", "",
				null, "",0 , 0, 0));
		rental.setEmployee(new Employee(rentalsRepository.getIdEmployeeByIdRental(idRental), "", "",
				"", "", "", 0, 0));
		rentalService.updateRental(idRental, rental);
		rentalService.increaseAmount(idRental);

		return rentalService.getCharge(idRental);
	}

}
