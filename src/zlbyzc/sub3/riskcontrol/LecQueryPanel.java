package zlbyzc.sub3.riskcontrol;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.JFrame;
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
import javax.swing.table.TableColumn;

import org.jdatepicker.JDatePicker;



public class LecQueryPanel extends JPanel  {
	private static final long serialVersionUID = 1L;
	
	private String tablename = "lec_table";  //table 设置
	
	//查询条件组件
	private JPanel panelQureyCondition; // 查询条件输入panel
	private JLabel lableKeyword;//关键词   
	private JLabel lablePeople;//人员
	private JLabel lableArgueStartDate;//起始时间 
	private JLabel lableArgueEndDate;//结束时间
	private JTextField textfieldKeyword;
	private JTextField textfieldPeople;
	private JButton buttonQuery;
	private JDatePicker datePickerArgueStartDate;
	private JDatePicker datePickerArgueEndDate;
	private JPanel panelKey;
	private JPanel panelPeople;
	private JPanel panelArgueStartDate;
	private JPanel panelArgueEndDate; 
	
	//查询结果表
	private JPanel panelQueyResult; // 查询查询结果表panel
	private JTable tableQueyResult;
	
	//查询结果详情
	private JPanel panelQueyDetail; //装载textAear
	private JTextArea textareaQueyDetail;

	public LecQueryPanel() {
		initializeComponent();
		layoutComponent();
		setEvents();
		showLecTable(); //added by shenhui,2016-05-11
	}
	
	//点击查询按钮之前显示Lectable的所有内容 added by 2016-05-11
	private void showLecTable(){
		JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");

		StringBuffer queryCmdBuf = new StringBuffer(String.format("SELECT DISTINCT * FROM %s", "lec_table"));
		String queryCmd = queryCmdBuf.toString();

		ResultSet res = mysql_.query(queryCmd);
		ResultSetTableModel model = new ResultSetTableModel(res);
				
				//model.setColumnNames( new String[] {"目标","日期","AHP模型"} ); //设置table title
				model.setColumnNames( new String[] {"项目名称","日期","人员","风险名称","L","E","C","D","风险等级"} ); //设置table title
				tableQueyResult.setModel(model);
				
				if(tableQueyResult.getRowCount()==0){
					textareaQueyDetail.setText("没有查询到相关记录");
				}else {
					//textareaQueyDetail.setText("");					
				}
		
	}
	
	private void initializeComponent() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//查询条件 panel
		panelQureyCondition = new JPanel(); 
		panelKey = new JPanel();
		panelPeople = new JPanel();
		panelArgueStartDate = new JPanel();
		panelArgueEndDate = new JPanel(); 
		
		panelQureyCondition.setLayout(new BoxLayout(panelQureyCondition, BoxLayout.Y_AXIS));
		panelQureyCondition.setBorder(new TitledBorder(new EtchedBorder(),"查询条件"));
		panelKey.setLayout(new BoxLayout(panelKey, BoxLayout.X_AXIS));
		panelPeople.setLayout(new BoxLayout(panelPeople, BoxLayout.X_AXIS));
		panelArgueStartDate.setLayout(new BoxLayout(panelArgueStartDate, BoxLayout.X_AXIS));
		panelArgueEndDate.setLayout(new BoxLayout(panelArgueEndDate, BoxLayout.X_AXIS)); 
		
		lableKeyword = new JLabel("项目名称 ");
		lablePeople = new JLabel("参与人员 ");
		lableArgueStartDate = new JLabel("起始日期 ");
		lableArgueEndDate = new JLabel("结束日期 ");
		textfieldKeyword = new JTextField();
		textfieldPeople = new JTextField();
		buttonQuery = new JButton("查询");
		datePickerArgueStartDate = new JDatePicker();
		datePickerArgueEndDate = new JDatePicker();

		
		//查询结果详情 panel
		panelQueyResult = new JPanel();
		panelQueyResult.setLayout(new BorderLayout());
		panelQueyResult.setBorder(new TitledBorder(new EtchedBorder(),	"查询结果列表"));
		tableQueyResult = new JTable();
		
		//查询结果详情 panel
//		panelQueyDetail = new JPanel();
//		panelQueyDetail.setLayout(new BorderLayout());
//		panelQueyDetail.setBorder(new TitledBorder(new EtchedBorder(),	"查询详情"));
//		textareaQueyDetail = new JTextArea(40, 80);	
		
		textfieldKeyword.setMinimumSize(new Dimension(100,25));
		textfieldKeyword.setMaximumSize(new Dimension(150,30));
		textfieldPeople.setMinimumSize(new Dimension(100,25));
		textfieldPeople.setMaximumSize(new Dimension(150,30));
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
		
		panelPeople.add(lablePeople);
		panelPeople.add(textfieldPeople);
		panelPeople.add(Box.createHorizontalStrut(75));
		
		panelArgueStartDate.add(lableArgueStartDate);
		panelArgueStartDate.add(datePickerArgueStartDate);
		panelArgueStartDate.add(Box.createHorizontalStrut(80));
		
		panelArgueEndDate.add(lableArgueEndDate);
		panelArgueEndDate.add(datePickerArgueEndDate);
		panelArgueEndDate.add(Box.createHorizontalStrut(20));
		panelArgueEndDate.add(buttonQuery);
		
		panelQureyCondition.add(panelKey);
		panelQureyCondition.add(Box.createVerticalStrut(20));
		panelQureyCondition.add(panelPeople);
		panelQureyCondition.add(Box.createVerticalStrut(20));
		panelQureyCondition.add(panelArgueStartDate);	
		panelQureyCondition.add(Box.createVerticalStrut(20));	
		panelQureyCondition.add(panelArgueEndDate);
		panelQureyCondition.add(Box.createVerticalStrut(20));
		
		
		//查询结果详情 panel
		panelQueyResult.add(new JScrollPane(tableQueyResult));
		
		//查询结果详情 panel
		//panelQueyDetail.add(new JScrollPane(textareaQueyDetail));
		
		add(panelQureyCondition);
		add(panelQueyResult);
		//add(panelQueyDetail);
	}
	
	private void setEvents() {
		
		//监听查询button
		buttonQuery.addActionListener(new ActionListener() {			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String key = textfieldKeyword.getText();
				String people = textfieldPeople.getText();
//				if (key.length() == 0 && people.length() == 0) {
//					JOptionPane.showMessageDialog(panelQureyCondition, "查询不能为空！");
//					return;
//				}
				
				JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");

				StringBuffer queryCmdBuf = new StringBuffer(String.format("SELECT DISTINCT * FROM %s", "lec_table"));
				 
				 if (key != "") {
					queryCmdBuf.append(String.format(" and PROJECTNAME LIKE '%%%s%%'", key));
				 }
				 
				if (people != "") {
					queryCmdBuf.append(String.format(" and PEOPLE LIKE '%%%s%%'", people));
					
				}
				//起始日期
				if (datePickerArgueStartDate.getModel().isSelected()) {
					Date startdate = new Date(datePickerArgueStartDate.getModel().getYear()-1900,
									     datePickerArgueStartDate.getModel().getMonth(),
						                 datePickerArgueStartDate.getModel().getDay() );
					queryCmdBuf.append(String.format(" and PROJECTTIME>='%s'", startdate));
				}
				//结束日期
				if (datePickerArgueEndDate.getModel().isSelected()) {
					Date enddate = new Date(datePickerArgueEndDate.getModel().getYear()-1900,
										 datePickerArgueEndDate.getModel().getMonth(),
										 datePickerArgueEndDate.getModel().getDay() );
					queryCmdBuf.append(String.format(" and PROJECTTIME<='%s'", enddate));
				}
				//String queryCmd = queryCmdBuf.toString();
				String queryCmd = queryCmdBuf.toString().replaceFirst("and", "WHERE");

				ResultSet res = mysql_.query(queryCmd);
				ResultSetTableModel model = new ResultSetTableModel(res);
				


				//model.setColumnNames( new String[] {"目标","日期","AHP模型"} ); //设置table title
				model.setColumnNames( new String[] {"项目名称","日期","人员","风险名称","L","E","C","D","风险等级"} ); //设置table title
				tableQueyResult.setModel(model);
				
				if(tableQueyResult.getRowCount()==0){
					textareaQueyDetail.setText("没有查询到相关记录");
				}else {
					//textareaQueyDetail.setText("");					
				}				
			}
		});
		
		//选中table的一行时，在textarea中显示详细信息
//		tableQueyResult.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {// 仅当鼠标单击时响应
//				// 得到选中的行的索引值
//				int r = tableQueyResult.getSelectedRow();
//				String rowdes = "";
//				for (int i = 0; i < tableQueyResult.getColumnCount(); i++) {
//					rowdes +=tableQueyResult.getColumnName(i)+": "+ tableQueyResult.getValueAt(r, i) + "\n";
//				}
//				textareaQueyDetail.setText(rowdes);
//			}
//		});
	} 
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LecQueryPanel panelAhpQuery = new LecQueryPanel();
					JFrame frame = new JFrame();
					frame.setContentPane(panelAhpQuery);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

//扩展AbstractTableModel，用于将一个ResultSet包装成TableModel
class ResultSetTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private String[] columnNames;

	// 构造器，初始化rs和rsmd两个属性
	public ResultSetTableModel(ResultSet aResultSet) {
		rs = aResultSet;
		try {
			rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			columnNames = new String[colCount];
			for (int i = 0; i < colCount; i++) {
				columnNames[i] = rsmd.getColumnName(i+1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//增加的方法，可以设置列名（因为mysql字段的变量名可能意思不清晰）
	public void setColumnNames(String[] colNames) {
		if(colNames.length!=columnNames.length){
			System.out.println("colNames has error length!");
			return;
		}
		for (int i = 0; i < columnNames.length; i++) {
			columnNames[i] = colNames[i];
		}
		super.fireTableStructureChanged(); 		
	}

	// 重写getColumnName方法，用于为该TableModel设置列名
	public String getColumnName(int c) {
		return columnNames[c];
	}

	// 重写getColumnCount方法，用于设置该TableModel的列数
	public int getColumnCount() {
		try {
			return rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 重写getValueAt方法，用于设置该TableModel指定单元格的值
	public Object getValueAt(int r, int c) {
		try {
			rs.absolute(r + 1);
			return rs.getObject(c + 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 重写getColumnCount方法，用于设置该TableModel的行数
	public int getRowCount() {
		try {
			rs.last();
			return rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 重写isCellEditable返回true，让每个单元格可编辑
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	// 重写setValueAt方法，用于实现用户编辑单元格时，程序做出对应的动作
	public void setValueAt(Object aValue, int row, int column) {
		try {
			// 结果集定位到对应的行数
			rs.absolute(row + 1);
			// 修改单元格多对应的值
			rs.updateObject(column + 1, aValue);
			// 提交修改
			rs.updateRow();
			// 触发单元格的修改事件
			fireTableCellUpdated(row, column);
		} catch (SQLException evt) {
			evt.printStackTrace();
		}
	}
}
