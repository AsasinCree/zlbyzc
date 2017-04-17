﻿package zlbyzc.sub3.analysis.panels;

public class SwotCheckboxListItem {				//复选框列表类
	private String label;
	public boolean isSelected = false;
	private int actorID = 0;		//参与者编号
	
	public SwotCheckboxListItem(String label)
	{
		this.label = label;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}
	
	public void setActorID(int actorID)
	{
		this.actorID = actorID;
	}
	
	public int getActorID()
	{
		return actorID;
	}
	
	public String toString()
	{
		return label;
	}
}
