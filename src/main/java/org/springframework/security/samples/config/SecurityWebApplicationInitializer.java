package org.springframework.security.samples.config;

import org.springframework.security.web.context.*;
/**
 * 将SecurityConfig注册起来
 * @author Mr-hang
 *功能：
 *1：添加springSecurityFilterChain过滤器
 *2：配置一个容器初始化的监听器：SecurityConfig
 */
public class SecurityWebApplicationInitializer
      extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebApplicationInitializer() {
        super(SecurityConfig.class);
    }
}