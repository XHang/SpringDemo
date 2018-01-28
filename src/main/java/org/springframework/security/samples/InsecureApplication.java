package org.springframework.security.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Joe Grandja
 * Spring boot启动程序
 */
@SpringBootApplication
public class InsecureApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsecureApplication.class, args);
	}
}
