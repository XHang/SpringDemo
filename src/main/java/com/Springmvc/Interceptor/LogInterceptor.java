package com.Springmvc.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 演示Spring的拦截器类
 * 拦截请求并记录日志
 * 懒得加日志依赖，直接控制台输出
 * @author Mr-hang
 *
 */
public class LogInterceptor implements HandlerInterceptor{

	/**
	 * 在整个请求后执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("请求完美结束");
	}
	
	/**
	 * 请求得到响应，返回客户端时拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("当前响应的AuthType为"+request.getAuthType());
	}
	/**
	 * 发请求还未执行响应任务前被拦截器拦截，返回值为true则放过拦截，为false则踢回客户端
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		System.out.println("当前请求的url地址是："+request.getRequestURI());
		return true;
	}

}
