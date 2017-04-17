package cn.whu.forum.analysis.services.interfaces;

import java.util.List;

import cn.whu.forum.analysis.entities.User;

public interface UserDAOInterface {
	public boolean insertUser(User user);
	public boolean updateUser(User user);
	public User getUserByID(int id);
	public List<User> getUsersByName(String nameString);
	public List<User> getAllUsers();
}
