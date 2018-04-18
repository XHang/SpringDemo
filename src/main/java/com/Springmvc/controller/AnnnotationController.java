package com.Springmvc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.Springmvc.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 采取注解来实现url映射
 * @author Mr-hang
 *
 */
@Controller
public class AnnnotationController {
	@RequestMapping("/annotation")
	public ModelAndView example(){
			ModelAndView mv=new ModelAndView();			//创建一个视图模型体
			mv.addObject("msg", "SpringMVC ，欢迎您,本次使用注解；来映射url地址");
			mv.setViewName("example");										//设置逻辑视图名。
		return mv; 
	}
	
	
	/**
	 * 测试@ResponseBody的作用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/responseExample")
	public User getUser(){
		User user = new User();
		user.setUsername("测试01");
		user.setPassword("12345");
		return user;
	}
	
	/**
	 * 测试@Response对String的支持
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getString")
	public String getString(){
		return "我是字符串，不准带双引号";
	}
	
	/**
	 * 演示SpringMVC的上传功能
	 * @param files
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile[] files ) throws Exception {
		BufferedOutputStream bos=null;
		BufferedInputStream bis=null;
		for(MultipartFile file:files){
			//获取上传的文件名
			String filename=file.getOriginalFilename();
			//创建上传的文件路径，并放在d盘
			bos=new BufferedOutputStream(new FileOutputStream("D:\\"+filename));
			bis=new BufferedInputStream(file.getInputStream());
			int leng;
			while((leng=bis.read())!=-1){
				bos.write(leng);
			}
			bos.flush();
		}
		bis.close();
		bos.close();	//关闭自动刷新流	
		return "success";	 
	}
	
	/**
	 * rest风格的url
	 */
	@ResponseBody
	@RequestMapping("/rest/{msg}/{keyWord}")
	public String  restMethod(@PathVariable("msg")String msg,@PathVariable("keyWord")String keyWord){
		return "成功响应，你发送的数据是"+msg+"和"+keyWord;
	}
	
	/**
	 * 别名演示，请求的参数和实际接收的参数不一致的情况下
	 * @param fuck
	 * @return
	 */
	@RequestMapping("/alias")
	@ResponseBody
	public String alias(@RequestParam("keyWord")String fuck){
		return "成功响应，你发送的数据是"+fuck+"嘻嘻嘻";
	}

	/**
	 * @param user
	 * @return
	 * @throws JsonProcessingException 
	 */
	@ResponseBody
	@RequestMapping("/sendObject") 
	public String sendObject(User user) throws JsonProcessingException{
		System.out.println(user.getUsername());
		ObjectMapper mapper = new ObjectMapper(); //转换器 
		String json = mapper.writeValueAsString(user);
		return "你发送的对象是"+json;
	}
	
	@ResponseBody
	@RequestMapping("/encodeExample") 
	public String encodeExample(String keyword){
		System.out.println("你输入的参数值是"+keyword);
		return keyword;
	}
	
	@ResponseBody
	@RequestMapping("/encodeExampleOfArray") 
	public String encodeExampleOfArray(String[] keyword){
		System.out.println("你输入的参数值是"+arrayToString(keyword));
		return arrayToString(keyword);
	}
	
	private String arrayToString(String[] strArr){
		StringBuilder sb = new StringBuilder();
		if(strArr==null ||strArr.length==0){
			return "";
		}
		for(String str:strArr){
			sb.append(str+",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	@RequestMapping("/xml")
	@ResponseBody
	public String xmlExample(@RequestBody String xml,HttpServletResponse response){
		System.out.println("接收的xml是");
		System.out.println(xml);
		response.setContentType("application/xml");
		return "<note>"
						+"<to>George</to>"
						+"<from>John</from>"
						+"<heading>Reminder</heading>"
						+"<body>Don't forget the meeting!</body>"
				+"</note>";
	}
	/**
	 * 把对象转字符串没什么问题，但是要把字符串转成对象。要考虑字符串的格式
	 * 是json对象，还是集合。
	 * Spring是怎么判断的，这点令我很好奇
	 * 毕竟FastJSON对于字符串还得区分是对象还是集合，由程序员手动去判断。
	 * Spring自然是不用的，那它是如何判断的呢？
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public String  returnUser(@RequestBody User user){
		System.out.println(user.getPassword());
		return "OK";
	}

	@ResponseBody
	@RequestMapping("/testPrintlnWrite")
	public void testPrintlnWrite(HttpServletResponse response) throws IOException {
		response.getWriter().print("打印流输出数据了");
	}

	
}

