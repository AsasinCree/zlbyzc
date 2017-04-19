package zlbyzc.sub3.juece.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	public Date date= new Date();//创建一个时间对象，获取到当前的时间
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间显示格式
	String str = sdf.format(date);//将当前时间格式化为需要的类型
	
	
	JPanel jp = new JPanel();
	JScrollPane scroll  = new JScrollPane(this.jp,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	int index = (int)(Math.random()*10000);//产生一个四位数的随机数，作为主题的编号
	
	JPanel panelName = new JPanel();
	JPanel panelTopicName = new JPanel();
	JPanel panelSchemeName = new JPanel();
	JPanel panelChange = new JPanel();
	JPanel panelScore = new JPanel();
	JPanel panelButton = new JPanel();
	
	JLabel name = new JLabel("决策树(1)");//主题名称标签
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
	JButton test = new JButton("实例");//保存基本信息
	
	public Map<Integer, String> ideaMap = new HashMap<Integer, String>();
	
	public int lie = 4;
	int row =3;
	
	List<String > pathList = new ArrayList<String>();
	//////////算法数据
	Object[] array;
	Object[][] arrayString;
	
	public PanelA(MainFrame mf) {
		this.mf = mf;
		
		System.out.println(str);//输出结果
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		jp.setLayout(new BoxLayout(this.jp, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		
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
        for (int i = 1; i < lie-1; i++) {
        	defaultModel.setValueAt("(请输入属性" + i + "名称)", 0, i);
		}
        defaultModel.setValueAt("(请输入结果名称)", 0, lie-1);
        for (int i = 1; i < row; i++) {
        	defaultModel.setValueAt("属性值", i, 0);
		}
      	scoreTable.setRowHeight(30);//表格中每一的高度
      	scoreTable.setTableHeader(null);//表头不要
      	scoreTable.setFont(new Font("Dialog",0,14));
      	scoreTable.isEditing();
      	DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();  
        r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);   
        scoreTable.setDefaultRenderer(Object.class,r);
        //scoreScroll.setMinimumSize(new Dimension(400,250));
        //scoreScroll.setMaximumSize(new Dimension(500,350));
        scoreScroll.setMinimumSize(new Dimension(500,25));
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
    	 		
    	panelButton.add(test);
    	panelButton.add(Box.createRigidArea(new Dimension(100, 60))); 
    	panelButton.add(save);
    	panelButton.add(Box.createRigidArea(new Dimension(100, 60)));  		
    	panelButton.add(juece);
    	

    	jp.add( Box.createVerticalGlue() );
    	jp.add(panelName);
    	//jp.add(Box.createVerticalStrut(10));  		
    	jp.add(panelTopicName);
    	//jp.add(Box.createVerticalStrut(10));  		
    	jp.add(panelSchemeName);
    	//jp.add(Box.createVerticalStrut(10));  		
    	jp.add(panelChange);
    	//jp.add(Box.createVerticalStrut(10));  		
    	jp.add(panelScore);
    	//jp.add(Box.createVerticalStrut(10));  		
    	
    	//jp.add( Box.createVerticalGlue() );
    	this.add(this.scroll); 
    	this.add(panelButton);
        this.addEvent();
	}
	
	public void addEvent(){
		 //决策树的监听组件
		 addLie.addActionListener(new Listener());
		 removeLie.addActionListener(new Listener());
		 addRow.addActionListener(new Listener());
		 removeRow.addActionListener(new Listener());
		 test.addActionListener(new Listener());
	     save.addActionListener(new Listener());
	     juece.addActionListener(new Listener());
	     //输入属性名时，提示信息清空
	     scoreTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					//if(e.getClickCount() == 2) {
						int row = ((JTable)e.getSource()).rowAtPoint(e.getPoint());
						int lie = ((JTable)e.getSource()).columnAtPoint(e.getPoint());
//						System.out.println("" + row + lie);
						String chu = "(请输入属性" + lie + "名称)";
						String chuResult = "(请输入结果名称)";
         				
						if(row ==0 && lie>0 ){
							String ideaName = defaultModel.getValueAt(row, lie).toString();
							if(ideaName.equals(chu) ||  ideaName.equals(chuResult)){
								defaultModel.setValueAt("", row, lie);
								scoreTable.editCellAt(row,lie);
						    }
						}
						
						//System.out.println(themeName);
					}
				//}
				
		});
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
			defaultModel.setValueAt("", 0, lie-1);
			lie ++;
			defaultModel.setColumnCount(lie);
			int a = lie-2;
		    defaultModel.setValueAt("(请输入属性" + a + "名称)", 0, lie-2);
				
		    defaultModel.setValueAt("(请输入结果名称)", 0, lie-1);
		}
	
		if(e.getSource() == removeLie) {
			lie --;
			if(lie < 1) {
				lie = 1;
			}
			defaultModel.setColumnCount(lie);
			/* for (int i = 1; i < lie-1; i++) {
		        	defaultModel.setValueAt("(请输入属性" + i + "名称)", 0, i);
				}*/
		        defaultModel.setValueAt("(请输入结果名称)", 0, lie-1);
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
		
		if(e.getSource() == test) {
			topicTxt.setText("导弹发射决策");
			schemeTxt.setText("根据条件判断导弹是否发射");
			String[] demo_idea = {"天气","湿度","湿度电磁干扰","风力","是否发射"};
			String[][] demo = {
					{"Sunny","large","High","Weak","No"},
					{"Sunny","large","High","Strong","No"},
					{"Overcast","large","High","Weak","Yes"},
									
					{"Rain","Mild","High","Weak","Yes"},
					{"Rain","small","Normal","Weak","Yes"},
					{"Rain","small","Normal","Strong","No"},
					{"Overcast","small","Normal","Strong","Yes"},
									
					{"Sunny","Mild","High","Weak","No"},
					{"Sunny","small","Normal","Weak","Yes"},
					{"Rain","Mild","Normal","Weak","Yes"},
					{"Sunny","Mild","Normal","Strong","Yes"},
					{"Overcast","Mild","High","Strong","Yes"},
					{"Overcast","large","Normal","Weak","Yes"},
					{"Rain","Mild","High","Strong","No"}	
//					{"晴","小","强","2","是"},
//					{"晴","大","弱","1","是"},
//					{"小雨","小","弱","3","是"},
//									
//					{"大雨","小","弱","3","是"},
//					{"大雨","大","弱","5","否"},
//					{"小雨","大","强","5","否"},
//					{"暴雨","大","弱","1","否"},
			};
			row = demo.length + 1;
			lie = demo[0].length + 1;
			defaultModel.setNumRows(row);
			defaultModel.setColumnCount(lie);
			defaultModel.setValueAt("属性名", 0, 0);
			for (int i = 0; i < demo_idea.length; i++) {
				defaultModel.setValueAt(demo_idea[i], 0, i+1);
			}
			for (int i = 0; i < demo.length; i++) {
				defaultModel.setValueAt("属性值", i+1, 0);
				for (int j = 0; j < demo[0].length; j++) {
					defaultModel.setValueAt(demo[i][j], i+1, j+1);
				}
			}
		}
		
		if(e.getSource() == save) {
			
			String topicName = topicTxt.getText();
			String schemeName = schemeTxt.getText();
		//	boolean flag = true;
			try{
				Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue"));	
				//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111"); 	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				
//				rs = stmt.executeQuery("select * from topic_table");
//				while(rs.next()) { 
//                	String ss = rs.getString("topicName");
//                  	if(topicName.equals(ss)){
//                  		flag = false;
//                  		JOptionPane.showMessageDialog(this, "主题名已存在！请重新输入主题!", "提示信息", JOptionPane.INFORMATION_MESSAGE);
//                  	}
//                }
				
			//	if(flag){
					rs = stmt.executeQuery("select * from topic_table where id = '"+index+"' ");
	                if(rs.next()) { 
	                	String ss ="delete from topic_table where id = '"+ index+"'";
	                  	stmt.executeUpdate(ss);
	                }
        			String rowC = (row-1) + "";
	        		String ideaCount = (lie-2) + "";
	        		String ss = "insert into topic_table(id,topicName,schemeName,rowCount,ideaCount,date) values('"+index+"','"+topicName+"','"+schemeName+"','"+rowC+"','"+ideaCount+"','"+str+"')";
				    stmt.executeUpdate(ss);
		        		
	        		rs = stmt.executeQuery("select * from idea_table where id = '"+index+"' ");
	                if(rs.next()) { 
	                	String ss2 ="delete from idea_table where id = '"+ index+"'";
	                  	stmt.executeUpdate(ss2);
	                }
        			String s = null;
	        	    for (int i = 1; i < lie-1; i++) {
	        	    	s = defaultModel.getValueAt(0, i).toString();
	        	    	String ss3 = "insert into idea_table(id,ideaNum,ideaDescription) values('"+index+"','"+i+"','"+s+"')";
					    stmt.executeUpdate(ss3);
					}
		        	
	        		rs = stmt.executeQuery("select * from ideavalue_table where id = '"+index+"' ");
	                if(rs.next()) { 
	                	String ss4 ="delete from ideavalue_table where id = '"+ index+"'";
	                  	stmt.executeUpdate(ss4);
	                }
        			String ss5;
        			String rowNum;
        			for (int i = 1; i < row; i++) {
        				rowNum = (i)+"";
        				String ii = null;
						for (int j = 1; j < lie; j++) {
							ii = defaultModel.getValueAt(0, j).toString();
							ss5 = "insert into ideavalue_table(id,row,ideaDescription,ideaValue) values('"+index+"','"+rowNum+"','"+ii+"','"+defaultModel.getValueAt(i, j)+"')";
						    stmt.executeUpdate(ss5);
						}
        			}
	        		JOptionPane.showMessageDialog(this, "保存成功!", "提示信息", JOptionPane.INFORMATION_MESSAGE);
		//		}
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
			
			mf.panelAout.setTopicName(topicTxt.getText().toString());
			mf.panelAout.setTopicID(this.index);
			
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
				
			Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue"));	
				//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111"); 		  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 								    			         	
		    		String aa = "update topic_table  set result =' "+out+"' where id = '"+ index+ "'";
				    stmt.executeUpdate(aa);
        		
        		
            } catch(Exception e1) {
                e1.printStackTrace();
            }  
		}
	}
}
