﻿package zlbyzc.sub3.analysis.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.DatePicker;
import org.jdatepicker.JDatePicker;

import zlbyzc.sub3.analysis.entities.ScenarioTask;
import zlbyzc.sub3.analysis.services.ScenarioTaskDAO;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioTaskDAOInterface;

public class ScenarioNewPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ScenarioTaskDAOInterface scenarioTaskDAO;

	JInternalFrame frame;
	
	private JPanel panelLayTaskName;
	private JPanel panelLayTaskDescription;
	private JPanel panelLayArgueTime;
	private JPanel panelLayTaskLocation;
	private JPanel panelLayButton;
	
	//四个标签
	private JLabel labelTaskName;
	private JLabel labelTaskDescription;
	private JLabel labelArgueTime;
	private JLabel labelTaskLocation;
	
	//两个文本框、日期选取框、地点下拉框
	private JTextField textfieldfTaskName;
	private JTextField textfieldTaskDescription;
	private DatePicker datePickerArgueTime;
	private JComboBox<String> comboBoxTaskLocation;
	
	//三个按钮
	private JButton buttonConfirm;
	private JButton buttonReset;
	private JButton buttonCancel;
	
	public ScenarioNewPanel(JInternalFrame jif) {
		
		scenarioTaskDAO = new ScenarioTaskDAO();
		this.frame = jif;
				
		initializeComponent();
		
		layoutComponent();

	}
	
	private void initializeComponent() {
		
		//JPanel设置
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelLayTaskName = new JPanel();
		panelLayTaskDescription = new JPanel();
		panelLayArgueTime = new JPanel();
		panelLayTaskLocation = new JPanel();
		panelLayButton = new JPanel();
		
		panelLayTaskName.setLayout(new BoxLayout(panelLayTaskName, BoxLayout.X_AXIS));
		panelLayTaskDescription.setLayout(new BoxLayout(panelLayTaskDescription, BoxLayout.X_AXIS));
		panelLayArgueTime.setLayout(new BoxLayout(panelLayArgueTime, BoxLayout.X_AXIS));
		panelLayTaskLocation.setLayout(new BoxLayout(panelLayTaskLocation, BoxLayout.X_AXIS));
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));
		
		labelTaskName = new JLabel("任务名称");
		labelTaskDescription = new JLabel("任务描述");
		labelArgueTime = new JLabel("研讨日期");
		labelTaskLocation = new JLabel("研讨地点");
		
		textfieldfTaskName = new JTextField();
		textfieldTaskDescription = new JTextField();
		
		datePickerArgueTime = new JDatePicker();
		
		comboBoxTaskLocation = new JComboBox<String> ();
		comboBoxTaskLocation.setEditable(true);	//可输入。得到输入值的方法： getEditor().getItem(); 
		List<String> swotTaskLocationList = scenarioTaskDAO.getAllScenarioTasksLocation();
		for(String location:swotTaskLocationList)
			comboBoxTaskLocation.addItem(location);
		comboBoxTaskLocation.setSelectedItem(null);
		
		textfieldfTaskName.setMinimumSize(new Dimension(100,25));
		textfieldfTaskName.setMaximumSize(new Dimension(150,30));
		textfieldTaskDescription.setMinimumSize(new Dimension(100,25));
		textfieldTaskDescription.setMaximumSize(new Dimension(150,30));
		((Component) datePickerArgueTime).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueTime).setMaximumSize(new Dimension(150,30));
		comboBoxTaskLocation.setMinimumSize(new Dimension(100,25));
		comboBoxTaskLocation.setMaximumSize(new Dimension(150,30));
		
		buttonConfirm = new JButton("确定");
		buttonReset = new JButton("重置");
		buttonCancel = new JButton("取消");
		
		buttonConfirm.addActionListener(this);
		buttonReset.addActionListener(this);
		buttonCancel.addActionListener(this);
		
	}
	
	public void layoutComponent() {
		
		panelLayTaskName.add(labelTaskName);		
		panelLayTaskName.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayTaskName.add(textfieldfTaskName);
		
		panelLayTaskDescription.add(labelTaskDescription);		
		panelLayTaskDescription.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayTaskDescription.add(textfieldTaskDescription);
		
		panelLayArgueTime.add(labelArgueTime);
		panelLayArgueTime.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayArgueTime.add((Component) datePickerArgueTime);
		
		panelLayTaskLocation.add(labelTaskLocation);
		panelLayTaskLocation.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayTaskLocation.add(comboBoxTaskLocation);
		
		panelLayButton.add(buttonConfirm);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonReset);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonCancel);						

		add( Box.createVerticalGlue() );
		add(panelLayTaskName);
		add(Box.createVerticalStrut(10));  		
		add(panelLayTaskDescription);
		add(Box.createVerticalStrut(10));  		
		add(panelLayArgueTime);
		add(Box.createVerticalStrut(10));  		
		add(panelLayTaskLocation);
		add(Box.createVerticalStrut(10));  		
		add(panelLayButton);
		add( Box.createVerticalGlue() );
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == buttonConfirm) {
			
			Calendar calendar = Calendar.getInstance();
            calendar.set(datePickerArgueTime.getModel().getYear(), datePickerArgueTime.getModel().getMonth(), 
            		datePickerArgueTime.getModel().getDay(), 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
			
			ScenarioTask scenarioTask = new ScenarioTask();
			
			if(textfieldfTaskName.getText() != null)
				scenarioTask.setTaskName(textfieldfTaskName.getText());
			else 
				scenarioTask.setTaskName("");
			
			if(textfieldTaskDescription.getText() != null)
				scenarioTask.setTaskDescription(textfieldTaskDescription.getText());
			else 
				scenarioTask.setTaskDescription("");
		
			if(datePickerArgueTime.getModel().getValue() != null)
				scenarioTask.setArgueTime(calendar.getTime());
			else 
				scenarioTask.setArgueTime(null);
			
			if(comboBoxTaskLocation.getSelectedItem() != null)
				scenarioTask.setTaskLocation(comboBoxTaskLocation.getSelectedItem().toString());
			else
				scenarioTask.setTaskLocation("");
			
			scenarioTaskDAO.addTask(scenarioTask);	
			
			frame.setContentPane(new ScenarioEditPanel(scenarioTask));
			frame.revalidate();
			frame.repaint(); 
			
		}
		else if(e.getSource() == buttonReset) {
			
			//清空
			textfieldfTaskName.setText("");
			textfieldTaskDescription.setText("");
			datePickerArgueTime.getModel().setValue(null);
			comboBoxTaskLocation.setSelectedItem(null);
			
		}
		else if(e.getSource() == buttonCancel) {
			
			//取消新建后续步骤
			frame.setContentPane(new JPanel());
			frame.revalidate();
			frame.repaint();
			
		}		
		
	}
}