package com.springaop.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	public String  add(){
		System.out.println("添加成功");
		return "success";
	}
	public String delete(){
		System.out.println("切面的第六个示例程序-----delete");
		return "success";
	}
}
