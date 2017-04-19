package zlbyzc.sub3.analysis.panels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
	
	private JPanel panelLayExampleButton;
	private Calendar calendar;
	private int flag;     //是否为示例日期（日期控件不能设置日期[显示的日期不是设置日期]）
	
	
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
	
	//按钮
	private JButton buttonConfirm;
	private JButton buttonReset;
	private JButton buttonCancel;
	private JButton buttonExample;
	
	public ScenarioNewPanel(JInternalFrame jif) {
		flag = 0;
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
		panelLayExampleButton = new JPanel();
		
		panelLayTaskName.setLayout(new BoxLayout(panelLayTaskName, BoxLayout.X_AXIS));
		panelLayTaskDescription.setLayout(new BoxLayout(panelLayTaskDescription, BoxLayout.X_AXIS));
		panelLayArgueTime.setLayout(new BoxLayout(panelLayArgueTime, BoxLayout.X_AXIS));
		panelLayTaskLocation.setLayout(new BoxLayout(panelLayTaskLocation, BoxLayout.X_AXIS));
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));
		panelLayExampleButton.setLayout(new BoxLayout(panelLayExampleButton, BoxLayout.X_AXIS));	//【Test】
		
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
		buttonExample = new JButton("示例");
		
		buttonConfirm.addActionListener(this);
		buttonReset.addActionListener(this);
		buttonCancel.addActionListener(this);
		buttonExample.addActionListener(this);
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
		
		panelLayExampleButton.add(buttonExample);

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
		add(Box.createVerticalStrut(10));  		
		add(panelLayExampleButton);
		add( Box.createVerticalGlue() );	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == buttonConfirm) {
			
			if(flag == 0){		//不是示例
				calendar = Calendar.getInstance();
	            calendar.set(datePickerArgueTime.getModel().getYear(), 		
	            		datePickerArgueTime.getModel().getMonth(), datePickerArgueTime.getModel().getDay(), 0, 0, 0);
	            calendar.set(Calendar.MILLISECOND, 0);
			}
			
			ScenarioTask scenarioTask = new ScenarioTask();
			
			if(textfieldfTaskName.getText() != null)
				scenarioTask.setTaskName(textfieldfTaskName.getText());
			else 
				scenarioTask.setTaskName("");
			
			if(textfieldTaskDescription.getText() != null)
				scenarioTask.setTaskDescription(textfieldTaskDescription.getText());
			else 
				scenarioTask.setTaskDescription("");
		
			if(datePickerArgueTime.getModel().getValue() != null || flag == 1)
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
		else if(e.getSource() == buttonExample) {		
			flag = 1;
			//快速填写示例
			textfieldfTaskName.setText("邛海环境规划");
			textfieldTaskDescription.setText("邛海环境规划");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
			String stringDate = "2016-7-15";		//自定义日期
			Date date = new Date();
			try {
				date = sdf.parse(stringDate);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			calendar = Calendar.getInstance();		//默认当前日期
		//    calendar.setTime(date);	//设置为自定义日期
			datePickerArgueTime.setCustomTextFieldValue(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
			
			comboBoxTaskLocation.setSelectedItem("武汉");		
		}	
	}
}
