package com.zuul.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * Zuul中对于断路器的回调类
 * @author Mr-hang
 *
 */
@Component
public class EurekaClientFallback  implements ZuulFallbackProvider {
	/**
	 * 返回路由的serverId,该路由的线路如果断开，将会执行该回调类的回调方法
	 */
	@Override
	public String getRoute() {
		return "EurekaClient";
	}

	@Override
	public ClientHttpResponse fallbackResponse() {
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				System.out.println("exec getHeaders");
				HttpHeaders headers = new HttpHeaders();
				headers.add("host", "www.google");
				headers.add("Cookie", "34564675");
				return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				System.out.println("exec getBody");
				return new ByteArrayInputStream("正确响应，嘻嘻嘻嘻".getBytes());
			}
			
			@Override
			public String getStatusText() throws IOException {
				System.out.println("exec getStatusText");
				return "一切正常";
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				System.out.println("run getStatusCode");
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				System.out.println("执行getRawStatusCode啦");
				return 200;
			}
			
			@Override
			public void close() {
				System.out.println("执行close啦");
			}
		};
	}

}
