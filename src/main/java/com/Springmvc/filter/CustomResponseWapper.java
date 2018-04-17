package com.Springmvc.filter;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * response的包装类，实现该包装类的目的，是为了能够取出response输出数据里面的内容
 * 其原理是通过覆写response的两个重要的对象，printlnWriter和OutputStream。
 * 自定义我们自己的打印流和输出流。
 * 最终覆写里面的写入方法，当调用者调用写入方法时，将数据存在两个流里面的一个容器中里面。
 *
 * 不过要想生效，还需要将这个响应对象在过滤链条中，偷梁换柱，把原先的response对象，替换为该类对象。
 * 方可大功告成
 */
public class CustomResponseWapper extends HttpServletResponseWrapper{


	private CustomOutputStream out;
	
	private PrintWriter writer;

    /**
     * 从使用上的角度来讲，其实没必要传response
     * 但是response的包装类默认必须提供这个构造方法，所以认栽吧。
     * @param response
     */
	public CustomResponseWapper(HttpServletResponse response) {
		super(response);
		out = new CustomOutputStream();
		writer = new PrintWriter(out);
	}
	
	

	
	
	

	@Override
	public ServletOutputStream getOutputStream()  {
		return this.out;
	}

	@Override
	public PrintWriter getWriter()  {
		return this.writer;
	}
}

class CustomOutputStream extends ServletOutputStream{

	//字节输出流，充当容器
	private ByteArrayOutputStream container = new ByteArrayOutputStream();
	
	//将其他调用方的数据写到容器里面
	@Override
	public void write(int b)  {
		container.write(b);
	}

	/**
	 * 获取该输入出流里面的字节数组
	 * 其实只是拿容器里面的东西
	 * @return byte[] 返回的字节数组
	 */
	public byte[] getContent(){
		return this.container.toByteArray();
	}

	
}


