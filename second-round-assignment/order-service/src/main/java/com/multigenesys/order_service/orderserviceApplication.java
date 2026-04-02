package com.multigenesys.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan("com.multigenesys.order_service")
public class orderserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(orderserviceApplication.class, args);
	}

}
