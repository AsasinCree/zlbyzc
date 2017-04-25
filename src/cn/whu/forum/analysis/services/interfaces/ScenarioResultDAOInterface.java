package cn.whu.forum.analysis.services.interfaces;

import java.util.List;
import java.util.Set;

import cn.whu.forum.analysis.entities.ScenarioResult;
import cn.whu.forum.analysis.entities.ScenarioTask;

/**
 * 情景分析法案例的结果数据操作接口
 * 
 * @author asasi
 *
 */
public interface ScenarioResultDAOInterface {
	/**
	 * 添加情景分析法案例结果内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioLogic
	 *            情景分析法结果内容
	 * @return 是否添加成功标志
	 */
	public boolean addResult(ScenarioTask scenarioTask, ScenarioResult scenarioResult);

	/**
	 * 添加情景分析法案例结果内容集合
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioLogic
	 *            情景分析法结果内容集合
	 * @return 是否添加成功标志
	 */
	public boolean addPropertis(ScenarioTask scenarioTask, Set<ScenarioResult> properties);

	/**
	 * 更新情景分析法案例结果内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioLogic
	 *            情景分析法结果内容
	 * @return 是否添加成功标志
	 */
	public boolean updateResult(ScenarioResult scenarioResult);

	/**
	 * 删除情景分析法案例结果内容
	 * 
	 * @param scenarioLogic
	 *            情景分析法结果内容
	 * @return 是否添加成功标志
	 */
	public boolean deleteResult(ScenarioResult scenarioResult);

	/**
	 * 删除情景分析法案例所有结果内容
	 * 
	 * @return 是否添加成功标志
	 */
	public boolean deleteAllResults();

	/**
	 * 依据编号结果编号删除情景分析法案例结果
	 * 
	 * @param id
	 *            情景分析法案例结果编号
	 * @return 是否添加成功标志
	 */
	public boolean deleteResultByID(int id);

	/**
	 * 依据编号结果编号获得情景分析法案例结果
	 * 
	 * @param id
	 *            情景分析法案例结果编号
	 * @return 是否添加成功标志
	 */
	public ScenarioResult getResultByID(int id);

	/**
	 * 依据结果名字编号获得情景分析法案例结果
	 * 
	 * @param nameString
	 *            情景分析法案例结果名称
	 * @return 情景分析法案例结果集合
	 */
	public List<ScenarioResult> getResultByName(String nameString);

	/**
	 * 获得情景分析法案例所有结果内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @return 情景分析法案例结果集合
	 */
	public List<ScenarioResult> getAllResults(ScenarioTask scenarioTask);
}
