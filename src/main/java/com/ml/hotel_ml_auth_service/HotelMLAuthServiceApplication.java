package com.ml.hotel_ml_auth_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class HotelMLAuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(HotelMLAuthServiceApplication.class, args);
	}
}
