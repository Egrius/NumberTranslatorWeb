package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo.config", "com.example.demo.controller", "com.example.demo.repository", "com.example.demo.service"})
public class NumberTranslatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberTranslatorApplication.class, args);
	}
}
