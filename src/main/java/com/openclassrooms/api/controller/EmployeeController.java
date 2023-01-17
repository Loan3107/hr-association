package com.openclassrooms.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.api.model.Employee;
import com.openclassrooms.api.service.EmployeeService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * GET - All employees
	 * @return - An Iterable object of Employee
	 */
	@GetMapping("/employees")
	public Iterable<Employee> getAll() {
		return employeeService.getAll();
	}
	
	/**
	 * GET - A specified employee
	 * @param id - Id of the employee
	 * @return - An Employee object
	 */
	@GetMapping("/employees/{id}")
	public Employee getById(@PathVariable("id") final Long id) {
		return employeeService.getById(id);
	}
	
	/**
	 * POST - Employee
	 * @param employee - Employee to create
	 * @return - The created Employee
	 */
	@PostMapping("/employees")
	@ResponseStatus(value = HttpStatus.CREATED, code = HttpStatus.CREATED)
	public Employee create(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}
	
	/**
	 * PUT - Employee
	 * @param id - Id of the employee
	 * @param employee - Employee with modified informations
	 * @return - The updated Employee
	 */
	@PutMapping("/employees/{id}")
	@ResponseStatus(value = HttpStatus.OK, code = HttpStatus.OK)
	public Employee put(@PathVariable("id") final Long id, @RequestBody Employee employee) {
		return employeeService.updateEmployee(id, employee);
	}
	
	/**
	 * DELETE - Employee
	 * @param id - Id of the employee
	 */
	@DeleteMapping("/employees/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT, code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") final Long id) {
		employeeService.deleteById(id);
	}
}
