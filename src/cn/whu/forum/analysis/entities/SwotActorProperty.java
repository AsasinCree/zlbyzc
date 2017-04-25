package cn.whu.forum.analysis.entities;

import java.io.Serializable;

public class SwotActorProperty implements Serializable {
	
	private static final long serialVersionUID = 1L;

	//SWOT法的案例的参与者的属性编号
	private int propertyID;
	//依附的SWOT法的案例的参与者实例
	private SwotActor swotActor;
	//SWOT法的案例的参与者的属性类型
	private String propertyType;
	//SWOT法的案例的参与者的属性内容
	private String propertyContent;
	//SWOT法的案例的参与者的属性的第一个备注
	private String mark1;
	//SWOT法的案例的参与者的属性的第二个备注
	private String mark2;

	//默认（空）的构造函数
	public SwotActorProperty(){
		
	}
	
	//包含所有属性初始化的构造函数
	public SwotActorProperty(int propertyID, SwotActor swotActor,
			String propertyType, String propertyContent, String mark1,
			String mark2) {
		super();
		this.propertyID = propertyID;
		this.swotActor = swotActor;
		this.propertyType = propertyType;
		this.propertyContent = propertyContent;
		this.mark1 = mark1;
		this.mark2 = mark2;
	}

	//获得SWOT法的案例的参与者的属性编号
	public int getPropertyID() {
		return propertyID;
	}

	//设置SWOT法的案例的参与者的属性编号
	public void setPropertyID(int propertyID) {
		this.propertyID = propertyID;
	}

	//获得SWOT法的案例的参与者实例
	public SwotActor getSwotActor() {
		return swotActor;
	}

	//设置SWOT法的案例的参与者实例
	public void setSwotActor(SwotActor swotActor) {
		this.swotActor = swotActor;
	}

	//获得SWOT法的案例的参与者的属性类型
	public String getPropertyType() {
		return propertyType;
	}

	//设置SWOT法的案例的参与者的属性类型
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	//获得SWOT法的案例的参与者的属性内容
	public String getPropertyContent() {
		return propertyContent;
	}

	//设置SWOT法的案例的参与者的属性内容
	public void setPropertyContent(String propertyContent) {
		this.propertyContent = propertyContent;
	}

	//获得SWOT法的案例的参与者的属性的第一个备注
	public String getMark1() {
		return mark1;
	}

	//设置SWOT法的案例的参与者的属性的第一个备注
	public void setMark1(String mark1) {
		this.mark1 = mark1;
	}

	//获得SWOT法的案例的参与者的属性的第二个备注
	public String getMark2() {
		return mark2;
	}

	//设置SWOT法的案例的参与者的属性的第二个备注
	public void setMark2(String mark2) {
		this.mark2 = mark2;
	}

	//以字符串形式拼接所有属性名称及内容
	@Override
	public String toString() {
		return "SwotActorProperty [propertyID=" + propertyID + ", propertyType=" + propertyType
		+ ", propertyContent=" + propertyContent + ", mark1=" + mark1
		+ ", mark2=" + mark2 + "]";
	}
}
