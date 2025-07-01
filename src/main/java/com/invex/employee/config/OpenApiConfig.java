package com.invex.employee.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Employee Management API",
        version = "1.0",
        description = "API documentation for Invex's employee management system.",
        termsOfService = "https://invex.com/terms",
        contact = @Contact(
            name = "Invex Support",
            email = "support@invex.com",
            url = "https://invex.com/contact"
        ),
        license = @License(
            name = "Apache License 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = @Server(url = "http://localhost:8080", description = "Local server")
)
public class OpenApiConfig {
}
