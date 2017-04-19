package zlbyzc.sub3.zlpj.fuzzy;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import org.jdatepicker.JDatePicker;
import org.liukan.mgraph.util.dbIO;

import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;
import zlbyzc.sub3.zlpj.dea.DeaQueryFrm;
import zlbyzc.sub3.zlpj.util.JDBC_wapper;
import zlbyzc.sub3.zlpj.util.ResultSetTableModel;


public class FuzzyQueryFrm extends sub3inFrame  {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JScrollPane contentPaneC;
	//查询条件组件
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
	private JPanel panelLayoutButton;  //上一页，下一页...
	private JButton buttonFirstPage,buttonNextPage,buttonLastPage,buttonDelete;
	private int queryIndex = 0; //[select ... limit queryIndex,itemNumPerPage]表示返回第queryIndex到queryIndex+itemNumPerPage 条记录。
								//queryIndex表示当前page显示的结果的偏移量，如果显示下一页，则仅需将queryIndex+=itemNumPerPage，然后调用[select ... limit queryIndex,itemNumPerPage]
								//如果显示上一页，queryIndex-=itemNumPerPage；如果显示第一页，将queryIndex置为0。	
	private int itemNumPerPage = 5;  //查询每页显示5条记录
	private int itemNumCurPage; //当前页实际显示记录条数，如果少于itemNumPerPage，则可判断查询完毕。
	private String queryCmd; //上一次的查询命令字符串。因为上一页、下一页的实现是利用命令[select ... limit m,n]
							 //但select的命令有两种，一是进入查询界面时的无条件查询，二是条件查询。因此有必要保存上一次的查询命令。

	
	//查询结果详情
	private JPanel panelQueyDetail; //装载textAear
	private JTextArea textareaQueyDetail;

	public FuzzyQueryFrm() {
		super("模糊综合评判历史查询",true,true,true,true);
		javax.swing.Icon r_icon=ImageTask.
				getResizableIconFromResource("/img/icons/fu.png");
		this.setFrameIcon(r_icon);
		
		initializeComponent();
		layoutComponent();
		initQuery(); 
		setEvents();
		setVisible(true);
	}
	
	
	private void initializeComponent() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
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

		panelLayoutButton = new JPanel();
		panelLayoutButton.setLayout(new FlowLayout());
		buttonFirstPage = new JButton("第一页");
		buttonNextPage = new JButton("下一页");
		buttonLastPage = new JButton("上一页");
		buttonDelete   = new JButton("删除");
		
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
		panelKey.add(Box.createHorizontalStrut(95));
		
		panelArgueStartDate.add(lableArgueStartDate);
		panelArgueStartDate.add(datePickerArgueStartDate);
		panelArgueStartDate.add(Box.createHorizontalStrut(95));
		panelArgueEndDate.add(lableArgueEndDate);
		panelArgueEndDate.add(datePickerArgueEndDate);
		panelArgueEndDate.add(Box.createHorizontalStrut(20));
		panelArgueEndDate.add(buttonQuery);
		
		panelQureyCondition.add(panelKey);
		panelQureyCondition.add(Box.createVerticalStrut(10));
		panelQureyCondition.add(panelArgueStartDate);	
		panelQureyCondition.add(Box.createVerticalStrut(10));	
		panelQureyCondition.add(panelArgueEndDate);
		panelQureyCondition.add(Box.createVerticalStrut(10));
		
		//查询结果详情 panel
		panelQueyResult.add(new JScrollPane(tableQueyResult));
		panelLayoutButton.add(buttonFirstPage);
		panelLayoutButton.add(buttonLastPage);
		panelLayoutButton.add(buttonNextPage);
		panelLayoutButton.add(buttonDelete);
		panelQueyResult.add("South",panelLayoutButton);
		
		//查询结果详情 panel
		panelQueyDetail.add(new JScrollPane(textareaQueyDetail));
		
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		add(panelQureyCondition);
		add(panelQueyResult);
		add(panelQueyDetail);
//		contentPaneC = new JScrollPane(contentPane);
//		add("Center",contentPaneC);
	}
	
	private int get_queryIndex(){
		return queryIndex;
	}
	private void set_queryIndex(int idx){
		queryIndex = idx;
	}
	
	//显示下一页查询结果：利用this.queryCmd
	private void displayOnePage(){		
		JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");
		ResultSet res = mysql_.query(queryCmd);  //这里返回的是引用？？mysql_.close()后res变为null！！！！！
		ResultSetTableModel resTableModel = new ResultSetTableModel(res);
		//mysql_.close();  //close 后res变为null
		resTableModel.setColumnNames( new String[] {"id","目标","日期","模糊模型","结论"} ); //设置table title
		tableQueyResult.setModel(resTableModel);
		itemNumCurPage = tableQueyResult.getRowCount();
		tableQueyResult.getColumnModel().getColumn(0).setMaxWidth(100); //第一列id 宽度
//		tableQueyResult.getColumnModel().getColumn(1).setMaxWidth(300); //目标 宽度
//		tableQueyResult.getColumnModel().getColumn(2).setMaxWidth(200); //日期 宽度
//		tableQueyResult.getColumnModel().getColumn(2).setMaxWidth(500); //日期 宽度
		
	}
	
	//用来决定多个button(第一页/上一页/下一页/)的使能状态
	//第一页/上一页的使能状态只与queryIndex有关
	//下一页的使能状态只与itemNumCurPage有关
	public void buttonEnableControl() {
		if (get_queryIndex()<=0) {//应该只能等于？
			buttonFirstPage.setEnabled(false);
			buttonLastPage.setEnabled(false);	
		}
		else{
			buttonFirstPage.setEnabled(true);
			buttonLastPage.setEnabled(true);			
		}
		
		if (itemNumCurPage<itemNumPerPage) {
			buttonNextPage.setEnabled(false);
		}
		else buttonNextPage.setEnabled(true);
		buttonDelete.setEnabled(false);
	}
	
	//显示所有的条目
	public void initQuery() {
		set_queryIndex(0);
		queryCmd = String.format("SELECT * FROM %s ORDER BY `id` DESC LIMIT %d, %d", "fuzzy", 0, itemNumPerPage);
		displayOnePage();
		buttonEnableControl();
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
			set_queryIndex(0);
			StringBuffer queryCmdBuf = new StringBuffer(String.format("SELECT * FROM %s", "fuzzy"));				
//			if (key.length() == 0) {
//				JOptionPane.showMessageDialog(panelQureyCondition, "查询不能为空！");
//				return;
//			}		
			if (textfieldKeyword.getText().length() != 0){
				queryCmdBuf.append(String.format(" and fuzzyaim LIKE '%%%s%%'", textfieldKeyword.getText()));
			} 
			
			
			//起始日期
			if (datePickerArgueStartDate.getModel().isSelected()) {
				Date startdate = new Date(datePickerArgueStartDate.getModel().getYear()-1900,
								     datePickerArgueStartDate.getModel().getMonth(),
					                 datePickerArgueStartDate.getModel().getDay() );
				queryCmdBuf.append(String.format(" and fuzzyargueTime>='%s'", startdate));
			}
			//结束日期
			if (datePickerArgueEndDate.getModel().isSelected()) {
				Date enddate = new Date(datePickerArgueEndDate.getModel().getYear()-1900,
									 datePickerArgueEndDate.getModel().getMonth(),
									 datePickerArgueEndDate.getModel().getDay() );
				queryCmdBuf.append(String.format(" and fuzzyargueTime<='%s'", enddate));
			}
			queryCmdBuf.append(String.format(" ORDER BY `id` DESC LIMIT %d,%d", 0,itemNumPerPage));
			queryCmd = queryCmdBuf.toString().replaceFirst("and", "WHERE");
			displayOnePage();
					
			if(tableQueyResult.getRowCount()==0){
				textareaQueyDetail.setText("没有查询到相关记录");
			}else {
				textareaQueyDetail.setText("");					
			}
			buttonEnableControl();	
		}
	});
	
	//选中table的一行时，在textarea中显示详细信息
	tableQueyResult.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {// 仅当鼠标单击时响应
			// 得到选中的行的索引值
			buttonDelete.setEnabled(true);
			int r = tableQueyResult.getSelectedRow();
			textareaQueyDetail.setText(tableQueyResult.getValueAt(r, 3).toString());
			textareaQueyDetail.append("\n");
			textareaQueyDetail.append(tableQueyResult.getValueAt(r, 4).toString());
		}
	});
	
	buttonFirstPage.addActionListener(new ActionListener() {			
		@Override
		public void actionPerformed(ActionEvent e) {
			String limitOldStr = String.format("LIMIT %d,", get_queryIndex());
			String limitNewStr = String.format("LIMIT %d,", 0);
			set_queryIndex(0);
			queryCmd = queryCmd.replaceFirst(limitOldStr, limitNewStr);
			//System.out.println("[First Page]  "+queryCmd);
			displayOnePage();
			buttonEnableControl();
		}
	});
	
	buttonNextPage.addActionListener(new ActionListener() {			
		@Override
		public void actionPerformed(ActionEvent e) {
			String limitOldStr = String.format("LIMIT %d,", get_queryIndex());
			String limitNewStr = String.format("LIMIT %d,", get_queryIndex()+itemNumPerPage);
			set_queryIndex(get_queryIndex()+itemNumPerPage);
			queryCmd = queryCmd.replaceFirst(limitOldStr, limitNewStr);
			//System.out.println("[Next Page]  "+queryCmd);
			displayOnePage();
			buttonEnableControl();
		}
	});
	
	//当queryIndex<=0时，【上一页】应该变暗。这里为了更保险，在actionPerformed内部重新判断。
	buttonLastPage.addActionListener(new ActionListener() {			
		@Override
		public void actionPerformed(ActionEvent e) {
			if (get_queryIndex()<=0) {
				buttonLastPage.setEnabled(false);
				return;
			}
			String limitOldStr = String.format("LIMIT %d,", get_queryIndex());
			String limitNewStr = String.format("LIMIT %d,", get_queryIndex()-itemNumPerPage);
			set_queryIndex(get_queryIndex()-itemNumPerPage);
			queryCmd = queryCmd.replaceFirst(limitOldStr, limitNewStr);
			//System.out.println("[Last Page]  "+queryCmd);
			displayOnePage();
			buttonEnableControl();
		}
	});
	
	buttonDelete.addActionListener(new ActionListener() {			
		@Override
		public void actionPerformed(ActionEvent e) {
			int rowIds[] = tableQueyResult.getSelectedRows();				
			if (rowIds.length==0) {
				buttonDelete.setEnabled(false);
				return;
			}
			JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");
//			dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://127.0.0.1:3306/db_zlpj","root","wipm");	    
	    	
			for (int i = 0; i < rowIds.length; i++) {
				Object PK_id = tableQueyResult.getModel().getValueAt(rowIds[i], 0);
				
				String deleteCmd = String.format("DELETE FROM %s where `id`= %d","fuzzy",PK_id);
				//System.out.println("deleteCmd: "+deleteCmd);
				//删除anp
				if (!mysql_.delete(deleteCmd)) {
					JOptionPane.showMessageDialog(null, "删除失败","注意！",JOptionPane.ERROR_MESSAGE);
				}	
			}
			JOptionPane.showMessageDialog(null, String.format("成功删除%d条记录！", rowIds.length));
//			dbio.close();
			mysql_.close();
			
			//删除以后，重新查询
			textareaQueyDetail.setText("");
			displayOnePage();
			buttonEnableControl();
		}
	});
} 

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					DeaQueryFrm frame = new DeaQueryFrm();
					frame.setVisible(true);
					frame.pack();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
