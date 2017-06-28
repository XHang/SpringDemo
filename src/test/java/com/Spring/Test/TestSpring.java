package com.Spring.Test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.Spring.Model.User;
import com.Spring.Service.UserService;

public class TestSpring {
	@Test
	public void Test_add(){
		ApplicationContext context=new ClassPathXmlApplicationContext("beans.xml");
		User user=new User();
		user.setUsername("陈晓航");
		UserService us=(UserService) context.getBean("Service");
		us.add(user);
	}

}
