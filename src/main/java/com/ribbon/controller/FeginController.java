package com.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ribbon.server.UserClient;

@Controller
public class FeginController {
	
	@Autowired
	UserClient client;
	
	@ResponseBody
	@RequestMapping("/getString")
	public String getString(String xml) {
		return  client.getXML(xml);
	}
}
