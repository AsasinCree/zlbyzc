package cn.whu.forum.analysis.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.whu.forum.analysis.entities.ScenarioProperty;
import cn.whu.forum.analysis.entities.ScenarioTask;
import cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface;
import cn.whu.forum.analysis.tools.HibernateTool;

/**
 * 情景分析法案例的属性数据操作实现类
 * @author asasi
 *
 */
public class ScenarioPropertyDAO implements ScenarioPropertyDAOInterface {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * addProperty(cn.whu.forum.analysis.entities.ScenarioTask,
	 * cn.whu.forum.analysis.entities.ScenarioProperty)
	 */
	@Override
	public boolean addProperty(ScenarioTask scenarioTask, ScenarioProperty scenarioProperty) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			scenarioProperty.setScenarioTask(scenarioTask);
			session.saveOrUpdate(scenarioProperty);
			transaction.commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return false;

		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * addPropertis(cn.whu.forum.analysis.entities.ScenarioTask, java.util.Set)
	 */
	@Override
	public boolean addPropertis(ScenarioTask scenarioTask, Set<ScenarioProperty> properties) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * updateProperty(cn.whu.forum.analysis.entities.ScenarioProperty)
	 */
	@Override
	public boolean updateProperty(ScenarioProperty scenarioProperty) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "update ScenarioProperty set propertyContent=:propertyContent where propertyID=:propertyID";
			Query query = session.createQuery(hql);
			query.setParameter("propertyContent", scenarioProperty.getPropertyContent());
			query.setParameter("propertyID", scenarioProperty.getPropertyID());
			query.executeUpdate();

			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return false;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * deleteProperty(cn.whu.forum.analysis.entities.ScenarioProperty)
	 */
	@Override
	public boolean deleteProperty(ScenarioProperty scenarioProperty) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * deleteAllProperties()
	 */
	@Override
	public boolean deleteAllProperties() {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "delete from ScenarioProperty where 1=1";
			Query query = session.createQuery(hql);
			query.executeUpdate();

			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return false;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * deletePropertyByID(int)
	 */
	@Override
	public boolean deletePropertyByID(int id) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "delete from ScenarioProperty where propertyID=:propertyID";
			Query query = session.createQuery(hql);
			query.setParameter("propertyID", id);
			query.executeUpdate();

			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return false;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * getPropertyByID(int)
	 */
	@Override
	public ScenarioProperty getPropertyByID(int id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * getPropertyByName(java.lang.String)
	 */
	@Override
	public List<ScenarioProperty> getPropertyByName(String nameString) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface#
	 * getAllPropertys(cn.whu.forum.analysis.entities.ScenarioTask)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioProperty> getAllPropertys(ScenarioTask scenarioTask) {
		Session session = null;
		Transaction transaction = null;
		List<ScenarioProperty> scenarioPropertyList = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from ScenarioProperty where scenarioTask=:scenarioTask";
			Query query = session.createQuery(hqlString);
			query.setParameter("scenarioTask", scenarioTask);
			scenarioPropertyList = (List<ScenarioProperty>) query.list();

			transaction.commit();
			return scenarioPropertyList;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return scenarioPropertyList;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}
}
