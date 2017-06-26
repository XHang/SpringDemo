package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.SpringBoot.Example.ControlDemo;
@EnableAutoConfiguration
public class BootExample {
	public static void main(String[] args) {
		 SpringApplication.run(BootExample.class);
	}
} 
