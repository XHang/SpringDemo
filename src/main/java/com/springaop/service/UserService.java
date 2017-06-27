package com.springaop.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	public String  add(){
		System.out.println("添加成功");
		return "success";
	}
}
