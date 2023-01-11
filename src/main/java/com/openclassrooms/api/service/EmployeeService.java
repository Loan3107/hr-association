package com.openclassrooms.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.api.exceptions.ResourceNotFoundException;
import com.openclassrooms.api.model.Employee;
import com.openclassrooms.api.repository.EmployeeRepository;

import lombok.Data;

@Data
@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Iterable<Employee> getAll() {
		return employeeRepository.findAll();
	}
	
	public Employee getById(final Long id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) return employee.get();
		else throw new ResourceNotFoundException("The requested employee does not exist");
	}
	
	public void deleteById(final Long id) {
		employeeRepository.deleteById(id);
	}
	
	public Employee saveEmployee(Employee employee) {
		Employee savedEmployee = employeeRepository.save(employee);
		return savedEmployee;
	}
}
