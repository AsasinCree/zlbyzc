package zlbyzc.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import zlbyzc.BasicRibbon;
import zlbyzc.mainDesk;
import zlbyzc.sub3.sub3inFrame;

import javax.swing.JList;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.event.ListSelectionListener;

import jdk.nashorn.internal.runtime.Context.BuiltinSwitchPoint;

import javax.swing.event.ListSelectionEvent;

public class mangeFrames extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private BasicRibbon BR;
	private DefaultListModel<String> defaultListModel;
	JList<String> list;
	private JInternalFrame[] frames;
	private mainDesk desktop;
	private JButton btnKillAll;
	private JButton btnKill;
	private JButton btnSwitch;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		try {
			mangeFrames dialog = new mangeFrames(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public mangeFrames(BasicRibbon _br) {
		this.BR=_br;
		setBounds(100, 100, 547, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			list = new JList<String>();			
			
		    genListModel();
		    list.setModel(defaultListModel);  
			contentPanel.add(list);
		}
		{
			JPanel buttonPane00 = new JPanel();
			buttonPane00.setLayout(new BoxLayout(buttonPane00, BoxLayout.X_AXIS));
			{
				btnKillAll = new JButton("关闭所有窗口");
				btnKillAll.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						for (JInternalFrame theframe:frames){
							theframe.dispose();							
							//desktop.remove(theframe);
						}
						defaultListModel.removeAllElements();
						btnKillAll.setEnabled(false);
						dispose();
					}
				});
				buttonPane00.add(btnKillAll);
				btnKillAll.setEnabled(false);
			}
			{
				Component horizontalGlue = Box.createHorizontalGlue();
				buttonPane00.add(horizontalGlue);
			}
			JPanel buttonPane = new JPanel();
			buttonPane00.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane00, BorderLayout.SOUTH);
			{
				btnSwitch = new JButton("切换到该窗口");
				btnSwitch.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int sel=list.getSelectedIndex();
						if(sel!=-1){
							frames[sel].moveToFront();
							desktop.setSelectedFrame(frames[sel]);
						}
						dispose();
					}
				});
				btnSwitch.setActionCommand("OK");
				buttonPane.add(btnSwitch);
				btnSwitch.setEnabled(false);
				//getRootPane().setDefaultButton(btnSwitch);
			}
			{
				btnKill = new JButton("关闭该窗口");
				btnKill.addActionListener(new ActionListener() {
					@SuppressWarnings("rawtypes")
					public void actionPerformed(ActionEvent arg0) {
						int[] sels=list.getSelectedIndices();
						for(int sel:sels){						
							frames[sel].dispose();	
							defaultListModel.removeElementAt(sel);
							//desktop.remove(frames[sel]);
						}
						btnKill.setEnabled(false);
						//dispose();
					}
				});
				btnKill.setEnabled(false);
				buttonPane.add(btnKill);
			}
			{
				JButton btnClose = new JButton("关闭本对话框");
				btnClose.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						dispose();
					}
				});
				buttonPane.add(btnClose);
			}
		}
		if(defaultListModel.size()>0)
			btnKillAll.setEnabled(true);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				btnSwitch.setEnabled(true);
				btnKill.setEnabled(true);
				btnKillAll.setEnabled(true);
			}
		});
	}

	private void genListModel() {
		desktop = BR.getDesktopPane();
	    frames = desktop.getAllFrames();
		defaultListModel = new DefaultListModel<String>();  
	    for (int i = 0; i < frames.length; i++) {
		      String title = frames[i].getTitle()+((sub3inFrame)frames[i]).createdTime;
		      defaultListModel.addElement(title);		      
	    }
	}

}
