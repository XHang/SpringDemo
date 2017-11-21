package cxh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
//声明这个是一个配置服务器
@EnableConfigServer
public class ConfigServer {
	public static void main(String[] args) {
		SpringApplication.run(ConfigServer.class, args);
	}
}
