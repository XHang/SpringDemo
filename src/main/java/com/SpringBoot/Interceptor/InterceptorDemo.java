package com.SpringBoot.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
@Component
public class InterceptorDemo  implements HandlerInterceptor {
	
	/**
	 * 请求调用前进行处理
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("请求来了");
		return true;
	}
	/**
	 * 请求调用后进行处理
	 */
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("请求走了");
		
	}
	/**
	 * 请求调用后，并且以上两者都调用后进行执行
	 * 用于资源清理动作。
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("清洁工来清理了");
	}

}
