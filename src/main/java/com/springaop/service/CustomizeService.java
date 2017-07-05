package com.springaop.service;

import org.springframework.stereotype.Service;

@Service
public class CustomizeService {
	public String customizeMethod(){
		System.out.println("切面的第六个示例程序----customizeMethod");
		return "success";
	}
}
