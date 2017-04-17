package zlbyzc.sub3.analysis.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import zlbyzc.sub3.analysis.entities.ScenarioLogic;
import zlbyzc.sub3.analysis.entities.ScenarioTask;
import zlbyzc.sub3.analysis.tools.HibernateTool;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioLogicDAOInterface;


public class ScenarioLogicDAO implements ScenarioLogicDAOInterface {

	@Override
	public boolean addLogic(ScenarioTask scenarioTask,
			ScenarioLogic scenarioLogic) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			scenarioLogic.setScenarioTask(scenarioTask);
			session.saveOrUpdate(scenarioLogic);
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
	public boolean addLogics(ScenarioTask scenarioTask,
			Set<ScenarioLogic> logics) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateLogic(ScenarioLogic scenarioLogic) {
	
		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="update ScenarioLogic set logicContent=:logicContent where logicID=:logicID";	
			Query query = session.createQuery(hql);
			query.setParameter("logicContent", scenarioLogic.getLogicContent());
			query.setParameter("logicID", scenarioLogic.getLogicID());
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
	public boolean deleteLogic(ScenarioLogic scenarioLogic) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteLogicByID(int id) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="delete from ScenarioLogic where logicID=:logicID";	
			Query query = session.createQuery(hql);
			query.setParameter("logicID", id);
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
	public ScenarioLogic getLogicByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScenarioLogic> getLogicByName(String nameString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScenarioLogic> getAllLogics() {
		// TODO Auto-generated method stub
		return null;
	}

}
