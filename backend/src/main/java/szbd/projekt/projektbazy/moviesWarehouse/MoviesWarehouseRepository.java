package szbd.projekt.projektbazy.moviesWarehouse;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import szbd.projekt.projektbazy.client.Client;

import java.util.List;

public interface MoviesWarehouseRepository extends CrudRepository<MoviesWarehouse, Integer>{

    @Query(value = "SELECT * from movies_warehouse m " +
            "where " +
            "m.id_rental_office = :idRentalOffice" , nativeQuery = true)
    List<MoviesWarehouse> getAllMoviesWarehousesByRentalOffice(@Param("idRentalOffice") Integer idRentalOffice);
}
