package cn.whu.forum.analysis.views.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cn.whu.forum.analysis.panels.ScenarioNewPanel;
import cn.whu.forum.analysis.panels.ScenarioSearchPanel;
import cn.whu.forum.analysis.panels.SwotNewPanel;
import cn.whu.forum.analysis.panels.SwotSearchPanel;
import cn.whu.forum.analysis.services.SwotTaskDAO;
import cn.whu.forum.analysis.services.interfaces.SwotTaskDAOInterface;

/**
 * 主程序界面入口
 * 
 * @author asasi
 *
 */
public class MainWindow extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// 总框架大小
	static final int WIDTH = 1200;
	static final int HEIGHT = 700;

	// 菜单按钮
	JMenuItem itemNewSwot;
	JMenuItem itemNewScenario;
	JMenuItem itemSearchSwot;
	JMenuItem itemSearchScenario;

	/**
	 * Main方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new MainWindow();
	}

	/**
	 * 构造函数，初始化菜单，界面，及数据库
	 */
	public MainWindow() {
		createMenubar();
		initializeFrame();
		// 做一次查询操作，为了解决初次访问数据库卡顿时间
		initializeDAO();
	}

	/**
	 * 生成菜单
	 */
	public void createMenubar() {
		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);

		JMenu menuNew = new JMenu("新建");
		JMenu menuOperation = new JMenu("操作");
		JMenu menuManager = new JMenu("管理");
		JMenu menuHelp = new JMenu("帮助");

		menubar.add(menuNew);
		menubar.add(menuOperation);
		menubar.add(menuManager);
		menubar.add(menuHelp);

		itemNewSwot = new JMenuItem("SWOT法");
		itemNewScenario = new JMenuItem("情景分析法");
		itemSearchSwot = new JMenuItem("SWOT法");
		itemSearchScenario = new JMenuItem("情景分析法");

		itemNewSwot.addActionListener(this);
		itemNewScenario.addActionListener(this);
		itemSearchSwot.addActionListener(this);
		itemSearchScenario.addActionListener(this);

		menuNew.add(itemNewSwot);
		menuNew.add(itemNewScenario);
		menuManager.add(itemSearchSwot);
		menuManager.add(itemSearchScenario);
	}

	/**
	 * 初始化界面
	 */
	public void initializeFrame() {
		setTitle("");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * 初始化数据库链接
	 */
	public void initializeDAO() {
		SwotTaskDAOInterface swotTaskDAO = new SwotTaskDAO();
		swotTaskDAO.getAllSwotTasks();
	}

	/**
	 * 点击菜单对应按钮进入相应面板
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemNewSwot) {
			// 载入SWOT_NewSWOT_Panel
			setContentPane(new SwotNewPanel(this));
			revalidate();
			repaint();
		} else if (e.getSource() == itemNewScenario) {
			// 载入Scenario_NewScenario_Panel
			setContentPane(new ScenarioNewPanel(this));
			revalidate();
			repaint();
		} else if (e.getSource() == itemSearchSwot) {
			// 载入SWOT_SearchSWOT_Panel
			setContentPane(new SwotSearchPanel());
			revalidate();
			repaint();
		} else if (e.getSource() == itemSearchScenario) {
			// 载入Scenario_SearchScenario_Panel
			setContentPane(new ScenarioSearchPanel());
			revalidate();
			repaint();
		}
	}
}
