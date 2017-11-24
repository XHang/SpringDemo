package com.Springvc.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.StringHttpMessageConverter;

/**
 * 自定义一个httpConverter转换器
 * 仅MediaType是application/xml才可以转换
 * @author Mr-hang
 *
 */
public class CustomizeConverter  implements HttpMessageConverter<String> {
	

	/**
	 * 聚合手段避免继承带来的不灵活性
	 */
	private StringHttpMessageConverter converter;
	
	/**
	 * 创建转换器对象
	 * @param charset编码
	 */
	public CustomizeConverter(String charset){
		converter = new StringHttpMessageConverter(Charset.forName(charset));
	}
	
	/**
	 * 判断请求的数据能否转换成java类型
	 * @param clazz 请求数据要转成什么java类型呢？
	 * @param 该请求的Content-Type是什么呢？
	 */
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		if(mediaType.equals(MediaType.APPLICATION_XML)){
			return true;
		}
		return false;
	}

	/**
	 * 是否可以将输出数据转换成指定输出内容
	 * @param 输出数据的类型
	 * @param 该请求的 Accept
	 */
	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if(mediaType.equals(MediaType.APPLICATION_XML)){
			return true;
		}
		return false;
	}

	/**
	 * 该转换器支持哪些MediaTypes
	 */
	@Override
	public List<MediaType> getSupportedMediaTypes() {
		List<MediaType> list = new ArrayList<MediaType>();
		list.add(MediaType.APPLICATION_XML);
		return list;
	}

	/**
	 * 将请求数据转换成java对象
	 * @param inputMessage 请求数据包装类
	 * @param 要转换的java对象类型
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String read( Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
		return converter.read(clazz, inputMessage);
		/* String xml = inputStreamConversionString( inputMessage.getBody(),charset);
		 return xml;*/
	}

	/**
	 * 将返回的数据转换成需要的类型传给客户端
	 * canWrite必须已经返回true
	 * @param t 返回的数据，未处理
	 * @param contentType 客户端请求头的Accept
	 * @param outputMessage 将处理的数据设到里面的body
	 */
	@Override
	public void write(String t, MediaType contentType, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		converter.write( t, contentType, outputMessage);
		
	}
	
	   /**
	    * 将字节流转换成字符串
	    * @param in 需要转换的字节流
	    * @param characterSet字符编码
	    * @return 转换完毕的字符串
	    * @throws IOException
	    */
	      @SuppressWarnings("unused")
		private   String inputStreamConversionString(InputStream in,String characterSet) throws IOException{
			   BufferedReader read = new BufferedReader(new InputStreamReader(in,characterSet));
			   StringBuilder sb = new StringBuilder();
			   String line = "";
			   while((line = read.readLine())  !=  null){
				   sb.append(line);
			   }
			   return sb.toString();
		}


}
