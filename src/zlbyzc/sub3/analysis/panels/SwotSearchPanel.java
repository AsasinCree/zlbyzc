package zlbyzc.sub3.analysis.panels;

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
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jdatepicker.DatePicker;
import org.jdatepicker.JDatePicker;

import zlbyzc.sub3.analysis.entities.ScenarioTask;
import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;
import zlbyzc.sub3.analysis.entities.SwotTask;
import zlbyzc.sub3.analysis.services.SwotActorDAO;
import zlbyzc.sub3.analysis.services.SwotPropertyDAO;
import zlbyzc.sub3.analysis.services.SwotTaskDAO;
import zlbyzc.sub3.analysis.services.interfaces.SwotActorDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotPropertyDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotTaskDAOInterface;

public class SwotSearchPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SwotTaskDAOInterface swotTaskDAO;
	
	private JSplitPane splitpane;
	private JPanel panelLeft;
	private JPanel panelRight;
	private JPanel panelLayInput;
	private JPanel panelLayInputTaskName;
	private JPanel panelLayInputTaskLocation;
	private JPanel panelLayInputArgueTimeStartDate;
	private JPanel panelLayInputArgueTimeEndDate;
	private JPanel panelLayInputButton;
	private JPanel panelLayTableANDPageButton;
	private JPanel panelLayTable;
	private JPanel panelLayPageButton;
	private JPanel panelLayEditButton;
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
	private JButton buttonPreviousPage;
	private JButton buttonNextPage;
	private JButton buttonDeleteSwotTask;
	private JButton buttonModifySwotTask;
	
	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane scrollpaneTable;
	private List<SwotTask> commonList;
	private int selectedTableRow;		
	private int pageCurrent;		//当前页码
	private int pageSum;	//总页数
	private int pageDivider;		//每页显示条目数
	
	//表格修改任务内容面板
	private JFrame frameModifyTask;
	private JPanel panelModifyTask;
	private JPanel panelLayTaskNameInModifyTask;
	private JPanel panelLayTaskDescriptionInModifyTask;
	private JPanel panelLayArgueTimeInModifyTask;
	private JPanel panelLayTaskLocationInModifyTask;
	private JPanel panelLayPageButtonInModifyTask;
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

	public SwotSearchPanel() {
		
		swotTaskDAO = new SwotTaskDAO();
		
		initializeComponent();
		
		layoutComponent();
		
	}
	
	private void initializeComponent() {
		
		//JPanel设置
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		splitpane = new JSplitPane();
		splitpane.setOneTouchExpandable(true);  		//让分隔线显示出箭头
		splitpane.setContinuousLayout(true); 		//当用户操作分隔线箭头时，系统重绘图形
		splitpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); 		//设置方向或者分隔窗格的方式（HORIZONTAL_SPLIT表示左右分隔，VERTICAL_SPLIT）
		splitpane.setDividerSize(3); 		//设置分隔条的大小
		splitpane.setDividerLocation(400); 	//设置分隔条的位置【】
		
		panelLeft = new JPanel();
		panelRight = new JPanel();
		panelLayInput = new JPanel();
		panelLayInputTaskName = new JPanel();
		panelLayInputTaskLocation = new JPanel();
		panelLayInputArgueTimeStartDate = new JPanel();
		panelLayInputArgueTimeEndDate = new JPanel();
		panelLayInputButton = new JPanel();
		panelLayTableANDPageButton = new JPanel();
		panelLayTable = new JPanel();
		panelLayPageButton = new JPanel();
		panelLayEditButton = new JPanel();
		panelLayView = new JPanel();
		panelLayTableANDPageButton.setPreferredSize(new Dimension(0, 550));			//【没有更好的办法设置该panel初始高度】
		
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		panelRight.setLayout(new BorderLayout());
		panelLayInput.setLayout(new BoxLayout(panelLayInput, BoxLayout.Y_AXIS));
		panelLayInputTaskName.setLayout(new BoxLayout(panelLayInputTaskName, BoxLayout.X_AXIS));
		panelLayInputTaskLocation.setLayout(new BoxLayout(panelLayInputTaskLocation, BoxLayout.X_AXIS));
		panelLayInputArgueTimeStartDate.setLayout(new BoxLayout(panelLayInputArgueTimeStartDate, BoxLayout.X_AXIS));
		panelLayInputArgueTimeEndDate.setLayout(new BoxLayout(panelLayInputArgueTimeEndDate, BoxLayout.X_AXIS));
		panelLayInputButton.setLayout(new BoxLayout(panelLayInputButton, BoxLayout.X_AXIS));
		panelLayTableANDPageButton.setLayout(new BoxLayout(panelLayTableANDPageButton, BoxLayout.Y_AXIS));
		panelLayTable.setLayout(new BorderLayout());
		panelLayPageButton.setLayout(new BoxLayout(panelLayPageButton, BoxLayout.X_AXIS));
		panelLayEditButton.setLayout(new BoxLayout(panelLayEditButton, BoxLayout.X_AXIS));
		panelLayView.setLayout(new BorderLayout());
		
		labelTaskName = new JLabel("任务名称");
		labelTaskLocation = new JLabel("研讨地点");
		labelArgueTimeStartDate = new JLabel("开始日期");
		labelArgueTimeEndDate = new JLabel("结束日期");
		
		textfieldTaskName = new JTextField();
		textfieldTaskLocation = new JTextField();
		datePickerArgueTimeStartDate = new JDatePicker();
		datePickerArgueTimeEndDate = new JDatePicker();
		
		//不设置大小会自动拉伸
		textfieldTaskName.setMinimumSize(new Dimension(100,25));
		textfieldTaskName.setMaximumSize(new Dimension(150,30));
		textfieldTaskLocation.setMinimumSize(new Dimension(100,25));
		textfieldTaskLocation.setMaximumSize(new Dimension(150,30));
		((Component) datePickerArgueTimeStartDate).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueTimeStartDate).setMaximumSize(new Dimension(150,30));
		((Component) datePickerArgueTimeEndDate).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueTimeEndDate).setMaximumSize(new Dimension(150,30));
	
		buttonSearch = new JButton("查找");	
		buttonPreviousPage = new JButton("上一页");
		buttonNextPage = new JButton("下一页");
		buttonDeleteSwotTask = new JButton("删除");
		buttonModifySwotTask = new JButton("修改");
		buttonPreviousPage.setEnabled(false);
		buttonNextPage.setEnabled(false);
		buttonSearch.addActionListener(this);
		buttonPreviousPage.addActionListener(this);
		buttonNextPage.addActionListener(this);
		buttonDeleteSwotTask.addActionListener(this);
		buttonModifySwotTask.addActionListener(this);
		
		table = initializeTable();
		
		scrollpaneTable = new JScrollPane(table);
		scrollpaneTable.setPreferredSize(new Dimension(500, 550));
		
		//右键菜单
		popupMenu = new JPopupMenu(); 
	    popmenuItemDeleteSwotTask = new JMenuItem("删除");  
	    popmenuItemModifySwotTask = new JMenuItem("修改");

		popmenuItemDeleteSwotTask.addActionListener(this);
		popmenuItemModifySwotTask.addActionListener(this);
		
	}
	
	public void layoutComponent() {
		
		panelLayInputTaskName.add(labelTaskName);
		panelLayInputTaskName.add(Box.createRigidArea(new Dimension(20, 30)));
		panelLayInputTaskName.add(textfieldTaskName);
		panelLayInputTaskLocation.add(labelTaskLocation);
		panelLayInputTaskLocation.add(Box.createRigidArea(new Dimension(20, 30)));
		panelLayInputTaskLocation.add(textfieldTaskLocation);
		panelLayInputArgueTimeStartDate.add(labelArgueTimeStartDate);
		panelLayInputArgueTimeStartDate.add(Box.createRigidArea(new Dimension(20, 30)));
		panelLayInputArgueTimeStartDate.add((Component) datePickerArgueTimeStartDate);
		panelLayInputArgueTimeEndDate.add(labelArgueTimeEndDate);
		panelLayInputArgueTimeEndDate.add(Box.createRigidArea(new Dimension(20, 30)));
		panelLayInputArgueTimeEndDate.add((Component) datePickerArgueTimeEndDate);
		panelLayInputButton.add(buttonSearch);
		
		panelLayInput.add(Box.createVerticalStrut(5));
		panelLayInput.add(panelLayInputTaskName);
		panelLayInput.add(Box.createVerticalStrut(5));
		panelLayInput.add(panelLayInputTaskLocation);
		panelLayInput.add(Box.createVerticalStrut(5));
		panelLayInput.add(panelLayInputArgueTimeStartDate);
		panelLayInput.add(Box.createVerticalStrut(5));
		panelLayInput.add(panelLayInputArgueTimeEndDate);
		panelLayInput.add(Box.createVerticalStrut(2));
		panelLayInput.add(panelLayInputButton);
		panelLayInput.add(Box.createVerticalStrut(5));
		
		panelLayTable.add(scrollpaneTable);
		panelLayPageButton.add(buttonPreviousPage);
		panelLayPageButton.add(Box.createHorizontalStrut(10));
		panelLayPageButton.add(buttonNextPage);
		panelLayTableANDPageButton.add(panelLayTable);
		panelLayTableANDPageButton.add(panelLayPageButton);
		
		panelLayEditButton.add(buttonDeleteSwotTask);
		panelLayEditButton.add(Box.createHorizontalStrut(10));
		panelLayEditButton.add(buttonModifySwotTask);
		
		panelLeft.add(panelLayInput);
		panelLeft.add(panelLayTableANDPageButton);
		panelLeft.add(panelLayEditButton);
		
		panelRight.add(panelLayView);
		
        popupMenu.add(popmenuItemDeleteSwotTask);  
        popupMenu.add(popmenuItemModifySwotTask);  
		
		splitpane.setLeftComponent(panelLeft);		
		splitpane.setRightComponent(panelRight);
		add(splitpane);
	}
	
	public JTable initializeTable() {
		
		//将数据载入表格中
		
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
	    TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.getColumn(0).setMaxWidth(40);
		columnModel.getColumn(1).setPreferredWidth(250);
		columnModel.getColumn(1).setMaxWidth(450);
		columnModel.getColumn(2).setPreferredWidth(250);
		columnModel.getColumn(2).setMaxWidth(450);
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
					SwotTask swotTask = swotTaskDAO.getSwotTaskByID((int)(table.getModel().getValueAt(selectedTableRow, 5)));		
					panelLayView.add(new SwotSearchContentPanel(swotTask));
				}
				
				panelLayView.revalidate();
				panelLayView.repaint();
			}
		});

		return table;
		
	}

	public void deleteTask() {
		
		SwotTask swotTask = swotTaskDAO.getSwotTaskByID((int)(table.getModel().getValueAt(selectedTableRow, 5)));
		List<SwotActor> swotActorList = swotTaskDAO.getAllTaskActors(swotTask);	
		for(SwotActor swotActor:swotActorList) {
			SwotActorDAOInterface swotActorDAO = new SwotActorDAO();
			List<SwotActorProperty> actorPropertyList = swotActorDAO.getAllActorPropertys(swotActor);	
			for(SwotActorProperty actorProperty:actorPropertyList) {		//删除属性
				SwotPropertyDAOInterface swotPropertyDAO = new SwotPropertyDAO();
				swotPropertyDAO.deletePropertyByID(actorProperty.getPropertyID());
			}	
			swotActorDAO.deleteActorByID(swotActor.getActorID());		//删除参与者
		}
		swotTaskDAO.deleteTaskByID((int)(table.getModel().getValueAt(selectedTableRow, 5)));	//删除任务
	
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
		panelLayPageButtonInModifyTask = new JPanel();
		
		panelLayTaskNameInModifyTask.setLayout(new BoxLayout(panelLayTaskNameInModifyTask, BoxLayout.X_AXIS));
		panelLayTaskDescriptionInModifyTask.setLayout(new BoxLayout(panelLayTaskDescriptionInModifyTask, BoxLayout.X_AXIS));
		panelLayArgueTimeInModifyTask.setLayout(new BoxLayout(panelLayArgueTimeInModifyTask, BoxLayout.X_AXIS));
		panelLayTaskLocationInModifyTask.setLayout(new BoxLayout(panelLayTaskLocationInModifyTask, BoxLayout.X_AXIS));
		panelLayPageButtonInModifyTask.setLayout(new BoxLayout(panelLayPageButtonInModifyTask, BoxLayout.X_AXIS));
		
		labelTaskNameInModifyTask = new JLabel("任务名称");
		labelTaskDescriptionInModifyTask = new JLabel("任务描述");
		labelArgueTimeInModifyTask = new JLabel("研讨日期");
		labelTaskLocationInModifyTask = new JLabel("研讨地点");
		
		textfieldfTaskNameInModifyTask = new JTextField();
		textfieldTaskDescriptionInModifyTask = new JTextField();
		
		datePickerArgueTimeInModifyTask = new JDatePicker();
		
		comboBoxTaskLocationInModifyTask = new JComboBox<String> ();
		comboBoxTaskLocationInModifyTask.setEditable(true);	//可输入。
		List<String> swotTaskLocationList = swotTaskDAO.getAllSwotTasksLocation();
		for(String location:swotTaskLocationList)
			comboBoxTaskLocationInModifyTask.addItem(location);
		
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
			datePickerArgueTimeInModifyTask.setCustomTextFieldValue(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
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
		
		panelLayPageButtonInModifyTask.add(buttonConfirmInModifyTask);
		panelLayPageButtonInModifyTask.add(Box.createRigidArea(new Dimension(20, 60)));    
		panelLayPageButtonInModifyTask.add(buttonCancelInModifyTask);						

		panelModifyTask.add( Box.createVerticalGlue() );
		panelModifyTask.add(panelLayTaskNameInModifyTask);
		panelModifyTask.add(Box.createVerticalStrut(10));  		
		panelModifyTask.add(panelLayTaskDescriptionInModifyTask);
		panelModifyTask.add(Box.createVerticalStrut(10));  		
		panelModifyTask.add(panelLayArgueTimeInModifyTask);
		panelModifyTask.add(Box.createVerticalStrut(10));  		
		panelModifyTask.add(panelLayTaskLocationInModifyTask);
		panelModifyTask.add(Box.createVerticalStrut(10));  		
		panelModifyTask.add(panelLayPageButtonInModifyTask);
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
			List<SwotTask> swotTaskListByLocation = swotTaskDAO.getSwotTaskByLocation(textfieldTaskLocation.getText());
			List<SwotTask> swotTaskListByDate = swotTaskDAO.getSwotTaskByDate(dateStartDate, dateEndDate);
			List<SwotTask> swotTaskListByName = swotTaskDAO.getSwotTaskByName(textfieldTaskName.getText());
			
			//【重要】两个复杂类型list直接retainAll方法结果是空，在此猜测是List类型（复杂）所致，故只提取出taskID进行比较得出公共元素
			List<Integer> taskIDListByLocation = new ArrayList<Integer>();	
			for(SwotTask list:swotTaskListByLocation) {		//【非常重要】在有外键关联时若设置成lazy属性，表明只有遍历用到外键时才查询其数据（为了节省开销）。此处若直接查询得SwotTask列表则不会有数据，必须打开SwotTask查询Task表得到数据才有数据
				taskIDListByLocation.add(list.getTaskID());
			}
			List<Integer> taskIDListByDate = new ArrayList<Integer>();
			for(SwotTask swotTask:swotTaskListByDate) {
				taskIDListByDate.add(swotTask.getTaskID());
			}			
			List<Integer> taskIDListByName = new ArrayList<Integer>();
			for(SwotTask swotTask:swotTaskListByName) {
				taskIDListByName.add(swotTask.getTaskID());
			}			
			List<Integer> taskIDCommonList = new ArrayList<Integer>(taskIDListByLocation);
			taskIDCommonList.retainAll(taskIDListByDate);
			taskIDCommonList.retainAll(taskIDListByName);
		
			//不能边遍历list边删除，固先新建一个临时删除列表
			List<SwotTask> deleteTaskList = new ArrayList<SwotTask>();		
			for(SwotTask swotTask:swotTaskListByDate) {		//此处swotTaskListByName也可以为swotTaskListByDate
				int flag = 0;
				for(Integer taskID:taskIDCommonList) {
					if(taskID.intValue() == swotTask.getTaskID())
						flag = 1;
				}			
				if(flag == 0)
					deleteTaskList.add(swotTask);
			}
			swotTaskListByDate.removeAll(deleteTaskList);
			
			commonList = new ArrayList<SwotTask>();
			commonList.addAll(swotTaskListByDate);
			
			//表格显示数据
			if(commonList.size() == 0)
				JOptionPane.showMessageDialog(null, "未查找到符合数据", "",JOptionPane.INFORMATION_MESSAGE);
			
			pageDivider = panelLayTableANDPageButton.getHeight() / 22; 
			if(commonList.size() % pageDivider == 0)	//查询结果数量是每页数量的整数倍
				pageSum = commonList.size() / pageDivider;
			else
				pageSum = commonList.size() / pageDivider + 1;
			pageCurrent = 0;
			
			if(commonList.size() > pageDivider)
				buttonNextPage.setEnabled(true);
			
			dtm = (DefaultTableModel)table.getModel(); 
			dtm.setRowCount(0);			//清空原有数据
			dtm.addRow(new Object[]{"", "", "", "", "", -1});
			for(int i = pageCurrent * pageDivider; i < pageCurrent * pageDivider + pageDivider; i++) {
				if(i < commonList.size()){
					if(dtm.getValueAt(0, 0) == "") {	//第一行
						dtm.setValueAt(1, 0, 0);
						dtm.setValueAt(commonList.get(i).getTaskName(), 0, 1);
						dtm.setValueAt(commonList.get(i).getTaskDescription(), 0, 2);
	
						if(commonList.get(i).getArgueTime() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String stringDate = sdf.format(commonList.get(i).getArgueTime());
							dtm.setValueAt(stringDate, 0, 3);
						} else
							dtm.setValueAt("", 0, 3);
							
						dtm.setValueAt(commonList.get(i).getTaskLocation(), 0, 4);
						dtm.setValueAt(commonList.get(i).getTaskID(), 0, 5);
					} else {		//不是第一行
						if(commonList.get(i).getArgueTime() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String stringDate = sdf.format(commonList.get(i).getArgueTime());
							dtm.addRow(new Object[]{dtm.getRowCount()+1, commonList.get(i).getTaskName(), commonList.get(i).getTaskDescription(), 
									stringDate, commonList.get(i).getTaskLocation(), commonList.get(i).getTaskID()});		
						} else
							dtm.addRow(new Object[]{dtm.getRowCount()+1, commonList.get(i).getTaskName(), commonList.get(i).getTaskDescription(), 
									"", commonList.get(i).getTaskLocation(), commonList.get(i).getTaskID()});		
					}
				}
			}
		}	
		else if(e.getSource() == buttonPreviousPage) {
			pageCurrent--;
			dtm.setRowCount(0);			//清空原有数据
			dtm.addRow(new Object[]{"", "", "", "", "", -1});
			for(int i = pageCurrent * pageDivider; i < pageCurrent * pageDivider + pageDivider; i++) {		
				if(i < commonList.size()){
					if(dtm.getValueAt(0, 0) == "") {		//第一行
						dtm.setValueAt(1, 0, 0);
						dtm.setValueAt(commonList.get(i).getTaskName(), 0, 1);
						dtm.setValueAt(commonList.get(i).getTaskDescription(), 0, 2);
						
						if(commonList.get(i).getArgueTime() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String stringDate = sdf.format(commonList.get(i).getArgueTime());
							dtm.setValueAt(stringDate, 0, 3);
						} else
							dtm.setValueAt("", 0, 3);
							
						dtm.setValueAt(commonList.get(i).getTaskLocation(), 0, 4);
						dtm.setValueAt(commonList.get(i).getTaskID(), 0, 5);
					} else{	     //不是第一行
						if(commonList.get(i).getArgueTime() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String stringDate = sdf.format(commonList.get(i).getArgueTime());
							dtm.addRow(new Object[]{dtm.getRowCount()+1, commonList.get(i).getTaskName(), commonList.get(i).getTaskDescription(), 
									stringDate, commonList.get(i).getTaskLocation(), commonList.get(i).getTaskID()});		
						} else
							dtm.addRow(new Object[]{dtm.getRowCount()+1, commonList.get(i).getTaskName(), commonList.get(i).getTaskDescription(), 
									"", commonList.get(i).getTaskLocation(), commonList.get(i).getTaskID()});				
					}
				}
			}
			buttonNextPage.setEnabled(true);
			if(pageCurrent == 0)
				buttonPreviousPage.setEnabled(false);
		}
		else if(e.getSource() == buttonNextPage) {
			pageCurrent++;
			dtm.setRowCount(0);			//清空原有数据
			dtm.addRow(new Object[]{"", "", "", "", "", -1});
			for(int i = pageCurrent * pageDivider; i < pageCurrent * pageDivider + pageDivider; i++) {		
				if(i < commonList.size()){
					if(dtm.getValueAt(0, 0) == "") {		//第一行
						dtm.setValueAt(1, 0, 0);
						dtm.setValueAt(commonList.get(i).getTaskName(), 0, 1);
						dtm.setValueAt(commonList.get(i).getTaskDescription(), 0, 2);
						
						if(commonList.get(i).getArgueTime() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String stringDate = sdf.format(commonList.get(i).getArgueTime());
							dtm.setValueAt(stringDate, 0, 3);
						} else
							dtm.setValueAt("", 0, 3);
							
						dtm.setValueAt(commonList.get(i).getTaskLocation(), 0, 4);
						dtm.setValueAt(commonList.get(i).getTaskID(), 0, 5);
					} else{	     //不是第一行
						if(commonList.get(i).getArgueTime() != null) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String stringDate = sdf.format(commonList.get(i).getArgueTime());
							dtm.addRow(new Object[]{dtm.getRowCount()+1, commonList.get(i).getTaskName(), commonList.get(i).getTaskDescription(), 
									stringDate, commonList.get(i).getTaskLocation(), commonList.get(i).getTaskID()});		
						} else
							dtm.addRow(new Object[]{dtm.getRowCount()+1, commonList.get(i).getTaskName(), commonList.get(i).getTaskDescription(), 
									"", commonList.get(i).getTaskLocation(), commonList.get(i).getTaskID()});				
					}
				}
			}
			buttonPreviousPage.setEnabled(true);
			System.out.println(pageSum+"  current"+pageCurrent+" divider"+pageDivider);
			if(pageSum <= pageCurrent + 1)
				buttonNextPage.setEnabled(false);
		}
		else if(e.getSource() == buttonDeleteSwotTask) {
			if(table.getValueAt(0, 0) != "") 
				deleteTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除操作", "",JOptionPane.INFORMATION_MESSAGE);
		}	
		else if(e.getSource() == buttonModifySwotTask) {
			if(table.getValueAt(0, 0) != "") 
				modifyTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
		}	
		else if(e.getSource() == buttonConfirmInModifyTask) {
			
			Calendar calendar = Calendar.getInstance();
            calendar.set(datePickerArgueTimeInModifyTask.getModel().getYear(), 		
            		datePickerArgueTimeInModifyTask.getModel().getMonth(), datePickerArgueTimeInModifyTask.getModel().getDay(), 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
			
			SwotTask swotTask = new SwotTask();
			swotTask.setTaskID((int)(table.getModel().getValueAt(selectedTableRow, 5)));		//获得remove掉的列的data必须使用TableModel
			swotTask.setTaskName(textfieldfTaskNameInModifyTask.getText());
			swotTask.setTaskDescription(textfieldTaskDescriptionInModifyTask.getText());
			swotTask.setArgueTime(calendar.getTime());
			swotTask.setTaskLocation(comboBoxTaskLocationInModifyTask.getSelectedItem().toString());

			swotTaskDAO.updateTask(swotTask);
			
			dtm.setValueAt(swotTask.getTaskName(), selectedTableRow, 1);
			dtm.setValueAt(swotTask.getTaskDescription(), selectedTableRow, 2);

			if(swotTask.getArgueTime() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String stringDate = sdf.format(swotTask.getArgueTime());
				dtm.setValueAt(stringDate, selectedTableRow, 3);
			} else
				dtm.setValueAt("", selectedTableRow, 3);
				
			dtm.setValueAt(swotTask.getTaskLocation(), selectedTableRow, 4);
			dtm.setValueAt(swotTask.getTaskID(), selectedTableRow, 5);			
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
 