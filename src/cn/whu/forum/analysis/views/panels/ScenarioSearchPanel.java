package cn.whu.forum.analysis.views.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdatepicker.DatePicker;
import org.jdatepicker.JDatePicker;

import cn.whu.forum.analysis.entities.ScenarioLogic;
import cn.whu.forum.analysis.entities.ScenarioProperty;
import cn.whu.forum.analysis.entities.ScenarioResult;
import cn.whu.forum.analysis.entities.ScenarioTask;
import cn.whu.forum.analysis.services.ScenarioLogicDAO;
import cn.whu.forum.analysis.services.ScenarioPropertyDAO;
import cn.whu.forum.analysis.services.ScenarioResultDAO;
import cn.whu.forum.analysis.services.ScenarioTaskDAO;
import cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface;
import cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface;
import cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface;
import cn.whu.forum.analysis.services.interfaces.ScenarioTaskDAOInterface;

public class ScenarioSearchPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ScenarioTaskDAOInterface scenarioTaskDAO;
	
	private JPanel panelLayInput;
	private JPanel panelLayTableANDButton;
	private JPanel panelLayTable;
	private JPanel panelLayButton;
	private JPanel panelLayView;
	
	private JLabel labelTaskName;
	private JLabel labelTaskLocation;
	private JLabel labelArgueTimeStartDate;
	private JLabel labelArgueTimeEndDate;
	
	private JTextField textfieldTaskName;
	private JTextField textfieldTaskLocation;
	private DatePicker datePickerArgueTimeStartDate;
	private DatePicker datePickerArgueTimeEndDate;
	
	private JButton buttonSearch;
	private JButton buttonDeleteScenarioTask;
	private JButton buttonModifyScenarioTask;
	
	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane scrollpaneTable;
	private int selectedTableRow;		
	
	//表格修改任务内容面板
	private JFrame frameModifyTask;
	private JPanel panelModifyTask;
	private JPanel panelLayTaskNameInModifyTask;
	private JPanel panelLayTaskDescriptionInModifyTask;
	private JPanel panelLayArgueTimeInModifyTask;
	private JPanel panelLayTaskLocationInModifyTask;
	private JPanel panelLayButtonInModifyTask;
	private JLabel labelTaskNameInModifyTask;
	private JLabel labelTaskDescriptionInModifyTask;
	private JLabel labelArgueTimeInModifyTask;
	private JLabel labelTaskLocationInModifyTask;
	private JTextField textfieldfTaskNameInModifyTask;
	private JTextField textfieldTaskDescriptionInModifyTask;
	private DatePicker datePickerArgueTimeInModifyTask;
	private JComboBox<String> comboBoxTaskLocationInModifyTask;
	private JButton buttonConfirmInModifyTask;
	private JButton buttonCancelInModifyTask;
	
	//右键菜单
	private JPopupMenu popupMenu;
	private JMenuItem popmenuItemDeleteSwotTask;
	private JMenuItem popmenuItemModifySwotTask;

	public ScenarioSearchPanel() {
		
		scenarioTaskDAO = new ScenarioTaskDAO();
		
		initializeComponent();
		
		layoutComponent();
		
	}
	
	private void initializeComponent() {
		
		//JPanel设置
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelLayInput = new JPanel();
		panelLayTableANDButton = new JPanel();
		panelLayTable = new JPanel();
		panelLayButton = new JPanel();
		panelLayView = new JPanel();
		panelLayTableANDButton.setMaximumSize(new Dimension(1500, 150));			//【没有更好的办法设置该panel初始高度】
		
		panelLayInput.setLayout(new BoxLayout(panelLayInput, BoxLayout.X_AXIS));
		panelLayTableANDButton.setLayout(new BoxLayout(panelLayTableANDButton, BoxLayout.X_AXIS));
		panelLayTable.setLayout(new BorderLayout());
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.Y_AXIS));
		panelLayView.setLayout(new BorderLayout());
		
		labelTaskName = new JLabel("任务名称");
		labelTaskLocation = new JLabel("研讨地点");
		labelArgueTimeStartDate = new JLabel("开始日期");
		labelArgueTimeEndDate = new JLabel("结束日期");
		
		textfieldTaskName = new JTextField();
		textfieldTaskLocation = new JTextField();
		datePickerArgueTimeStartDate = new JDatePicker();
		datePickerArgueTimeEndDate = new JDatePicker();
		
		textfieldTaskName.setMinimumSize(new Dimension(100,25));
		textfieldTaskName.setMaximumSize(new Dimension(150,30));
		textfieldTaskLocation.setMinimumSize(new Dimension(100,25));
		textfieldTaskLocation.setMaximumSize(new Dimension(150,30));
		((Component) datePickerArgueTimeStartDate).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueTimeStartDate).setMaximumSize(new Dimension(150,30));
		((Component) datePickerArgueTimeEndDate).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueTimeEndDate).setMaximumSize(new Dimension(150,30));
	
		buttonSearch = new JButton("查找");	
		buttonDeleteScenarioTask = new JButton("删除");
		buttonModifyScenarioTask = new JButton("修改");
		buttonSearch.addActionListener(this);
		buttonDeleteScenarioTask.addActionListener(this);
		buttonModifyScenarioTask.addActionListener(this);
		
		table = initializeTable();
		
		scrollpaneTable = new JScrollPane(table);
		scrollpaneTable.setPreferredSize(new Dimension(0, 100));
		
		//右键菜单
		popupMenu = new JPopupMenu(); 
	    popmenuItemDeleteSwotTask = new JMenuItem("删除");  
	    popmenuItemModifySwotTask = new JMenuItem("修改");

		popmenuItemDeleteSwotTask.addActionListener(this);
		popmenuItemModifySwotTask.addActionListener(this);
		
	}
	
	public void layoutComponent() {
		
		panelLayInput.add(labelTaskName);
		panelLayInput.add(Box.createHorizontalStrut(10));
		panelLayInput.add(textfieldTaskName);
		panelLayInput.add(Box.createHorizontalStrut(20));
		panelLayInput.add(labelTaskLocation);
		panelLayInput.add(Box.createHorizontalStrut(10));
		panelLayInput.add(textfieldTaskLocation);
		panelLayInput.add(Box.createHorizontalStrut(20));
		panelLayInput.add(labelArgueTimeStartDate);
		panelLayInput.add(Box.createHorizontalStrut(10));
		panelLayInput.add((Component) datePickerArgueTimeStartDate);
		panelLayInput.add(Box.createHorizontalStrut(20));
		panelLayInput.add(labelArgueTimeEndDate);
		panelLayInput.add(Box.createHorizontalStrut(10));
		panelLayInput.add((Component) datePickerArgueTimeEndDate);
		panelLayInput.add(Box.createHorizontalStrut(20));
		panelLayInput.add(buttonSearch);
		
		panelLayTable.add(scrollpaneTable);
		panelLayButton.add(buttonDeleteScenarioTask);
		panelLayButton.add(buttonModifyScenarioTask);
		panelLayTableANDButton.add(panelLayTable);
		panelLayTableANDButton.add(panelLayButton);
		
        popupMenu.add(popmenuItemDeleteSwotTask);  
        popupMenu.add(popmenuItemModifySwotTask);  
		
		add(panelLayInput);
		add(panelLayTableANDButton);
		add(panelLayView);
		
	}
	
	public JTable initializeTable() {
			
	    dtm = new DefaultTableModel() {
		      /**
			 * 
			 */
	    	private static final long serialVersionUID = 1L;
			public Class<String> getColumnClass(int columnIndex) {
		        return String.class;
		    }
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
	    };
	    dtm.setDataVector(
		    	new Object[][] {
		    		{"", "", "", "", "", -1}
		    	}, 
			    new Object[] {"编号", "任务名称", "任务描述", "研讨日期", "研讨地点", "taskID"}
		);

	    final JTable table = new JTable(dtm);
	    
	    //设置表格列宽
	    TableColumnModel columnModel=table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(0).setMaxWidth(40);
		columnModel.getColumn(1).setPreferredWidth(250);
		columnModel.getColumn(1).setMaxWidth(450);
		columnModel.getColumn(2).setPreferredWidth(700);
		columnModel.getColumn(2).setMaxWidth(800);
		columnModel.getColumn(3).setPreferredWidth(150);
		columnModel.getColumn(3).setMaxWidth(150);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(4).setMaxWidth(100);
		columnModel.removeColumn(columnModel.getColumn(5));		//第五列为隐藏列，存储任务ID
	    table.setDefaultRenderer(String.class, new MultiLineTableCellRender());

	    
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
			
				selectedTableRow = table.rowAtPoint(e.getPoint());		//getSelectedRow()对于右键无效，值为-1
				
				if(e.getButton() == MouseEvent.BUTTON3) {	//监听右键菜单
					
					popupMenu.show(e.getComponent(), e.getX(), e.getY());	 //在鼠标点击位置弹出右键菜单
	                
				} else if(e.getClickCount() == 2) {		//监听双击菜单
					if(table.getValueAt(0, 0) != "") 
						modifyTask();
					else
						JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
				}
		
				if(panelLayView != null)
					panelLayView.removeAll();
				
				if(table.getModel().getValueAt(selectedTableRow, 0) != "") {
					ScenarioTask scenarioTask = scenarioTaskDAO.getScenarioTaskByID((int)(table.getModel().getValueAt(selectedTableRow, 5)));
					panelLayView.add(new ScenarioSearchContentPanel(scenarioTask));
				}
					
				panelLayView.revalidate();
				panelLayView.repaint();
			}
		});

		return table;
		
	}
	
	public void deleteTask() {
		
		ScenarioTask scenarioTask = scenarioTaskDAO.getScenarioTaskByID((int)(table.getModel().getValueAt(selectedTableRow, 5)));
		List<ScenarioProperty> scenarioPropertyList = scenarioTaskDAO.getAllTaskProperties(scenarioTask);
		List<ScenarioLogic> scenarioLogicList = scenarioTaskDAO.getAllTaskLogics(scenarioTask);
		List<ScenarioResult> scenarioResultList = scenarioTaskDAO.getAllTaskResults(scenarioTask);
		for(ScenarioProperty scenarioProperty:scenarioPropertyList) {
			ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
			scenarioPropertyDAO.deletePropertyByID(scenarioProperty.getPropertyID());
		}
		for(ScenarioLogic scenarioLogic:scenarioLogicList) {
			ScenarioLogicDAOInterface scenarioLogicDAO = new ScenarioLogicDAO();
			scenarioLogicDAO.deleteLogicByID(scenarioLogic.getLogicID());
		}
		for(ScenarioResult scenarioResult:scenarioResultList) {
			ScenarioResultDAOInterface scenarioResultDAO = new ScenarioResultDAO();
			scenarioResultDAO.deleteResultByID(scenarioResult.getResultID());
		}
		scenarioTaskDAO.deleteTaskByID((int)(table.getModel().getValueAt(selectedTableRow, 5)));
		
		//表格更新
		dtm = (DefaultTableModel)table.getModel();
		dtm.removeRow(selectedTableRow);
		
		if(table.getRowCount() == 0) {
			dtm.addRow(new Object[]{"", "", "", "", -1});
		}else {
			for(int i = 0; i < dtm.getRowCount(); i++) {
				dtm.setValueAt(i + 1, i, 0);
			}
		}		
		
		if(panelLayView != null)
			panelLayView.removeAll();
		panelLayView.revalidate();
		panelLayView.repaint();
	}
	
	//修改属性内容面板START（双击单元格）
	public void modifyTask() {
		
		/*-------------初始化Start--------------*/
		frameModifyTask = new JFrame("修改任务");
		frameModifyTask.setSize(400, 450);
		frameModifyTask.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameModifyTask.setLocationRelativeTo( null ); 
		
		panelModifyTask = new JPanel();
		panelModifyTask.setLayout(new BoxLayout(panelModifyTask, BoxLayout.Y_AXIS));
		
		panelLayTaskNameInModifyTask = new JPanel();
		panelLayTaskDescriptionInModifyTask = new JPanel();
		panelLayArgueTimeInModifyTask = new JPanel();
		panelLayTaskLocationInModifyTask = new JPanel();
		panelLayButtonInModifyTask = new JPanel();
		
		panelLayTaskNameInModifyTask.setLayout(new BoxLayout(panelLayTaskNameInModifyTask, BoxLayout.X_AXIS));
		panelLayTaskDescriptionInModifyTask.setLayout(new BoxLayout(panelLayTaskDescriptionInModifyTask, BoxLayout.X_AXIS));
		panelLayArgueTimeInModifyTask.setLayout(new BoxLayout(panelLayArgueTimeInModifyTask, BoxLayout.X_AXIS));
		panelLayTaskLocationInModifyTask.setLayout(new BoxLayout(panelLayTaskLocationInModifyTask, BoxLayout.X_AXIS));
		panelLayButtonInModifyTask.setLayout(new BoxLayout(panelLayButtonInModifyTask, BoxLayout.X_AXIS));
		
		labelTaskNameInModifyTask = new JLabel("任务名称");
		labelTaskDescriptionInModifyTask = new JLabel("任务描述");
		labelArgueTimeInModifyTask = new JLabel("研讨日期");
		labelTaskLocationInModifyTask = new JLabel("研讨地点");
		
		textfieldfTaskNameInModifyTask = new JTextField();
		textfieldTaskDescriptionInModifyTask = new JTextField();
		
		datePickerArgueTimeInModifyTask = new JDatePicker();
		
		comboBoxTaskLocationInModifyTask = new JComboBox<String> ();
		comboBoxTaskLocationInModifyTask.setEditable(true);	//可输入。得到输入值的方法： getEditor().getItem(); 
		comboBoxTaskLocationInModifyTask.addItem("武汉");
		comboBoxTaskLocationInModifyTask.addItem("北京");
		comboBoxTaskLocationInModifyTask.addItem("深圳");
		comboBoxTaskLocationInModifyTask.addItem("上海");
		comboBoxTaskLocationInModifyTask.setSelectedItem(null);
		
		textfieldfTaskNameInModifyTask.setMinimumSize(new Dimension(100,25));
		textfieldfTaskNameInModifyTask.setMaximumSize(new Dimension(150,30));
		textfieldTaskDescriptionInModifyTask.setMinimumSize(new Dimension(100,25));
		textfieldTaskDescriptionInModifyTask.setMaximumSize(new Dimension(150,30));
		((Component) datePickerArgueTimeInModifyTask).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueTimeInModifyTask).setMaximumSize(new Dimension(150,30));
		comboBoxTaskLocationInModifyTask.setMinimumSize(new Dimension(100,25));
		comboBoxTaskLocationInModifyTask.setMaximumSize(new Dimension(150,30));
		
		//还原初始值
		textfieldfTaskNameInModifyTask.setText(table.getModel().getValueAt(selectedTableRow, 1).toString());
		textfieldTaskDescriptionInModifyTask.setText(table.getModel().getValueAt(selectedTableRow, 2).toString());
		
		if(table.getModel().getValueAt(selectedTableRow, 3) != null)  {	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");	
			String stringDate = table.getModel().getValueAt(selectedTableRow, 3).toString();
			Date date = new Date();
			try {
				date = sdf.parse(stringDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
			datePickerArgueTimeInModifyTask.setCustomTextFieldValue(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		}
		
		comboBoxTaskLocationInModifyTask.setSelectedItem(table.getModel().getValueAt(selectedTableRow, 4));
		
		buttonConfirmInModifyTask = new JButton("确定");
		buttonCancelInModifyTask = new JButton("取消");
		
		buttonConfirmInModifyTask.addActionListener(this);
		buttonCancelInModifyTask.addActionListener(this);
		/*-------------初始化End--------------*/
		
		/*-------------布局Start--------------*/
		panelLayTaskNameInModifyTask.add(labelTaskNameInModifyTask);		
		panelLayTaskNameInModifyTask.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayTaskNameInModifyTask.add(textfieldfTaskNameInModifyTask);
		
		panelLayTaskDescriptionInModifyTask.add(labelTaskDescriptionInModifyTask);		
		panelLayTaskDescriptionInModifyTask.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayTaskDescriptionInModifyTask.add(textfieldTaskDescriptionInModifyTask);
		
		panelLayArgueTimeInModifyTask.add(labelArgueTimeInModifyTask);
		panelLayArgueTimeInModifyTask.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayArgueTimeInModifyTask.add((Component) datePickerArgueTimeInModifyTask);
		
		panelLayTaskLocationInModifyTask.add(labelTaskLocationInModifyTask);
		panelLayTaskLocationInModifyTask.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayTaskLocationInModifyTask.add(comboBoxTaskLocationInModifyTask);
		
		panelLayButtonInModifyTask.add(buttonConfirmInModifyTask);
		panelLayButtonInModifyTask.add(Box.createRigidArea(new Dimension(20, 60)));    
		panelLayButtonInModifyTask.add(buttonCancelInModifyTask);						

		panelModifyTask.add( Box.createVerticalGlue() );
		panelModifyTask.add(panelLayTaskNameInModifyTask);
		panelModifyTask.add(Box.createVerticalStrut(10));  		
		panelModifyTask.add(panelLayTaskDescriptionInModifyTask);
		panelModifyTask.add(Box.createVerticalStrut(10));  		
		panelModifyTask.add(panelLayArgueTimeInModifyTask);
		panelModifyTask.add(Box.createVerticalStrut(10));  		
		panelModifyTask.add(panelLayTaskLocationInModifyTask);
		panelModifyTask.add(Box.createVerticalStrut(10));  		
		panelModifyTask.add(panelLayButtonInModifyTask);
		panelModifyTask.add( Box.createVerticalGlue() );
		
		frameModifyTask.setContentPane(panelModifyTask);			
		frameModifyTask.setVisible(true);
		/*-------------布局End--------------*/
		
	}
	//修改属性内容面板END

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == buttonSearch) {

			if(panelLayView != null)
				panelLayView.removeAll();
			panelLayView.revalidate();
			panelLayView.repaint();
			
			Date dateStartDate = new Date();		//默认当下时间
			Calendar dateStartCalendar = Calendar.getInstance();		
			if(!datePickerArgueTimeStartDate.getModel().isSelected())		//如若用户未填写初值，则设置为空
				dateStartDate = null;
			else {
				dateStartCalendar.set(datePickerArgueTimeStartDate.getModel().getYear(), datePickerArgueTimeStartDate.getModel().getMonth(), 
					datePickerArgueTimeStartDate.getModel().getDay(), 0, 0, 0);
				dateStartCalendar.set(Calendar.MILLISECOND, 0);
				dateStartDate = dateStartCalendar.getTime();
			}
			
			Date dateEndDate = new Date();		//默认当下时间
			Calendar dateEndCalendar = Calendar.getInstance();		
			if(!datePickerArgueTimeEndDate.getModel().isSelected())		//如若用户未填写初值，则设置为空
				dateEndDate = null;
			else {
				dateEndCalendar.set(datePickerArgueTimeEndDate.getModel().getYear(), datePickerArgueTimeEndDate.getModel().getMonth(), 
						datePickerArgueTimeEndDate.getModel().getDay(), 0, 0, 0);
				dateEndCalendar.set(Calendar.MILLISECOND, 0);
				dateEndDate = dateEndCalendar.getTime();
			}
			
			//得到查询结果
			List<ScenarioTask> scenarioTaskListByLocation = scenarioTaskDAO.getScenarioTaskByLocation(textfieldTaskLocation.getText());
			List<ScenarioTask> scenarioTaskListByDate = scenarioTaskDAO.getScenarioTaskByDate(dateStartDate, dateEndDate);
			List<ScenarioTask> scenarioTaskListByName = scenarioTaskDAO.getScenarioTaskByName(textfieldTaskName.getText());
			
			//【重要】两个复杂类型list直接retainAll方法结果是空，在此猜测是List类型（复杂）所致，故只提取出taskID进行比较得出公共元素
			List<Integer> taskIDListByLocation = new ArrayList<Integer>();	
			for(ScenarioTask list:scenarioTaskListByLocation) {		//【非常重要】在有外键关联时若设置成lazy属性，表明只有遍历用到外键时才查询其数据（为了节省开销）。此处若直接查询得SwotTask列表则不会有数据，必须打开SwotTask查询Task表得到数据才有数据
				taskIDListByLocation.add(list.getTaskID());
			}
			List<Integer> taskIDListByDate = new ArrayList<Integer>();
			for(ScenarioTask scenarioTask:scenarioTaskListByDate) {
				taskIDListByDate.add(scenarioTask.getTaskID());
			}			
			List<Integer> taskIDListByName = new ArrayList<Integer>();
			for(ScenarioTask scenarioTask:scenarioTaskListByName) {
				taskIDListByName.add(scenarioTask.getTaskID());
			}			
			List<Integer> taskIDCommonList = new ArrayList<Integer>(taskIDListByLocation);
			taskIDCommonList.retainAll(taskIDListByDate);
			taskIDCommonList.retainAll(taskIDListByName);
		
			//不能边遍历list边删除，固先新建一个临时删除列表
			List<ScenarioTask> deleteTaskList = new ArrayList<ScenarioTask>();		
			for(ScenarioTask scenarioTask:scenarioTaskListByDate) {		//此处scenarioTaskListByName也可以为scenarioTaskListByDate
				int flag = 0;
				for(Integer taskID:taskIDCommonList) {
					if(taskID.intValue() == scenarioTask.getTaskID())
						flag = 1;
				}			
				if(flag == 0)
					deleteTaskList.add(scenarioTask);
			}
			scenarioTaskListByDate.removeAll(deleteTaskList);
			
			//表格显示数据
			if(scenarioTaskListByDate.size() == 0)
				JOptionPane.showMessageDialog(null, "未查找到符合数据", "",JOptionPane.INFORMATION_MESSAGE);
			dtm = (DefaultTableModel)table.getModel(); 						
			dtm.setRowCount(0);			//清空原有数据
			dtm.addRow(new Object[]{"", "", "", "", "", -1});
			for(ScenarioTask scenarioTask:scenarioTaskListByName) {		
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(scenarioTask.getTaskName(), 0, 1);
					dtm.setValueAt(scenarioTask.getTaskDescription(), 0, 2);
					
					if(scenarioTask.getArgueTime() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String stringDate = sdf.format(scenarioTask.getArgueTime());
						dtm.setValueAt(stringDate, 0, 3);
					} else
						dtm.setValueAt("", 0, 3);
						
					dtm.setValueAt(scenarioTask.getTaskLocation(), 0, 4);
					dtm.setValueAt(scenarioTask.getTaskID(), 0, 5);
				} else{	
					if(scenarioTask.getArgueTime() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String stringDate = sdf.format(scenarioTask.getArgueTime());
						dtm.addRow(new Object[]{dtm.getRowCount()+1, scenarioTask.getTaskName(), scenarioTask.getTaskDescription(), 
								stringDate, scenarioTask.getTaskLocation(), scenarioTask.getTaskID()});		
					} else
						dtm.addRow(new Object[]{dtm.getRowCount()+1, scenarioTask.getTaskName(), scenarioTask.getTaskDescription(), 
								"", scenarioTask.getTaskLocation(), scenarioTask.getTaskID()});				
				}
			}
		}
		
		else if(e.getSource() == buttonDeleteScenarioTask) {
			if(table.getValueAt(0, 0) != "") 
				deleteTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除操作", "",JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if(e.getSource() == buttonModifyScenarioTask) {
			if(table.getValueAt(0, 0) != "") 
				modifyTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if(e.getSource() == buttonConfirmInModifyTask) {
			
			Calendar calendar = Calendar.getInstance();
            calendar.set(datePickerArgueTimeInModifyTask.getModel().getYear(), 		//年份自动多余，不知缘由
            		datePickerArgueTimeInModifyTask.getModel().getMonth(), datePickerArgueTimeInModifyTask.getModel().getDay(), 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
			
			ScenarioTask scenarioTask = new ScenarioTask();
			scenarioTask.setTaskID((int)(table.getModel().getValueAt(selectedTableRow, 5)));		//获得remove掉的列的data必须使用TableModel
			scenarioTask.setTaskName(textfieldfTaskNameInModifyTask.getText());
			scenarioTask.setTaskDescription(textfieldTaskDescriptionInModifyTask.getText());
			scenarioTask.setArgueTime(calendar.getTime());
			scenarioTask.setTaskLocation(comboBoxTaskLocationInModifyTask.getSelectedItem().toString());

			scenarioTaskDAO.updateTask(scenarioTask);

			dtm.setValueAt(scenarioTask.getTaskName(), 0, 1);
			dtm.setValueAt(scenarioTask.getTaskDescription(), 0, 2);
			
			if(scenarioTask.getArgueTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String stringDate = sdf.format(scenarioTask.getArgueTime());
				dtm.setValueAt(stringDate, 0, 3);
			} else
				dtm.setValueAt("", 0, 3);
				
			dtm.setValueAt(scenarioTask.getTaskLocation(), 0, 4);
			dtm.setValueAt(scenarioTask.getTaskID(), 0, 5);
		}
		
		else if(e.getSource() == buttonCancelInModifyTask) {
			
			frameModifyTask.dispose();
			
		}
		
		else if(e.getSource() == popmenuItemDeleteSwotTask) {
			if(table.getValueAt(0, 0) != "") 
				deleteTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除操作", "",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		else if(e.getSource() == popmenuItemModifySwotTask) {
			if(table.getValueAt(0, 0) != "") 
				modifyTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
}
 