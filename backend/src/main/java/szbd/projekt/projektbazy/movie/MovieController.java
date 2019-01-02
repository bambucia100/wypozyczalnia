package szbd.projekt.projektbazy.movie;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import szbd.projekt.projektbazy.genre.Genre;


@RestController
public class MovieController {

	@Autowired
	MovieService movieService;
	
	@RequestMapping(method=RequestMethod.GET,value="/movie/all")
	public List<Movie> getAllMovies()
	{
	  return movieService.getAllMovies();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/movie/{idMovie}")
	public Optional<Movie> getMovie(@PathVariable Integer idMovie) {
		return movieService.getMovie(idMovie);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/movie/{genreName}")
	public void addMovie(@RequestBody Movie movie, @PathVariable String genreName) {
		movie.setGenre(new Genre(genreName, ""));
		movieService.addMovie(movie);
	}
	@RequestMapping(method=RequestMethod.POST,value="/movie")
	public void addMovie(@RequestBody Movie movie) {

		movieService.addMovie(movie);
	}
	@RequestMapping(method=RequestMethod.PUT,value="/movie/{idMovie}/{genreName}")
	public void updateMovie(@RequestBody Movie movie,@PathVariable Integer idMovie,
			@PathVariable String genreName) {
		movie.setGenre(new Genre(genreName, ""));
		movieService.updateMovie(idMovie, movie);
	}
	@RequestMapping(method=RequestMethod.DELETE,value="/movie/{idMovie}")
	public void deleteMovie(@PathVariable Integer idMovie) {
		 movieService.deleteMovie(idMovie);
	}
	
	
	
}
