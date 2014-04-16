package com.terrynow.springtest.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
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
		if (no == null)
			return null;
		Session s = sessionFactory.getCurrentSession();
		System.out.println("get session:" + (s == null));
		Query query = s.createQuery("from User u where u.no=:no");
		query.setString("no", no);
		return (User) query.uniqueResult();
	}

	@Override
	public long getCountUsers() {
		String sql = "select count(u) from User u";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		return (Long) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers(int start, int limit) {
		String sql = "from User u";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.list();
	}
}
