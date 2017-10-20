package com.Springmvc.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
/**
 * 乱码过滤器
 * @author Administrator
 *
 */
public class EncodeFilter  implements Filter{

	@Override
	public void destroy() {
	}

	private String encode;
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		filterChain.doFilter(new CharEcnodeRequest(httpServletRequest, encode), response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		Enumeration<?> e = config.getServletContext().getAttributeNames();
		while(e.hasMoreElements()){
			System.out.println(e.nextElement());
		}
		
		
		 encode = config.getInitParameter("charSet");
		 if(encode == null || "".equals(encode.trim())){
			 encode = "utf-8";
		 }
	}

}
