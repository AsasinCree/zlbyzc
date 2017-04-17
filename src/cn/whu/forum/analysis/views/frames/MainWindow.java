package cn.whu.forum.analysis.views.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cn.whu.forum.analysis.services.SwotTaskDAO;
import cn.whu.forum.analysis.services.interfaces.SwotTaskDAOInterface;
import cn.whu.forum.analysis.views.panels.ScenarioNewPanel;
import cn.whu.forum.analysis.views.panels.ScenarioSearchPanel;
import cn.whu.forum.analysis.views.panels.SwotNewPanel;
import cn.whu.forum.analysis.views.panels.SwotSearchPanel;

public class MainWindow extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	static final int WIDTH=1200;		//总框架
	static final int HEIGHT=700;
	
	JMenuItem itemNewSwot;
	JMenuItem itemNewScenario;
	JMenuItem itemSearchSwot;
	JMenuItem itemSearchScenario;
	
	public static void main(String[] args) {
		new MainWindow();
	}
	
	public MainWindow() {
		
		createMenubar();

		initializeFrame();
		
		initializeDAO();		//做一次查询操作，为了解决初次访问数据库卡顿时间
		
	}
	
	//生成菜单
	public void createMenubar() {
		
		JMenuBar menubar=new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu menuNew=new JMenu("新建");
		JMenu menuOperation=new JMenu("操作");
		JMenu menuManager=new JMenu("管理");
		JMenu menuHelp=new JMenu("帮助");
		
		menubar.add(menuNew);
		menubar.add(menuOperation);
		menubar.add(menuManager);
		menubar.add(menuHelp);
		
		itemNewSwot=new JMenuItem("SWOT法");
		itemNewScenario=new JMenuItem("情景分析法");
		itemSearchSwot=new JMenuItem("SWOT法");
		itemSearchScenario=new JMenuItem("情景分析法");
		
		itemNewSwot.addActionListener(this);
		itemNewScenario.addActionListener(this);
		itemSearchSwot.addActionListener(this);
		itemSearchScenario.addActionListener(this);
		
		menuNew.add(itemNewSwot);
		menuNew.add(itemNewScenario);
		menuManager.add(itemSearchSwot);
		menuManager.add(itemSearchScenario);
		
	}
	
	public void initializeFrame() {
				
		setTitle("");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo( null ); 
		setVisible(true);
		
	}

	public void initializeDAO() {
		
		SwotTaskDAOInterface swotTaskDAO = new SwotTaskDAO();
		swotTaskDAO.getAllSwotTasks();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == itemNewSwot) {
			
			//载入SWOT_NewSWOT_Panel	
			setContentPane(new SwotNewPanel(this));
			revalidate();
			repaint(); 

		}
		else if(e.getSource() == itemNewScenario) {
			
			//载入Scenario_NewScenario_Panel
			setContentPane(new ScenarioNewPanel(this));
			revalidate();
			repaint(); 
			
		}
		else if(e.getSource() == itemSearchSwot) {
			
			//载入SWOT_SearchSWOT_Panel
			setContentPane(new SwotSearchPanel());
			revalidate();
			repaint(); 
			
		}
		else if(e.getSource() == itemSearchScenario) {
			
			//载入Scenario_SearchScenario_Panel
			setContentPane(new ScenarioSearchPanel());
			revalidate();
			repaint(); 
			
		}	
	}
}
