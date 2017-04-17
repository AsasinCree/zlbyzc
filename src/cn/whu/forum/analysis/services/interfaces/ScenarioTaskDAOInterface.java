package cn.whu.forum.analysis.services.interfaces;

import java.util.Date;
import java.util.List;

import cn.whu.forum.analysis.entities.ScenarioLogic;
import cn.whu.forum.analysis.entities.ScenarioProperty;
import cn.whu.forum.analysis.entities.ScenarioResult;
import cn.whu.forum.analysis.entities.ScenarioTask;

public interface ScenarioTaskDAOInterface {
	public boolean addTask(ScenarioTask scenarioTask);
	public boolean updateTask(ScenarioTask scenarioTask);
	public boolean deleteTask(ScenarioTask scenarioTask);
	public boolean deleteTaskByID(int id);
	public ScenarioTask getScenarioTaskByID(int id);
	public List<ScenarioTask> getScenarioTaskByName(String nameString);
	public List<ScenarioTask> getAllScenarioTasks();
	List<ScenarioProperty> getAllTaskProperties(ScenarioTask scenarioTask);
	List<ScenarioLogic> getAllTaskLogics(ScenarioTask scenarioTask);
	List<ScenarioResult> getAllTaskResults(ScenarioTask scenarioTask);
	List<ScenarioTask> getScenarioTaskByDate(Date startDate, Date endDate);
	List<String> getAllScenarioTasksLocation();
	List<ScenarioTask> getScenarioTaskByLocation(String locationString);
}
