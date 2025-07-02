package com.invex.employee.endpoint.filter;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestHeaderLoggingFilterTest {

    private RequestHeaderLoggingFilter filter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        filter = new RequestHeaderLoggingFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    public void testLogHeadersAndProceedFilterChain() throws ServletException, IOException {
        // Given headers
        Vector<String> headerNames = new Vector<>();
        headerNames.add("User-Agent");
        headerNames.add("Accept");

        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/employees"));
        when(request.getHeaderNames()).thenReturn(headerNames.elements());
        when(request.getHeader("User-Agent")).thenReturn("JUnit-Test-Agent");
        when(request.getHeader("Accept")).thenReturn("application/json");

        // When
        filter.doFilterInternal(request, response, filterChain);

        // Then
        verify(request, times(1)).getHeaderNames();
        verify(request, times(1)).getHeader("User-Agent");
        verify(request, times(1)).getHeader("Accept");
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testNoHeadersPresent() throws ServletException, IOException {
        // Given no headers
        Enumeration<String> emptyHeaders = Collections.emptyEnumeration();

        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/employees"));
        when(request.getHeaderNames()).thenReturn(emptyHeaders);

        // When
        filter.doFilterInternal(request, response, filterChain);

        // Then
        verify(request, times(1)).getHeaderNames();
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
