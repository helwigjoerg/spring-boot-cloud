package com.helwig.microservices.cloudnetflixeurekanamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CloudNetflixEurekaNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudNetflixEurekaNamingServerApplication.class, args);
	}

}
