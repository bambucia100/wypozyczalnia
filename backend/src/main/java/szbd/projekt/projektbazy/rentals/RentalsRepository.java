package szbd.projekt.projektbazy.rentals;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface RentalsRepository extends CrudRepository<Rentals, Integer>{




}
