package cn.whu.forum.analysis.services.interfaces;

import java.util.List;
import java.util.Set;

import cn.whu.forum.analysis.entities.ScenarioLogic;
import cn.whu.forum.analysis.entities.ScenarioTask;

/**
 * 情景分析法案例的逻辑数据操作接口
 * 
 * @author asasi
 *
 */
public interface ScenarioLogicDAOInterface {
	/**
	 * 添加情景分析法案例逻辑内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioLogic
	 *            情景分析法逻辑内容
	 * @return 是否添加成功标志
	 */
	public boolean addLogic(ScenarioTask scenarioTask, ScenarioLogic scenarioLogic);

	/**
	 * 添加情景分析法案例逻辑内容集合
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioLogic
	 *            情景分析法逻辑内容集合
	 * @return 是否添加成功标志
	 */
	public boolean addLogics(ScenarioTask scenarioTask, Set<ScenarioLogic> logics);

	/**
	 * 更新情景分析法案例逻辑内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioLogic
	 *            情景分析法逻辑内容
	 * @return 是否添加成功标志
	 */
	public boolean updateLogic(ScenarioLogic scenarioLogic);

	/**
	 * 删除情景分析法案例逻辑内容
	 * 
	 * @param scenarioLogic
	 *            情景分析法逻辑内容
	 * @return 是否添加成功标志
	 */
	public boolean deleteLogic(ScenarioLogic scenarioLogic);

	/**
	 * 删除情景分析法案例所有逻辑内容
	 * 
	 * @return 是否添加成功标志
	 */
	public boolean deleteAllLogics();

	/**
	 * 依据编号逻辑编号删除情景分析法案例逻辑
	 * 
	 * @param id
	 *            情景分析法案例逻辑编号
	 * @return 是否添加成功标志
	 */
	public boolean deleteLogicByID(int id);

	/**
	 * 依据编号逻辑编号获得情景分析法案例逻辑
	 * 
	 * @param id
	 *            情景分析法案例逻辑编号
	 * @return 是否添加成功标志
	 */
	public ScenarioLogic getLogicByID(int id);

	/**
	 * 依据逻辑名字编号获得情景分析法案例逻辑
	 * 
	 * @param nameString
	 *            情景分析法案例逻辑名称
	 * @return 情景分析法案例逻辑集合
	 */
	public List<ScenarioLogic> getLogicByName(String nameString);

	/**
	 * 获得情景分析法案例所有逻辑内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @return 情景分析法案例逻辑集合
	 */
	public List<ScenarioLogic> getAllLogics(ScenarioTask scenarioTask);
}
