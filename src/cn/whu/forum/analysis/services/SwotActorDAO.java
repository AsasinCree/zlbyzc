﻿package cn.whu.forum.analysis.services;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.entities.SwotTask;
import cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface;
import cn.whu.forum.analysis.tools.HibernateTool;

public class SwotActorDAO implements SwotActorDAOInterface {
	
	@Override
	public boolean addActor(SwotActor swotActor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addActor(SwotTask swotTask, SwotActor swotActor) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			swotActor.setSwotTask(swotTask);
			session.saveOrUpdate(swotActor);
			transaction.commit();
			return true;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return false;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
			
		}
	}

	@Override
	public boolean updateActor(SwotActor SwotActor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteActor(SwotActor SwotActor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteActorByID(int id) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="delete from SwotActor where actorID=:actorID";	
			Query query = session.createQuery(hql);
			query.setParameter("actorID", id);
			query.executeUpdate();
			   
			transaction.commit();
			return true;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return false;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
			
		}
		
	}

	@Override
	public SwotActor getActorByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SwotActor> getActorByName(String nameString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SwotActor> getActorByTask(SwotTask swotTask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SwotActor> getActorByTaskID(int taskID) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SwotActorProperty> getAllActorPropertys(SwotActor swotActor) {

		Session session = null;
		Transaction transaction = null;
		List<SwotActorProperty> swotPropertyList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from SwotActorProperty where swotActor=:swotActor";
			Query query = session.createQuery(hqlString);
			query.setParameter("swotActor", swotActor);
			swotPropertyList = (List<SwotActorProperty>)query.list();
			
			transaction.commit();
			return swotPropertyList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return swotPropertyList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SwotActor> getSwotActorByActorName(String nameString) {

		Session session = null;
		Transaction transaction = null;
		List<SwotActor> swotTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="from SwotActor where actorName like :actorName";	
			Query query = session.createQuery(hql);
			query.setParameter("actorName", "%"+nameString+"%");
			swotTaskList = (List<SwotActor>)query.list();
			
			transaction.commit();
			return swotTaskList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return swotTaskList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
		
	}
		
}
