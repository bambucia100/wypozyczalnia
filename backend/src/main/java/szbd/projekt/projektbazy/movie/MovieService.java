package szbd.projekt.projektbazy.movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	public List<Movie> getAllMovies() {
		List<Movie> movie = new ArrayList<>();
		movieRepository.findAll()
		.forEach(movie::add);
		return movie;
	}
	
	public Optional<Movie> getMovie(Integer idMovie) {
		return movieRepository.findById(idMovie);
	}
	
	public void addMovie(Movie movie) {
		movieRepository.save(movie);
	}

	public void updateMovie(Integer idMovie, Movie movie) {
		movieRepository.save(movie);
	}
	
	public void deleteMovie(Integer idMovie) {

		try {
			movieRepository.deleteById(idMovie);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist.", ex);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is a foreign key in CAST and/or MOVIES " +
					"table. Delete records in CAST and MOVIES first.", ex);
		}
	}
	
}
