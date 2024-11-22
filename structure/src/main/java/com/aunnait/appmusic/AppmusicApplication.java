package com.aunnait.appmusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) //Catches our custom configuration class
public class AppmusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppmusicApplication.class, args);
	}

}
