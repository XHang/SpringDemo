package com.SpringBoot.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 一个简单的过滤器。不简单的是，这个过滤器不由Web.xml注册，而是在配置类里面配置
 * @author Administrator
 *
 */
@WebFilter("/FilterDemo")
public class FilterDemo implements Filter {

    public FilterDemo() {
    }
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("过滤器执行中。。。。");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
