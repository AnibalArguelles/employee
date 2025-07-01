package com.invex.employee.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class EmployeeExceptionHandlerTest {

    private EmployeeExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new EmployeeExceptionHandler();
    }

    @Test
    void testHandleEmployeeNotFoundException() {
        EmployeeNotFoundException ex = new EmployeeNotFoundException("Empleado no encontrado");

        ResponseEntity<Object> response = handler.handleEmployeeNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getHeaders().isEmpty());
        assertNull(response.getBody()); // porque se devuelve solo headers y status
    }

    @Test
    void testHandleHttpMessageNotReadable() {
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("JSON inválido");

        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getHeaders().isEmpty());
        assertNotNull(response.getBody());
        String body = (String) response.getBody();
        assertTrue(body.contains("El formato del cuerpo de la petición es inválido"));
        assertTrue(body.contains("JSON inválido"));
    }

    @Test
    void testHandleValidationExceptions() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);

        ObjectError error1 = new ObjectError("field1", "Campo obligatorio");
        ObjectError error2 = new ObjectError("field2", "Formato inválido");
        when(bindingResult.getAllErrors()).thenReturn(Arrays.asList(error1, error2));

        ResponseEntity<Object> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getHeaders().isEmpty());
        assertNotNull(response.getBody());

        String body = (String) response.getBody();
        assertTrue(body.contains("Errores de validación:"));
        assertTrue(body.contains("Campo obligatorio"));
        assertTrue(body.contains("Formato inválido"));
    }
}
