package zlbyzc.sub3.analysis.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;
import zlbyzc.sub3.analysis.services.interfaces.SwotPropertyDAOInterface;
import zlbyzc.sub3.analysis.tools.HibernateTool;


public class SwotPropertyDAO implements SwotPropertyDAOInterface {

	@Override
	public boolean addProperty(SwotActor swotActor,
			SwotActorProperty swotActorProperty) {

		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			swotActorProperty.setSwotActor(swotActor);
			session.saveOrUpdate(swotActorProperty);
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
	public boolean addPropertis(SwotActor swotActor,
			Set<SwotActorProperty> SwotActorPropertis) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProperty(SwotActorProperty swotActorProperty) {
	
		Session session = null;
		Transaction transaction = null;
		
		try{			
			session = HibernateTool.getSession();
			transaction = (Transaction) session.beginTransaction();
			String hql="update SwotActorProperty set propertyContent=:propertyContent where propertyID=:propertyID";	
			Query query = session.createQuery(hql);
			query.setParameter("propertyContent", swotActorProperty.getPropertyContent());
			query.setParameter("propertyID", swotActorProperty.getPropertyID());
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
	public boolean deleteProperty(SwotActorProperty SwotActorProperty) {
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
			String hql="delete from SwotActorProperty where propertyID=:propertyID";	
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
	public SwotActorProperty getPropertyByID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SwotActorProperty> getPropertyByName(String nameString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SwotActorProperty> getPropertyByActor(SwotActor swotActor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SwotActorProperty> getPropertyByActorID(int actorID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SwotActorProperty> getAllPropertys() {
		// TODO Auto-generated method stub
		return null;
	}



	
}
