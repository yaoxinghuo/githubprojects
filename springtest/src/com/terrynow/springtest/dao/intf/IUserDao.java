package com.terrynow.springtest.dao.intf;

import java.util.List;

import com.terrynow.springtest.entity.User;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Mar 19, 2014 3:39:26 PM
 * @description
 */
public interface IUserDao {
	public User getUserByNo(String no);

	public long getCountUsers();

	public List<User> getUsers(int start, int limit);
}
