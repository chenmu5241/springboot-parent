package net.newglobe.app.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("demo")
@Controller
public class DemoController {
	
	@RequestMapping({"{path}"})
	public String suofang(HttpServletRequest request,@PathVariable String path) {
		return "app/demo/"+path;
	}
	
	@RequestMapping("validate")
	public void validate() {
		
		
	}
	
	
	
	//富文本编辑器
//	@RequestMapping(value="ueditor")
//	public String ajaxParam1()  {
//		return "app/demo/ueditor";
//	}
	
	/** 
	  * 微信公众平台 成为开发者验证入口 
	  *  
	  * @param request 
	  *            the request send by the client to the server 
	  * @param response 
	  *            the response send by the server to the client 
	  * @throws ServletException 
	  *             if an error occurred 
	  * @throws IOException 
	  *             if an error occurred 
	  */  
	@RequestMapping(value="weixin")
	@ResponseBody
	 public void weixin(HttpServletRequest request, HttpServletResponse response)  
	         throws ServletException, IOException {  
	  
	     // 微信加密签名  
	     String signature = request.getParameter("signature");  
	     // 随机字符串  
	     String echostr = request.getParameter("echostr");  
	     // 时间戳  
	     String timestamp = request.getParameter("timestamp");  
	     // 随机数  
	     String nonce = request.getParameter("nonce");  
	     String token = "diaodongmei";
	     String[] str = { token, timestamp, nonce };  
	     Arrays.sort(str); // 字典序排序  
	     String bigStr = str[0] + str[1] + str[2];  
	     // SHA1加密  
//	     String digest = new SHA1().getDigestOfString(bigStr.getBytes())  
//	             .toLowerCase();  
//	  
//	     // 确认请求来至微信  
//	     if (digest.equals(signature)) {
//	         response.getWriter().print(echostr);  
//	     }  
	 }  
}
