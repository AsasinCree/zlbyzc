package zlbyzc.sub3.analysis.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import zlbyzc.sub3.analysis.entities.ScenarioLogic;
import zlbyzc.sub3.analysis.entities.ScenarioProperty;
import zlbyzc.sub3.analysis.entities.ScenarioResult;
import zlbyzc.sub3.analysis.entities.ScenarioTask;
import zlbyzc.sub3.analysis.services.ScenarioLogicDAO;
import zlbyzc.sub3.analysis.services.ScenarioPropertyDAO;
import zlbyzc.sub3.analysis.services.ScenarioResultDAO;
import zlbyzc.sub3.analysis.services.ScenarioTaskDAO;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioLogicDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioPropertyDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioResultDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioTaskDAOInterface;

public class ScenarioSearchContentPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScenarioTask scenarioTask;
	
	private JPanel panelBottom;
	
	private JTabbedPane tabbedPane;
	
	private JPanel panelTableViewInTabbedPane;
	private JPanel panelTestViewInTabbedPane;
	
	//TableView
	private JPanel panelLayJInternalFrameInTableView;
	private JPanel panelLayButtonInTableView;
	
	private JDesktopPane desktop;
	private JInternalFrame jifFocus;
	private JInternalFrame jifKeyFactor;
	private JInternalFrame jifDrivingpower;
	private JInternalFrame jifUncertainties;	
	private JInternalFrame jifDevelopment;	
	private JInternalFrame jifResult;	
	private int jifWidth;
	private int jifHeight;
	
	//六个属性table
	private DefaultTableModel dtm;
	private String propertyType;		//属性名称标志
	private int selectedTableRow;
	private JTable tableFocus;
	private JTable tableKeyFactor;
	private JTable tableDrivingpower;
	private JTable tableUncertainties;	
	private JTable tableDevelopment;	
	private JTable tableResult;	
	private JScrollPane spTableFocus;
	private JScrollPane spTableKeyFactor;
	private JScrollPane spTableDrivingpower;
	private JScrollPane spTableUncertainties;	
	private JScrollPane spTableDevelopment;	
	private JScrollPane spTableResult;	
	
	//右键菜单
	private JPopupMenu popupMenu;
	private JMenuItem popMenuItemAddProperty;
	private JMenuItem popMenuItemDeleteProperty;
	private JMenuItem popMenuItemModifyProperty;
	
	//表格双击修改属性内容面板
	private JFrame frameModifyProperty;
	private JPanel panelModifyProperty;
	private JPanel panelLayLabelInModifyProperty;
	private JPanel panelLaytextAreaContentInModifyProperty;
	private JPanel panelLayButtonInModifyProperty;
	private JLabel lablePropertyContentInModifyProperty;
	private JTextArea textAreaPropertyContentInModifyProperty;
	private JButton buttonConfirmInModifyProperty;
	private JButton buttonCancelInModifyProperty;
	
	//表格右键菜单添加属性内容面板
	private JFrame frameAddProperty;
	private JPanel panelAddProperty;
	private JPanel panelLayLabelInAddProperty;
	private JPanel panelLayTextareaInAddProperty;
	private JPanel panelLayButtonInAddProperty;
	private JLabel lableContentInAddProperty;
//	private JLabel lableRemark1InAddProperty;
//	private JLabel lableRemark2InPopmenuAddProperty;
	private JTextArea textareaContentInAddProperty;
//	private JTextArea textareaRemark1InAddProperty;
//	private JTextArea textareaRemark2InAddProperty;
	private JButton buttonConfirmInAddProperty;
	private JButton buttonCancelInAddProperty;
	
	public ScenarioSearchContentPanel(ScenarioTask scenarioTask) {		
		
		this.scenarioTask = scenarioTask;
		
		initializeComponent();
		
		layoutComponent();
		
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void initializeComponent()  {		
		
		//JPanel设置
		setLayout(new BorderLayout());
		
		/*-------------底部面板Start--------------*/
		panelBottom = new JPanel();
		panelBottom.setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane();
	
		panelTableViewInTabbedPane = new JPanel();
		panelTableViewInTabbedPane.setLayout(new BorderLayout());
		panelTestViewInTabbedPane = new JPanel();		
		
		panelLayJInternalFrameInTableView = new JPanel();		
		panelLayButtonInTableView = new JPanel();		
		panelLayJInternalFrameInTableView.setLayout(new BorderLayout());
	
		panelLayJInternalFrameInTableView.addComponentListener(new ComponentListener() {
						
			@Override
			public void componentResized(ComponentEvent e) {		//由于panel在布局初始化时没有大小，固获取其大小要放在此处监听，并同时把6个jif设置大小位置后放入该panel
				// TODO Auto-generated method stub
				
				jifWidth = panelLayJInternalFrameInTableView.getSize().width;
				jifHeight = panelLayJInternalFrameInTableView.getSize().height / 6;
	
				refreshPanelLayJInternalFrameInTableView();
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		jifFocus = initializeJInternalFrame("决策焦点");
		jifKeyFactor = initializeJInternalFrame("关键因素");
		jifDrivingpower = initializeJInternalFrame("驱动力量");
		jifUncertainties = initializeJInternalFrame("不确定因素");
		jifDevelopment = initializeJInternalFrame("发展逻辑");
		jifResult = initializeJInternalFrame("分析结果");
		
		tableFocus = initializeTable("focus");
		tableKeyFactor = initializeTable("keyfactor");
		tableDrivingpower = initializeTable("drivingpower");
		tableUncertainties = initializeTable("uncertainties");	
		tableDevelopment = initializeTable("development");	
		tableResult = initializeTable("result");
		
		importData();
			
		spTableFocus = new JScrollPane(tableFocus);
		spTableKeyFactor = new JScrollPane(tableKeyFactor);
		spTableDrivingpower = new JScrollPane(tableDrivingpower);
		spTableUncertainties = new JScrollPane(tableUncertainties);
		spTableDevelopment = new JScrollPane(tableDevelopment);
		spTableResult = new JScrollPane(tableResult);
		
		popupMenu = new JPopupMenu();
		popMenuItemAddProperty = new JMenuItem("添加");  
	    popMenuItemDeleteProperty = new JMenuItem("删除");  
	    popMenuItemModifyProperty = new JMenuItem("修改");
	    popupMenu.add(popMenuItemAddProperty);  
        popupMenu.add(popMenuItemDeleteProperty);  
        popupMenu.add(popMenuItemModifyProperty); 
        
		popMenuItemAddProperty.addActionListener(this);
		popMenuItemDeleteProperty.addActionListener(this);
		popMenuItemModifyProperty.addActionListener(this);
		        
	}
	
	public void layoutComponent() {
				
		/*-------------底部面板Start--------------*/
		jifFocus.setContentPane(spTableFocus);
		jifKeyFactor.setContentPane(spTableKeyFactor);
		jifDrivingpower.setContentPane(spTableDrivingpower);
		jifUncertainties.setContentPane(spTableUncertainties);
		jifDevelopment.setContentPane(spTableDevelopment);
		jifResult.setContentPane(spTableResult);
		
		panelTableViewInTabbedPane.add(panelLayJInternalFrameInTableView, BorderLayout.CENTER);
		panelTableViewInTabbedPane.add(panelLayButtonInTableView, BorderLayout.SOUTH);  
		
		tabbedPane.add(panelTableViewInTabbedPane, "表格");
		tabbedPane.add(panelTestViewInTabbedPane,"图形");
		
		panelBottom.add(tabbedPane);
		/*-------------底部面板End-----------------*/

		add(panelBottom, BorderLayout.CENTER);
		
	}
	
	public JInternalFrame initializeJInternalFrame(String tableType) {
		
		final JInternalFrame jif = new JInternalFrame(tableType, true, false, true, true);
		
		jif.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		jif.setLayout(new BorderLayout());				
		jif.setVisible(true);
						
		jif.addInternalFrameListener(new InternalFrameAdapter() {	//获取当前被选中的JInternalFrame的index，以便于toolbar定位（MouseListener监听不到）
			
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				// TODO Auto-generated method stub

				propertyType = jif.getTitle();
			}
		});
		
		return jif;
		
	}
	
	public JTable initializeTable(final String tableType) {
					
	    dtm = new DefaultTableModel() {
		      /**
			 * 
			 */
	    	private static final long serialVersionUID = 1L;
			public Class<String> getColumnClass(int columnIndex) {
		        return String.class;
		    }
		    public boolean isCellEditable(int row, int column) {		//不可编辑，变成双击弹出修改框
		        return false;
		    }
	    };
	    dtm.setDataVector(
	    	new Object[][]{
		    	{"", "", -1}
		    }, 
		    new Object[] {"编号", "内容", "ID"}
	    );

	    final JTable table = new JTable(dtm);	    
	    TableColumnModel columnModel=table.getColumnModel();
		columnModel.getColumn(0).setMaxWidth(40);
		columnModel.getColumn(0).setPreferredWidth(40);		
		columnModel.removeColumn(columnModel.getColumn(2));		//第三列为隐藏列，存储属性ID。获得remove掉的列的data必须使用TableModel
	    table.setDefaultRenderer(String.class, new MultiLineTableCellRender());		//渲染成可换行
	    
	    table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				
				selectedTableRow = table.rowAtPoint(e.getPoint());		//getSelectedRow()对于右键无效，值为-1
				
				table.changeSelection(table.getSelectedRow(), table.getSelectedColumn(), true, false);
				table.requestFocus();
				
                propertyType = tableType;		//标记当前选中的属性表格
				
				if(e.getButton() == MouseEvent.BUTTON3) {	//监听右键菜单
					
					popupMenu.show(e.getComponent(), e.getX(), e.getY());	 //在鼠标点击位置弹出右键菜单
	                
				} else if(e.getClickCount() == 2) {		//监听双击菜单
					
					if(getSelectedTable().getValueAt(0, 0) != "") 
						modifyContent();
					else
						JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		return table;
		
	}
	
	public void importData() {
		
		ScenarioTaskDAOInterface scenarioTaskDAO = new ScenarioTaskDAO();
		
		List<ScenarioProperty> scenarioPropertyList = scenarioTaskDAO.getAllTaskProperties(scenarioTask);
		if(scenarioPropertyList != null) {
			for(ScenarioProperty scenarioProperty:scenarioPropertyList) {
				
				dtm = (DefaultTableModel)getInsertTable(scenarioProperty.getPropertyType()).getModel();
				System.out.println(scenarioProperty.getPropertyType());

				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(scenarioProperty.getPropertyContent(), 0, 1);
					dtm.setValueAt(scenarioProperty.getPropertyID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, scenarioProperty.getPropertyContent(), scenarioProperty.getPropertyID()});		
				}			
			}
		}	
		
		List<ScenarioLogic> scenarioLogicList = scenarioTaskDAO.getAllTaskLogics(scenarioTask);
		if(scenarioLogicList != null) {
			for(ScenarioLogic scenarioLogic:scenarioLogicList) {
				
				dtm = (DefaultTableModel)tableDevelopment.getModel();
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(scenarioLogic.getLogicContent(), 0, 1);
					dtm.setValueAt(scenarioLogic.getLogicID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, scenarioLogic.getLogicContent(), scenarioLogic.getLogicID()});		
				}			
			}
		}	
		
		List<ScenarioResult> scenarioResultList = scenarioTaskDAO.getAllTaskResults(scenarioTask);
		if(scenarioResultList != null) {
			for(ScenarioResult scenarioResult:scenarioResultList) {
				
				dtm = (DefaultTableModel)tableDevelopment.getModel();
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(scenarioResult.getResultContent(), 0, 1);
					dtm.setValueAt(scenarioResult.getResultID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, scenarioResult.getResultContent(), scenarioResult.getResultID()});		
				}			
			}
		}

	}
	
	public void refreshPanelLayJInternalFrameInTableView() {		//可同时对总框架改变大小时做出内部jif大小相应调整
		
		//清空原有的参与者面板
		if(desktop != null) {
			panelLayJInternalFrameInTableView.remove(desktop);
		}
		
		//将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后  actorpanel 层次问题【未知缘由】
		refreshJInternalFrameStatus(jifFocus);
		refreshJInternalFrameStatus(jifKeyFactor);
		refreshJInternalFrameStatus(jifDrivingpower);
		refreshJInternalFrameStatus(jifUncertainties);
		refreshJInternalFrameStatus(jifDevelopment);
		refreshJInternalFrameStatus(jifResult);
	
		desktop = new JDesktopPane();
		desktop.setLayout(null);
		
		jifFocus.setBounds(0, 0, jifWidth, jifHeight);
		jifKeyFactor.setBounds(0, jifHeight, jifWidth, jifHeight);
		jifDrivingpower.setBounds(0, jifHeight*2, jifWidth, jifHeight);
		jifUncertainties.setBounds(0, jifHeight*3, jifWidth, jifHeight);
		jifDevelopment.setBounds(0, jifHeight*4, jifWidth, jifHeight);
		jifResult.setBounds(0, jifHeight*5, jifWidth, jifHeight);
		
		desktop.add(jifFocus);
		desktop.add(jifKeyFactor);
		desktop.add(jifDrivingpower);
		desktop.add(jifUncertainties);
		desktop.add(jifDevelopment);
		desktop.add(jifResult);
		
		panelLayJInternalFrameInTableView.add(desktop);
		
	}
	
	//将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后  actorpanel 层次问题【未知缘由】
	public void refreshJInternalFrameStatus(JInternalFrame jif) {
		jif.pack();
		try {
			if(jif.isIcon())
				jif.setIcon(false);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		try {
			if(jif.isSelected())
				jif.setSelected(false);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void addContent() {
		
		/*-------------初始化Start--------------*/
		frameAddProperty = new JFrame("添加属性");
		frameAddProperty.setSize(500, 400);
		frameAddProperty.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameAddProperty.setLocationRelativeTo( null ); 
		frameAddProperty.getContentPane().setLayout(new BorderLayout());
		
		panelAddProperty = new JPanel();
		panelLayLabelInAddProperty = new JPanel();
		panelLayTextareaInAddProperty = new JPanel();
		panelLayButtonInAddProperty = new JPanel();
		panelAddProperty.setLayout(new BoxLayout(panelAddProperty, BoxLayout.Y_AXIS));
		panelLayTextareaInAddProperty.setLayout(new BoxLayout(panelLayTextareaInAddProperty, BoxLayout.X_AXIS));
		panelLayButtonInAddProperty.setLayout(new BoxLayout(panelLayButtonInAddProperty, BoxLayout.X_AXIS));
		
		lableContentInAddProperty = new JLabel("内容");
//		lableRemark1InAddProperty = new JLabel("备注1");
//		lableRemark2InPopmenuAddProperty = new JLabel("备注2");
		
		textareaContentInAddProperty = new JTextArea(5,5);
		textareaContentInAddProperty.setLineWrap(true);		//支持自动换行
		textareaContentInAddProperty.setBorder(BorderFactory.createTitledBorder(""));
//		textareaRemark1InAddProperty = new JTextArea();
//		textareaRemark2InAddProperty = new JTextArea();		
//		textareaRemark1InAddProperty.setLineWrap(true);		//支持自动换行
//		textareaRemark2InAddProperty.setLineWrap(true);		//支持自动换行
		
//		textareaContentInAddProperty.setMinimumSize(new Dimension(200,200));
//		textareaContentInAddProperty.setMaximumSize(new Dimension(900,900));
//		textareaRemark1InAddProperty.setMinimumSize(new Dimension(100,250));
//		textareaRemark1InAddProperty.setMaximumSize(new Dimension(200,200));
//		textareaRemark2InAddProperty.setMinimumSize(new Dimension(100,250));
//		textareaRemark2InAddProperty.setMaximumSize(new Dimension(200,200));
	

		buttonConfirmInAddProperty = new JButton("确定");
		buttonCancelInAddProperty = new JButton("取消");		
		
		buttonConfirmInAddProperty.addActionListener(this);
		buttonCancelInAddProperty.addActionListener(this);
		/*-------------初始化End--------------*/
		
		/*-------------布局Start--------------*/
		panelLayLabelInAddProperty.add(lableContentInAddProperty);
		panelLayTextareaInAddProperty.add(Box.createHorizontalStrut(20));
		panelLayTextareaInAddProperty.add(textareaContentInAddProperty);
		panelLayTextareaInAddProperty.add(Box.createHorizontalStrut(20));
//		panelLayTextareaInAddProperty.add(lableRemark1InAddProperty);	
//		panelLayTextareaInAddProperty.add(textareaRemark1InAddProperty);
//		panelLayTextareaInAddProperty.add(lableRemark2InPopmenuAddProperty);	
//		panelLayTextareaInAddProperty.add(textareaRemark2InAddProperty);	
		panelLayButtonInAddProperty.add(buttonConfirmInAddProperty);
		panelLayButtonInAddProperty.add(Box.createHorizontalStrut(10));
		panelLayButtonInAddProperty.add(buttonCancelInAddProperty);
		
		panelAddProperty.add(Box.createVerticalStrut(20));
		panelAddProperty.add(panelLayLabelInAddProperty);
		panelAddProperty.add(Box.createVerticalStrut(5));
		panelAddProperty.add(panelLayTextareaInAddProperty);
		panelAddProperty.add(Box.createVerticalStrut(10));
		panelAddProperty.add(panelLayButtonInAddProperty);
		panelAddProperty.add(Box.createVerticalStrut(20));
		
		frameAddProperty.setContentPane(panelAddProperty);			
		frameAddProperty.setVisible(true);
		/*-------------布局End--------------*/
		
	}
	
	public void deleteContent() {
		
		if(getSelectedTable().getValueAt(0, 0) != "") {
			if(propertyType.equals("focus") || propertyType.equals("keyfactor") ||
					propertyType.equals("drivingpower") || propertyType.equals("uncertainties")) {
				ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
				scenarioPropertyDAO.deletePropertyByID(
						(int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));		//获得remove掉的列的data必须使用TableModel
			}
			else if(propertyType.equals("development")) {
				ScenarioLogicDAOInterface scenarioPropertyDAO = new ScenarioLogicDAO();
				scenarioPropertyDAO.deleteLogicByID(
						(int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));		//获得remove掉的列的data必须使用TableModel
			}
			else if(propertyType.equals("result")) {
				ScenarioResultDAOInterface scenarioPropertyDAO = new ScenarioResultDAO();
				scenarioPropertyDAO.deleteResultByID(
						(int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));		//获得remove掉的列的data必须使用TableModel
			}
							
			dtm = (DefaultTableModel)getSelectedTable().getModel();
			dtm.removeRow(selectedTableRow);
			
			if(getSelectedTable().getRowCount() == 0) {
				dtm.addRow(new Object[]{"", "", -1});
			}else {
				for(int i = 0; i < dtm.getRowCount(); i++) {
					dtm.setValueAt(i + 1, i, 0);
				}
			}	
		}else {
			JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除内容操作", "",JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
	
	//修改属性内容面板START（双击单元格）
	public void modifyContent() {
		
		/*-------------初始化Start--------------*/
		frameModifyProperty = new JFrame("修改属性");
		frameModifyProperty.setSize(500, 400);
		frameModifyProperty.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameModifyProperty.setLocationRelativeTo( null ); 
		
		panelModifyProperty = new JPanel();
		panelModifyProperty = new JPanel();
		panelLayLabelInModifyProperty = new JPanel();
		panelLaytextAreaContentInModifyProperty = new JPanel();
		panelLayButtonInModifyProperty = new JPanel();
		panelModifyProperty.setLayout(new BoxLayout(panelModifyProperty, BoxLayout.Y_AXIS));
		panelLaytextAreaContentInModifyProperty.setLayout(new BoxLayout(panelLaytextAreaContentInModifyProperty, BoxLayout.X_AXIS));
		panelLayButtonInModifyProperty.setLayout(new BoxLayout(panelLayButtonInModifyProperty, BoxLayout.X_AXIS));
		
		lablePropertyContentInModifyProperty=new JLabel("内容");
		
		textAreaPropertyContentInModifyProperty = new JTextArea(5,5);
		textAreaPropertyContentInModifyProperty.setText((String) getSelectedTable().getValueAt(selectedTableRow, 1));
		textAreaPropertyContentInModifyProperty.setLineWrap(true);		//支持自动换行
		textAreaPropertyContentInModifyProperty.setBorder(BorderFactory.createTitledBorder(""));
//		textAreaPropertyContentInModifyProperty.setMinimumSize(new Dimension(100,80));
//		textAreaPropertyContentInModifyProperty.setMaximumSize(new Dimension(150,130));
		
		buttonConfirmInModifyProperty = new JButton("确定");
		buttonCancelInModifyProperty = new JButton("取消");		
		
		buttonConfirmInModifyProperty.addActionListener(this);
		buttonCancelInModifyProperty.addActionListener(this);
		/*-------------初始化End--------------*/
		
		/*-------------布局Start--------------*/
		panelLayLabelInModifyProperty.add(lablePropertyContentInModifyProperty);
		panelLaytextAreaContentInModifyProperty.add(Box.createHorizontalStrut(20));
		panelLaytextAreaContentInModifyProperty.add(textAreaPropertyContentInModifyProperty);
		panelLaytextAreaContentInModifyProperty.add(Box.createHorizontalStrut(20));
		
		panelLayButtonInModifyProperty.add(buttonConfirmInModifyProperty);
		panelLayButtonInModifyProperty.add(Box.createHorizontalStrut(10));
		panelLayButtonInModifyProperty.add(buttonCancelInModifyProperty);
		
		panelModifyProperty.add(Box.createVerticalStrut(20));
		panelModifyProperty.add(panelLayLabelInModifyProperty);
		panelModifyProperty.add(Box.createVerticalStrut(5));
		panelModifyProperty.add(panelLaytextAreaContentInModifyProperty);
		panelModifyProperty.add(Box.createVerticalStrut(10));
		panelModifyProperty.add(panelLayButtonInModifyProperty);
		panelModifyProperty.add(Box.createVerticalStrut(20));
		
		frameModifyProperty.setContentPane(panelModifyProperty);			
		frameModifyProperty.setVisible(true);
		/*-------------布局End--------------*/
		
	}
	//修改属性内容面板END
	
	//返回当前选中的table
	public JTable getSelectedTable() {
		
		if(propertyType.equals("focus")) {
			return tableFocus;
		}
		else if(propertyType.equals("keyfactor")) {
			return tableKeyFactor;
		}
		else if(propertyType.equals("drivingpower"))  {
			return tableDrivingpower;
		}
		else if(propertyType.equals("uncertainties")) {
			return tableUncertainties;
		}
		else if(propertyType.equals("development")) {
			return tableDevelopment;
		}
		else if(propertyType.equals("result")) {
			return tableResult;
		}
		
		return null;
		
	}
	
	//查询面板所用
	public JTable getInsertTable(String PropertyType) {
		
		if(PropertyType.equals("focus")) {
			return tableFocus;
		}
		else if(PropertyType.equals("keyfactor")) {
			return tableKeyFactor;
		}
		else if(PropertyType.equals("drivingpower"))  {
			return tableDrivingpower;
		}
		else if(PropertyType.equals("uncertainties")) {
			return tableUncertainties;
		}
		
		return null;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == popMenuItemAddProperty) {
			
			addContent();
			
		}
		
		else if(e.getSource() == popMenuItemDeleteProperty) {
			
			deleteContent();
			
		}
		
		else if(e.getSource() == popMenuItemModifyProperty) {
			
			if(getSelectedTable().getValueAt(0, 0) != "") 
				modifyContent();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		else if(e.getSource() == buttonConfirmInModifyProperty) {
			
			if(propertyType.equals("focus") || propertyType.equals("keyfactor") ||
					propertyType.equals("drivingpower") || propertyType.equals("uncertainties")) {
				
				ScenarioProperty scenarioProperty = new ScenarioProperty();
				scenarioProperty.setPropertyID((int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));
				scenarioProperty.setPropertyContent(textAreaPropertyContentInModifyProperty.getText());
				
				ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
				scenarioPropertyDAO.updateProperty(scenarioProperty);
						
			}
			else if(propertyType.equals("development")) {
				
				ScenarioLogic scenarioLogic = new ScenarioLogic();
				scenarioLogic.setLogicID((int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));
				
				ScenarioLogicDAOInterface scenarioLogicDAO = new ScenarioLogicDAO();
				scenarioLogicDAO.updateLogic(scenarioLogic);
				
			}
			else if(propertyType.equals("result")) {
				
				ScenarioResult scenarioResult = new ScenarioResult();
				scenarioResult.setResultID((int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));
				
				ScenarioResultDAOInterface scenarioResultDAO = new ScenarioResultDAO();
				scenarioResultDAO.updateResult(scenarioResult);
				
			}
			
			getSelectedTable().setValueAt(textAreaPropertyContentInModifyProperty.getText(), selectedTableRow, 1);
			
		}
		
		else if(e.getSource() == buttonCancelInModifyProperty) {
			
			frameModifyProperty.dispose();
			
		}
				
		else if(e.getSource() == buttonConfirmInAddProperty) {
			
			if(propertyType.equals("focus") || propertyType.equals("keyfactor") ||
					propertyType.equals("drivingpower") || propertyType.equals("uncertainties")) {
				
				ScenarioProperty scenarioProperty = new ScenarioProperty();
				scenarioProperty.setPropertyType(propertyType);
				scenarioProperty.setPropertyContent(textareaContentInAddProperty.getText());
				
				ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
				scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty);
				
				dtm = (DefaultTableModel)getSelectedTable().getModel();
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddProperty.getText(), 0, 1);
					dtm.setValueAt(scenarioProperty.getPropertyID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, textareaContentInAddProperty.getText(), scenarioProperty.getPropertyID()});		
				}
				
			}
			else if(propertyType.equals("development")) {
				
				ScenarioLogic scenarioLogic = new ScenarioLogic();
				scenarioLogic.setLogicContent(textareaContentInAddProperty.getText());
				
				ScenarioLogicDAOInterface scenarioLogicDAO = new ScenarioLogicDAO();
				scenarioLogicDAO.addLogic(scenarioTask, scenarioLogic);
				
				dtm = (DefaultTableModel)getSelectedTable().getModel();
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddProperty.getText(), 0, 1);
					dtm.setValueAt(scenarioLogic.getLogicID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, textareaContentInAddProperty.getText(), scenarioLogic.getLogicID()});		
				}
				
			}
			else if(propertyType.equals("result")) {
				
				ScenarioResult scenarioResult = new ScenarioResult();
				scenarioResult.setResultContent(textareaContentInAddProperty.getText());
				
				ScenarioResultDAOInterface scenarioResultDAO = new ScenarioResultDAO();
				scenarioResultDAO.addResult(scenarioTask, scenarioResult);
				
				dtm = (DefaultTableModel)getSelectedTable().getModel();
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddProperty.getText(), 0, 1);
					dtm.setValueAt(scenarioResult.getResultID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, textareaContentInAddProperty.getText(), scenarioResult.getResultID()});		
				}
				
			}
			
			buttonConfirmInAddProperty.setText("继续添加");
			textareaContentInAddProperty.setText("");
//			textareaRemark1InPopMenuAddProperty.setText("");
//			textareaRemark2InPopMenuAddProperty.setText("");
	
		}
		
		else if(e.getSource() == buttonCancelInAddProperty) {
			
			frameAddProperty.dispose();
			
		}
	}	
}
