package net.newglobe.app.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
public class LoginController {

	public Logger logger = LoggerFactory.getLogger("LoginController");
	
	@Autowired
    private DefaultKaptcha captchaProducer;
	@Autowired
    private RedisTemplate<String,Object> redisTemplate;

	@RequestMapping(value = "/login")
	public String login(HttpServletResponse response, HttpServletRequest request,Authentication authentication) {
		System.out.println(redisTemplate);
		
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		
		if(authentication!=null) {
			Object obj = authentication.getPrincipal();
			System.out.println(obj);
			return "redirect:/index";
		}
		return "login";
	}
	@RequestMapping(value = "/loginSubmit")
	public String loginSubmit(HttpServletResponse response, HttpServletRequest request,Authentication authentication) {
		return "redirect:/login";
	}
	
	/*@RequestMapping(value = "/loginSubmit")
	public String loginSubmit(HttpServletResponse response, HttpServletRequest request) {
		return "loginSubmit";
	}*/
	
	
	/**
     * 获取验证码 的 请求路径
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    @RequestMapping("/getCode")  
    public void defaultKaptcha(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{  
            byte[] captchaChallengeAsJpeg = null;    
             ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();    
             try {    
             //生产验证码字符串并保存到session中  
             String createText = captchaProducer.createText();  
             httpServletRequest.getSession().setAttribute("randomcode", createText);  
             //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中  
             BufferedImage challenge = captchaProducer.createImage(createText);  
             ImageIO.write(challenge, "jpg", jpegOutputStream);  
             } catch (IllegalArgumentException e) {    
                 httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);    
                 return;    
             }   

             //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组  
             captchaChallengeAsJpeg = jpegOutputStream.toByteArray();    
             httpServletResponse.setHeader("Cache-Control", "no-store");    
             httpServletResponse.setHeader("Pragma", "no-cache");    
             httpServletResponse.setDateHeader("Expires", 0);    
             httpServletResponse.setContentType("image/jpeg");    
             ServletOutputStream responseOutputStream =    
                     httpServletResponse.getOutputStream();    
             responseOutputStream.write(captchaChallengeAsJpeg);    
             responseOutputStream.flush();    
             responseOutputStream.close();    
    }  

}