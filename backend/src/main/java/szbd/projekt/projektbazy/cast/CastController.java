package szbd.projekt.projektbazy.cast;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public List<Cast> getAllCast() {
		
		  return castService.getAllCast();
	}

	@RequestMapping(method=RequestMethod.GET,value = "/movie/cast/{idMovie}/{idActor}")
	public Optional<Cast> getCast(@PathVariable Integer idMovie, @PathVariable Integer idActor) {

		return castService.getCast(new CastId(idMovie, idActor));
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/movie/cast/{idMovie}/{idActor}")
	public void addCast(@RequestBody Cast cast, @PathVariable Integer idMovie, 
			@PathVariable Integer idActor) {
		
		cast.setMovie(new Movie(idMovie, "", 0, "", 0, "", ""));
		cast.setActor(new Actor(idActor, "", "", null));
		castService.addCast(cast);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/movie/cast/{idMovie}/{idActor}")
	public void updateCast(@RequestBody Cast cast,@PathVariable Integer idMovie,
			@PathVariable Integer idActor) {
		
		CastId idCast = new CastId(idMovie, idActor);
		cast.setId(idCast);
		cast.setMovie(new Movie(idMovie, "", 0, "", 0, "", ""));
		cast.setActor(new Actor(idActor, "", "", null));
		castService.updateCast(idCast, cast);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/movie/cast/{idMovie}/{idActor}")
	public void deleteCast(@PathVariable Integer idMovie, @PathVariable Integer idActor) {
		
		CastId idCast = new CastId(idMovie, idActor);
		castService.deleteCast(idCast);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/movie/{idMovie}/cast/full")
	public List<String> getAllActors(@PathVariable Integer idMovie) {
		
		List<Object[]> lst = castRepository.getAllActorsByMovie(idMovie);
		List<String> actorsList = new ArrayList<>();
		for (Object o[] : lst) {
			String fn = (String) o[0];
			String ln = (String) o[1];
			actorsList.add(fn + " " + ln);
			//System.out.println(actorsList);
		}
		return actorsList;
	}
	
	
}
