﻿package cn.whu.forum.analysis.services.interfaces;

import java.util.List;
import java.util.Set;

import cn.whu.forum.analysis.entities.ScenarioProperty;
import cn.whu.forum.analysis.entities.ScenarioTask;

public interface ScenarioPropertyDAOInterface {
	public boolean addProperty(ScenarioTask scenarioTask,
			ScenarioProperty scenarioProperty);
	public boolean addPropertis(ScenarioTask scenarioTask,
			Set<ScenarioProperty>  properties);
	public boolean updateProperty(ScenarioProperty scenarioProperty);
	public boolean deleteProperty(ScenarioProperty scenarioProperty);
	public boolean deletePropertyByID(int id);
	public ScenarioProperty getPropertyByID(int id);
	public List<ScenarioProperty> getPropertyByName(String nameString);
	public List<ScenarioProperty> getAllPropertys();
}
