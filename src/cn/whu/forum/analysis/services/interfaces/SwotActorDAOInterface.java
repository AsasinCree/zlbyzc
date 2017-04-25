package cn.whu.forum.analysis.services.interfaces;

import java.util.List;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.entities.SwotTask;

/**
 * SWOT法案例参与者数据操作接口
 * 
 * @author asasi
 *
 */
public interface SwotActorDAOInterface {
	/**
	 * 添加参与者
	 * 
	 * @param swotActor
	 *            SWOT法案例参与者
	 * @return 是否添加成功标志
	 */
	public boolean addActor(SwotActor swotActor);

	/**
	 * 添加参与者
	 * 
	 * @param swotTask
	 *            SWOT法案例
	 * @param swotActor
	 *            SWOT法案例参与者
	 * @return 是否添加成功标志
	 */
	public boolean addActor(SwotTask swotTask, SwotActor swotActor);

	/**
	 * 更新参与者
	 * 
	 * @param swotActor
	 *            SWOT法案例参与者
	 * @return 是否更新成功标志
	 */
	public boolean updateActor(SwotActor swotActor);

	/**
	 * 删除参与者
	 * 
	 * @param swotActor
	 *            SWOT法案例参与者
	 * @return 是否删除成功标志
	 */
	public boolean deleteActor(SwotActor swotActor);

	/**
	 * 根据参与者编号删除参与者
	 * 
	 * @param id
	 *            SWOT法案例参与者编号
	 * @return 是否删除成功标志
	 */
	public boolean deleteActorByID(int id);

	/**
	 * 根据参与者编号获取参与者
	 * 
	 * @param id
	 *            SWOT法案例参与者编号
	 * @return SWOT法案例参与者
	 */
	public SwotActor getActorByID(int id);

	/**
	 * 根据参与者名称获取参与者
	 * 
	 * @param nameString
	 *            SWOT法案例参与者编号
	 * @return SWOT法案例参与者
	 */
	public List<SwotActor> getActorByName(String nameString);

	/**
	 * 根据SWOT法案例获取参与者
	 * 
	 * @param swotTask
	 *            SWOT法案例
	 * @return SWOT法案例参与者集合
	 */
	public List<SwotActor> getActorByTask(SwotTask swotTask);

	/**
	 * 根据SWOT法案例编号获取参与者
	 * 
	 * @param id
	 *            SWOT法案例编号
	 * @return SWOT法案例参与者集合
	 */
	public List<SwotActor> getActorByTaskID(int taskID);

	/**
	 * 根据SWOT法案例参与者实例获取参与者
	 * 
	 * @param swotActor
	 *            SWOT法案例参与者实例
	 * @return SWOT法案例参与者属性集合
	 */
	public List<SwotActorProperty> getAllActorPropertys(SwotActor swotActor);

	/**
	 * 根据参与者名称获取参与者属性集合
	 * 
	 * @param nameString
	 *            SWOT法案例参与者名称
	 * @return SWOT法案例参与者属性集合
	 */
	List<SwotActor> getSwotActorByActorName(String nameString);
}
