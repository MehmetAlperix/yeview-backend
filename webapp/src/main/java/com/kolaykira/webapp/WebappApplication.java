package com.kolaykira.webapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.security.SecureRandom;


@SpringBootApplication
@EnableScheduling
public class WebappApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebappApplication.class, args);
	}

}
