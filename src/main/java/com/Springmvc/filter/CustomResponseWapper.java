package com.Springmvc.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

	/**
	 * 重点是实现输出流，调用方想用输出流写东西的话，就可以拦截了
	 */
	private CustomOutputStream out;

	//response里面维护一个字节输出流，充当容器，打印流和字节输出流都把数据输入到该容器里面
	private ByteArrayOutputStream container;

	/**
	 * 重点是实现打印流，这样调用方想用打印流写东西的话，就会输入到我们的容器里面了
	 * 但是我们不知道调用方想用打印流哪一个方法写入数据，目前只能祈祷调用方用printf(String str)
	 * 的方法写数据了
	 * 其实要解决也简单吧，打印流无论写入什么数据终究会调用某个基本方法。
	 * 只要魔改它就行了
	 */
	private CustomPrintWriter printWriter;


    /**
     * 从使用上的角度来讲，其实没必要传response
     * 但是response的包装类默认必须提供这个构造方法，所以认栽吧。
     * @param response
     */
	public CustomResponseWapper(HttpServletResponse response) {
		super(response);
		container = new ByteArrayOutputStream();
		out = new CustomOutputStream(container);
		printWriter = new CustomPrintWriter(container);

	}

	@Override
	public PrintWriter getWriter()  {
		return this.printWriter;
	}

	@Override
	public ServletOutputStream getOutputStream()  {
		return this.out;
	}

	public byte[] getContent(){
		return this.container.toByteArray();
	}

}

class CustomOutputStream extends ServletOutputStream{

	private ByteArrayOutputStream container;
	public CustomOutputStream(ByteArrayOutputStream out){
		container = out;
	}
	//将其他调用方的数据写到容器里面
	@Override
	public void write(int b)  {
		container.write(b);
	}
}


class CustomPrintWriter extends PrintWriter{

	private ByteArrayOutputStream out;
	@Override
	public void print(String s) {
		try {
			out.write(s.getBytes("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public CustomPrintWriter(ByteArrayOutputStream out){
		super(out);
		this.out = out;
	}
}