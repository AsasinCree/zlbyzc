package cn.whu.forum.analysis.entities;

import java.io.Serializable;
import java.util.Set;

public class SwotActor implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//SWOT法的案例的参与者编号
	private int actorID;
	//依附的SWOT法的案例实例
	private SwotTask swotTask;
	//SWOT法的案例的参与者名称
	private String actorName;
	//SWOT法的案例的参与者描述
	private String actorDescription;
	//SWOT法的案例的参与者的第一个备注
	private String mark1;
	//SWOT法的案例的参与者的第二个备注
	private String mark2;
	//SWOT法的案例的参与者的属性字段集合
	private Set<SwotActorProperty> swotActorProperties;

	//默认（空）的构造函数
	public SwotActor(){
		
	}

	//包含所有属性初始化的构造函数
	public SwotActor(int actorID, SwotTask swotTask, String actorName,
			String actorDescription, String mark1, String mark2,
			Set<SwotActorProperty> swotActorProperties) {
		super();
		this.actorID = actorID;
		this.swotTask = swotTask;
		this.actorName = actorName;
		this.actorDescription = actorDescription;
		this.mark1 = mark1;
		this.mark2 = mark2;
		this.swotActorProperties = swotActorProperties;
	}

	//获得SWOT法的案例的参与者编号
	public int getActorID() {
		return actorID;
	}

	//设置SWOT法的案例的参与者编号
	public void setActorID(int actorID) {
		this.actorID = actorID;
	}

	//获得依附的SWOT法的案例实例
	public SwotTask getSwotTask() {
		return swotTask;
	}

	//设置依附的SWOT法的案例实例
	public void setSwotTask(SwotTask swotTask) {
		this.swotTask = swotTask;
	}

	//获得SWOT法的案例的参与者名称
	public String getActorName() {
		return actorName;
	}

	//设置SWOT法的案例的参与者名称
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	//获得SWOT法的案例的参与者描述
	public String getActorDescription() {
		return actorDescription;
	}
	
	//设置SWOT法的案例的参与者描述
	public void setActorDescription(String actorDescription) {
		this.actorDescription = actorDescription;
	}

	//设置SWOT法的案例的参与者的第一个备注
	public String getMark1() {
		return mark1;
	}

	//获得SWOT法的案例的参与者的第一个备注
	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	//设置SWOT法的案例的参与者的第二个备注
	public String getMark2() {
		return mark2;
	}

	//获得SWOT法的案例的参与者的第二个备注
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	//获得SWOT法的案例的参与者的属性字段集合
	public Set<SwotActorProperty> getSwotActorProperties() {
		return swotActorProperties;
	}

	//设置SWOT法的案例的参与者的属性字段集合
	public void setSwotActorProperties(Set<SwotActorProperty> swotActorProperties) {
		this.swotActorProperties = swotActorProperties;
	}

	//以字符串形式拼接所有属性名称及内容
	@Override
	public String toString() {
		return "SwotActor [actorID=" + actorID + ", swotTask=" 
				+ ", actorName=" + actorName + ", actorDescription="
				+ actorDescription + ", mark1=" + mark1 + ", mark2=" + mark2
				+ "]";
	}
}
