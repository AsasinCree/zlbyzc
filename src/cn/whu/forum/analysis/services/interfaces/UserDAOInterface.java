package cn.whu.forum.analysis.services.interfaces;

import java.util.List;

import cn.whu.forum.analysis.entities.User;

/**
 * 用户的数据库操作接口
 * 
 * @author asasi
 *
 */
public interface UserDAOInterface {
	/**
	 * 插入用户
	 * 
	 * @param user
	 *            用户实例
	 * @return 是否添加成功标志
	 */
	public boolean insertUser(User user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 *            用户实例
	 * @return 是否更新成功标志
	 */
	public boolean updateUser(User user);

	/**
	 * 根据用户编号获取用户实例
	 * 
	 * @param id
	 *            用户实例编号
	 * @return 用户实例
	 */
	public User getUserByID(int id);

	/**
	 * 根据用户名称获取用户实例
	 * 
	 * @param nameString
	 *            用户实例名称
	 * @return 用户实例集合
	 */
	public List<User> getUsersByName(String nameString);

	/**
	 * 获取用户实例集合
	 * 
	 * @return 用户实例集合
	 */
	public List<User> getAllUsers();
}
