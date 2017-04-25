package cn.whu.forum.analysis.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
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
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

/**
 * @author cree 情景分析法编辑界面
 */
public class ScenarioEditPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	// 装载的情景分析法案例实例
	private ScenarioTask scenarioTask;
	// 情景分析法图形展示面板
	private ScenarioGraphPanel scenarioGraphPanel;

	// 情景分析法的属性数据操作接口
	ScenarioPropertyDAOInterface scenarioPropertyDAO;
	// 情景分析法的逻辑数据操作接口
	ScenarioLogicDAOInterface scenarioLogicDAO;
	// 情景分析法的结果数据操作接口
	ScenarioResultDAOInterface scenarioResultDAO;

	// 顶部面板
	private JPanel panelTop;
	// 底部面板
	private JPanel panelBottom;

	// 工具栏
	JToolBar toolBar;
	// 工具栏中的添加内容按钮
	JButton buttonAddContentInToolBar;
	// 工具栏中的删除内容按钮
	JButton buttonDeleteContentInToolBar;
	// 工具栏中的删除内容按钮
	JButton buttonModifyContentInToolBar;

	// 示例数据
	List<ScenarioProperty> scenarioPropertyList;
	List<ScenarioLogic> scenarioLogicList;
	List<ScenarioResult> scenarioResultList;

	// 选项卡容器
	private JTabbedPane tabbedPane;

	// 表格视图选项卡面板
	private JPanel panelTableViewInTabbedPane;
	// 图形视图选项卡面板
	private JPanel panelGraphViewInTabbedPane;

	// 表格视图选项卡面板中装载表格框架的面板
	private JPanel panelLayJInternalFrameInTableView;
	// 表格视图选项卡面板中装载按钮的面板
	private JPanel panelLayButtonInTableView;

	// 标记当前情景分析法的步骤序号
	private int flagStep;
	// 返回按钮
	private JButton buttonBack;
	// 下一个按钮
	private JButton buttonNext;
	// 保存按钮
	private JButton buttonSave;
	// 示例按钮
	private JButton buttonExample;
	// 示例清空按钮
	private JButton buttonClearExample;

	// 用于装载表格框架的容器
	private JDesktopPane desktop;
	// 焦点表格框架
	private JInternalFrame jifFocus;
	// 关键因素表格框架
	private JInternalFrame jifKeyFactor;
	// 驱动力表格框架
	private JInternalFrame jifDrivingpower;
	// 不确定因素表格框架
	private JInternalFrame jifUncertainties;
	// 发展因素表格框架
	private JInternalFrame jifDevelopment;
	// 结果表格框架
	private JInternalFrame jifResult;
	// 表格框架平均宽度
	private int jifWidth;
	// 表格框架平均高度
	private int jifHeight;

	// 用于操控某表格框架
	private DefaultTableModel dtm;
	// 当前操控的属性类型
	private String propertyType;
	// 当前操控的行号
	private int selectedTableRow;
	// 焦点表格
	private JTable tableFocus;
	// 关键因素表格
	private JTable tableKeyFactor;
	// 驱动力表格
	private JTable tableDrivingpower;
	// 不确定因素表格
	private JTable tableUncertainties;
	// 发展因素表格
	private JTable tableDevelopment;
	// 结果表格
	private JTable tableResult;
	// 焦点表格容器
	private JScrollPane spTableFocus;
	// 关键因素表格容器
	private JScrollPane spTableKeyFactor;
	// 去动力因素表格容器
	private JScrollPane spTableDrivingpower;
	// 不确定因素表格容器
	private JScrollPane spTableUncertainties;
	// 发展因素表格容器
	private JScrollPane spTableDevelopment;
	// 结果表格容器
	private JScrollPane spTableResult;

	// 右键弹出菜单集
	private JPopupMenu popupMenu;
	// 右键添加内容菜单
	private JMenuItem popMenuItemAddContent;
	// 右键删除内容菜单
	private JMenuItem popMenuItemDeleteContent;
	// 右键修改内容菜单
	private JMenuItem popMenuItemModifyContent;

	// 双击表格修改属性内容的框架
	private JFrame frameModifyContent;
	// 双击表格修改属性内容的面板
	private JPanel panelModifyContent;
	// 双击表格修改属性内容的标签面板
	private JPanel panelLayLabelInModifyContent;
	// 双击表格修改属性内容的文本域面板
	private JPanel panelLaytextAreaContentInModifyContent;
	// 双击表格修改属性内容的按钮面板
	private JPanel panelLayButtonInModifyContent;
	// 双击表格修改属性内容的标签
	private JLabel lableContentContentInModifyContent;
	// 双击表格修改属性内容的文本域
	private JTextArea textAreaContentContentInModifyContent;
	// 双击表格修改属性内容的确定按钮
	private JButton buttonConfirmInModifyContent;
	// 双击表格修改属性内容的取消按钮
	private JButton buttonCancelInModifyContent;

	// 表格右键菜单添加属性内容的框架
	private JFrame frameAddContent;
	// 表格右键菜单添加属性内容的面板
	private JPanel panelAddContent;
	// 表格右键菜单添加属性内容的标签面板
	private JPanel panelLayLabelInAddContent;
	// 表格右键菜单添加属性内容的文本域面板
	private JPanel panelLayTextareaInAddContent;
	// 表格右键菜单添加属性内容的按钮面板
	private JPanel panelLayButtonInAddContent;
	// 表格右键菜单添加属性内容的标签
	private JLabel lableContentInAddContent;
	// 表格右键菜单添加属性内容的文本域
	private JTextArea textareaContentInAddContent;
	// 表格右键菜单添加属性内容的确定按钮
	private JButton buttonConfirmInAddContent;
	// 表格右键菜单添加属性内容的取消按钮
	private JButton buttonCancelInAddContent;

	/**
	 * 构造函数：初始化操作接口、控件及布局。
	 * @param scenarioTask
	 *            表示装载的情景分析法案例实例 
	 */
	public ScenarioEditPanel(ScenarioTask scenarioTask) {
		this.scenarioTask = scenarioTask;

		scenarioPropertyDAO = new ScenarioPropertyDAO();
		scenarioLogicDAO = new ScenarioLogicDAO();
		scenarioResultDAO = new ScenarioResultDAO();

		initializeComponent();

		layoutComponent();
	}

	/**
	 * 初始化控件。
	 */
	public void initializeComponent() {
		// JPanel设置
		setLayout(new BorderLayout());

		// 顶部面板
		panelTop = new JPanel();
		panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.X_AXIS));

		// 工具栏
		toolBar = new JToolBar();
		buttonAddContentInToolBar = new JButton("添加内容");
		buttonDeleteContentInToolBar = new JButton("删除内容");
		buttonModifyContentInToolBar = new JButton("修改内容");

		// 工具栏按钮事件注册
		buttonAddContentInToolBar.addActionListener(this);
		buttonDeleteContentInToolBar.addActionListener(this);
		buttonModifyContentInToolBar.addActionListener(this);

		// 底部面板
		panelBottom = new JPanel();
		panelBottom.setLayout(new BorderLayout());

		// 选项卡面板
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(StaticConfig.FONT_EDITVIEW_LABEL);
		// 点击切换选项卡时刷新图形界面
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
				int selectedIndex = tabbedPane.getSelectedIndex();

				if (selectedIndex == 1)
					refreshPanelGraphViewInTabbedPane();
			}
		});

		// 选项卡面板初始化
		panelTableViewInTabbedPane = new JPanel();
		panelTableViewInTabbedPane.setLayout(new BorderLayout());
		panelGraphViewInTabbedPane = new JPanel();
		panelGraphViewInTabbedPane.setLayout(new BorderLayout(0, 0));

		// 表格选项卡内面板初始化
		panelLayJInternalFrameInTableView = new JPanel();
		panelLayButtonInTableView = new JPanel();
		panelLayJInternalFrameInTableView.setLayout(new BorderLayout());

		panelLayJInternalFrameInTableView.addComponentListener(new ComponentListener() {
			// 由于panel在布局初始化时没有大小，固获取其大小要放在此处监听
			// 并同时把6个jif设置大小位置后放入该panel
			@Override
			public void componentResized(ComponentEvent e) {
				jifWidth = panelLayJInternalFrameInTableView.getSize().width;
				jifHeight = panelLayJInternalFrameInTableView.getSize().height / 6 - 5;

				refreshPanelTableViewInTabbedPane();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}
		});

		// 表格框架初始化
		jifFocus = initializeJInternalFrame("决策焦点");
		jifKeyFactor = initializeJInternalFrame("关键因素");
		jifDrivingpower = initializeJInternalFrame("驱动力量");
		jifUncertainties = initializeJInternalFrame("不确定因素");
		jifDevelopment = initializeJInternalFrame("发展逻辑");
		jifResult = initializeJInternalFrame("分析结果");

		// 表格初始化
		tableFocus = initializeTable("focus");
		tableKeyFactor = initializeTable("keyfactor");
		tableDrivingpower = initializeTable("drivingpower");
		tableUncertainties = initializeTable("uncertainties");
		tableDevelopment = initializeTable("development");
		tableResult = initializeTable("result");

		// 表格容器初始化
		spTableFocus = new JScrollPane(tableFocus);
		spTableKeyFactor = new JScrollPane(tableKeyFactor);
		spTableDrivingpower = new JScrollPane(tableDrivingpower);
		spTableUncertainties = new JScrollPane(tableUncertainties);
		spTableDevelopment = new JScrollPane(tableDevelopment);
		spTableResult = new JScrollPane(tableResult);

		// 右键菜单初始化
		popupMenu = new JPopupMenu();
		popMenuItemAddContent = new JMenuItem("添加");
		popMenuItemAddContent.setFont(StaticConfig.FONT_TABLE_RIGHT_CLICK_MENU);
		popMenuItemDeleteContent = new JMenuItem("删除");
		popMenuItemDeleteContent.setFont(StaticConfig.FONT_TABLE_RIGHT_CLICK_MENU);
		popMenuItemModifyContent = new JMenuItem("修改");
		popMenuItemModifyContent.setFont(StaticConfig.FONT_TABLE_RIGHT_CLICK_MENU);
		popupMenu.add(popMenuItemAddContent);
		popupMenu.add(popMenuItemDeleteContent);
		popupMenu.add(popMenuItemModifyContent);

		// 右键菜单事件注册
		popMenuItemAddContent.addActionListener(this);
		popMenuItemDeleteContent.addActionListener(this);
		popMenuItemModifyContent.addActionListener(this);

		// 步骤序号初始化
		flagStep = 1;
		// 返回按钮初始化
		buttonBack = new JButton("返回");
		buttonBack.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		// 下一步按钮初始化
		buttonNext = new JButton("开始编辑");
		buttonNext.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		// 保存按钮初始化
		buttonSave = new JButton("保存");
		buttonSave.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		// 示例按钮初始化
		buttonExample = new JButton("示例");
		buttonExample.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		// 示例清空按钮初始化
		buttonClearExample = new JButton("示例清空");
		buttonClearExample.setFont(StaticConfig.FONT_EDITVIEW_BUTTON);
		// 按钮事件注册
		buttonBack.addActionListener(this);
		buttonNext.addActionListener(this);
		buttonSave.addActionListener(this);
		buttonExample.addActionListener(this);
		buttonClearExample.addActionListener(this);

		// 示例内容初始化
		scenarioPropertyList = new ArrayList<ScenarioProperty>();
		scenarioLogicList = new ArrayList<ScenarioLogic>();
		scenarioResultList = new ArrayList<ScenarioResult>();

		// 图形视图初始化
		scenarioGraphPanel = new ScenarioGraphPanel(scenarioTask);
	}

	/**
	 * 初始化布局
	 */
	public void layoutComponent() {
		// 顶部面板
		panelTop.add(toolBar);

		toolBar.add(buttonAddContentInToolBar);
		toolBar.addSeparator();
		toolBar.add(buttonDeleteContentInToolBar);
		toolBar.addSeparator();
		toolBar.add(buttonModifyContentInToolBar);

		jifFocus.setContentPane(spTableFocus);
		jifKeyFactor.setContentPane(spTableKeyFactor);
		jifDrivingpower.setContentPane(spTableDrivingpower);
		jifUncertainties.setContentPane(spTableUncertainties);
		jifDevelopment.setContentPane(spTableDevelopment);
		jifResult.setContentPane(spTableResult);

		panelLayButtonInTableView.add(buttonExample);
		panelLayButtonInTableView.add(buttonClearExample);
		panelLayButtonInTableView.add(buttonNext);

		panelTableViewInTabbedPane.add(panelLayJInternalFrameInTableView, BorderLayout.CENTER);
		panelTableViewInTabbedPane.add(panelLayButtonInTableView, BorderLayout.SOUTH);

		tabbedPane.add(panelTableViewInTabbedPane, "表格");
		tabbedPane.add(panelGraphViewInTabbedPane, "图形");

		panelBottom.add(tabbedPane);

		add(panelTop, BorderLayout.NORTH);
		add(panelBottom, BorderLayout.CENTER);
	}

	/**
	 * @param tableType
	 *            表格类型
	 * @return 表格框架 初始化表格框架
	 */
	public JInternalFrame initializeJInternalFrame(String tableType) {
		final JInternalFrame jif = new JInternalFrame(tableType, true, false, true, true);

		jif.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		jif.setLayout(new BorderLayout());
		jif.setVisible(true);

		// 获取当前被选中的JInternalFrame的index，以便于toolbar定位（MouseListener监听不到）
		jif.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameActivated(InternalFrameEvent e) {
				propertyType = jif.getTitle();
			}
		});

		return jif;
	}

	/**
	 * @param tableType
	 *            表格类型
	 * @return 表格 初始化表格
	 */
	public JTable initializeTable(final String tableType) {
		dtm = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public Class<String> getColumnClass(int columnIndex) {
				return String.class;
			}

			// 不可编辑，变成双击弹出修改框
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		dtm.setDataVector(new Object[][] { { "", "", -1 } }, new Object[] { "编号", "内容", "ID" });

		final JTable table = new JTable(dtm);
		table.getTableHeader().setFont(StaticConfig.FONT_TABLEHEADER);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setMaxWidth(40);
		columnModel.getColumn(0).setPreferredWidth(40);
		// 第三列为隐藏列，存储属性ID。获得remove掉的列的data必须使用TableModel
		columnModel.removeColumn(columnModel.getColumn(2));
		// 渲染成可换行
		table.setDefaultRenderer(String.class, new MultiLineTableCellRender());

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// getSelectedRow()对于右键无效，值为-1
				selectedTableRow = table.rowAtPoint(e.getPoint());

				table.changeSelection(table.getSelectedRow(), table.getSelectedColumn(), true, false);
				table.requestFocus();

				// 标记当前选中的属性表格
				propertyType = tableType;

				// 监听右键菜单
				if (e.getButton() == MouseEvent.BUTTON3) {
					// 在鼠标点击位置弹出右键菜单
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				} else if (e.getClickCount() == 2) {
					// 监听双击菜单
					if (getSelectedTable().getValueAt(0, 0) != "")
						modifyContent();
					else
						JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		return table;
	}

	/**
	 * 可同时对总框架改变大小时做出内部jif大小相应调整
	 */
	public void refreshPanelTableViewInTabbedPane() {

		// 清空原有的参与者面板
		if (desktop != null) {
			panelLayJInternalFrameInTableView.remove(desktop);
		}

		// 将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后 actorpanel 层次问题
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
		jifDrivingpower.setBounds(0, jifHeight * 2, jifWidth, jifHeight);
		jifUncertainties.setBounds(0, jifHeight * 3, jifWidth, jifHeight);
		jifDevelopment.setBounds(0, jifHeight * 4, jifWidth, jifHeight);
		jifResult.setBounds(0, jifHeight * 5, jifWidth, jifHeight);

		desktop.add(jifFocus);
		desktop.add(jifKeyFactor);
		desktop.add(jifDrivingpower);
		desktop.add(jifUncertainties);
		desktop.add(jifDevelopment);
		desktop.add(jifResult);

		panelLayJInternalFrameInTableView.add(desktop);
	}

	/**
	 * 将所有JInternalFrame设置为为无最小化以及未选中状态。解决在添加/删除/筛选参与者后 actorpanel 层次问题
	 * 
	 * @param jif
	 *            表格框架
	 */
	public void refreshJInternalFrameStatus(JInternalFrame jif) {
		jif.pack();
		try {
			if (jif.isIcon())
				jif.setIcon(false);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		try {
			if (jif.isSelected())
				jif.setSelected(false);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 刷新图形视图界面
	 */
	public void refreshPanelGraphViewInTabbedPane() {
		if (scenarioGraphPanel != null) {
			panelGraphViewInTabbedPane.remove(scenarioGraphPanel);
			scenarioGraphPanel = new ScenarioGraphPanel(scenarioTask, scenarioPropertyList, scenarioLogicList,
					scenarioResultList); // 【示例修改处】
		}

		panelGraphViewInTabbedPane.add(scenarioGraphPanel);
		panelGraphViewInTabbedPane.revalidate();
		panelGraphViewInTabbedPane.repaint();
	}

	/**
	 * 添加内容
	 */
	public void addContent() {
		// 初始化框架
		frameAddContent = new JFrame("添加属性");
		frameAddContent.setSize(500, 400);
		frameAddContent.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameAddContent.setLocationRelativeTo(null);
		frameAddContent.getContentPane().setLayout(new BorderLayout());

		// 初始化面板
		panelAddContent = new JPanel();
		panelLayLabelInAddContent = new JPanel();
		panelLayTextareaInAddContent = new JPanel();
		panelLayButtonInAddContent = new JPanel();
		panelAddContent.setLayout(new BoxLayout(panelAddContent, BoxLayout.Y_AXIS));
		panelLayTextareaInAddContent.setLayout(new BoxLayout(panelLayTextareaInAddContent, BoxLayout.X_AXIS));
		panelLayButtonInAddContent.setLayout(new BoxLayout(panelLayButtonInAddContent, BoxLayout.X_AXIS));

		// 初始化标签
		lableContentInAddContent = new JLabel("内容");
		lableContentInAddContent.setFont(StaticConfig.FONT_DIALOG_LABEL);

		// 初始化文本域
		textareaContentInAddContent = new JTextArea(5, 5);
		textareaContentInAddContent.setFont(StaticConfig.FONT_DIALOG_TEXTAREA);
		textareaContentInAddContent.setLineWrap(true); // 支持自动换行
		textareaContentInAddContent.setBorder(BorderFactory.createTitledBorder(""));

		// 初始化按钮
		buttonConfirmInAddContent = new JButton("确定");
		buttonConfirmInAddContent.setFont(StaticConfig.FONT_DIALOG_BUTTON);
		buttonCancelInAddContent = new JButton("取消");
		buttonCancelInAddContent.setFont(StaticConfig.FONT_DIALOG_BUTTON);

		// 初始化按钮注册事件
		buttonConfirmInAddContent.addActionListener(this);
		buttonCancelInAddContent.addActionListener(this);

		// 初始化布局
		panelLayLabelInAddContent.add(lableContentInAddContent);
		panelLayTextareaInAddContent.add(Box.createHorizontalStrut(20));
		panelLayTextareaInAddContent.add(textareaContentInAddContent);
		panelLayTextareaInAddContent.add(Box.createHorizontalStrut(20));
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
	}

	/**
	 * 删除内容
	 */
	public void deleteContent() {
		// 确定是哪个属性
		if (getSelectedTable().getValueAt(0, 0) != "") {
			if (propertyType.equals("focus") || propertyType.equals("keyfactor") || propertyType.equals("drivingpower")
					|| propertyType.equals("uncertainties")) {
				ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
				scenarioPropertyDAO
						.deletePropertyByID((int) (getSelectedTable().getModel().getValueAt(selectedTableRow, 2))); // 获得remove掉的列的data必须使用TableModel
			} else if (propertyType.equals("development")) {
				ScenarioLogicDAOInterface scenarioPropertyDAO = new ScenarioLogicDAO();
				scenarioPropertyDAO
						.deleteLogicByID((int) (getSelectedTable().getModel().getValueAt(selectedTableRow, 2))); // 获得remove掉的列的data必须使用TableModel
			} else if (propertyType.equals("result")) {
				ScenarioResultDAOInterface scenarioPropertyDAO = new ScenarioResultDAO();
				scenarioPropertyDAO
						.deleteResultByID((int) (getSelectedTable().getModel().getValueAt(selectedTableRow, 2))); // 获得remove掉的列的data必须使用TableModel
			}

			dtm = (DefaultTableModel) getSelectedTable().getModel();
			dtm.removeRow(selectedTableRow);

			if (getSelectedTable().getRowCount() == 0) {
				dtm.addRow(new Object[] { "", "", -1 });
			} else {
				for (int i = 0; i < dtm.getRowCount(); i++) {
					dtm.setValueAt(i + 1, i, 0);
				}
			}
			// 属性无内容
		} else {
			JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除内容操作", "", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 修改属性
	 */
	public void modifyContent() {
		// 初始化框架
		frameModifyContent = new JFrame("修改属性");
		frameModifyContent.setSize(500, 400);
		frameModifyContent.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameModifyContent.setLocationRelativeTo(null);

		// 初始化面板
		panelModifyContent = new JPanel();
		panelModifyContent = new JPanel();
		panelLayLabelInModifyContent = new JPanel();
		panelLaytextAreaContentInModifyContent = new JPanel();
		panelLayButtonInModifyContent = new JPanel();
		panelModifyContent.setLayout(new BoxLayout(panelModifyContent, BoxLayout.Y_AXIS));
		panelLaytextAreaContentInModifyContent
				.setLayout(new BoxLayout(panelLaytextAreaContentInModifyContent, BoxLayout.X_AXIS));
		panelLayButtonInModifyContent.setLayout(new BoxLayout(panelLayButtonInModifyContent, BoxLayout.X_AXIS));

		// 初始化标签
		lableContentContentInModifyContent = new JLabel("内容");
		lableContentContentInModifyContent.setFont(StaticConfig.FONT_DIALOG_LABEL);

		// 初始化文本域
		textAreaContentContentInModifyContent = new JTextArea(5, 5);
		textAreaContentContentInModifyContent.setFont(StaticConfig.FONT_DIALOG_TEXTAREA);
		textAreaContentContentInModifyContent.setText((String) getSelectedTable().getValueAt(selectedTableRow, 1));
		// 支持自动换行
		textAreaContentContentInModifyContent.setLineWrap(true);
		textAreaContentContentInModifyContent.setBorder(BorderFactory.createTitledBorder(""));

		// 初始化按钮
		buttonConfirmInModifyContent = new JButton("确定");
		buttonConfirmInModifyContent.setFont(StaticConfig.FONT_DIALOG_BUTTON);
		buttonCancelInModifyContent = new JButton("取消");
		buttonCancelInModifyContent.setFont(StaticConfig.FONT_DIALOG_BUTTON);

		// 初始化按钮注册事件
		buttonConfirmInModifyContent.addActionListener(this);
		buttonCancelInModifyContent.addActionListener(this);

		// 初始化布局
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
	}

	/**
	 * @return 返回当前选中的table
	 */
	public JTable getSelectedTable() {

		if (propertyType.equals("focus")) {
			return tableFocus;
		} else if (propertyType.equals("keyfactor")) {
			return tableKeyFactor;
		} else if (propertyType.equals("drivingpower")) {
			return tableDrivingpower;
		} else if (propertyType.equals("uncertainties")) {
			return tableUncertainties;
		} else if (propertyType.equals("development")) {
			return tableDevelopment;
		} else if (propertyType.equals("result")) {
			return tableResult;
		}

		return null;
	}

	/*
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 综合处理按钮事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// 工具栏添加按钮或者右键菜单添加按钮
		if (e.getSource() == buttonAddContentInToolBar || e.getSource() == popMenuItemAddContent) {
			addContent();
		}
		// 工具栏删除按钮或者右键菜单删除按钮
		else if (e.getSource() == buttonDeleteContentInToolBar || e.getSource() == popMenuItemDeleteContent) {
			deleteContent();
		}
		// 工具栏修改按钮或者右键菜单修改按钮
		else if (e.getSource() == buttonModifyContentInToolBar || e.getSource() == popMenuItemModifyContent) {
			if (getSelectedTable().getValueAt(0, 0) != "")
				modifyContent();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "", JOptionPane.INFORMATION_MESSAGE);
		}
		// 上一步按钮
		else if (e.getSource() == buttonBack) {
			if (flagStep == 2) {
				panelLayButtonInTableView.remove(buttonBack);
				buttonNext.setText("开始编辑");
				panelLayButtonInTableView.revalidate();
				panelLayButtonInTableView.repaint();

				try {
					jifFocus.setMaximum(false);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep--;
			} else if (flagStep == 3) {
				buttonBack.setText("返回");
				buttonNext.setText("编辑关键因素");
				try {
					jifKeyFactor.setMaximum(false);
					jifFocus.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep--;
			} else if (flagStep == 4) {
				buttonBack.setText("编辑决策焦点");
				buttonNext.setText("编辑驱动力量");
				try {
					jifDrivingpower.setMaximum(false);
					jifKeyFactor.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep--;
			} else if (flagStep == 5) {
				buttonBack.setText("编辑关键因素");
				buttonNext.setText("编辑不确定因素");
				try {
					jifUncertainties.setMaximum(false);
					jifDrivingpower.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep--;
			} else if (flagStep == 6) {
				buttonBack.setText("编辑驱动力量");
				buttonNext.setText("编辑发展逻辑");
				try {
					jifDevelopment.setMaximum(false);
					jifUncertainties.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep--;
			} else if (flagStep == 7) {
				buttonBack.setText("编辑不确定因素");
				buttonNext.setText("编辑分析结果");
				try {
					jifResult.setMaximum(false);
					jifDevelopment.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep--;
			} else if (flagStep == 8) {
				panelLayButtonInTableView.remove(buttonSave);
				panelLayButtonInTableView.add(buttonNext);
				buttonBack.setText("编辑发展逻辑");
				buttonNext.setText("结束编辑");
				panelLayButtonInTableView.revalidate();
				panelLayButtonInTableView.repaint();

				try {
					jifResult.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep--;
			}
		}
		// 下一步按钮
		else if (e.getSource() == buttonNext) {
			if (flagStep == 1) {
				panelLayButtonInTableView.remove(buttonNext);
				panelLayButtonInTableView.add(buttonBack);
				buttonNext.setText("编辑关键因素");
				panelLayButtonInTableView.add(buttonNext);
				panelLayButtonInTableView.revalidate();
				panelLayButtonInTableView.repaint();

				try {
					jifFocus.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep++;
			} else if (flagStep == 2) {
				buttonBack.setText("编辑决策焦点");
				buttonNext.setText("编辑驱动力量");
				try {
					jifFocus.setMaximum(false);
					jifKeyFactor.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep++;
			} else if (flagStep == 3) {
				buttonBack.setText("编辑关键因素");
				buttonNext.setText("编辑不确定因素");
				try {
					jifKeyFactor.setMaximum(false);
					jifDrivingpower.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep++;
			} else if (flagStep == 4) {
				buttonBack.setText("编辑驱动力量");
				buttonNext.setText("编辑发展逻辑");
				try {
					jifDrivingpower.setMaximum(false);
					jifUncertainties.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep++;
			} else if (flagStep == 5) {
				buttonBack.setText("编辑不确定因素");
				buttonNext.setText("编辑分析结果");
				try {
					jifUncertainties.setMaximum(false);
					jifDevelopment.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep++;
			} else if (flagStep == 6) {
				buttonBack.setText("编辑发展逻辑");
				buttonNext.setText("结束编辑");
				try {
					jifDevelopment.setMaximum(false);
					jifResult.setMaximum(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep++;
			} else if (flagStep == 7) {
				buttonBack.setText("编辑分析结果");
				panelLayButtonInTableView.remove(buttonNext);
				panelLayButtonInTableView.add(buttonSave);
				panelLayButtonInTableView.revalidate();
				panelLayButtonInTableView.repaint();

				try {
					jifResult.setMaximum(false);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				flagStep++;
			}
		}
		// 保存按钮
		else if (e.getSource() == buttonSave) {
			JOptionPane.showMessageDialog(null, "保存成功", "", JOptionPane.INFORMATION_MESSAGE);
		}
		// 修改内容窗口的确定修改按钮
		else if (e.getSource() == buttonConfirmInModifyContent) {
			if (propertyType.equals("focus") || propertyType.equals("keyfactor") || propertyType.equals("drivingpower")
					|| propertyType.equals("uncertainties")) {
				ScenarioProperty scenarioProperty = new ScenarioProperty();
				scenarioProperty.setPropertyID((int) (getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));
				scenarioProperty.setPropertyContent(textAreaContentContentInModifyContent.getText());

				ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
				scenarioPropertyDAO.updateProperty(scenarioProperty);
			} else if (propertyType.equals("development")) {
				ScenarioLogic scenarioLogic = new ScenarioLogic();
				scenarioLogic.setLogicID((int) (getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));

				ScenarioLogicDAOInterface scenarioLogicDAO = new ScenarioLogicDAO();
				scenarioLogicDAO.updateLogic(scenarioLogic);
			} else if (propertyType.equals("result")) {
				ScenarioResult scenarioResult = new ScenarioResult();
				scenarioResult.setResultID((int) (getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));

				ScenarioResultDAOInterface scenarioResultDAO = new ScenarioResultDAO();
				scenarioResultDAO.updateResult(scenarioResult);
			}
			getSelectedTable().setValueAt(textAreaContentContentInModifyContent.getText(), selectedTableRow, 1);
		}
		// 修改内容窗口的取消修改按钮
		else if (e.getSource() == buttonCancelInModifyContent) {
			frameModifyContent.dispose();
		}
		// 添加内容窗口的确定添加按钮
		else if (e.getSource() == buttonConfirmInAddContent) {
			if (propertyType.equals("focus") || propertyType.equals("keyfactor") || propertyType.equals("drivingpower")
					|| propertyType.equals("uncertainties")) {
				ScenarioProperty scenarioProperty = new ScenarioProperty();
				scenarioProperty.setPropertyType(propertyType);
				scenarioProperty.setPropertyContent(textareaContentInAddContent.getText());

				ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
				scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty);

				dtm = (DefaultTableModel) getSelectedTable().getModel();
				if (dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddContent.getText(), 0, 1);
					dtm.setValueAt(scenarioProperty.getPropertyID(), 0, 2);
				} else {
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, textareaContentInAddContent.getText(),
							scenarioProperty.getPropertyID() });
				}
			} else if (propertyType.equals("development")) {
				ScenarioLogic scenarioLogic = new ScenarioLogic();
				scenarioLogic.setLogicContent(textareaContentInAddContent.getText());

				ScenarioLogicDAOInterface scenarioLogicDAO = new ScenarioLogicDAO();
				scenarioLogicDAO.addLogic(scenarioTask, scenarioLogic);

				dtm = (DefaultTableModel) getSelectedTable().getModel();
				if (dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddContent.getText(), 0, 1);
					dtm.setValueAt(scenarioLogic.getLogicID(), 0, 2);
				} else {
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, textareaContentInAddContent.getText(),
							scenarioLogic.getLogicID() });
				}
			} else if (propertyType.equals("result")) {
				ScenarioResult scenarioResult = new ScenarioResult();
				scenarioResult.setResultContent(textareaContentInAddContent.getText());

				ScenarioResultDAOInterface scenarioResultDAO = new ScenarioResultDAO();
				scenarioResultDAO.addResult(scenarioTask, scenarioResult);

				dtm = (DefaultTableModel) getSelectedTable().getModel();
				if (dtm.getValueAt(0, 0) == "") {
					dtm.setValueAt(1, 0, 0);
					dtm.setValueAt(textareaContentInAddContent.getText(), 0, 1);
					dtm.setValueAt(scenarioResult.getResultID(), 0, 2);
				} else {
					dtm.addRow(new Object[] { dtm.getRowCount() + 1, textareaContentInAddContent.getText(),
							scenarioResult.getResultID() });
				}
			}

			buttonConfirmInAddContent.setText("继续添加");
			textareaContentInAddContent.setText("");
		}
		// 添加内容窗口的取消添加按钮
		else if (e.getSource() == buttonCancelInAddContent) {
			frameAddContent.dispose();
			// 示例添加按钮
		} else if (e.getSource() == buttonExample) {
			scenarioPropertyList = new ArrayList<ScenarioProperty>();
			scenarioLogicList = new ArrayList<ScenarioLogic>();
			scenarioResultList = new ArrayList<ScenarioResult>();

			// 第一个测试属性 focus
			dtm = (DefaultTableModel) tableFocus.getModel();
			dtm.setValueAt(1, 0, 0);
			dtm.setValueAt("污染和生态破坏", 0, 1);
			// 第一行比较特殊
			dtm.setValueAt(1, 0, 2);
			ScenarioProperty scenarioProperty0 = new ScenarioProperty();
			scenarioProperty0.setPropertyType("focus");
			scenarioProperty0.setPropertyContent("污染和生态破坏");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty0);

			// 第二个测试属性 keyFactor
			dtm = (DefaultTableModel) tableKeyFactor.getModel();
			dtm.setValueAt(1, 0, 0);
			dtm.setValueAt("经济因素", 0, 1);
			dtm.setValueAt(1, 0, 2);
			dtm.addRow(new Object[] { dtm.getRowCount() + 1, "政府政策因素", 2 });
			ScenarioProperty scenarioProperty2 = new ScenarioProperty();
			scenarioProperty2.setPropertyType("keyfactor");
			scenarioProperty2.setPropertyContent("经济因素");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty2);
			ScenarioProperty scenarioProperty3 = new ScenarioProperty();
			scenarioProperty3.setPropertyType("keyfactor");
			scenarioProperty3.setPropertyContent("政府政策因素");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty3);

			// 第三个测试属性 drivingpower
			dtm = (DefaultTableModel) tableDrivingpower.getModel();
			dtm.setValueAt(1, 0, 0);
			dtm.setValueAt("经济发展（含财政负担）", 0, 1);
			dtm.setValueAt(1, 0, 2);
			dtm.addRow(new Object[] { dtm.getRowCount() + 1, "政府相关计划与政策", 2 });
			dtm.addRow(new Object[] { dtm.getRowCount() + 1, "工程技术", 3 });
			dtm.addRow(new Object[] { dtm.getRowCount() + 1, "外来影响", 4 });
			ScenarioProperty scenarioProperty6 = new ScenarioProperty();
			scenarioProperty6.setPropertyType("drivingpower");
			scenarioProperty6.setPropertyContent("经济发展（含财政负担）");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty6);
			ScenarioProperty scenarioProperty7 = new ScenarioProperty();
			scenarioProperty7.setPropertyType("drivingpower");
			scenarioProperty7.setPropertyContent("政府相关计划与政策");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty7);
			ScenarioProperty scenarioProperty8 = new ScenarioProperty();
			scenarioProperty8.setPropertyType("drivingpower");
			scenarioProperty8.setPropertyContent("工程技术");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty8);
			ScenarioProperty scenarioProperty9 = new ScenarioProperty();
			scenarioProperty9.setPropertyType("drivingpower");
			scenarioProperty9.setPropertyContent("外来影响");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty9);

			// 第四个测试属性 uncertainties
			dtm = (DefaultTableModel) tableUncertainties.getModel();
			dtm.setValueAt(1, 0, 0);
			dtm.setValueAt("社会发展", 0, 1);
			dtm.setValueAt(1, 0, 2);
			dtm.addRow(new Object[] { dtm.getRowCount() + 1, "外来影响", 2 });
			ScenarioProperty scenarioProperty10 = new ScenarioProperty();
			scenarioProperty10.setPropertyType("uncertainties");
			scenarioProperty10.setPropertyContent("社会发展");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty10);
			ScenarioProperty scenarioProperty11 = new ScenarioProperty();
			scenarioProperty11.setPropertyType("uncertainties");
			scenarioProperty11.setPropertyContent("外来影响");
			scenarioPropertyDAO.addProperty(scenarioTask, scenarioProperty11);

			scenarioPropertyList.add(scenarioProperty0);
			scenarioPropertyList.add(scenarioProperty2);
			scenarioPropertyList.add(scenarioProperty3);
			scenarioPropertyList.add(scenarioProperty6);
			scenarioPropertyList.add(scenarioProperty7);
			scenarioPropertyList.add(scenarioProperty8);
			scenarioPropertyList.add(scenarioProperty9);
			scenarioPropertyList.add(scenarioProperty10);
			scenarioPropertyList.add(scenarioProperty11);

			// 逻辑内容
			dtm = (DefaultTableModel) tableDevelopment.getModel();
			dtm.setValueAt(1, 0, 0);
			dtm.setValueAt("社会经济低速发展，强的政府决策", 0, 1);
			dtm.setValueAt(1, 0, 2);
			dtm.addRow(new Object[] { dtm.getRowCount() + 1, "社会经济低速发展，弱的政府决策", 2 });
			dtm.addRow(new Object[] { dtm.getRowCount() + 1, "社会经济高速发展，强的政府决策", 3 });
			dtm.addRow(new Object[] { dtm.getRowCount() + 1, "社会经济高速发展，弱的政府决策", 4 });
			ScenarioLogic scenarioLogic0 = new ScenarioLogic();
			scenarioLogic0.setLogicContent("社会经济低速发展，强的政府决策");
			scenarioLogicDAO.addLogic(scenarioTask, scenarioLogic0);
			ScenarioLogic scenarioLogic1 = new ScenarioLogic();
			scenarioLogic1.setLogicContent("社会经济低速发展，弱的政府决策");
			scenarioLogicDAO.addLogic(scenarioTask, scenarioLogic1);
			ScenarioLogic scenarioLogic2 = new ScenarioLogic();
			scenarioLogic2.setLogicContent("社会经济高速发展，强的政府决策");
			scenarioLogicDAO.addLogic(scenarioTask, scenarioLogic2);
			ScenarioLogic scenarioLogic3 = new ScenarioLogic();
			scenarioLogic3.setLogicContent("社会经济高速发展，弱的政府决策");
			scenarioLogicDAO.addLogic(scenarioTask, scenarioLogic3);

			scenarioLogicList.add(scenarioLogic0);
			scenarioLogicList.add(scenarioLogic1);
			scenarioLogicList.add(scenarioLogic2);
			scenarioLogicList.add(scenarioLogic3);

			// 结果内容
			dtm = (DefaultTableModel) tableResult.getModel();
			dtm.setValueAt(1, 0, 0);
			dtm.setValueAt(
					"流域发展会给邛海带来新的压力, 资金短缺和政府决策是制约\n邛海流域未来生态环境变化的重要因素。尽管国家和地方各级政府会\n给予一定的资金扶持, 但是资金短缺的问题仍然十分突出。\n因此, 需要对工程方案的优先次序进行排列和组合。根据邛海流域 IFMOP 模型\n的优化结果和项目效益综合分析可见, 污染源治理、生态恢复、生态移民、绿色体系建设工程、\n湖岸界桩控制区拆迁改造工程、湖滨区生态景观恢复与改造、森林生态建设、水土流失及泥石流防\n治工程以及加强对旅游资源的开发和管制, 是邛海流域近中期的优\n先项目, 而水资源开发和底泥疏浚等, 则是在资金充裕情况下的远期备选项目。",
					0, 1);
			dtm.setValueAt(1, 0, 2);

			ScenarioResult scenarioResult0 = new ScenarioResult();
			scenarioResult0.setResultContent(
					"流域发展会给邛海带来新的压力, 资金短缺和政府决策是制约\n邛海流域未来生态环境变化的重要因素。尽管国家和地方各级政府会\n给予一定的资金扶持, 但是资金短缺的问题仍然十分突出。\n因此, 需要对工程方案的优先次序进行排列和组合。根据邛海流域 IFMOP 模型\n的优化结果和项目效益综合分析可见, 污染源治理、生态恢复、生态移民、绿色体系建设工程、\n湖岸界桩控制区拆迁改造工程、湖滨区生态景观恢复与改造、森林生态建设、水土流失及泥石流防\n治工程以及加强对旅游资源的开发和管制, 是邛海流域近中期的优\n先项目, 而水资源开发和底泥疏浚等, 则是在资金充裕情况下的远期备选项目。");
			scenarioResultDAO.addResult(scenarioTask, scenarioResult0);

			scenarioResultList.add(scenarioResult0);
			// 示例清空
		} else if (e.getSource() == buttonClearExample) {
			dtm = (DefaultTableModel) tableFocus.getModel();
			dtm.setRowCount(0);
			dtm.addRow(new Object[] { "", "", -1 });
			dtm = (DefaultTableModel) tableKeyFactor.getModel();
			dtm.setRowCount(0);
			dtm.addRow(new Object[] { "", "", -1 });
			dtm = (DefaultTableModel) tableDrivingpower.getModel();
			dtm.setRowCount(0);
			dtm.addRow(new Object[] { "", "", -1 });
			dtm = (DefaultTableModel) tableUncertainties.getModel();
			dtm.setRowCount(0);
			dtm.addRow(new Object[] { "", "", -1 });
			dtm = (DefaultTableModel) tableDevelopment.getModel();
			dtm.setRowCount(0);
			dtm.addRow(new Object[] { "", "", -1 });
			dtm = (DefaultTableModel) tableResult.getModel();
			dtm.setRowCount(0);
			dtm.addRow(new Object[] { "", "", -1 });

			scenarioPropertyList.clear();
			scenarioPropertyDAO.deleteAllProperties();
			scenarioLogicList.clear();
			scenarioLogicDAO.deleteAllLogics();
			scenarioResultList.clear();
			scenarioResultDAO.deleteAllResults();
			refreshPanelGraphViewInTabbedPane();
		}
	}
}
