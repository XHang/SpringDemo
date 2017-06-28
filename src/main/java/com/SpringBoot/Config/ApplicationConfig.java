package com.SpringBoot.Config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * boot内置Tomcat的配置类
 * @author Administrator
 *
 */
@Configuration
public class ApplicationConfig  implements EmbeddedServletContainerCustomizer{

	@Override
	public void customize(ConfigurableEmbeddedServletContainer config) {
		//设置Tomcat的启动端口号为10086
		config.setPort(10086);
	}

}
