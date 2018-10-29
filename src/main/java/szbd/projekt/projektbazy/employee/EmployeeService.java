package szbd.projekt.projektbazy.employee;

import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Employee> getAllEmployees(Iterable<Integer> idEmployee) {
		return (List<Employee>) employeeRepository.findAllById(idEmployee);
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
		employeeRepository.deleteById(idEmployee);
	}
}
