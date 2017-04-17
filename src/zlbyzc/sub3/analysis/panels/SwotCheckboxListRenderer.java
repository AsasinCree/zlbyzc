package zlbyzc.sub3.analysis.panels;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class SwotCheckboxListRenderer extends JCheckBox implements ListCellRenderer<SwotCheckboxListItem>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(
			JList<? extends SwotCheckboxListItem> list, SwotCheckboxListItem value,
			int index, boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
		
		//针对JCheckBox设置
		setEnabled(list.isEnabled());
	    setSelected(value.isSelected());
	    setFont(list.getFont());
	    setBackground(list.getBackground());
	    setForeground(list.getForeground());
	    setText(value.toString());
	    
	    return this;		//返回JCheckBox来填充JList列表内容
	}
}
