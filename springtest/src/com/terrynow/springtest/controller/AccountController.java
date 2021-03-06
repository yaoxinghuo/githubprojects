package com.terrynow.springtest.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.terrynow.springtest.entity.User;
import com.terrynow.springtest.service.intf.IUserService;
import com.terrynow.springtest.util.JsonResult;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Apr 14, 2014 5:26:37 PM
 * @description
 */
@Controller
@RequestMapping("/account")
public class AccountController {

	@Resource(name = "userService")
	private IUserService userService;

	@RequestMapping(value = "/accounts")
	public @ResponseBody JsonResult listAccounts(
			@RequestParam(value = "start", required = true) int start,
			@RequestParam(value = "limit", required = true) int limit) {
		return userService.listAccounts(start, limit);
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public ModelAndView findAllAccounts() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("account");
		mav.addObject("someText", userService.test());
		return mav;
	}

	@RequestMapping("/test")
	public void test(HttpServletResponse response) {
		String result = "{\"result\":\"error\"}";
		response.setContentType("application/json");
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/test2")
	public @ResponseBody JsonResult test2() {
		JsonResult jr = new JsonResult(true, "测试");
		jr.addKeyValue("abc", 123);
		return jr;
	}

	@RequestMapping("/toAddUser")
	public String toAddUser() {
		return "/account";
	}

	/**
	 * 
	 * @author Terry
	 * @date Apr 15, 2014 1:46:45 PM
	 * @description 这是redirect的例子
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/addUser")
	public String addUser(User user, HttpServletRequest request) {
		return "redirect:/user/getAllUser";
	}

	/**
	 * 
	 * @author Terry
	 * @date Apr 15, 2014 10:44:57 AM
	 * @description 
	 *              在SpringMVC中可以在Controller的某个方法上加@ResponseBody注解，表示该方法的返回结果直接写入HTTP
	 *              response body中。
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/testjson")
	public @ResponseBody String testjson(HttpServletRequest request) {
		return "ResponseBody";
	}

	/**
	 * 
	 * @author Terry
	 * @date Apr 15, 2014 1:45:30 PM
	 * @description 加了jackson依赖包后，spring自动检测有这个包，那么输入和输出的Entity都会转成json
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/userjson")
	public @ResponseBody String userjson(@RequestBody User user) {
		StringBuilder sb = new StringBuilder();
		sb.append("username:" + user.getUsername());
		sb.append(",password:" + user.getPassword());
		System.out.println("userinfo: " + sb.toString());
		return sb.toString();
	}

	@RequestMapping(value = "/userjson2/{no}", method = RequestMethod.GET)
	public @ResponseBody User userjson2(@PathVariable String no) {
		User user = userService.getUserByNo(no);
		return user;
	}

	@RequestMapping("/greeting")
	public @ResponseBody String greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
		return "Hello, " + name;
	}
}
