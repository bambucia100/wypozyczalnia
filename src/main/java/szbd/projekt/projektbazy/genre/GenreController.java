package szbd.projekt.projektbazy.genre;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenreController {

	@Autowired
	private GenreService genreService;
	
	@RequestMapping(method=RequestMethod.GET,value="/genre/all")
	public List<Genre> getAllGenres() {
		return genreService.getAllGenres();
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/genre/{idGenre}")
	public Optional<Genre> getGenre(@PathVariable Integer idGenre) {
		return genreService.getGenre(idGenre);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/genre")
	public void addGenre(@RequestBody Genre genre)
	{
		 genreService.addGenre(genre);
	}
	
	
	@RequestMapping(method=RequestMethod.DELETE,value="/genre/{idGenre}")
	public void deleteGenre(@PathVariable Integer idGenre)
	{
		
		genreService.deleteGenre(idGenre);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/genre/{idGenre}")
	public void updateBeer(@RequestBody Genre genre,@PathVariable Integer idGenre)
	{
		 genreService.updateGenre(idGenre, genre);
	}
	
}
