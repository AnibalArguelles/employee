package com.invex.employee.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.invex.employee.dto.EmployeeDTO;
import com.invex.employee.exception.EmployeeNotFoundException;
import com.invex.employee.model.Employee;
import com.invex.employee.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setup() {
        employee = new Employee();
        employee.setEmployeeId(1);
        employee.setFirstName("Juan");
        employee.setMiddleName("Carlos");
        employee.setLastName("Pérez");
        employee.setAge(30);
        employee.setGender("M");
        employee.setPosition("Developer");
        employee.setActive(true);
        employee.setRegistrationDate(new Date());

        employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setFirstName("Juan");
        employeeDTO.setMiddleName("Carlos");
        employeeDTO.setLastName("Pérez");
        employeeDTO.setAge(30);
        employeeDTO.setGender("M");
        employeeDTO.setPosition("Developer");
        employeeDTO.setActive(true);
        employeeDTO.setRegistrationDate(employee.getRegistrationDate());
    }

    @Test
    void testGetAllEmployees() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        List<EmployeeDTO> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employee.getFirstName(), result.get(0).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById_found() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        EmployeeDTO result = employeeService.getEmployeeById(1);

        assertNotNull(result);
        assertEquals(employee.getFirstName(), result.getFirstName());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testGetEmployeeById_notFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        EmployeeNotFoundException ex = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(1));

        assertEquals("Empleado con ID 1 no encontrado", ex.getMessage());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testSearchEmployeesByName() {
        String name = "Juan";
        when(employeeRepository.findByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(name, name))
                .thenReturn(Collections.singletonList(employee));

        List<EmployeeDTO> result = employeeService.searchEmployeesByName(name);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employee.getFirstName(), result.get(0).getFirstName());
        verify(employeeRepository, times(1)).findByFirstNameContainingIgnoreCaseOrMiddleNameContainingIgnoreCase(name, name);
    }

    @Test
    void testInsertEmployees() {
        List<EmployeeDTO> dtos = Collections.singletonList(employeeDTO);
        when(employeeRepository.saveAll(anyList())).thenReturn(Collections.singletonList(employee));

        List<EmployeeDTO> result = employeeService.insertEmployees(dtos);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employee.getFirstName(), result.get(0).getFirstName());
        verify(employeeRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testUpdateEmployee_found() {
    	employeeDTO.setRegistrationDate(null);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO result = employeeService.updateEmployee(employeeDTO, 1);

        assertNotNull(result);
        assertEquals(employee.getFirstName(), result.getFirstName());
        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_notFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        EmployeeNotFoundException ex = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.updateEmployee(employeeDTO, 1));

        assertEquals("Empleado con ID 1 no encontrado", ex.getMessage());
        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void testDeleteEmployee_found() {
        when(employeeRepository.existsById(1)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1);

        assertDoesNotThrow(() -> employeeService.deleteEmployee(1));

        verify(employeeRepository, times(1)).existsById(1);
        verify(employeeRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteEmployee_notFound() {
        when(employeeRepository.existsById(1)).thenReturn(false);

        EmployeeNotFoundException ex = assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.deleteEmployee(1));

        assertEquals("Empleado con ID 1 no encontrado", ex.getMessage());
        verify(employeeRepository, times(1)).existsById(1);
        verify(employeeRepository, never()).deleteById(anyInt());
    }
}
