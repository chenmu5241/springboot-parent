package net.newglobe.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import net.newglobe.app.model.vo.Result;
import net.newglobe.util.RequestUtil;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// 异步请求
		if (RequestUtil.isAjaxRequest(request)) {
			Result result = new Result();
			result.setSuccess(false);
			result.setMessage("登录成功");
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(result));
		} else {// 页面提交方式
			//成功之后跳转到首页
//			response.sendRedirect("/springboot-api/index");
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}

}
