package szbd.projekt.projektbazy.cast;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CastRepository extends CrudRepository<Cast, Integer> {

	@Query(value = "Select c.id_movie, a.actor_first_name, a.actor_last_name from Cast c, Actor a "
			+ "where "
			+ "c.id_actor = a.id_actor" , nativeQuery = true)
	List<Object[]> getAllActorsByMovie();
}	