package zlbyzc.sub3.juece.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import org.liukan.mgraph.mgraphxEx;
import org.liukan.mgraph.util.dbIO;

import zlbyzc.sub3.juece.ui.PanelOutput.Listener;

public class PanelAsearch extends JPanel {
	String date;
	int focusedRowIndex;
	MainFrame mf = null;
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	
	JPanel tableLabel = new JPanel();
	JPanel panelJlabel = new JPanel();
	JPanel panelTable = new JPanel();
	JPanel panelInput = new JPanel();
	JPanel panelResearchTxt = new JPanel();
	JPopupMenu menu = new JPopupMenu();  
	JMenuItem delMenItem = new JMenuItem("删除"); 
     
	JLabel searchResult = new JLabel("决策树（1）查询结果");
	
	JLabel search = new JLabel("请输入查询主题:");
	JLabel nowPage = new JLabel("当前多少页");
	JLabel allSearchNum = new JLabel("一共多少记录");
	JTextArea searchInput = new JTextArea();//主题输入文本框
	JButton firm = new JButton("查询");
	JButton lastPage = new JButton("上一页");
	JButton nextPage = new JButton("下一页");	
	
	JTextArea sTxt = new JTextArea();//查询主题显示文本框
	JScrollPane searchTxt  = new JScrollPane(sTxt,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	//JTextArea formTxt = new JTextArea();//查询所有显示文本框
//	JScrollPane formSearchTxt  = new JScrollPane(formTxt,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	String[] tableTitle = {"主题名称","方案名称","行数","列数","保存时间 "};
	public DefaultTableModel defaultModel = new DefaultTableModel(tableTitle,0);
	JTable scoreTable = new JTable(defaultModel);
	JScrollPane formSearchTable  = new JScrollPane(scoreTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	int rowCount = 0;
	int pageNum = 1;
	int currentPage = 1;
	
	public PanelAsearch(MainFrame mf) {
		this.mf = mf;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));//指定组件应该从左到右放置
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		
		tableLabel.setLayout(new BoxLayout(tableLabel, BoxLayout.X_AXIS));
		panelJlabel.setLayout(new BoxLayout(panelJlabel, BoxLayout.X_AXIS));
		panelTable.setLayout(new BoxLayout(panelTable, BoxLayout.X_AXIS));
		panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.X_AXIS));
		panelResearchTxt.setLayout(new BoxLayout(panelResearchTxt, BoxLayout.X_AXIS));
		
		searchResult.setFont(new Font("Dialog",0,16));
		search.setFont(new Font("Dialog",0,16));
		searchInput.setFont(new Font("Dialog",0,16));
		searchInput.setMinimumSize(new Dimension(200,25));//将此组件的最大大小设置为一个常量值。
		searchInput.setMaximumSize(new Dimension(400,30));//将此组件的最大小设置为一个常量值。
		
		
		
		nowPage.setFont(new Font("Dialog",0,14));
		allSearchNum.setFont(new Font("Dialog",0,14));

		
		scoreTable.setRowHeight(21);//表格中每一的高度
		
		sTxt.setFont(new Font("Dialog",0,16));
		searchTxt.setPreferredSize(new Dimension(500,450));
		//searchTxt.setMinimumSize(new Dimension(400,200));//将此组件的最大大小设置为一个常量值。
		searchTxt.setMaximumSize(new Dimension(520,550));//将此组件的最大小设置为一个常量值。
		
		//formTxt.setFont(new Font("Dialog",0,16));
		formSearchTable.setPreferredSize(new Dimension(300,450));
		//searchTxt.setMinimumSize(new Dimension(400,200));//将此组件的最大大小设置为一个常量值。
		formSearchTable.setMaximumSize(new Dimension(400,550));//将此组件的最大小设置为一个常量值。
		
				
		panelJlabel.add(searchResult);
		
		panelTable.add(formSearchTable);
		
		tableLabel.add( Box.createVerticalGlue() );
		tableLabel.add(lastPage);
		tableLabel.add(Box.createRigidArea(new Dimension(10, 60))); 
		tableLabel.add(nextPage);
		tableLabel.add(Box.createRigidArea(new Dimension(10, 60))); 
		tableLabel.add(allSearchNum);
		tableLabel.add(nowPage);
		tableLabel.add( Box.createVerticalGlue() );
				
		panelInput.add(search);
		panelInput.add(Box.createRigidArea(new Dimension(10, 60))); 
		panelInput.add(searchInput);
		panelInput.add(Box.createRigidArea(new Dimension(20, 60))); 
		panelInput.add(firm);		
		
		
		panelResearchTxt.add(searchTxt);		
		
		
	//	panel1.add( Box.createVerticalGlue() );
		panel1.add(panelJlabel);
		panel1.add(Box.createVerticalStrut(30)); 
		panel1.add(panelTable);
		panel1.add(Box.createVerticalStrut(10)); 
		panel1.add(tableLabel);
	//	panel1.add( Box.createVerticalGlue() );
		
		
	//	panel2.add( Box.createVerticalGlue() );
		panel2.add(panelInput);
		panel2.add(Box.createVerticalStrut(20));  		
		panel2.add(panelResearchTxt);
	//	panel2.add( Box.createVerticalGlue() );
		
	
		menu.add(delMenItem);  
		
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.addEvent();
		this.showAllData();//显示数据保存数据
		this.tableClick();
	}
	
	public void addEvent(){
		 //决策树的监听组件
	     firm.addActionListener(new Listener());
	     delMenItem.addActionListener(new Listener());
	     lastPage.addActionListener(new Listener());
	     nextPage.addActionListener(new Listener());
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
		
		if(e.getSource() == delMenItem) {
	     	   String id="0";
	     	   try{
	     		  Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue"));
//	     		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111"); 	  	
	        		Statement stmt = conn.createStatement();
	        		Statement stmt1 = conn.createStatement(); 
	    			ResultSet rs; 			
	    			rs = stmt.executeQuery("select * from topic_table where date = '"+date+"' ");
	    		 if(rs.next()) {             	
	          	 id = rs.getString("id");
	          	}
	    		 rs = stmt.executeQuery("select * from topic_table where id = '"+id+"' ");
	    		 if(rs.next()) { 
	             	String ss ="delete from topic_table where id = '"+id+"'";
	               	stmt.executeUpdate(ss);
	               	
	             }
	    		rs = stmt.executeQuery("select * from idea_table where id = '"+id+"' ");
	    		while(rs.next()) {
	    			
	    			String ss ="delete from idea_table where id = '"+id+"'";
	           	stmt1.executeUpdate(ss);
	         }
	    		rs = stmt.executeQuery("select * from ideavalue_table where id = '"+id+"' ");
	    		while(rs.next()) { 
	    			String ss ="delete from ideavalue_table where id = '"+id+"'";
	           	stmt1.executeUpdate(ss);
	         }
	    		 int rrr= scoreTable.getRowCount();
	     	   }catch(Exception e1) {
	                    e1.printStackTrace();
	        	} 
	     	   
	     	  try{
	     		 Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("testshenlei"));
		     		//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testshenlei?useUnicode=true&characterEncoding=utf8", "root","111"); 	  	
		        		Statement stmt = conn.createStatement();
		        		Statement stmt1 = conn.createStatement(); 
		    			ResultSet rs; 			
		    			rs = stmt.executeQuery("select * from graphs where date = '"+date+"' ");
		    		 if(rs.next()) {             	
		          	 id = rs.getString("id");
		          	}
		    		 rs = stmt.executeQuery("select * from graphs where id = '"+id+"' ");
		    		 if(rs.next()) { 
		             	String ss ="delete from graphs where id = '"+id+"'";
		               	stmt.executeUpdate(ss);
		               	
		             }
		    		rs = stmt.executeQuery("select * from nodes where gid = '"+id+"' ");
		    		while(rs.next()) {
		    			
		    			String ss ="delete from nodes where gid = '"+id+"'";
		           	stmt1.executeUpdate(ss);
		         }
		    		rs = stmt.executeQuery("select * from edges where gid = '"+id+"' ");
		    		while(rs.next()) { 
		    			String ss ="delete from edges where gid = '"+id+"'";
		           	stmt1.executeUpdate(ss);
		         }
		     	   }catch(Exception e2) {
		                    e2.printStackTrace();
		        	} 	     	
	     	 defaultModel.removeRow(focusedRowIndex);
	     	 
			}
		if(e.getSource() == lastPage) {
			String[] row = new String[5];
			try{
				Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue"));  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				///分页
				if(currentPage == 1){
					return;
				}
				defaultModel.setRowCount(0);
				rs = stmt.executeQuery("select * from topic_table  ");
				int searchNum = 1;
				String [][] value = new String[20][5];
				int k = 0;
				while(rs.next()) {
					if((searchNum>=rowCount-20*(currentPage-1)+1) &&(searchNum<=rowCount-20*(currentPage-2))) {									
							String ss1 = rs.getString("topicName");
			            	String ss2 = rs.getString("schemeName");
			            	String ss3 = rs.getString("rowCount");
			            	String ss4 = rs.getString("ideaCount");
			            	String ss5 = rs.getString("date"); 
			            	value[k][0] = ss1;
			            	value[k][1] = ss2;
			            	value[k][2] = ss3;
			            	value[k][3] = ss4;
			            	value[k][4] = ss5;
			            	k++;
			            	
					} 
					searchNum++;
				}
				for (int i = 20-1; i >= 0; i--) {
					row[0] = value[i][0];
					row[1] = value[i][1];
					row[2] = value[i][2];
					row[3] = value[i][3];
					row[4] = value[i][4];
					defaultModel.addRow(row);
				}
			    currentPage--;
			    nowPage.setText("当前" + currentPage +"页" + "|");
			    nowPage.updateUI();
			}
			catch(Exception e1) {
	            e1.printStackTrace();
			}  
		}
		
		if(e.getSource() == nextPage) {
			String[] row = new String[5];
			try{
				Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue")); 	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				rs = stmt.executeQuery("select * from topic_table  ");
				rs.last();
				///分页
				if(currentPage == pageNum){
					return;
				}
				defaultModel.setRowCount(0);
				rs = stmt.executeQuery("select * from topic_table  ");
				int searchNum = 1;
				if((rowCount-currentPage*20) <= 20) {
					String [][] value = new String[rowCount][5];
					while(rs.next()) {
						if(searchNum <= rowCount-currentPage*20) {
							String ss1 = rs.getString("topicName");
			            	String ss2 = rs.getString("actionNum");
			            	String ss3 = rs.getString("stateNum");
			            	String ss4 = rs.getString("childActionNum");
			            	String ss5 = rs.getString("date");
			            	value[searchNum-1][0] = ss1;
			            	value[searchNum-1][1] = ss2;
			            	value[searchNum-1][2] = ss3;
			            	value[searchNum-1][3] = ss4;
			            	value[searchNum-1][4] = ss5;					
						}	
						searchNum++;	
					}
					for (int i = rowCount-currentPage*20-1; i >= 0; i--) {
						row[0] = value[i][0];
						row[1] = value[i][1];
						row[2] = value[i][2];
						row[3] = value[i][3];
						row[4] = value[i][4];
						defaultModel.addRow(row);
					}
					currentPage++;
				} else {
					String [][] value = new String[20][5];
					while(rs.next()) {
						if((searchNum>=rowCount-20*(currentPage+1)+1) &&(searchNum<=rowCount-20*(currentPage))) {
							String ss1 = rs.getString("topicName");
			            	String ss2 = rs.getString("actionNum");
			            	String ss3 = rs.getString("stateNum");
			            	String ss4 = rs.getString("childActionNum");
			            	String ss5 = rs.getString("date");
							row[0] = ss1;
							row[1] = ss2;
							row[2] = ss3;
							row[3] = ss4;
							row[4] = ss5;												
						} 
						searchNum++;
					}
					for (int i = 20-1; i >=0; i--) {
						row[0] = value[i][0];
						row[1] = value[i][1];
						row[2] = value[i][2];
						row[3] = value[i][3];
						row[4] = value[i][4];
						defaultModel.addRow(row);
					}
					currentPage ++;   
				}
				nowPage.setText("当前" + currentPage +"页" + "|");
				nowPage.updateUI();
			}
			catch(Exception e1) {
	            e1.printStackTrace();
			}  
		}
		
		if(e.getSource() == firm) {
			defaultModel.setRowCount(0);
			String sTopicName = searchInput.getText();
			String[] row = new String[5];
			
			try{
					
				Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue"));
//				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111"); 	  	
	    		Statement stmt = conn.createStatement();  
				ResultSet rs; 
				rs = stmt.executeQuery("select * from topic_table where topicName like '%"+sTopicName+"%' ");
				rs.last();
				///分页
			    rowCount = rs.getRow();
				pageNum = 1;
				int searchNum = 1;
				rs = stmt.executeQuery("select * from topic_table where topicName like '%"+sTopicName+"%' ");
				if(rowCount <= 20) {
					String [][] value = new String[rowCount][5];
					currentPage = 1;
					while(rs.next()) {			
						String ss1 = rs.getString("topicName");
		            	String ss2 = rs.getString("schemeName");
		            	String ss3 = rs.getString("rowCount");
		            	String ss4 = rs.getString("ideaCount");
		            	String ss5 = rs.getString("date"); 
		            	value[searchNum-1][0] = ss1;
		            	value[searchNum-1][1] = ss2;
		            	value[searchNum-1][2] = ss3;
		            	value[searchNum-1][3] = ss4;
		            	value[searchNum-1][4] = ss5;
		            	searchNum++;
					}
					for (int i = rowCount-1; i >= 0; i--) {
						row[0] = value[i][0];
						row[1] = value[i][1];
						row[2] = value[i][2];
						row[3] = value[i][3];
						row[4] = value[i][4];
						defaultModel.addRow(row);
					}
				} else {
					String [][] value = new String[20][5];
					currentPage = 1; 
					int k = 0;
					while(rs.next()) {
						if(searchNum >= rowCount-20+1) {			
								String ss1 = rs.getString("topicName");
				            	String ss2 = rs.getString("schemeName");
				            	String ss3 = rs.getString("rowCount");
				            	String ss4 = rs.getString("ideaCount");
				            	String ss5 = rs.getString("date"); 
				            	value[k][0] = ss1;
				            	value[k][1] = ss2;
				            	value[k][2] = ss3;
				            	value[k][3] = ss4;
				            	value[k][4] = ss5;
				            	k++;
				            			            						
						}
						searchNum++;
					}	
					for (int i = 20-1; i >= 0; i--) {
						row[0] = value[i][0];
						row[1] = value[i][1];
						row[2] = value[i][2];
						row[3] = value[i][3];
						row[4] = value[i][4];
						defaultModel.addRow(row);
					}
				}
				nowPage.setText("当前" + currentPage +"页");
				nowPage.updateUI();
				allSearchNum.setText("一共" + rowCount + "记录" + "|");
				allSearchNum.updateUI();
			}
			catch(Exception e1) {
	            e1.printStackTrace();
			}  
		}
			
			
		}

	public void showAllData(){
		//formTxt.setText("");
		String[] row = new String[5];
		try{
				
			Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue"));
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111"); 	  	
    		Statement stmt = conn.createStatement();  
			ResultSet rs; 
			rs = stmt.executeQuery("select * from topic_table  ");
			rs.last();
			///分页
			int rowCount = rs.getRow();
			int pageNum = 1;
			if(rowCount%20 == 0) {
				pageNum = rowCount/20;
			} else {
				pageNum = (rowCount/20) + 1;
			}
			rs = stmt.executeQuery("select * from topic_table  ");
			int searchNum = 1;		
			if(rowCount <= 20) {
				String [][] value = new String[rowCount][5];
				currentPage = 1;
				while(rs.next()) {			
					String ss1 = rs.getString("topicName");
	            	String ss2 = rs.getString("schemeName");
	            	String ss3 = rs.getString("rowCount");
	            	String ss4 = rs.getString("ideaCount");
	            	String ss5 = rs.getString("date"); 
	            	value[searchNum-1][0] = ss1;
	            	value[searchNum-1][1] = ss2;
	            	value[searchNum-1][2] = ss3;
	            	value[searchNum-1][3] = ss4;
	            	value[searchNum-1][4] = ss5;
	            	searchNum++;
				}
				for (int i = rowCount-1; i >= 0; i--) {
					row[0] = value[i][0];
					row[1] = value[i][1];
					row[2] = value[i][2];
					row[3] = value[i][3];
					row[4] = value[i][4];
					defaultModel.addRow(row);
				}
			} else {
				String [][] value = new String[20][5];
				currentPage = 1; 
				int k = 0;
				while(rs.next()) {
					if(searchNum >= rowCount-20+1) {			
							String ss1 = rs.getString("topicName");
			            	String ss2 = rs.getString("schemeName");
			            	String ss3 = rs.getString("rowCount");
			            	String ss4 = rs.getString("ideaCount");
			            	String ss5 = rs.getString("date"); 
			            	value[k][0] = ss1;
			            	value[k][1] = ss2;
			            	value[k][2] = ss3;
			            	value[k][3] = ss4;
			            	value[k][4] = ss5;
			            	k++;
			            			            						
					}
					searchNum++;
				}	
				for (int i = 20-1; i >= 0; i--) {
					row[0] = value[i][0];
					row[1] = value[i][1];
					row[2] = value[i][2];
					row[3] = value[i][3];
					row[4] = value[i][4];
					defaultModel.addRow(row);
				}
			}
			nowPage.setText("当前" + currentPage +"页");
			nowPage.updateUI();
			allSearchNum.setText("一共" + rowCount + "记录" + "|");
			allSearchNum.updateUI();
	}
		
		catch(Exception e1) {
            e1.printStackTrace();
		}  
	}
	
	//表格双击事件
	public void tableClick(){
		scoreTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == java.awt.event.MouseEvent.BUTTON1) {
					int row = ((JTable)e.getSource()).rowAtPoint(e.getPoint());
					date = scoreTable.getValueAt(row, 4).toString();			
					searchFromTheme(date);
					panel3.removeAll();
					JPanel frame = new JPanel();

					frame.setLayout(new BorderLayout());
					
					int topicID = -1;
					mgraphxEx c=new mgraphxEx(false,20,25,true);
					Connection conn;
					try {
						////需要改为自己的数据库，我这里是testshenlei,登录名和密码你要改
						conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue"));
//						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111");
						Statement stmt = conn.createStatement();  
						ResultSet rs = stmt.executeQuery("select  id from topic_table where date = '"+date+"'"); 
						while(rs.next()){
							topicID = rs.getInt("id");
						}
						dbIO dbio = new dbIO(mf.BR.setting.getDriver(),mf.BR.setting.getConnURL("testshenlei"));
						//需要改为自己的数据库，我这里是testshenlei,登录名和密码你要改
//						dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://localhost/testshenlei",
//				    			"root","111");	      
				    	c.gpanel.readGfromDB(dbio,topicID);
				    	System.out.println("this is topic "+topicID);
				    	dbio.close();	
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 	
					//frame.add(panel_button, BorderLayout.SOUTH);
//					frame.getContentPane().add(c,BorderLayout.CENTER);
	//				
//					frame.pack();
					//设置大小
//					frame.setSize(800, 800);
					//frame.setLocation(1050, 200);
					frame.setVisible(true);
//					frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					frame.add(c);
					panel3.add(frame);
				}
				 if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {  
			           //通过点击位置找到点击为表格中的行  
			           focusedRowIndex =  scoreTable.rowAtPoint(e.getPoint()); 
			           date =  scoreTable.getValueAt(focusedRowIndex, 4).toString();
			           if (focusedRowIndex == -1) {  
			               return;  
			           }  
			           //将表格所选项设为当前右键点击的行  
			           scoreTable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);  
			           //弹出菜单  
			           menu.show( scoreTable, e.getX(), e.getY());  
			           
			       } 
			}
			
			
		});
	}
	
	public void searchFromTheme(String date) {
		sTxt.setText("");
		//String sTopicName = searchInput.getText();
		
		String idd = null;
		int r = 0;
		int count = 0;
		
		try{
				
			Connection conn = DriverManager.getConnection(mf.BR.setting.getConnURL("zhanlue"));
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/zhanlue?useUnicode=true&characterEncoding=utf8", "root","111"); 	  	
    		Statement stmt = conn.createStatement();  
			ResultSet rs; 
			
			rs = stmt.executeQuery("select * from topic_table where date = '"+date+"' ");
            if(rs.next()) { 
            	String ss = rs.getString("schemeName");
            	idd = rs.getString("id");
                String	sTopicName = rs.getString("topicName");
            	r = Integer.parseInt(rs.getString("rowCount"));
            	count = Integer.parseInt(rs.getString("ideaCount")) + 1;
            	sTxt.append("------主题名称：" + sTopicName +  "\r\n");            	
            	sTxt.append("------方案名称：" + ss +  "\r\n");
            	sTxt.append("保存时间" + date + "\r\n");
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
        		rs = stmt.executeQuery("select * from ideavalue_table where id = '"+idd+"' and row = '"+i+"'");
        		 while(rs.next()) {
            		String ss = rs.getString("ideaValue");
                	sTxt.append(ss + "\t");
        		 }
        		 sTxt.append("\r\n");
			}
        	
            rs = stmt.executeQuery("select * from topic_table where date = '"+date+"' ");
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
}

