package com.Springmvc.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CustomResponseWapper extends HttpServletResponseWrapper{

	
	private CustomOutputStream out;
	
	private PrintWriter writer;
	
	
	public CustomResponseWapper(HttpServletResponse response) {
		super(response);
		out = new CustomOutputStream();
	}
	
	

	
	
	

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return this.out;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return super.getWriter();
	}
}

class CustomOutputStream extends ServletOutputStream{

	//字节输出流，充当容器
	ByteArrayOutputStream container = new ByteArrayOutputStream();
	
	//将其他调用方的数据写到容器里面
	@Override
	public void write(int b) throws IOException {
		container.write(b);
	}
	
}


