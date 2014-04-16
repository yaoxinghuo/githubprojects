package com.terrynow.springtest.service.intf;

import com.terrynow.springtest.entity.User;
import com.terrynow.springtest.util.JsonResult;


/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Mar 19, 2014 3:36:48 PM
 * @description
 */
public interface IUserService {
	public String test();
	
	public User getUserByNo(String no);
	
	public JsonResult listAccounts(int start, int limit);
}
