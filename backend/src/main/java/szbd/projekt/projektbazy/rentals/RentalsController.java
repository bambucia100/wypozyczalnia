package szbd.projekt.projektbazy.rentals;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import szbd.projekt.projektbazy.adress.Adress;
import szbd.projekt.projektbazy.client.Client;
import szbd.projekt.projektbazy.employee.Employee;
import szbd.projekt.projektbazy.movie.Movie;
import szbd.projekt.projektbazy.moviesWarehouse.MoviesWarehouse;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@RestController
public class RentalsController {

	@Autowired
	RentalsService rentalService;

	@RequestMapping(method=RequestMethod.GET,value="/rentals/all")
	public List<Rentals> getAllRentals() {
		
		return rentalService.getAllRentals();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/rentals/{idRental}")
	public Optional<Rentals> getRental(@PathVariable Integer idRental) {
		
		return rentalService.getRental(idRental);
	}


	//TODO
	//sprawdzic zeby bylo > 0 //chyba ze na froncie
	@RequestMapping(method=RequestMethod.POST,value="/rentalOffice/{idRentalOffice}/"
			+ "client/{idClient}/rental/{idMovie}/{idEmployee}")
	public void addRentals(@RequestBody Rentals rental, @PathVariable Integer idClient, 
			@PathVariable Integer idMovie, @PathVariable Integer idRentalOffice, @PathVariable Integer idEmployee) {

		rentalService.changeQuantity(idMovie, idRentalOffice);
		rental.setMovie(new Movie(idMovie, "", 0, "", 0, "", ""));
		rental.setClient(new Client(idClient, "", "", null, "", 0 , 0, 0));
		rental.setEmployee(new Employee(idEmployee, "", "", "", "", "", 0, 0));
		rentalService.addRental(rental);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/rentalOffice/client/{idClient}/rental"
			+ "/{idRental}/{idMovie}/{idEmployee}")
	public void updateRentals(@RequestBody Rentals rental, @PathVariable Integer idClient, 
			@PathVariable Integer idMovie, @PathVariable Integer idRental, @PathVariable Integer idEmployee) {
		
		rental.setMovie(new Movie(idMovie, "", 0, "", 0, "", ""));
		rental.setClient(new Client(idClient, "", "", null, "",0 , 0, 0));
		rental.setEmployee(new Employee(idEmployee, "", "", "", "", "", 0, 0));
		rentalService.updateRental(idRental, rental);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/rental/{idRental}")
	public void deleteRental(@PathVariable Integer idRental) {
		
		rentalService.deleteRental(idRental);
	}
	
}
