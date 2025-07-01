package com.invex.employee.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    void testNoArgsConstructorAndSettersGetters() {
        Employee emp = new Employee();

        emp.setEmployeeId(1);
        emp.setFirstName("Ana");
        emp.setMiddleName("María");
        emp.setLastName("Pérez");
        emp.setSecondLastName("Gómez");
        emp.setAge(28);
        emp.setGender("Femenino");
        Date dob = new Date();
        emp.setDateOfBirth(dob);
        emp.setPosition("QA Engineer");
        Date regDate = new Date();
        emp.setRegistrationDate(regDate);
        emp.setActive(true);

        assertEquals(1, emp.getEmployeeId());
        assertEquals("Ana", emp.getFirstName());
        assertEquals("María", emp.getMiddleName());
        assertEquals("Pérez", emp.getLastName());
        assertEquals("Gómez", emp.getSecondLastName());
        assertEquals(28, emp.getAge());
        assertEquals("Femenino", emp.getGender());
        assertEquals(dob, emp.getDateOfBirth());
        assertEquals("QA Engineer", emp.getPosition());
        assertEquals(regDate, emp.getRegistrationDate());
        assertTrue(emp.getActive());
    }

    @Test
    void testAllArgsConstructor() {
        Date dob = new Date();
        Date regDate = new Date();

        Employee emp = new Employee(
                1, "Ana", "María", "Pérez", "Gómez",
                28, "Femenino", dob, "QA Engineer",
                regDate, true);

        assertEquals(1, emp.getEmployeeId());
        assertEquals("Ana", emp.getFirstName());
        assertEquals("María", emp.getMiddleName());
        assertEquals("Pérez", emp.getLastName());
        assertEquals("Gómez", emp.getSecondLastName());
        assertEquals(28, emp.getAge());
        assertEquals("Femenino", emp.getGender());
        assertEquals(dob, emp.getDateOfBirth());
        assertEquals("QA Engineer", emp.getPosition());
        assertEquals(regDate, emp.getRegistrationDate());
        assertTrue(emp.getActive());
    }

    @Test
    void testToString() {
        Employee emp = new Employee(
                1, "Ana", "María", "Pérez", "Gómez",
                28, "Femenino", new Date(), "QA Engineer",
                new Date(), true);

        String str = emp.toString();

        assertNotNull(str);
        assertTrue(str.contains("Ana"));
        assertTrue(str.contains("Pérez"));
        assertTrue(str.contains("QA Engineer"));
    }
}
