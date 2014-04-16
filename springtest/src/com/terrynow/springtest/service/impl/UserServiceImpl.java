package com.terrynow.springtest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terrynow.springtest.dao.intf.IUserDao;
import com.terrynow.springtest.entity.User;
import com.terrynow.springtest.service.intf.IUserService;
import com.terrynow.springtest.util.JsonResult;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Apr 14, 2014 11:10:34 PM
 * @description
 */
@Service("userService")
@Transactional(readOnly = false)
@Repository
public class UserServiceImpl implements IUserService {

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Override
	public String test() {
		String s = "user count: " + userDao.getCountUsers();
		return s;
	}

	@Override
	public User getUserByNo(String no) {
		return userDao.getUserByNo(no);
	}

	@Override
	public JsonResult listAccounts(int start, int limit) {
		long total = userDao.getCountUsers();
		List<User> users = userDao.getUsers(start, limit);
		JsonResult jr = new JsonResult(true,"1111");
		jr.addKeyValue("results", total);
		jr.addKeyValue("rows", users);
		return jr;
	}

}
