package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
//去掉该注解，Zuul不依赖Eureka服务端来发现微服务
//@EnableEurekaClient
//不加这个注解居然还报错了
//Unable to start EmbeddedWebApplicationContext due to missing EmbeddedServletContainerFactory bean.
@SpringBootApplication
public class ZuulApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
}
