package com.example.Spring4Shell.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new AuthorizationCheckFilter(), BasicAuthenticationFilter.class);
		http.authorizeRequests()
		.regexMatchers("/login/.*")
		.authenticated()
		.and()
		.formLogin()		
		.and()
		.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth // Builder Design Pattern
				.inMemoryAuthentication() // 自訂Runtime時的使用者帳號
				.withUser("admin") 		  // 新增user
				.password("{noop}ew5SBTXWRREMXgQ8pbKpMGEUm5RbTTEKHdFZEVGnfm7RHypEcSN7GBMh9kGp5S3z") // 指定密碼
				.roles("ADMIN");
	}
}