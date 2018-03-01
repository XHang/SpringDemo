package fegin.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fegin.Decoder.FeginResponseDecoder;
import fegin.interceptor.FeginRequestInterceptor;
import feign.RequestInterceptor;
import feign.codec.Decoder;

@Configuration
public class FeginConfig {
	
	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;
	
	@Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return new FeginRequestInterceptor();
    }
	@Bean
	public Decoder feignDecoder(){
		return new FeginResponseDecoder(new SpringDecoder(this.messageConverters));
	}
}
