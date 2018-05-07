package com.SpringBoot.Config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.SpringBoot.Interceptor.InterceptorDemo;
import com.SpringBoot.filter.FilterDemo;
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
	
	/**
	 * 注册一个过滤器，只在SpringBoot环境下才有效
	 * @return
	 */
	@Bean 
	public FilterRegistrationBean filterRegistrationBean(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		List<String> urlPatterns = new ArrayList<String>();
		//匹配该域名下面的所有URL
		urlPatterns.add("/*");
		FilterDemo filter = new FilterDemo();
		registrationBean.setFilter(filter);
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}

}
