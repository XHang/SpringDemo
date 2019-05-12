package org.springframework.security.samples.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.*;
/**
 * 该类是一个springsecurity配置类
 * @author Mr-hang
 * 它可以实现以下功能：
 * 1：要求对应用程序中的每个URL进行身份验证,如果没登陆，跳到登陆页面
 * 2：生成一个登陆的表单(简单的html表单)
 * 3：允许使用用户名和密码来作为基本验证
 * 4：允许用户注销
 * 5：CSRF攻击预防
 * 。。。。
 *
 */
@EnableWebSecurity
public class SecurityConfig {

	//@Autowired写在这里其实跟写在set方法所起的作用是一样的，就是为方法的参数自动注入对象
	//configureGlobal方法名可以随意取
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //创建一个虚拟的用户，用户名是user12。密码是password。用户组是USER
        auth.inMemoryAuthentication().withUser("user12").password("password").roles("USER");
    }
}
