package szbd.projekt.projektbazy.client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Integer>{

    @Query(value = "SELECT * from Client c " +
            "where " +
            "c.id_rental_office = :idRentalOffice" , nativeQuery = true)
    List<Client> getAllClientsByWarehouse(@Param("idRentalOffice") Integer idRentalOffice);
}
