package zlbyzc.sub3.juece.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.liukan.mgraph.mgraphx;
import org.liukan.mgraph.util.dbIO;

import zlbyzc.sub3.juece.decisiontree.ID3;

public class PanelA2 extends JPanel {
	Date date= new Date();//创建一个时间对象，获取到当前的时间
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间显示格式
	String str = sdf.format(date);//将当前时间格式化为需要的类型
	MainFrame mf = null;
	JPanel jp = new JPanel();
	JPanel jp1 = new JPanel();
	
	JScrollPane scroll  = new JScrollPane(this.jp,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	int index = (int) (Math.random() * 10000);// 产生一个四位数的随机数，作为主题的编号
	JPanel panel2 = new JPanel();

	JPanel panelName = new JPanel();
	JPanel panelTopicName = new JPanel();
	JPanel panelSchemeName = new JPanel();
	JPanel panelChange = new JPanel();
	JPanel panelScore = new JPanel();
	JPanel panelButton = new JPanel();

	JLabel name = new JLabel("决策树(2)");// 主题名称标签
	JLabel topicName = new JLabel("主题名称：");// 主题名称标签
	JLabel actionNum = new JLabel("行动方案数量：");// 行动方案数量标签
	JLabel stateNum = new JLabel("状态数量：");// 状态数量标签
	JLabel jueceLevel = new JLabel("子行动数量选择：");// 状态数量标签
	
	

	// JLabel schemeName = new JLabel("方案名称：");
	// JLabel ideaNum = new JLabel("设置属性数量：");

	JTextPane topicTxt = new JTextPane();// 主题输入文本框
	JTextPane actionNumTxt = new JTextPane();// 主题输入文本框
	JTextPane stateNumTxt = new JTextPane();// 主题输入文本框
	JTextPane jueceLeveTxt = new JTextPane();// 主题输入文本框
	// JTextPane schemeTxt = new JTextPane();

	boolean frameFlag = false;

	// Vector<String> jueceLevelVector = new Vector<String>();//准则向量
	// public JComboBox<String> jueceLevelList = new
	// JComboBox<String>(jueceLevelVector);//准则列单

	// JLabel ideaMessage = new JLabel("准则信息描述：");//主题名称标签
	/// Vector<String> ideaVector = new Vector<String>();//准则向量
	// JComboBox<String> ideaList = new JComboBox<String>(ideaVector);//准则列单
	// JTextPane ideaTxt = new JTextPane();//主题输入文本框

	// String[] pai = {"(空)","(空)","(空)","方案是否可行"};
	public DefaultTableModel defaultModel = new DefaultTableModel(4, 4);
	JTable scoreTable = new JTable(defaultModel);
	JScrollPane scoreScroll = new JScrollPane(scoreTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	JButton save = new JButton("保存");// 保存基本信息
	JButton firm = new JButton("确定");// 保存基本信息

	JButton juece = new JButton("决策");// 保存基本信息
	JButton test = new JButton("实例");// 保存基本信息

	public Map<Integer, String> ideaMap = new HashMap<Integer, String>();

	public int lie = 4;
	int row = 4;
	int levelNum = 1;
	
	int huahua = 0;
	int chuzhi = huahua;
	int rowGet;
	int lieGet;
	int YY = -10;
	String oldValue = "";
//	RR rr = new RR();

	List<String> pathList = new ArrayList<String>();
	////////// 算法数据
	Object[] array;
	Object[][] arrayString;

	public PanelA2(MainFrame mf) {
		this.mf = mf;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));// 指定组件应该从上到下放置
		jp.setLayout(new BoxLayout(this.jp, BoxLayout.Y_AXIS));
		jp1.setLayout(new BoxLayout(this.jp1, BoxLayout.Y_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

		panelName.setLayout(new BoxLayout(panelName, BoxLayout.X_AXIS));
		panelTopicName.setLayout(new BoxLayout(panelTopicName, BoxLayout.X_AXIS));
		panelSchemeName.setLayout(new BoxLayout(panelSchemeName, BoxLayout.X_AXIS));
		// panelChange.setLayout(new BoxLayout(panelChange, BoxLayout.X_AXIS));
		panelScore.setLayout(new BoxLayout(panelScore, BoxLayout.X_AXIS));
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));

		juece.setPreferredSize(new Dimension(60, 16));
		
		test.setPreferredSize(new Dimension(60, 16));

		name.setFont(new Font("Dialog", 0, 18));
		topicName.setFont(new Font("Dialog", 0, 16));
		actionNum.setFont(new Font("Dialog", 0, 16));
		stateNum.setFont(new Font("Dialog", 0, 16));
		jueceLevel.setFont(new Font("Dialog", 0, 16));

		topicTxt.setFont(new Font("Dialog", 0, 16));
		topicTxt.setMinimumSize(new Dimension(480, 25));// 将此组件的最大大小设置为一个常量值。
		topicTxt.setMaximumSize(new Dimension(620, 30));// 将此组件的最大小设置为一个常量值。
		actionNumTxt.setFont(new Font("Dialog", 0, 16));
		actionNumTxt.setMinimumSize(new Dimension(50, 25));// 将此组件的最大大小设置为一个常量值。
		actionNumTxt.setMaximumSize(new Dimension(80, 30));// 将此组件的最大小设置为一个常量值。
		stateNumTxt.setFont(new Font("Dialog", 0, 16));
		stateNumTxt.setMinimumSize(new Dimension(50, 25));// 将此组件的最大大小设置为一个常量值。
		stateNumTxt.setMaximumSize(new Dimension(80, 30));// 将此组件的最大小设置为一个常量值。
		jueceLeveTxt.setFont(new Font("Dialog", 0, 16));
		jueceLeveTxt.setMinimumSize(new Dimension(50, 25));// 将此组件的最大大小设置为一个常量值。
		jueceLeveTxt.setMaximumSize(new Dimension(80, 30));// 将此组件的最大小设置为一个常量值。

		// schemeName.setFont(new Font("Dialog",0,16));
		// schemeTxt.setFont(new Font("Dialog",0,16));
		// schemeTxt.setMinimumSize(new Dimension(450,25));
		// schemeTxt.setMaximumSize(new Dimension(600,30));

		for (int i = 1; i < lie; i++) {
			defaultModel.setValueAt("状态" + i, 0, i);
		}
		defaultModel.setValueAt("概率", 1, 0);
		for (int i = 2; i < row; i++) {
			defaultModel.setValueAt("行动方案" + (i - 1), i, 0);
		}
		scoreTable.setRowHeight(30);// 表格中每一的高度
		scoreTable.setTableHeader(null);// 表头不要
		scoreTable.setFont(new Font("Dialog", 0, 14));
		scoreTable.isEditing();
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		scoreTable.setDefaultRenderer(Object.class, r);
		// scoreScroll.setMinimumSize(new Dimension(400,250));
		// scoreScroll.setMaximumSize(new Dimension(500,350));
		scoreScroll.setMinimumSize(new Dimension(100, 25));
		scoreScroll.setMaximumSize(new Dimension(600, 300));

		//panelName.add(Box.createRigidArea(new Dimension(0, 60)));
		panelName.add(name);
		//panelName.add(Box.createRigidArea(new Dimension(150, 60)));

		//panelTopicName.add(Box.createRigidArea(new Dimension(0, 60)));
		panelTopicName.add(topicName);
		panelTopicName.add(Box.createRigidArea(new Dimension(20, 60)));
		panelTopicName.add(topicTxt);
		//panelTopicName.add(Box.createRigidArea(new Dimension(150, 60)));

		//panelSchemeName.add(Box.createRigidArea(new Dimension(0, 60)));
		panelSchemeName.add(actionNum);
		panelSchemeName.add(Box.createRigidArea(new Dimension(10, 60)));
		panelSchemeName.add(actionNumTxt);
		panelSchemeName.add(Box.createRigidArea(new Dimension(25, 60)));
		panelSchemeName.add(stateNum);
		panelSchemeName.add(Box.createRigidArea(new Dimension(10, 60)));
		panelSchemeName.add(stateNumTxt);
		panelSchemeName.add(Box.createRigidArea(new Dimension(25, 60)));
		panelSchemeName.add(jueceLevel);
		panelSchemeName.add(Box.createRigidArea(new Dimension(10, 60)));
		panelSchemeName.add(jueceLeveTxt);
		panelSchemeName.add(Box.createRigidArea(new Dimension(25, 60)));
		panelSchemeName.add(firm);
		//panelSchemeName.add(Box.createRigidArea(new Dimension(150, 60)));

		/*
		 * panelChange.add(addLie); panelChange.add(Box.createRigidArea(new
		 * Dimension(30, 60))); panelChange.add(removeLie);
		 * panelChange.add(Box.createRigidArea(new Dimension(100, 60)));
		 * panelChange.add(addRow); panelChange.add(Box.createRigidArea(new
		 * Dimension(30, 60))); panelChange.add(removeRow);
		 */

		//panelScore.add(Box.createRigidArea(new Dimension(0, 60)));
		panelScore.add(scoreScroll);
		//panelScore.add(Box.createRigidArea(new Dimension(150, 60)));

		//panelButton.add(Box.createRigidArea(new Dimension(0, 60)));
		panelButton.add(test);
		panelButton.add(Box.createRigidArea(new Dimension(100, 60)));
		panelButton.add(save);
		panelButton.add(Box.createRigidArea(new Dimension(100, 60)));
		panelButton.add(juece);
		//panelButton.add(Box.createRigidArea(new Dimension(100, 60)));

		//this.add(Box.createRigidArea(new Dimension(10, 10)));
	
		
		//this.add(Box.createRigidArea(new Dimension(10, 10)));
		//this.add(Box.createRigidArea(new Dimension(10, 10)));
		// this.add( Box.createVerticalGlue() );

		jp.add(panelName);
		//jp.add(Box.createVerticalStrut(10));
		jp.add(panelTopicName);
		//jp.add(Box.createVerticalStrut(10));
		jp.add(panelSchemeName);
		 add(Box.createVerticalStrut(10));
		// add(panelChange);
		//jp.add(Box.createVerticalStrut(10));
		jp.add(panelScore);
		jp.add(Box.createVerticalStrut(10));
		
		jp1.add(this.scroll);
		jp1.add(panelButton);
		
		this.add(jp1);
		addEvent();
		
//		Thread thread = new Thread(rr);
//	    thread.start();
//	     
		defaultModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				/**
		          * 处理表格数据变化事件
		          */
				int jj = Integer.parseInt(jueceLeveTxt.getText());
				int aa = Integer.parseInt(actionNumTxt.getText());
				int ss = Integer.parseInt(stateNumTxt.getText());								
				if(e.getColumn() == 0) {
					rowGet = e.getFirstRow();
			        lieGet = e.getColumn();
			         // 获取所选数据的行数
					if(chuzhi == huahua) {

				        String tttt = defaultModel.getValueAt(rowGet, lieGet).toString();
				        int yy = ((rowGet-2)/jj);
				        if(yy == YY && oldValue == tttt){
				        	return;
				        }
				        YY = yy;
				        oldValue = tttt;
				        if((rowGet-2)>=0){
				        	 for (int i = 0; i < jj; i++) {
//					        		 System.out.println(tttt);
//					        		 System.out.println(2+i+jj*yy + "");
				        		 int dd = 2+i+jj*yy;
						         defaultModel.setValueAt(tttt, dd, 0);
							 } 
				        }
						         // 系统重新绘制表格
				         scoreTable.repaint();
			        } 
				}	
				
				if(e.getColumn() == 1) {
		        	rowGet = e.getFirstRow();
			        lieGet = e.getColumn();
			         // 获取所选数据的行数
					if(chuzhi == huahua) {

				        String tttt = defaultModel.getValueAt(rowGet, lieGet).toString();
				        int yy = ((rowGet-2)%jj);
				        if(yy == YY && oldValue == tttt ){
				        	return;
				        }
				        YY = yy;
				        oldValue = tttt;
				        if((rowGet-2)>=0){
				        	 for (int i = 0; i < aa; i++) {
//						        		 System.out.println(tttt);
//						        		 System.out.println(2+i+jj*yy + "");
				        		 int dd = 2+i*jj+yy;
						         defaultModel.setValueAt(tttt, dd, 1);
							 } 
				        }
						         // 系统重新绘制表格
				         scoreTable.repaint();
		            }
				}
			}} ); 
	}
 
	 
	public void addEvent() {
		// 决策树的监听组件
		test.addActionListener(new Listener());
		firm.addActionListener(new Listener());
		save.addActionListener(new Listener());
		juece.addActionListener(new Listener());
	}

	/**
	 * 监听窗口的类
	 */
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// 决策树
			APerformed(e); // A页
		}
	}

	/**
	 * 按钮事件
	 */
	public void APerformed(ActionEvent e) {

		if (e.getSource() == firm) {
			huahua++;
			if (Integer.parseInt(jueceLeveTxt.getText()) == 1) {

				row = Integer.parseInt(actionNumTxt.getText()) + 2;
				;
				lie = Integer.parseInt(stateNumTxt.getText()) + 1;
				defaultModel.setRowCount(row);
				defaultModel.setColumnCount(lie);
				for (int i = 1; i < row; i++) {
					for (int j = 1; j < lie; j++) {
						defaultModel.setValueAt("", i, j);
					}
				}
				for (int i = 1; i < lie; i++) {
					defaultModel.setValueAt("状态" + i, 0, i);
				}
				defaultModel.setValueAt("概率", 1, 0);
				for (int i = 2; i < row; i++) {
					defaultModel.setValueAt("行动方案" + (i - 1), i, 0);
				}
				;
			}

			if (Integer.parseInt(jueceLeveTxt.getText()) >= 2) {
				defaultModel.setValueAt("", 0, 1);
				row = Integer.parseInt(actionNumTxt.getText()) * (Integer.parseInt(jueceLeveTxt.getText())) + 2;
				;
				lie = Integer.parseInt(stateNumTxt.getText()) + 2;
				defaultModel.setRowCount(row);
				defaultModel.setColumnCount(lie);
				for (int i = 1; i < row; i++) {
					for (int j = 1; j < lie - 1; j++) {
						defaultModel.setValueAt("", i, j + 1);
					}
				}
				for (int i = 0; i < lie - 2; i++) {
					defaultModel.setValueAt("状态" + (i + 1), 0, i + 2);
				}
				defaultModel.setValueAt("", 1, 1);
				defaultModel.setValueAt("概率", 1, 0);

				for (int i = 0; i < Integer.parseInt(actionNumTxt.getText()); i++)
					for (int j = 0; j < Integer.parseInt(jueceLeveTxt.getText()); j++) {
						defaultModel.setValueAt("行动方案" + (i + 1),
								i * (Integer.parseInt(jueceLeveTxt.getText())) + 2 + j, 0);
					}
				for (int i = 0; i < Integer.parseInt(actionNumTxt.getText()); i++)
					for (int j = 0; j < Integer.parseInt(jueceLeveTxt.getText()); j++) {
						defaultModel.setValueAt("子行动方案" + (j + 1), i * Integer.parseInt(jueceLeveTxt.getText()) + 2 + j,
								1);
					}
			}
			chuzhi = huahua;
		}

		if (e.getSource() == test) {	
			huahua++;
			row=6;
			lie=4;
			topicTxt.setText("对某地实施开展决策");
			actionNumTxt.setText("2");
			stateNumTxt.setText("2");
			jueceLeveTxt.setText("2");
			defaultModel.setRowCount(row);
			defaultModel.setColumnCount(lie);
			defaultModel.setValueAt("", 0, 0);
			defaultModel.setValueAt("", 0, 1);
			defaultModel.setValueAt("天气好", 0, 2);
			defaultModel.setValueAt("天气坏", 0, 3);
			defaultModel.setValueAt("概率", 1, 0);
			defaultModel.setValueAt("从A1点出战", 2, 0);
			defaultModel.setValueAt("从A1点出战", 3, 0);
			defaultModel.setValueAt("从A2点出战", 4, 0);
			defaultModel.setValueAt("从A2点出战", 5, 0);
			defaultModel.setValueAt("使用武器B1", 2, 1);
			defaultModel.setValueAt("使用武器B2", 3, 1);
			defaultModel.setValueAt("使用武器B1", 4, 1);
			defaultModel.setValueAt("使用武器B2", 5, 1);
			String[] A= {"0.4 ","0.6","5","40","8","41","1","30","6","50",};
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 2; j++)					
				defaultModel.setValueAt(A[i*2+j], i+1, j+2);	
			}
			chuzhi = huahua;
		}
		
		if (e.getSource() == save) {
			scoreTable.editCellAt(row+8,0);
			String topicName = topicTxt.getText();

			String actionNum = actionNumTxt.getText();
			String stateNum = stateNumTxt.getText();
			String jueceLeve = jueceLeveTxt.getText();
	//		boolean flag = true;
			try {
				 Connection conn =
				 DriverManager.getConnection(mf.BR.setting.getConnURL("juece_2"));
//				Connection conn = DriverManager.getConnection(
//						"jdbc:mysql://localhost:3306/juece_2?useUnicode=true&characterEncoding=utf8", "root", "111");
				Statement stmt = conn.createStatement();
				ResultSet rs;
				
				rs = stmt.executeQuery("select * from topic_table");
//				while(rs.next()) { 
//                	String ss = rs.getString("topicName");
//                  	if(topicName.equals(ss)){
//                  		flag = false;
//                  		JOptionPane.showMessageDialog(this, "主题名已存在！请重新输入主题!", "提示信息", JOptionPane.INFORMATION_MESSAGE);
//                  	}
//                }
				
//				if(flag) {
					// topic
					rs = stmt.executeQuery("select * from topic_table where id = '" + index + "' ");
					if (rs.next()) {
						String ss = "delete from topic_table where id = '" + index + "'";
						stmt.executeUpdate(ss);
					}
					stmt.executeUpdate("insert into topic_table(id,topicName,actionNum,stateNum,childActionNum,date) values('"
								+ index + "','" + topicName + "','" + actionNum + "','" + stateNum + "','" + jueceLeve
								+ "','" + str + "') ");
					// chance
					rs = stmt.executeQuery("select * from chance_table where id = '" + index + "' ");
					if (rs.next()) {
						String ss = "delete from chance_table where id = '" + index + "'";
						stmt.executeUpdate(ss);
					}
					int jueceL = Integer.parseInt(jueceLeve);
					if (jueceL == 1) {
						for (int i = 1; i < lie; i++) {
							String state = defaultModel.getValueAt(0, i).toString();
							String chance = defaultModel.getValueAt(1, i).toString();
							String ss = "insert into chance_table(id,state,chance) values('" + index + "','" + state
									+ "','" + chance + "')";
							stmt.executeUpdate(ss);
						}
					} else {
						for (int i = 1; i < lie - 1; i++) {
							String state = defaultModel.getValueAt(0, 1 + i).toString();
							String chance = defaultModel.getValueAt(1, 1 + i).toString();
							String ss = "insert into chance_table(id,state,chance) values('" + index + "','" + state
									+ "','" + chance + "')";
							stmt.executeUpdate(ss);
						}
					}
					
					// benefit
					rs = stmt.executeQuery("select * from benefit_table where id = '" + index + "' ");
					if (rs.next()) {
						String ss = "delete from benefit_table where id = '" + index + "'";
						stmt.executeUpdate(ss);
					}
					// int actionN = Integer.parseInt(actionNumTxt.getText());
					// int stateN = Integer.parseInt(stateNumTxt.getText());
					int jueceLe = Integer.parseInt(jueceLeveTxt.getText());
					if (jueceLe == 1) {
						for (int i = 1; i < row - 1; i++) {
							String ff = defaultModel.getValueAt(i + 1, 0).toString();
							String tt = "无";
							for (int j = 1; j < lie; j++) {
								String kk = defaultModel.getValueAt(0, j).toString();
								String pp = defaultModel.getValueAt(i + 1, j).toString();
								String ss = "insert into benefit_table(id,action,childAction,state,value) values('"
										+ index + "','" + ff + "','" + tt + "','" + kk + "','" + pp + "')";
								stmt.executeUpdate(ss);
							}
						}
					} else {
						for (int i = 1; i < row - 1; i++) {
							String ff = defaultModel.getValueAt(i + 1, 0).toString();
							String tt = defaultModel.getValueAt(i + 1, 1).toString();
							for (int j = 1; j < lie - 1; j++) {
								String kk = defaultModel.getValueAt(0, j + 1).toString();
								String pp = defaultModel.getValueAt(i + 1, j + 1).toString();
								String ss = "insert into benefit_table(id,action,childAction,state,value) values('"
										+ index + "','" + ff + "','" + tt + "','" + kk + "','" + pp + "')";
								stmt.executeUpdate(ss);
							}
						}
					}
				
					JOptionPane.showMessageDialog(this, "保存成功!", "提示信息", JOptionPane.INFORMATION_MESSAGE);
	//			}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		if (e.getSource() == juece) {
			scoreTable.editCellAt(row+8,0);
			
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			panel2.removeAll();
			this.add(panel2);
			//panel2.removeAll();
			// 首先，定义一个jframe,用于显示画图的结果
			JPanel frame = new JPanel();
			
			int levelNum = Integer.parseInt(jueceLeveTxt.getText());
			
			mgraphx cs = new mgraphx(false, 20, 20, true);
			JButton exit = new JButton("保存决策图");
			JPanel panel_button = new JPanel();
//			JPanel panel_picture = new JPanel();
			panel_button.setLayout(new BoxLayout(panel_button, BoxLayout.X_AXIS));
//			panel_picture.setLayout(new BoxLayout(panel_picture, BoxLayout.X_AXIS));
			Font f=new Font("Dialog",Font.BOLD,16);
			//exit.setBounds(500, 400, 100,200);
			exit.setFont(f);
			String topic = topicTxt.getText().toString();
			exit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						//改为自己的数据库，用户名和密码
						//dbIO dbio = new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://localhost/testshenlei","root","111");
						dbIO dbio = new dbIO(mf.BR.setting.getDriver(),mf.BR.setting.getConnURL("testshenlei"));
						cs.saveG2DB(topic, index, dbio);
						dbio.close();
						
					} catch (Exception ess) {
						System.err.println(ess.getClass().getName() + ": " + ess.getMessage());
						// System.exit(0);
					}
					
				}
			});

			
			boolean graphFlag = true;

			System.out.println("this is leavel " + jueceLeveTxt.getText());

			int actionProjectNumber = row - 1;

			// 如果方案内容为空，提示错误信息
			if (topicTxt.getText().toString().equals("") && topicTxt.getText().toString() == null) {
				JOptionPane.showMessageDialog(this, "请输入主题!", "错误信息", JOptionPane.INFORMATION_MESSAGE);
			}

			// 用画图类进行添加节点，参数1是节点的名称，100是节点的横坐标，400是节点的纵坐标，下面是根节点
			Object rootNode = cs.addNode(topicTxt.getText(), 100, 400);
			// 用来装概率值得集合
			List<String> proList = new ArrayList<>();
			double gailvSum = 0;
			/* set probability list */
			if (levelNum == 1)
				{
				for (int index = 1; index < lie; index++) {
					proList.add(defaultModel.getValueAt(1, index).toString());
					if(checkDouble(defaultModel.getValueAt(1, index).toString() ) == false){
						graphFlag = false;
					}
					//System.out.println("this is 概率值 : " + defaultModel.getValueAt(1, index));
				}
				int childlastIndex  = 0;
				for (int i = 1; i < actionProjectNumber; i++) {
					// 添加一个节点
					String act = defaultModel.getValueAt(2+(i-1)*Integer.parseInt(jueceLeveTxt.getText()), 0).toString();
//					Object actionNode = cs.addNode("行动方案" + i, 300, i * (600 / (row - 1)));
					Object actionNode = cs.addNode(act, 300, i * (600 / (row - 1)));
					// 添加与根节点的边
					cs.addEdge("", rootNode, actionNode);
					for (int index = 1; index < lie; index++) {
						
						double value = 0;
						if(checkDouble(defaultModel.getValueAt(i + 1, index).toString())){
							if(graphFlag){
								value = Double.parseDouble(defaultModel.getValueAt(i + 1, index).toString());
								value = value * Double.parseDouble(proList.get(index - 1));
								DecimalFormat df = new DecimalFormat("#.##");
								value  = Double.parseDouble(df.format(value));
								Object statusNode = cs.addNode(String.valueOf(value), 550,
										childlastIndex + 30);
								childlastIndex = childlastIndex + 50;
								cs.addEdge("概率值:" + proList.get(index - 1), actionNode, statusNode);
							}
						}
						else{
							graphFlag = false;
						}
						

					}
					
				}
				
				//System.out.println("graphFlag "+graphFlag);
				if( graphFlag ){

					frame.setVisible(true);
	
					this.remove(panel2);
					frame.add(cs);
					JScrollPane scrollFrame  = new JScrollPane(frame,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					panel2.add (scrollFrame);
					panel2.add (panel_button);
					this.add(panel2);
					panel2.updateUI();
					this.updateUI();
				}
				else{
					JOptionPane.showMessageDialog(this, "请确保所有概率值或者行动方案的值输入类型是数值型", "错误信息", JOptionPane.INFORMATION_MESSAGE);
				}
				return;
				
				
				}
			
				else {
					//System.out.println("进入到2循环中");
				for (int index = 2; index < lie; index++) {
					proList.add(defaultModel.getValueAt(1, index).toString());
					if(checkDouble(defaultModel.getValueAt(1, index).toString() ) == false){
						graphFlag = false;
					}
					//System.out.println("this is 概率值 : " + defaultModel.getValueAt(1, index));
					
				}
				
				int child1lastIndex = 0;
				
				
			
				 if (levelNum > 1) {
					for (int i = 1; i <= actionProjectNumber /levelNum ; i++) {
						// 添加一个节点
						String act = defaultModel.getValueAt(2+(i-1)*Integer.parseInt(jueceLeveTxt.getText()), 0).toString();
						Object actionNode = cs.addNode(act, 300, i * (600 / (row - 1)));
						// 添加与根节点的边
						cs.addEdge("", rootNode, actionNode);
						int parent1Index =i * (600 / (row - 1)) -100;
						for(int actionIndex = 1;actionIndex<=levelNum;actionIndex++){
							
							Object childNode = cs.addNode(defaultModel.getValueAt(actionIndex+1, 1).toString(), 600,parent1Index+30);
							cs.addEdge("", actionNode, childNode);
							
						for (int index = 2; index < lie; index++) {
							
								System.out.println("shenleijiayou   :"+defaultModel.getValueAt((i-1)*levelNum+1 + actionIndex, index).toString());
								
								double value = 0;
								if(checkDouble(defaultModel.getValueAt((i-1)*levelNum+1 + actionIndex, index).toString())){
									if(graphFlag){
								 value = Double.parseDouble(defaultModel.getValueAt((i-1)*levelNum+1 + actionIndex, index).toString());
								 
								 value = value * Double.parseDouble(proList.get(index - 2));}
									DecimalFormat df = new DecimalFormat("#.##");
									value  = Double.parseDouble(df.format(value));
								}
								else{
									graphFlag = false;
								}
								
								
								Object statusNode = cs.addNode(String.valueOf(value), 850,
										child1lastIndex+10 );
								child1lastIndex = child1lastIndex + 40;
								
								cs.addEdge("概率值:" + proList.get(index - 2), childNode, statusNode);
							
							
								/*child1lastIndex = parent1Index-50;
								Object statusNode = cs.addNode(defaultModel.getValueAt(1 + actionIndex, index).toString(), 750,
										child1lastIndex );
								child1lastIndex = childlastIndex + 40;
								//cs.addEdge("概率值" + proList.get(index - 2), actionNode, statusNode);
								cs.addEdge("", childNode, statusNode);*/
							

							

						}
						parent1Index = parent1Index + 30;
					}
						}
				}

				

				
				if( graphFlag ){
					frame.setVisible(true);
					panel_button.add(exit);	
					this.remove(panel2);
					frame.add(cs);
					JScrollPane scrollFrame  = new JScrollPane(frame,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					panel2.add (scrollFrame);
					panel2.add (panel_button);
					this.add(panel2);
					panel2.updateUI();
					this.updateUI();
				}
				else{
					JOptionPane.showMessageDialog(this, "请确保所有概率值或者行动方案的值输入类型是数值型", "错误信息", JOptionPane.INFORMATION_MESSAGE);
				}

				// 将画图类添加进行，进行显示
				

			}
		}
	}
	private boolean checkDouble(String value){
		
	
		try{
			double changeValue = Double.parseDouble(value);
		}
		catch(Exception exception){
			return false;
		}
		return true;
	}
}
