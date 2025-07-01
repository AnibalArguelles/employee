package com.invex.employee.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeNotFoundExceptionTest {

    @Test
    void testConstructorAndMessage() {
        String expectedMessage = "Empleado no encontrado";
        EmployeeNotFoundException exception = new EmployeeNotFoundException(expectedMessage);

        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }
}
