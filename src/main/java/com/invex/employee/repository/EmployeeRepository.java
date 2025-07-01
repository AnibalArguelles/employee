package com.invex.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invex.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	/**
	 * Finds employees whose first or middle name contains the given string,
	 * ignoring case sensitivity.
	 *
	 * Equivalent to: SELECT * FROM EMPLOYEE WHERE LOWER(FIRST_NAME) LIKE
	 * LOWER('%name%') OR LOWER(MIDDLE_NAME) LIKE LOWER('%name%')
	 *
	 * @param name1 partial name to search in firstName
	 * @param name2 partial name to search in middleName
	 * @return list of matching employees
	 */
	List<Employee> findByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(String name1, String name2);
}
