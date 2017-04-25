package cn.whu.forum.analysis.tools;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 用于数据库创建
 * @author cree
 *
 */
public class HibernateTool {
	private static Configuration configuration;
	private static SessionFactory sessionFactory;

	private HibernateTool() {

	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			 configuration = new Configuration().configure();
			 sessionFactory = configuration.buildSessionFactory();
			return sessionFactory;
		} else {
			return sessionFactory;
		}
	}

	public static Session getSession() {
		return getSessionFactory().openSession();

	}

	public static void closeSession(Session session) {
		if (session != null) {
			session.close();
		}
	}
}
