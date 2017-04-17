package zlbyzc.sub3.analysis.services.interfaces;

import java.util.List;
import java.util.Set;

import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;


public interface SwotPropertyDAOInterface {
	public boolean addProperty(SwotActor swotActor,SwotActorProperty swotActorProperty);
	public boolean addPropertis(SwotActor swotActor, Set<SwotActorProperty> swotActorPropertis);
	public boolean updateProperty(SwotActorProperty swotActorProperty);
	public boolean deleteProperty(SwotActorProperty swotActorProperty);
	public boolean deletePropertyByID(int id);
	public SwotActorProperty getPropertyByID(int id);
	public List<SwotActorProperty> getPropertyByName(String nameString);
	public List<SwotActorProperty> getPropertyByActor(SwotActor swotActor);
	public List<SwotActorProperty> getPropertyByActorID(int actorID);
	public List<SwotActorProperty> getAllPropertys();
}
