

package org.springframework.security.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 * spring boot 的springsecurity安全配置类
 * @author Mr-hang
 * 功能如下：
 * 	与/ css / **和/ index匹配的请求是完全可访问的
 * 	匹配/ user / **的请求要求用户进行身份验证，?并且必须关联到USER角色	?
 * 使用自定义登录页面和登录失败的页面
 * 启用基于表单的身份验证
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**", "/index").permitAll().antMatchers("/user/**").hasRole("USER").and().formLogin()
		.loginPage("/login").failureUrl("/login-error");	
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}
}