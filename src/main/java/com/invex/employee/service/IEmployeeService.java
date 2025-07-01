package com.invex.employee.service;

import java.util.List;

import com.invex.employee.dto.EmployeeDTO;

public interface IEmployeeService {

	/**
	 * Retrieves all registered employees.
	 * 
	 * @return List of EmployeeDTO objects
	 */
	List<EmployeeDTO> getAllEmployees();

	/**
	 * Deletes an employee by their identifier.
	 * 
	 * @param employeeId Employee identifier
	 */
	void deleteEmployee(Integer employeeId);

	/**
	 * Updates the information of an employee.
	 * 
	 * @param employeeDTO Object containing the updated data
	 * @param employeeId Employee identifier
	 * @return Updated object
	 */
	EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, Integer employeeId);

	/**
	 * Inserts one or more employees.
	 * 
	 * @param employees List of employees to insert
	 * @return List of inserted employees
	 */
	List<EmployeeDTO> insertEmployees(List<EmployeeDTO> employees);

	/**
	 * Retrieves an employee by their identifier.
	 * 
	 * @param id Employee identifier
	 * @return {@link EmployeeDTO} object representing the employee
	 */
	EmployeeDTO getEmployeeById(Integer id);

	/**
	 * Searches for employees whose name contains the given string (partial match).
	 *
	 * @param name Partial name string to search for
	 * @return List of {@link EmployeeDTO} objects that match the name
	 */
	List<EmployeeDTO> searchEmployeesByName(String name);
}
