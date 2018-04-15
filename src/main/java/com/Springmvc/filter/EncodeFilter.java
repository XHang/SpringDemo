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
		//执行该过滤方法时，把过滤链request对象替换为自己的request对象
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		filterChain.doFilter(new CharEcnodeRequest(httpServletRequest, encode), response);
	}
	//过滤器初始化
	@Override
	public void init(FilterConfig config)  {
		Enumeration<?> e = config.getServletContext().getAttributeNames();
		while(e.hasMoreElements()){
			System.out.println(e.nextElement());
		}
		//从web.xml读取字符编码配置，如果读取不到，设置默认值为utf-8
		 encode = config.getInitParameter("charSet");
		 if(encode == null || "".equals(encode.trim())){
			 encode = "utf-8";
		 }
	}

}
