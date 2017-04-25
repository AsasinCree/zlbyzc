package cn.whu.forum.analysis.panels;

/**
 * 复选框列表类，集成checkbox和list
 * 
 * @author asasi
 *
 */
public class SwotCheckboxListItem {	
	//显示的文本标签
	private String label;
	//表示是否选中
	public boolean isSelected = false;
	//参与者编号
	private int actorID = 0;		
	
	/**
	 * 构造函数
	 * @param label
	 * 显示的文本内容
	 */
	public SwotCheckboxListItem(String label)
	{
		this.label = label;
	}
	
	/**
	 * @return
	 * 是否选中状态
	 */
	public boolean isSelected()
	{
		return isSelected;
	}
	
	/**
	 * 设置选中状态
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}
	
	/**
	 * 设置参与者编号
	 * @param actorID
	 */
	public void setActorID(int actorID)
	{
		this.actorID = actorID;
	}
	
	/**
	 * 获取参与者编号
	 * @return
	 */
	public int getActorID()
	{
		return actorID;
	}
	
	/** 
	 * 获取文本内容
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return label;
	}
}
