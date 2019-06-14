package net.newglobe.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "1、登录相关接口")
@RestController
public class LoginApiController {

	public Logger logger = LoggerFactory.getLogger("LoginController");

	@RequestMapping("api/login")
	public String login(String name, String password) {
		
		return null;
	}
}