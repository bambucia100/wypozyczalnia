package szbd.projekt.projektbazy.rentals;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface RentalsRepository extends CrudRepository<Rentals, Integer>{

    @Query(value = "SELECT * FROM rentals where return_date is null", nativeQuery = true)
    List<Rentals> getAllRentalsByNullReturnDate();

    @Query(value = "SELECT m.charge, re.amount_of_rentals, r.rental_date, r.return_date " +
            "FROM movies_warehouse m, rental_element re, rentals r " +
            "where m.id_warehouse = re.id_warehouse and r.id_rental = re.id_rental and r.id_rental = :idRental"
            , nativeQuery = true)
    List<Object[]> getCharge(@Param("idRental") Integer idRental);


}
