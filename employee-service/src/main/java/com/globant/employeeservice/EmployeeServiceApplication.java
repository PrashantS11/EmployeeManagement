package com.globant.employeeservice;

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
//import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Employee Service REST API Documentation",
				description = "Employee Service REST API Documentation",
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
				description = "Spring Boot Employee Management Documentation",
				url = "https://www.google.com"
		)
)
@EnableFeignClients
//@EnableRetry
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
