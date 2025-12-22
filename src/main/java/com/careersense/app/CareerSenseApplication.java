package com.careersense.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CareerSenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerSenseApplication.class, args);
        System.out.println("CareerSenseApplication started");
	}

}
