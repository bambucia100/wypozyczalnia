package szbd.projekt.projektbazy.genre;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class GenreController {

	@Autowired
	private GenreService genreService;
	
	@RequestMapping(method=RequestMethod.GET,value="/genre/all")
	public List<Genre> getAllGenres() {
		return genreService.getAllGenres();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/genre/{genreName}")
	public Optional<Genre> getGenre(@PathVariable String genreName) {
		return genreService.getGenre(genreName);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/genre")
	public void addGenre(@RequestBody Genre genre)
	{
		 genreService.addGenre(genre);
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE,value="/genre/{genreName}")
	public void deleteGenre(@PathVariable String genreName) {

		try {
			genreService.deleteGenre(genreName);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is foreign key", ex);
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/genre/{genreName}")
	public void updateBeer(@RequestBody Genre genre,@PathVariable String genreName)
	{
		 genreService.updateGenre(genreName, genre);
	}

	
}
