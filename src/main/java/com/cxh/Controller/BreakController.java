package com.cxh.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxh.breaker.StoreIntegration;

@Controller
public class BreakController {
	
	@Autowired
	private StoreIntegration server;
	
	@RequestMapping("/testBreak")
	@ResponseBody
	public Object  testBreak(){
		return server.getStore(null);
	}
}
