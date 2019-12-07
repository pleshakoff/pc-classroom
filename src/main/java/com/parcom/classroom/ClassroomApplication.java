package com.parcom.classroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableSwagger2WebMvc
@Import(SpringDataRestConfiguration.class)
public class ClassroomApplication {


//	docker run --name classroom_pg -e POSTGRES_PASSWORD=parcom -d -p 5432:5432 postgres:11
//  docker image build -t pleshakoff/pc-classroom:hw1 .
//  docker image push pleshakoff/pc-classroom:hw1
	public static void main(String[] args) {
		SpringApplication.run(ClassroomApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).globalOperationParameters(
				Arrays.asList(new ParameterBuilder()
						.name("X-Auth-Token")
						.description("User session token")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()))
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any())
		    	.build();
	}

}

