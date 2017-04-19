package zlbyzc.sub3.analysis.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import zlbyzc.sub3.analysis.entities.ScenarioLogic;
import zlbyzc.sub3.analysis.entities.ScenarioResult;
import zlbyzc.sub3.analysis.entities.ScenarioTask;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioResultDAOInterface;
import db.HibernateTool;



public class ScenarioResultDAO implements ScenarioResultDAOInterface {

	@Override
	public boolean addResult(ScenarioTask scenarioTask,
			ScenarioResult scenarioResult) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			scenarioResult.setScenarioTask(scenarioTask);
			session.saveOrUpdate(scenarioResult);
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
			Set<ScenarioResult>  properties) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateResult(ScenarioResult scenarioResult) {
	
		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="update ScenarioResult set resultContent=:resultContent where resultID=:resultID";	
			Query query = session.createQuery(hql);
			query.setParameter("resultContent", scenarioResult.getResultContent());
			query.setParameter("resultID", scenarioResult.getResultID());
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
	public boolean deleteResult(ScenarioResult scenarioResult) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteResultByID(int id) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="delete from ScenarioResult where resultID=:resultID";	
			Query query = session.createQuery(hql);
			query.setParameter("resultID", id);
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
	public ScenarioResult getResultByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScenarioResult> getResultByName(String nameString) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ScenarioResult> getAllResults(ScenarioTask scenarioTask) {
		Session session = null;
		Transaction transaction = null;
		List<ScenarioResult> scenarioResultList = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hqlString = "from ScenarioResult where scenarioTask=:scenarioTask";
			Query query = session.createQuery(hqlString);
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
