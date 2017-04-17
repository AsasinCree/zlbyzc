package zlbyzc.sub3.analysis.services.interfaces;

import java.util.List;
import java.util.Set;

import zlbyzc.sub3.analysis.entities.ScenarioLogic;
import zlbyzc.sub3.analysis.entities.ScenarioTask;

public interface ScenarioLogicDAOInterface {
	public boolean addLogic(ScenarioTask scenarioTask,
			ScenarioLogic scenarioLogic);
	public boolean addLogics(ScenarioTask scenarioTask,
			Set<ScenarioLogic> logics);
	public boolean updateLogic(ScenarioLogic scenarioLogic);
	public boolean deleteLogic(ScenarioLogic scenarioLogic);
	public boolean deleteLogicByID(int id);
	public ScenarioLogic getLogicByID(int id);
	public List<ScenarioLogic> getLogicByName(String nameString);
	public List<ScenarioLogic> getAllLogics();
}
