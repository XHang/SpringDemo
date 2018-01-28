package org.springframework.security.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.*;

/**
 * springsecurity 配置类
 * 该配置类完成了一下功能：
 * 要求对应用程序中的每个URL进行身份验证
	为您生成一个登录表单
	允许与用户名 用户和密码 口令与基于表单的身份验证来验证
	允许用户注销
	CSRF攻击预防
	会话固定保护
	安全头集成
	与Servlet API方法集成
 * @author Mr-hang
 *
 */
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
}