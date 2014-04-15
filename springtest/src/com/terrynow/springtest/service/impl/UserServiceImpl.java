package com.terrynow.springtest.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.terrynow.springtest.dao.intf.IUserDao;
import com.terrynow.springtest.service.intf.IUserService;

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
		String s = "type1 count: " + userDao.getCountUsers(1);
		return s;
	}

}
