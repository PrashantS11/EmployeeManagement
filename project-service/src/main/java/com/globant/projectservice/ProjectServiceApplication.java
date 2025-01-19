package com.globant.projectservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Project Service REST API Documentation",
				description = "Project Service REST API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Prashant",
						email = "prashantsonawane0811@gmail.com",
						url = "https://www.google.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.google.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Project Management Documentation",
				url = "https://www.google.com"
		)
)
@EnableFeignClients
public class ProjectServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
