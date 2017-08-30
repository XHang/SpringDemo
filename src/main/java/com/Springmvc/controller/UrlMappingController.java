package com.Springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 该类主要演示用传参数的方式来访问控制器方法
 * @author Mr-hang
 *
 */
@Controller
@RequestMapping("/mapping")
public class UrlMappingController {
	
	@RequestMapping(params="method=otherMapping",method=RequestMethod.GET)
	@ResponseBody
	public String otherMapping(){
		return "你通过传参数的方式访问到这个方法了";
	}

}
