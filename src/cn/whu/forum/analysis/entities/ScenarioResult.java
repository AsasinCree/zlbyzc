package cn.whu.forum.analysis.entities;

import java.io.Serializable;

public class ScenarioResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int resultID;
	private ScenarioTask scenarioTask;
	private String resultContent;
	private String mark1;
	private String mark2;
	
	public ScenarioResult(){
		
	}
	
	public ScenarioResult(int resultID, ScenarioTask scenarioTask,
			String resultContent, String mark1, String mark2) {
		super();
		this.resultID = resultID;
		this.scenarioTask = scenarioTask;
		this.resultContent = resultContent;
		this.mark1 = mark1;
		this.mark2 = mark2;
	}

	public int getResultID() {
		return resultID;
	}

	public void setResultID(int resultID) {
		this.resultID = resultID;
	}

	public ScenarioTask getScenarioTask() {
		return scenarioTask;
	}

	public void setScenarioTask(ScenarioTask scenarioTask) {
		this.scenarioTask = scenarioTask;
	}

	public String getResultContent() {
		return resultContent;
	}

	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
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
		return "ScenarioResult [resultID=" + resultID + ", scenarioTask="
				+ scenarioTask + ", resultContent=" + resultContent
				+ ", mark1=" + mark1 + ", mark2=" + mark2 + "]";
	}
	
		

}
