package com.invex.employee.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invex.employee.dto.EmployeeDTO;
import com.invex.employee.endpoint.EmployeeEndpoint;
import com.invex.employee.service.IEmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeEndpointTest {

	@Mock
	private IEmployeeService employeeService;

	@InjectMocks
	private EmployeeEndpoint employeeEndpoint;

	private EmployeeDTO mockEmployee;

	// Método para inicializar el empleado mock
	@BeforeEach
	public void setUp() {
		mockEmployee = new EmployeeDTO();
		mockEmployee.setEmployeeId(1);
		mockEmployee.setFirstName("Juan");
		mockEmployee.setLastName("Pérez");
		mockEmployee.setAge(30);
		mockEmployee.setGender("M");
		mockEmployee.setPosition("Developer");

	}

	@Test
	public void testGetAllEmployees() {
		when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(mockEmployee));

		List<EmployeeDTO> result = employeeEndpoint.getAllEmployees();

		assertEquals(1, result.size());
		assertEquals(mockEmployee.getEmployeeId(), result.get(0).getEmployeeId());
		verify(employeeService, times(1)).getAllEmployees();
	}

	@Test
	public void getEmployeeById() {
		when(employeeService.getEmployeeById(anyInt())).thenReturn(mockEmployee);

		EmployeeDTO result = employeeEndpoint.getEmployeeById(1);

		assertEquals(mockEmployee.getEmployeeId(), result.getEmployeeId());
		verify(employeeService, times(1)).getEmployeeById(1);
	}
	
	@Test
	public void search() {
		when(employeeService.searchEmployeesByName(anyString())).thenReturn(Collections.singletonList(mockEmployee));
		
		List<EmployeeDTO> result = employeeEndpoint.search("Anibal");
		
		assertEquals(mockEmployee.getEmployeeId(), result.get(0).getEmployeeId());
		verify(employeeService, times(1)).searchEmployeesByName("Anibal");
	}

	@Test
	public void testInsertEmployees() {
		List<EmployeeDTO> inputList = Arrays.asList(mockEmployee);
		when(employeeService.insertEmployees(inputList)).thenReturn(inputList);

		List<EmployeeDTO> result = employeeEndpoint.insertEmployees(inputList);

		assertEquals(1, result.size());
		assertEquals(mockEmployee.getFirstName(), result.get(0).getFirstName());
		verify(employeeService, times(1)).insertEmployees(inputList);
	}

	@Test
	public void testUpdateEmployee() {
		// Cambiado para reflejar la nueva firma del método con @PathVariable
		when(employeeService.updateEmployee(any(EmployeeDTO.class), any(Integer.class))).thenReturn(mockEmployee);

		EmployeeDTO result = employeeEndpoint.updateEmployee(mockEmployee, mockEmployee.getEmployeeId());

		assertEquals(mockEmployee.getEmployeeId(), result.getEmployeeId());
		verify(employeeService, times(1)).updateEmployee(any(EmployeeDTO.class), any(Integer.class));
	}

	@Test
	public void testDeleteEmployee() {
		Integer employeeId = 1;

		// Cambiado a la nueva firma del método con @PathVariable
		employeeEndpoint.deleteEmployee(employeeId);

		verify(employeeService, times(1)).deleteEmployee(employeeId);
	}
}
