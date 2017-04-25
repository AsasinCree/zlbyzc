package cn.whu.forum.analysis.services.interfaces;

import java.util.Date;
import java.util.List;

import cn.whu.forum.analysis.entities.ScenarioLogic;
import cn.whu.forum.analysis.entities.ScenarioProperty;
import cn.whu.forum.analysis.entities.ScenarioResult;
import cn.whu.forum.analysis.entities.ScenarioTask;

/**
 * 情景分析法案例的数据库操作接口
 * 
 * @author asasi
 *
 */
public interface ScenarioTaskDAOInterface {
	/**
	 * 添加情景分析法案例
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @return 是否添加成功标志
	 */
	public boolean addTask(ScenarioTask scenarioTask);

	/**
	 * 更新情景分析法案例
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @return 是否添加成功标志
	 */
	public boolean updateTask(ScenarioTask scenarioTask);

	/**
	 * 删除情景分析法案例
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @return 是否添加成功标志
	 */
	public boolean deleteTask(ScenarioTask scenarioTask);

	/**
	 * 根据案例编号删除案例
	 * 
	 * @param id
	 *            情景分析法案例编号
	 * @return 是否删除成功标志
	 */
	public boolean deleteTaskByID(int id);

	/**
	 * 根据案例编号获取案例
	 * 
	 * @param id
	 *            情景分析法案例编号
	 * @return 情景分析法案例
	 */
	public ScenarioTask getScenarioTaskByID(int id);

	/**
	 * 根据案例名称获取案例
	 * 
	 * @param nameString
	 *            情景分析法案例名称
	 * @return 情景分析法案例集合
	 */
	public List<ScenarioTask> getScenarioTaskByName(String nameString);

	/**
	 * 获取所有案例
	 * 
	 * @return 情景分析法案例集合
	 */
	public List<ScenarioTask> getAllScenarioTasks();

	/**
	 * 获取所有情景分析法案例属性内容集合
	 * 
	 * @return 情景分析法案例属性内容集合
	 */
	List<ScenarioProperty> getAllTaskProperties(ScenarioTask scenarioTask);

	/**
	 * 获取所有情景分析法案例逻辑内容集合
	 * 
	 * @return 情景分析法案例逻辑内容集合
	 */
	List<ScenarioLogic> getAllTaskLogics(ScenarioTask scenarioTask);

	/**
	 * 获取所有情景分析法案例结果内容集合
	 * 
	 * @return 情景分析法案例结果内容集合
	 */
	List<ScenarioResult> getAllTaskResults(ScenarioTask scenarioTask);

	/**
	 * 获取所有符合条件的情景分析法案例集合
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 情景分析法案例集合
	 */
	List<ScenarioTask> getScenarioTaskByDate(Date startDate, Date endDate);

	/**
	 * 获取所有情景分析法案例的地点集合
	 * 
	 * @return 地点集合
	 */
	List<String> getAllScenarioTasksLocation();

	/**
	 * 根据案例地点获取案例
	 * 
	 * @param locationString
	 *            情景分析法案例地点
	 * @return 情景分析法案例集合
	 */
	List<ScenarioTask> getScenarioTaskByLocation(String locationString);
}
