package net.newglobe.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import net.newglobe.app.model.SysAccount;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	@Autowired
	private PersistentTokenRepository persistentTokenRepository;
//	@Qualifier("dataSourceMysql")
//	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PersistentTokenRepository persistentTokenRepository(@Qualifier("dataSourceMysql") DataSource dataSource) {
		JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true); // 系统在启动的时候生成“记住我”的数据表（只能使用一次）
		return tokenRepository;
	}
	
/*	 @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new MyUserDetailsService());
    }*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		validateCodeFilter.setAuthenctiationFailureHandler(myAuthenticationFailureHandler);

//		http.httpBasic();
		http
//		.anonymous().disable()//关闭匿名用户
		.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class).formLogin()
				.loginPage("/login")// 登录页
				.loginProcessingUrl("/loginSubmit")// 提交请求,这个只是一个标记，实际controller不必存在
				.successHandler(myAuthenticationSuccessHandler)// 自定义成功处理
				.failureHandler(myAuthenticationFailureHandler)// 自定义失败处理
				.and()
				.rememberMe()
				.tokenRepository(persistentTokenRepository)
				.tokenValiditySeconds(40) // 设置Token的有效时间
				.userDetailsService(userDetailsService)//使用userDetailsService用Token从数据库中获取用户自动登录

				.and().authorizeRequests()// 授权
				.antMatchers("/api/**","/login", "/static/**", "/css/**", "/getCode").permitAll().anyRequest()// 所有请求
				.authenticated()// 都需要身份认证
				.and().csrf().disable();// 跨站请求伪造
	}

}