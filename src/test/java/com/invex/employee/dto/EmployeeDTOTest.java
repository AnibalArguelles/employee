package com.invex.employee.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class EmployeeDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeId(101);
        dto.setFirstName("Carlos");
        dto.setMiddleName("Andrés");
        dto.setLastName("Ramírez");
        dto.setSecondLastName("López");
        dto.setAge(30);
        dto.setGender("Masculino");
        Date dob = new Date();
        dto.setDateOfBirth(dob);
        dto.setPosition("Desarrollador Backend");
        Date regDate = new Date();
        dto.setRegistrationDate(regDate);
        dto.setActive(true);

        assertEquals(101, dto.getEmployeeId());
        assertEquals("Carlos", dto.getFirstName());
        assertEquals("Andrés", dto.getMiddleName());
        assertEquals("Ramírez", dto.getLastName());
        assertEquals("López", dto.getSecondLastName());
        assertEquals(30, dto.getAge());
        assertEquals("Masculino", dto.getGender());
        assertEquals(dob, dto.getDateOfBirth());
        assertEquals("Desarrollador Backend", dto.getPosition());
        assertEquals(regDate, dto.getRegistrationDate());
        assertTrue(dto.getActive());
    }

    @Test
    void testAllArgsConstructor() {
        Date dob = new Date();
        Date regDate = new Date();

        EmployeeDTO dto = new EmployeeDTO(
                101, "Carlos", "Andrés", "Ramírez", "López",
                30, "Masculino", dob, "Desarrollador Backend",
                regDate, true);

        assertEquals(101, dto.getEmployeeId());
        assertEquals("Carlos", dto.getFirstName());
        assertEquals("Andrés", dto.getMiddleName());
        assertEquals("Ramírez", dto.getLastName());
        assertEquals("López", dto.getSecondLastName());
        assertEquals(30, dto.getAge());
        assertEquals("Masculino", dto.getGender());
        assertEquals(dob, dto.getDateOfBirth());
        assertEquals("Desarrollador Backend", dto.getPosition());
        assertEquals(regDate, dto.getRegistrationDate());
        assertTrue(dto.getActive());
    }

    @Test
    void testToString() {
        EmployeeDTO dto = new EmployeeDTO(101, "Carlos", "Andrés", "Ramírez", "López", 30, "Masculino", new Date(),
                "Desarrollador Backend", new Date(), true);
        String str = dto.toString();

        assertNotNull(str);
        assertTrue(str.contains("Carlos"));
        assertTrue(str.contains("Ramírez"));
        assertTrue(str.contains("Desarrollador Backend"));
    }

    @Test
    void testValidationFailsWhenRequiredFieldsNull() {
        EmployeeDTO dto = new EmployeeDTO();

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(dto);

        // Debe tener violaciones para los campos @NotNull
        assertFalse(violations.isEmpty());

        // Comprueba que hay violaciones para cada campo obligatorio
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("secondLastName")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("age")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("gender")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("dateOfBirth")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("position")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("active")));
    }

    @Test
    void testValidationPassesWhenAllRequiredFieldsSet() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setFirstName("Carlos");
        dto.setLastName("Ramírez");
        dto.setSecondLastName("López");
        dto.setAge(30);
        dto.setGender("Masculino");
        dto.setDateOfBirth(new Date());
        dto.setPosition("Desarrollador Backend");
        dto.setActive(true);

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}
