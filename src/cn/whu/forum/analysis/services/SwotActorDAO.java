package cn.whu.forum.analysis.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.entities.SwotTask;
import cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface;
import cn.whu.forum.analysis.tools.HibernateTool;

/**
 * SWOT法案例参与者数据操作实现类
 * 
 * @author asasi
 *
 */
public class SwotActorDAO implements SwotActorDAOInterface {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#addActor(
	 * cn.whu.forum.analysis.entities.SwotActor)
	 */
	@Override
	public boolean addActor(SwotActor swotActor) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#addActor(
	 * cn.whu.forum.analysis.entities.SwotTask,
	 * cn.whu.forum.analysis.entities.SwotActor)
	 */
	@Override
	public boolean addActor(SwotTask swotTask, SwotActor swotActor) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			swotActor.setSwotTask(swotTask);
			session.saveOrUpdate(swotActor);
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
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * updateActor(cn.whu.forum.analysis.entities.SwotActor)
	 */
	@Override
	public boolean updateActor(SwotActor SwotActor) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * deleteActor(cn.whu.forum.analysis.entities.SwotActor)
	 */
	@Override
	public boolean deleteActor(SwotActor SwotActor) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * deleteActorByID(int)
	 */
	@Override
	public boolean deleteActorByID(int id) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "delete from SwotActor where actorID=:actorID";
			Query query = session.createQuery(hql);
			query.setParameter("actorID", id);
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
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * getActorByID(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SwotActor getActorByID(int actorID) {
		Session session = null;
		Transaction transaction = null;
		SwotActor swotActor = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "from SwotActor where actorID=:actorID";
			Query query = session.createQuery(hql);
			query.setParameter("actorID", actorID);
			List<SwotActor> actorList = (List<SwotActor>) query.list();
			swotActor = actorList.get(0);

			transaction.commit();
			return swotActor;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return swotActor;
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
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * getActorByName(java.lang.String)
	 */
	@Override
	public List<SwotActor> getActorByName(String nameString) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * getActorByTask(cn.whu.forum.analysis.entities.SwotTask)
	 */
	@Override
	public List<SwotActor> getActorByTask(SwotTask swotTask) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * getActorByTaskID(int)
	 */
	@Override
	public List<SwotActor> getActorByTaskID(int taskID) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * getAllActorPropertys(cn.whu.forum.analysis.entities.SwotActor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwotActorProperty> getAllActorPropertys(SwotActor swotActor) {
		Session session = null;
		Transaction transaction = null;
		List<SwotActorProperty> swotPropertyList = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from SwotActorProperty where swotActor=:swotActor";
			Query query = session.createQuery(hqlString);
			query.setParameter("swotActor", swotActor);
			swotPropertyList = (List<SwotActorProperty>) query.list();

			transaction.commit();
			return swotPropertyList;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return swotPropertyList;
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
	 * @see cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface#
	 * getSwotActorByActorName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SwotActor> getSwotActorByActorName(String nameString) {
		Session session = null;
		Transaction transaction = null;
		List<SwotActor> swotTaskList = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "from SwotActor where actorName like :actorName";
			Query query = session.createQuery(hql);
			query.setParameter("actorName", "%" + nameString + "%");
			swotTaskList = (List<SwotActor>) query.list();

			transaction.commit();
			return swotTaskList;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.commit();
			return swotTaskList;
		} finally {
			if (transaction != null)
				transaction = null;
			if (session != null)
				HibernateTool.closeSession(session);
		}
	}
}
