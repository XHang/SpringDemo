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

	//当第三方调用自定义的request方法时，在这里面进行解码
	@Override
	public String getParameter(String name) {
		String method = super.getMethod();
		//如果是get请求，则需要先取出数据，再解码返回
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
			//如果是post请求就很简单了，只需要设置setCharacterEncoding即可
		}else if(METHOD_POST.equals(method)){
			try {
				super.setCharacterEncoding(charSet);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("CharSet No support,please check init parameter in web.xml");
			}
		}
		return super.getParameter(name);
	}

	//如果第三方调用的是getParameterValues方法，也需要解码，其解码的步骤和上面是一样的
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
