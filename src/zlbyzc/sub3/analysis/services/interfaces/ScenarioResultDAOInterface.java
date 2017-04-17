package zlbyzc.sub3.analysis.services.interfaces;

import java.util.List;
import java.util.Set;

import zlbyzc.sub3.analysis.entities.ScenarioResult;
import zlbyzc.sub3.analysis.entities.ScenarioTask;

public interface ScenarioResultDAOInterface {
	public boolean addResult(ScenarioTask scenarioTask,
			ScenarioResult scenarioResult);
	public boolean addPropertis(ScenarioTask scenarioTask,
			Set<ScenarioResult>  properties);
	public boolean updateResult(ScenarioResult scenarioResult);
	public boolean deleteResult(ScenarioResult scenarioResult);
	public boolean deleteResultByID(int id);
	public ScenarioResult getResultByID(int id);
	public List<ScenarioResult> getResultByName(String nameString);
	public List<ScenarioResult> getAllResults();
}
