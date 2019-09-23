package net.newglobe.app.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.newglobe.app.model.SysAccount;

@Controller
public class IndexController {
	public Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	
	@RequestMapping({"index",""})
	public String index(HttpServletRequest request, Authentication authentication) {
		//1
		SysAccount account = (SysAccount)authentication.getPrincipal();
		System.out.println(account);
		//2
		SysAccount zz = (SysAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(zz);
		//3获取登录用户
		Principal userPrincipal = request.getUserPrincipal();
		if(userPrincipal!=null) {
			Authentication principalzz = (Authentication)userPrincipal;
			System.out.println(principalzz.getPrincipal());
		}
		return "index";
	}
}
 