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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javafx.scene.layout.Border;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import zlbyzc.sub3.juece.markov.BubbleSort;

public class PanelAA extends JPanel {
	Date date= new Date();//创建一个时间对象，获取到当前的时间
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间显示格式
	String str = sdf.format(date);//将当前时间格式化为需要的类型
	MainFrame mf = null;
	JPanel jp = new JPanel();	
	JScrollPane scroll  = new JScrollPane(this.jp,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	int index = (int)(Math.random()*10000);//产生一个四位数的随机数，作为主题的编号
	
	JPanel panelName = new JPanel();
	JPanel panelTopicName = new JPanel();
	JPanel panelActionStateNum = new JPanel();
	
	JPanel jp1 = new JPanel();	
	JPanel jp2 = new JPanel();	
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
	
	JPanel rrrPanel = new JPanel();
	JLabel rrrNum = new JLabel("马尔科夫决策结果");
	
	JLabel stateTableName = new JLabel("                  ---状态转移表---                  选择行动");//主题名称标签
	public Vector<String> actionVector = new Vector<String>();//方案向量
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
	JButton example = new JButton("实例");//实例演示
	
	JTextPane resultT = new JTextPane();//主题输入文本框
	JScrollPane resultTxt  = new JScrollPane(resultT,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	int rowRow = -1;
	int lieLie = -1;
	String oldValue = "";
	int huahua = 0;
	int chuzhi = huahua;
	
	float R[][];
	float r[][];//受益
	Map<String, String[][]> stateToActionMap = new HashMap<String, String[][]>();
	float [][][] arry1;//转移概率
	
	public PanelAA(MainFrame mf) {
		this.mf = mf;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		jp.setLayout(new BoxLayout(this.jp, BoxLayout.Y_AXIS));		;//指定组件应该从上到下放置setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		
		panelName.setLayout(new BoxLayout(panelName, BoxLayout.X_AXIS));
		panelTopicName.setLayout(new BoxLayout(panelTopicName, BoxLayout.X_AXIS));
		panelActionStateNum.setLayout(new BoxLayout(panelActionStateNum, BoxLayout.X_AXIS));
		
		jp1.setLayout(new BoxLayout(jp1, BoxLayout.X_AXIS));
		rrrPanel.setLayout(new BoxLayout(rrrPanel, BoxLayout.X_AXIS));
		jp2.setLayout(new BoxLayout(jp2, BoxLayout.Y_AXIS));
		panelStateTableName.setLayout(new BoxLayout(panelStateTableName, BoxLayout.X_AXIS));
		panelStateTable.setLayout(new BoxLayout(panelStateTable, BoxLayout.X_AXIS));
		panelBenefitTableName.setLayout(new BoxLayout(panelBenefitTableName, BoxLayout.X_AXIS));
		panelBenefitTable.setLayout(new BoxLayout(panelBenefitTable, BoxLayout.X_AXIS));
		
		panelResultButton.setLayout(new BoxLayout(panelResultButton, BoxLayout.X_AXIS));
		panelResultTxt.setLayout(new BoxLayout(panelResultTxt, BoxLayout.Y_AXIS));
		
		name.setFont(new Font("Dialog",0,18));
		topicName.setFont(new Font("Dialog",10,16));
		topicTxt.setFont(new Font("Dialog",10,16));
        topicTxt.setMinimumSize(new Dimension(300,25));//将此组件的最大大小设置为一个常量值。
        topicTxt.setMaximumSize(new Dimension(400,30));//将此组件的最大小设置为一个常量值。

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
        	defaultModelFirst.setValueAt(""+"状态"+i, 0, i);
        	defaultModelFirst.setValueAt("状态"+i, i, 0);
		}
        scoreTableFirst.setRowHeight(20);//表格中每一的高度
        scoreTableFirst.setTableHeader(null);//表头不要
        scoreTableFirst.setFont(new Font("Dialog",0,14));
      	DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();  
        r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);   
        scoreTableFirst.setDefaultRenderer(Object.class,r);
        scoreScrollFirst.setMinimumSize(new Dimension(500,100));
        scoreScrollFirst.setMaximumSize(new Dimension(600,100));
        
        benefitTableName.setFont(new Font("Dialog",10,16));
        defaultModelSecond.setValueAt("行动\\状态", 0, 0);
        for (int i = 1; i <= stateCount; i++) {
        	defaultModelSecond.setValueAt("状态"+i, 0, i);
		}
        for (int i = 1; i <= actionCount; i++) {
        	defaultModelSecond.setValueAt("行动"+i, i, 0);
		}
        scoreTableSecond.setRowHeight(20);//表格中每一的高度
        scoreTableSecond.setTableHeader(null);//表头不要
        scoreTableSecond.setFont(new Font("Dialog",10,14));
        scoreTableSecond.setDefaultRenderer(Object.class,r);
        scoreScrollSecond.setMinimumSize(new Dimension(500,120));
        scoreScrollSecond.setMaximumSize(new Dimension(680,120));
        
        resultTxt.setFont(new Font("Dialog",10,16));
		resultTxt.setMinimumSize(new Dimension(200,300));//将此组件的最大大小设置为一个常量值。
		resultTxt.setMaximumSize(new Dimension(400,300));//将此组件的最大小设置为一个常量值。
		rrrNum.setFont(new Font("Dialog",10,16));
		
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
    	
    	panelStateTable.add(scoreScrollFirst);
    	panelStateTableName.add(Box.createRigidArea(new Dimension(20, 60))); 
    	panelStateTable.add(actionFirm);
    	
        panelBenefitTableName.add(benefitTableName);
        
        panelBenefitTable.add(scoreScrollSecond);
        
        
      //  panelResultButton.add(Box.createRigidArea(new Dimension(80, 60)));
        panelResultButton.add(example);
        panelResultButton.add(Box.createRigidArea(new Dimension(80, 60)));
        panelResultButton.add(save);
        panelResultButton.add(Box.createRigidArea(new Dimension(80, 60))); 
        panelResultButton.add(markovButton);
       
        rrrPanel.add(rrrNum);
        panelResultTxt.add(rrrPanel);
        panelResultTxt.add(resultTxt);
         
  
        jp.add(panelName);
        jp.add(panelTopicName);			
        jp.add(panelActionStateNum);
        
        jp1.add(jp2);
        jp1.add(Box.createRigidArea(new Dimension(60, 60))); 
        jp2.add(panelStateTableName);				
        jp2.add(panelStateTable);				
        jp2.add(panelBenefitTableName);	 		
        jp2.add(panelBenefitTable);
        jp1.add(panelResultTxt);
        jp.add(jp1);
        
        this.add(this.scroll);
        this.add(panelResultButton);			
        
		this.addListListener();
        this.addEvent();
        
        defaultModelFirst.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if(chuzhi == huahua) {
					//处理表格的命名联动一致
					int rowGet = e.getFirstRow();
					int lieGet = e.getColumn();
					String valueGet = defaultModelFirst.getValueAt(rowGet, lieGet).toString();
					
					if(rowGet == 0 && lieGet == 0) {
						return;
					}
					if((lieGet == rowRow || rowGet == lieLie)&&(valueGet==oldValue)) {
						return;
					}
						
					if(lieGet == 0) {
						rowRow = rowGet;
						oldValue = valueGet;
						defaultModelFirst.setValueAt(valueGet, 0, rowGet);
						defaultModelSecond.setValueAt(valueGet, 0, rowGet);
				    } 
					
					if(rowGet == 0) {
						lieLie = lieGet;
						oldValue = valueGet;
						defaultModelFirst.setValueAt(valueGet, lieGet, 0);
						defaultModelSecond.setValueAt(valueGet, 0, lieGet);
			        }
				}
			}} ); 
        
        defaultModelSecond.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				if(chuzhi == huahua) {
					//处理表格的命名联动一致
					int rowGet = e.getFirstRow();
					int lieGet = e.getColumn();
					String valueGet = defaultModelSecond.getValueAt(rowGet, lieGet).toString();
					
					if(rowGet == 0 && lieGet == 0) {
						return;
					}
					if(valueGet == oldValue) {
						return;
					}
						
					if(rowGet == 0) {
						defaultModelFirst.setValueAt(valueGet, lieGet, 0);
						defaultModelFirst.setValueAt(valueGet, 0, lieGet);
			        }
					
					if(lieGet == 0) {
						int nnn = actionList.getSelectedIndex();
						String[][] sss = stateToActionMap.get(actionVector.get(rowGet-1));
						stateToActionMap.remove(actionVector.get(rowGet-1));
						actionVector.removeAllElements();
						
						for (int i = 1; i <= actionCount; i++) {
							String valueNew = defaultModelSecond.getValueAt(i, 0).toString();
							actionVector.add(valueNew);
					    }
						stateToActionMap.put(actionVector.get(rowGet-1), sss);
						actionList.setSelectedIndex(nnn);
						actionList.updateUI();// 更新内容操作
				   }
				}
			}} ); 
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
	    example.addActionListener(new Listener());
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
	
		if(e.getSource() == example) {
			huahua++;
			String [] stateDemo = {
					"状态1（飞机在位置1）","状态2（飞机在位置2）","状态3（飞机在位置3）"
			};
			String [] actionDemo = {
				    "行动1（飞机向前飞行）", "行动2（飞机向后飞行）", 
				    "行动3（飞机向左飞行）", "行动4（飞机向右飞行）"
			};
			actionVector.removeAllElements();
			for (int i = 1; i <= actionCount; i++) {
				actionVector.add(actionDemo[i-1]);
			}
			//actionList.setSelectedIndex(0);
			actionList.updateUI();// 更新内容操作
			
			defaultModelFirst.setRowCount(stateCount+1);
			defaultModelFirst.setColumnCount(stateCount+1);
			defaultModelFirst.setValueAt("(请输入行动名称)", 0, 0);
			for (int i = 1; i <= stateCount; i++) {
				defaultModelFirst.setValueAt(stateDemo[i-1], 0, i);
		        defaultModelFirst.setValueAt(stateDemo[i-1], i, 0);
			}
			
			defaultModelSecond.setRowCount(actionCount+1);
			defaultModelSecond.setColumnCount(stateCount+1);
			defaultModelSecond.setValueAt("行动\\状态", 0, 0);
	        for (int i = 1; i <= stateCount; i++) {
	        	defaultModelSecond.setValueAt(stateDemo[i-1], 0, i);
			}
	        for (int i = 1; i <= actionCount; i++) {
	        	defaultModelSecond.setValueAt(actionDemo[i-1], i, 0);
			}
	        String C[][][]={
	        		{{"0.4","0.5","0.1"},{"0.2","0.6","0.2"},{"0.7","0.2","0.1"}},
	        		{{"0.0","0.9","0.1"},{"0.5","0.5","0.0"},{"0.7","0.2","0.1"}},
	        		{{"0.5","0.5","0.0"},{"0.5","0.3","0.2"},{"0.4","0.5","0.1"}},
	        		{{"0.5","0.3","0.2"},{"0.5","0.4","0.1"},{"0.5","0.5","0.0"}}			
	        			};  
	        for (int i = 1; i <= actionCount; i++) {
	        	stateToActionMap.put(actionDemo[i-1], new String[stateCount][stateCount]);
			}
	        for (int i = 1; i <= actionCount; i++) {
	        	stateToActionMap.put(actionDemo[i-1], C[i-1]);
			}
	        actionList.setSelectedIndex(0);//下拉框的默认显示
	        topicTxt.setText("马尔科夫案例");
	        actionNumTxt.setText("4");
	        stateNumTxt.setText("3");
	        String B[][]={{"5.2","-1","5.0"},{"-5","4.1","6.0"},{"4.0","4.0","6.0"},{"5.0","3.0","4.0"}		
	        };
	        for(int i = 0; i < actionCount ; i++)
	        for (int j = 0; j < stateCount; j++) {
	        defaultModelSecond.setValueAt(B[i][j], i+1, j+1);
						    
			}
	        chuzhi = huahua;
		}
		
		if(e.getSource() == firm) {
			scoreTableFirst.editCellAt(stateCount+2,0);
			scoreTableSecond.editCellAt(actionCount+2,0);
			
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
		//	actionList.setSelectedIndex(0);
			actionList.updateUI();// 更新内容操作
			
			defaultModelFirst.setRowCount(stateCount+1);
			defaultModelFirst.setColumnCount(stateCount+1);
			defaultModelFirst.setValueAt("(请输入行动名称)", 0, 0);
			for (int i = 1; i <= stateCount; i++) {
				defaultModelFirst.setValueAt(""+"状态"+i, 0, i);
		        defaultModelFirst.setValueAt("状态"+i, i, 0);
			}
			
			defaultModelSecond.setRowCount(actionCount+1);
			defaultModelSecond.setColumnCount(stateCount+1);
			defaultModelSecond.setValueAt("行动\\状态", 0, 0);
	        for (int i = 1; i <= stateCount; i++) {
	        	defaultModelSecond.setValueAt("状态"+i, 0, i);
			}
	        for (int i = 1; i <= actionCount; i++) {
	        	defaultModelSecond.setValueAt("行动"+i, i, 0);
			}
	        
	        for (int i = 1; i <= actionCount; i++) {
	        	stateToActionMap.put("行动"+i, new String[stateCount][stateCount]);
			}
	        actionList.setSelectedIndex(0);
		}
		
		if(e.getSource() == actionFirm) {
			scoreTableFirst.editCellAt(stateCount+2,0);
			scoreTableSecond.editCellAt(actionCount+2,0);
			
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
			scoreTableFirst.editCellAt(stateCount+2,0);
			scoreTableSecond.editCellAt(actionCount+2,0);
			/////////
//			String action = actionList.getSelectedItem().toString();
//			
//			float A[][] = new float[stateCount][];
//			float a[][] = new float[stateCount][];
//			for (int i = 0; i < stateCount; i++) {
//				a[i] = new float[stateCount];
//				for (int j = 0; j < stateCount; j++) {
//					if(defaultModelFirst.getValueAt(i+1,j+1) == null) {
//						defaultModelFirst.setValueAt("0", i+1,j+1);
//					}
//					a[i][j] = Float.parseFloat(defaultModelFirst.getValueAt(i+1,
//							j+1).toString());
//				}
//			}
//			for (int i = 0; i < stateCount; i++) {
//				A[i] = (float[]) a[i];
//			}
//
//			String B[][] = new String[stateCount][stateCount];
//			for (int i = 0; i < A.length; i++) {
//				for (int j = 0; j < A[0].length; j++) {
//					B[i][j] = String.valueOf(A[i][j]);
//				}
//			}
//			stateToActionMap.put(action, B);
			////////////
			
			String topicName = topicTxt.getText();
//			boolean flag = true;
			try{
				Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("markov"));	
				//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/markov?useUnicode=true&characterEncoding=utf8", "root","111");  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				
//				if(flag) {
					rs = stmt.executeQuery("select * from topic_table where id = '"+index+"' ");
	                if(rs.next()) { 
	                	String ss ="delete from topic_table where id = '"+ index+"'";
	                  	stmt.executeUpdate(ss);
	                }
        			String actionC = (actionCount) + "";
	        		String stateC = (stateCount) + "";
	        		String ss = "insert into topic_table(id,topicName,actionCount,stateCount,date) values('"+index+"','"+topicName+"','"+actionC+"','"+stateC+"','"+str+"')";
				    stmt.executeUpdate(ss);
		        		
	        		rs = stmt.executeQuery("select * from state_table where id = '"+index+"' ");
	                if(rs.next()) { 
	                	String ss1 ="delete from state_table where id = '"+ index+"'";
	                  	stmt.executeUpdate(ss1);
	                }
        			for (int i = 0; i < actionCount; i++) {
        				String nn = actionVector.get(i);
        				for (int j = 0; j < stateCount; j++) {
        					for (int k = 0; k < stateCount; k++) {
								String mm = defaultModelFirst.getValueAt(j+1,0).toString() + "--->" +
										defaultModelFirst.getValueAt(0,k+1).toString() ;
								String rr = stateToActionMap.get(nn)[j][k];
								String ss2 = "insert into state_table(id,actionName,stateChange,value) values('"+index+"','"+nn+"','"+mm+"','"+rr+"')";
	        				    stmt.executeUpdate(ss2);
							}
						}
					}
	        		
		        	
	        		rs = stmt.executeQuery("select * from benefit_table where id = '"+index+"' ");
	                if(rs.next()) { 
	                	String ss33 ="delete from benefit_table where id = '"+ index+"'";
	                  	stmt.executeUpdate(ss33);
	                }
        			for (int i = 0; i < actionCount; i++) {
        				String aa = defaultModelSecond.getValueAt(i+1,0).toString();
						for (int j = 0; j < stateCount; j++) {
							String tt = defaultModelSecond.getValueAt(0,j+1).toString();
							String ddd = defaultModelSecond.getValueAt(i+1,j+1).toString();
							String ss3 = "insert into benefit_table(id,actionName,stateName,value) values('"+index+"','"+aa+"','"+tt+"','"+ddd+"')";
						    stmt.executeUpdate(ss3);
						}
					}
	        		JOptionPane.showMessageDialog(this, "保存成功!", "提示信息", JOptionPane.INFORMATION_MESSAGE);
	//			}
			} catch(Exception e1) {
                e1.printStackTrace();
            }  
		}
		
		if(e.getSource() == markovButton) {
			scoreTableFirst.editCellAt(stateCount+2,0);
			scoreTableSecond.editCellAt(actionCount+2,0);
			//将之前的决策结果清空
			mf.output.setText("");
			mf.output.setFont(new Font("Dialog",10,16));
			
	    	//转移概率表
	    	this.arry1 = new float[actionCount][stateCount][stateCount];
	    	for (int i = 0; i < actionCount; i++) {
				for (int j = 0; j < stateCount; j++) {
					for (int k = 0; k < stateCount; k++) {
						arry1[i][j][k] = Float.parseFloat(stateToActionMap.get(actionVector.get(i))[j][k]);
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
			mf.output.setFont(new Font("Dialog",10,16));
			resultT.setFont(new Font("Dialog",10,16));
			resultT.setText(mf.output.getText());	
			try{
				Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("markov"));	
				//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/markov?useUnicode=true&characterEncoding=utf8", "root","111");	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				
				
				rs = stmt.executeQuery("select * from topic_table where id = '"+index+"' ");
                if(rs.next()) { 
                	String sss ="delete from topic_table where id = '"+ index+"'";
                  	stmt.executeUpdate(sss);
                }
				String topicName = topicTxt.getText();
				String out = this.resultT.getText().toString();
    			String actionC = (actionCount) + "";
        		String stateC = (stateCount) + "";
        		String ss = "insert into topic_table(id,topicName,actionCount,stateCount,result,date) values('"+index+"','"+topicName+"','"+actionC+"','"+stateC+"','"+out+"','"+str+"')";
			    stmt.executeUpdate(ss);
            } catch(Exception e1) {
                e1.printStackTrace();
            }
		}
	
		
	}
}
