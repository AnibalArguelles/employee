package com.invex.employee.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invex.employee.dto.EmployeeDTO;
import com.invex.employee.exception.EmployeeNotFoundException;
import com.invex.employee.model.Employee;
import com.invex.employee.repository.EmployeeRepository;
import com.invex.employee.service.IEmployeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the employee service. Contains the logic to retrieve,
 * insert, update, and delete employees from the database.
 */
@Service
@Slf4j
public class EmployeeService implements IEmployeeService {

	/** Repository for employee data access */
	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * Retrieves all employees registered in the database.
	 * 
	 * @return List of {@link EmployeeDTO} objects representing all employees.
	 */
	@Override
	public List<EmployeeDTO> getAllEmployees() {
		log.info("Retrieving all employees from the database");
		List<Employee> employees = employeeRepository.findAll();
		return employees.stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	/**
	 * Retrieves an employee by their ID.
	 * 
	 * @param id The employee ID.
	 * @return {@link EmployeeDTO} object representing the employee.
	 */
	@Override
	public EmployeeDTO getEmployeeById(Integer id) {
		log.info("Retrieving employee with ID from the database");
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));
		return mapToDTO(employee);
	}
	
	/**
	 * Searches for employees whose name contains the given string (partial match).
	 *
	 * @param name Partial name string to search for.
	 * @return List of {@link EmployeeDTO} objects that match the name.
	 */
	@Override
	public List<EmployeeDTO> searchEmployeesByName(String name) {
		log.info("Searching for employees whose name contains: {}", name);
		List<Employee> employees = employeeRepository
				.findByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(name, name);
		return employees.stream()
				.map(this::mapToDTO)
				.collect(Collectors.toList());
	}

	/**
	 * Inserts a list of employees into the database.
	 * 
	 * @param employees List of {@link EmployeeDTO} objects to insert.
	 * @return List of saved {@link EmployeeDTO} objects.
	 */
	@Override
	public List<EmployeeDTO> insertEmployees(List<EmployeeDTO> employees) {
		log.info("Inserting {} employees", employees.size());
		List<Employee> employeeEntities = employees.stream()
				.map(dto -> mapToEntity(dto, new Employee()))
				.collect(Collectors.toList());
		List<Employee> saved = employeeRepository.saveAll(employeeEntities);
		return saved.stream().map(this::mapToDTO).collect(Collectors.toList());
	}
	
	/**
	 * Updates an employee's data by their ID.
	 * 
	 * @param employeeDTO Object with the employee's updated information.
	 * @param employeeId  The ID of the employee to update.
	 * @return Updated {@link EmployeeDTO} object.
	 * @throws EmployeeNotFoundException if the employee to update does not exist.
	 */
	@Override
	public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, Integer employeeId) {
		log.info("Updating employee with ID: {}", employeeId);
		Optional<Employee> optional = employeeRepository.findById(employeeId);

		if (optional.isPresent()) {
			employeeDTO.setEmployeeId(employeeId);
			Employee employee = optional.get();
			mapToEntity(employeeDTO, employee);
			employee = employeeRepository.save(employee);
			return mapToDTO(employee);
		} else {
			log.warn("Employee with ID {} not found for update", employeeId);
			throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found");
		}
	}
	
	/**
	 * Deletes an employee by their ID.
	 * 
	 * @param employeeId The ID of the employee to delete.
	 * @throws EmployeeNotFoundException if no employee with the specified ID is found.
	 */
	@Override
	public void deleteEmployee(Integer employeeId) {
		log.info("Deleting employee with ID: {}", employeeId);
		if (!employeeRepository.existsById(employeeId)) {
			log.warn("Employee with ID {} not found for deletion", employeeId);
			throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found");
		}
		employeeRepository.deleteById(employeeId);
	}

	/**
	 * Converts an {@link Employee} entity to an {@link EmployeeDTO} object.
	 * 
	 * @param employee Entity to convert.
	 * @return Equivalent {@link EmployeeDTO} object.
	 */
	private EmployeeDTO mapToDTO(Employee employee) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeId(employee.getEmployeeId());
		dto.setFirstName(employee.getFirstName());
		dto.setMiddleName(employee.getMiddleName());
		dto.setLastName(employee.getLastName());
		dto.setSecondLastName(employee.getSecondLastName());
		dto.setAge(employee.getAge());
		dto.setGender(employee.getGender());
		dto.setDateOfBirth(employee.getDateOfBirth());
		dto.setPosition(employee.getPosition());
		dto.setRegistrationDate(employee.getRegistrationDate());
		dto.setActive(employee.getActive());
		return dto;
	}

	/**
	 * Converts an {@link EmployeeDTO} object to an {@link Employee} entity.
	 * 
	 * @param dto      Object containing employee data.
	 * @param employee Entity to update.
	 * @return Updated entity.
	 */
	private Employee mapToEntity(EmployeeDTO dto, Employee employee) {
		employee.setEmployeeId(dto.getEmployeeId());
		employee.setFirstName(dto.getFirstName());
		employee.setMiddleName(dto.getMiddleName());
		employee.setLastName(dto.getLastName());
		employee.setSecondLastName(dto.getSecondLastName());
		employee.setAge(dto.getAge());
		employee.setGender(dto.getGender());
		employee.setDateOfBirth(dto.getDateOfBirth());
		employee.setPosition(dto.getPosition());
		employee.setRegistrationDate(dto.getRegistrationDate() != null ? dto.getRegistrationDate() : employee.getRegistrationDate());
		employee.setActive(dto.getActive());
		return employee;
	}
}
