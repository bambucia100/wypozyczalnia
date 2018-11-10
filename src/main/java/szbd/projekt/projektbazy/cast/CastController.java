package szbd.projekt.projektbazy.cast;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import szbd.projekt.projektbazy.actor.Actor;
import szbd.projekt.projektbazy.movie.Movie;

@RestController
public class CastController {

	@Autowired
	CastService castService;
	@Autowired
	CastRepository castRepository;
	
	@RequestMapping(method=RequestMethod.GET,value="/movie/cast")
	public List<Cast> getAllCast()
	{
	  return castService.getAllCast();
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/movie/cast/{idMovie}/{idActor}")
	public void addEmployee(@RequestBody Cast cast, @PathVariable Integer idMovie, 
			@PathVariable Integer idActor) {
		cast.setMovie(new Movie(idMovie, "", 0, "", 0, "", 0));
		cast.setActor(new Actor(idActor, "", "", null, 0));
		castService.addCast(cast);
	}
	@RequestMapping(method=RequestMethod.PUT,value="/movie/cast/{idCast}/{idMovie}/{idActor}")
	public void updateEmployee(@RequestBody Cast cast,@PathVariable Integer idMovie,
			@PathVariable Integer idActor, @PathVariable Integer idCast)
	{
		cast.setMovie(new Movie(idMovie, "", 0, "", 0, "", 0));
		cast.setActor(new Actor(idActor, "", "", null, 0));
		castService.updateCast(idCast, cast);
	}
	@RequestMapping(method=RequestMethod.DELETE,value="/movie/cast/{idMovie}/{idCast}")
	public void deleteEmployee(@PathVariable Integer idCast)
	{
		 castService.deleteCast(idCast);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/movie/cast/full")
	public List<String> getAllActors() {
		List<Object[]> lst = castRepository.getAllActorsByMovie();
		List<String> actorsList = new ArrayList<String>();
		for (Object o[] : lst) {
			Integer im = (Integer) o[0];
			String fn = (String) o[1];
			String ln = (String) o[2];
			
			actorsList.add(im.toString());
			actorsList.add(fn);
			actorsList.add(ln);
			System.out.printf("\nIMovie: %s \n Actor First name: %s \n Actor last name: %s \n", im, fn, ln);
		}
		return actorsList;
	}
	
	
}
