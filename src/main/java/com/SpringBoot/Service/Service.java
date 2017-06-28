package com.SpringBoot.Service;

import java.net.URISyntaxException;

import org.springframework.web.client.RestClientException;

public interface Service {
	public String  testRestTemplate() throws RestClientException, URISyntaxException;
}
