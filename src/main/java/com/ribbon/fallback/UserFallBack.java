package com.ribbon.fallback;

import org.springframework.stereotype.Component;

import com.fegin.model.User;
import com.ribbon.server.UserClient;

import feign.hystrix.FallbackFactory;


/**如果UserClient调用微服务发生线路上的问题，则调用回调类的方法。该类就是传说中的回调类**/
@Component
public class UserFallBack  implements FallbackFactory<UserClient>{

	/*
	 * 
	这个就是当UserClient端执行getXML请求微服务时，失败执行的回调函数
	@Override
	public String getXML(String xml) {
		return "微服务狗带了！！！";
	}
	前提是回调类继承了UserClient，但是现在要查看线路断开的具体原因，所以不能这么做了
	*/
	//当连接微服务的线路断开时，参数throwable可以查看具体原因
	@Override
	public UserClient create(Throwable throwable) {
		System.out.println("马上创建示例");
		return new UserClient() {
			@Override
			public String getXML(String xml) {
				System.out.println(throwable);
					return "微服务不可用，抱歉";
			}

			@Override
			public String addUser(User user, String type) {
				return "添加用户失败";
			}
		};
	}

}
