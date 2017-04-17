package cn.whu.forum.analysis.views.panels;

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
import javax.swing.JToolBar;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import cn.whu.forum.analysis.entities.ScenarioLogic;
import cn.whu.forum.analysis.entities.ScenarioProperty;
import cn.whu.forum.analysis.entities.ScenarioResult;
import cn.whu.forum.analysis.entities.ScenarioTask;
import cn.whu.forum.analysis.services.ScenarioLogicDAO;
import cn.whu.forum.analysis.services.ScenarioPropertyDAO;
import cn.whu.forum.analysis.services.ScenarioResultDAO;
import cn.whu.forum.analysis.services.interfaces.ScenarioLogicDAOInterface;
import cn.whu.forum.analysis.services.interfaces.ScenarioPropertyDAOInterface;
import cn.whu.forum.analysis.services.interfaces.ScenarioResultDAOInterface;

public class ScenarioEditPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScenarioTask scenarioTask;
	
	private JPanel panelTop;
	private JPanel panelBottom;
	
	JToolBar toolBar;		//任意的Component都可以添加到工具栏
	JButton buttonAddContentInToolBar;		
	JButton buttonDeleteContentInToolBar;
	JButton buttonModifyContentInToolBar;
	
	private JTabbedPane tabbedPane;
	
	private JPanel panelTableViewInTabbedPane;
	private JPanel panelTestViewInTabbedPane;
	
	//TableView
	private JPanel panelLayJInternalFrameInTableView;
	private JPanel panelLayButtonInTableView;

	private int flagStep;
	private JButton buttonBack;
	private JButton buttonNext;
	private JButton buttonSave;	
	
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
	private JMenuItem popMenuItemAddContent;
	private JMenuItem popMenuItemDeleteContent;
	private JMenuItem popMenuItemModifyContent;
	
	//表格双击修改属性内容面板
	private JFrame frameModifyContent;
	private JPanel panelModifyContent;
	private JPanel panelLayLabelInModifyContent;
	private JPanel panelLaytextAreaContentInModifyContent;
	private JPanel panelLayButtonInModifyContent;
	private JLabel lableContentContentInModifyContent;
	private JTextArea textAreaContentContentInModifyContent;
	private JButton buttonConfirmInModifyContent;
	private JButton buttonCancelInModifyContent;
	
	//表格右键菜单添加属性内容面板
	private JFrame frameAddContent;
	private JPanel panelAddContent;
	private JPanel panelLayLabelInAddContent;
	private JPanel panelLayTextareaInAddContent;
	private JPanel panelLayButtonInAddContent;
	private JLabel lableContentInAddContent;
//	private JLabel lableRemark1InAddContent;
//	private JLabel lableRemark2InPopmenuAddContent;
	private JTextArea textareaContentInAddContent;
//	private JTextArea textareaRemark1InAddContent;
//	private JTextArea textareaRemark2InAddContent;
	private JButton buttonConfirmInAddContent;
	private JButton buttonCancelInAddContent;
	
	public ScenarioEditPanel(ScenarioTask scenarioTask) {		
		
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
		
		/*------------顶部面板Start--------------*/
		panelTop = new JPanel();
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.X_AXIS));
		
		toolBar = new JToolBar();		//任意的Component都可以添加到工具栏	
		buttonAddContentInToolBar = new JButton("添加内容");		
		buttonDeleteContentInToolBar = new JButton("删除内容");	
		buttonModifyContentInToolBar = new JButton("修改内容");
		
		buttonAddContentInToolBar.addActionListener(this);
		buttonDeleteContentInToolBar.addActionListener(this);
		buttonModifyContentInToolBar.addActionListener(this);
		/*-------------顶部面板End--------------*/
		
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
				jifHeight = panelLayJInternalFrameInTableView.getSize().height / 6 - 5;
	
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
			
		spTableFocus = new JScrollPane(tableFocus);
		spTableKeyFactor = new JScrollPane(tableKeyFactor);
		spTableDrivingpower = new JScrollPane(tableDrivingpower);
		spTableUncertainties = new JScrollPane(tableUncertainties);
		spTableDevelopment = new JScrollPane(tableDevelopment);
		spTableResult = new JScrollPane(tableResult);
		
		popupMenu = new JPopupMenu();
		popMenuItemAddContent = new JMenuItem("添加");  
	    popMenuItemDeleteContent = new JMenuItem("删除");  
	    popMenuItemModifyContent = new JMenuItem("修改");
	    popupMenu.add(popMenuItemAddContent);  
        popupMenu.add(popMenuItemDeleteContent);  
        popupMenu.add(popMenuItemModifyContent); 
        
		popMenuItemAddContent.addActionListener(this);
		popMenuItemDeleteContent.addActionListener(this);
		popMenuItemModifyContent.addActionListener(this);
		
		flagStep = 1;
		buttonBack = new JButton("返回");
		buttonNext = new JButton("开始编辑");
		buttonSave = new JButton("保存");
		buttonBack.addActionListener(this);
		buttonNext.addActionListener(this);
		buttonSave.addActionListener(this);
        
	}
	
	public void layoutComponent() {
		
		/*------------顶部面板Start--------------*/
		panelTop.add(toolBar);
		
		toolBar.add(buttonAddContentInToolBar);   
		toolBar.addSeparator();
		toolBar.add(buttonDeleteContentInToolBar);
		toolBar.addSeparator();
		toolBar.add(buttonModifyContentInToolBar);
		/*------------顶部面板End--------------*/
		
		/*-------------底部面板Start--------------*/
		jifFocus.setContentPane(spTableFocus);
		jifKeyFactor.setContentPane(spTableKeyFactor);
		jifDrivingpower.setContentPane(spTableDrivingpower);
		jifUncertainties.setContentPane(spTableUncertainties);
		jifDevelopment.setContentPane(spTableDevelopment);
		jifResult.setContentPane(spTableResult);
		
	//	panelLayButtonInTableView.add(buttonBack);
		panelLayButtonInTableView.add(buttonNext);
//		panelLayButtonInTableView.add(buttonSave);
		
		panelTableViewInTabbedPane.add(panelLayJInternalFrameInTableView, BorderLayout.CENTER);
		panelTableViewInTabbedPane.add(panelLayButtonInTableView, BorderLayout.SOUTH);  
		
		tabbedPane.add(panelTableViewInTabbedPane, "表格");
		tabbedPane.add(panelTestViewInTabbedPane,"图形");
		
		panelBottom.add(tabbedPane);
		/*-------------底部面板End-----------------*/
		
		add(panelTop, BorderLayout.NORTH);
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
		frameAddContent = new JFrame("添加属性");
		frameAddContent.setSize(500, 400);
		frameAddContent.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameAddContent.setLocationRelativeTo( null ); 
		frameAddContent.getContentPane().setLayout(new BorderLayout());
		
		panelAddContent = new JPanel();
		panelLayLabelInAddContent = new JPanel();
		panelLayTextareaInAddContent = new JPanel();
		panelLayButtonInAddContent = new JPanel();
		panelAddContent.setLayout(new BoxLayout(panelAddContent, BoxLayout.Y_AXIS));
		panelLayTextareaInAddContent.setLayout(new BoxLayout(panelLayTextareaInAddContent, BoxLayout.X_AXIS));
		panelLayButtonInAddContent.setLayout(new BoxLayout(panelLayButtonInAddContent, BoxLayout.X_AXIS));
		
		lableContentInAddContent = new JLabel("内容");
//		lableRemark1InAddContent = new JLabel("备注1");
//		lableRemark2InPopmenuAddContent = new JLabel("备注2");
		
		textareaContentInAddContent = new JTextArea(5,5);
		textareaContentInAddContent.setLineWrap(true);		//支持自动换行
		textareaContentInAddContent.setBorder(BorderFactory.createTitledBorder(""));
//		textareaRemark1InAddContent = new JTextArea();
//		textareaRemark2InAddContent = new JTextArea();		
//		textareaRemark1InAddContent.setLineWrap(true);		//支持自动换行
//		textareaRemark2InAddContent.setLineWrap(true);		//支持自动换行
		
//		textareaContentInAddContent.setMinimumSize(new Dimension(200,200));
//		textareaContentInAddContent.setMaximumSize(new Dimension(900,900));
//		textareaRemark1InAddContent.setMinimumSize(new Dimension(100,250));
//		textareaRemark1InAddContent.setMaximumSize(new Dimension(200,200));
//		textareaRemark2InAddContent.setMinimumSize(new Dimension(100,250));
//		textareaRemark2InAddContent.setMaximumSize(new Dimension(200,200));
	

		buttonConfirmInAddContent = new JButton("确定");
		buttonCancelInAddContent = new JButton("取消");		
		
		buttonConfirmInAddContent.addActionListener(this);
		buttonCancelInAddContent.addActionListener(this);
		/*-------------初始化End--------------*/
		
		/*-------------布局Start--------------*/
		panelLayLabelInAddContent.add(lableContentInAddContent);
		panelLayTextareaInAddContent.add(Box.createHorizontalStrut(20));
		panelLayTextareaInAddContent.add(textareaContentInAddContent);
		panelLayTextareaInAddContent.add(Box.createHorizontalStrut(20));
//		panelLayTextareaInAddContent.add(lableRemark1InAddContent);	
//		panelLayTextareaInAddContent.add(textareaRemark1InAddContent);
//		panelLayTextareaInAddContent.add(lableRemark2InPopmenuAddContent);	
//		panelLayTextareaInAddContent.add(textareaRemark2InAddContent);	
		panelLayButtonInAddContent.add(buttonConfirmInAddContent);
		panelLayButtonInAddContent.add(Box.createHorizontalStrut(10));
		panelLayButtonInAddContent.add(buttonCancelInAddContent);
		
		panelAddContent.add(Box.createVerticalStrut(20));
		panelAddContent.add(panelLayLabelInAddContent);
		panelAddContent.add(Box.createVerticalStrut(5));
		panelAddContent.add(panelLayTextareaInAddContent);
		panelAddContent.add(Box.createVerticalStrut(10));
		panelAddContent.add(panelLayButtonInAddContent);
		panelAddContent.add(Box.createVerticalStrut(20));
		
		frameAddContent.setContentPane(panelAddContent);			
		frameAddContent.setVisible(true);
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
		frameModifyContent = new JFrame("修改属性");
		frameModifyContent.setSize(500, 400);
		frameModifyContent.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameModifyContent.setLocationRelativeTo( null ); 
		
		panelModifyContent = new JPanel();
		panelModifyContent = new JPanel();
		panelLayLabelInModifyContent = new JPanel();
		panelLaytextAreaContentInModifyContent = new JPanel();
		panelLayButtonInModifyContent = new JPanel();
		panelModifyContent.setLayout(new BoxLayout(panelModifyContent, BoxLayout.Y_AXIS));
		panelLaytextAreaContentInModifyContent.setLayout(new BoxLayout(panelLaytextAreaContentInModifyContent, BoxLayout.X_AXIS));
		panelLayButtonInModifyContent.setLayout(new BoxLayout(panelLayButtonInModifyContent, BoxLayout.X_AXIS));
		
		lableContentContentInModifyContent=new JLabel("内容");
		
		textAreaContentContentInModifyContent = new JTextArea(5,5);
		textAreaContentContentInModifyContent.setText((String) getSelectedTable().getValueAt(selectedTableRow, 1));
		textAreaContentContentInModifyContent.setLineWrap(true);		//支持自动换行
		textAreaContentContentInModifyContent.setBorder(BorderFactory.createTitledBorder(""));
//		textAreaContentContentInModifyContent.setMinimumSize(new Dimension(100,80));
//		textAreaContentContentInModifyContent.setMaximumSize(new Dimension(150,130));
		
		buttonConfirmInModifyContent = new JButton("确定");
		buttonCancelInModifyContent = new JButton("取消");		
		
		buttonConfirmInModifyContent.addActionListener(this);
		buttonCancelInModifyContent.addActionListener(this);
		/*-------------初始化End--------------*/
		
		/*-------------布局Start--------------*/
		panelLayLabelInModifyContent.add(lableContentContentInModifyContent);
		panelLaytextAreaContentInModifyContent.add(Box.createHorizontalStrut(20));
		panelLaytextAreaContentInModifyContent.add(textAreaContentContentInModifyContent);
		panelLaytextAreaContentInModifyContent.add(Box.createHorizontalStrut(20));
		
		panelLayButtonInModifyContent.add(buttonConfirmInModifyContent);
		panelLayButtonInModifyContent.add(Box.createHorizontalStrut(10));
		panelLayButtonInModifyContent.add(buttonCancelInModifyContent);
		
		panelModifyContent.add(Box.createVerticalStrut(20));
		panelModifyContent.add(panelLayLabelInModifyContent);
		panelModifyContent.add(Box.createVerticalStrut(5));
		panelModifyContent.add(panelLaytextAreaContentInModifyContent);
		panelModifyContent.add(Box.createVerticalStrut(10));
		panelModifyContent.add(panelLayButtonInModifyContent);
		panelModifyContent.add(Box.createVerticalStrut(20));
		
		frameModifyContent.setContentPane(panelModifyContent);			
		frameModifyContent.setVisible(true);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == buttonAddContentInToolBar || e.getSource() == popMenuItemAddContent) {
			
			addContent();
			
		}
		
		else if(e.getSource() == buttonDeleteContentInToolBar || e.getSource() == popMenuItemDeleteContent) {
			
			deleteContent();
			
		}
		
		else if(e.getSource() == buttonModifyContentInToolBar || e.getSource() == popMenuItemModifyContent) {
			
			if(getSelectedTable().getValueAt(0, 0) != "") 
				modifyContent();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		else if(e.getSource() == buttonBack) {
			
			if(flagStep == 2) {
				
				panelLayButtonInTableView.remove(buttonBack);
				buttonNext.setText("开始编辑");
				panelLayButtonInTableView.revalidate();
				panelLayButtonInTableView.repaint(); 
				
				try {
					jifFocus.setMaximum(false);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				flagStep--;
				
			}
			
			else if(flagStep == 3) {
				
				buttonBack.setText("返回");
				buttonNext.setText("编辑关键因素");
				try {
					jifKeyFactor.setMaximum(false);
					jifFocus.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				flagStep--;
				
			}
			
			else if(flagStep == 4) {
				
				buttonBack.setText("编辑决策焦点");
				buttonNext.setText("编辑驱动力量");
				try {
					jifDrivingpower.setMaximum(false);
					jifKeyFactor.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				flagStep--;
				
			}
			
			else if(flagStep == 5) {
				
				buttonBack.setText("编辑关键因素");
				buttonNext.setText("编辑不确定因素");
				try {
					jifUncertainties.setMaximum(false);
					jifDrivingpower.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
				flagStep--;
				
			}
			
			else if(flagStep == 6) {
				
				buttonBack.setText("编辑驱动力量");
				buttonNext.setText("编辑发展逻辑");
				try {
					jifDevelopment.setMaximum(false);
					jifUncertainties.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				flagStep--;		
				
			}
			
			else if(flagStep == 7) {
				
				buttonBack.setText("编辑不确定因素");
				buttonNext.setText("编辑分析结果");
				try {
					jifResult.setMaximum(false);
					jifDevelopment.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				flagStep--;		
				
			}
			
			else if(flagStep == 8) {
				
				panelLayButtonInTableView.remove(buttonSave);
				panelLayButtonInTableView.add(buttonNext);
				buttonBack.setText("编辑发展逻辑");
				buttonNext.setText("结束编辑");
				panelLayButtonInTableView.revalidate();
				panelLayButtonInTableView.repaint(); 
				
				try {
					jifResult.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				flagStep--;		
				
			}
			
		}
		
		else if(e.getSource() == buttonNext) {
			
			if(flagStep == 1) {
				
				panelLayButtonInTableView.remove(buttonNext);
				panelLayButtonInTableView.add(buttonBack);
				buttonNext.setText("编辑关键因素");
				panelLayButtonInTableView.add(buttonNext);
				panelLayButtonInTableView.revalidate();
				panelLayButtonInTableView.repaint(); 
				
				try {
					jifFocus.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				flagStep++;
				
			}
			
			else if(flagStep == 2) {
				
				buttonBack.setText("编辑决策焦点");
				buttonNext.setText("编辑驱动力量");
				try {
					jifFocus.setMaximum(false);
					jifKeyFactor.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				flagStep++;
				
			}
			
			else if(flagStep == 3) {
				
				buttonBack.setText("编辑关键因素");
				buttonNext.setText("编辑不确定因素");
				try {
					jifKeyFactor.setMaximum(false);
					jifDrivingpower.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				flagStep++;
				
			}
			
			else if(flagStep == 4) {
				
				buttonBack.setText("编辑驱动力量");
				buttonNext.setText("编辑发展逻辑");
				try {
					jifDrivingpower.setMaximum(false);
					jifUncertainties.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				flagStep++;
				
			}
			
			else if(flagStep == 5) {
				
				buttonBack.setText("编辑不确定因素");
				buttonNext.setText("编辑分析结果");
				try {
					jifUncertainties.setMaximum(false);
					jifDevelopment.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
				flagStep++;
				
			}
			
			else if(flagStep == 6) {
				
				buttonBack.setText("编辑发展逻辑");
				buttonNext.setText("结束编辑");
				try {
					jifDevelopment.setMaximum(false);
					jifResult.setMaximum(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
							
				flagStep++;		
				
			}
			
			else if(flagStep == 7) {
				
				buttonBack.setText("编辑分析结果");	
				panelLayButtonInTableView.remove(buttonNext);
				panelLayButtonInTableView.add(buttonSave);
				panelLayButtonInTableView.revalidate();
				panelLayButtonInTableView.repaint(); 
				
				try {
					jifResult.setMaximum(false);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				flagStep++;		
				
			}
				
		}
		
		else if(e.getSource() == buttonSave) {
			
			//数据库操作：获取当前所有表格的内容并存入 情景要素表、情景发展逻辑表、情景分析结果表【目前用单次保存】
			JOptionPane.showMessageDialog(null, "保存成功", "",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		else if(e.getSource() == buttonConfirmInModifyContent) {
			
			if(propertyType.equals("focus") || propertyType.equals("keyfactor") ||
					propertyType.equals("drivingpower") || propertyType.equals("uncertainties")) {
				
				ScenarioProperty scenarioProperty = new ScenarioProperty();
				scenarioProperty.setPropertyID((int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));
				scenarioProperty.setPropertyContent(textAreaContentContentInModifyContent.getText());
					
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
			
			getSelectedTable().setValueAt(textAreaContentContentInModifyContent.getText(), selectedTableRow, 1);
			
		}
		
		else if(e.getSource() == buttonCancelInModifyContent) {
			
			frameModifyContent.dispose();
			
		}
				
		else if(e.getSource() == buttonConfirmInAddContent) {
			
			if(propertyType.equals("focus") || propertyType.equals("keyfactor") ||
					propertyType.equals("drivingpower") || propertyType.equals("uncertainties")) {
				
				ScenarioProperty scenarioProperty = new ScenarioProperty();
				scenarioProperty.setPropertyType(propertyType);
				scenarioProperty.setPropertyContent(textareaContentInAddContent.getText());
				
				ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
				scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty);
				
				dtm = (DefaultTableModel)getSelectedTable().getModel();
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddContent.getText(), 0, 1);
					dtm.setValueAt(scenarioProperty.getPropertyID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, textareaContentInAddContent.getText(), scenarioProperty.getPropertyID()});		
				}
				
			}
			else if(propertyType.equals("development")) {
				
				ScenarioLogic scenarioLogic = new ScenarioLogic();
				scenarioLogic.setLogicContent(textareaContentInAddContent.getText());
				
				ScenarioLogicDAOInterface scenarioLogicDAO = new ScenarioLogicDAO();
				scenarioLogicDAO.addLogic(scenarioTask, scenarioLogic);
				
				dtm = (DefaultTableModel)getSelectedTable().getModel();
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddContent.getText(), 0, 1);
					dtm.setValueAt(scenarioLogic.getLogicID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, textareaContentInAddContent.getText(), scenarioLogic.getLogicID()});		
				}
				
			}
			else if(propertyType.equals("result")) {
				
				ScenarioResult scenarioResult = new ScenarioResult();
				scenarioResult.setResultContent(textareaContentInAddContent.getText());
				
				ScenarioResultDAOInterface scenarioResultDAO = new ScenarioResultDAO();
				scenarioResultDAO.addResult(scenarioTask, scenarioResult);
				
				dtm = (DefaultTableModel)getSelectedTable().getModel();
				if(dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddContent.getText(), 0, 1);
					dtm.setValueAt(scenarioResult.getResultID(), 0, 2);
				} else{	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, textareaContentInAddContent.getText(), scenarioResult.getResultID()});		
				}
				
			}
			
			buttonConfirmInAddContent.setText("继续添加");
			textareaContentInAddContent.setText("");
//			textareaRemark1InPopMenuAddContent.setText("");
//			textareaRemark2InPopMenuAddContent.setText("");
	
		}
		
		else if(e.getSource() == buttonCancelInAddContent) {
			
			frameAddContent.dispose();
			
		}
	}	
}
