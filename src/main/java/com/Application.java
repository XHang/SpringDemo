package com;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

/**
 * SpringBoot启动程序，该程序使用的配置值将从远程的配置服务器获取
 * @author Administrator
 *
 */
@Configuration
@EnableAutoConfiguration
@RestController
public class Application {
	
}
