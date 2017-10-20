package com.Springmvc.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 字符乱码处理Request包装类
 * @author Administrator
 *
 */
public class CharEcnodeRequest  extends HttpServletRequestWrapper{
	
	public static final String METHOD_GET = "get";
	
	public static final String METHOD_POST = "post";
	
	private String charSet;
	public CharEcnodeRequest(HttpServletRequest request,String charSet) {
		super(request);
		this.charSet = charSet;
	}
	
	@Override
	public String getParameter(String name) {
		String method = super.getMethod();
		if(METHOD_GET.equalsIgnoreCase(method)){
			String value = super.getParameter(name);
			super.getParameterValues(name);
			if(value == null){
				return null;
			}
			try {
				return new String(value.getBytes("ISO-8859-1"),charSet);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("CharSet No support,please check init parameter in web.xml");
			}
		}else if(METHOD_POST.equals(method)){
			try {
				super.setCharacterEncoding(charSet);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("CharSet No support,please check init parameter in web.xml");
			}
		}
		return super.getParameter(name);
	}

	@Override
	public String[] getParameterValues(String name) {
		String method = super.getMethod();
		if(METHOD_GET.equalsIgnoreCase(method)){
			String[] values = super.getParameterValues(name);
			if(values == null ){
				return null;
			}
			try {
				for(int i=0;i<values.length;i++){
					values[i] = new String(values[i].getBytes("ISO-8859-1"),charSet);
				}
				return values;
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("CharSet No support,please check init parameter in web.xml");
			}
		}else if(METHOD_POST.equals(method)){
			try {
				super.setCharacterEncoding(charSet);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("CharSet No support,please check init parameter in web.xml");
			}
		}
		return super.getParameterValues(name);
	}

}
