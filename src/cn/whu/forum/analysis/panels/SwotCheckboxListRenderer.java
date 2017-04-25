package cn.whu.forum.analysis.panels;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * 复选框列表渲染类，集成checkbox和list
 * @author asasi
 *
 */
public class SwotCheckboxListRenderer extends JCheckBox implements ListCellRenderer<SwotCheckboxListItem>{
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(
			JList<? extends SwotCheckboxListItem> list, SwotCheckboxListItem value,
			int index, boolean isSelected, boolean cellHasFocus) {
		//针对JCheckBox设置，返回JCheckBox来填充JList列表内容
		setEnabled(list.isEnabled());
	    setSelected(value.isSelected());
	    setFont(StaticConfig.FONT_EDITVIEW_LIST);
	    setBackground(list.getBackground());
	    setForeground(list.getForeground());
		setText(value.toString());
	    
	    return this;		
	}
}
