package com;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


/**
 * 作为主要boot的运行类和配置类
 * @author Administrator
 *
 */
@SpringBootApplication
public class BootExample {
	public static void main(String[] args) {
		 SpringApplication.run(BootExample.class);
	}
} 
