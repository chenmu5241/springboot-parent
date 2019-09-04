package net.newglobe.app.controller.api;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
			Subject subject = SecurityUtils.getSubject();
			
			password = Base64.getEncoder().encodeToString(password.getBytes());//密码编码
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			subject.login(token);// 如果登录成功才会继续执行下面的操作
			// 设置token
			String tokenStr = "";// 设置token

			//获取用户权限
			SysAccount sysAccount = (SysAccount) SecurityUtils.getSubject().getPrincipal();
			result.setData(tokenStr);
			result.setSuccess(true);
			result.setMessage("登录成功!");
		} catch (Exception e) {
			if (UnknownAccountException.class.getName().equals(e.getClass().getName())) {
				// 最终会抛给异常处理器
				result.setMessage("账号不存在");
			} else if (IncorrectCredentialsException.class.getName().equals(e.getClass().getName())) {
				result.setMessage("用户名/密码错误");
			} else if ("randomCodeError".equals(e.getClass().getName())) {
				result.setMessage("验证码错误");
			} else if ("longTimeSession".equals(e.getClass().getName())) {
				result.setMessage("session无效刷新重试");
			} else if (NullPointerException.class.getName().equals(e.getClass().getName())) {
				result.setMessage("用户名密码不能为空");
			} else {
				result.setMessage("其他错误");
			}
			result.setSuccess(false);
			// logger.error("登录异常:", e);
		}
		return result;
	}
	
	@RequestMapping(value = "api/isLogin", method = RequestMethod.POST)
	public Result isLogin(@NotNull(message="token不能为空") String token) {
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