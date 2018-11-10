package szbd.projekt.projektbazy.moviesWarehouse;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@RestController
public class MoviesWarehouseController {
	
	@Autowired
	private MoviesWarehouseService  moviesWarehouseService;
	
	@RequestMapping(method=RequestMethod.GET,value="/rentalOffice/{idRentalOffice}/warehouse")
	public Optional<MoviesWarehouse> getMoviesWarehouse(@PathVariable Integer idRentalOffice) {
		return moviesWarehouseService.getMoviesWarehouse(idRentalOffice);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/rentalOffice/{idRentalOffice}/warehouse")
	public void addMoviesWarehouse(@RequestBody MoviesWarehouse moviesWarehouse, @PathVariable Integer idRentalOffice) {
		moviesWarehouse.setRentalOffice(new RentalOffice(idRentalOffice, ""));
		moviesWarehouseService.addMoviesWarehouse(moviesWarehouse);;
	}
	@RequestMapping(method=RequestMethod.PUT,value="/rentalOffice/{idRentalOffice}/warehouse")
	public void updateBeer(@RequestBody MoviesWarehouse moviesWarehouse,@PathVariable Integer idRentalOffice)
	{
		moviesWarehouse.setRentalOffice(new RentalOffice(idRentalOffice,""));
		moviesWarehouseService.updateMoviesWarehouse(idRentalOffice, moviesWarehouse);
	}
	@RequestMapping(method=RequestMethod.DELETE,value="/rentalOffice/{idRentalOffice}/warehouse")
	public void deleteBeer(@PathVariable Integer idRentalOffice)
	{
		 moviesWarehouseService.deleteMoviesWarehouse(idRentalOffice);
	}
	
}
