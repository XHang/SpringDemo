package com.Spring.Test;

import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.Spring.Example.IOCExample;
import com.Spring.Model.User;

public class TestIOCExample {
	@Test
	public void Test_add(){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
			
		IOCExample bean = context.getBean("IOCExample", IOCExample.class);
		System.out.println("取出Map集合对象");
		for(Map.Entry<String, String> entry:bean.getMap().entrySet()){
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
		System.out.println("取出List对象");
		for(Integer i:bean.getList()){
			System.out.println(i);
		}
		System.out.println("取出Set对象");
		for(String str:bean.getSet()){
			System.out.println(str);
		}
		System.out.println("取出List集合中的用户对象");
		for(User user:bean.getUsers()){
			System.out.println(user);
		}
	}

}
