package com.springaop.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springaop.service.UserService;
@Controller
@RequestMapping("/demo")
public class ActionDemo {
	@Autowired
	private UserService service;
	@ResponseBody
	@RequestMapping("/save")
	public String save(){
		return service.add();
	}
}
