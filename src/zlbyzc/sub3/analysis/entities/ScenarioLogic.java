package zlbyzc.sub3.analysis.entities;

import java.io.Serializable;

public class ScenarioLogic implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int logicID;
	private ScenarioTask scenarioTask;
	private String logicContent;
	private String mark1;
	private String mark2;
	
	public ScenarioLogic(){
		
	}
	
	public ScenarioLogic(int logicID, ScenarioTask scenarioTask,
			String logicContent, String mark1, String mark2) {
		super();
		this.logicID = logicID;
		this.scenarioTask = scenarioTask;
		this.logicContent = logicContent;
		this.mark1 = mark1;
		this.mark2 = mark2;
	}

	public int getLogicID() {
		return logicID;
	}

	public void setLogicID(int logicID) {
		this.logicID = logicID;
	}

	public ScenarioTask getScenarioTask() {
		return scenarioTask;
	}

	public void setScenarioTask(ScenarioTask scenarioTask) {
		this.scenarioTask = scenarioTask;
	}

	public String getLogicContent() {
		return logicContent;
	}

	public void setLogicContent(String logicContent) {
		this.logicContent = logicContent;
	}

	public String getMark1() {
		return mark1;
	}

	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	public String getMark2() {
		return mark2;
	}

	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	@Override
	public String toString() {
		return "ScenarioLogic [logicID=" + logicID + ", scenarioTask="
				+ scenarioTask + ", logicContent=" + logicContent + ", mark1="
				+ mark1 + ", mark2=" + mark2 + "]";
	}

	
	
}
