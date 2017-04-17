package zlbyzc.sub3.juece.ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import zlbyzc.sub3.juece.decisiontree.ID3;

public class PanelA extends JPanel {
	MainFrame mf = null;
	
	int index = (int)(Math.random()*10000);//产生一个四位数的随机数，作为主题的编号
	
	JPanel panelName = new JPanel();
	JPanel panelTopicName = new JPanel();
	JPanel panelSchemeName = new JPanel();
	JPanel panelChange = new JPanel();
	JPanel panelScore = new JPanel();
	JPanel panelButton = new JPanel();
	
	JLabel name = new JLabel("决策树");//主题名称标签
	JLabel topicName = new JLabel("主题名称：");//主题名称标签
	JLabel schemeName = new JLabel("方案名称：");
	//JLabel ideaNum = new JLabel("设置属性数量：");
	
	JTextPane topicTxt = new JTextPane();//主题输入文本框
	JTextPane schemeTxt = new JTextPane();
	
	//Vector<String> ideaNumVector = new Vector<String>();//准则向量
	//public JComboBox<String> ideaNumList = new JComboBox<String>(ideaNumVector);//准则列单
	
	//JLabel ideaMessage = new JLabel("准则信息描述：");//主题名称标签
	///Vector<String> ideaVector = new Vector<String>();//准则向量
	//JComboBox<String> ideaList = new JComboBox<String>(ideaVector);//准则列单
	//JTextPane ideaTxt = new JTextPane();//主题输入文本框
	
	//String[] pai = {"(空)","(空)","(空)","方案是否可行"};
	public DefaultTableModel defaultModel = new DefaultTableModel(3, 4);
	JTable scoreTable = new JTable(defaultModel);
	JScrollPane scoreScroll  = new JScrollPane(scoreTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	JButton addLie = new JButton("添加列");//保存基本信息
	JButton addRow = new JButton("添加行");//保存基本信息
	JButton removeLie = new JButton("删除列");//保存基本信息
	JButton removeRow = new JButton("删除行");//保存基本信息
	JButton save = new JButton("保存");//保存基本信息
	
	JButton juece = new JButton("决策");//保存基本信息
	
	public Map<Integer, String> ideaMap = new HashMap<Integer, String>();
	
	public int lie = 4;
	int row =3;
	
	List<String > pathList = new ArrayList<String>();
	//////////算法数据
	Object[] array;
	Object[][] arrayString;
	
	public PanelA(MainFrame mf) {
		this.mf = mf;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		
		panelName.setLayout(new BoxLayout(panelName, BoxLayout.X_AXIS));
		panelTopicName.setLayout(new BoxLayout(panelTopicName, BoxLayout.X_AXIS));
		panelSchemeName.setLayout(new BoxLayout(panelSchemeName, BoxLayout.X_AXIS));
		panelChange.setLayout(new BoxLayout(panelChange, BoxLayout.X_AXIS));
		panelScore.setLayout(new BoxLayout(panelScore, BoxLayout.X_AXIS));
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
	
		addLie.setPreferredSize(new Dimension(60,16));
		removeRow.setPreferredSize(new Dimension(60,16));
		addRow.setPreferredSize(new Dimension(60,16));
		removeLie.setPreferredSize(new Dimension(60,16));
		juece.setPreferredSize(new Dimension(60,16));
		
		name.setFont(new Font("Dialog",0,18));
		topicName.setFont(new Font("Dialog",0,16));
		topicTxt.setFont(new Font("Dialog",0,16));
        topicTxt.setMinimumSize(new Dimension(4500,25));//将此组件的最大大小设置为一个常量值。
        topicTxt.setMaximumSize(new Dimension(600,30));//将此组件的最大小设置为一个常量值。

        schemeName.setFont(new Font("Dialog",0,16));
        schemeTxt.setFont(new Font("Dialog",0,16));
        schemeTxt.setMinimumSize(new Dimension(450,25));
        schemeTxt.setMaximumSize(new Dimension(600,30));
        
        defaultModel.setValueAt("属性名", 0, 0);
        for (int i = 1; i < row; i++) {
        	defaultModel.setValueAt("属性值", i, 0);
		}
      	scoreTable.setRowHeight(30);//表格中每一的高度
      	scoreTable.setTableHeader(null);//表头不要
      	scoreTable.setFont(new Font("Dialog",0,14));
      	DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();  
        r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);   
        scoreTable.setDefaultRenderer(Object.class,r);
        //scoreScroll.setMinimumSize(new Dimension(400,250));
        //scoreScroll.setMaximumSize(new Dimension(500,350));
        scoreScroll.setMinimumSize(new Dimension(600,200));
        scoreScroll.setMaximumSize(new Dimension(700,300));
              
    	panelName.add(name);		
    	
    	panelTopicName.add(topicName);		
    	panelTopicName.add(Box.createRigidArea(new Dimension(20, 60)));  		
    	panelTopicName.add(topicTxt);
    	
    	panelSchemeName.add(schemeName);		
    	panelSchemeName.add(Box.createRigidArea(new Dimension(20, 60)));  		
    	panelSchemeName.add(schemeTxt);
    	
    	panelChange.add(addLie);		
    	panelChange.add(Box.createRigidArea(new Dimension(30, 60)));  		
    	panelChange.add(removeLie);
    	panelChange.add(Box.createRigidArea(new Dimension(100, 60)));  		
    	panelChange.add(addRow);
    	panelChange.add(Box.createRigidArea(new Dimension(30, 60)));  		
    	panelChange.add(removeRow);
    	
    	panelScore.add(scoreScroll);		
   
    	panelButton.add(save);
    	panelButton.add(Box.createRigidArea(new Dimension(100, 60)));  		
    	panelButton.add(juece);

    	add( Box.createVerticalGlue() );
		add(panelName);
		add(Box.createVerticalStrut(10));  		
		add(panelTopicName);
		add(Box.createVerticalStrut(10));  		
		add(panelSchemeName);
		add(Box.createVerticalStrut(10));  		
		add(panelChange);
		add(Box.createVerticalStrut(10));  		
		add(panelScore);
		add(Box.createVerticalStrut(10));  		
		add(panelButton);
		add( Box.createVerticalGlue() );
        
        this.addEvent();
	}
	
	public void addEvent(){
		 //决策树的监听组件
		 addLie.addActionListener(new Listener());
		 removeLie.addActionListener(new Listener());
		 addRow.addActionListener(new Listener());
		 removeRow.addActionListener(new Listener());
	     save.addActionListener(new Listener());
	     juece.addActionListener(new Listener());
	}
	
	/**
	 * 监听窗口的类
	 */
	class Listener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			//决策树
			APerformed(e);  //A页
		}		
	}
	
	/**
	 * 按钮事件
	 */
	public void APerformed(ActionEvent e) {
		
		if(e.getSource() == addLie) {
			lie ++;
			defaultModel.setColumnCount(lie);
		}
	
		if(e.getSource() == removeLie) {
			lie --;
			if(lie < 1) {
				lie = 1;
			}
			defaultModel.setColumnCount(lie);
		}
		
		
		if(e.getSource() == addRow) {
			row ++;
			defaultModel.setNumRows(row);
			for (int i = 1; i < row; i++) {
	        	defaultModel.setValueAt("属性值", i, 0);
			}
		}
	
		if(e.getSource() == removeRow) {
			row --;
			if(row < 1) {
				row = 1;
			}
			defaultModel.setNumRows(row);
			for (int i = 1; i < row; i++) {
	        	defaultModel.setValueAt("属性值", i, 0);
			}
		}
		
		if(e.getSource() == save) {
			
			String topicName = topicTxt.getText();
			String schemeName = schemeTxt.getText();
			
			try{
				Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.2:3336/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111");  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				
    			rs = stmt.executeQuery("select * from topic_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String ss ="delete from topic_table where id = '"+ index+"'";
                  	stmt.executeUpdate(ss);
                }
    			rs = stmt.executeQuery("select * from topic_table ");
        		if(rs.next()) {  
        			String rowC = (row-1) + "";
	        		String ideaCount = (lie-2) + "";
	        		String ss = "insert into topic_table(id,topicName,schemeName,rowCount,ideaCount) values('"+index+"','"+topicName+"','"+schemeName+"','"+rowC+"','"+ideaCount+"')";
				    stmt.executeUpdate(ss);
        		}
	        		
        		rs = stmt.executeQuery("select * from idea_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String ss ="delete from idea_table where id = '"+ index+"'";
                  	stmt.executeUpdate(ss);
                }
        		rs = stmt.executeQuery("select * from idea_table ");
        		if(rs.next()) {  
        			String s = null;
	        	    for (int i = 1; i < lie-1; i++) {
	        	    	s = defaultModel.getValueAt(0, i).toString();
	        	    	String ss = "insert into idea_table(id,ideaNum,ideaDescription) values('"+index+"','"+i+"','"+s+"')";
					    stmt.executeUpdate(ss);
					}
        		}
	        	
        		rs = stmt.executeQuery("select * from ideaValue_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String ss ="delete from ideaValue_table where id = '"+ index+"'";
                  	stmt.executeUpdate(ss);
                }
                rs = stmt.executeQuery("select * from ideaValue_table ");
        		if(rs.next()) { 
        			String ss;
        			String rowNum;
        			for (int i = 1; i < row; i++) {
        				rowNum = (i)+"";
        				String ii = null;
						for (int j = 1; j < lie; j++) {
							ii = defaultModel.getValueAt(0, j).toString();
							ss = "insert into ideaValue_table(id,row,ideaDescription,ideaValue) values('"+index+"','"+rowNum+"','"+ii+"','"+defaultModel.getValueAt(i, j)+"')";
						    stmt.executeUpdate(ss);
						}
        			}
        		}
			} catch(Exception e1) {
                e1.printStackTrace();
            }  
		}
		
		if(e.getSource() == juece) {
			//将之前的决策结果清空
			this.mf.output.setText("");
			
			this.array = new Object[row-1];
			this.arrayString = new Object[row-1][];
			
			//作为决策的原始数据
	    	for (int i =1; i< row; i++) {
	    		arrayString[i-1] = new String[lie-1];
				for (int j = 1; j < lie; j++) {
					arrayString[i-1][j-1] = defaultModel.getValueAt(i, j).toString();
				}
			}
	    	
	    	for (int i = 0; i < row-1; i++) {		    		
	    			array[i] = (String[])arrayString[i];	
			}
	    	
	    	
	    	//建决策树
			ID3 ID3Tree = new ID3(this.mf);
			
			//这里array是训练集数组，4是训练集属性的个数
		
			
			//在控制台打印训练数组
			/////////打印训练数组
			/*for (int i = 0; i < array.length; i++) {
				 String[] str = (String[]) array[i];
				 for (int j = 0; j < str.length; j++) {
						System.out.print(str[j] + "\t");
					}
				System.out.println();
		    }
			System.out.println("\n\n");*/
	        /////////打印训练数组
			
			ID3Tree.create(array, lie-2);
			//System.out.println("==========END PRINT TREE==========");
			
			
			//测试数据
			//String[] printData = new String[]{"Overcast","Cool","Normal","Weak"};
			//String[] printData = new String[]{"攻击敌军制胜率","减少我方损失","国际影响","全局制胜率"};
			
			//System.out.println("==========DECISION RESULT==========");
			
			//预测
			ID3Tree.compare(ID3Tree.printData, ID3Tree.root);
			
			//决策路径
			pathList = ID3Tree.getPathList();
			
			//决策结果
			String out = mf.output.getText().toString();
			
			mf.panelAout.resultT.setText(out);
			
			mf.panelAout.setC(ID3Tree.getC());
			
			String ss = null;
			mf.panelAout.defaultModel.setColumnCount(lie-1);
			for (int i = 0; i < lie-1; i++) {
				ss = defaultModel.getValueAt(0, i).toString();
				mf.panelAout.defaultModel.setValueAt(ss, 0, i);
			}
			mf.panelAout.defaultModel.setValueAt("属性值", 1, 0);
			for (int i = 1; i < mf.panelA.lie-1; i++) {
				mf.panelAout.defaultModel.setValueAt("", 1, i);
			}
			mf.setContentPane(mf.panelAout);
			
			mf.revalidate();
			mf.repaint(); 
			
			String topicName = topicTxt.getText();
			String schemeName = schemeTxt.getText();
			try{
				Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.2:3336/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111");  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				
				String ideaCount = (lie-2) + "";
				
				rs = stmt.executeQuery("select * from topic_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String sss ="delete from topic_table where id = '"+ index+"'";
                  	stmt.executeUpdate(sss);
                }
    			rs = stmt.executeQuery("select * from topic_table ");
        		if(rs.next()) {  
        			String rowC = (row-1) + "";
		    		String aa = "insert into topic_table(id,topicName,schemeName,rowCount,ideaCount,result) values('"+index+"','"+topicName+"','"+schemeName+"','"+rowC+"','"+ideaCount+"','"+out+"')";
				    stmt.executeUpdate(aa);
        		}
            } catch(Exception e1) {
                e1.printStackTrace();
            }  
		}
	}
}
