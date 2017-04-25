package cn.whu.forum.analysis.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.whu.forum.analysis.entities.ScenarioLogic;
import cn.whu.forum.analysis.entities.ScenarioTask;
import cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface;
import cn.whu.forum.analysis.tools.HibernateTool;

/**
 * 情景分析法案例的逻辑数据操作实现类
 * 
 * @author asasi
 *
 */
public class ScenarioLogicDAO implements ScenarioLogicDAOInterface {
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * addLogic(cn.whu.forum.analysis.entities.ScenarioTask,
	 * cn.whu.forum.analysis.entities.ScenarioLogic)
	 */
	@Override
	public boolean addLogic(ScenarioTask scenarioTask, ScenarioLogic scenarioLogic) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			scenarioLogic.setScenarioTask(scenarioTask);
			session.saveOrUpdate(scenarioLogic);
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
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * addLogics(cn.whu.forum.analysis.entities.ScenarioTask, java.util.Set)
	 */
	@Override
	public boolean addLogics(ScenarioTask scenarioTask, Set<ScenarioLogic> logics) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * updateLogic(cn.whu.forum.analysis.entities.ScenarioLogic)
	 */
	@Override
	public boolean updateLogic(ScenarioLogic scenarioLogic) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "update ScenarioLogic set logicContent=:logicContent where logicID=:logicID";
			Query query = session.createQuery(hql);
			query.setParameter("logicContent", scenarioLogic.getLogicContent());
			query.setParameter("logicID", scenarioLogic.getLogicID());
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
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * deleteLogic(cn.whu.forum.analysis.entities.ScenarioLogic)
	 */
	@Override
	public boolean deleteLogic(ScenarioLogic scenarioLogic) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * deleteAllLogics()
	 */
	@Override
	public boolean deleteAllLogics() {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "delete from ScenarioLogic where 1=1";
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
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * deleteLogicByID(int)
	 */
	@Override
	public boolean deleteLogicByID(int id) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "delete from ScenarioLogic where logicID=:logicID";
			Query query = session.createQuery(hql);
			query.setParameter("logicID", id);
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
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * getLogicByID(int)
	 */
	@Override
	public ScenarioLogic getLogicByID(int id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * getLogicByName(java.lang.String)
	 */
	@Override
	public List<ScenarioLogic> getLogicByName(String nameString) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface#
	 * getAllLogics(cn.whu.forum.analysis.entities.ScenarioTask)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioLogic> getAllLogics(ScenarioTask scenarioTask) {
		Session session = null;
		Transaction transaction = null;
		List<ScenarioLogic> scenarioLogicList = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from ScenarioLogic where scenarioTask=:scenarioTask";
			Query query = session.createQuery(hqlString);
			query.setParameter("scenarioTask", scenarioTask);
			scenarioLogicList = (List<ScenarioLogic>) query.list();

			transaction.commit();
			return scenarioLogicList;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return scenarioLogicList;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}
}
