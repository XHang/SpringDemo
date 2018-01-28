/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * springsecurity java配置
 * @author Mr-hang
 *
 */
@EnableWebSecurity
//
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	// @formatter:off
	@Autowired
	public void configureGlobal(
			AuthenticationManagerBuilder auth) throws Exception {
		auth
			.inMemoryAuthentication()
				.withUser("user").password("password").roles("USER");
	}
	// @formatter:on

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		super.configure(http);
		http
		//允许匿名用户可以访问resources/**相匹配的资源
        .authorizeRequests().antMatchers("resources/**").permitAll()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            //允许匿名用户可以访问/login页面以及login/error
            .permitAll()
          //允许匿名用户可以访问/logout页面
            .and().logout().permitAll();
	}
	//loginPage("/login");做了什么事？
	// 1:当需要认证请求时，将浏览器重定向到login页面
	// 2:当身份验证失败时，重定向到 /login?error  这是一个默认值，因为我们并没有指定验证失败应该跳转的页面地址
	// 3: 成功注销后，可以跳转到 /login?logout 地址，这也是一个默认值
	//以上三种重定向，都可以由用户自己自定义一个登陆页面来使用
	
	
	
}
