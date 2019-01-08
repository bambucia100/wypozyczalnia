package szbd.projekt.projektbazy.rentalElement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalElementRepository extends CrudRepository<RentalElement, RentalElementId> {

    @Query(value = "SELECT e.* FROM rental_element e, rentals r, client c " +
            "where " +
            "e.id_rental = r.id_rental and r.id_client = c.id_client and c.id_client = 1", nativeQuery = true)
    List<RentalElement> getAllRentalElementsByClient(@Param("id_client") Integer idClient);
}
