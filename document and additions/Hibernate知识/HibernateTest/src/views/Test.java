package views;

import javax.swing.JFrame;

import services.UserDAO;

public class Test  extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

			UserDAO ud = new UserDAO();
			System.out.println(ud.getAllUsers());

	}

}
