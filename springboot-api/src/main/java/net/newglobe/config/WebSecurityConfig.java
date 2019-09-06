package net.newglobe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.newglobe.security.MyAuthenticationFailureHandler;
import net.newglobe.security.MyAuthenticationSuccessHandler;
import net.newglobe.security.ValidateCodeFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenctiationFailureHandler(myAuthenticationFailureHandler);
        
//		http.httpBasic();
		http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
		.formLogin()
				.loginPage("/login")// 登录页
				.loginProcessingUrl("/loginSubmit")//提交请求
				.failureUrl("/login?error=true")
				.successForwardUrl("/index")
				.defaultSuccessUrl("/index")//不配置会默认跳转到请求之前的页面
				.successHandler(myAuthenticationSuccessHandler)//自定义成功处理
				.failureHandler(myAuthenticationFailureHandler)//自定义失败处理
				
				.and().authorizeRequests()// 授权
				.antMatchers("/login","/static/**","/css/**","/getCode")
				.permitAll()
				.anyRequest()// 所有请求
				.authenticated()// 都需要身份认证
				.and().csrf().disable();//跨站请求伪造
	}

}