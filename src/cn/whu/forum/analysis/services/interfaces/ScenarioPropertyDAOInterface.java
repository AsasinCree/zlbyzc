package cn.whu.forum.analysis.services.interfaces;

import java.util.List;
import java.util.Set;

import cn.whu.forum.analysis.entities.ScenarioProperty;
import cn.whu.forum.analysis.entities.ScenarioTask;

/**
 * 情景分析法案例的属性数据操作接口
 * 
 * @author asasi
 *
 */
public interface ScenarioPropertyDAOInterface {
	/**
	 * 添加情景分析法案例属性内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioProperty
	 *            情景分析法属性内容
	 * @return 是否添加成功标志
	 */
	public boolean addProperty(ScenarioTask scenarioTask, ScenarioProperty scenarioProperty);

	/**
	 * 添加情景分析法案例属性内容集合
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioLogic
	 *            情景分析法属性内容集合
	 * @return 是否添加成功标志
	 */
	public boolean addPropertis(ScenarioTask scenarioTask, Set<ScenarioProperty> properties);

	/**
	 * 更新情景分析法案例属性内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @param scenarioLogic
	 *            情景分析法属性内容
	 * @return 是否添加成功标志
	 */
	public boolean updateProperty(ScenarioProperty scenarioProperty);

	/**
	 * 删除情景分析法案例属性内容
	 * 
	 * @param scenarioLogic
	 *            情景分析法属性内容
	 * @return 是否添加成功标志
	 */
	public boolean deleteProperty(ScenarioProperty scenarioProperty);

	/**
	 * 删除情景分析法案例所有属性内容
	 * 
	 * @return 是否添加成功标志
	 */
	public boolean deleteAllProperties();

	/**
	 * 依据编号属性编号删除情景分析法案例属性
	 * 
	 * @param id
	 *            情景分析法案例属性编号
	 * @return 是否添加成功标志
	 */
	public boolean deletePropertyByID(int id);

	/**
	 * 依据编号属性编号获得情景分析法案例属性
	 * 
	 * @param id
	 *            情景分析法案例属性编号
	 * @return 是否添加成功标志
	 */
	public ScenarioProperty getPropertyByID(int id);

	/**
	 * 依据属性名字编号获得情景分析法案例属性
	 * 
	 * @param nameString
	 *            情景分析法案例属性名称
	 * @return 情景分析法案例属性集合
	 */
	public List<ScenarioProperty> getPropertyByName(String nameString);

	/**
	 * 获得情景分析法案例所有属性内容
	 * 
	 * @param scenarioTask
	 *            情景分析法案例
	 * @return 情景分析法案例属性集合
	 */
	public List<ScenarioProperty> getAllPropertys(ScenarioTask scenarioTask);
}
