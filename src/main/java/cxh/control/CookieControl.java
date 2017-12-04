package cxh.control;

import java.util.Enumeration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.Header;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/eurekaclient")
public class CookieControl {

	@ResponseBody
	@RequestMapping("/showCookie")
	public String showCookie(HttpServletRequest request){
		System.out.println("接收请求");
		Cookie[] cookies = request.getCookies();
		if (cookies !=null) {
			for (Cookie cookie : cookies) {
				System.out.println("cookie的key是" + cookie.getName());
				System.out.println("cookie的value是" + cookie.getValue());
			} 
		}
		return "cookie已经显示在控制台，赶紧去看看吧";
	}
}
