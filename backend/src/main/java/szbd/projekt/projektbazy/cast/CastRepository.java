package szbd.projekt.projektbazy.cast;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CastRepository extends CrudRepository<Cast, CastId> {

	@Query(value = "Select a.actor_first_name, a.actor_last_name from Cast c, Actor a "
			+ "where "
			+ "c.id_actor = a.id_actor " +
			"and " +
			"c.id_movie = :idMovie" , nativeQuery = true)
	List<Object[]> getAllActorsByMovie(@Param("idMovie") Integer idMovie);

}	