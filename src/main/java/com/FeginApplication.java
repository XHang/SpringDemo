package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RestController;


@EnableEurekaClient 
@RestController
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
//该注解可以启用HystrixDashboard支持
@EnableHystrixDashboard
public class FeginApplication {
	public static void main(String[] args) {
		SpringApplication.run(FeginApplication.class, args);
	}
	
	
	/*
	未使用Feign时用restTemplate演示负载均衡
	@Bean
	@LoadBalanced
	 RestTemplate restTemplate() {
		return new RestTemplate();
	}*/
}
