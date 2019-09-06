package net.newglobe.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

// OncePerRequestFilter : 保证过滤器只被调用一次
public class ValidateCodeFilter extends OncePerRequestFilter {

	private AuthenticationFailureHandler authenctiationFailureHandler;

	// 过滤 逻辑
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println(request.getRequestURI());
		// 是一个登陆请求
		if (StringUtils.equals("/springboot-api/loginSubmit", request.getRequestURI())) {
			try {
				validate(new ServletWebRequest(request));
			} catch (ValidateCodeException e) {
				// 有异常就返回自定义失败处理器
				authenctiationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		// 不是一个登录请求，不做校验 直接通过
		filterChain.doFilter(request, response);
	}

	private void validate(ServletWebRequest request) throws ServletRequestBindingException {
		HttpServletRequest httpServletRequest = request.getRequest();
		HttpSession session = httpServletRequest.getSession();
		String randomcode = request.getParameter("randomcode");
		// 从session中取出验证码
		String validateCode = (String) session.getAttribute("randomcode");
		if (randomcode != null && validateCode != null) {
			if (!randomcode.equalsIgnoreCase(validateCode)) {
				throw new ValidateCodeException("验证码不匹配");
			}
		} else {
			throw new ValidateCodeException("验证码不匹配");
		}
	}

	public AuthenticationFailureHandler getAuthenctiationFailureHandler() {
		return authenctiationFailureHandler;
	}

	public void setAuthenctiationFailureHandler(AuthenticationFailureHandler authenctiationFailureHandler) {
		this.authenctiationFailureHandler = authenctiationFailureHandler;
	}

}
