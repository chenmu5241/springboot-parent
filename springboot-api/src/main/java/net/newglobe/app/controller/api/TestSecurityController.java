package net.newglobe.app.controller.api;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.swagger.annotations.ApiOperation;
import net.newglobe.app.model.vo.Result;

@Api(tags = "登录接口")
@RestController
@Validated // 开启校验
public class TestSecurityController {

	public Logger logger = LoggerFactory.getLogger(TestSecurityController.class);

//	@PreAuthorize("hasAuthority('read')")//权限
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
//	@PreAuthorize("hasAnyAuthority('read')")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiOperation(value = "测试授权")
	@RequestMapping(value = "api/testAuthorite", method = RequestMethod.POST)
	public Result testAuthorite(String token1) {
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
	@PreAuthorize("hasRole('ROLE_VIP')")
	@ApiOperation(value = "测试新增的权限")
	@RequestMapping(value = "api/newAuthorite", method = RequestMethod.POST)
	public Result testNewAuthorite(String token1) {
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

	@ApiOperation(value = "增加VIP角色")
	@RequestMapping(value = "api/updateAuthority", method = RequestMethod.POST)
	public Result updateAuthority(String token1) {
		Result result = new Result();
		try {
			// 得到当前的认证信息
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			// 生成当前的所有授权
			ArrayList<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
			// 添加 ROLE_VIP 授权
			updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_VIP"));
			// 生成新的认证信息
			Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
					updatedAuthorities);
			// 重置认证信息
			SecurityContextHolder.getContext().setAuthentication(newAuth);

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