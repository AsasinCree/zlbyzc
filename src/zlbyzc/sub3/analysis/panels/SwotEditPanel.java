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
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;
import zlbyzc.sub3.analysis.entities.SwotTask;
import zlbyzc.sub3.analysis.services.SwotActorDAO;
import zlbyzc.sub3.analysis.services.SwotTaskDAO;
import zlbyzc.sub3.analysis.services.interfaces.SwotActorDAOInterface;
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
	private JPanel panelTestViewInTabbedPane;
	private JPanel panelInTestView;
	private Button refresh;
	private JDesktopPane desktop;
	private JDesktopPane desktopTest;
	private int actorPanelDimension;		//由actor个数决定 n x n 的排版进而算出每个actor面板大小
	private int actorPanelWidth;
	private int actorPanelHeight;
	private int selectedIndexOfListActorPanel;		//JInternalFrame监听。获取当前被选中的JInternalFrame的index，以便于toolbar定位
	private DefaultListModel<JInternalFrame> listModelJInternalFrame;
	private DefaultListModel<SwotActorPanel> listModelActorPanel;
	
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
		splitpane.setDividerLocation(110); 	//设置分隔条的位置
	
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
		
		buttonDeleteActor.addActionListener(this);
		buttonAddActor.addActionListener(this);
		
		labelInLeft = new JLabel("参与方");
		
		initializeListActorName();

		tabbedPane = new JTabbedPane();
		
		panelTableViewInTabbedPane = new JPanel();
		panelTableViewInTabbedPane.setLayout(new BorderLayout(0, 0));
		panelTestViewInTabbedPane = new JPanel();
		panelTestViewInTabbedPane.setLayout(new BorderLayout(0, 0));
		panelInTestView = new JPanel();
		panelInTestView.setLayout(new BorderLayout(0, 0));

		refresh = new Button("刷新");
		refresh.addActionListener(this);
		
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
		
		panelListLayList.add(Box.createHorizontalStrut(3));
		panelListLayList.add(scrollpaneList);
		
		panelList.add(Box.createVerticalStrut(10));
		panelList.add(panelListLayButton);
		panelList.add(Box.createVerticalStrut(10));
		panelList.add(panelListLayLabel);
		panelList.add(Box.createVerticalStrut(10));
		panelList.add(panelListLayList);
		
		tabbedPane.add(panelTableViewInTabbedPane,"表格");
		tabbedPane.add(panelTestViewInTabbedPane,"图形");
		
		panelTestViewInTabbedPane.add(panelInTestView, BorderLayout.CENTER);
		panelTestViewInTabbedPane.add(refresh, BorderLayout.SOUTH);
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
						
		listModelJInternalFrame = new DefaultListModel<JInternalFrame>();
	//	listJInternalFrame = new JList<JInternalFrame>(listModelJInternalFrame);
		
		listModelActorPanel = new DefaultListModel<SwotActorPanel>();
//		listActorPanel = new JList<SwotActorPanel>(listModelActorPanel);					
		
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
		listModelJInternalFrame.remove(selectedIndex);
		listModelActorPanel.remove(selectedIndex);
		
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
		if(desktop != null) {
			panelTableViewInTabbedPane.remove(desktop);
		}
		
		//将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后  actorpanel 层次问题【未知缘由】
		for(i = 0; i < listModelJInternalFrame.getSize(); i++) {
			
			try {
				if(listModelJInternalFrame.getElementAt(i).isIcon())
					listModelJInternalFrame.getElementAt(i).setIcon(false);		//去除最小化
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {	
				if(listModelJInternalFrame.getElementAt(i).isSelected())
					listModelJInternalFrame.getElementAt(i).setSelected(false);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		desktop = new JDesktopPane();
		desktop.setLayout(null);
		
		//循环加入一个个面板(未选中的除外)
		for(i = 0, j = 0; i < listModelActorName.getSize(); i++) {
			
			if(listModelActorName.getElementAt(i).isSelected() == true || countSelectedActor == 0) { //后者条件为没有实行筛选
					
				listModelJInternalFrame.getElementAt(i).setBounds((j%actorPanelDimension)*actorPanelWidth, (j/actorPanelDimension)*actorPanelHeight, actorPanelWidth, actorPanelHeight);

				desktop.add(listModelJInternalFrame.getElementAt(i));		
		
				j++;
			}
		}
		
		panelTableViewInTabbedPane.add(desktop);
		panelTableViewInTabbedPane.revalidate();
		panelTableViewInTabbedPane.repaint();
		
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
			
			/*-------------添加一个装载actorpanel的JInternalFrame Start--------------*/
			final JInternalFrame internalFrame = new JInternalFrame(swotActor.getActorName(), true, false, true, true);
			internalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			internalFrame.setLayout(new BorderLayout());		//该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果							
			internalFrame.setContentPane(newActorPanel);
			internalFrame.setVisible(true);
			listModelJInternalFrame.addElement(internalFrame);
					
			internalFrame.addInternalFrameListener(new InternalFrameAdapter() {	//获取当前被选中的JInternalFrame的index，以便于toolbar定位（MouseListener监听不到）	
				@Override
				public void internalFrameActivated(InternalFrameEvent e) {
					// TODO Auto-generated method stub
					for(int	i = 0; i < listModelJInternalFrame.getSize(); i++)    {  		
						if(internalFrame.equals(listModelJInternalFrame.getElementAt(i))) 
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
		
		else if(e.getSource() == refresh)	{
			int i=0, j=0;
			
			//计算出面板大小
			actorPanelDimension = 1;
			while(actorPanelDimension*actorPanelDimension < listModelActorName.getSize()) {
				actorPanelDimension++;
			}		System.out.println(panelInTestView.getSize().width);
			actorPanelWidth = panelInTestView.getSize().width/actorPanelDimension;
			actorPanelHeight = panelInTestView.getSize().height/actorPanelDimension;
			
			//清空原有的参与者面板
			if(desktopTest != null) {
				panelInTestView.remove(desktopTest);
			}
			
			desktopTest = new JDesktopPane();
			desktopTest.setLayout(null);
			
			SwotTaskDAOInterface swotTaskDAO = new SwotTaskDAO();
			List<SwotActor> swotActorList = swotTaskDAO.getAllTaskActors(swotTask);
			
			
			//循环加入
			if(swotActorList != null) {
				for(SwotActor swotActor:swotActorList) {
				//	SwotActorGrapghPanel newActorPanel = new SwotActorGrapghPanel(swotActor);
					
					SwotActor newActor = new SwotActor();
					SwotActorDAOInterface swotAcDAO = new SwotActorDAO();
					newActor = swotAcDAO.getActorByID(5);
					SwotActorGrapghPanel newActorPanel = new SwotActorGrapghPanel(swotActor);
					
					//添加一个装载panel的JInternalFrame Start
					final JInternalFrame internalFrame = new JInternalFrame(swotActor.getActorName(), true, false, true, true);
					internalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
					internalFrame.setLayout(new BorderLayout());		//该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果							
					internalFrame.setContentPane(newActorPanel);
					internalFrame.setVisible(true);
			
					internalFrame.setBounds((j%actorPanelDimension)*actorPanelWidth, (j/actorPanelDimension)*actorPanelHeight, actorPanelWidth, actorPanelHeight);
					desktopTest.add(internalFrame);		
					i++;
					j++;
				
				}
			}
			
			panelInTestView.add(desktopTest);
			panelInTestView.revalidate();
			panelInTestView.repaint();
		}	
	}
}
