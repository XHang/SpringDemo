package com.SpringBoot.Example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller

public class ControlDemo {
	@RequestMapping("/")
	@ResponseBody
	public String sayHello(){
		return "hello SpringBoot!";
	}
}