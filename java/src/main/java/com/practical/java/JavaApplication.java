package com.practical.java;

import com.practical.java.jdbc.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaApplication implements CommandLineRunner {

	@Autowired
	private FlightBookingService flightBookingService;

	public static void main(String[] args) {
		SpringApplication.run(JavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		flightBookingService.processBooking();
	}

}
