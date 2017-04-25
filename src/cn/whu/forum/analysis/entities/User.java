package cn.whu.forum.analysis.entities;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	//用户的编号
	private int userID;
	//用户的名称
	private String userName;
	//用户的密码
	private String password;

	//默认（空）的构造函数
	public User()
	{
		
	}

	//包含所有属性初始化的构造函数
	public User(int userID, String userName, String password) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
	}

	//获得用户的编号
	public int getUserID() {
		return userID;
	}
	
	//设置用户的编号
	public void setUserID(int uid) {
		this.userID = uid;
	}

	//获得用户的名称
	public String getUserName() {
		return userName;
	}
	
	//设置用户的名称
	public void setUserName(String userName) {
		this.userName = userName;
	}

	//获得用户的密码
	public String getPassword() {
		return password;
	}
	
	//设置用户的密码
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String toString(){
		return userID +" " +userName +" " + password;
	}  
}
