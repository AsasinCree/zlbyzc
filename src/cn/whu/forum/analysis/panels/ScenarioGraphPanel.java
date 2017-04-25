package cn.whu.forum.analysis.panels;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.liukan.mgraph.mgraphx;

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
 * @author cree 情景分析法图形面板
 */
public class ScenarioGraphPanel extends JPanel {

	private static final long serialVersionUID = 1;

	// 依附的情景分析法任务实例
	private ScenarioTask scenarioTask;

	// panel初始化会刷新两次，第二次才是正确的大小
	private int flag = 0;
	private static mgraphx graph;
	private int nodeWidth;
	private int nodeHeight;

	// 各属性个数的序号
	private int countFocus;
	private int countKeyFactor;
	private int countDrivingpower;
	private int countDevelopment;
	private int countUncertainties;
	private int countResult;

	// 示例
	private List<ScenarioProperty> scenarioPropertyList;
	private List<ScenarioLogic> scenarioLogicList;
	private List<ScenarioResult> scenarioResultList;
	// 判断是否示例
	private int flagExample;

	private int panelWidth;
	private int panelHeight;

	// 显示标签节点
	private Object nodeFocus;
	private Object nodeKeyFactor;
	private Object nodeDrivingpower;
	private Object nodeUncertainties;
	private Object nodeDevelopment;
	private Object nodeResult;

	/**
	 * @param scenarioTask
	 *            表示依附的情景分析法任务实例 构造函数：初始化操作接口、控件及布局。
	 */
	public ScenarioGraphPanel(ScenarioTask scenarioTask) {
		this.scenarioTask = scenarioTask;
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
	public ScenarioGraphPanel(ScenarioTask scenarioTask, List<ScenarioProperty> scenarioPropertyList,
			List<ScenarioLogic> scenarioLogicList, List<ScenarioResult> scenarioResultList) {
		this.scenarioTask = scenarioTask;

		// 不是示例
		if (scenarioPropertyList.size() == 0 && scenarioLogicList.size() == 0 && scenarioResultList.size() == 0) {
			flagExample = 0;
		} else {
			this.scenarioPropertyList = scenarioPropertyList;
			this.scenarioLogicList = scenarioLogicList;
			this.scenarioResultList = scenarioResultList;
			flagExample = 1;
		}

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
	}

	/**
	 * 在SwotEditPanel中装入JInternalFrame并不会触发该panel的componentResized，故要人为的做第一次刷新才会显示出
	 */
	public void layoutComponent() {
		refreshPanel();
	}

	/**
	 * 刷新面板
	 */
	public void refreshPanel() {
		if (flag == 1) {
			if (graph != null) {
				removeAll();
				// 默认边字体大小，节点字体大小
				graph = new mgraphx(false, StaticConfig.FONTSIZE_EDITVIEW_NODE_EDGE,
						StaticConfig.FONTSIZE_EDITVIEW_NODE_CONTENT, true); 
			}

			graph.setBounds(1, 1, panelWidth, panelHeight);
			graph.setBorder(new EmptyBorder(0, 0, 0, 0));

			nodeWidth = graph.getWidth();
			// 15号字体行高为22
			nodeHeight = 29;

			// 显示标签节点
			importData();

			// 添加节点
			graph.addEdge("", nodeFocus, nodeKeyFactor);
			graph.addEdge("", nodeKeyFactor, nodeDrivingpower);
			graph.addEdge("", nodeDrivingpower, nodeUncertainties);
			graph.addEdge("", nodeUncertainties, nodeDevelopment);
			graph.addEdge("", nodeDevelopment, nodeResult);

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
		String reslutFocus = "";
		String reslutKeyFactor = "";
		String reslutDrivingpower = "";
		String reslutUncertainties = "";
		String reslutDevelopment = "";
		String reslutResult = "";

		// 重置序号
		countFocus = 1;
		countKeyFactor = 1;
		countDrivingpower = 1;
		countUncertainties = 1;
		countDevelopment = 1;
		countResult = 1;

		// 各个属性内容高度
		int heightFocus;
		int heightKeyFactor;
		int heightDrivingpower;
		int heightUncertainties;
		int heightDevelopment;
		int heightResult;
		// 箭头间隔
		int space = 20;

		ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();

		// 不是示例
		if (flagExample == 0)
			scenarioPropertyList = scenarioPropertyDAO.getAllPropertys(scenarioTask);
		// 情景分析法属性数据
		if (scenarioPropertyList != null) {
			for (ScenarioProperty scenarioProperty : scenarioPropertyList) {
				String outputString = "";
				outputString = scenarioProperty.getPropertyContent();

				if (scenarioProperty.getPropertyType().equals("focus")) {
					// 测量换行符的个数
					String[] strings = outputString.split("\n");
					// 多余的空行
					countFocus += strings.length;
					countFocus--;
					if (reslutFocus.equals(""))
						reslutFocus = String.valueOf(countFocus++) + "." + outputString;
					else {
						reslutFocus = reslutFocus + "\n" + String.valueOf(countFocus++) + "." + outputString;
					}
				} else if (scenarioProperty.getPropertyType().equals("keyfactor")) {
					// 测量换行符的个数
					String[] strings = outputString.split("\n");
					// 多余的空行
					countKeyFactor += strings.length;
					countKeyFactor--;
					if (reslutKeyFactor.equals(""))
						reslutKeyFactor = String.valueOf(countKeyFactor++) + "." + outputString;
					else
						reslutKeyFactor = reslutKeyFactor + "\n" + String.valueOf(countKeyFactor++) + "."
								+ outputString;
				} else if (scenarioProperty.getPropertyType().equals("drivingpower")) {
					// 测量换行符的个数
					String[] strings = outputString.split("\n");
					// 多余的空行
					countDrivingpower += strings.length;
					countDrivingpower--;
					if (reslutDrivingpower.equals(""))
						reslutDrivingpower = String.valueOf(countDrivingpower++) + "." + outputString;
					else
						reslutDrivingpower = reslutDrivingpower + "\n" + String.valueOf(countDrivingpower++) + "."
								+ outputString;
				} else if (scenarioProperty.getPropertyType().equals("uncertainties")) {
					// 测量换行符的个数
					String[] strings = outputString.split("\n");
					// 多余的空行
					countUncertainties += strings.length;
					countUncertainties--;
					if (reslutUncertainties.equals(""))
						reslutUncertainties = String.valueOf(countUncertainties++) + "." + outputString;
					else
						reslutUncertainties = reslutUncertainties + "\n" + String.valueOf(countUncertainties++) + "."
								+ outputString;
				}
			}
		}

		// 情景分析法逻辑数据
		ScenarioLogicDAOInterface scenarioLogicDAO = new ScenarioLogicDAO();
		if (flagExample == 0)
			scenarioLogicList = scenarioLogicDAO.getAllLogics(scenarioTask);
		if (scenarioLogicList != null) {
			for (ScenarioLogic scenarioLogic : scenarioLogicList) {
				String outputString = "";
				outputString = scenarioLogic.getLogicContent();
				// 测量换行符的个数
				String[] strings = outputString.split("\n");
				// 多余的空行
				countDevelopment += strings.length;
				countDevelopment--;
				if (reslutDevelopment.equals(""))
					reslutDevelopment = String.valueOf(countDevelopment++) + "." + outputString;
				else {
					reslutDevelopment = reslutDevelopment + "\n" + String.valueOf(countDevelopment++) + "."
							+ outputString;
				}
			}
		}

		// 情景分析法结果数据
		ScenarioResultDAOInterface scenarioResultDAO = new ScenarioResultDAO();
		if (flagExample == 0)
			scenarioResultList = scenarioResultDAO.getAllResults(scenarioTask);
		if (scenarioResultList != null) {
			for (ScenarioResult scenarioResult : scenarioResultList) {
				String outputString = "";
				outputString = scenarioResult.getResultContent();
				String[] strings = outputString.split("\n"); // �?测换行符的个�?
				countResult += strings.length;
				countResult--; // 多余�?�?
				if (reslutResult.equals(""))
					reslutResult = String.valueOf(countResult++) + "." + outputString;
				else {
					reslutResult = reslutResult + "\n" + String.valueOf(countResult++) + "." + outputString;
				}
			}
		}

		heightFocus = nodeHeight * countFocus;
		heightKeyFactor = nodeHeight * countKeyFactor;
		heightDrivingpower = nodeHeight * countDrivingpower;
		heightUncertainties = nodeHeight * countUncertainties;
		heightDevelopment = nodeHeight * countDevelopment;
		heightResult = nodeHeight * countResult;
		// 节点的位置是中心位置
		nodeFocus = graph.addNode(reslutFocus, nodeWidth / 2, heightFocus / 2, nodeWidth, heightFocus);
		nodeKeyFactor = graph.addNode(reslutKeyFactor, nodeWidth / 2, heightFocus + space + heightKeyFactor / 2,
				nodeWidth, heightKeyFactor);
		nodeDrivingpower = graph.addNode(reslutDrivingpower, nodeWidth / 2,
				heightFocus + 2 * space + heightKeyFactor + heightDrivingpower / 2, nodeWidth, heightDrivingpower);
		nodeUncertainties = graph.addNode(reslutUncertainties, nodeWidth / 2,
				heightFocus + 3 * space + heightKeyFactor + heightDrivingpower + heightUncertainties / 2, nodeWidth,
				heightUncertainties);
		nodeDevelopment = graph.addNode(reslutDevelopment, nodeWidth / 2, heightFocus + 4 * space + heightKeyFactor
				+ heightDrivingpower + heightUncertainties + heightDevelopment / 2, nodeWidth, heightDevelopment);
		nodeResult = graph.addNode(reslutResult, nodeWidth / 2, heightFocus + 5 * space + heightKeyFactor
				+ heightDrivingpower + heightUncertainties + heightDevelopment + heightResult / 2, nodeWidth,
				heightResult);
	}
}
