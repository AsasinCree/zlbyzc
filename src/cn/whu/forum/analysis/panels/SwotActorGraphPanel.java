package cn.whu.forum.analysis.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.liukan.mgraph.mgraphx;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.services.SwotActorDAO;
import cn.whu.forum.analysis.services.interfaces.SwotActorDAOInterface;

/**
 * SWOT法图形面板
 * 
 * @author asasi
 *
 */
public class SwotActorGraphPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1;
	private SwotActor swotActor;

	// panel初始化会刷新两次，第二次才是正确的大小
	private int flag = 0;
	private static mgraphx graph;
	private int nodeWidth;
	private int nodeHeight;

	// 各属性个数的序号
	private int countAdvantage;
	private int countChance;
	private int countDisadvantage;
	private int countThreat;
	// 最大的行数
	private int countMax;

	// 面板高度及宽度
	private int panelWidth;
	private int panelHeight;

	// 显示标签节点
	private Object nodeAdvantage;
	private Object nodeChance;
	private Object nodeDisadvantage;
	private Object nodeThreat;
	private Object nodeActorName;
	private List<SwotActorProperty> swotPropertyList;
	// 判断是否示例
	private int flagExample;
	// 当前操作属性标志
	private int propertyTypeFlag;

	// 鍙抽敭鑿滃崟
	private JPopupMenu popupMenu;
	private JMenuItem popmenuItemAddProperty;
	private JMenuItem popmenuItemDeleteProperty;
	private JMenuItem popmenuItemModifyProperty;

	// 表格右键菜单添加属性内容面板
	private JFrame frameAddProperty;
	private JPanel panelAddProperty;
	private JPanel panelLayLabelInAddProperty;
	private JPanel panelLayTextareaInAddProperty;
	private JPanel panelLayButtonInAddProperty;
	private JLabel lableContentInAddProperty;
	private JTextArea textareaContentInAddProperty;
	private JButton buttonConfirmInAddProperty;
	private JButton buttonCancelInAddProperty;

	/**
	 * @param scenarioTask
	 *            表示依附的情景分析法任务实例 构造函数：初始化操作接口、控件及布局。
	 */
	public SwotActorGraphPanel(SwotActor swotActor) {
		this.swotActor = swotActor;
		flagExample = 0;
		initializeComponent();

		layoutComponent();
	}

	/**
	 * @param scenarioTask
	 * @param scenarioPropertyList
	 * @param scenarioLogicList
	 * @param scenarioResultList
	 *            附带构造函数：初始化操作接口、控件及布局。
	 */
	public SwotActorGraphPanel(SwotActor swotActor, List<SwotActorProperty> swotPropertyList) {
		this.swotActor = swotActor;
		this.swotPropertyList = swotPropertyList;
		flagExample = 1;
		initializeComponent();

		layoutComponent();
	}

	/**
	 * 初始化控件。
	 */
	public void initializeComponent() {
		setLayout(new BorderLayout());

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				panelWidth = getSize().width;
				panelHeight = getSize().height;

				refreshPanel();
			}
		});

		graph = new mgraphx(false, StaticConfig.FONTSIZE_EDITVIEW_NODE_EDGE,
				StaticConfig.FONTSIZE_EDITVIEW_NODE_CONTENT, true);

		// 右键菜单
		popupMenu = new JPopupMenu();
		popmenuItemAddProperty = new JMenuItem("娣诲姞");
		popmenuItemDeleteProperty = new JMenuItem("鍒犻櫎");
		popmenuItemModifyProperty = new JMenuItem("淇敼");

		popmenuItemAddProperty.addActionListener(this);
		popmenuItemDeleteProperty.addActionListener(this);
		popmenuItemModifyProperty.addActionListener(this);
	}

	/**
	 * 在SwotEditPanel中装入JInternalFrame并不会触发该panel的componentResized，
	 * 故要人为的做第一次刷新才会显示出
	 */
	public void layoutComponent() {
		refreshPanel();

		popupMenu.add(popmenuItemAddProperty);
		popupMenu.add(popmenuItemDeleteProperty);
		popupMenu.add(popmenuItemModifyProperty);
	}

	/**
	 * 刷新面板
	 */
	public void refreshPanel() {
		if (flag == 1) {
			if (graph != null) {
				removeAll();
				graph = new mgraphx(false, StaticConfig.FONTSIZE_EDITVIEW_NODE_EDGE,
						StaticConfig.FONTSIZE_EDITVIEW_NODE_CONTENT, true); // 榛樿锛岃竟瀛椾綋澶у皬锛岃妭鐐瑰瓧浣撳ぇ灏忥紝榛樿
			}

			graph.setBounds(1, 1, panelWidth, panelHeight);
			graph.setBorder(new EmptyBorder(0, 0, 0, 0));

			nodeWidth = graph.getWidth() / 3;
			// 15号字体行高为22
			nodeHeight = 29;

			// 显示标签节点
			importData();

			// 添加节点
			graph.addEdge("优势", nodeActorName, nodeAdvantage);
			graph.addEdge("机会", nodeActorName, nodeChance);
			graph.addEdge("劣势", nodeActorName, nodeDisadvantage);
			graph.addEdge("威胁", nodeActorName, nodeThreat);

			add(graph);
			revalidate();
			repaint();
		} else
			flag = 1;
	}

	/**
	 * 导入数据
	 */
	public void importData() {
		SwotActorDAOInterface swotActorDAO = new SwotActorDAO();
		// 不是示例，从数据库载入数据
		if (flagExample == 0)
			swotPropertyList = swotActorDAO.getAllActorPropertys(swotActor);

		String reslutAdvantage = "";
		String reslutChance = "";
		String reslutDisadvantage = "";
		String reslutThreat = "";

		// 重置序号
		countAdvantage = 1;
		countChance = 1;
		countDisadvantage = 1;
		countThreat = 1;

		// 中间取最大值
		int countMax1;
		int countMax2;

		if (swotPropertyList != null) {
			for (SwotActorProperty swotProperty : swotPropertyList) {
				String outputString = "";
				outputString = swotProperty.getPropertyContent();

				// 判断是哪种属性
				if (swotProperty.getPropertyType().equals("advantage")) {
					if (reslutAdvantage.equals(""))
						reslutAdvantage = String.valueOf(countAdvantage++) + "." + outputString;
					else {
						reslutAdvantage = reslutAdvantage + "\n" + String.valueOf(countAdvantage++) + "."
								+ outputString;
					}
				} else if (swotProperty.getPropertyType().equals("chance")) {
					if (reslutChance.equals(""))
						reslutChance = String.valueOf(countChance++) + "." + outputString;
					else
						reslutChance = reslutChance + "\n" + String.valueOf(countChance++) + "." + outputString;
				} else if (swotProperty.getPropertyType().equals("disadvantage")) {
					if (reslutDisadvantage.equals(""))
						reslutDisadvantage = String.valueOf(countDisadvantage++) + "." + outputString;
					else
						reslutDisadvantage = reslutDisadvantage + "\n" + String.valueOf(countDisadvantage++) + "."
								+ outputString;
				} else if (swotProperty.getPropertyType().equals("threat")) {
					if (reslutThreat.equals(""))
						reslutThreat = String.valueOf(countThreat++) + "." + outputString;
					else
						reslutThreat = reslutThreat + "\n" + String.valueOf(countThreat++) + "." + outputString;
				}
			}
		}

		// 4个属性行数取最大值
		if (countAdvantage > countChance) {
			countMax1 = countAdvantage;
		} else {
			countMax1 = countChance;
		}
		if (countDisadvantage > countThreat) {
			countMax2 = countAdvantage;
		} else {
			countMax2 = countChance;
		}
		if (countMax1 > countMax2) {
			countMax = countMax1;
		} else {
			countMax = countMax2;
		}

		nodeHeight *= countMax;

		// 节点的位置是中心位置
		nodeAdvantage = graph.addNode(reslutAdvantage, nodeWidth / 2, nodeHeight / 2, nodeWidth, nodeHeight);
		nodeChance = graph.addNode(reslutChance, graph.getWidth() - nodeWidth / 2, nodeHeight / 2, nodeWidth,
				nodeHeight);
		nodeDisadvantage = graph.addNode(reslutDisadvantage, nodeWidth / 2, nodeHeight * 5 / 2, nodeWidth, nodeHeight);
		nodeThreat = graph.addNode(reslutThreat, graph.getWidth() - nodeWidth / 2, nodeHeight * 5 / 2, nodeWidth,
				nodeHeight);
		nodeActorName = graph.addNode(swotActor.getActorName(), graph.getWidth() / 2, nodeHeight * 3 / 2);
	}

	/**
	 * 根据当前操控属性标志取得对应属性节点
	 * 
	 * @param propertyTypeFlag
	 *            属性标志
	 * @return 对应属性节点
	 */
	public Object getPropertyType(int propertyTypeFlag) {
		if (propertyTypeFlag == 1) {
			return nodeAdvantage;
		} else if (propertyTypeFlag == 2) {
			return nodeChance;
		} else if (propertyTypeFlag == 3) {
			return nodeDisadvantage;
		} else {
			return nodeThreat;
		}
	}

	/**
	 * 添加属性
	 */
	public void addProperty() {
		frameAddProperty = new JFrame("添加属性");
		frameAddProperty.setSize(500, 400);
		frameAddProperty.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameAddProperty.setLocationRelativeTo(null);
		frameAddProperty.getContentPane().setLayout(new BorderLayout());

		panelAddProperty = new JPanel();
		panelLayLabelInAddProperty = new JPanel();
		panelLayTextareaInAddProperty = new JPanel();
		panelLayButtonInAddProperty = new JPanel();
		panelAddProperty.setLayout(new BoxLayout(panelAddProperty, BoxLayout.Y_AXIS));
		panelLayTextareaInAddProperty.setLayout(new BoxLayout(panelLayTextareaInAddProperty, BoxLayout.X_AXIS));
		panelLayButtonInAddProperty.setLayout(new BoxLayout(panelLayButtonInAddProperty, BoxLayout.X_AXIS));

		lableContentInAddProperty = new JLabel("内容");

		textareaContentInAddProperty = new JTextArea(5, 5);
		textareaContentInAddProperty.setLineWrap(true); // 鏀寔鑷姩鎹㈣
		textareaContentInAddProperty.setBorder(BorderFactory.createTitledBorder(""));

		buttonConfirmInAddProperty = new JButton("确定");
		buttonCancelInAddProperty = new JButton("取消");

		buttonConfirmInAddProperty.addActionListener(this);
		buttonCancelInAddProperty.addActionListener(this);

		panelLayLabelInAddProperty.add(lableContentInAddProperty);
		panelLayTextareaInAddProperty.add(Box.createHorizontalStrut(20));
		panelLayTextareaInAddProperty.add(textareaContentInAddProperty);
		panelLayTextareaInAddProperty.add(Box.createHorizontalStrut(20));
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
		if (e.getSource() == popmenuItemAddProperty) {
			// 右键菜单添加属性
			addProperty();
		} else if (e.getSource() == buttonConfirmInAddProperty) {
			// 右键菜单确认添加属性
			buttonConfirmInAddProperty.setText("继续添加");
			textareaContentInAddProperty.setText("");
		} else if (e.getSource() == buttonCancelInAddProperty) {
			// 右键菜单取消添加属性
			frameAddProperty.dispose();
		}
	}
}
