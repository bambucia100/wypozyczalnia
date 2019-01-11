package szbd.projekt.projektbazy.actor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActorRepository  extends CrudRepository<Actor, Integer>{

    @Query(value = "select avg_actor_rating(:idActor)", nativeQuery = true)
    double getAvgActorRating(@Param("idActor") Integer idActor);

}

