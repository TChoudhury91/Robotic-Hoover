package com.tanvirchoudhury.robotichoover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = RoboticHooverApplication.class)
public class RoboticHooverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoboticHooverApplication.class, args);
	}
}
