package cn.whu.forum.analysis.services.interfaces;

import java.util.List;
import java.util.Set;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;

/**
 * SWOT法案例参与者属性数据操作接口
 * 
 * @author asasi
 *
 */
public interface SwotPropertyDAOInterface {
	/**
	 * 添加参与者属性
	 * 
	 * @param swotActor
	 *            SWOT法案例参与者实例
	 * @param swotActorProperty
	 *            SWOT法案例参与者属性
	 * @return 是否添加成功标志
	 */
	public boolean addProperty(SwotActor swotActor, SwotActorProperty swotActorProperty);

	/**
	 * 添加参与者属性集合
	 * 
	 * @param swotActor
	 *            SWOT法案例参与者实例
	 * @param swotActorPropertis
	 *            SWOT法案例参与者属性集合
	 * @return 是否添加成功标志
	 */
	public boolean addPropertis(SwotActor swotActor, Set<SwotActorProperty> swotActorPropertis);

	/**
	 * 更新SWOT法案例参与者属性
	 * 
	 * @param swotActorProperty
	 *            SWOT法案例参与者属性
	 * @return 是否更新成功标志
	 */
	public boolean updateProperty(SwotActorProperty swotActorProperty);

	/**
	 * 删除SWOT法案例参与者属性
	 * 
	 * @param swotActorProperty
	 *            SWOT法案例参与者属性
	 * @return 是否删除成功标志
	 */
	public boolean deleteProperty(SwotActorProperty swotActorProperty);

	/**
	 * 根据属性编号删除SWOT法案例参与者属性
	 * 
	 * @param id
	 *            SWOT法案例参与者属性编号
	 * @return 是否删除成功标志
	 */
	public boolean deletePropertyByID(int id);

	/**
	 * 根据属性编号获取SWOT法案例参与者属性
	 * 
	 * @param id
	 *            SWOT法案例参与者属性编号
	 * @return SWOT法案例参与者属性
	 */
	public SwotActorProperty getPropertyByID(int id);

	/**
	 * 根据属性编号获取SWOT法案例参与者属性
	 * 
	 * @param nameString
	 *            SWOT法案例参与者属性名称
	 * @return SWOT法案例参与者属性集合
	 */
	public List<SwotActorProperty> getPropertyByName(String nameString);

	/**
	 * 根据参与者实例获取SWOT法案例参与者属性
	 * 
	 * @param swotActor
	 *            SWOT法案例参与者实例
	 * @return SWOT法案例参与者属性集合
	 */
	public List<SwotActorProperty> getPropertyByActor(SwotActor swotActor);

	/**
	 * 根据参与者实例编号获取SWOT法案例参与者属性
	 * 
	 * @param actorID
	 *            SWOT法案例参与者实例编号
	 * @return SWOT法案例参与者属性集合
	 */
	public List<SwotActorProperty> getPropertyByActorID(int actorID);

	/**
	 * 获取SWOT法案例参与者属性集合
	 * 
	 * @return SWOT法案例参与者属性集合
	 */
	public List<SwotActorProperty> getAllPropertys();
}
