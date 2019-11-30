package com.parcom.classroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassroomApplication {

//	docker run --name classroom_pg -e POSTGRES_PASSWORD=parcom -d -p 5432:5432 postgres:11
	public static void main(String[] args) {
		SpringApplication.run(ClassroomApplication.class, args);
	}

}
