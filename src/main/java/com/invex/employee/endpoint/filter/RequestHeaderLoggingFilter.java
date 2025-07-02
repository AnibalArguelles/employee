package com.invex.employee.endpoint.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * Filter that logs the requested URL and all HTTP request headers for each incoming request.
 * <p>
 * This filter is executed once per request and is automatically registered by Spring Boot.
 * It helps in debugging and tracking client requests by printing request details to the logs.
 * </p>
 * 
 * Example output:
 * <pre>
 * Incoming request: GET http://localhost:8080/employees
 * Headers: [Host=localhost:8080; User-Agent=PostmanRuntime/7.35.0; Accept=; ...]
 * </pre>
 * 
 * Author: Invex
 */
@Component
@Slf4j
public class RequestHeaderLoggingFilter extends OncePerRequestFilter {

    /**
     * Intercepts each HTTP request once per request lifecycle and logs its headers and URL.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException in case of servlet errors
     * @throws IOException      in case of I/O errors
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        logRequestDetails(request);
        filterChain.doFilter(request, response);
    }

    /**
     * Logs the HTTP method, request URL, and all headers from the given {@link HttpServletRequest}.
     *
     * @param request the HTTP request
     */
    private void logRequestDetails(HttpServletRequest request) {
        String method = request.getMethod();
        String requestUrl = request.getRequestURL().toString();
        StringBuilder headersLog = new StringBuilder();

        headersLog.append("Incoming request: ")
                  .append(method)
                  .append(" ")
                  .append(requestUrl)
                  .append("\nHeaders: [");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            headersLog.append(header).append("=").append(value).append("; ");
        }

        headersLog.append("]");

        log.info(headersLog.toString());
    }
}
