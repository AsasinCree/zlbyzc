package cn.whu.forum.analysis.entities;

import java.io.Serializable;

public class ScenarioResult implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//情景分析法的结果字段编号
	private int resultID;
	//依附的情景分析法案例实例
	private ScenarioTask scenarioTask;
	//情景分析法的结果字段内容
	private String resultContent;
	//情景分析法的结果字段第一个备注
	private String mark1;
	//情景分析法的结果字段第二个备注
	private String mark2;

	//默认（空）的构造函数
	public ScenarioResult(){
		
	}

	//包含所有属性初始化的构造函数
	public ScenarioResult(int resultID, ScenarioTask scenarioTask,
			String resultContent, String mark1, String mark2) {
		super();
		this.resultID = resultID;
		this.scenarioTask = scenarioTask;
		this.resultContent = resultContent;
		this.mark1 = mark1;
		this.mark2 = mark2;
	}

	//获得情景分析法的结果字段编号
	public int getResultID() {
		return resultID;
	}

	//设置情景分析法的结果字段编号
	public void setResultID(int resultID) {
		this.resultID = resultID;
	}

	//获得依附的情景分析法案例实例
	public ScenarioTask getScenarioTask() {
		return scenarioTask;
	}

	//设置依附的情景分析法案例实例
	public void setScenarioTask(ScenarioTask scenarioTask) {
		this.scenarioTask = scenarioTask;
	}

	//获得情景分析法的结果字段内容
	public String getResultContent() {
		return resultContent;
	}

	//设置情景分析法的结果字段内容
	public void setResultContent(String resultContent) {
		this.resultContent = resultContent;
	}

	//获得情景分析法的结果字段第一个备注
	public String getMark1() {
		return mark1;
	}

	//设置情景分析法的结果字段第一个备注
	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	//获得情景分析法的结果字段第二个备注
	public String getMark2() {
		return mark2;
	}

	//设置情景分析法的结果字段第二个备注
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	//以字符串形式拼接所有属性名称及内容
	@Override
	public String toString() {
		return "ScenarioResult [resultID=" + resultID + ", scenarioTask="
				+ scenarioTask + ", resultContent=" + resultContent
				+ ", mark1=" + mark1 + ", mark2=" + mark2 + "]";
	}
}
