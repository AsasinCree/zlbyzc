package zlbyzc.sub3.juece.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import zlbyzc.sub3.juece.ui.PanelOutput.Listener;

public class PanelAsearch extends JPanel {
	MainFrame mf = null;
	
	JPanel panelInput = new JPanel();
	JPanel panelResearchTxt = new JPanel();
	JPanel panelXiuGai = new JPanel();
	
	JLabel search = new JLabel("请输入查询主题:");
	JTextArea searchInput = new JTextArea();//主题输入文本框
	JButton firm = new JButton("查询");
	
	JTextArea sTxt = new JTextArea();//主题输入文本框
	JScrollPane searchTxt  = new JScrollPane(sTxt,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JButton xiuGai = new JButton("修改");
	
	public PanelAsearch(MainFrame mf) {
		this.mf = mf;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		
		panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.X_AXIS));
		panelResearchTxt.setLayout(new BoxLayout(panelResearchTxt, BoxLayout.X_AXIS));
		panelXiuGai.setLayout(new BoxLayout(panelXiuGai, BoxLayout.X_AXIS));
		
		search.setFont(new Font("Dialog",0,16));
		searchInput.setFont(new Font("Dialog",0,16));
		searchInput.setMinimumSize(new Dimension(300,25));//将此组件的最大大小设置为一个常量值。
		searchInput.setMaximumSize(new Dimension(400,30));//将此组件的最大小设置为一个常量值。
		
		sTxt.setFont(new Font("Dialog",0,16));
		searchTxt.setPreferredSize(new Dimension(450,350));
		//searchTxt.setMinimumSize(new Dimension(400,200));//将此组件的最大大小设置为一个常量值。
		searchTxt.setMaximumSize(new Dimension(550,450));//将此组件的最大小设置为一个常量值。
		
		panelInput.add(search);
		panelInput.add(Box.createRigidArea(new Dimension(20, 60))); 
		panelInput.add(searchInput);
		panelInput.add(Box.createRigidArea(new Dimension(20, 60))); 
		panelInput.add(firm);
		
		panelResearchTxt.add(searchTxt);
		panelXiuGai.add(xiuGai);
		
		add( Box.createVerticalGlue() );
		add(panelInput);
		add(Box.createVerticalStrut(10));  		
		add(panelResearchTxt);
		add(Box.createVerticalStrut(10));  		
		add(panelXiuGai);
		add( Box.createVerticalGlue() );

		this.addEvent();
	}
	
	public void addEvent(){
		 //决策树的监听组件
	     firm.addActionListener(new Listener());
	     xiuGai.addActionListener(new Listener());
	}
	
	/**
	 * 监听窗口的类
	 */
	class Listener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			//决策树
			searchPerformed(e);  //A页
		}		
	}
	
	/**
	 * 按钮事件
	 */
	public void searchPerformed(ActionEvent e) {
		
		if(e.getSource() == firm) {
			sTxt.setText("");
			String sTopicName = searchInput.getText();
			
			String idd = null;
			int r = 0;
			int count = 0;
			
			try{
				Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.2:3336/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111");  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				
    			rs = stmt.executeQuery("select * from topic_table where topicName = '"+sTopicName+"' ");
                if(rs.next()) { 
                	String ss = rs.getString("schemeName");
                	idd = rs.getString("id");
                	r = Integer.parseInt(rs.getString("rowCount"));
                	count = Integer.parseInt(rs.getString("ideaCount")) + 1;
                	sTxt.append("------主题名称：" + sTopicName +  "\r\n");
                	sTxt.append("------方案名称：" + ss +  "\r\n");
                	sTxt.append("\r\n");
                }
                
                rs = stmt.executeQuery("select * from idea_table where id = '"+idd+"' ");
                sTxt.append("属性名"+ "\t");
                while(rs.next()) { 
                	String ss = rs.getString("ideaDescription");
                	sTxt.append(ss + "\t");
                }
                sTxt.append("attitude" + "\t");
                sTxt.append("\r\n");
                
                
            	for (int i = 1; i <= r; i++) {
            		sTxt.append("属性值"+ "\t");
            		rs = stmt.executeQuery("select * from ideaValue_table where id = '"+idd+"' and row = '"+i+"'");
            		 while(rs.next()) {
	            		String ss = rs.getString("ideaValue");
	                	sTxt.append(ss + "\t");
            		 }
            		 sTxt.append("\r\n");
				}
            	
                rs = stmt.executeQuery("select * from topic_table where topicName = '"+sTopicName+"' ");
                if(rs.next()) { 
                	sTxt.append("\r\n");
                	sTxt.append("------决策树结果------" + "\r\n");
                	sTxt.append("\r\n");
                	String ss = rs.getString("result");
                	sTxt.append(ss);
                }
                
                if(sTxt.getText().length() < 25) {
                	sTxt.setText("该主题查询不到，请重新输入主题！！！" );
                }
                
            }catch(Exception e1) {
                    e1.printStackTrace();
        	}  
		}
		
		//修改
		if(e.getSource() == xiuGai) {
		}
	}
}
