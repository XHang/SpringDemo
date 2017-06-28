package com.SpringBoot.ServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.SpringBoot.Service.Service;
@Component
public class ServiceImpl implements Service{
	@Autowired
	RestTemplate restTemplate;

	@Override
	public String testRestTemplate() throws RestClientException, URISyntaxException {
		return restTemplate.getForObject(new URI("http://127.0.0.1:8080/SubmitTest/submitTest"), String.class);
	}
	
	
}
