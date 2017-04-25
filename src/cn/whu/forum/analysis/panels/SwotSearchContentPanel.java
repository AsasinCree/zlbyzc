package cn.whu.forum.analysis.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.entities.SwotTask;
import cn.whu.forum.analysis.services.SwotActorDAO;
import cn.whu.forum.analysis.services.SwotTaskDAO;
import cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface;
import cn.whu.forum.analysis.services.interfaces.SwotTaskDAOInterface;

/**
 * SWOT法查询系统内容面板
 * 
 * @author asasi
 *
 */
public class SwotSearchContentPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	// 参与者数据操作接口
	private SwotActorDAOInterface swotActorDAO;
	private SwotTask swotTask;

	// 底部面板
	private JPanel panelBottom;
	// 分隔面板
	private JSplitPane splitpane;

	// 底部左侧面板
	private JPanel panelList;
	private JPanel panelListLayButton;
	private JPanel panelListLayLabel;
	private JPanel panelListLayList;
	// 添加参与方按钮
	private JButton buttonAddActor;
	// 删除参与方按钮
	private JButton buttonDeleteActor;
	// "参与方"显示标签
	private JLabel labelInLeft;
	// 列表容器
	private JScrollPane scrollpaneList;
	// 选择显示的参与者的数量
	private int countSelectedActor;
	// 存储参与方名称
	private JList<SwotCheckboxListItem> listActorName;
	private DefaultListModel<SwotCheckboxListItem> listModelActorName; // 存储参与方名称
	private DefaultTableModel dtm;

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
	private DefaultListModel<JInternalFrame> listModelTableJInternalFrame;
	private DefaultListModel<SwotActorPanel> listModelActorPanel;
	private DefaultListModel<JInternalFrame> listModelGraphJInternalFrame;
	private DefaultListModel<SwotActorGraphPanel> listModelActorGraphPanel;

	/**
	 * 构造函数：初始化操作接口、控件及布局。
	 * 
	 * @param swotTask
	 *            表示装载的SWOT法案例实例
	 */
	public SwotSearchContentPanel(SwotTask swotTask) {
		swotActorDAO = new SwotActorDAO();
		this.swotTask = swotTask;

		initializeComponent();

		layoutComponent();
	}

	/**
	 * 初始化控件。
	 */
	public void initializeComponent() {
		// JPanel设置
		setLayout(new BorderLayout());

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

		buttonDeleteActor.addActionListener(this);
		buttonAddActor.addActionListener(this);

		labelInLeft = new JLabel("参与方");
		labelInLeft.setFont(StaticConfig.FONT_EDITVIEW_LABEL);

		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(StaticConfig.FONT_EDITVIEW_LABEL);
		// 点击切换选项卡时刷新图形界面
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
				textfieldResultInTableView.setText(swotTaskDAO.getTaskResult(swotTask));
				textfieldResultInGraphView.setText(swotTaskDAO.getTaskResult(swotTask));
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

		initializeList();

		panelTableViewInTabbedPane.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				// 由于panel在布局初始化时没有大小，固获取其大小要放在此处监听
				// 计算出面板大小
				actorPanelDimension = 1;
				while (actorPanelDimension * actorPanelDimension < listModelActorName.getSize()) {
					actorPanelDimension++;
				}

				actorPanelWidth = panelTableViewInTabbedPane.getSize().width / actorPanelDimension;
				actorPanelHeight = panelTableViewInTabbedPane.getSize().height / actorPanelDimension;

				refreshAll();
			}
		});

		// 左侧面板列表容器
		scrollpaneList = new JScrollPane(listActorName);
	}

	/**
	 * 初始化布局
	 */
	public void layoutComponent() {
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
	 * 初始化列表
	 */
	public void initializeList() {
		// 为选择显示的参与者的数量 赋初值0
		countSelectedActor = 0;

		listModelActorName = new DefaultListModel<SwotCheckboxListItem>();
		listActorName = new JList<SwotCheckboxListItem>(listModelActorName); // 左侧面板列表
		listActorName.setCellRenderer(new SwotCheckboxListRenderer()); // 添加渲染使得JList内容装入JCheckBox
		listActorName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 【待验证功能】
		listActorName.setBorder(BorderFactory.createTitledBorder(""));

		listModelTableJInternalFrame = new DefaultListModel<JInternalFrame>();
		listModelActorPanel = new DefaultListModel<SwotActorPanel>();
		listModelGraphJInternalFrame = new DefaultListModel<JInternalFrame>();
		listModelActorGraphPanel = new DefaultListModel<SwotActorGraphPanel>();

		SwotTaskDAOInterface swotTaskDAO = new SwotTaskDAO();
		List<SwotActor> swotActorList = swotTaskDAO.getAllTaskActors(swotTask);

		if (swotActorList != null) {
			for (SwotActor swotActor : swotActorList) {
				listModelActorName.addElement(new SwotCheckboxListItem(swotActor.getActorName()));

				SwotActorPanel actorPanel = new SwotActorPanel(swotActor);
				listModelActorPanel.addElement(actorPanel);
				SwotActorGraphPanel newActorGraphPanel = new SwotActorGraphPanel(swotActor);
				listModelActorGraphPanel.addElement(newActorGraphPanel);

				SwotActorDAOInterface swotActorDAO = new SwotActorDAO();
				List<SwotActorProperty> swotPropertyList = swotActorDAO.getAllActorPropertys(swotActor);

				if (swotPropertyList != null) {
					for (SwotActorProperty swotProperty : swotPropertyList) {
						dtm = (DefaultTableModel) actorPanel.getInsertTable(swotProperty.getPropertyType()).getModel();
						if (dtm.getValueAt(0, 0) == "") {
							dtm.setValueAt(1, 0, 0);
							dtm.setValueAt(swotProperty.getPropertyContent(), 0, 1);
						} else {
							dtm.addRow(new Object[] { dtm.getRowCount() + 1, swotProperty.getPropertyContent() });
						}
					}
				}

				final JInternalFrame jif = new JInternalFrame(swotActor.getActorName(), true, false, true, true);
				jif.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				// 该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果
				jif.setLayout(new BorderLayout());
				jif.setVisible(true);
				jif.setContentPane(actorPanel);
				listModelTableJInternalFrame.addElement(jif);

				final JInternalFrame graphInternalFrame = new JInternalFrame(swotActor.getActorName(), true, false,
						true, true);
				graphInternalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				graphInternalFrame.setLayout(new BorderLayout()); // 该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果
				graphInternalFrame.setContentPane(newActorGraphPanel);
				graphInternalFrame.setVisible(true);
				listModelGraphJInternalFrame.addElement(graphInternalFrame);
			}
		}

		// 监听单击时checkbox框toggle
		listActorName.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// 所单击的列表项目isSelected切换
				SwotCheckboxListItem selecteditem = listActorName.getSelectedValue();
				selecteditem.setSelected(!selecteditem.isSelected);

				// 检测是否选中
				if (selecteditem.isSelected() == true) {
					countSelectedActor++;
				} else {
					countSelectedActor--;
				}

				refreshAll();
				listActorName.repaint();
			}
		});

		textfieldResultInTableView.setText(swotTaskDAO.getTaskResult(swotTask));
		textfieldResultInGraphView.setText(swotTaskDAO.getTaskResult(swotTask));
	}

	/**
	 * 添加参与者
	 */
	public void addActor() {
		// 初始化控件
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
	 * 添加参与者
	 */
	public void deleteActor() {
		swotActorDAO.deleteActorByID(listActorName.getSelectedValue().getActorID());

		int selectedIndex = listActorName.getSelectedIndex();
		listModelActorName.remove(selectedIndex);
		listModelTableJInternalFrame.remove(selectedIndex);
		listModelActorPanel.remove(selectedIndex);

		// 初始化筛选显示参与者面板的个数
		countSelectedActor = 0;

		refreshAll();
	}

	/**
	 * 筛选或者添加删除参与者之后重新排版右侧参与者面板
	 */
	public void refreshAll() {
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
		if (graphDesktop != null) {
			panelLayGraphInGraphView.remove(graphDesktop);
		}

		// 将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后 actorpanel
		// 层次问题【未知缘由】
		for (i = 0; i < listModelTableJInternalFrame.getSize(); i++) {
			try {
				if (listModelTableJInternalFrame.getElementAt(i).isIcon())
					listModelTableJInternalFrame.getElementAt(i).setIcon(false); // 去除最小化
				if (listModelGraphJInternalFrame.getElementAt(i).isIcon())
					listModelGraphJInternalFrame.getElementAt(i).setIcon(false);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}

			try {
				if (listModelTableJInternalFrame.getElementAt(i).isSelected())
					listModelTableJInternalFrame.getElementAt(i).setSelected(false);
				if (listModelGraphJInternalFrame.getElementAt(i).isSelected())
					listModelGraphJInternalFrame.getElementAt(i).setSelected(false);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
		}

		tableDesktop = new JDesktopPane();
		tableDesktop.setLayout(null);

		graphDesktop = new JDesktopPane();
		graphDesktop.setLayout(null);

		// 循环加入一个个面板(未选中的除外)
		for (i = 0, j = 0; i < listModelActorName.getSize(); i++) {
			if (listModelActorName.getElementAt(i).isSelected() == true || countSelectedActor == 0) { // 后者条件为没有实行筛选
				listModelTableJInternalFrame.getElementAt(i).setBounds((j % actorPanelDimension) * actorPanelWidth,
						(j / actorPanelDimension) * actorPanelHeight, actorPanelWidth, actorPanelHeight);
				tableDesktop.add(listModelTableJInternalFrame.getElementAt(i));

				listModelGraphJInternalFrame.getElementAt(i).setBounds((j % actorPanelDimension) * actorPanelWidth,
						(j / actorPanelDimension) * actorPanelHeight, actorPanelWidth, actorPanelHeight);
				graphDesktop.add(listModelGraphJInternalFrame.getElementAt(i));
				listModelActorGraphPanel.getElementAt(i).refreshPanel();
				j++;
			}
		}

		SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
		textfieldResultInTableView.setText(swotTaskDAO.getTaskResult(swotTask));
		splitpaneInTableView.setDividerLocation(splitpaneInTableView.getDividerLocation());

		textfieldResultInGraphView.setText(swotTaskDAO.getTaskResult(swotTask));
		splitpaneInGraphView.setDividerLocation(splitpaneInGraphView.getDividerLocation());

		panelLayTableInTableView.add(tableDesktop);
		panelLayTableInTableView.revalidate();
		panelLayTableInTableView.repaint();

		panelLayGraphInGraphView.add(graphDesktop);
		panelLayGraphInGraphView.revalidate();
		panelLayGraphInGraphView.repaint();
	}

	/*
	 * 综合处理按钮事件
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonAddActor) {
			// 添加参与者弹出面板
			addActor();
		} else if (e.getSource() == buttonDeleteActor) {
			// 删除参与者弹出面板
			deleteActor();
		} else if (e.getSource() == buttonConfirmInAddActor) {
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

			// 添加一个装载actorpanel的JInternalFrame
			final JInternalFrame internalFrame = new JInternalFrame(swotActor.getActorName(), true, false, true, true);
			internalFrame.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			internalFrame.setLayout(new BorderLayout()); // 该布局可以使actorPanelNew随JInternalFrame大小而改变，达到一直是填满整个JInternalFrame效果
			internalFrame.setContentPane(newActorPanel);
			internalFrame.setVisible(true);

			listModelTableJInternalFrame.addElement(internalFrame);

			textfieldActorNameInAddActor.setText("");
			buttonConfirmInAddActor.setText("继续添加");

			refreshAll();
		} else if (e.getSource() == buttonCancelInAddActor) {
			// 添加参与者取消按钮
			frameAddActor.dispose();
		} else if (e.getSource() == buttonSaveInResultInTableView) {
			// 表格界面结果保存
			SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
			swotTask.setMark1(textfieldResultInTableView.getText());
			swotTaskDAO.updateTaskResult(swotTask);
		} else if (e.getSource() == buttonResetInResultInTableView) {
			// 表格界面结果保存
			textfieldResultInTableView.setText("");
		} else if (e.getSource() == buttonSaveInResultInGraphView) {
			// 图形界面结果保存
			SwotTaskDAO swotTaskDAO = new SwotTaskDAO();
			swotTask.setMark1(textfieldResultInGraphView.getText());
			;
			swotTaskDAO.updateTaskResult(swotTask);
		} else if (e.getSource() == buttonResetInResultInGraphView) {
			// 图形界面结果保存
			textfieldResultInGraphView.setText("");
		}
	}
}
