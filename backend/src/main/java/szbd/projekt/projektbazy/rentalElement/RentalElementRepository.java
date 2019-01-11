package szbd.projekt.projektbazy.rentalElement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface RentalElementRepository extends CrudRepository<RentalElement, RentalElementId> {

    @Query(value = "SELECT e.* FROM rental_element e, rentals r, client c " +
            "where " +
            "e.id_rental = r.id_rental and r.id_client = c.id_client and c.id_client = :idClient", nativeQuery = true)
    List<RentalElement> getAllRentalElementsByClient(@Param("idClient") Integer idClient);
    @Query(value = "select return_date from rentals " +
            "where id_rental = :idRental", nativeQuery = true)
    Timestamp getReturnDateByIdRental(@Param("idRental") Integer idRental);
    @Query(value = "select quantity from movies_warehouse " +
            "where id_warehouse = :idWarehouse", nativeQuery = true)
    Integer getQuantityByIdWarehouse(@Param ("idWarehouse") Integer idWarehouse);
    @Query(value = "select amount_of_rentals from rental_element " +
            "where id_warehouse = :idWarehouse and id_rental = :idRental", nativeQuery = true)
    Integer getAmountOfRentals(@Param ("idWarehouse") Integer idWarehouse, @Param ("idRental") Integer idRental);

}
