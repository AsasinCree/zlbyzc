package cn.whu.forum.analysis.services.interfaces;

import java.util.Date;
import java.util.List;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotTask;

/**
 * SWOT法案例的数据库操作接口
 * 
 * @author asasi
 *
 */
public interface SwotTaskDAOInterface {
	/**
	 * 更新SWOT结果
	 * 
	 * @param swotTask
	 *            SWOT法案例
	 * @return 是否更新成功标志
	 */
	public boolean updateTaskResult(SwotTask swotTask);

	/**
	 * 获取SWOT结果内容
	 * 
	 * @param swotTask
	 *            SWOT法案例
	 * @return 获取SWOT法案例结果内容
	 */
	public String getTaskResult(SwotTask swotTask);

	/**
	 * 添加SWOT法案例
	 * 
	 * @param swotTask
	 *            要添加的SWOT法案例
	 * @return 是否添加成功标志
	 */
	public boolean addTask(SwotTask swotTask);

	/**
	 * 更新SWOT法案例
	 * 
	 * @param swotTask
	 *            要更新的SWOT法案例
	 * @return 是否更新成功标志
	 */
	public boolean updateTask(SwotTask swotTask);

	/**
	 * 删除SWOT法案例
	 * 
	 * @param swotTask
	 *            要删除的SWOT法案例
	 * @return 是否删除成功标志
	 */
	public boolean deleteTask(SwotTask swotTask);

	/**
	 * 删除SWOT法案例
	 * 
	 * @param id
	 *            要删除的SWOT法案例编号
	 * @return 是否删除成功标志
	 */
	public boolean deleteTaskByID(int id);

	/**
	 * 获取SWOT法案例
	 * 
	 * @param id
	 *            SWOT法案例编号
	 * @return SWOT法案例
	 */
	public SwotTask getSwotTaskByID(int id);

	/**
	 * 根据案例名称获取SWOT法案例集合
	 * 
	 * @param nameString
	 *            SWOT法案例名称
	 * @return SWOT法案例集合
	 */
	public List<SwotTask> getSwotTaskByName(String nameString);

	/**
	 * 获取所有SWOT法案例
	 * 
	 * @return SWOT法案例集合
	 */
	public List<SwotTask> getAllSwotTasks();

	/**
	 * 根据 SWOT法案例获取所有关联的参与者
	 * 
	 * @param swotTask
	 *            SWOT法案例
	 * @return SWOT法案例参与者集合
	 */
	List<SwotActor> getAllTaskActors(SwotTask swotTask);

	/**
	 * 根据日期区间获取所有符合条件的SWOT法案例
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return SWOT法案例集合
	 */
	List<SwotTask> getSwotTaskByDate(Date startDate, Date endDate);

	/**
	 * 获取所有SWOT法案例地点
	 * 
	 * @return SWOT法案例地点集合
	 */
	List<String> getAllSwotTasksLocation();

	/**
	 * 根据案例地点获取所有SWOT法案例
	 * 
	 * @param locationString
	 *            SWOT法案例地点
	 * @return SWOT法案例集合
	 */
	List<SwotTask> getSwotTaskByLocation(String locationString);
}
