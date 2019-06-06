package net.newglobe.shiro;
/**
 * 本地用户信息过滤器
 *
 * @author weiller 2018年1月23日10:27:30
 */

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalUserInfoFilter implements Filter {
 
    Logger logger =  LoggerFactory.getLogger(LocalUserInfoFilter.class);
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request_ = (HttpServletRequest)request;
        String loginName = CASUtil.getAccountNameFromCas(request_);
        if(StringUtils.isNotEmpty(loginName)){
            logger.info("访问者 ：" +loginName);
            request_.getSession().setAttribute("loginName", loginName);
        }
 
        chain.doFilter(request, response);
    }
 
    @Override
    public void destroy() {
 
    }
}