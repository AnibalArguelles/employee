package com.invex.employee.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class OpenApiConfigTest {

    @Test
    void testOpenApiConfigInstantiation() {
        OpenApiConfig config = new OpenApiConfig();
        assertNotNull(config, "La instancia de OpenApiConfig no debe ser null");
    }
}
