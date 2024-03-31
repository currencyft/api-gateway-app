package com.comvarse.microservices.routeinvocation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RouteInvocationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteInvocationApplication.class, args);
	}

}
