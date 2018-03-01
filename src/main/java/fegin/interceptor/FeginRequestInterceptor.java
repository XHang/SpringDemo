package fegin.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeginRequestInterceptor  implements RequestInterceptor{

	/**
	 * 在Fegin发请求的时候设置请求头
	 */
	@Override
	public void apply(RequestTemplate template) {
		System.out.println("已添加请求头");
		template.header("header", "value");
	}

}
