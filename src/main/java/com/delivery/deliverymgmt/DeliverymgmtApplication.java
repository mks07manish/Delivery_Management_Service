package com.delivery.deliverymgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DeliverymgmtApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliverymgmtApplication.class, args);
		// System.out.println("Delivery Management Service Started");
	}
	
}