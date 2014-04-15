package com.terrynow.springtest.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.terrynow.springtest.dao.intf.IUserDao;
import com.terrynow.springtest.entity.User;

/**
 * @author Terry E-mail: yaoxinghuo at 126 dot com
 * @date Mar 19, 2014 3:39:56 PM
 * @description
 */
@Component("userDao")
public class UserDaoImpl implements IUserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User getUserByNo(final String no) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from User u where u.no=:no");
		query.setString("no", no);
		return (User) query.uniqueResult();
	}

	@Override
	public long getCountUsers(int type) {
		String sql = "select count(u) from User u";
		if (type != -1)
			sql += " where u.type=:type";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		if (type != -1)
			query.setParameter("type", type);
		return (Long) query.uniqueResult();
	}
}
