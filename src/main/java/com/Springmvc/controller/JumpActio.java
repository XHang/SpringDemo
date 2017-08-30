package com.Springmvc.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 演示Action是怎么做到重定向，forword的
 * @author Mr-hang
 *
 */
@Controller
public class JumpActio {

	/**
	 * 故名思意，其实就是用原来的request对象来forword，跟MVC框架没有一毛钱的关系
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@RequestMapping("/requestForWord")
	public void requestForWord(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		request.setAttribute("msg","欢迎您，当前乘坐的交通工具是RequestForward");
		request.getRequestDispatcher("/example.jsp").forward(request, response);
	}
	
	/**
	 * 用原来的Response对象来成重定向，Servlet就可以做到了
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/responseRedirection")
	public void responseRedirection(HttpServletRequest request,HttpServletResponse response) throws IOException{
		request.setAttribute("msg","欢迎您，不过可惜你绝对看不到这句话的了");
		response.sendRedirect("FileUpload.jsp");
	}
	
	@RequestMapping("/mvcJump")
	public String handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		/* 通过SpringMVC自带的转发来实现结果跳转，有视图解析器的话，不要带jsp
		 * 方式1*/
		 /*request.setAttribute("msg","欢迎您，当前乘坐的交通工具是MVC和视图解析器独家提供的Forward");
		 return "example";*/
		 
		 
		
		/* 通过SpringMVC自带的转发来实现结果跳转
		 * 方式2
		 */
		/* request.setAttribute("msg","欢迎您，当前乘坐的交通工具是MVC独家提供的Forward");
		  return "forward:example.jsp";*/
		 
		
		//通过SpringMVC自带的重定向来实现结果跳转
		System.out.print("mvc自带重定向");
		return "redirect:FileUpload.jsp";
	}
}
