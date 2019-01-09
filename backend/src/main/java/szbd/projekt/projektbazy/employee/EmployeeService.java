package szbd.projekt.projektbazy.employee;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		employeeRepository.findAll()
		.forEach(employees::add);
		return employees;
	}
	
	public Optional<Employee> getEmployee(Integer idEmployee) {
		return employeeRepository.findById(idEmployee);
	}
	
	public void addEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	public void updateEmployee(Integer idEmployee, Employee employee) {
		employeeRepository.save(employee);
	}
	
	public void deleteEmployee(Integer idEmployee) {

		try {
			employeeRepository.deleteById(idEmployee);
		} catch (EmptyResultDataAccessException ex) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element does not exist.", ex);
		} catch (Exception ex) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Element is a foreign key in RENTAL and " +
					"table. Delete records in RENTAL first.", ex);
		}
	}
}
