package com.ribbon.server;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ribbon.fallback.UserFallBack;


//这个注解创建了一个别名叫userFeignClient的Bean，也叫客户端,同时还创建了Ribbon的负载均衡器。另外user是Eureka服务中被注册的实例名
//只有找到这个实例名，才能访问这个实例的服务
@FeignClient(value="user",fallbackFactory=UserFallBack.class)
public interface UserClient {
	
	//以下所有方法由于Hystrix在类路径上，所以都启用了断路器
	//这个value就是user这个实例对外服务的接口地址
	@RequestMapping(value="/server/getXml",method=RequestMethod.POST)
	public String getXML(String xml);
	
}
