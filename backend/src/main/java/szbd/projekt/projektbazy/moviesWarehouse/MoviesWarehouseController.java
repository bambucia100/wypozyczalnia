package szbd.projekt.projektbazy.moviesWarehouse;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import szbd.projekt.projektbazy.movie.Movie;
import szbd.projekt.projektbazy.rentalOffice.RentalOffice;

@RestController
public class MoviesWarehouseController {

	@Autowired
	private MoviesWarehouseService  moviesWarehouseService;
	@Autowired
	private MoviesWarehouseRepository moviesWarehouseRepository;

	@RequestMapping(method=RequestMethod.GET, value="/rentalOffice/warehouse/all")
	public List<MoviesWarehouse> getAllMoviesWarehouse() {

		return moviesWarehouseService.getAllMoviesWarehouse();
	}

	@RequestMapping(method=RequestMethod.GET, value="/rentalOffice/{idRentalOffice}/warehouse/all")
	public List<MoviesWarehouse> getAllMoviesWarehouseByIdOffice(@PathVariable Integer idRentalOffice) {

		return moviesWarehouseRepository.getAllMoviesWarehousesByRentalOffice(idRentalOffice);
	}

	@RequestMapping(method=RequestMethod.GET,value="/rentalOffice/warehouse/{idMoviesWarehouse}")
	public Optional<MoviesWarehouse> getMoviesWarehouse(@PathVariable Integer idMoviesWarehouse) {

		return moviesWarehouseService.getMoviesWarehouse(idMoviesWarehouse);
	}

	@RequestMapping(method=RequestMethod.POST,value="/rentalOffice/{idRentalOffice}/warehouse/{idMovie}")
	public void addMoviesWarehouse(@RequestBody MoviesWarehouse moviesWarehouse, @PathVariable Integer idRentalOffice,
			@PathVariable Integer idMovie) {

		moviesWarehouse.setRentalOffice(new RentalOffice(idRentalOffice, "", 0));
		moviesWarehouse.setMovie(new Movie(idMovie,  "", 0, "", 0, "", ""));
		moviesWarehouseService.addMoviesWarehouse(moviesWarehouse);;
	}
	@RequestMapping(method=RequestMethod.PUT,value="/rentalOffice/{idRentalOffice}/warehouse/{idMovie}/{idMoviesWarehouse}")
	public void updateMoviesWarehouse(@RequestBody MoviesWarehouse moviesWarehouse,@PathVariable Integer idRentalOffice,
						   @PathVariable Integer idMovie, @PathVariable Integer idMoviesWarehouse) {

		moviesWarehouse.setMovie(new Movie(idMovie,  "", 0, "", 0, "", ""));
		moviesWarehouse.setRentalOffice(new RentalOffice(idRentalOffice, "", 0));
		moviesWarehouseService.updateMoviesWarehouse(idMoviesWarehouse, moviesWarehouse);
	}
	@RequestMapping(method=RequestMethod.DELETE,value="/rentalOffice/warehouse/{idWarehouse}")
	public void deleteMoviesWarehouse(@PathVariable Integer idWarehouse) {

		moviesWarehouseService.deleteMoviesWarehouse(idWarehouse);
	}

}