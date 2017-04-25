package cn.whu.forum.analysis.panels;

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

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.entities.SwotTask;
import cn.whu.forum.analysis.services.SwotActorDAO;
import cn.whu.forum.analysis.services.SwotPropertyDAO;
import cn.whu.forum.analysis.services.SwotTaskDAO;
import cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface;
import cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface;
import cn.whu.forum.analysis.services.interfaces.SwotTaskDAOInterface;

/**
 *  SWOT法查询系统面板
 * @author asasi
 *
 */
public class SwotSearchPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;

	// SWOT法的属性数据操作接口
	private SwotTaskDAOInterface swotTaskDAO;

	// 分隔面板
	private JSplitPane splitpane;
	// 左边面板
	private JPanel panelLeft;
	// 右边面板
	private JPanel panelRight;
	// 输入关键字面板
	private JPanel panelLayInput;
	// 案例名称关键字面板
	private JPanel panelLayInputTaskName;
	// 案例地点关键字面板
	private JPanel panelLayInputTaskLocation;
	// 案例研讨开始时间关键字面板
	private JPanel panelLayInputArgueTimeStartDate;
	// 案例研讨结束时间关键字面板
	private JPanel panelLayInputArgueTimeEndDate;
	// 搜索面板
	private JPanel panelLayInputButton;
	// 装载查询结果表格及翻页按钮的面板
	private JPanel panelLayTableANDPageButton;
	// 查询表格的面板
	private JPanel panelLayTable;
	// 翻页按钮的面板
	private JPanel panelLayPageButton;
	// 编辑按钮的面板
	private JPanel panelLayEditButton;
	// 装载查询结果中某案例内容的面板
	private JPanel panelLayView;

	// 案例名称标签
	private JLabel labelTaskName;
	// 案例地点标签
	private JLabel labelTaskLocation;
	// 案例研讨开始时间标签
	private JLabel labelArgueTimeStartDate;
	// 案例研讨结束时间标签
	private JLabel labelArgueTimeEndDate;

	// 案例名称文本域
	private JTextField textfieldTaskName;
	// 案例地点文本域
	private JTextField textfieldTaskLocation;
	// 案例研讨开始时间文本域
	private JDatePicker datePickerArgueTimeStartDate;
	// 案例研讨结束时间文本域
	private JDatePicker datePickerArgueTimeEndDate;

	// 搜索按钮
	private JButton buttonSearch;
	// 上一页按钮
	private JButton buttonPreviousPage;
	// 下一页按钮
	private JButton buttonNextPage;
	// 删除情景分析法案例按钮
	private JButton buttonDeleteSwotTask;
	// 修改情景分析法案例按钮
	private JButton buttonModifySwotTask;

	// 用于操控某表格框架
	private DefaultTableModel dtm;
	// 当前操作的表格
	private JTable table;
	// 用于实现滚动条的面板
	private JScrollPane scrollpaneTable;
	// 情景分析法查询结果的列表
	private List<SwotTask> commonList;
	// 当前操控的行号
	private int selectedTableRow;
	// 当前页码
	private int pageCurrent;
	// 总页数
	private int pageSum;
	// 页大小
	private int pageDivider;

	// 双击表格修改情景分析法案例的属性的框架
	private JFrame frameModifyTask;
	// 双击表格修改情景分析法案例的属性的面板
	private JPanel panelModifyTask;
	// 装载双击表格修改情景分析法案例的案例名称的面板
	private JPanel panelLayTaskNameInModifyTask;
	// 装载双击表格修改情景分析法案例的案例描述的面板
	private JPanel panelLayTaskDescriptionInModifyTask;
	// 装载双击表格修改情景分析法案例的案例研讨时间的面板
	private JPanel panelLayArgueTimeInModifyTask;
	// 装载双击表格修改情景分析法案例的案例研讨地点的面板
	private JPanel panelLayTaskLocationInModifyTask;
	// 装载双击表格修改情景分析法案例的按钮的面板
	private JPanel panelLayPageButtonInModifyTask;
	// 双击表格修改情景分析法案例的案例名称标签
	private JLabel labelTaskNameInModifyTask;
	// 双击表格修改情景分析法案例的案例描述标签
	private JLabel labelTaskDescriptionInModifyTask;
	// 双击表格修改情景分析法案例的案例研讨时间标签
	private JLabel labelArgueTimeInModifyTask;
	// 双击表格修改情景分析法案例的案例研讨地点标签
	private JLabel labelTaskLocationInModifyTask;
	// 双击表格修改情景分析法案例的案例名称文本域
	private JTextField textfieldfTaskNameInModifyTask;
	// 双击表格修改情景分析法案例的案例描述文本域
	private JTextField textfieldTaskDescriptionInModifyTask;
	// 双击表格修改情景分析法案例的案例研讨时间日期选择器
	private DatePicker datePickerArgueTimeInModifyTask;
	// 双击表格修改情景分析法案例的案例研讨研讨地点下拉列表
	private JComboBox<String> comboBoxTaskLocationInModifyTask;
	// 双击表格修改情景分析法案例的确认修改按钮
	private JButton buttonConfirmInModifyTask;
	// 双击表格修改情景分析法案例的取消按钮
	private JButton buttonCancelInModifyTask;

	// 右键弹出菜单：用于修改，删除情景分析法案例
	private JPopupMenu popupMenu;
	// 右键删除案例
	private JMenuItem popmenuItemDeleteSwotTask;
	// 右键修改案例
	private JMenuItem popmenuItemModifySwotTask;

	/**
	 * 构造函数：初始化操作接口、控件及布局。
	 * 
	 * @param scenarioTask
	 *            表示装载的SWOT法案例实例
	 */
	public SwotSearchPanel() {
		swotTaskDAO = new SwotTaskDAO();
		
		initializeComponent();
		
		layoutComponent();
	}

	/**
	 * 初始化控件。
	 */
	private void initializeComponent() {
		//JPanel设置
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// 设置对话框字体
		StaticConfig.setJOptionPaneFont();

		splitpane = new JSplitPane();
		// 让分隔线显示出箭头
		splitpane.setOneTouchExpandable(true);
		// 当用户操作分隔线箭头时，系统重绘图形
		splitpane.setContinuousLayout(true);
		// 设置方向或者分隔窗格的方式（HORIZONTAL_SPLIT表示左右分隔，VERTICAL_SPLIT）
		splitpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		// 设置分隔条的大小
		splitpane.setDividerSize(3);
		// 设置分隔条的位置
		splitpane.setDividerLocation(400);
		
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
		panelLayTableANDPageButton.setPreferredSize(new Dimension(0, 550));			
		
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
		
		labelTaskName = new JLabel("案例名称");
		labelTaskName.setFont(StaticConfig.FONT_SEARCHVIEW_LABEL);
		labelTaskLocation = new JLabel("研讨地点");
		labelTaskLocation.setFont(StaticConfig.FONT_SEARCHVIEW_LABEL);
		labelArgueTimeStartDate = new JLabel("开始日期");
		labelArgueTimeStartDate.setFont(StaticConfig.FONT_SEARCHVIEW_LABEL);
		labelArgueTimeEndDate = new JLabel("结束日期");
		labelArgueTimeEndDate.setFont(StaticConfig.FONT_SEARCHVIEW_LABEL);
		
		textfieldTaskName = new JTextField();
		textfieldTaskName.setFont(StaticConfig.FONT_SEARCHVIEW_TEXTFIELD);
		textfieldTaskLocation = new JTextField();
		textfieldTaskLocation.setFont(StaticConfig.FONT_SEARCHVIEW_TEXTFIELD);
		datePickerArgueTimeStartDate = new JDatePicker();
		datePickerArgueTimeStartDate.setFont(StaticConfig.FONT_NEWTASK_TEXTFIELD);
		// width对应按钮占比宽度
		datePickerArgueTimeStartDate.setPreferredSize(StaticConfig.WIDTH_JDATEPICKER_BUTTON, StaticConfig.FONT_NEWTASK_TEXTFIELD.getSize() + 11);		
		datePickerArgueTimeEndDate = new JDatePicker();
		datePickerArgueTimeEndDate.setFont(StaticConfig.FONT_NEWTASK_TEXTFIELD);
		// width对应按钮占比宽度
		datePickerArgueTimeEndDate.setPreferredSize(StaticConfig.WIDTH_JDATEPICKER_BUTTON, StaticConfig.FONT_NEWTASK_TEXTFIELD.getSize() + 11);		
		
		
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
		buttonSearch.setFont(StaticConfig.FONT_SEARCHVIEW_BUTTON);
		buttonPreviousPage = new JButton("上一页");
		buttonPreviousPage.setFont(StaticConfig.FONT_SEARCHVIEW_BUTTON);
		buttonNextPage = new JButton("下一页");
		buttonNextPage.setFont(StaticConfig.FONT_SEARCHVIEW_BUTTON);
		buttonDeleteSwotTask = new JButton("删除");
		buttonDeleteSwotTask.setFont(StaticConfig.FONT_SEARCHVIEW_BUTTON);
		buttonModifySwotTask = new JButton("修改");
		buttonModifySwotTask.setFont(StaticConfig.FONT_SEARCHVIEW_BUTTON);
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
	    popmenuItemDeleteSwotTask.setFont(StaticConfig.FONT_TABLE_RIGHT_CLICK_MENU);
	    popmenuItemModifySwotTask = new JMenuItem("修改");
	    popmenuItemModifySwotTask.setFont(StaticConfig.FONT_TABLE_RIGHT_CLICK_MENU);

		popmenuItemDeleteSwotTask.addActionListener(this);
		popmenuItemModifySwotTask.addActionListener(this);
		
	}

	/**
	 * 初始化布局
	 */
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

	/**
	 * @param tableType
	 *            表格类型
	 * @return 表格 初始化表格
	 */
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
		    new Object[] {"编号", "案例名称", "案例描述", "研讨日期", "研讨地点", "taskID"}
	    );

	    final JTable table = new JTable(dtm);
	    table.getTableHeader().setFont(StaticConfig.FONT_TABLEHEADER);
	    
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
		columnModel.removeColumn(columnModel.getColumn(5));		
		//第五列为隐藏列，存储案例ID
	    table.setDefaultRenderer(String.class, new MultiLineTableCellRender());

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// getSelectedRow()对于右键无效，值为-1
				selectedTableRow = table.rowAtPoint(e.getPoint());
				// 监听右键菜单
				if (e.getButton() == MouseEvent.BUTTON3) {
					// 在鼠标点击位置弹出右键菜单
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				} else if (e.getClickCount() == 2) {
					// 监听双击菜单
					if (table.getValueAt(0, 0) != "")
						modifyTask();
					else
						JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "", JOptionPane.INFORMATION_MESSAGE);
				}

				if (panelLayView != null)
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

	/**
	 * 删除情景分析法案例
	 */
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
		swotTaskDAO.deleteTaskByID((int)(table.getModel().getValueAt(selectedTableRow, 5)));	//删除案例
	
		//表格更新
		dtm = (DefaultTableModel)table.getModel();
		dtm.removeRow(selectedTableRow);
		
		// 若是唯一一行，则删除后插入一行空行
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

	/**
	 * 修改情景分析法案例
	 */
	public void modifyTask() {
		frameModifyTask = new JFrame("修改案例");
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
		
		labelTaskNameInModifyTask = new JLabel("案例名称");
		labelTaskNameInModifyTask.setFont(StaticConfig.FONT_DIALOG_LABEL);
		labelTaskDescriptionInModifyTask = new JLabel("案例描述");
		labelTaskDescriptionInModifyTask.setFont(StaticConfig.FONT_DIALOG_LABEL);
		labelArgueTimeInModifyTask = new JLabel("研讨日期");
		labelArgueTimeInModifyTask.setFont(StaticConfig.FONT_DIALOG_LABEL);
		labelTaskLocationInModifyTask = new JLabel("研讨地点");
		labelTaskLocationInModifyTask.setFont(StaticConfig.FONT_DIALOG_LABEL);
		
		textfieldfTaskNameInModifyTask = new JTextField();
		textfieldfTaskNameInModifyTask.setFont(StaticConfig.FONT_DIALOG_TEXTFIELD);
		textfieldTaskDescriptionInModifyTask = new JTextField();
		textfieldTaskDescriptionInModifyTask.setFont(StaticConfig.FONT_DIALOG_TEXTFIELD);
		
		datePickerArgueTimeInModifyTask = new JDatePicker();
		((Component)datePickerArgueTimeInModifyTask).setFont(StaticConfig.FONT_DIALOG_TEXTFIELD);
		
		comboBoxTaskLocationInModifyTask = new JComboBox<String> ();
		comboBoxTaskLocationInModifyTask.setFont(StaticConfig.FONT_DIALOG_TEXTFIELD);
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
		buttonConfirmInModifyTask.setFont(StaticConfig.FONT_DIALOG_BUTTON);
		buttonCancelInModifyTask = new JButton("取消");
		buttonCancelInModifyTask.setFont(StaticConfig.FONT_DIALOG_BUTTON);
		
		buttonConfirmInModifyTask.addActionListener(this);
		buttonCancelInModifyTask.addActionListener(this);
		
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
	}

	/*
	 *综合处理按钮事件
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonSearch) {
			// 查询按钮
			if (panelLayView != null)
				panelLayView.removeAll();
			panelLayView.revalidate();
			panelLayView.repaint();

			// 默认当下时间
			Date dateStartDate = new Date();
			Calendar dateStartCalendar = Calendar.getInstance();
			// 如若用户未填写初值，则设置为空
			if (!datePickerArgueTimeStartDate.getModel().isSelected())
				dateStartDate = null;
			else {
				dateStartCalendar.set(datePickerArgueTimeStartDate.getModel().getYear(),
						datePickerArgueTimeStartDate.getModel().getMonth(),
						datePickerArgueTimeStartDate.getModel().getDay(), 0, 0, 0);
				dateStartCalendar.set(Calendar.MILLISECOND, 0);
				dateStartDate = dateStartCalendar.getTime();
			}

			// 默认当下时间
			Date dateEndDate = new Date();
			Calendar dateEndCalendar = Calendar.getInstance();
			// 如若用户未填写初值，则设置为空
			if (!datePickerArgueTimeEndDate.getModel().isSelected())
				dateEndDate = null;
			else {
				dateEndCalendar.set(datePickerArgueTimeEndDate.getModel().getYear(),
						datePickerArgueTimeEndDate.getModel().getMonth(),
						datePickerArgueTimeEndDate.getModel().getDay(), 0, 0, 0);
				dateEndCalendar.set(Calendar.MILLISECOND, 0);
				dateEndDate = dateEndCalendar.getTime();
			}
			
			//得到查询结果
			List<SwotTask> swotTaskListByLocation = swotTaskDAO.getSwotTaskByLocation(textfieldTaskLocation.getText());
			List<SwotTask> swotTaskListByDate = swotTaskDAO.getSwotTaskByDate(dateStartDate, dateEndDate);
			List<SwotTask> swotTaskListByName = swotTaskDAO.getSwotTaskByName(textfieldTaskName.getText());
			
			//两个复杂类型list直接retainAll方法结果是空，在此猜测是List类型（复杂）所致，故只提取出taskID进行比较得出公共元素
			List<Integer> taskIDListByLocation = new ArrayList<Integer>();	
			for(SwotTask list:swotTaskListByLocation) {		
				// 在有外键关联时若设置成lazy属性，表明只有遍历用到外键时才查询其数据（为了节省开销）。
				// 此处若直接查询得SwotTask列表则不会有数据，必须打开SwotTask查询Task表得到数据才有数据
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
			if(commonList.size() % pageDivider == 0)	
				//查询结果数量是每页数量的整数倍
				pageSum = commonList.size() / pageDivider;
			else
				pageSum = commonList.size() / pageDivider + 1;
			pageCurrent = 0;
			
			if(commonList.size() > pageDivider)
				buttonNextPage.setEnabled(true);
			
			dtm = (DefaultTableModel)table.getModel(); 
			dtm.setRowCount(0);			
			//清空原有数据
			dtm.addRow(new Object[]{"", "", "", "", "", -1});
			for(int i = pageCurrent * pageDivider; i < pageCurrent * pageDivider + pageDivider; i++) {
				if(i < commonList.size()){
					if(dtm.getValueAt(0, 0) == "") {	
						//第一行
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
					} else {		
						//不是第一行
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
			// 上一页按钮
			pageCurrent--;
			//清空原有数据
			dtm.setRowCount(0);		
			dtm.addRow(new Object[]{"", "", "", "", "", -1});
			for(int i = pageCurrent * pageDivider; i < pageCurrent * pageDivider + pageDivider; i++) {		
				if(i < commonList.size()){
					if(dtm.getValueAt(0, 0) == "") {		
						//第一行
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
					} else{	     
						//不是第一行
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
			//清空原有数据
			dtm.setRowCount(0);			
			dtm.addRow(new Object[]{"", "", "", "", "", -1});
			for(int i = pageCurrent * pageDivider; i < pageCurrent * pageDivider + pageDivider; i++) {		
				if(i < commonList.size()){
					if(dtm.getValueAt(0, 0) == "") {		
						//第一行
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
					} else{	     
						//不是第一行
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
			// 删除SWOT法案例按钮
			if(table.getValueAt(0, 0) != "") 
				deleteTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除操作", "",JOptionPane.INFORMATION_MESSAGE);
		}	
		else if(e.getSource() == buttonModifySwotTask) {
			// 修改SWOT法案例按钮
			if(table.getValueAt(0, 0) != "") 
				modifyTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
		}	
		else if(e.getSource() == buttonConfirmInModifyTask) {
			// 确认修改SWOT法案例按钮
			Calendar calendar = Calendar.getInstance();
			// 年份自动多余，不知缘由
            calendar.set(datePickerArgueTimeInModifyTask.getModel().getYear(), 		
            		datePickerArgueTimeInModifyTask.getModel().getMonth(), datePickerArgueTimeInModifyTask.getModel().getDay(), 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
			
			SwotTask swotTask = new SwotTask();
			// 获得remove掉的列的data必须使用TableModel
			swotTask.setTaskID((int)(table.getModel().getValueAt(selectedTableRow, 5)));		
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
			// 取消修改情景分析法案例按钮
			frameModifyTask.dispose();
		}
		else if(e.getSource() == popmenuItemDeleteSwotTask) {
			// 右键菜单删除情景分析法案例按钮
			if(table.getValueAt(0, 0) != "") 
				deleteTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除操作", "",JOptionPane.INFORMATION_MESSAGE);
		}
		
		else if(e.getSource() == popmenuItemModifySwotTask) {
			// 右键菜单修改情景分析法案例按钮
			if(table.getValueAt(0, 0) != "") 
				modifyTask();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
 