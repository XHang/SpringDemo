package com.SpringBoot.Control;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import com.SpringBoot.Service.Service;
@Controller



/**
 * 控制器层
 * @author Administrator
 *
 */
public class ControlDemo {
	@Autowired
	private Service  service;
	
	/**
	 * boot入门程序
	 * @return
	 */
	@RequestMapping("/")
	@ResponseBody
	public String sayHello(){
		return "hello SpringBoot!";
	}
	
	/**
	 * 调用远程服务器的服务并返回结果
	 * @return
	 * @throws RestClientException
	 * @throws URISyntaxException
	 */
	@RequestMapping("/testRestTemplate")
	@ResponseBody
	public String testRestTemplate() throws RestClientException, URISyntaxException{
		return service.testRestTemplate();
	}
}