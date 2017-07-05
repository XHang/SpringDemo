package com.springaop.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springaop.service.CustomizeService;
import com.springaop.service.UserService;
@Controller
@RequestMapping("/demo")
public class ActionDemo {
	@Autowired
	private UserService service;
	@Autowired
	private CustomizeService customizeService;
	@ResponseBody
	@RequestMapping("/save")
	public String save(){
		return service.add();
	}
	
	@ResponseBody
	@RequestMapping("/example6_1")
	public String example6_1(){
		return service.delete();
	}
	
	@ResponseBody
	@RequestMapping("/example6_2")
	public String example6_2(){
		return customizeService.customizeMethod();
	}
	
}
