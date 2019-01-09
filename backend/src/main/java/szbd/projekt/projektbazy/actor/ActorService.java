package szbd.projekt.projektbazy.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;
	
	public List<Actor> getAllActors() {
		List<Actor> actor = new ArrayList<>();
		actorRepository.findAll()
		.forEach(actor::add);
		return actor;
	}
	
	
	public Optional<Actor> getActor(Integer idActor) {
		return actorRepository.findById(idActor);
	}
	
	public void addActor(Actor actor) {
		actorRepository.save(actor);
	}

	public void updateActor(Integer idActor, Actor actor) {
		actorRepository.save(actor);
	}

	public void deleteActor(Integer idActor) {
		try {
			actorRepository.deleteById(idActor);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist", ex);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is a foreign key in CASTS table. " +
					"Delete records in CASTS first", ex);
		}
	}
	
}
