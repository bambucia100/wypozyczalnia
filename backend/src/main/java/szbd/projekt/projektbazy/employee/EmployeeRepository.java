package szbd.projekt.projektbazy.employee;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import szbd.projekt.projektbazy.client.Client;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{

    @Query(value = "SELECT * from Employee e " +
            "where " +
            "e.id_rental_office = :idRentalOffice" , nativeQuery = true)
    List<Employee> getAllEmployeesByOffice(@Param("idRentalOffice") Integer idRentalOffice);

}
