package cn.whu.forum.analysis.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.whu.forum.analysis.entities.ScenarioResult;
import cn.whu.forum.analysis.entities.ScenarioTask;
import cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface;
import cn.whu.forum.analysis.tools.HibernateTool;

/**
 * 情景分析法案例的结果数据操作实现类
 * 
 * @author asasi
 *
 */
public class ScenarioResultDAO implements ScenarioResultDAOInterface {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * addResult(cn.whu.forum.analysis.entities.ScenarioTask,
	 * cn.whu.forum.analysis.entities.ScenarioResult)
	 */
	@Override
	public boolean addResult(ScenarioTask scenarioTask, ScenarioResult scenarioResult) {

		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			scenarioResult.setScenarioTask(scenarioTask);
			session.saveOrUpdate(scenarioResult);
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
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * addPropertis(cn.whu.forum.analysis.entities.ScenarioTask, java.util.Set)
	 */
	@Override
	public boolean addPropertis(ScenarioTask scenarioTask, Set<ScenarioResult> properties) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * updateResult(cn.whu.forum.analysis.entities.ScenarioResult)
	 */
	@Override
	public boolean updateResult(ScenarioResult scenarioResult) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "update ScenarioResult set resultContent=:resultContent where resultID=:resultID";
			Query query = session.createQuery(hql);
			query.setParameter("resultContent", scenarioResult.getResultContent());
			query.setParameter("resultID", scenarioResult.getResultID());
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
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * deleteResult(cn.whu.forum.analysis.entities.ScenarioResult)
	 */
	@Override
	public boolean deleteResult(ScenarioResult scenarioResult) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * deleteAllResults()
	 */
	@Override
	public boolean deleteAllResults() {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "delete from ScenarioResult where 1=1";
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
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * deleteResultByID(int)
	 */
	@Override
	public boolean deleteResultByID(int id) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "delete from ScenarioResult where resultID=:resultID";
			Query query = session.createQuery(hql);
			query.setParameter("resultID", id);
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
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * getResultByID(int)
	 */
	@Override
	public ScenarioResult getResultByID(int id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * getResultByName(java.lang.String)
	 */
	@Override
	public List<ScenarioResult> getResultByName(String nameString) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface#
	 * getAllResults(cn.whu.forum.analysis.entities.ScenarioTask)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioResult> getAllResults(ScenarioTask scenarioTask) {
		Session session = null;
		Transaction transaction = null;
		List<ScenarioResult> scenarioResultList = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from ScenarioResult where scenarioTask=:scenarioTask";
			Query query = session.createQuery(hqlString);
			query.setParameter("scenarioTask", scenarioTask);
			scenarioResultList = (List<ScenarioResult>) query.list();

			transaction.commit();
			return scenarioResultList;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return scenarioResultList;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}
}
