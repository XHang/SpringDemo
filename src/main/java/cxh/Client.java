package cxh;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan
@EnableAutoConfiguration
//声明这个组件是一个Eureka客户端
@EnableEurekaClient
@RestController
public class Client {
	
	@RequestMapping("/")
	public String home() {
		return "hello Eujreka !";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Client.class, args);
	}

}
