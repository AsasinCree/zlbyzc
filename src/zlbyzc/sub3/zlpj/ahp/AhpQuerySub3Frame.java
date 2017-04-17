package zlbyzc.sub3.zlpj.ahp;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jdatepicker.JDatePicker;
import org.liukan.mgraph.mgraphx;
import org.liukan.mgraph.mgraphxEx;
import org.liukan.mgraph.util.dbIO;

import zlbyzc.sub3.sub3inFrame;
import zlbyzc.sub3.zlpj.util.JDBC_wapper;
import zlbyzc.sub3.zlpj.util.ResultSetTableModel; 



public class AhpQuerySub3Frame extends sub3inFrame  {
	private static final long serialVersionUID = 1L;
	
	//查询条件组件
	private JPanel panelQuery; //左边查询面板
	private mgraphx panelMgraphx; //右边ahp层次结构图
	
	private JPanel panelQureyCondition; // 查询条件输入panel
	private JLabel lableKeyword;//关键词   
	private JLabel lableArgueStartDate;//起始时间 
	private JLabel lableArgueEndDate;//结束时间
	private JTextField textfieldKeyword;
	private JButton buttonQuery;
	private JDatePicker datePickerArgueStartDate;
	private JDatePicker datePickerArgueEndDate;
	private JPanel panelKey;
	private JPanel panelArgueStartDate;
	private JPanel panelArgueEndDate; 
	
	//查询结果表
	private JPanel panelQueyResult; // 查询查询结果表panel
	private JTable tableQueyResult;
	
	//查询结果详情
	private JPanel panelQueyDetail; //装载textAear
	private JTextArea textareaQueyDetail;

	public AhpQuerySub3Frame() {
		super("",true,true,true,true);
		initializeComponent();
		layoutComponent();
		showAll();
		setEvents();
		
		setVisible(true);
	}
	
	private void initializeComponent() {
		setLayout(new BorderLayout());
		
		panelQuery=new JPanel();
		panelQuery.setLayout(new BoxLayout(panelQuery, BoxLayout.Y_AXIS));
		panelMgraphx = new mgraphx(false,22,22,true);
		panelMgraphx.setPreferredSize(new Dimension(800, 1000));
		
		System.out.println("init\n");
    	System.out.println("panelMgraph:\nw: "+panelMgraphx.getWidth()+" h: "+panelMgraphx.getHeight());

		
		//查询条件 panel
		panelQureyCondition = new JPanel(); 
		panelKey = new JPanel();
		panelArgueStartDate = new JPanel();
		panelArgueEndDate = new JPanel(); 
		
		panelQureyCondition.setLayout(new BoxLayout(panelQureyCondition, BoxLayout.Y_AXIS));
		panelQureyCondition.setBorder(new TitledBorder(new EtchedBorder(),"查询条件"));
		panelKey.setLayout(new BoxLayout(panelKey, BoxLayout.X_AXIS));
		panelArgueStartDate.setLayout(new BoxLayout(panelArgueStartDate, BoxLayout.X_AXIS));
		panelArgueEndDate.setLayout(new BoxLayout(panelArgueEndDate, BoxLayout.X_AXIS)); 
		
		lableKeyword = new JLabel("关键词  ");
		lableArgueStartDate = new JLabel("起始日期 ");
		lableArgueEndDate = new JLabel("结束日期 ");
		textfieldKeyword = new JTextField();
		buttonQuery = new JButton("查询");
		datePickerArgueStartDate = new JDatePicker();
		datePickerArgueEndDate = new JDatePicker();

		
		//查询结果详情 panel
		panelQueyResult = new JPanel();
		panelQueyResult.setLayout(new BorderLayout());
		panelQueyResult.setBorder(new TitledBorder(new EtchedBorder(),	"查询结果列表"));
		tableQueyResult = new JTable();
		
		//查询结果详情 panel
		panelQueyDetail = new JPanel();
		panelQueyDetail.setLayout(new BorderLayout());
		panelQueyDetail.setBorder(new TitledBorder(new EtchedBorder(),	"查询详情"));
		textareaQueyDetail = new JTextArea(40, 80);	
		
		textfieldKeyword.setMinimumSize(new Dimension(100,25));
		textfieldKeyword.setMaximumSize(new Dimension(150,30));
		((Component) datePickerArgueStartDate).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueStartDate).setMaximumSize(new Dimension(150,30));
		((Component) datePickerArgueEndDate).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueEndDate).setMaximumSize(new Dimension(150,30));
	}

	private void layoutComponent() {
		//查询条件 panel
		panelKey.add(lableKeyword);
		panelKey.add(textfieldKeyword);
		panelKey.add(Box.createHorizontalStrut(75));
		
		panelArgueStartDate.add(lableArgueStartDate);
		panelArgueStartDate.add(datePickerArgueStartDate);
		panelArgueStartDate.add(Box.createHorizontalStrut(80));
		
		panelArgueEndDate.add(lableArgueEndDate);
		panelArgueEndDate.add(datePickerArgueEndDate);
		panelArgueEndDate.add(Box.createHorizontalStrut(20));
		panelArgueEndDate.add(buttonQuery);
		
		panelQureyCondition.add(panelKey);
		panelQureyCondition.add(Box.createVerticalStrut(20));
		panelQureyCondition.add(panelArgueStartDate);	
		panelQureyCondition.add(Box.createVerticalStrut(20));	
		panelQureyCondition.add(panelArgueEndDate);
		panelQureyCondition.add(Box.createVerticalStrut(20));
		
		
		//查询结果详情 panel
		panelQueyResult.add(new JScrollPane(tableQueyResult));
		
		//查询结果详情 panel
		panelQueyDetail.add(new JScrollPane(textareaQueyDetail));
		
		panelQuery.add(panelQureyCondition);
		panelQuery.add(panelQueyResult);
		panelQuery.add(panelQueyDetail);
		
		add(panelQuery,BorderLayout.CENTER);
		add(panelMgraphx,BorderLayout.EAST);
		
		System.out.println("after add\n");
    	System.out.println("panelMgraph:\nw: "+panelMgraphx.getWidth()+" h: "+panelMgraphx.getHeight());

	}
	
	//显示所有的条目
	public void showAll() {
		JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");
		String queryForAll = String.format("SELECT DISTINCT * FROM %s", "ahp");
		ResultSet res = mysql_.query(queryForAll);
		ResultSetTableModel model = new ResultSetTableModel(res);
		model.setColumnNames( new String[] {"目标","日期","AHP模型","图id"} ); //设置table title
		tableQueyResult.setModel(model);
		tableQueyResult.getColumnModel().getColumn(3).setMaxWidth(0); //隐藏最后一列 gid
		
		
		if(tableQueyResult.getRowCount()==0){
			textareaQueyDetail.setText("没有查询到相关记录");
		}else {
			textareaQueyDetail.setText("");					
		}	
	}
	
	private void setEvents() {
		
		//监听查询button
		buttonQuery.addActionListener(new ActionListener() {			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				StringBuffer queryCmdBuf = new StringBuffer(String.format("SELECT DISTINCT * FROM %s", "ahp"));
				
//				if (key.length() == 0) {
//					JOptionPane.showMessageDialog(panelQureyCondition, "查询不能为空！");
//					return;
//				}
				
				JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");

				if (textfieldKeyword.getText().length() != 0){
					queryCmdBuf.append(String.format(" and aim LIKE '%%%s%%'", textfieldKeyword.getText()));
				} 
			
				//起始日期
				if (datePickerArgueStartDate.getModel().isSelected()) {
					Date startdate = new Date(datePickerArgueStartDate.getModel().getYear()-1900,
									     datePickerArgueStartDate.getModel().getMonth(),
						                 datePickerArgueStartDate.getModel().getDay() );
					queryCmdBuf.append(String.format(" and argueDate>='%s'", startdate));
				}
				//结束日期
				if (datePickerArgueEndDate.getModel().isSelected()) {
					Date enddate = new Date(datePickerArgueEndDate.getModel().getYear()-1900,
										 datePickerArgueEndDate.getModel().getMonth(),
										 datePickerArgueEndDate.getModel().getDay() );
					queryCmdBuf.append(String.format(" and argueDate<='%s'", enddate));
				}
				String queryStr = queryCmdBuf.toString().replaceFirst("and", "where");  //tricky: select * from table and ... and ... -->select * from table where ... and ...

				ResultSet res = mysql_.query(queryStr);
				ResultSetTableModel model = new ResultSetTableModel(res);
				


				model.setColumnNames( new String[] {"目标","日期","AHP模型","图id"} ); //设置table title
				tableQueyResult.setModel(model);
				tableQueyResult.getColumnModel().getColumn(3).setMaxWidth(0); //隐藏最后一列 gid
				
				if(tableQueyResult.getRowCount()==0){
					textareaQueyDetail.setText("没有查询到相关记录");
				}else {
					textareaQueyDetail.setText("");					
				}				
			}
		});
		
		//选中table的一行时，在textarea中显示详细信息
		tableQueyResult.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {// 仅当鼠标单击时响应
				// 得到选中的行的索引值
				int r = tableQueyResult.getSelectedRow();
				textareaQueyDetail.setText(tableQueyResult.getValueAt(r, 2).toString()); //2即ahpModel在sqltable中的序号。
				int gid = (int) tableQueyResult.getValueAt(r, 3);
				System.out.println("read gid: "+gid);
				if(gid<=0){
					return;}
				 try {
				    	dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://192.168.0.2:3336/db_zlpj",
				    			"root","wipm");	 
 
				    	panelMgraphx.readGfromDB(dbio,gid);
				    	System.out.println("panelMgraph:\nw: "+panelMgraphx.getWidth()+" h: "+panelMgraphx.getHeight());
		 
				    	dbio.close();			    	
				    } catch ( Exception e1 ) {
				      System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
				      System.exit(0);
				    }
			}
		});
	} 
	
	

}

