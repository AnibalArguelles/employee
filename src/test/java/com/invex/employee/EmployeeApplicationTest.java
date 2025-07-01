package com.invex.employee;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class EmployeeApplicationTest {

		
		@SuppressWarnings({ "resource", "static-access" })
	    @Test
	    void testMainMethodRunsSpringApplication() {
			EmployeeApplication application = new EmployeeApplication();
	        assertNotNull(application);
	        // Given
	        String[] args = { "arg1", "arg2" };
	        ConfigurableApplicationContext applicationContextMock = Mockito.mock(ConfigurableApplicationContext.class);

	        // Mock static method call
	        try (MockedStatic<SpringApplication> mockedStatic = Mockito.mockStatic(SpringApplication.class)) {
	            mockedStatic.when(() -> SpringApplication.run(EmployeeApplication.class, args))
	                    .thenReturn(applicationContextMock);

	            // When
	            application.main(args);

	            // Then: Verify static method call
	            mockedStatic.verify(() -> SpringApplication.run(EmployeeApplication.class, args));
	        }
	}
}
