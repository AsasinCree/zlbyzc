package zlbyzc.sub3.juece.ui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import zlbyzc.sub3.juece.markov.BubbleSort;

public class PanelAA extends JPanel {
	MainFrame mf = null;
	
	int index = (int)(Math.random()*10000);//产生一个四位数的随机数，作为主题的编号
	
	JPanel panelName = new JPanel();
	JPanel panelTopicName = new JPanel();
	JPanel panelActionStateNum = new JPanel();
	
	JPanel panelStateTableName = new JPanel();
	JPanel panelStateTable = new JPanel();
	JPanel panelBenefitTableName = new JPanel();
	JPanel panelBenefitTable = new JPanel();
	
	JPanel panelResultButton = new JPanel();
	JPanel panelResultTxt = new JPanel();

	JLabel name = new JLabel("马尔可夫决策");//主题名称标签
	
	JLabel topicName = new JLabel("主题名称：");//主题名称标签
	JTextPane topicTxt = new JTextPane();//主题输入文本框
	
	int actionCount = 4;
	JLabel actionNum = new JLabel("行动数量：");
	JTextPane actionNumTxt = new JTextPane();//主题输入文本框
	int stateCount = 3;
	JLabel stateNum = new JLabel("态势数量：");
	JTextPane stateNumTxt = new JTextPane();//主题输入文本框
	JButton firm = new JButton("确定");//保存基本信息
	
	JLabel stateTableName = new JLabel("                  ---状态转移表---                  选择行动");//主题名称标签
	Vector<String> actionVector = new Vector<String>();//方案向量
	JComboBox<String> actionList = new JComboBox<String>(actionVector);//方案列单
	
	public DefaultTableModel defaultModelFirst = new DefaultTableModel(stateCount+1, stateCount+1);
	JTable scoreTableFirst = new JTable(defaultModelFirst);
	JScrollPane scoreScrollFirst  = new JScrollPane(scoreTableFirst,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JButton actionFirm = new JButton("确定");
	
	JLabel benefitTableName = new JLabel("---受益表---");//主题名称标签
	public DefaultTableModel defaultModelSecond = new DefaultTableModel(actionCount+1, stateCount+1);
	JTable scoreTableSecond = new JTable(defaultModelSecond);
	JScrollPane scoreScrollSecond  = new JScrollPane(scoreTableSecond,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	JButton save = new JButton("保存");//保存基本信息
	JButton markovButton = new JButton("markov");//保存基本信息
	
	JTextPane resultT = new JTextPane();//主题输入文本框
	JScrollPane resultTxt  = new JScrollPane(resultT,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	//public Map<Integer, String> actionMap = new HashMap<Integer, String>();
	//public Map<String, String> actionStateMap = new HashMap<String, String>();
	
	/*JLabel schemeNum = new JLabel("方案数量：");
	Vector<String> schemeNumVector = new Vector<String>();//方案向量
	JComboBox<String> schemeNumList = new JComboBox<String>(schemeNumVector);//方案列单
	
	JLabel scheme = new JLabel("方案描述：");
	JTextPane schemeTxt = new JTextPane();//主题输入文本框
	Vector<String> schemeVector = new Vector<String>();//方案向量
	JComboBox<String> schemeList = new JComboBox<String>(schemeVector);//方案列单
	
	JLabel statementNum = new JLabel("态势数量：");
	Vector<String> statementNumVector = new Vector<String>();//态势向量
	public JComboBox<String> statementNumList = new JComboBox<String>(statementNumVector);//态势数量输入文本框
	
	JLabel statement = new JLabel("转移态势描述：");
	JTextPane statementTxt = new JTextPane();
	Vector<String> statementVector = new Vector<String>();//态势向量
	public JComboBox<String> statementList = new JComboBox<String>(statementVector);//态势数量输入文本框
	
	JLabel schemeTable = new JLabel("该方案态势转移表：");
	Vector<String> schemeTableVector = new Vector<String>();//方案向量
	JComboBox<String> schemeTableList = new JComboBox<String>(schemeTableVector);//方案列单
	
	JLabel benefitTable = new JLabel("方案受益表：");
	
	String[] firstPai = {"(空)","(空)","(空)","(空)","(空)"};
	DefaultTableModel firstDefaultModel = new DefaultTableModel(firstPai, 0);
	JTable firstScoreTable = new JTable(firstDefaultModel);
	JScrollPane firstScoreScroll  = new JScrollPane(firstScoreTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	String[] secondPai = {"(空)","(空)","(空)","(空)","(空)"};
	DefaultTableModel secondDefaultModel = new DefaultTableModel(secondPai, 0);
	JTable secondScoreTable = new JTable(secondDefaultModel);
	JScrollPane secondScoreScroll  = new JScrollPane(secondScoreTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
	
	int row = 0;*/
	
	//////////算法数据
	float R[][];
	float r[][];//受益
	Map<String, String[][]> stateToActionMap = new HashMap<String, String[][]>();
	float [][][] arry1;//转移概率
	
	public PanelAA(MainFrame mf) {
		this.mf = mf;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		
		panelName.setLayout(new BoxLayout(panelName, BoxLayout.X_AXIS));
		panelTopicName.setLayout(new BoxLayout(panelTopicName, BoxLayout.X_AXIS));
		panelActionStateNum.setLayout(new BoxLayout(panelActionStateNum, BoxLayout.X_AXIS));
		
		panelStateTableName.setLayout(new BoxLayout(panelStateTableName, BoxLayout.X_AXIS));
		panelStateTable.setLayout(new BoxLayout(panelStateTable, BoxLayout.X_AXIS));
		panelBenefitTableName.setLayout(new BoxLayout(panelBenefitTableName, BoxLayout.X_AXIS));
		panelBenefitTable.setLayout(new BoxLayout(panelBenefitTable, BoxLayout.X_AXIS));
		
		panelResultButton.setLayout(new BoxLayout(panelResultButton, BoxLayout.X_AXIS));
		panelResultTxt.setLayout(new BoxLayout(panelResultTxt, BoxLayout.X_AXIS));
		
		name.setFont(new Font("Dialog",0,18));
		topicName.setFont(new Font("Dialog",10,16));
		topicTxt.setFont(new Font("Dialog",10,16));
        topicTxt.setMinimumSize(new Dimension(500,25));//将此组件的最大大小设置为一个常量值。
        topicTxt.setMaximumSize(new Dimension(600,30));//将此组件的最大小设置为一个常量值。

        actionNum.setFont(new Font("Dialog",10,16));
        actionNumTxt.setFont(new Font("Dialog",10,16));
        actionNumTxt.setMinimumSize(new Dimension(100,25));//将此组件的最大大小设置为一个常量值。
        actionNumTxt.setMaximumSize(new Dimension(200,30));//将此组件的最大小设置为一个常量值。
        stateNum.setFont(new Font("Dialog",10,16));
        stateNumTxt.setFont(new Font("Dialog",10,16));
        stateNumTxt.setMinimumSize(new Dimension(100,25));//将此组件的最大大小设置为一个常量值。
        stateNumTxt.setMaximumSize(new Dimension(200,30));//将此组件的最大小设置为一个常量值。
    	
        stateTableName.setFont(new Font("Dialog",10,16));
        actionList.setFont(new Font("Dialog",10,16));
        actionList.setMaximumSize(new Dimension(150,30));//将此组件的最大小设置为一个常量值。
        
        defaultModelFirst.setValueAt("(请输入行动名称)", 0, 0);
        for (int i = 1; i <= stateCount; i++) {
        	defaultModelFirst.setValueAt("--->"+"S"+i, 0, i);
        	defaultModelFirst.setValueAt("S"+i, i, 0);
		}
        scoreTableFirst.setRowHeight(20);//表格中每一的高度
        scoreTableFirst.setTableHeader(null);//表头不要
        scoreTableFirst.setFont(new Font("Dialog",0,14));
      	DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();  
        r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);   
        scoreTableFirst.setDefaultRenderer(Object.class,r);
        scoreScrollFirst.setMaximumSize(new Dimension(600,200));
        
        benefitTableName.setFont(new Font("Dialog",10,16));
        defaultModelSecond.setValueAt("行动\\状态", 0, 0);
        for (int i = 1; i <= stateCount; i++) {
        	defaultModelSecond.setValueAt("S"+i, 0, i);
		}
        for (int i = 1; i <= actionCount; i++) {
        	defaultModelSecond.setValueAt("行动"+i, i, 0);
		}
        scoreTableSecond.setRowHeight(20);//表格中每一的高度
        scoreTableSecond.setTableHeader(null);//表头不要
        scoreTableSecond.setFont(new Font("Dialog",10,14));
        scoreTableSecond.setDefaultRenderer(Object.class,r);
        scoreScrollSecond.setMaximumSize(new Dimension(680,240));
        
        resultTxt.setFont(new Font("Dialog",10,16));
		resultTxt.setMinimumSize(new Dimension(750,70));//将此组件的最大大小设置为一个常量值。
		resultTxt.setMaximumSize(new Dimension(750,75));//将此组件的最大小设置为一个常量值。
		
        panelName.add(name);	
        
    	panelTopicName.add(topicName);		
    	panelTopicName.add(Box.createRigidArea(new Dimension(20, 60)));  		
    	panelTopicName.add(topicTxt);
    	
    	panelActionStateNum.add(actionNum);
    	panelActionStateNum.add(Box.createRigidArea(new Dimension(20, 60))); 
    	panelActionStateNum.add(actionNumTxt);
    	panelActionStateNum.add(Box.createRigidArea(new Dimension(30, 60))); 
    	panelActionStateNum.add(stateNum);
    	panelActionStateNum.add(Box.createRigidArea(new Dimension(20, 60))); 
    	panelActionStateNum.add(stateNumTxt);
    	panelActionStateNum.add(Box.createRigidArea(new Dimension(30, 60))); 
    	panelActionStateNum.add(firm);
        
    	panelStateTableName.add(stateTableName);
    	panelStateTableName.add(Box.createRigidArea(new Dimension(20, 60))); 
    	panelStateTableName.add(actionList);
    	panelStateTableName.add(Box.createRigidArea(new Dimension(20, 60)));
    	panelStateTable.add(scoreScrollFirst);
    	panelStateTableName.add(Box.createRigidArea(new Dimension(20, 60))); 
    	panelStateTable.add(actionFirm);
    	
        panelBenefitTableName.add(benefitTableName);
        panelBenefitTable.add(scoreScrollSecond);
       
        panelResultButton.add(save);
        panelResultButton.add(Box.createRigidArea(new Dimension(80, 60))); 
        panelResultButton.add(markovButton);
       
        panelResultTxt.add(resultTxt);
            
        add( Box.createVerticalGlue() );
		add(panelName);
		//add(Box.createVerticalStrut(10));  		
		add(panelTopicName);
		//add(Box.createVerticalStrut(10));  		
		add(panelActionStateNum);
		
		//add(Box.createVerticalStrut(10));  		
		add(panelStateTableName);
		//add(Box.createVerticalStrut(10));  		
		add(panelStateTable);
		
		//add(Box.createVerticalStrut(10));  		
		add(panelBenefitTableName);
		//add(Box.createVerticalStrut(10));  		
		add(panelBenefitTable);
		
		//add(Box.createVerticalStrut(10));  		
		add(panelResultButton);
		//add(Box.createVerticalStrut(10));  		
		add(panelResultTxt);
		add( Box.createVerticalGlue() );
   
		this.addListListener();
        this.addEvent();
	}
	
	/**
	 * 列表事件
	 */
	public void addListListener() {
		
		actionList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String action = actionList.getSelectedItem().toString();
				String A[][] = stateToActionMap.get(action);;
				for (int i = 0; i < stateCount; i++) {
					for (int j = 0; j < stateCount; j++) {
						defaultModelFirst.setValueAt(A[i][j] , i+1,j+1);
					}
				}
			}
		});
	}

	public void addEvent(){
		 //马尔可夫决策的监听组件
		firm.addActionListener(new Listener());
		actionFirm.addActionListener(new Listener());
	    save.addActionListener(new Listener());
	    markovButton.addActionListener(new Listener());
	}
	
	/**
	 * 监听窗口的类
	 */
	class Listener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			//马尔可夫决策
			AAPerformed(e); //AA页 
		}		
	}
	
	
	/**
	 * 按钮事件
	 */
	public void AAPerformed(ActionEvent e) {
	
		if(e.getSource() == firm) {
			actionCount = Integer.parseInt(actionNumTxt.getText());
			stateCount = Integer.parseInt(stateNumTxt.getText());
			
			/*actionMap.clear();
			for (int j = 1; j <= actionCount; j++) {
				actionMap.put(j, "行动"+j);
			}*/
			
			actionVector.removeAllElements();
			for (int i = 1; i <= actionCount; i++) {
				actionVector.add("行动" + i);
			}
			actionList.updateUI();// 更新内容操作
			
			defaultModelFirst.setRowCount(stateCount+1);
			defaultModelFirst.setColumnCount(stateCount+1);
			defaultModelFirst.setValueAt("(请输入行动名称)", 0, 0);
			for (int i = 1; i <= stateCount; i++) {
				defaultModelFirst.setValueAt("--->"+"S"+i, 0, i);
		        defaultModelFirst.setValueAt("S"+i, i, 0);
			}
			
			defaultModelSecond.setRowCount(actionCount+1);
			defaultModelSecond.setColumnCount(stateCount+1);
			defaultModelSecond.setValueAt("行动\\状态", 0, 0);
	        for (int i = 1; i <= stateCount; i++) {
	        	defaultModelSecond.setValueAt("S"+i, 0, i);
			}
	        for (int i = 1; i <= actionCount; i++) {
	        	defaultModelSecond.setValueAt("行动"+i, i, 0);
			}
	        
	        for (int i = 1; i <= actionCount; i++) {
	        	stateToActionMap.put("行动"+i, new String[stateCount][stateCount]);
			}
		}
		
		if(e.getSource() == actionFirm) {
			String action = actionList.getSelectedItem().toString();
			
			float A[][] = new float[stateCount][];
			float a[][] = new float[stateCount][];
			for (int i = 0; i < stateCount; i++) {
				a[i] = new float[stateCount];
				for (int j = 0; j < stateCount; j++) {
					if(defaultModelFirst.getValueAt(i+1,j+1) == null) {
						defaultModelFirst.setValueAt("0", i+1,j+1);
					}
					a[i][j] = Float.parseFloat(defaultModelFirst.getValueAt(i+1,
							j+1).toString());
				}
			}
			for (int i = 0; i < stateCount; i++) {
				A[i] = (float[]) a[i];
			}

			String B[][] = new String[stateCount][stateCount];
			for (int i = 0; i < A.length; i++) {
				for (int j = 0; j < A[0].length; j++) {
					B[i][j] = String.valueOf(A[i][j]);
				}
			}
			stateToActionMap.put(action, B);
		}
		
		if(e.getSource() == save) {

			String topicName = topicTxt.getText();
			
			try{
				Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.2:3336/markov?useUnicode=true&characterEncoding=utf8", "root","111");  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				
    			rs = stmt.executeQuery("select * from topic_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String ss ="delete from topic_table where id = '"+ index+"'";
                  	stmt.executeUpdate(ss);
                }
    			rs = stmt.executeQuery("select * from topic_table ");
        		if(rs.next()) {  
        			String actionC = (actionCount) + "";
	        		String stateC = (stateCount) + "";
	        		String ss = "insert into topic_table(id,topicName,actionCount,stateCount) values('"+index+"','"+topicName+"','"+actionC+"','"+stateC+"')";
				    stmt.executeUpdate(ss);
        		}
	        		
        		rs = stmt.executeQuery("select * from state_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String ss ="delete from state_table where id = '"+ index+"'";
                  	stmt.executeUpdate(ss);
                }
        		rs = stmt.executeQuery("select * from state_table ");
        		if(rs.next()) {  
        			for (int i = 0; i < actionCount; i++) {
        				String nn = "行动" + (i+1);
        				for (int j = 0; j < stateCount; j++) {
        					for (int k = 0; k < stateCount; k++) {
								String mm = defaultModelFirst.getValueAt(j+1,0).toString() + 
										defaultModelFirst.getValueAt(0,k+1).toString() ;
								String rr = stateToActionMap.get("行动" + (i+1))[j][k];
								String ss = "insert into state_table(id,actionName,stateChange,value) values('"+index+"','"+nn+"','"+mm+"','"+rr+"')";
	        				    stmt.executeUpdate(ss);
							}
						}
					}
        		}
	        	
        		rs = stmt.executeQuery("select * from benefit_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String ss ="delete from benefit_table where id = '"+ index+"'";
                  	stmt.executeUpdate(ss);
                }
    			rs = stmt.executeQuery("select * from benefit_table ");
        		if(rs.next()) {  
        			for (int i = 0; i < actionCount; i++) {
        				String aa = defaultModelSecond.getValueAt(i+1,0).toString();
						for (int j = 0; j < stateCount; j++) {
							String tt = defaultModelSecond.getValueAt(0,j+1).toString();
							String ddd = defaultModelSecond.getValueAt(i+1,j+1).toString();
							String ss = "insert into benefit_table(id,actionName,stateName,value) values('"+index+"','"+aa+"','"+tt+"','"+ddd+"')";
						    stmt.executeUpdate(ss);
						}
					}
        		}
        		
        		/*rs = stmt.executeQuery("select * from ideaValue_table where id = '"+index+"' ");
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
        		}*/
			} catch(Exception e1) {
                e1.printStackTrace();
            }  
		}
		
		if(e.getSource() == markovButton) {
			//将之前的决策结果清空
			mf.output.setText("");
			
	    	//转移概率表
	    	this.arry1 = new float[actionCount][stateCount][stateCount];
	    	for (int i = 0; i < actionCount; i++) {
				for (int j = 0; j < stateCount; j++) {
					for (int k = 0; k < stateCount; k++) {
						arry1[i][j][k] = Float.parseFloat(stateToActionMap.get("行动"+(i+1))[j][k]);
					}
				}
			}
	    	
	    	//受益表数据获取
			this.R = new float[stateCount][];
			this.r = new float[stateCount][];
			
	    	for (int i =0; i< stateCount; i++) {
	    		r[i] = new float[actionCount];
				for (int j = 0; j < actionCount; j++) {
					r[i][j] = Float.parseFloat(defaultModelSecond.getValueAt(j+1, i+1).toString());
					//System.out.println(r[i][j] + "\t");
				}
				//System.out.println("\r\n");
			}
	    	for (int i = 0; i < stateCount; i++) {		    		
	    			R[i] = (float[])r[i];	
			}
	    	
	    	
	    	float [][][] P;
			P=new float[arry1.length][arry1[0].length][arry1[0][0].length];
			for(int i=0;i<arry1.length;i++)
			{
				for(int j=0;j<arry1[0].length;j++)
					
				{
					for (int k = 0; k < arry1[0][0].length; k++)

					{
						P[i][j][k] = arry1[i][j][k];
//						System.out.print( P[i][j][k]+"\t");// 输出每个数组元素值
					}
//					System.out.print(" \n" );// 输出每个数组元素值
				}
//				System.out.print(" \n" );// 输出每个数组元素值
			}
			float discount=9;
			discount=discount/10;//折算率为：0.9
			BubbleSort mdp = new BubbleSort(this.mf);
			mdp.mdp_value_iteration(P, R, discount);
			
			resultT.setText(mf.output.getText());
			try{
				Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.0.2:3336/markov?useUnicode=true&characterEncoding=utf8", "root","111");  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				
				
				rs = stmt.executeQuery("select * from topic_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String sss ="delete from topic_table where id = '"+ index+"'";
                  	stmt.executeUpdate(sss);
                }
    			rs = stmt.executeQuery("select * from topic_table ");
    			if(rs.next()) {  
    				String topicName = topicTxt.getText();
    				String out = this.resultT.getText().toString();
        			String actionC = (actionCount) + "";
	        		String stateC = (stateCount) + "";
	        		String ss = "insert into topic_table(id,topicName,actionCount,stateCount,result) values('"+index+"','"+topicName+"','"+actionC+"','"+stateC+"','"+out+"')";
				    stmt.executeUpdate(ss);
        		}
            } catch(Exception e1) {
                e1.printStackTrace();
            }
		}
		/*
		if(e.getSource() == save) {
			String schemeNum = schemeNumList.getSelectedItem().toString();
			int schemeCount = Integer.parseInt(schemeNum);
			int name = schemeTableList.getSelectedIndex();
			String statementNum = statementNumList.getSelectedItem().toString();
			int statementCount = Integer.parseInt(statementNum);
			
			float A[][] = new float[row][];
			float a[][] = new float[row][];
			for (int i =0; i< row; i++) {
	    		a[i] = new float[statementCount];
				for (int j = 0; j < statementCount; j++) {
					a[i][j] = Float.parseFloat(firstDefaultModel.getValueAt(i, j).toString());
				}
			}
			for (int i = 0; i < row; i++) {		    		
    			A[i] = (float[])a[i];	
			}
			
			stateToSchemeMap.put(name + 1, A);
		}*/
		
	}
}
