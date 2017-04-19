package zlbyzc.sub3.analysis.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import zlbyzc.sub3.analysis.entities.ScenarioProperty;
import zlbyzc.sub3.analysis.entities.ScenarioTask;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioPropertyDAOInterface;
import db.HibernateTool;



public class ScenarioPropertyDAO implements ScenarioPropertyDAOInterface {

	@Override
	public boolean addProperty(ScenarioTask scenarioTask,
			ScenarioProperty scenarioProperty) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			scenarioProperty.setScenarioTask(scenarioTask);
			session.saveOrUpdate(scenarioProperty);
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
	public boolean addPropertis(ScenarioTask scenarioTask,
			Set<ScenarioProperty>  properties) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProperty(ScenarioProperty scenarioProperty) {
	
		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="update ScenarioProperty set propertyContent=:propertyContent where propertyID=:propertyID";	
			Query query = session.createQuery(hql);
			query.setParameter("propertyContent", scenarioProperty.getPropertyContent());
			query.setParameter("propertyID", scenarioProperty.getPropertyID());
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
	public boolean deleteProperty(ScenarioProperty scenarioProperty) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePropertyByID(int id) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="delete from ScenarioProperty where propertyID=:propertyID";	
			Query query = session.createQuery(hql);
			query.setParameter("propertyID", id);
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
	public ScenarioProperty getPropertyByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScenarioProperty> getPropertyByName(String nameString) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioProperty> getAllPropertys(ScenarioTask scenarioTask) {
		Session session = null;
		Transaction transaction = null;
		List<ScenarioProperty> scenarioPropertyList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from ScenarioProperty where scenarioTask=:scenarioTask";
			Query query = session.createQuery(hqlString);
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

}
