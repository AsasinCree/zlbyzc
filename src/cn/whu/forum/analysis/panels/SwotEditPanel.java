package cn.whu.forum.analysis.panels;

import java.awt.BorderLayout;
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
import javax.swing.Box;
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
import javax.swing.JTextArea;
//import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableModel;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.entities.SwotTask;
import cn.whu.forum.analysis.services.SwotActorDAO;
import cn.whu.forum.analysis.services.SwotPropertyDAO;
import cn.whu.forum.analysis.services.SwotTaskDAO;
import cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface;
import cn.whu.forum.analysis.services.interfaces.SwotPropertyDAOInterface;

public class SwotEditPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	// 参与者数据操作接口
	private SwotActorDAOInterface swotActorDAO;
	// 依附的SWOT法案例
	private SwotTask swotTask;

	// 顶部面板
	private JPanel panelTop;
	// 底部面板
	private JPanel panelBottom;
	// 分隔面板
	private JSplitPane splitpane;

	// 任意的Component都可以添加到工具栏
	JToolBar toolBar;
	// 工具栏添加属性按钮
	JButton buttonAddPropertyInToolBar;
	// 工具栏删除属性按钮
	JButton buttonDeletePropertyInToolBar;
	// 工具栏修改属性按钮
	JButton buttonModifyPropertyInToolBar;

	// 底部左侧面板
	private JPanel panelList;
	private JPanel panelListLayButton;
	private JPanel panelListLayLabel;
	private JPanel panelListLayList;
	// 添加参与方按钮
	private JButton buttonAddActor;
	// 删除参与方按钮
	private JButton buttonDeleteActor;
	// 示例按钮
	private JButton buttonExample;
	// 示例清空按钮
	private JButton buttonClearExample;
	// 存储参与方名称
	private DefaultListModel<SwotActor> listModelSwotActor;
	// "参与方"显示标签
	private JLabel labelInLeft;
	// 列表容器
	private JScrollPane scrollpaneList;
	// 选择显示的参与者的数量
	private int countSelectedActor;
	// 存储参与方名称
	private JList<SwotCheckboxListItem> listActorName;
	// 存储参与方名称
	private DefaultListModel<SwotCheckboxListItem> listModelActorName;

	// 底部左侧面板添加参与者面板
	private JFrame frameAddActor;
	private JPanel panelAddActor;
	private JPanel panelLayLabelInAddActor;
	private JPanel panelLayTextfieldInAddActor;
	private JPanel panelLayButtonInAddActor;
	private JLabel lableActorNameInAddActor;
	private JTextField textfieldActorNameInAddActor;
	// 添加参与方按钮
	private JButton buttonConfirmInAddActor;
	// 删除参与方按钮
	private JButton buttonCancelInAddActor;

	// 底部右侧面板
	private JTabbedPane tabbedPane;
	private JTextArea textfieldResultInTableView;
	private JTextArea textfieldResultInGraphView;
	private JSplitPane splitpaneInTableView;
	private JSplitPane splitpaneInGraphView;
	private JButton buttonSaveInResultInTableView;
	private JButton buttonResetInResultInTableView;
	private JButton buttonSaveInResultInGraphView;
	private JButton buttonResetInResultInGraphView;
	private JPanel panelTableViewInTabbedPane;
	private JPanel panelLayTableInTableView;
	private JPanel panelLayResultInTableView;
	private JPanel panelLayTextAreaInResultInTableView;
	private JPanel panelLayButtonInResultInTableView;
	private JPanel panelGraphViewInTabbedPane;
	private JPanel panelLayGraphInGraphView;
	private JPanel panelLayResultInGraphView;
	private JPanel panelLayTextAreaInResultInGraphView;
	private JPanel panelLayButtonInResultInGraphView;
	private JDesktopPane tableDesktop;
	private JDesktopPane graphDesktop;
	// 由actor个数决定 n x n 的排版进而算出每个actor面板大小
	private int actorPanelDimension;
	private int actorPanelWidth;
	private int actorPanelHeight;
	// JInternalFrame监听。获取当前被选中的JInternalFrame的index，以便于toolbar定位
	private int selectedIndexOfListActorPanel;
	private DefaultListModel<JInternalFrame> listModelTableJInternalFrame;
	private DefaultListModel<SwotActorPanel> listModelActorPanel;
	private DefaultListModel<JInternalFrame> listModelGraphJInternalFrame;
	private DefaultListModel<SwotActorGraphPanel> listModelActorGraphPanel;

	/**
	 * 构造函数：初始化操作接口、控件及布局。
	 * 
	 * @param swotActor
	 *            SWOT法案例
	 */
	public SwotEditPanel(SwotTask swotTask) {
		swotActorDAO = new SwotActorDAO();
		this.swotTask = swotTask;

		initializeComponent();

		layoutComponent();
	}

	/**
	 * 初始化控件。
	 */
	public void initializeComponent() {
		// 当前JPanel设置
		setLayout(new BorderLayout());

		// 顶部面板
		panelTop = new JPanel();
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.X_AXIS));

		// 任意的Component都可以添加到工具栏
		toolBar = new JToolBar();
		buttonAddPropertyInToolBar = new JButton("添加属性");
		buttonDeletePropertyInToolBar = new JButton("删除属性");
		buttonModifyPropertyInToolBar = new JButton("修改属性");

		buttonAddPropertyInToolBar.addActionListener(this);
		buttonDeletePropertyInToolBar.addActionListener(this);
		buttonModifyPropertyInToolBar.addActionListener(this);

		// 底部面板
		panelBottom = new JPanel();
		panelBottom.setLayout(new BorderLayout());

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
		splitpane.setDividerLocation(260);

		panelList = new JPanel();
		panelList.setLayout(new BoxLayout(panelList, BoxLayout.Y_AXIS));

		panelListLayButton = new JPanel();
		panelListLayLabel = new JPanel();
		panelListLayList = new JPanel();

		panelListLayButton.setLayout(new BoxLayout(panelListLayButton, BoxLayout.X_AXIS));
		panelListLayLabel.setLayout(new BoxLayout(panelListLayLabel, BoxLayout.X_AXIS));
		panelListLayList.setLayout(new BoxLayout(panelListLayList, BoxLayout.X_AXIS));

		// 左侧面板添加参与方按钮
		buttonAddActor = new JButton("+");
		buttonAddActor.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		// 左侧面板删除参与方按钮
		buttonDeleteActor = new JButton("-");
		buttonDeleteActor.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		buttonExample = new JButton("示例");
		buttonExample.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		buttonClearExample = new JButton("示例清空");
		buttonClearExample.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);

		// 按钮事件注册
		buttonDeleteActor.addActionListener(this);
		buttonAddActor.addActionListener(this);
		buttonExample.addActionListener(this);
		buttonClearExample.addActionListener(this);

		labelInLeft = new JLabel("参与方");
		labelInLeft.setFont(StaticConfig.FONT_EDITVIEW_LABEL);

		// 初始化参与者列表及选中事件注册
		initializeListActorName();
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(StaticConfig.FONT_EDITVIEW_LABEL);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
				textfieldResultInTableView.setText(swotTaskDAO.getTaskResult(swotTask));
				textfieldResultInGraphView.setText(swotTaskDAO.getTaskResult(swotTask));
				refreshActorGraphPanel();
			}
		});

		splitpaneInTableView = new JSplitPane();
		// 让分隔线显示出箭头
		splitpaneInTableView.setOneTouchExpandable(true);
		// 当用户操作分隔线箭头时，系统重绘图形
		splitpaneInTableView.setContinuousLayout(true);
		// 设置方向或者分隔窗格的方式（HORIZONTAL_SPLIT表示左右分隔，VERTICAL_SPLIT）
		splitpaneInTableView.setOrientation(JSplitPane.VERTICAL_SPLIT);
		// 设置分隔条的大小
		splitpaneInTableView.setDividerSize(3);
		// 设置分隔条的位置
		splitpaneInTableView.setDividerLocation(600);

		splitpaneInGraphView = new JSplitPane();
		// 让分隔线显示出箭头
		splitpaneInGraphView.setOneTouchExpandable(true);
		// 当用户操作分隔线箭头时，系统重绘图形
		splitpaneInGraphView.setContinuousLayout(true);
		// 设置方向或者分隔窗格的方式（HORIZONTAL_SPLIT表示左右分隔，VERTICAL_SPLIT）
		splitpaneInGraphView.setOrientation(JSplitPane.VERTICAL_SPLIT);
		// 设置分隔条的大小
		splitpaneInGraphView.setDividerSize(3);
		// 设置分隔条的位置
		splitpaneInGraphView.setDividerLocation(600);

		textfieldResultInTableView = new JTextArea();
		// 支持自动换行
		textfieldResultInTableView.setLineWrap(true);
		textfieldResultInTableView.setFont(StaticConfig.FONT_EDITVIEW_TEXTAREA);
		textfieldResultInGraphView = new JTextArea();
		// 支持自动换行
		textfieldResultInGraphView.setLineWrap(true);
		textfieldResultInGraphView.setFont(StaticConfig.FONT_EDITVIEW_TEXTAREA);

		buttonSaveInResultInTableView = new JButton("保存");
		buttonSaveInResultInTableView.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		buttonResetInResultInTableView = new JButton("重置");
		buttonResetInResultInTableView.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		buttonSaveInResultInGraphView = new JButton("保存");
		buttonSaveInResultInGraphView.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		buttonResetInResultInGraphView = new JButton("重置");
		buttonResetInResultInGraphView.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		buttonSaveInResultInTableView.addActionListener(this);
		buttonResetInResultInTableView.addActionListener(this);
		buttonSaveInResultInGraphView.addActionListener(this);
		buttonResetInResultInGraphView.addActionListener(this);

		panelTableViewInTabbedPane = new JPanel();
		panelLayTableInTableView = new JPanel();
		panelLayResultInTableView = new JPanel();
		panelLayTextAreaInResultInTableView = new JPanel();
		panelLayButtonInResultInTableView = new JPanel();

		panelGraphViewInTabbedPane = new JPanel();
		panelLayGraphInGraphView = new JPanel();
		panelLayResultInGraphView = new JPanel();
		panelLayTextAreaInResultInGraphView = new JPanel();
		panelLayButtonInResultInGraphView = new JPanel();

		panelTableViewInTabbedPane.setLayout(new BorderLayout(0, 0));
		panelLayTableInTableView.setLayout(new BorderLayout(0, 0));
		panelLayResultInTableView.setLayout(new BoxLayout(panelLayResultInTableView, BoxLayout.X_AXIS));
		panelLayTextAreaInResultInTableView.setLayout(new BorderLayout(0, 0));
		panelLayButtonInResultInTableView.setLayout(new BoxLayout(panelLayButtonInResultInTableView, BoxLayout.Y_AXIS));

		panelGraphViewInTabbedPane.setLayout(new BorderLayout(0, 0));
		panelLayGraphInGraphView.setLayout(new BorderLayout(0, 0));
		panelLayResultInGraphView.setLayout(new BoxLayout(panelLayResultInGraphView, BoxLayout.X_AXIS));
		panelLayTextAreaInResultInGraphView.setLayout(new BorderLayout(0, 0));
		panelLayButtonInResultInGraphView.setLayout(new BoxLayout(panelLayButtonInResultInGraphView, BoxLayout.Y_AXIS));

		initializeListActorPanel();
	}

	/**
	 * 初始化布局
	 */
	public void layoutComponent() {
		panelTop.add(toolBar);

		toolBar.add(buttonAddPropertyInToolBar);
		toolBar.addSeparator();
		toolBar.add(buttonDeletePropertyInToolBar);
		toolBar.addSeparator();
		toolBar.add(buttonModifyPropertyInToolBar);

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

		tabbedPane.add(panelTableViewInTabbedPane, "表格");
		tabbedPane.add(panelGraphViewInTabbedPane, "图形");

		panelTableViewInTabbedPane.add(splitpaneInTableView);
		panelGraphViewInTabbedPane.add(splitpaneInGraphView);

		splitpaneInTableView.setTopComponent(panelLayTableInTableView);
		splitpaneInTableView.setBottomComponent(panelLayResultInTableView);
		splitpaneInGraphView.setTopComponent(panelLayGraphInGraphView);
		splitpaneInGraphView.setBottomComponent(panelLayResultInGraphView);

		panelLayResultInTableView.add(panelLayTextAreaInResultInTableView);
		panelLayResultInTableView.add(panelLayButtonInResultInTableView);
		panelLayResultInGraphView.add(panelLayTextAreaInResultInGraphView);
		panelLayResultInGraphView.add(panelLayButtonInResultInGraphView);

		JLabel labelInResultInTableView = new JLabel();
		labelInResultInTableView.setFont(StaticConfig.FONT_EDITVIEW_LABEL);
		labelInResultInTableView.setText("<html>结<br/>果</html>");
		JLabel labelInResultInGraphView = new JLabel();
		labelInResultInGraphView.setFont(StaticConfig.FONT_EDITVIEW_LABEL);
		labelInResultInGraphView.setText("<html>结<br/>果</html>");
		panelLayTextAreaInResultInTableView.add(labelInResultInTableView, BorderLayout.WEST);
		panelLayTextAreaInResultInTableView.add(new JScrollPane(textfieldResultInTableView));
		panelLayButtonInResultInTableView.add(buttonSaveInResultInTableView);
		panelLayButtonInResultInTableView.add(buttonResetInResultInTableView);
		panelLayTextAreaInResultInGraphView.add(labelInResultInGraphView, BorderLayout.WEST);
		panelLayTextAreaInResultInGraphView.add(new JScrollPane(textfieldResultInGraphView));
		panelLayButtonInResultInGraphView.add(buttonSaveInResultInGraphView);
		panelLayButtonInResultInGraphView.add(buttonResetInResultInGraphView);

		add(panelBottom, BorderLayout.CENTER);
	}

	/**
	 * 初始化左侧参与者列表
	 */
	public void initializeListActorName() {
		listModelActorName = new DefaultListModel<SwotCheckboxListItem>();

		listActorName = new JList<SwotCheckboxListItem>(listModelActorName); // 左侧面板列表
		listActorName.setCellRenderer(new SwotCheckboxListRenderer()); // 添加渲染使得JList内容装入JCheckBox
		listActorName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 【待验证功能】
		listActorName.setBorder(BorderFactory.createTitledBorder(""));

		// 为 选择显示的参与者的数量 赋初值0
		countSelectedActor = 0;

		// 监听单击时checkbox框toggle
		listActorName.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 所单击的列表项目isSelected切换
				SwotCheckboxListItem selectedSwotActorListItem = listActorName.getSelectedValue();
				selectedSwotActorListItem.setSelected(!selectedSwotActorListItem.isSelected);

				// 检测是否选中
				if (selectedSwotActorListItem.isSelected() == true) {
					countSelectedActor++;
				} else {
					countSelectedActor--;
				}

				refreshActorPanel();
				refreshActorGraphPanel();

				listActorName.repaint();
			}
		});
		// 左侧面板列表容器
		scrollpaneList = new JScrollPane(listActorName);
	}

	/**
	 * 初始化右侧参与者面板列表
	 */
	public void initializeListActorPanel() {
		listModelTableJInternalFrame = new DefaultListModel<JInternalFrame>();

		listModelActorPanel = new DefaultListModel<SwotActorPanel>();

		listModelGraphJInternalFrame = new DefaultListModel<JInternalFrame>();
		listModelActorGraphPanel = new DefaultListModel<SwotActorGraphPanel>();
	}

	/**
	 * 添加参与者
	 */
	public void addActor() {
		// 初始化各个控件
		frameAddActor = new JFrame("添加参与方");
		frameAddActor.setSize(400, 300);
		frameAddActor.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // HIDE_ON_CLOSE表示在点击X按钮时不退出整个程序，只是将子对话框隐藏
		frameAddActor.setLocationRelativeTo(null);

		panelAddActor = new JPanel();
		panelLayLabelInAddActor = new JPanel();
		panelLayTextfieldInAddActor = new JPanel();
		panelLayButtonInAddActor = new JPanel();
		panelAddActor.setLayout(new BoxLayout(panelAddActor, BoxLayout.Y_AXIS));
		panelLayLabelInAddActor.setLayout(new BoxLayout(panelLayLabelInAddActor, BoxLayout.X_AXIS));
		panelLayTextfieldInAddActor.setLayout(new BoxLayout(panelLayTextfieldInAddActor, BoxLayout.X_AXIS));
		panelLayButtonInAddActor.setLayout(new BoxLayout(panelLayButtonInAddActor, BoxLayout.X_AXIS));

		lableActorNameInAddActor = new JLabel("参与方名称");
		lableActorNameInAddActor.setFont(StaticConfig.FONT_DIALOG_LABEL);

		textfieldActorNameInAddActor = new JTextField();
		textfieldActorNameInAddActor.setFont(StaticConfig.FONT_DIALOG_TEXTAREA);
		textfieldActorNameInAddActor.setMinimumSize(new Dimension(150, 25));
		textfieldActorNameInAddActor.setMaximumSize(new Dimension(150, 30));

		buttonConfirmInAddActor = new JButton("确认");
		buttonConfirmInAddActor.setFont(StaticConfig.FONT_DIALOG_BUTTON);
		buttonCancelInAddActor = new JButton("取消");
		buttonCancelInAddActor.setFont(StaticConfig.FONT_DIALOG_BUTTON);

		buttonConfirmInAddActor.addActionListener(this);
		buttonCancelInAddActor.addActionListener(this);

		// 布局控件
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
	}

	/**
	 * 删除参与者
	 */
	public void deleteActor() {
		swotActorDAO.deleteActorByID(listActorName.getSelectedValue().getActorID());

		int selectedIndex = listActorName.getSelectedIndex();
		listModelActorName.remove(selectedIndex);
		listModelTableJInternalFrame.remove(selectedIndex);
		listModelActorPanel.remove(selectedIndex);
		listModelGraphJInternalFrame.remove(selectedIndex);
		listModelActorGraphPanel.remove(selectedIndex);

		// 初始化筛选显示参与者面板的个数
		countSelectedActor = 0;

		refreshActorPanel();
	}

	/**
	 * 筛选或者添加删除参与者之后重新排版右侧参与者面板 根据筛选的数目计算出面板大小 循环生成面板
	 */
	public void refreshActorPanel() {
		int countLoop, i, j;

		// 检测有没有实行筛选
		if (countSelectedActor == 0)
			// 没有筛选
			countLoop = listModelActorName.getSize();
		else
			countLoop = countSelectedActor;

		// 计算出面板大小
		actorPanelDimension = 1;
		while (actorPanelDimension * actorPanelDimension < countLoop) {
			actorPanelDimension++;
		}
		actorPanelWidth = panelLayTableInTableView.getSize().width / actorPanelDimension;
		actorPanelHeight = panelLayTableInTableView.getSize().height / actorPanelDimension;

		// 清空原有的参与者面板
		if (tableDesktop != null) {
			panelLayTableInTableView.remove(tableDesktop);
		}

		// 将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后 actorpanel
		// 层次问题【未知缘由】
		for (i = 0; i < listModelTableJInternalFrame.getSize(); i++) {

			try {
				if (listModelTableJInternalFrame.getElementAt(i).isIcon())
					listModelTableJInternalFrame.getElementAt(i).setIcon(false); // 去除最小化
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}

			try {
				if (listModelTableJInternalFrame.getElementAt(i).isSelected())
					listModelTableJInternalFrame.getElementAt(i).setSelected(false);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}

		tableDesktop = new JDesktopPane();
		tableDesktop.setLayout(null);

		// 循环加入一个个面板(未选中的除外)
		for (i = 0, j = 0; i < listModelActorName.getSize(); i++) {
			// 后者条件为没有实行筛选
			if (listModelActorName.getElementAt(i).isSelected() == true || countSelectedActor == 0) {
				listModelTableJInternalFrame.getElementAt(i).setBounds((j % actorPanelDimension) * actorPanelWidth,
						(j / actorPanelDimension) * actorPanelHeight, actorPanelWidth, actorPanelHeight);
				tableDesktop.add(listModelTableJInternalFrame.getElementAt(i));

				j++;
			}
		}

		SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
		textfieldResultInTableView.setText(swotTaskDAO.getTaskResult(swotTask));

		splitpaneInTableView.setDividerLocation(splitpaneInTableView.getDividerLocation());

		panelLayTableInTableView.add(tableDesktop);
		panelLayTableInTableView.revalidate();
		panelLayTableInTableView.repaint();
	}

	/**
	 * 刷新参与者图形面板 根据筛选的数目计算出面板大小 循环生成面板
	 */
	public void refreshActorGraphPanel() {
		int countLoop, i, j;

		// 检测有没有实行筛选
		if (countSelectedActor == 0)
			// 没有筛选
			countLoop = listModelActorName.getSize();
		else
			countLoop = countSelectedActor;

		// 计算出面板大小
		actorPanelDimension = 1;
		while (actorPanelDimension * actorPanelDimension < countLoop) {
			actorPanelDimension++;
		}
		actorPanelWidth = panelLayGraphInGraphView.getSize().width / actorPanelDimension;
		actorPanelHeight = panelLayGraphInGraphView.getSize().height / actorPanelDimension;

		// 清空原有的参与者面板
		if (graphDesktop != null) {
			panelLayGraphInGraphView.remove(graphDesktop);
		}

		// 将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后 actorpanel
		// 层次问题【未知缘由】
		for (i = 0; i < listModelTableJInternalFrame.getSize(); i++) {
			try {
				if (listModelGraphJInternalFrame.getElementAt(i).isIcon())
					listModelGraphJInternalFrame.getElementAt(i).setIcon(false);
			} catch (PropertyVetoException e1) {

				e1.printStackTrace();
			}

			try {
				if (listModelGraphJInternalFrame.getElementAt(i).isSelected())
					listModelGraphJInternalFrame.getElementAt(i).setSelected(false);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}

		graphDesktop = new JDesktopPane();
		graphDesktop.setLayout(null);

		// 循环加入一个个面板(未选中的除外)
		for (i = 0, j = 0; i < listModelActorName.getSize(); i++) {
			if (listModelActorName.getElementAt(i).isSelected() == true || countSelectedActor == 0) { // 后者条件为没有实行筛选
				listModelGraphJInternalFrame.getElementAt(i).setBounds((j % actorPanelDimension) * actorPanelWidth,
						(j / actorPanelDimension) * actorPanelHeight, actorPanelWidth, actorPanelHeight);
				graphDesktop.add(listModelGraphJInternalFrame.getElementAt(i));
				listModelActorGraphPanel.getElementAt(i).refreshPanel();
				j++;
			}
		}

		SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
		textfieldResultInGraphView.setText(swotTaskDAO.getTaskResult(swotTask));

		splitpaneInGraphView.setDividerLocation(splitpaneInGraphView.getDividerLocation());

		panelLayGraphInGraphView.add(graphDesktop);
		panelLayGraphInGraphView.revalidate();
		panelLayGraphInGraphView.repaint();
	}

	/*
	 * 综合处理按钮事件
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonAddPropertyInToolBar) {
			// 工具栏添加属性按钮
			// 获取当前选中的actorpanel
			// 调用actorpanel中的添加属性方法
			listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).addProperty();
		} else if (e.getSource() == buttonDeletePropertyInToolBar) {
			// 工具栏删除属性按钮
			if (listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).getSelectedTable().getValueAt(0,
					0) != "") {
				listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).deleteProperty();
			} else {
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除内容操作", "", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == buttonModifyPropertyInToolBar) {
			// 工具栏删除属性按钮修改
			if (listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).getSelectedTable().getValueAt(0,
					0) != "")
				listModelActorPanel.getElementAt(selectedIndexOfListActorPanel).modifyProperty();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == buttonAddActor) {
			// 添加参与者弹出面板
			addActor();
		} else if (e.getSource() == buttonDeleteActor) {
			// 删除参与者按钮
			deleteActor();
		}

		else if (e.getSource() == buttonConfirmInAddActor) {
			// 确认添加参与者
			SwotActor swotActor = new SwotActor();
			swotActor.setActorName(textfieldActorNameInAddActor.getText());

			swotActorDAO.addActor(swotTask, swotActor);

			// 添加一个参与者列表项目
			SwotCheckboxListItem newSwotCheckboxListItem = new SwotCheckboxListItem(swotActor.getActorName());
			newSwotCheckboxListItem.setActorID(swotActor.getActorID());
			listModelActorName.addElement(newSwotCheckboxListItem);

			// 添加一个actorpanel
			SwotActorPanel newActorPanel = new SwotActorPanel(swotActor);
			listModelActorPanel.addElement(newActorPanel);
			SwotActorGraphPanel newActorGraphPanel = new SwotActorGraphPanel(swotActor);

			listModelActorGraphPanel.addElement(newActorGraphPanel);

			// 添加一个装载actorpanel的JInternalFrame
			final JInternalFrame tableInternalFrame = new JInternalFrame(swotActor.getActorName(), true, false, true,
					true);
			tableInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			// 该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果
			tableInternalFrame.setLayout(new BorderLayout());
			tableInternalFrame.setContentPane(newActorPanel);
			tableInternalFrame.setVisible(true);
			listModelTableJInternalFrame.addElement(tableInternalFrame);

			final JInternalFrame graphInternalFrame = new JInternalFrame(swotActor.getActorName(), true, false, true,
					true);
			graphInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			// 该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果
			graphInternalFrame.setLayout(new BorderLayout());
			graphInternalFrame.setContentPane(newActorGraphPanel);
			graphInternalFrame.setVisible(true);
			listModelGraphJInternalFrame.addElement(graphInternalFrame);

			tableInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {
				// 获取当前被选中的JInternalFrame的index，以便于toolbar定位（MouseListener监听不到）
				@Override
				public void internalFrameActivated(InternalFrameEvent e) {
					for (int i = 0; i < listModelTableJInternalFrame.getSize(); i++) {
						if (tableInternalFrame.equals(listModelTableJInternalFrame.getElementAt(i)))
							selectedIndexOfListActorPanel = i;
					}
				}
			});
			textfieldActorNameInAddActor.setText("");
			buttonConfirmInAddActor.setText("继续添加");

			refreshActorPanel();
		} else if (e.getSource() == buttonCancelInAddActor) {
			// 取消添加参与者
			frameAddActor.dispose();
		} else if (e.getSource() == buttonExample) {
			// 示例添加按钮
			listModelSwotActor = new DefaultListModel<SwotActor>();

			// 此处修改默认添加actor列表
			SwotActor swotActor = new SwotActor();
			swotActor.setActorName("某部队");
			listModelSwotActor.addElement(swotActor);
			swotActorDAO.addActor(swotTask, swotActor);

			for (int i = 0; i < listModelSwotActor.size(); i++) {
				// 添加一个参与者列表项目
				SwotCheckboxListItem newSwotCheckboxListItem = new SwotCheckboxListItem(
						listModelSwotActor.getElementAt(i).getActorName());
				newSwotCheckboxListItem.setActorID(listModelSwotActor.getElementAt(i).getActorID());
				listModelActorName.addElement(newSwotCheckboxListItem);

				DefaultTableModel dtm;
				SwotPropertyDAOInterface swotPropertyDAO = new SwotPropertyDAO();

				// 此处if里面修改默认参与者属性
				SwotActorPanel newActorPanel = new SwotActorPanel(listModelSwotActor.getElementAt(i));
				List<SwotActorProperty> swotPropertyList1 = new ArrayList<SwotActorProperty>();
				if (i == 0) {
					// 第一个actor属性
					dtm = (DefaultTableModel) newActorPanel.getInsertTable("advantage").getModel();
					// 为advantage添加4个条目
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt("资源丰富", 0, 1);
					dtm.setValueAt(0, 0, 2);
					// 第一行比较特殊
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, "经济性优势", 1 });
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, "标准化优势", 2 });
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

					dtm = (DefaultTableModel) newActorPanel.getInsertTable("chance").getModel();
					// 为chance添加3个条目
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt("国家号召相应建设节约型社会", 0, 1);
					dtm.setValueAt(0, 0, 2);
					// 第一行比较特殊
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, "军队后勤也在积极发展节约型后勤", 1 });
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

					dtm = (DefaultTableModel) newActorPanel.getInsertTable("disadvantage").getModel(); // 为disadvantage添加3个条目
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt("基础设施薄弱", 0, 1);
					dtm.setValueAt(0, 0, 2);
					// 第一行比较特殊
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, "管理手段劣势", 1 });
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, "信息技术落后", 2 });
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

					dtm = (DefaultTableModel) newActorPanel.getInsertTable("threat").getModel();
					// 为threat添加5个条目
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt("未来信息化战争的挑战", 0, 1);
					dtm.setValueAt(0, 0, 2);
					// 第一行比较特殊
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, "人才的挑战", 1 });
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
				}
				listModelActorPanel.addElement(newActorPanel);
				SwotActorGraphPanel newActorGraphPanel = null;
				// 此处if里面修改默认参与者属性（图形面板）~~~~start，有几个actor就有几个if判断
				if (i == 0) {
					newActorGraphPanel = new SwotActorGraphPanel(listModelSwotActor.getElementAt(i), swotPropertyList1);
				}
				// 此处if里面修改默认参与者属性（图形面板）~~~~end，有几个actor就有几个if判断

				listModelActorGraphPanel.addElement(newActorGraphPanel);

				// 添加一个装载actorpanel的JInternalFrame
				final JInternalFrame tableInternalFrame = new JInternalFrame(
						listModelSwotActor.getElementAt(i).getActorName(), true, false, true, true);
				tableInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				// 该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果
				tableInternalFrame.setLayout(new BorderLayout());
				tableInternalFrame.setContentPane(newActorPanel);
				tableInternalFrame.setVisible(true);
				listModelTableJInternalFrame.addElement(tableInternalFrame);

				final JInternalFrame graphInternalFrame = new JInternalFrame(
						listModelSwotActor.getElementAt(i).getActorName(), true, false, true, true);
				graphInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				// 该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果
				graphInternalFrame.setLayout(new BorderLayout());
				graphInternalFrame.setContentPane(newActorGraphPanel);
				graphInternalFrame.setVisible(true);
				listModelGraphJInternalFrame.addElement(graphInternalFrame);

				tableInternalFrame.addInternalFrameListener(new InternalFrameAdapter() {
					// 获取当前被选中的JInternalFrame的index，以便于toolbar定位（MouseListener监听不到）
					@Override
					public void internalFrameActivated(InternalFrameEvent e) {
						for (int i = 0; i < listModelTableJInternalFrame.getSize(); i++) {
							if (tableInternalFrame.equals(listModelTableJInternalFrame.getElementAt(i)))
								selectedIndexOfListActorPanel = i;
						}
					}
				});
			}

			refreshActorPanel();
		} else if (e.getSource() == buttonClearExample) {
			// 示例清空
			listModelSwotActor.clear();
			listModelActorName.clear();
			listModelActorPanel.clear();
			listModelActorGraphPanel.clear();
			listModelTableJInternalFrame.clear();
			listModelGraphJInternalFrame.clear();

			// 初始化筛选显示参与者面板的个数
			countSelectedActor = 0;
			refreshActorPanel();
			refreshActorGraphPanel();
		} else if (e.getSource() == buttonSaveInResultInTableView) {
			// 表格界面结果保存
			SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
			swotTask.setMark1(textfieldResultInTableView.getText());
			swotTaskDAO.updateTaskResult(swotTask);
		} else if (e.getSource() == buttonResetInResultInTableView) { // 表格界面结果保存
			textfieldResultInTableView.setText("");
		} else if (e.getSource() == buttonSaveInResultInGraphView) {
			// 图形界面结果保存
			SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
			swotTask.setMark1(textfieldResultInGraphView.getText());
			;
			swotTaskDAO.updateTaskResult(swotTask);
		} else if (e.getSource() == buttonResetInResultInGraphView) { // 图形界面结果保存
			textfieldResultInGraphView.setText("");
		}
	}
}
