package szbd.projekt.projektbazy.moviesWarehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MoviesWarehouseService {

	@Autowired
	private MoviesWarehouseRepository moviesWarehouseRepository;

	public List<MoviesWarehouse> getAllMoviesWarehouse() {
		List<MoviesWarehouse> moviesWarehouses = new ArrayList<>();
		moviesWarehouseRepository.findAll()
				.forEach(moviesWarehouses::add);
		return moviesWarehouses;
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

	public void deleteMoviesWarehouse(Integer idWarehouse) {
		moviesWarehouseRepository.deleteById(idWarehouse);
	}
}