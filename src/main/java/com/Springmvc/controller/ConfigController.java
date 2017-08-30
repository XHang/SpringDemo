package com.Springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
/*
 * 演示Spring的控制器，用配置文件指定路径
 */
public class ConfigController implements Controller{
	
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			ModelAndView mv=new ModelAndView();			//创建一个视图模型体
			mv.addObject("msg", "SpringMVC ，欢迎您,本次使用配置文件来指定映射文件");
			mv.setViewName("example");										//设置逻辑视图名。
		return mv; 
	}
	
}
