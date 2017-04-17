package zlbyzc.sub3.juece.ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu {
	MainFrame mf = null;
	
	JMenuBar menu = new JMenuBar();
	
	//JMenu menuProject = new JMenu("新建");
	JMenu menuOperate = new JMenu("操作");
	JMenu menuHelp = new JMenu("帮助");
	
	//JMenuItem juece = new  JMenuItem("决策树");
	//JMenuItem markov = new  JMenuItem("马尔可夫决策");
	
	JMenu query = new  JMenu("查询");
	JMenuItem queryJuece = new  JMenuItem("决策树");
	JMenuItem queryMarkov = new  JMenuItem("马尔可夫决策");
	
	JMenuItem helpAbout = new  JMenuItem("关于");
	
	public Menu(MainFrame mf) {
		this.mf = mf; //持有主窗口的一个引用，便于操作它的成员变量
		
		//menu.add(menuProject);   
		menu.add(menuOperate);
		menu.add(menuHelp);
		
		//menuProject.add(juece);
		//menuProject.add(markov);
		
		menuOperate.add(query);
		query.add(queryJuece);
		query.add(queryMarkov);

        mf.setJMenuBar(menu);
        
        this.addEvent();
	}
	
	public void addEvent() {
		//juece.addActionListener(new Listener());
		//markov.addActionListener(new Listener());
		queryJuece.addActionListener(new Listener());
		queryMarkov.addActionListener(new Listener());	    
	}
	
	/**
	 * 监听窗口的类
	 */
	class Listener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			MemuPerformed(e);  //监听窗口的工具栏
		}		
	}
	/**
	 * 菜单事件
	 */
	public void MemuPerformed(ActionEvent e) {
		//放在工具栏了
		/*if(e.getSource() == juece) {
			mf.panelA = new PanelA(this.mf);
			mf.setContentPane(mf.panelA);
			mf.revalidate();
			mf.repaint(); 
			//mf.panelA = new PanelA(this.mf);
			//this.mf.board.setUpPane(mf.panelA);
		}
		
		
		if(e.getSource() == markov) {
			mf.panelAA = new PanelAA(this.mf);
			mf.setContentPane(mf.panelAA);
			mf.revalidate();//更新
			mf.repaint(); 
			//this.mf.board.setUpPane(mf.panelAA);
		}
		*/
		if(e.getSource() == queryJuece) {
			mf.setContentPane(new PanelAsearch(mf));
			mf.revalidate();//更新
			mf.repaint(); 
		}
		
		
		if(e.getSource() == queryMarkov) {
			mf.setContentPane(new PanelAAsearch(mf));
			mf.revalidate();//更新
			mf.repaint(); 
		}
	}
}
