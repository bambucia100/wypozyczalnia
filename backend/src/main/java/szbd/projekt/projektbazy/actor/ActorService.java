package szbd.projekt.projektbazy.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.sql.DataSource;

@Service
public class ActorService {

	@Autowired
	private ActorRepository actorRepository;
	@PersistenceContext
	private EntityManager entityManager ;
	
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

	public double avgRating(Integer idActor) {

		return actorRepository.getAvgActorRating(idActor);
	}

}
