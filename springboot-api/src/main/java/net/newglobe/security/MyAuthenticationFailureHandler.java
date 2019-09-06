package net.newglobe.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import net.newglobe.app.model.vo.ErrorResult;
import net.newglobe.util.RequestUtil;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// 异步请求
		if (RequestUtil.isAjaxRequest(request)) {
			ErrorResult result = new ErrorResult();
			result.setSuccess(false);
			result.setMessage(exception.getMessage());
			response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(result));
		} else {// 页面提交方式
			request.getSession().setAttribute("username", request.getParameter("username"));
			request.getSession().setAttribute("password", request.getParameter("password"));
			request.getSession().setAttribute("loginErrorMsg", exception.getMessage());
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
