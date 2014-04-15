package com.terrynow.springtest.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.servlet.KaptchaExtend;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Apr 15, 2014 10:12:02 AM
 * @description
 */
@Controller
public class KaptchaController extends KaptchaExtend {

	@RequestMapping(value = "/captcha.jpg", method = RequestMethod.GET)
	public void captcha(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.captcha(req, resp);
	}
}
