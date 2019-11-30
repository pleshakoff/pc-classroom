package com.parcom.pcclassroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PcClassroomApplication {

//	docker run --rm --name classroom_pg -e POSTGRES_PASSWORD=parcom -d -p 5432:5432 postgres
	public static void main(String[] args) {
		SpringApplication.run(PcClassroomApplication.class, args);
	}

}
