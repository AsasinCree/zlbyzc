﻿package cn.whu.forum.analysis.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.whu.forum.analysis.entities.ScenarioLogic;
import cn.whu.forum.analysis.entities.ScenarioProperty;
import cn.whu.forum.analysis.entities.ScenarioResult;
import cn.whu.forum.analysis.entities.ScenarioTask;
import cn.whu.forum.analysis.services.interfaces.ScenarioTaskDAOInterface;
import cn.whu.forum.analysis.tools.HibernateTool;

public class ScenarioTaskDAO implements ScenarioTaskDAOInterface {

	@Override
	public boolean addTask(ScenarioTask scenarioTask) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			session.saveOrUpdate(scenarioTask);
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
	public boolean updateTask(ScenarioTask scenarioTask) {
		
		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="update ScenarioTask set taskName=:taskName, taskDescription=:taskDescription, argueTime=:argueTime, taskLocation=:taskLocation where taskID=:taskID";	
			Query query = session.createQuery(hql);
			query.setParameter("taskName", scenarioTask.getTaskName());
			query.setParameter("taskDescription", scenarioTask.getTaskDescription());
			query.setParameter("argueTime", scenarioTask.getArgueTime());
			query.setParameter("taskLocation", scenarioTask.getTaskLocation());
			query.setParameter("taskID", scenarioTask.getTaskID());
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
	public boolean deleteTask(ScenarioTask scenarioTask) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTaskByID(int taskID) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="delete from ScenarioTask where taskID=:taskID";	
			Query query = session.createQuery(hql);
			query.setParameter("taskID", taskID);
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

	@SuppressWarnings("unchecked")
	@Override
	public ScenarioTask getScenarioTaskByID(int taskID) {

		Session session = null;
		Transaction transaction = null;
		ScenarioTask scenarioTask = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="from ScenarioTask where taskID=:taskID";	
			Query query = session.createQuery(hql);
			query.setParameter("taskID", taskID);
			List<ScenarioTask> scenarioTaskList = (List<ScenarioTask>)query.list();
			scenarioTask = scenarioTaskList.get(0);
			
			transaction.commit();
			return scenarioTask;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return scenarioTask;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioTask> getScenarioTaskByName(String nameString) {

		Session session = null;
		Transaction transaction = null;
		List<ScenarioTask> scenarioTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="from ScenarioTask where taskName like :taskName";	
			Query query = session.createQuery(hql);
			query.setParameter("taskName", "%"+nameString+"%");
			scenarioTaskList = (List<ScenarioTask>)query.list();
			
			transaction.commit();
			return scenarioTaskList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return scenarioTaskList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioTask> getScenarioTaskByLocation(String locationString) {

		Session session = null;
		Transaction transaction = null;
		List<ScenarioTask> scenarioTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="from ScenarioTask where taskLocation like :taskLocation";	
			Query query = session.createQuery(hql);
			query.setParameter("taskLocation", "%"+locationString+"%");
			scenarioTaskList = (List<ScenarioTask>)query.list();
			
			transaction.commit();
			return scenarioTaskList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return scenarioTaskList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ScenarioTask> getScenarioTaskByDate(Date startDate, Date endDate) {

		Session session = null;
		Transaction transaction = null;
		List<ScenarioTask> scenarioTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "";
			Query query = null;
			
			if(startDate == null && endDate != null ) {
				hql = "from ScenarioTask where argueTime <:endDate";
				query = session.createQuery(hql);
				query.setParameter("endDate", endDate);
			}
			else if(startDate != null && endDate == null) {
				hql = "from ScenarioTask where argueTime >:startDate";	
				query = session.createQuery(hql);
				query.setParameter("startDate", startDate);
			}
			else if(startDate == null && endDate == null) {
				hql = "from ScenarioTask";	
				query = session.createQuery(hql);
			}
			else {
				hql = "from ScenarioTask where argueTime between :startDate and :endDate";
				query = session.createQuery(hql);
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
			}

			scenarioTaskList = (List<ScenarioTask>)query.list();
			
			transaction.commit();
			return scenarioTaskList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return scenarioTaskList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioTask> getAllScenarioTasks() {

		Session session = null;
		Transaction transaction = null;
		List<ScenarioTask> scenarioTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "from ScenarioTask";
			Query query = session.createQuery(hql);
			scenarioTaskList = (List<ScenarioTask>)query.list();
			
			transaction.commit();
			return scenarioTaskList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return scenarioTaskList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllScenarioTasksLocation() {

		Session session = null;
		Transaction transaction = null;
		List<String> swotTaskLocationList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "select distinct taskLocation from ScenarioTask order by taskID desc";		//按任务ID降序
			Query query = session.createQuery(hqlString);
			swotTaskLocationList = (List<String>)query.list();
			
			transaction.commit();
			return swotTaskLocationList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return swotTaskLocationList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioProperty> getAllTaskProperties(ScenarioTask scenarioTask) {

		Session session = null;
		Transaction transaction = null;
		List<ScenarioProperty> scenarioPropertyList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "from ScenarioProperty where scenarioTask=:scenarioTask";
			Query query = session.createQuery(hql);
			query.setParameter("scenarioTask", scenarioTask);
			scenarioPropertyList = (List<ScenarioProperty>)query.list();
			
			transaction.commit();
			return scenarioPropertyList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return scenarioPropertyList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioLogic> getAllTaskLogics(ScenarioTask scenarioTask) {

		Session session = null;
		Transaction transaction = null;
		List<ScenarioLogic> scenarioLogicList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "from ScenarioLogic where scenarioTask=:scenarioTask";
			Query query = session.createQuery(hql);
			query.setParameter("scenarioTask", scenarioTask);
			scenarioLogicList = (List<ScenarioLogic>)query.list();
			
			transaction.commit();
			return scenarioLogicList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return scenarioLogicList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioResult> getAllTaskResults(ScenarioTask scenarioTask) {

		Session session = null;
		Transaction transaction = null;
		List<ScenarioResult> scenarioResultList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "from ScenarioResult where scenarioTask=:scenarioTask";
			Query query = session.createQuery(hql);
			query.setParameter("scenarioTask", scenarioTask);
			scenarioResultList = (List<ScenarioResult>)query.list();
			
			transaction.commit();
			return scenarioResultList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return scenarioResultList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}	
	}
}
