package com;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * SpringBoot启动程序，该程序使用的配置值将从远程的配置服务器获取
 * @author Administrator
 *
 */
@Configuration
@EnableAutoConfiguration
@RestController
public class Application {
	
	@Value("${zuul.routes.api-a.path}")
	 String name;
	
	@Value("CCC")
	String app;
	
	@RequestMapping("/")
	public String home() {
		return "CinfigName:"+name+"   "+app;
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	
	
}
