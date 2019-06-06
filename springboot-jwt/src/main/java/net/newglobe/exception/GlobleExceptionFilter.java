package net.newglobe.exception;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.newglobe.app.model.vo.Result;


@ControllerAdvice
public class GlobleExceptionFilter {
	
	public Logger logger = LoggerFactory.getLogger(GlobleExceptionFilter.class);

	@ResponseBody
	@ExceptionHandler(value = java.lang.Exception.class)
	public Result myException(Exception ex) {
		//记录错误
		ex.printStackTrace();
		ex.printStackTrace();
		
		Result result = new Result();
		result.setSuccess(false);
		result.setMessage("遇到异常");
		return result;
	}

	@ResponseBody
	@ExceptionHandler(value = BindException.class)
	public Result BindException(BindException ex) {
		//记录错误
		logger.error("登录异常:", ex);
		ex.printStackTrace();
		
		List<FieldError> fieldErrors = ex.getFieldErrors();
//		for(FieldError f : fieldErrors) {
//			System.out.println(JSON.toJSONString(f));
//			System.out.println(f.getField()+":"+f.getDefaultMessage());
//		}
		Result result = new Result();
		result.setSuccess(false);
		result.setMessage(fieldErrors.get(0).getDefaultMessage());
		return result;
	}

	/**
	 * sql执行异常
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = SQLException.class)
	public Result sqlException(Exception ex) {
		//记录错误
		logger.error("登录异常:", ex);
		ex.printStackTrace();
		
		Result result = new Result();
		result.setSuccess(false);
		result.setMessage("sql执行异常");
		return result;
	}

}
