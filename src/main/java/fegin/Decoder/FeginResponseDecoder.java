package fegin.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

/**
 * 本来应该实现Decoder
 * ResponseEntityDecoder 也是有实现Decoder的
 * 所以这样也行
 * @author Administrator
 *
 */
public class FeginResponseDecoder  extends ResponseEntityDecoder {

	public FeginResponseDecoder(Decoder decoder) {
		super(decoder);
	}
	
	/**
	 * @param response 这个的话，是服务器返回的响应数据
	 * @param type Fegin接口的返回值类型
	 */
	@Override
	public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
		return super.decode(response, type);
	}

}
