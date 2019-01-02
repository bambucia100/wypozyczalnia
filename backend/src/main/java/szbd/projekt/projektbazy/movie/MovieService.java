package szbd.projekt.projektbazy.movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



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
		movieRepository.deleteById(idMovie);
	}
	
}
