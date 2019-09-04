package net.newglobe.exception;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import net.newglobe.app.model.vo.ErrorResult;

@ControllerAdvice
public class GlobleExceptionFilter {

	public Logger logger = LoggerFactory.getLogger(GlobleExceptionFilter.class);

	@ResponseBody
	@ExceptionHandler(value = java.lang.Exception.class)
	public ErrorResult myException(Exception ex) {
		// 记录错误
		logger.error("参数绑定异常:", ex);
		ex.printStackTrace();

		ErrorResult result = new ErrorResult();
		result.setSuccess(false);
		result.setMessage("遇到异常,请重试或联系管理员");
		result.setErrorDetail(ex.getMessage());
		return result;
	}

	/**
	 * validation异常
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = BindException.class)
	public ErrorResult BindException(BindException ex) {
		// 记录错误
		logger.error("参数绑定异常:", ex);
		ex.printStackTrace();

		List<FieldError> fieldErrors = ex.getFieldErrors();
		List<String> list = new LinkedList<String>();
		for (FieldError f : fieldErrors) {
			list.add(f.getDefaultMessage());
		}
		ErrorResult result = new ErrorResult();
		result.setSuccess(false);
		result.setMessage(StringUtils.join(list, ","));
		result.setErrorDetail(ex.getMessage());
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
	public ErrorResult sqlException(Exception ex) {
		ex.printStackTrace();
		ErrorResult result = new ErrorResult();
		result.setSuccess(false);
		result.setMessage("sql执行异常");
		result.setErrorDetail(ex.getMessage());
		return result;
	}

	/**
	 * 处理controller上基本参数的验证
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = ValidationException.class)
	public ErrorResult validationException(Exception ex) {
		// 记录错误
		ex.printStackTrace();
		logger.error("参数格式不符合要求:", ex);

		ValidationException ex2 = (ValidationException) ex;
		String localizedMessage = ex2.getLocalizedMessage();
		String[] messages = localizedMessage.split(",");
		String[] resultStr = new String[messages.length];
		for (int i = 0; i < messages.length; i++) {
			resultStr[0] = messages[i].split(":")[1];
		}
		ErrorResult result = new ErrorResult();
		result.setSuccess(false);
		result.setMessage(StringUtils.join(resultStr, ","));
		result.setErrorDetail(localizedMessage);
		return result;
	}

}
