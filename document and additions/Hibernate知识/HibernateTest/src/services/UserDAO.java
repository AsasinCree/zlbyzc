package services;

import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import entities.User;
import service.interfaces.UserDAOInterface;
import tools.HibernateTool;

public class UserDAO implements UserDAOInterface {

	@Override
	public boolean insertUser(User user) {
		
		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
			return true;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return false;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
			
		}
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUserByID(int id) {
		// TODO Auto-generated method stub
		
//		hqlString = "from User";
//		Query query =  session.createQuery(hqlString);
//		query.executeUpdate();
		
		return null;
	}

	@Override
	public List<User> getUsersByName(String nameString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction transaction = null;
		List<User> userList = null;
		String hqlString = "";
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			hqlString = "from User";
			Query query = session.createQuery(hqlString);
			userList = (List<User>) query.list();
			transaction.commit();
			return userList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return userList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
			
		}
	}

	
}
