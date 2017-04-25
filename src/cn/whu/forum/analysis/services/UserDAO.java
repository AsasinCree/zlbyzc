package cn.whu.forum.analysis.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.whu.forum.analysis.entities.User;
import cn.whu.forum.analysis.services.interfaces.UserDAOInterface;
import cn.whu.forum.analysis.tools.HibernateTool;

/**
 * 用户数据操作实现类
 * 
 * @author asasi
 *
 */
public class UserDAO implements UserDAOInterface {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.UserDAOInterface#insertUser(cn.
	 * whu.forum.analysis.entities.User)
	 */
	@Override
	public boolean insertUser(User user) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return false;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.UserDAOInterface#updateUser(cn.
	 * whu.forum.analysis.entities.User)
	 */
	@Override
	public boolean updateUser(User user) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.UserDAOInterface#getUserByID(
	 * int)
	 */
	@Override
	public User getUserByID(int id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.UserDAOInterface#getUsersByName
	 * (java.lang.String)
	 */
	@Override
	public List<User> getUsersByName(String nameString) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.UserDAOInterface#getAllUsers()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		Session session = null;
		Transaction transaction = null;
		List<User> userList = null;
		String hqlString = "";

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			hqlString = "from User";
			Query query = session.createQuery(hqlString);
			userList = (List<User>) query.list();

			transaction.commit();
			return userList;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return userList;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}
}
