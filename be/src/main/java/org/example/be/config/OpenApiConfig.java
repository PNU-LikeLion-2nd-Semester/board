package org.example.be.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "My API",
		version = "v1.0",
		description = "This is the API documentation for My Application",
		contact = @Contact(name = "John Doe", email = "john.doe@example.com"),
		license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
	)
)
public class OpenApiConfig {
}
