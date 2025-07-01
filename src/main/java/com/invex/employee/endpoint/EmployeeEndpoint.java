package com.invex.employee.endpoint;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invex.employee.dto.EmployeeDTO;
import com.invex.employee.service.IEmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/employees")
@Tag(
	    name = "Employee Management",
	    description = "Operations to insert, update, delete, and retrieve employees."
	)
public class EmployeeEndpoint {

	/** Service field */
	@Autowired
	IEmployeeService employeeService;

	/**
	 * Retrieves the list of all employees registered in the system.
	 *
	 * @return List of {@link EmployeeDTO} objects representing the employees.
	 */
	@Operation(summary = "List all employees", description = "Retrieves all employees registered in the database.")
	@ApiResponse(responseCode = "200", description = "List successfully retrieved")
	@GetMapping
	public List<EmployeeDTO> getAllEmployees() {

		log.info("Operation 'List all employees' - Retrieving all registered employees");
		return employeeService.getAllEmployees();
	}

	/**
	 * Retrieves information of a specific employee.
	 *
	 * @return An {@link EmployeeDTO} object representing the employee.
	 */
	@Operation(summary = "Get an employee", description = "Retrieves the information of a registered employee from the database.")
	@ApiResponse(responseCode = "200", description = "Employee successfully retrieved")
	@GetMapping("/{id}")
	public EmployeeDTO getEmployeeById(
			@Parameter(description = "ID of the employee to retrieve", required = true)
			@PathVariable("id") Integer id) {

		log.info("Operation 'Get an employee' - Retrieving employee with ID: {}",  id );
		return employeeService.getEmployeeById(id);
	}

	/**
	 * Searches employees by name.
	 *
	 * @param name Name or part of the name of the employee to search for.
	 * @return List of {@link EmployeeDTO} objects matching the search criteria.
	 */
	@Operation(summary = "Search employees by name", description = "Searches for employees whose name contains a given string.")
	@ApiResponse(responseCode = "200", description = "List of employees found successfully")
	@GetMapping("/search")
	public List<EmployeeDTO> search(
			@Parameter(description = "Name or part of the name of the employee to search for", required = true)
			@RequestParam("name") String name) {
	    
	    log.info("Operation 'Search employees' - Searching employees with name containing: {}", name);
	    return employeeService.searchEmployeesByName(name);
	}

	/**
	 * Registers one or more employees in a single request.
	 *
	 * @param employees List of employees to insert.
	 * @return List of registered employees with their respective IDs.
	 */
	@Operation(summary = "Insert employees", description = "Registers one or more employees in the database in a single request.")
	@ApiResponse(responseCode = "201", description = "Employees successfully inserted")
	@PostMapping
	public List<EmployeeDTO> insertEmployees(
			@Parameter(description = "List of employees to insert", required = true)
			@RequestBody @Valid List<EmployeeDTO> employees) {
		
		log.info("Operation 'Insert employees' - Inserting employees: {} records");
		return employeeService.insertEmployees(employees);
	}
	
	/**
	 * Updates the information of an existing employee.
	 *
	 * @param employeeDTO Employee data to update.
	 * @param id Identifier of the employee to update.
	 * @return Updated {@link EmployeeDTO} object.
	 */
	@Operation(summary = "Update an employee", description = "Updates the information of an existing employee by ID.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Employee successfully updated"),
		@ApiResponse(responseCode = "404", description = "Employee not found")
	})
	@PutMapping("/{id}")
	public EmployeeDTO updateEmployee(
			@Parameter(description = "Updated employee data", required = true)
			@RequestBody @Valid EmployeeDTO employeeDTO,
			@Parameter(description = "ID of the employee to update", required = true)
			@PathVariable("id") Integer id) {
		
		log.info("Operation 'Update an employee' - Updating employee with ID: {} and data: {}", id, employeeDTO);
		return employeeService.updateEmployee(employeeDTO, id);
	}
	
	/**
	 * Deletes an employee by ID.
	 *
	 * @param id Unique identifier of the employee to delete.
	 */
	@Operation(summary = "Delete an employee", description = "Deletes a registered employee by their identifier.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Employee successfully deleted"),
		@ApiResponse(responseCode = "404", description = "Employee not found")
	})
	@DeleteMapping("/{id}")
	public void deleteEmployee(
			@Parameter(description = "ID of the employee to delete", required = true)
			@PathVariable("id") Integer id) {
		
		log.info("Operation 'Delete an employee' - Deleting employee with ID: {}", id);
		employeeService.deleteEmployee(id);
	}
}
