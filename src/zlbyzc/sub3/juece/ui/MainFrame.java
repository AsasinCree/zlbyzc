package zlbyzc.sub3.juece.ui;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.*;

import zlbyzc.BasicRibbon;
import zlbyzc.sub3.sub3inFrame;

public class MainFrame extends sub3inFrame {
	
	

	/**窗口宽度*/
	public static final int WIDTH = 1000;
	/**窗口高度*/
	public static final int HEIGHT = 750;
	
//	Menu menu;//窗口菜单 
	
//	public JPanel contentpane = new JPanel();//窗口中主容器
	public JScrollPane scroll  = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
//	Board board;//显示窗口，包括上下两个部分
	//决策树的主要5个界面
	public PanelA panelA = new PanelA(this);
	public PanelA2 panelA2 = new PanelA2(this);
	public PanelOutput panelAout;
	//public PanelA panelAa = new PanelA(this);///供查询来用
	//马尔可夫决策的界面
	public PanelAA panelAA = new PanelAA(this);
	
	public JTextArea output = new JTextArea();//主题输入文本框
	JScrollPane resultScroll  = new JScrollPane(output,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	public BasicRibbon BR;
	
	/*public static void main(String[] args) {
		new MainFrame().launchFrame();//进入窗口	
	}*/

	public MainFrame(BasicRibbon _br) {
		super("马尔科夫决策与决策树模块",true,true,true,true);
		BR = _br;
		panelAout = new PanelOutput(this);
		launchFrame();
	}
	
	/**
	 * 启动窗口
	 */
	public void launchFrame() {
		//this.setIconImage(Toolkit.getDefaultToolkit().createImage("images/frame.png"));  
		this.setTitle("战略决策");
		
		//contentpane.setLayout(new BorderLayout());
        this.setSize(WIDTH, HEIGHT);
        /*this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}	
		});*/
        this.setResizable(true);
        //this.setLocationRelativeTo( null );//会出现在屏幕的正中间
        
//        menu = new Menu(this);//窗口中的菜单 
//      board = new Board(this);
//      board.setUpPane(new JPanel()); //左侧一开始就显示出来
        this.setVisible(true);
        
	}
}
