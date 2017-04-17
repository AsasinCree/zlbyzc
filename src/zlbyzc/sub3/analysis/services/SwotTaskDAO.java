package zlbyzc.sub3.analysis.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotTask;
import zlbyzc.sub3.analysis.services.interfaces.SwotTaskDAOInterface;
import zlbyzc.sub3.analysis.tools.HibernateTool;

public class SwotTaskDAO implements SwotTaskDAOInterface {

	@Override
	public boolean addTask(SwotTask swotTask) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			session.saveOrUpdate(swotTask);
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
	public boolean updateTask(SwotTask swotTask) {
		
		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="update SwotTask set taskName=:taskName, taskDescription=:taskDescription, argueTime=:argueTime, taskLocation=:taskLocation where taskID=:taskID";	
			Query query = session.createQuery(hql);
			query.setParameter("taskName", swotTask.getTaskName());
			query.setParameter("taskDescription", swotTask.getTaskDescription());
			query.setParameter("argueTime", swotTask.getArgueTime());
			query.setParameter("taskLocation", swotTask.getTaskLocation());
			query.setParameter("taskID", swotTask.getTaskID());
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
	public boolean deleteTask(SwotTask swotTask) {
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
			String hql="delete from SwotTask where taskID=:taskID";	
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
	public SwotTask getSwotTaskByID(int taskID) {

		Session session = null;
		Transaction transaction = null;
		SwotTask swotTask = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="from SwotTask where taskID=:taskID";	
			Query query = session.createQuery(hql);
			query.setParameter("taskID", taskID);
			List<SwotTask> swotTaskList = (List<SwotTask>)query.list();
			swotTask = swotTaskList.get(0);
			
			transaction.commit();
			return swotTask;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return swotTask;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SwotTask> getSwotTaskByName(String nameString) {

		Session session = null;
		Transaction transaction = null;
		List<SwotTask> swotTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="from SwotTask where taskName like :taskName";	
			Query query = session.createQuery(hql);
			query.setParameter("taskName", "%"+nameString+"%");
			swotTaskList = (List<SwotTask>)query.list();
			
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SwotTask> getSwotTaskByLocation(String locationString) {

		Session session = null;
		Transaction transaction = null;
		List<SwotTask> swotTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="from SwotTask where taskLocation like :taskLocation";	
			Query query = session.createQuery(hql);
			query.setParameter("taskLocation", "%"+locationString+"%");
			swotTaskList = (List<SwotTask>)query.list();
			
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

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<SwotTask> getSwotTaskByDate(Date startDate, Date endDate) {

		Session session = null;
		Transaction transaction = null;
		List<SwotTask> swotTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql = "";
			Query query = null;
			
			if(startDate == null && endDate != null ) {
				hql = "from SwotTask where argueTime <:endDate";
				query = session.createQuery(hql);
				query.setParameter("endDate", endDate);
			}
			else if(startDate != null && endDate == null) {
				hql = "from SwotTask where argueTime >:startDate";	
				query = session.createQuery(hql);
				query.setParameter("startDate", startDate);
			}
			else if(startDate == null && endDate == null) {
				hql = "from SwotTask";	
				query = session.createQuery(hql);
			}
			else {
				hql = "from SwotTask where argueTime between :startDate and :endDate";
				query = session.createQuery(hql);
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
			}

			swotTaskList = (List<SwotTask>)query.list();
			
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

	@SuppressWarnings("unchecked")
	@Override
	public List<SwotTask> getAllSwotTasks() {

		Session session = null;
		Transaction transaction = null;
		List<SwotTask> swotTaskList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from SwotTask";
			Query query = session.createQuery(hqlString);
			swotTaskList = (List<SwotTask>)query.list();
			
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllSwotTasksLocation() {

		Session session = null;
		Transaction transaction = null;
		List<String> swotTaskLocationList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "select distinct taskLocation from SwotTask order by taskID desc";		//按任务ID降序
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
	public List<SwotActor> getAllTaskActors(SwotTask swotTask) {

		Session session = null;
		Transaction transaction = null;
		List<SwotActor> swotActorList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from SwotActor where swotTask=:swotTask";
			Query query = session.createQuery(hqlString);
			query.setParameter("swotTask", swotTask);
			swotActorList = (List<SwotActor>)query.list();
			
			transaction.commit();
			return swotActorList;	
			
		}catch(Exception e){			
			e.printStackTrace();	
			transaction.commit();
			return swotActorList;
			
		}finally{			
			if(transaction != null)
				transaction =null;
			if(session != null)
				HibernateTool.closeSession(session);
		}
		
	}

}
