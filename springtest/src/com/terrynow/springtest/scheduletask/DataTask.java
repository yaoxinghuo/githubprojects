package com.terrynow.springtest.scheduletask;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.terrynow.springtest.entity.User;
import com.terrynow.springtest.service.intf.IUserService;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Jun 4, 2013 1:54:42 PM
 */

@Component("dataTask")
public class DataTask {

	private static Log log = LogFactory.getLog(DataTask.class);

	@Resource(name = "userService")
	private IUserService userService;

	public void doDataTask() {
		log.warn("starting doDataTask");
		User user = userService.getUserByNo("103005731");
		log.warn("user: " + user.getUsername());
	}

}
