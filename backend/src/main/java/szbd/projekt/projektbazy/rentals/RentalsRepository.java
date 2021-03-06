package szbd.projekt.projektbazy.rentals;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;

public interface RentalsRepository extends CrudRepository<Rentals, Integer>{

    @Query(value = "SELECT * FROM rentals where return_date is null", nativeQuery = true)
    List<Rentals> getAllRentalsByNullReturnDate();

    @Query(value = "SELECT r.* FROM rentals r, employee e, rental_office ro " +
            "where r.id_employee = e.id_employee and e.id_rental_office = ro.id_rental_office " +
            "and r.return_date is null and ro.id_rental_office = :idRentalOffice", nativeQuery = true)
    List<Rentals> getAllRentalsNullReturnedByRentalOffice(@Param("idRentalOffice") Integer idRentalOffice);

    @Query(value = "SELECT m.charge, re.amount_of_rentals, r.rental_date, r.return_date " +
            "FROM movies_warehouse m, rental_element re, rentals r " +
            "where m.id_warehouse = re.id_warehouse and r.id_rental = re.id_rental and r.id_rental = :idRental"
            , nativeQuery = true)
    List<Object[]> getCharge(@Param("idRental") Integer idRental);

    @Query(value = "SELECT re.id_warehouse, re.amount_of_rentals from rental_element re, rentals r\n" +
            "where r.id_rental = re.id_rental and r.id_rental = :idRental", nativeQuery = true)
    List<Object[]> getWarehouseAndAmount(@Param("idRental") Integer idRental);

    @Query(value = "SELECT rental_date FROM rentals where id_rental = :idRental", nativeQuery = true)
    Timestamp getRentalDateByIdRental(@Param("idRental") Integer idRental);

    @Query(value = "SELECT id_client FROM rentals where id_rental = :idRental", nativeQuery = true)
    Integer getIdClientByIdRental(@Param("idRental") Integer idRental);

    @Query(value = "SELECT id_employee FROM rentals where id_rental = :idRental", nativeQuery = true)
    Integer getIdEmployeeByIdRental(@Param("idRental") Integer idRental);

    @Query(value = "SELECT m.title FROM movies_warehouse mw, movie m, rental_element re, rentals r " +
            "where m.id_movie = mw.id_movie and mw.id_warehouse = re.id_warehouse " +
            "and re.id_rental = r.id_rental and r.id_rental = :idRental", nativeQuery = true)
    List<String> getTitleMoviesByIdRental(@Param("idRental") Integer idRental);
}
