package net.newglobe.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.newglobe.app.model.vo.Result;

@Controller
@RequestMapping("error")
public class ErrorController {
	public Logger logger = LoggerFactory.getLogger(ErrorController.class);

	@RequestMapping(value = "{errorCode}", produces = "html/text")
	public String htmlError(HttpServletRequest request, @PathVariable String errorCode) {
		return "error/" + errorCode;
	}

	@RequestMapping("{errorCode}")
	@ResponseBody
	public Result index(HttpServletRequest request, @PathVariable String errorCode) {
		Result result = new Result();
		result.setSuccess(false);
		result.setMessage(errorCode + "错误");
		return result;
	}

	public static void main(String[] args) {
		long sum = 1;
		for (int i = 1; i < 10; i++) {
			sum *= i;
		}
		System.out.println(sum);
	}

}
