package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

/**
 * 提供断路器的服务
 * @author Mr-hang
 *
 */
@EnableHystrixDashboard
@SpringBootApplication
@EnableCircuitBreaker
public class HystrixApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(HystrixApplication.class).web(true).run(args);
	}

}
