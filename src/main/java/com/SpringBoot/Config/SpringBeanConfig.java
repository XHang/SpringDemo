package com.SpringBoot.Config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
/**
 * SpringBean 配置类
 * @author Administrator
 *
 */
@Configuration
public class SpringBeanConfig {
	/**
	 * 
	 * @param lesshttpClientFactory
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(SimpleClientHttpRequestFactory httpClientFactory){
		RestTemplate restTemplate=new RestTemplate();
		restTemplate.setRequestFactory(httpClientFactory);
		List <HttpMessageConverter <?>> converterList=new ArrayList<HttpMessageConverter <?>> ();
		converterList.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
		restTemplate.setMessageConverters(converterList);
		return restTemplate;
		
	}
	@Bean
	public SimpleClientHttpRequestFactory  httpClientFactory(){
		SimpleClientHttpRequestFactory httpRequestClient=new SimpleClientHttpRequestFactory();
		httpRequestClient.setConnectTimeout(10000);  //10s超时
		httpRequestClient.setReadTimeout(10000);
		return httpRequestClient;
	}
	@Bean
	public SimpleClientHttpRequestFactory  lesshttpClientFactory(){
		SimpleClientHttpRequestFactory httpRequestClient=new SimpleClientHttpRequestFactory();
		httpRequestClient.setConnectTimeout(5000);   //5秒超时
		httpRequestClient.setReadTimeout(5000);
		return httpRequestClient;
	}
}
