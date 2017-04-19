package services.test;

import org.junit.Test;

import entities.User;
import services.UserDAO;

public class TestUserDAO {
	
	@Test
	public void testInsertUser()
	{
		User user = new User();
		user.setUserID(0);
		user.setUserName("ÍõÎå");
		user.setPassword("111111");
		UserDAO ud = new UserDAO();
		if ( ud.insertUser(user)){
			System.out.println("True");
		}
		else {
			System.out.println("False");
		}
	}
	
	
	@Test
	public void getAllUsers()
	{

		UserDAO ud = new UserDAO();
		System.out.println(ud.getAllUsers());
	}

}
