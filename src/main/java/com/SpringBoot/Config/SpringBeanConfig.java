package com.SpringBoot.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
/**
 * SpringBean 配置类
 * @author Administrator
 *
 */
@Configuration
public class SpringBeanConfig {
	@Bean("restTemplate")
	public RestTemplate newRestTemplate(){
		
	}
	@Bean("httpClientFactory")
	public SimpleClientHttpRequestFactory  newHttpClientFactory(){
		SimpleClientHttpRequestFactory httpRequestClient=new SimpleClientHttpRequestFactory();
		httpRequestClient.setConnectTimeout(10000);
		httpRequestClient.setReadTimeout(10000);
		return httpRequestClient;
		
	}
}
