package szbd.projekt.projektbazy.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		actorRepository.deleteById(idActor);
	}
	
}
