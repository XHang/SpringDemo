package com.SpringBoot.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.SpringBoot.Interceptor.InterceptorDemo;
/**
 * SpringMVC的配置类
 * CallableProcessingInterceptor
 * DeferredResultProcessingInterceptor
 * 好像挺有趣
 * @author Administrator
 *
 */
@Configuration
public class SpringMvcConfig  extends WebMvcConfigurerAdapter{
	//推荐用自动注入的形式注册拦截器
	@Autowired
	InterceptorDemo interceptorDemo ;
	
	/**
	 * 该方法可以添加自定义的拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptorDemo).addPathPatterns("/**");
	}
	@Override
	
	/**
	 * 该方法可以设置默认异步超时值<br/>
	 * 如果不设置，默认是servlct容器的超时值，比如说Tomcat，默认超时为10s<br/>
	 * 你可以在这个方法上+1s
	 */
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		//设置默认异步超时时间为11s
		configurer.setDefaultTimeout(11000L);
	}

}
