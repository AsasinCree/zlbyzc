package zlbyzc.sub3.juece.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.liukan.mgraph.mgraphx;
import org.liukan.mgraph.util.dbIO;

import zlbyzc.sub3.juece.ui.PanelA.Listener;

public class PanelOutput extends JPanel {
	MainFrame mf = null;
	
	
	private static mgraphx c;
	
	public mgraphx getC() {
		return c;
	}

	public void setC(mgraphx c) {
		this.c = c;
	}

	JPanel panelResultName = new JPanel();
	JPanel panelResultTxt = new JPanel();
	JPanel panelFenxi = new JPanel();
	JPanel panelFenxiTable = new JPanel();
	JPanel panelFenxiResult = new JPanel();
	JPanel panelFenxiTableFirm = new JPanel();
	JPanel panelButton = new JPanel();
	
	JLabel resultName = new JLabel("决策树结果");
	JLabel fenxiName = new JLabel("决策分析");
	
	JTextPane resultT = new JTextPane();//主题输入文本框
	
	
	
	JScrollPane resultTxt  = new JScrollPane(resultT,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	JTextPane fenxiT = new JTextPane();
	
	JScrollPane fenxiTxt  = new JScrollPane(fenxiT,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public DefaultTableModel defaultModel = new DefaultTableModel(2,2);
	JTable fenxiTable = new JTable(defaultModel);
	JScrollPane scoreScroll  = new JScrollPane(fenxiTable,JScrollPane.VERTICAL_SCROLLBAR_NEVER,
								JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	JButton former = new JButton("上一步");
	JButton save = new JButton("保存");
	JButton firm = new JButton("确定");
	
	JButton graphSave = new JButton("保存决策图");
	
	JTextField graphID = new JTextField();
	
	
	


	
	JButton displayGraph = new JButton("显示决策图");
	
	public PanelOutput(MainFrame mf) {
		this.mf = mf;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));//指定组件应该从上到下放置
		
		
		graphID.setBounds(500, 500, 70, 50);
		
		add(graphID);
		
		panelResultName.setLayout(new BoxLayout(panelResultName, BoxLayout.X_AXIS));
		panelResultTxt.setLayout(new BoxLayout(panelResultTxt, BoxLayout.X_AXIS));
		panelFenxi.setLayout(new BoxLayout(panelFenxi, BoxLayout.X_AXIS));
		panelFenxiTable.setLayout(new BoxLayout(panelFenxiTable, BoxLayout.X_AXIS));
		panelFenxiTableFirm.setLayout(new BoxLayout(panelFenxiTableFirm, BoxLayout.X_AXIS));
		panelFenxiResult.setLayout(new BoxLayout(panelFenxiResult, BoxLayout.X_AXIS));
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
		
		resultName.setFont(new Font("Dialog",0,16));
		fenxiName.setFont(new Font("Dialog",0,16));
		
		resultTxt.setFont(new Font("Dialog",0,16));
		resultTxt.setMinimumSize(new Dimension(500,210));//将此组件的最大大小设置为一个常量值。
		resultTxt.setMaximumSize(new Dimension(600,310));//将此组件的最大小设置为一个常量值。
		
		fenxiTxt.setFont(new Font("Dialog",0,16));
		fenxiTxt.setMinimumSize(new Dimension(500,210));//将此组件的最大大小设置为一个常量值。
		fenxiTxt.setMaximumSize(new Dimension(600,310));//将此组件的最大小设置为一个常量值。
		
		fenxiTable.setTableHeader(null);//表头不要
		fenxiTable.setRowHeight(30);//表格中每一的高度
		fenxiTable.setFont(new Font("Dialog",0,14));
      	DefaultTableCellRenderer   r   =   new   DefaultTableCellRenderer();  
        r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);   
        fenxiTable.setDefaultRenderer(Object.class,r);
        scoreScroll.setMinimumSize(new Dimension(500,60));
        scoreScroll.setMaximumSize(new Dimension(600,80));
        
        panelResultName.add(resultName);		
        panelResultTxt.add(resultTxt);
        panelFenxi.add(fenxiName);
       
        panelFenxiTable.add(scoreScroll);
        
        panelFenxiTableFirm.add(firm);
        
        panelFenxiResult.add(fenxiTxt);
        
        panelButton.add(former);
        panelButton.add(Box.createRigidArea(new Dimension(20, 60)));  
        panelButton.add(save);
        displayGraph.setBounds(200, 100, 70, 50);
		add(displayGraph);
		
      /*  
		
		graphSave.setBounds(100,100,70,50);
		add(graphSave);*/
        
        
    	add( Box.createVerticalGlue() );
		add(panelResultName);
		add(Box.createVerticalStrut(10));  		
		add(panelResultTxt);
		add(Box.createVerticalStrut(10));  		
		add(panelFenxi);
		add(Box.createVerticalStrut(10));  		
		add(panelFenxiTable);
		add(Box.createVerticalStrut(10));  		
		add(panelFenxiTableFirm);
		add(Box.createVerticalStrut(10));  		
		add(panelFenxiResult);
		add(Box.createVerticalStrut(10));  		
		add(panelButton);
		add( Box.createVerticalGlue() );
        
        this.addEvent();
	}
	
	public void addEvent(){
		 //决策树的监听组件
		 former.addActionListener(new Listener());
	     save.addActionListener(new Listener());
	     firm.addActionListener(new Listener());
	     
	     graphSave.addActionListener(new Listener());
	     
	     displayGraph.addActionListener(new Listener());
	}
	
	/**
	 * 监听窗口的类
	 */
	class Listener implements ActionListener {		
		public void actionPerformed(ActionEvent e) {
			//决策树
			fenxiPerformed(e);  //A页
		}		
	}
	
	/**
	 * 按钮事件
	 */
	public void fenxiPerformed(ActionEvent e) {
		
		if(e.getSource() == former) {
			mf.setContentPane(mf.panelA);
			mf.revalidate();
			mf.repaint(); 
		}
		
		if(e.getSource() == save) {
		}
		
		
		if(e.getSource() == graphSave){
			System.out.println("你点击了graphSave按钮");
			
			
			
			//System.out.println(cs.);
			
			try {
				 dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://192.168.0.2:3336/testshenlei",
			    			"root","199211");
				 
				 this.c.saveG2DB("hoh33o",3,dbio);			    	
			    	dbio.close();		    	
			    } catch ( Exception ess ) {
			      System.err.println( ess.getClass().getName() + ": " + ess.getMessage() );
			      //System.exit(0);
			    }
			
			
		}
		
		
		if(e.getSource() == displayGraph){
			
			JFrame  frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());
			mgraphx cs=this.c;
			
			
			JPanel panel_button = new JPanel();
			
			JButton exit = new JButton("Exit Window");
			exit.setBounds(700, 700, 100, 70);
			
			exit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					frame.dispose();
				}
			});
			panel_button.add(exit);
			frame.add(panel_button, BorderLayout.SOUTH);
			
			/*Object n1=c.addNode("haha", 260, 40);
			Object n2=c.addNode("xixi\nhoho", 180, 200);
			
			
			c.addEdge("这是测试数据", n1, n2);*/
			
			 try {
			    /*	dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://192.168.0.2:3336/testshenlei",
			    			"root","199211");	 
			    	
			    	cs.readGfromDB(dbio,3);
			    	
			    	dbio.close();			*/    	
			    } catch ( Exception es ) {
			      System.err.println( es.getClass().getName() + ": " + es.getMessage() );
			      //System.exit(0);
			    }	
			 
			 	frame.getContentPane().add(c,BorderLayout.CENTER);
				frame.pack();
				frame.setSize(850, 820);
		        frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.setUndecorated(true);
		}
		
		
		if(e.getSource() == firm) {
			///mf.panelA.pathList
			
			fenxiT.setText("");
			String input = "";
			for (int i = 1; i < mf.panelA.lie-1; i++) {
				if(defaultModel.getValueAt(1, i).toString().length() == 0) {
					continue;
				}
				input = input + defaultModel.getValueAt(0, i).toString() + "[" +"value:"+
						defaultModel.getValueAt(1, i).toString() + "]" + ",";
			}
			System.out.println("THIS IS INPUT "+input);
			
			List<String> pathList = mf.panelA.pathList;
			for(String value : pathList){
				System.out.println(value);
			}
			StringBuffer buffer = new StringBuffer();
			
			if(input.equals("")){
				fenxiT.setText("用户输入的是:"+"\"\""+",请仔细输入");
			}
			if(!input.equals("")) {
				//定义包含用户输入结果的集合
				List<String > resultList = new ArrayList<String>();
				//通过逗号得到每个属性和其属性值
				String[] splitInput = input.trim().split(",");
				//得到决策树的路径集合
				
				//开始进行遍历决策树每一条路径结果
				for(String path :pathList){
					boolean flag = true;
					//如果每个属性和属性值都在路径中
					for(String value : splitInput){
						//不包含直接退出最里面的一层循环
						if(!path.trim().contains(value)){
							flag = false;
							break;
						}
					}
					if(flag == true){
						resultList.add(path);
					}
				}
				
				//如果没有包含用户输入的属性和属性值
				if(resultList.size() == 0){
					fenxiT.setText("请重新输入，决策树中没有包含你输入的属性和属性值情况");
				}
				else{
					
					
					for(String resultPath : resultList){
						buffer.append(resultPath+"\r\n");
					}
					fenxiT.setText("决策结果: "+buffer.toString());
				}
			}
			
		}
	}
	
}
