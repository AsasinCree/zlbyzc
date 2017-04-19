package zlbyzc.sub3.analysis.panels;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
//import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xml.internal.dtm.DTM;

import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;
import zlbyzc.sub3.analysis.entities.SwotTask;
import zlbyzc.sub3.analysis.services.SwotActorDAO;
import zlbyzc.sub3.analysis.services.SwotPropertyDAO;
import zlbyzc.sub3.analysis.services.SwotTaskDAO;
import zlbyzc.sub3.analysis.services.interfaces.SwotActorDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotPropertyDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotTaskDAOInterface;


public class SwotEditPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SwotActorDAOInterface swotActorDAO;
	private SwotTask swotTask;
	
	private JPanel panelTop;
	private JPanel panelBottom;
	private JSplitPane splitpane;
	
	JToolBar toolBar;		//任意的Component都可以添加到工具栏
	JButton buttonAddPropertyInToolBar;		
	JButton buttonDeletePropertyInToolBar;
	JButton buttonModifyPropertyInToolBar;
	
	//底部左侧面板
	private JPanel panelList;		
	private JPanel panelListLayButton;	
	private JPanel panelListLayLabel;	
	private JPanel panelListLayList;	
	private JButton buttonAddActor;		//添加参与方按钮
	private JButton buttonDeleteActor;		//删除参与方按钮
	private JButton buttonExample;		//【示例】
	private JButton buttonClearExample;		//【示例清空】
	private JList<SwotActor> listSwotActor;		//【示例】
	private DefaultListModel<SwotActor> listModelSwotActor;		//存储参与方名称
	private JLabel labelInLeft;		//"参与方"显示标签
	private JScrollPane scrollpaneList;		//列表容器
	private int countSelectedActor;		//选择显示的参与者的数量
	private JList<SwotCheckboxListItem> listActorName;		//存储参与方名称
	private DefaultListModel<SwotCheckboxListItem> listModelActorName;		//存储参与方名称
	
	//底部左侧面板添加参与者面板
	private JFrame frameAddActor;
	private JPanel panelAddActor;		
	private JPanel panelLayLabelInAddActor;
	private JPanel panelLayTextfieldInAddActor;
	private JPanel panelLayButtonInAddActor;
	private JLabel lableActorNameInAddActor;
//	private JLabel lableRemark1InAddActor;
//	private JLabel lableRemark2InAddActor;
	private JTextField textfieldActorNameInAddActor;
//	private JTextArea textfieldRemark1InAddActor;
//	private JTextArea textfieldRemark2InAddActor;
	private JButton buttonConfirmInAddActor;		//添加参与方按钮
	private JButton buttonCancelInAddActor;		//删除参与方按钮

	//底部右侧面板
	private JTabbedPane tabbedPane;
	private JPanel panelTableViewInTabbedPane;
	private JPanel panelGraphViewInTabbedPane;
	private JDesktopPane tableDesktop;
	private JDesktopPane graphDesktop;
	private int actorPanelDimension;		//由actor个数决定 n x n 的排版进而算出每个actor面板大小
	private int actorPanelWidth;
	private int actorPanelHeight;
	private int selectedIndexOfListActorPanel;		//JInternalFrame监听。获取当前被选中的JInternalFrame的index，以便于toolbar定位
	private DefaultListModel<JInternalFrame> listModelTableJInternalFrame;
	private DefaultListModel<SwotActorPanel> listModelActorPanel;
	private DefaultListModel<JInternalFrame> listModelGraphJInternalFrame;
	private DefaultListModel<SwotActorGraphPanel> listModelActorGraphPanel;
	
	public SwotEditPanel(SwotTask swotTask) {
		
		swotActorDAO = new SwotActorDAO();
		this.swotTask = swotTask;			
		
		initializeComponent();
		
		layoutComponent();
		
	}
	
	public void initializeComponent() {
		
		setLayout(new BorderLayout());	
		
		/*------------顶部面板Start--------------*/
		panelTop = new JPanel();
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.X_AXIS));
		
		toolBar = new JToolBar();		//任意的Component都可以添加到工具栏	
		buttonAddPropertyInToolBar = new JButton("添加属性");		
		buttonDeletePropertyInToolBar = new JButton("删除属性");	
		buttonModifyPropertyInToolBar = new JButton("修改属性");
		
		buttonAddPropertyInToolBar.addActionListener(this);
		buttonDeletePropertyInToolBar.addActionListener(this);
		buttonModifyPropertyInToolBar.addActionListener(this);
		/*-------------顶部面板End--------------*/
		
		/*-------------底部面板Start--------------*/
		panelBottom = new JPanel();
		panelBottom.setLayout(new BorderLayout());
		
		splitpane = new JSplitPane();
		splitpane.setOneTouchExpandable(true);  		//让分隔线显示出箭头
		splitpane.setContinuousLayout(true); 		//当用户操作分隔线箭头时，系统重绘图形
		splitpane.setOrientation(JSplitPane.HORIZONTAL_SPLIT); 		//设置方向或者分隔窗格的方式（HORIZONTAL_SPLIT表示左右分隔，VERTICAL_SPLIT）
		splitpane.setDividerSize(3); 		//设置分隔条的大小
		splitpane.setDividerLocation(174); 	//设置分隔条的位置
	
		panelList = new JPanel();
		panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));
		
		panelListLayButton = new JPanel(); 
		panelListLayLabel = new JPanel(); 
		panelListLayList = new JPanel();
		
		panelListLayButton.setLayout(new BoxLayout(panelListLayButton, BoxLayout.X_AXIS));
		panelListLayLabel.setLayout(new BoxLayout(panelListLayLabel, BoxLayout.X_AXIS));
		panelListLayList.setLayout(new BoxLayout(panelListLayList, BoxLayout.X_AXIS));
		
		buttonAddActor = new JButton("+");			//左侧面板添加参与方按钮
		buttonDeleteActor = new JButton("-");		//左侧面板删除参与方按钮
		buttonExample = new JButton("示例");
		buttonClearExample = new JButton("示例清空");
		
		buttonDeleteActor.addActionListener(this);
		buttonAddActor.addActionListener(this);
		buttonExample.addActionListener(this);
		buttonClearExample.addActionListener(this);
		
		labelInLeft = new JLabel("参与方");
		
		initializeListActorName();
		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener(){
			   public void stateChanged(ChangeEvent e){
			    JTabbedPane tabbedPane = (JTabbedPane)e.getSource();
			    int selectedIndex = tabbedPane.getSelectedIndex();
			    
			    if(selectedIndex == 1){
			    	refreshActorGraphPanel();}
			   }
		});
				
		panelTableViewInTabbedPane = new JPanel();
		panelTableViewInTabbedPane.setLayout(new BorderLayout(0, 0));
		panelGraphViewInTabbedPane = new JPanel();
		panelGraphViewInTabbedPane.setLayout(new BorderLayout(0, 0));
		
		initializeListActorPanel();
		/*-------------底部面板End-----------------*/
		
	}
	
	public void layoutComponent() {
					
		/*------------顶部面板Start--------------*/
//		panelTop.add(toolBar);
//		
//		toolBar.add(buttonAddPropertyInToolBar);   
//		toolBar.addSeparator();
//		toolBar.add(buttonDeletePropertyInToolBar);
//		toolBar.addSeparator();
//		toolBar.add(buttonModifyPropertyInToolBar);
		/*------------顶部面板End--------------*/
		
		/*-------------底部面板Start--------------*/
		panelBottom.add(splitpane);
		
		splitpane.setLeftComponent(panelList);		
		splitpane.setRightComponent(tabbedPane);
		
		panelListLayButton.add(Box.createRigidArea(new Dimension(10, 40)));  
		panelListLayButton.add(buttonAddActor); 
		panelListLayButton.add(Box.createRigidArea(new Dimension(10, 40)));  
		panelListLayButton.add(buttonDeleteActor);
		panelListLayButton.add(Box.createRigidArea(new Dimension(10, 40)));  
		
		panelListLayLabel.add(labelInLeft);
		panelListLayLabel.add(buttonExample);
		panelListLayLabel.add(buttonClearExample);
		
		panelListLayList.add(Box.createHorizontalStrut(3));
		panelListLayList.add(scrollpaneList);
		
		panelList.add(Box.createVerticalStrut(10));
		panelList.add(panelListLayButton);
		panelList.add(Box.createVerticalStrut(10));
		panelList.add(panelListLayLabel);
		panelList.add(Box.createVerticalStrut(10));
		panelList.add(panelListLayList);
		
		tabbedPane.add(panelTableViewInTabbedPane,"表格");
		tabbedPane.add(panelGraphViewInTabbedPane,"图形");

		/*-------------底部面板End-----------------*/
		
		add(panelTop, BorderLayout.NORTH);
		add(panelBottom, BorderLayout.CENTER);
	
	}
	
	public void initializeListActorName() {
				
		listModelActorName = new DefaultListModel<SwotCheckboxListItem>();
		
		listActorName = new JList<SwotCheckboxListItem>(listModelActorName);		//左侧面板列表		
		listActorName.setCellRenderer(new SwotCheckboxListRenderer());		//添加渲染使得JList内容装入JCheckBox
		listActorName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		//【待验证功能】	
		listActorName.setBorder(BorderFactory.createTitledBorder(""));		
		
		//为   选择显示的参与者的数量   赋初值0
		countSelectedActor = 0;
		
		listActorName.addMouseListener(new MouseAdapter() {		//监听单击时checkbox框toggle
		
			public void mouseClicked(MouseEvent e) {
				
				//所单击的列表项目isSelected切换
				SwotCheckboxListItem selectedSwotActorListItem = listActorName.getSelectedValue();
				selectedSwotActorListItem.setSelected(!selectedSwotActorListItem.isSelected);
				
				//检测是否选中
				if(selectedSwotActorListItem.isSelected() == true) {
					countSelectedActor++;
				} else {
					countSelectedActor--;
				}
				
				refreshActorPanel();
				
				listActorName.repaint();
			}
		});
		
		scrollpaneList = new JScrollPane(listActorName);		//左侧面板列表容器
		
	}
	
	public void initializeListActorPanel() {
						
		listModelTableJInternalFrame = new DefaultListModel<JInternalFrame>();
	//	listJInternalFrame = new JList<JInternalFrame>(listModelTableJInternalFrame);
		
		listModelActorPanel = new DefaultListModel<SwotActorPanel>();
//		listActorPanel = new JList<SwotActorPanel>(listModelActorPanel);					
		
		listModelGraphJInternalFrame = new DefaultListModel<JInternalFrame>();
		listModelActorGraphPanel = new DefaultListModel<SwotActorGraphPanel>();
	}
	
	public void addActor() {
		
		/*-------------初始化Start--------------*/
		frameAddActor=new JFrame("添加参与方");			
		frameAddActor.setSize(400, 300);	//【绝对】
		frameAddActor.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);			//HIDE_ON_CLOSE表示在点击X按钮时不退出整个程序，只是将子对话框隐藏
		frameAddActor.setLocationRelativeTo( null );
		
		panelAddActor=new JPanel();
		panelLayLabelInAddActor = new JPanel();
		panelLayTextfieldInAddActor = new JPanel();
		panelLayButtonInAddActor = new JPanel();
		panelAddActor.setLayout(new BoxLayout(panelAddActor, BoxLayout.Y_AXIS));
		panelLayLabelInAddActor.setLayout(new BoxLayout(panelLayLabelInAddActor, BoxLayout.X_AXIS));
		panelLayTextfieldInAddActor.setLayout(new BoxLayout(panelLayTextfieldInAddActor, BoxLayout.X_AXIS));
		panelLayButtonInAddActor.setLayout(new BoxLayout(panelLayButtonInAddActor, BoxLayout.X_AXIS));
		
		lableActorNameInAddActor = new JLabel("参与方名称");
//		lableRemark1InAddActor = new JLabel("备注1");
//		lableRemark2InAddActor = new JLabel("备注2");
		
		textfieldActorNameInAddActor=new JTextField();
		textfieldActorNameInAddActor.setMinimumSize(new Dimension(150,25));
		textfieldActorNameInAddActor.setMaximumSize(new Dimension(150,30));
		
//		textfieldRemark1InAddActor=new JTextArea();		
//		textfieldRemark2InAddActor=new JTextArea();
//		textfieldRemark1InAddActor.setLineWrap(true);		//支持自动换行
//		textfieldRemark2InAddActor.setLineWrap(true);		//支持自动换行
//		textfieldRemark1InAddActor.setMinimumSize(new Dimension(100,25));
//		textfieldRemark1InAddActor.setMaximumSize(new Dimension(200,200));
//		textfieldRemark2InAddActor.setMinimumSize(new Dimension(100,25));
//		textfieldRemark2InAddActor.setMaximumSize(new Dimension(200,200));
		
		buttonConfirmInAddActor=new JButton("确认");
		buttonCancelInAddActor=new JButton("取消");		
		
		buttonConfirmInAddActor.addActionListener(this);
		buttonCancelInAddActor.addActionListener(this);
		/*-------------初始化End--------------*/
		
		/*-------------布局Start--------------*/
		panelLayLabelInAddActor.add(lableActorNameInAddActor);	 	
		panelLayTextfieldInAddActor.add(textfieldActorNameInAddActor);	
		panelLayButtonInAddActor.add(buttonConfirmInAddActor);
		panelLayButtonInAddActor.add(Box.createHorizontalStrut(20));
		panelLayButtonInAddActor.add(buttonCancelInAddActor);
		
		panelAddActor.add(Box.createVerticalGlue());
		panelAddActor.add(panelLayLabelInAddActor);
		panelAddActor.add(Box.createVerticalStrut(20));
		panelAddActor.add(panelLayTextfieldInAddActor);
		panelAddActor.add(Box.createVerticalStrut(5));
		panelAddActor.add(panelLayButtonInAddActor);
		panelAddActor.add(Box.createVerticalGlue());
		
		frameAddActor.setContentPane(panelAddActor);
		frameAddActor.setVisible(true);
		/*-------------布局End--------------*/
		
	}
	
	public void deleteActor() {
		
		swotActorDAO.deleteActorByID(listActorName.getSelectedValue().getActorID());
		
		int selectedIndex = listActorName.getSelectedIndex();
		listModelActorName.remove(selectedIndex);	
		listModelTableJInternalFrame.remove(selectedIndex);
		listModelActorPanel.remove(selectedIndex);
		listModelGraphJInternalFrame.remove(selectedIndex);
		listModelActorGraphPanel.remove(selectedIndex);
		
		countSelectedActor = 0;		//初始化筛选显示参与者面板的个数	
		
		refreshActorPanel();
		
	}
	
	//筛选或者添加删除参与者之后重新排版右侧参与者面板
	public void refreshActorPanel() {
		
		//根据筛选的数目计算出面板大小
		//循环生成面板
		
		int countLoop, i, j;
		
		//检测有没有实行筛选	
		if(countSelectedActor == 0)		//没有筛选
			countLoop = listModelActorName.getSize();
		else
			countLoop = countSelectedActor;
		
		//计算出面板大小
		actorPanelDimension = 1;
		while(actorPanelDimension*actorPanelDimension < countLoop) {
			actorPanelDimension++;
		}		
		actorPanelWidth = panelTableViewInTabbedPane.getSize().width/actorPanelDimension;
		actorPanelHeight = panelTableViewInTabbedPane.getSize().height/actorPanelDimension;
		
		//清空原有的参与者面板
		if(tableDesktop != null) {
			panelTableViewInTabbedPane.remove(tableDesktop);
		}
		
		//将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后  actorpanel 层次问题【未知缘由】
		for(i = 0; i < listModelTableJInternalFrame.getSize(); i++) {
			
			try {
				if(listModelTableJInternalFrame.getElementAt(i).isIcon())
					listModelTableJInternalFrame.getElementAt(i).setIcon(false);		//去除最小化
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {	
				if(listModelTableJInternalFrame.getElementAt(i).isSelected())
					listModelTableJInternalFrame.getElementAt(i).setSelected(false);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		tableDesktop = new JDesktopPane();
		tableDesktop.setLayout(null);
		
		//循环加入一个个面板(未选中的除外)
		for(i = 0, j = 0; i < listModelActorName.getSize(); i++) {
			if(listModelActorName.getElementAt(i).isSelected() == true || countSelectedActor == 0) { //后者条件为没有实行筛选				
				listModelTableJInternalFrame.getElementAt(i).setBounds((j%actorPanelDimension)*actorPanelWidth, 
						(j/actorPanelDimension)*actorPanelHeight, actorPanelWidth, actorPanelHeight);
				tableDesktop.add(listModelTableJInternalFrame.getElementAt(i));		

				j++;
			}
		}
		
		panelTableViewInTabbedPane.add(tableDesktop);
		panelTableViewInTabbedPane.revalidate();
		panelTableViewInTabbedPane.repaint();
	}
	
	public void refreshActorGraphPanel() {
		
		//根据筛选的数目计算出面板大小
		//循环生成面板
		
		int countLoop, i, j;
		
		//检测有没有实行筛选	
		if(countSelectedActor == 0)		//没有筛选
			countLoop = listModelActorName.getSize();
		else
			countLoop = countSelectedActor;
		
		//计算出面板大小
		actorPanelDimension = 1;
		while(actorPanelDimension*actorPanelDimension < countLoop) {
			actorPanelDimension++;
		}		
		actorPanelWidth = panelTableViewInTabbedPane.getSize().width/actorPanelDimension;
		actorPanelHeight = panelTableViewInTabbedPane.getSize().height/actorPanelDimension;
		
		//清空原有的参与者面板
		if(graphDesktop != null) {
			panelGraphViewInTabbedPane.remove(graphDesktop);
		}
		
		//将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后  actorpanel 层次问题【未知缘由】
		for(i = 0; i < listModelTableJInternalFrame.getSize(); i++) {
			
			try {
				if(listModelGraphJInternalFrame.getElementAt(i).isIcon())
					listModelGraphJInternalFrame.getElementAt(i).setIcon(false);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				if(listModelGraphJInternalFrame.getElementAt(i).isSelected())
					listModelGraphJInternalFrame.getElementAt(i).setSelected(false);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		graphDesktop = new JDesktopPane();
		graphDesktop.setLayout(null);
		
		//循环加入一个个面板(未选中的除外)
		for(i = 0, j = 0; i < listModelActorName.getSize(); i++) {
			if(listModelActorName.getElementAt(i).isSelected() == true || countSelectedActor == 0) { //后者条件为没有实行筛选				
				listModelGraphJInternalFrame.getElementAt(i).setBounds((j%actorPanelDimension)*actorPanelWidth, 
						(j/actorPanelDimension)*actorPanelHeight, actorPanelWidth, actorPanelHeight);
				graphDesktop.add(listModelGraphJInternalFrame.getElementAt(i));		
				listModelActorGraphPanel.getElementAt(i).refreshPanel();
				j++;
			}
		}
	
		panelGraphViewInTabbedPane.add(graphDesktop);
		panelGraphViewInTabbedPane.revalidate();
		panelGraphViewInTabbedPane.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		if(e.getSource() == buttonAddPropertyInToolBar)	{
			
			//获取当前选中的actorpanel
			//调用actorpanel中的添加属性方法
			
			listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).addProperty();
			
		}
		
		else if(e.getSource() == buttonDeletePropertyInToolBar)	{
			
			if(listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).getSelectedTable().getValueAt(0, 0) != "") {
				listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).deleteProperty();
			}else {
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除内容操作", "",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		else if(e.getSource() == buttonModifyPropertyInToolBar)	{
			
			if(listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).getSelectedTable().getValueAt(0, 0) != "") 
				listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).modifyProperty();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		else if(e.getSource() == buttonAddActor)	{		//添加参与者弹出面板
			
			addActor();
			
		}
		
		else if(e.getSource() == buttonDeleteActor)	{
			
			deleteActor();
			
		}
		
		else if(e.getSource() == buttonConfirmInAddActor)	{		//确认添加参与者								
			SwotActor swotActor = new SwotActor();
			swotActor.setActorName(textfieldActorNameInAddActor.getText());
	
			swotActorDAO.addActor(swotTask, swotActor);
			
			//添加一个参与者列表项目
			SwotCheckboxListItem newSwotCheckboxListItem = new SwotCheckboxListItem(swotActor.getActorName());
			newSwotCheckboxListItem.setActorID(swotActor.getActorID());
			listModelActorName.addElement(newSwotCheckboxListItem);
			
			//添加一个actorpanel
			SwotActorPanel newActorPanel = new SwotActorPanel(swotActor);
			listModelActorPanel.addElement(newActorPanel);
			SwotActorGraphPanel newActorGraphPanel = new SwotActorGraphPanel(swotActor);
			
			listModelActorGraphPanel.addElement(newActorGraphPanel);
			
			/*-------------添加一个装载actorpanel的JInternalFrame Start--------------*/
			final JInternalFrame tableInternalFrame = new JInternalFrame(swotActor.getActorName(), true, false, true, true);
			tableInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			tableInternalFrame.setLayout(new BorderLayout());		//该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果							
			tableInternalFrame.setContentPane(newActorPanel);
			tableInternalFrame.setVisible(true);
			listModelTableJInternalFrame.addElement(tableInternalFrame);
			
			final JInternalFrame graphInternalFrame = new JInternalFrame(swotActor.getActorName(), true, false, true, true);
			graphInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			graphInternalFrame.setLayout(new BorderLayout());		//该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果							
			graphInternalFrame.setContentPane(newActorGraphPanel);
			graphInternalFrame.setVisible(true);
			listModelGraphJInternalFrame.addElement(graphInternalFrame);
					
			tableInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {	//获取当前被选中的JInternalFrame的index，以便于toolbar定位（MouseListener监听不到）	
				@Override
				public void internalFrameActivated(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					for(int	i = 0; i < listModelTableJInternalFrame.getSize(); i++)    {  		
						if(tableInternalFrame.equals(listModelTableJInternalFrame.getElementAt(i))) 
							selectedIndexOfListActorPanel = i;				
					}  
				}
			});
			/*-------------添加一个装载actorpanel的JInternalFrame End--------------*/
			textfieldActorNameInAddActor.setText("");
			buttonConfirmInAddActor.setText("继续添加");
			
			refreshActorPanel();
		}		
		else if(e.getSource() == buttonCancelInAddActor)	{			
			frameAddActor.dispose();			
		}	
		else if(e.getSource() == buttonExample)	{		//【示例添加】
			listModelSwotActor = new DefaultListModel<SwotActor>();
			listSwotActor = new JList<SwotActor>(listModelSwotActor);		//左侧面板列表
			
			//【此处修改默认添加actor列表~~~start】
			SwotActor swotActor = new SwotActor();
			swotActor.setActorName("name1");
			listModelSwotActor.addElement(swotActor);
			swotActorDAO.addActor(swotTask, swotActor);
			
			SwotActor swotActor2 = new SwotActor();
			swotActor2.setActorName("name2");
			listModelSwotActor.addElement(swotActor2);
			swotActorDAO.addActor(swotTask, swotActor2);
			
			SwotActor swotActor3 = new SwotActor();
			swotActor3.setActorName("name3");
			listModelSwotActor.addElement(swotActor3);
			swotActorDAO.addActor(swotTask, swotActor3);
			
			SwotActor swotActor4 = new SwotActor();
			swotActor4.setActorName("name4");
			listModelSwotActor.addElement(swotActor4);
			swotActorDAO.addActor(swotTask, swotActor4);
			//【此处修改默认添加actor列表~~~end】
			
			for(int i = 0; i<listModelSwotActor.size(); i++){	
				//添加一个参与者列表项目
				SwotCheckboxListItem newSwotCheckboxListItem = new SwotCheckboxListItem(listModelSwotActor.getElementAt(i).getActorName());
				newSwotCheckboxListItem.setActorID(listModelSwotActor.getElementAt(i).getActorID());
				listModelActorName.addElement(newSwotCheckboxListItem);
				
				DefaultTableModel dtm;
				SwotPropertyDAOInterface swotPropertyDAO = new SwotPropertyDAO();
				
				//【此处if里面修改默认参与者属性~~~~start，有几个actor就有几个if判断】
				SwotActorPanel newActorPanel = new SwotActorPanel(listModelSwotActor.getElementAt(i));
				List<SwotActorProperty> swotPropertyList1 = new ArrayList<SwotActorProperty>();
				List<SwotActorProperty> swotPropertyList2 = new ArrayList<SwotActorProperty>();;
				List<SwotActorProperty> swotPropertyList3 = new ArrayList<SwotActorProperty>();;
				List<SwotActorProperty> swotPropertyList4 = new ArrayList<SwotActorProperty>();;
				if(i == 0){		//第一个actor属性
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("advantage").getModel();		//为advantage添加4个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("资源丰富", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "经济性优势", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "标准化优势", 2});	
					SwotActorProperty swotActorProperty0 = new SwotActorProperty();
					swotActorProperty0.setPropertyType("advantage");
					swotActorProperty0.setPropertyContent("不会被显示");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty0);
					SwotActorProperty swotActorProperty1 = new SwotActorProperty();
					swotActorProperty1.setPropertyType("advantage");
					swotActorProperty1.setPropertyContent("资源丰富");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty1);
					SwotActorProperty swotActorProperty2 = new SwotActorProperty();
					swotActorProperty2.setPropertyType("advantage");
					swotActorProperty2.setPropertyContent("经济性优势");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty2);
					SwotActorProperty swotActorProperty3 = new SwotActorProperty();
					swotActorProperty3.setPropertyType("advantage");
					swotActorProperty3.setPropertyContent("标准化优势");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty3);

					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("chance").getModel();		//为chance添加3个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("国家号召相应建设节约型社会", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "军队后勤也在积极发展节约型后勤", 1});
					SwotActorProperty swotActorProperty34 = new SwotActorProperty();
					swotActorProperty34.setPropertyType("chance");
					swotActorProperty34.setPropertyContent("不会被显示");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty34);
					SwotActorProperty swotActorProperty4 = new SwotActorProperty();
					swotActorProperty4.setPropertyType("chance");
					swotActorProperty4.setPropertyContent("国家号召相应建设节约型社会");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty4);
					SwotActorProperty swotActorProperty5 = new SwotActorProperty();
					swotActorProperty5.setPropertyType("chance");
					swotActorProperty5.setPropertyContent("军队后勤也在积极发展节约型后勤");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty5);

					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("disadvantage").getModel();	//为disadvantage添加3个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("基础设施薄弱", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "管理手段劣势", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "信息技术落后", 2});
					SwotActorProperty swotActorProperty56 = new SwotActorProperty();
					swotActorProperty56.setPropertyType("disadvantage");
					swotActorProperty56.setPropertyContent("不会被显示");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty56);
					SwotActorProperty swotActorProperty6 = new SwotActorProperty();
					swotActorProperty6.setPropertyType("disadvantage");
					swotActorProperty6.setPropertyContent("基础设施薄弱");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty6);
					SwotActorProperty swotActorProperty7 = new SwotActorProperty();
					swotActorProperty7.setPropertyType("disadvantage");
					swotActorProperty7.setPropertyContent("管理手段劣势");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty7);
					SwotActorProperty swotActorProperty8 = new SwotActorProperty();
					swotActorProperty8.setPropertyType("disadvantage");
					swotActorProperty8.setPropertyContent("信息技术落后");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty8);
					
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("threat").getModel();		//为threat添加5个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("未来信息化战争的挑战", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "人才的挑战", 1});
					SwotActorProperty swotActorProperty78 = new SwotActorProperty();
					swotActorProperty78.setPropertyType("threat");
					swotActorProperty78.setPropertyContent("不会被显示");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty78);
					SwotActorProperty swotActorProperty9 = new SwotActorProperty();
					swotActorProperty9.setPropertyType("threat");
					swotActorProperty9.setPropertyContent("未来信息化战争的挑战");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty9);
					SwotActorProperty swotActorProperty10 = new SwotActorProperty();
					swotActorProperty10.setPropertyType("threat");
					swotActorProperty10.setPropertyContent("人才的挑战");
					swotPropertyDAO.addProperty(swotActor, swotActorProperty10);
					
					swotPropertyList1.add(swotActorProperty1);
					swotPropertyList1.add(swotActorProperty2);
					swotPropertyList1.add(swotActorProperty3);
					swotPropertyList1.add(swotActorProperty4);
					swotPropertyList1.add(swotActorProperty5);
					swotPropertyList1.add(swotActorProperty6);
					swotPropertyList1.add(swotActorProperty7);
					swotPropertyList1.add(swotActorProperty8);
					swotPropertyList1.add(swotActorProperty9);
					swotPropertyList1.add(swotActorProperty10);
				} else if(i == 1){		//第二个actor属性
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("advantage").getModel();		//为advantage添加4个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("advantageContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent2", 2});	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent3", 3});	
					SwotActorProperty swotActorProperty0 = new SwotActorProperty();
					swotActorProperty0.setPropertyType("advantage");
					swotActorProperty0.setPropertyContent("advantageContent0");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty0);
					SwotActorProperty swotActorProperty1 = new SwotActorProperty();
					swotActorProperty1.setPropertyType("advantage");
					swotActorProperty1.setPropertyContent("advantageContent1");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty1);
					SwotActorProperty swotActorProperty2 = new SwotActorProperty();
					swotActorProperty2.setPropertyType("advantage");
					swotActorProperty2.setPropertyContent("advantageContent2");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty2);
					SwotActorProperty swotActorProperty3 = new SwotActorProperty();
					swotActorProperty3.setPropertyType("advantage");
					swotActorProperty3.setPropertyContent("advantageContent3");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty3);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("chance").getModel();		//为chance添加3个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("chanceContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "chanceContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "chanceContent2", 2});	
					SwotActorProperty swotActorProperty34 = new SwotActorProperty();
					swotActorProperty34.setPropertyType("chance");
					swotActorProperty34.setPropertyContent("chanceContent0");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty34);
					SwotActorProperty swotActorProperty4 = new SwotActorProperty();
					swotActorProperty4.setPropertyType("chance");
					swotActorProperty4.setPropertyContent("chanceContent1");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty4);
					SwotActorProperty swotActorProperty5 = new SwotActorProperty();
					swotActorProperty5.setPropertyType("chance");
					swotActorProperty5.setPropertyContent("chanceContent2");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty5);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("disadvantage").getModel();	//为disadvantage添加3个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("disadvantageContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "disadvantageContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "disadvantageContent2", 2});
					SwotActorProperty swotActorProperty56 = new SwotActorProperty();
					swotActorProperty56.setPropertyType("disadvantage");
					swotActorProperty56.setPropertyContent("disadvantageContent56");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty56);
					SwotActorProperty swotActorProperty6 = new SwotActorProperty();
					swotActorProperty6.setPropertyType("disadvantage");
					swotActorProperty6.setPropertyContent("disadvantageContent1");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty6);
					SwotActorProperty swotActorProperty7 = new SwotActorProperty();
					swotActorProperty7.setPropertyType("disadvantage");
					swotActorProperty7.setPropertyContent("disadvantageContent2");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty7);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("threat").getModel();		//为threat添加5个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("threatContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent2", 2});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent3", 3});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent4", 4});
					SwotActorProperty swotActorProperty78 = new SwotActorProperty();
					swotActorProperty78.setPropertyType("threat");
					swotActorProperty78.setPropertyContent("threatContent0");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty78);
					SwotActorProperty swotActorProperty8 = new SwotActorProperty();
					swotActorProperty8.setPropertyType("threat");
					swotActorProperty8.setPropertyContent("threatContent1");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty8);
					SwotActorProperty swotActorProperty9 = new SwotActorProperty();
					swotActorProperty9.setPropertyType("threat");
					swotActorProperty9.setPropertyContent("threatContent2");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty9);
					SwotActorProperty swotActorProperty10 = new SwotActorProperty();
					swotActorProperty10.setPropertyType("threat");
					swotActorProperty10.setPropertyContent("threatContent3");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty10);
					SwotActorProperty swotActorProperty11 = new SwotActorProperty();
					swotActorProperty11.setPropertyType("threat");
					swotActorProperty11.setPropertyContent("threatContent4");
					swotPropertyDAO.addProperty(swotActor2, swotActorProperty11);
					
					swotPropertyList2.add(swotActorProperty1);
					swotPropertyList2.add(swotActorProperty2);
					swotPropertyList2.add(swotActorProperty3);
					swotPropertyList2.add(swotActorProperty4);
					swotPropertyList2.add(swotActorProperty5);
					swotPropertyList2.add(swotActorProperty6);
					swotPropertyList2.add(swotActorProperty7);
					swotPropertyList2.add(swotActorProperty8);
					swotPropertyList2.add(swotActorProperty9);
					swotPropertyList2.add(swotActorProperty10);
					swotPropertyList2.add(swotActorProperty11);
				}else if(i == 2){
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("advantage").getModel();		//为advantage添加4个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("advantageContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent2", 2});	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent3", 3});	
					SwotActorProperty swotActorProperty0 = new SwotActorProperty();
					swotActorProperty0.setPropertyType("advantage");
					swotActorProperty0.setPropertyContent("advantageContent0");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty0);
					SwotActorProperty swotActorProperty1 = new SwotActorProperty();
					swotActorProperty1.setPropertyType("advantage");
					swotActorProperty1.setPropertyContent("advantageContent1");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty1);
					SwotActorProperty swotActorProperty2 = new SwotActorProperty();
					swotActorProperty2.setPropertyType("advantage");
					swotActorProperty2.setPropertyContent("advantageContent2");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty2);
					SwotActorProperty swotActorProperty3 = new SwotActorProperty();
					swotActorProperty3.setPropertyType("advantage");
					swotActorProperty3.setPropertyContent("advantageContent3");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty3);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("chance").getModel();		//为chance添加3个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("chanceContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "chanceContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "chanceContent2", 2});	
					SwotActorProperty swotActorProperty34 = new SwotActorProperty();
					swotActorProperty34.setPropertyType("chance");
					swotActorProperty34.setPropertyContent("chanceContent0");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty34);
					SwotActorProperty swotActorProperty4 = new SwotActorProperty();
					swotActorProperty4.setPropertyType("chance");
					swotActorProperty4.setPropertyContent("chanceContent1");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty4);
					SwotActorProperty swotActorProperty5 = new SwotActorProperty();
					swotActorProperty5.setPropertyType("chance");
					swotActorProperty5.setPropertyContent("chanceContent2");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty5);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("disadvantage").getModel();	//为disadvantage添加3个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("disadvantageContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "disadvantageContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "disadvantageContent2", 2});
					SwotActorProperty swotActorProperty56 = new SwotActorProperty();
					swotActorProperty56.setPropertyType("disadvantage");
					swotActorProperty56.setPropertyContent("disadvantageContent0");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty56);
					SwotActorProperty swotActorProperty6 = new SwotActorProperty();
					swotActorProperty6.setPropertyType("disadvantage");
					swotActorProperty6.setPropertyContent("disadvantageContent1");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty6);
					SwotActorProperty swotActorProperty7 = new SwotActorProperty();
					swotActorProperty7.setPropertyType("disadvantage");
					swotActorProperty7.setPropertyContent("disadvantageContent2");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty7);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("threat").getModel();		//为threat添加5个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("threatContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent2", 2});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent3", 3});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent4", 4});
					SwotActorProperty swotActorProperty78 = new SwotActorProperty();
					swotActorProperty78.setPropertyType("threat");
					swotActorProperty78.setPropertyContent("threatContent0");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty78);
					SwotActorProperty swotActorProperty8 = new SwotActorProperty();
					swotActorProperty8.setPropertyType("threat");
					swotActorProperty8.setPropertyContent("threatContent1");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty8);
					SwotActorProperty swotActorProperty9 = new SwotActorProperty();
					swotActorProperty9.setPropertyType("threat");
					swotActorProperty9.setPropertyContent("threatContent2");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty9);
					SwotActorProperty swotActorProperty10 = new SwotActorProperty();
					swotActorProperty10.setPropertyType("threat");
					swotActorProperty10.setPropertyContent("threatContent3");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty10);
					SwotActorProperty swotActorProperty11 = new SwotActorProperty();
					swotActorProperty11.setPropertyType("threat");
					swotActorProperty11.setPropertyContent("threatContent4");
					swotPropertyDAO.addProperty(swotActor3, swotActorProperty11);
					
					swotPropertyList3.add(swotActorProperty1);
					swotPropertyList3.add(swotActorProperty2);
					swotPropertyList3.add(swotActorProperty3);
					swotPropertyList3.add(swotActorProperty4);
					swotPropertyList3.add(swotActorProperty5);
					swotPropertyList3.add(swotActorProperty6);
					swotPropertyList3.add(swotActorProperty7);
					swotPropertyList3.add(swotActorProperty8);
					swotPropertyList3.add(swotActorProperty9);
					swotPropertyList3.add(swotActorProperty10);
					swotPropertyList3.add(swotActorProperty11);
				}else if(i == 3){
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("advantage").getModel();		//为advantage添加4个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("advantageContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent2", 2});	
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "advantageContent3", 3});	
					SwotActorProperty swotActorProperty0 = new SwotActorProperty();
					swotActorProperty0.setPropertyType("advantage");
					swotActorProperty0.setPropertyContent("advantageContent0");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty0);
					SwotActorProperty swotActorProperty1 = new SwotActorProperty();
					swotActorProperty1.setPropertyType("advantage");
					swotActorProperty1.setPropertyContent("advantageContent1");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty1);
					SwotActorProperty swotActorProperty2 = new SwotActorProperty();
					swotActorProperty2.setPropertyType("advantage");
					swotActorProperty2.setPropertyContent("advantageContent2");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty2);
					SwotActorProperty swotActorProperty3 = new SwotActorProperty();
					swotActorProperty3.setPropertyType("advantage");
					swotActorProperty3.setPropertyContent("advantageContent3");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty3);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("chance").getModel();		//为chance添加3个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("chanceContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "chanceContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "chanceContent2", 2});	
					SwotActorProperty swotActorProperty34 = new SwotActorProperty();
					swotActorProperty34.setPropertyType("chance");
					swotActorProperty34.setPropertyContent("chanceContent0");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty34);
					SwotActorProperty swotActorProperty4 = new SwotActorProperty();
					swotActorProperty4.setPropertyType("chance");
					swotActorProperty4.setPropertyContent("chanceContent1");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty4);
					SwotActorProperty swotActorProperty5 = new SwotActorProperty();
					swotActorProperty5.setPropertyType("chance");
					swotActorProperty5.setPropertyContent("chanceContent2");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty5);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("disadvantage").getModel();	//为disadvantage添加3个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("disadvantageContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "disadvantageContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "disadvantageContent2", 2});
					SwotActorProperty swotActorProperty56 = new SwotActorProperty();
					swotActorProperty56.setPropertyType("disadvantage");
					swotActorProperty56.setPropertyContent("disadvantageContent0");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty56);
					SwotActorProperty swotActorProperty6 = new SwotActorProperty();
					swotActorProperty6.setPropertyType("disadvantage");
					swotActorProperty6.setPropertyContent("disadvantageContent1");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty6);
					SwotActorProperty swotActorProperty7 = new SwotActorProperty();
					swotActorProperty7.setPropertyType("disadvantage");
					swotActorProperty7.setPropertyContent("disadvantageContent2");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty7);
					
					dtm = (DefaultTableModel)newActorPanel.getInsertTable("threat").getModel();		//为threat添加5个条目
					dtm.setValueAt(1, 0, 0);dtm.setValueAt("threatContent0", 0, 1);dtm.setValueAt(0, 0, 2);		//第一行比较特殊
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent1", 1});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent2", 2});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent3", 3});
					dtm.addRow(new Object[]{dtm.getRowCount()+1, "threatContent4", 4});
					SwotActorProperty swotActorProperty78 = new SwotActorProperty();
					swotActorProperty78.setPropertyType("threat");
					swotActorProperty78.setPropertyContent("threatContent0");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty78);
					SwotActorProperty swotActorProperty8 = new SwotActorProperty();
					swotActorProperty8.setPropertyType("threat");
					swotActorProperty8.setPropertyContent("threatContent1");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty8);
					SwotActorProperty swotActorProperty9 = new SwotActorProperty();
					swotActorProperty9.setPropertyType("threat");
					swotActorProperty9.setPropertyContent("threatContent2");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty9);
					SwotActorProperty swotActorProperty10 = new SwotActorProperty();
					swotActorProperty10.setPropertyType("threat");
					swotActorProperty10.setPropertyContent("threatContent3");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty10);
					SwotActorProperty swotActorProperty11 = new SwotActorProperty();
					swotActorProperty11.setPropertyType("threat");
					swotActorProperty11.setPropertyContent("threatContent4");
					swotPropertyDAO.addProperty(swotActor4, swotActorProperty11);
					
					swotPropertyList4.add(swotActorProperty1);
					swotPropertyList4.add(swotActorProperty2);
					swotPropertyList4.add(swotActorProperty3);
					swotPropertyList4.add(swotActorProperty4);
					swotPropertyList4.add(swotActorProperty5);
					swotPropertyList4.add(swotActorProperty6);
					swotPropertyList4.add(swotActorProperty7);
					swotPropertyList4.add(swotActorProperty8);
					swotPropertyList4.add(swotActorProperty9);
					swotPropertyList4.add(swotActorProperty10);
					swotPropertyList4.add(swotActorProperty11);
				}
				//【此处if里面修改默认参与者属性~~~~end，有几个actor就有几个if判断】

				listModelActorPanel.addElement(newActorPanel);
				SwotActorGraphPanel newActorGraphPanel = null;
				//【此处if里面修改默认参与者属性（图形面板）~~~~start，有几个actor就有几个if判断】
				if(i == 0){
					newActorGraphPanel = new SwotActorGraphPanel(listModelSwotActor.getElementAt(i), swotPropertyList1);
				}else if(i == 1){
					newActorGraphPanel = new SwotActorGraphPanel(listModelSwotActor.getElementAt(i), swotPropertyList2);
				}else if(i == 2){
					newActorGraphPanel = new SwotActorGraphPanel(listModelSwotActor.getElementAt(i), swotPropertyList3);
				}else if(i == 3){
					newActorGraphPanel = new SwotActorGraphPanel(listModelSwotActor.getElementAt(i), swotPropertyList4);
				}
				//【此处if里面修改默认参与者属性（图形面板）~~~~end，有几个actor就有几个if判断】
					
				listModelActorGraphPanel.addElement(newActorGraphPanel);
				
				/*-------------添加一个装载actorpanel的JInternalFrame Start--------------*/
				final JInternalFrame tableInternalFrame = new JInternalFrame(listModelSwotActor.getElementAt(i).getActorName(), true, false, true, true);
				tableInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				tableInternalFrame.setLayout(new BorderLayout());		//该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果							
				tableInternalFrame.setContentPane(newActorPanel);
				tableInternalFrame.setVisible(true);
				listModelTableJInternalFrame.addElement(tableInternalFrame);
				
				final JInternalFrame graphInternalFrame = new JInternalFrame(listModelSwotActor.getElementAt(i).getActorName(), true, false, true, true);
				graphInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				graphInternalFrame.setLayout(new BorderLayout());		//该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果							
				graphInternalFrame.setContentPane(newActorGraphPanel);
				graphInternalFrame.setVisible(true);
				listModelGraphJInternalFrame.addElement(graphInternalFrame);
						
				tableInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {	//获取当前被选中的JInternalFrame的index，以便于toolbar定位（MouseListener监听不到）	
					@Override
					public void internalFrameActivated(InternalFrameEvent e) {
						// TODO Auto-generated method stub
						for(int	i = 0; i < listModelTableJInternalFrame.getSize(); i++)    {  		
							if(tableInternalFrame.equals(listModelTableJInternalFrame.getElementAt(i))) 
								selectedIndexOfListActorPanel = i;				
						}  
					}
				});
				/*-------------添加一个装载actorpanel的JInternalFrame End--------------*/
			}

			refreshActorPanel();	
		}	
		else if(e.getSource() == buttonClearExample)	{		//示例清空
			listModelSwotActor.clear();
			listModelActorName.clear();
			listModelActorPanel.clear();
			listModelActorGraphPanel.clear();
			listModelTableJInternalFrame.clear();
			listModelGraphJInternalFrame.clear();

			countSelectedActor = 0;		//初始化筛选显示参与者面板的个数	
			refreshActorPanel();	
			refreshActorGraphPanel();
		}
	}
}
