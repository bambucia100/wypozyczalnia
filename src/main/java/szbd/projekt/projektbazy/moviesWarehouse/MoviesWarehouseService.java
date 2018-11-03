package szbd.projekt.projektbazy.moviesWarehouse;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import szbd.projekt.projektbazy.employee.Employee;

@Service
public class MoviesWarehouseService {

	@Autowired
	private MoviesWarehouseRepository moviesWarehouseRepository;
	
	public List<MoviesWarehouse> getAllMoviesWarehouse(Iterable<Integer> idRentalOffice) {
		return (List<MoviesWarehouse>) moviesWarehouseRepository.findAllById(idRentalOffice);
	}
	public Optional<MoviesWarehouse> getMoviesWarehouse(Integer idMovieWarehouse) {
		return moviesWarehouseRepository.findById(idMovieWarehouse);
	}
	
	public void addMoviesWarehouse(MoviesWarehouse moviesWarehouse) {
		moviesWarehouseRepository.save(moviesWarehouse);
	}

	public void updateMoviesWarehouse(Integer idMoviesWarehouse, MoviesWarehouse moviesWarehouse) {
		moviesWarehouseRepository.save(moviesWarehouse);
	}
	
	public void deleteMoviesWarehouse(Integer idMoviesWarehouse) {
		moviesWarehouseRepository.deleteById(idMoviesWarehouse);
	}
}
