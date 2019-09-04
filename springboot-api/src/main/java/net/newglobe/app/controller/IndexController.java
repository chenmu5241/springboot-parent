package net.newglobe.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import net.newglobe.app.model.SysAccount;

@Controller
public class IndexController {
	public Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	
	@RequestMapping({"index",""})
	public String index(HttpServletRequest request) {
		SysAccount sysAccount = (SysAccount) SecurityUtils.getSubject().getPrincipal();
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		httpRequest.getSession().setAttribute("account", sysAccount);
		return "index";
	}
}
