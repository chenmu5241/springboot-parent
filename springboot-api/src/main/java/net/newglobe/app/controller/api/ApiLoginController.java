package net.newglobe.app.controller.api;

import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.newglobe.app.model.SysAccount;
import net.newglobe.app.model.vo.DataResult;
import net.newglobe.app.model.vo.Result;

@Api(tags = "登录接口")
@RestController
@Validated//开启校验
public class ApiLoginController {

	public Logger logger = LoggerFactory.getLogger("ApiLoginController");

	@ApiOperation(value = "登录获取token")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "token", value = "token不需要", required = false, defaultValue = ""),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "username", value = "用户名", required = true, defaultValue = "root"),
			@ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true, defaultValue = "123456") })
	@RequestMapping(value = "api/login", method = RequestMethod.POST)
	public DataResult<String> apiLogin(HttpServletRequest request, HttpServletResponse response, @NotNull(message="用户名不能为空") String username,
			@NotNull(message="密码不能为空") String password) {
		DataResult<String> result = new DataResult<String>();
		try {
			result.setSuccess(true);
			result.setMessage("登录成功!");
		} catch (Exception e) {
		}
		return result;
	}
	
	@RequestMapping(value = "api/isLogin", method = RequestMethod.POST)
	public Result isLogin(String token1) {
		Result result = new Result();
		try {
			result.setSuccess(true);
			result.setMessage("有效token！");
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("验证token失败!");
			logger.error("ApiLoginController.isLogin方法执行遇到异常", e);
		}
		return result;
	}

}