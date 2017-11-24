package com.cxh.server;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class StoreIntegration {
	
	//熔断器功能的方法，defaultStores是熔断方法，发生熔断时会使用
	@HystrixCommand(fallbackMethod = "defaultStores")
	public Object  getStore(Map<String, Object> parameters){
		//do stuff that might fail
		throw new RuntimeException("失败啦");
	}
	public Object defaultStores(Map<String, Object> parameters) {
			//something useful
	        return "服务失败";
	}

	
}
