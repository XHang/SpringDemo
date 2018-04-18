package com.Springmvc.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 可以实现修改Response里面数据的Filter
 * @author cxh
 * 过滤器修改数据的原理，
 * 实际就是搞一个容器，让调用方将数据放在里面
 * （欺骗调用方的，它以为是往流里面写东西，其实写在了容器里）
 *  在doFilter，就可以在容器里面把数据取出来，写入到真正的HttpServletResponse流里面。
 *  达到修改数据的效果
 *
 */
public class ModifyResponseFilter  implements Filter{

	@Override
	public void destroy() {
		System.out.println("filter挂了");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		CustomResponseWapper responseWapper = new CustomResponseWapper(servletResponse);
		//责任链模式，让下面的过滤器链直至servlet方法，都使用我的response的自定义对象
        chain.doFilter(request,responseWapper);
		//责任链返回了，可以拿数据改造送进真正的输出流了
		byte [] bytes = motifyData(responseWapper);
        response.setContentLength(bytes.length);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain");
        response.getOutputStream().write(bytes);
	}

    /**
     * 修改响应的数据
     * @param responseWapper 响应流包装类
     * @return 修改后的包装类
     */
	private byte[] motifyData(CustomResponseWapper responseWapper) throws UnsupportedEncodingException {
		byte[] bytes =responseWapper.getContent();
		String str = new String(bytes,"utf-8");
		System.out.println("响应的数据为"+str);
		return (str+"你的数据被我吃了，哈哈哈").getBytes("utf-8");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("初始化ModifyResponseFilter中.....");
	}
	
	
}
