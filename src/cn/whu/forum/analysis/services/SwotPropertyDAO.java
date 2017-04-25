package cn.whu.forum.analysis.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface;
import cn.whu.forum.analysis.tools.HibernateTool;

/**
 * SWOT法案例参与者属性数据操作实现类
 * 
 * @author asasi
 *
 */
public class SwotPropertyDAO implements SwotPropertyDAOInterface {
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * addProperty(cn.whu.forum.analysis.entities.SwotActor,
	 * cn.whu.forum.analysis.entities.SwotActorProperty)
	 */
	@Override
	public boolean addProperty(SwotActor swotActor, SwotActorProperty swotActorProperty) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			swotActorProperty.setSwotActor(swotActor);
			session.saveOrUpdate(swotActorProperty);
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
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * addPropertis(cn.whu.forum.analysis.entities.SwotActor, java.util.Set)
	 */
	@Override
	public boolean addPropertis(SwotActor swotActor, Set<SwotActorProperty> SwotActorPropertis) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * updateProperty(cn.whu.forum.analysis.entities.SwotActorProperty)
	 */
	@Override
	public boolean updateProperty(SwotActorProperty swotActorProperty) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "update SwotActorProperty set propertyContent=:propertyContent where propertyID=:propertyID";
			Query query = session.createQuery(hql);
			query.setParameter("propertyContent", swotActorProperty.getPropertyContent());
			query.setParameter("propertyID", swotActorProperty.getPropertyID());
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
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * deleteProperty(cn.whu.forum.analysis.entities.SwotActorProperty)
	 */
	@Override
	public boolean deleteProperty(SwotActorProperty SwotActorProperty) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * deletePropertyByID(int)
	 */
	@Override
	public boolean deletePropertyByID(int id) {
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "delete from SwotActorProperty where propertyID=:propertyID";
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
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * getPropertyByID(int)
	 */
	@Override
	public SwotActorProperty getPropertyByID(int id) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * getPropertyByName(java.lang.String)
	 */
	@Override
	public List<SwotActorProperty> getPropertyByName(String nameString) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * getPropertyByActor(cn.whu.forum.analysis.entities.SwotActor)
	 */
	@Override
	public List<SwotActorProperty> getPropertyByActor(SwotActor swotActor) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * getPropertyByActorID(int)
	 */
	@Override
	public List<SwotActorProperty> getPropertyByActorID(int actorID) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface#
	 * getAllPropertys()
	 */
	@Override
	public List<SwotActorProperty> getAllPropertys() {
		return null;
	}
}
