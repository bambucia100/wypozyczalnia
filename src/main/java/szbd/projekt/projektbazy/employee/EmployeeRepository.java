package szbd.projekt.projektbazy.employee;

import java.util.List;

import javax.persistence.*;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
	public List<Employee> findByIdEmployee(Integer idEmployee);
	
}
