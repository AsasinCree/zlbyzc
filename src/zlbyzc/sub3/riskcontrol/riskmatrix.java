package zlbyzc.sub3.riskcontrol;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Box;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import zlbyzc.sub3.sub3inFrame;

//import rm1.ConnectionFactory;

import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.awt.event.ActionEvent;

public class riskmatrix extends sub3inFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_1_1;
	private JTextField textField_2;
	private JTable table_3;
	private JTable table_4;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;
	JComboBox comboBox = new JComboBox();//选择框
	JButton button = new JButton("\u5220\u9664\u9009\u4E2D\u884C");// 删除选中行
	JButton btnNewButton_save = new JButton("\u4FDD\u5B58");//保存按键


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					riskmatrix frame = new riskmatrix();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public riskmatrix() {
		setTitle("\u98CE\u9669\u7BA1\u63A7\u5B50\u7CFB\u7EDF--\u98CE\u9669\u77E9\u9635\u5206\u6790\u6CD5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1089, 501);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("\u9879\u76EE\u540D\u79F0\uFF1A");
		panel_2.add(lblNewLabel);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut);
		
		JLabel lblNewLabel_1 = new JLabel("\u8BA8\u8BBA\u65F6\u95F4\uFF1A");
		panel_2.add(lblNewLabel_1);
		
//		textField_1 = new JTextField();
//		panel_2.add(textField_1);
//		textField_1.setColumns(10);
		
		
		
		
		//ren
		//自动获取日期
		Calendar ca = Calendar.getInstance();//自动获取当前日期
		String projectTime = String.format("%s-%s-%s", ca.get(Calendar.YEAR),ca.get(Calendar.MONTH)+1,ca.get(Calendar.DATE));
		//textField_time = new JTextField();
		textField_1 = new JTextField(projectTime);
		panel_2.add(textField_1);
		textField_1.setColumns(10);
		//textField_time.setEnabled(false);
		textField_1.setEditable(false);


		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		panel_2.add(horizontalStrut_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u53C2\u4E0E\u4EBA\u5458\uFF1A");
		panel_2.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		panel_2.add(textField_2);
		textField_2.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator = new JSeparator();
		panel_3.add(separator, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("\u8BF7\u53C2\u8003\u98CE\u9669\u7684\u5212\u5206\u6807\u51C6\uFF1A");
		panel_4.add(lblNewLabel_3);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_6.add(scrollPane);
		


		
		table_3 = new JTable();
		table_3.setRowHeight(30);
		DefaultTableCellRenderer t = new DefaultTableCellRenderer();
		t.setHorizontalAlignment(JLabel.CENTER);
		table_3.setDefaultRenderer(Object.class, t);
		table_3.setModel(new DefaultTableModel(
			new Object[][] {
				{"\u5173\u952E(critical) ", "\u4E00\u65E6\u98CE\u9669\u4E8B\u4EF6\u53D1\u751F,\u5C06\u5BFC\u81F4\u9879\u76EE\u5931\u8D25\u3002 "},
				{"\u4E25\u91CD(serious) ", "\u4E00\u65E6\u98CE\u9669\u4E8B\u4EF6\u53D1\u751F,\u4F1A\u5BFC\u81F4\u7ECF\u8D39\u5927\u5E45\u589E\u52A0,\u9879\u76EE \u5468\u671F\u5EF6\u957F,\u53EF\u80FD\u65E0\u6CD5\u6EE1\u8DB3\u9879\u76EE\u7684\u4E8C\u7EA7\u9700\u6C42\u3002 "},
				{"\u4E00\u822C(moderate) ", "\u4E00\u65E6\u98CE\u9669\u4E8B\u4EF6\u53D1\u751F,\u4F1A\u5BFC\u81F4\u7ECF\u8D39\u4E00\u822C\u7A0B\u5EA6\u7684\u589E \u52A0,\u9879\u76EE\u5468\u671F\u4E00\u822C\u6027\u5EF6\u957F,\u4F46\u4ECD\u80FD\u6EE1\u8DB3\u9879\u76EE\u4E00\u4E9B \u91CD\u8981\u7684\u8981\u6C42\u3002 "},
				{"\u5FAE\u5C0F(minor) ", "\u4E00\u65E6\u98CE\u9669\u4E8B\u4EF6\u53D1\u751F,\u7ECF\u8D39\u53EA\u6709\u5C0F\u5E45\u589E\u52A0,\u9879\u76EE\u5468 \u671F\u5EF6\u957F\u4E0D\u5927,\u9879\u76EE\u9700\u6C42\u7684\u5404\u9879\u6307\u6807\u4ECD\u80FD\u4FDD\u8BC1\u3002 "},
				{"\u53EF\u5FFD\u7565(negligible) ", "\u4E00\u65E6\u98CE\u9669\u4E8B\u4EF6\u53D1\u751F,\u5BF9\u9879\u76EE\u6CA1\u6709\u5F71\u54CD\u3002 "},
			},
			new String[] {
				"\u98CE\u9669\u5F71\u54CD\u7B49\u7EA7", "\u5B9A\u4E49\u6216\u8BF4\u660E"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_3.getColumnModel().getColumn(0).setPreferredWidth(160);
		table_3.getColumnModel().getColumn(1).setPreferredWidth(406);
		scrollPane.setViewportView(table_3);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_6.add(scrollPane_1);
		
		table_4 = new JTable();
		table_4.setRowHeight(30);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table_4.setDefaultRenderer(Object.class, r);
		table_4.setModel(new DefaultTableModel(
			new Object[][] {
				{"0-10", "\u975E\u5E38\u4E0D\u53EF\u80FD\u53D1\u751F "},
				{"11-40", "\u4E0D\u53EF\u80FD\u53D1\u751F "},
				{"41-60", "\u53EF\u80FD\u5728\u9879\u76EE\u4E2D\u671F\u53D1\u751F "},
				{"61-90", "\u53EF\u80FD\u53D1\u751F "},
				{"91-100", "\u6781\u53EF\u80FD\u53D1\u751F "},
			},
			new String[] {
				"\u98CE\u9669\u6982\u7387\u8303\u56F4\uFF08%\uFF09", "\u89E3\u91CA\u8BF4\u660E"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_4.getColumnModel().getColumn(0).setPreferredWidth(89);
		table_4.getColumnModel().getColumn(0).setMinWidth(20);
		table_4.getColumnModel().getColumn(1).setPreferredWidth(146);
		scrollPane_1.setViewportView(table_4);
		
		JPanel panel_7 = new JPanel();
		panel.add(panel_7, BorderLayout.SOUTH);
		panel_7.setPreferredSize(new Dimension(10, 20));
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		JSeparator separator_1 = new JSeparator();
		panel_7.add(separator_1);
		
		JPanel panel_8 = new JPanel();
		contentPane.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9, BorderLayout.NORTH);
		panel_9.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_11 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_11.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_9.add(panel_11);
		
		JLabel lblNewLabel_4 = new JLabel("\u8BF7\u8F93\u5165\u98CE\u9669\u77E9\u9635\u7684\u5404\u53C2\u6570\uFF1A");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		panel_11.add(lblNewLabel_4);
		
		JPanel panel_13 = new JPanel();
		panel_9.add(panel_13);
		panel_13.setLayout(new BoxLayout(panel_13, BoxLayout.X_AXIS));
		
		JLabel label = new JLabel("\u98CE\u9669\u540D\u79F0\uFF1A");
		panel_13.add(label);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		panel_13.add(textField_3);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		panel_13.add(horizontalStrut_2);
		
		JLabel label_1 = new JLabel("\u98CE\u9669\u5F71\u54CD\uFF1A");
		panel_13.add(label_1);
		

		comboBox.setMaximumRowCount(15);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\u8BF7\u9009\u62E9", "\u5173\u952E", "\u4E25\u91CD", "\u4E00\u822C", "\u5FAE\u5C0F", "\u53EF\u5FFD\u7565"}));
		panel_13.add(comboBox);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		panel_13.add(horizontalStrut_3);
		
		JLabel label_2 = new JLabel("\u98CE\u9669\u5F71\u54CD\u91CF\u5316\u503C\uFF1A");
		panel_13.add(label_2);
		
		textField_4 = new JTextField();
		textField_4.setColumns(5);
		panel_13.add(textField_4);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel_13.add(horizontalStrut_4);
		
		JLabel label_3 = new JLabel("\u98CE\u9669\u53D1\u751F\u6982\u7387(%)\uFF1A");
		panel_13.add(label_3);
		
		textField_5 = new JTextField();
		textField_5.setColumns(5);
		panel_13.add(textField_5);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(50);
		panel_13.add(horizontalStrut_5);
		
		JButton btnNewButton_2 = new JButton("\u786E  \u5B9A");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String txt1 = textField.getText();
				String txt2 = textField_1.getText();
				String txt3 = textField_2.getText();

				String txt4 = textField_3.getText();
				String txt5 = textField_4.getText();
				String txt6 = textField_5.getText();
				int level = 0;
				String[][] RiskLevel = { { "中", "中", "低", "低", "低" }, { "高", "中", "中", "低", "低" },
						{ "高", "中", "中", "中", "低" }, { "高", "中", "中", "中", "中" }, { "高", "高", "高", "高", "中" } };

				if (comboBox.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null,  "请选择风险等级...");
					return;
				}
				if (!(txt5.trim().matches("\\d+.?\\d{1,}") || txt5.trim().matches("\\d{1,}"))) {
					JOptionPane.showMessageDialog(null, "风险量化值请输入...");
					return;
				} else {
					// int sum = Integer.parseInt(txt5.trim());
					Float sum = Float.parseFloat(txt5.trim());
					if (sum < 0 || sum > 5) {
						JOptionPane.showMessageDialog(null, "请输入风险量化值正确范围(0-5)");
						return;
					}
				}
				if (!txt6.trim().matches("\\d{1,}")) {
					JOptionPane.showMessageDialog(null, "风险发生概率请输入整数...");
					return;
				} else {
					int sum = Integer.parseInt(txt6.trim());
					if (sum < 0 || sum > 100) {
						JOptionPane.showMessageDialog(null, "请输入风险发生概率正确范围(0-100)");
						return;
					}
					if (0 <= sum && sum <= 10) {
						level = 1;
					}
					if (11 <= sum && sum <= 40) {
						level = 2;
					}
					if (41 <= sum && sum <= 60) {
						level = 3;
					}
					if (61 <= sum && sum <= 90) {
						level = 4;
					}
					if (91 <= sum && sum <= 100) {
						level = 5;
					}
				}
				String selectText = comboBox.getSelectedItem() + "";
				int selectItem = comboBox.getSelectedIndex();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] { model.getRowCount() + 1, txt4, selectText, txt5, txt6,
						RiskLevel[level - 1][selectItem - 1], false, null, null, null });
				button.setEnabled(true);
			
			}
		});
		panel_13.add(btnNewButton_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_8.add(scrollPane_2, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u5E8F\u53F7", "\u98CE\u9669\u540D\u79F0", "\u98CE\u9669\u5F71\u54CD", "\u98CE\u9669\u5F71\u54CD\u91CF\u5316\u503C", "\u98CE\u9669\u6982\u7387\uFF08%\uFF09", "\u98CE\u9669\u7B49\u7EA7", "", "Borda\u6570", "Borda\u5E8F\u503C", "\u98CE\u9669\u7BA1\u63A7\u63AA\u65BD"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Boolean.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		scrollPane_2.setViewportView(table);
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10, BorderLayout.SOUTH);
		

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DefaultTableModel model = (DefaultTableModel) table.getModel();

				// 鍒犻櫎瀹炵幇鍘熸柟娉曪細浠庝笂寰�涓嬮亶鍘�*******************************************
				// for (int i = 0; i < model.getRowCount();) {
				//
				// if (model.getValueAt(i, 6).toString().equals("true")) {
				// model.removeRow(i);
				// i = 0;
				// continue;
				// } else {
				// i++;
				// }
				//
				// }

				// 删除实现原方法：从上往下遍历*******************************************
				for (int i = model.getRowCount() - 1; i >= 0; i--) {
					if (model.getValueAt(i, 6).toString().equals("true")) {
						model.removeRow(i);
					}
				}
				// 删除实现新方法：从下往上遍历，避免出现删除一列之后出现的行号全部发生的变化导致遍历受阻
			
			}
		});
		button.setEnabled(false);
		panel_10.add(button);
		
		JButton btnNewButton = new JButton("\u63D0\u4EA4\u8BA1\u7B97");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				DefaultTableModel model = (DefaultTableModel) table.getModel();
				List<Integer> list = new ArrayList<Integer>();

				// List<Integer> list3 = new ArrayList<Integer>();//风险量化值数据存储
				List<Float> list3 = new ArrayList<Float>();
				List<Integer> list3x = new ArrayList<Integer>();
				List<Integer> list4 = new ArrayList<Integer>();//风险概率数据存储
				for (int i = 0; i < model.getRowCount(); i++) {
					// int value3 = getInt(model.getValueAt(i, 3));
					Float value3 = getFloat(model.getValueAt(i, 3));
					int value4 = getInt(model.getValueAt(i, 4));
					list3.add(value3);
					list4.add(value4);
				}

				list3x = getBigSumListFloat(list3);
				list4 = getBigSumList(list4);
				// Table2 frame = new Table2();
				List<Integer> resultList = unionList(list3x, list4);
				List<Integer> resultListnumber = getBigSumList(resultList);
				for (int i = 0; i < model.getRowCount(); i++) {

					// frame.addRow(new Object[] { model.getValueAt(i, 0),
					// model.getValueAt(i, 1), model.getValueAt(i, 2),
					// model.getValueAt(i, 3), model.getValueAt(i, 4),
					// model.getValueAt(i, 5), resultList.get(i),
					// resultListnumber.get(i), null });
					model.setValueAt(resultList.get(i), i, 7); //在一个界面显示数据
					model.setValueAt(resultListnumber.get(i), i, 8);

				}
				// btnNewButton_save.setEnabled(true);
				btnNewButton_save.setEnabled(true);
				button.setEnabled(false);

				// frame.setVisible(true);

				// ConnectionFactory c= new ConnectionFactory();
				// try {
				// c.OpenConn();
				// String sql="select * from test";
				// String sql2="insert into test
				// (RANGESTART,RANGEOVER,LEVEL,VALUE) values(1,10,100,1000)";
				//// ResultSet rs=c.executeQuery(sql);
				//// while(rs.next()){
				//// System.out.println(rs.getString(1));
				//// System.out.println(rs.getString(2));
				//// System.out.println(rs.getString(3));
				//// }
				// c.insert(sql2);
				// c.close();
				// System.out.println();
				// } catch (Exception e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

			
				
				
			}
		});
		panel_10.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u53D6\u6D88");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		panel_10.add(btnNewButton_1);
		

		btnNewButton_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String projectname = textField.getText();
				String projecttime = textField_1.getText();
				String projectpeople = textField_2.getText();
				DefaultTableModel model = (DefaultTableModel) table.getModel();

	//			ConnectionFactory connect = new ConnectionFactory();

				JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");
				

				
				try {
			
					String sqlE = "SELECT * FROM riskproject WHERE projectname = '" + projectname + "'";
					String projcetsql = "insert into riskproject (Projectname,Projecttime,People) values ('"
							+ projectname + "','" + projecttime + "','" + projectpeople + "')";

	//				ResultSet rs = connect.executeQuery(sqlE);
					ResultSet rs = mysql_.query(sqlE);
					if (rs.next()) {
						JOptionPane.showMessageDialog(null, "该项目名称已存在，请修改项目名称");

					} else {
						mysql_.insert(projcetsql);
						// preparedStatement.setString(1, projectname);
						// preparedStatement.setString(2, projecttime);
						// preparedStatement.setString(3, projectpeople);
						// preparedStatement.executeUpdate();
						for (int i = 0; i < model.getRowCount(); i++) {

							String RiskName = (String) model.getValueAt(i, 1);
							String RiskImpact = (String) model.getValueAt(i, 2);

							String RiskImpactNumber1 = (String) model.getValueAt(i, 3);
							Float RiskImpactNumber = Float.parseFloat(RiskImpactNumber1.trim());

							String RiskProbability1 = (String) model.getValueAt(i, 4);
							int RiskProbability = Integer.parseInt(RiskProbability1.trim());

							String RiskLevel = (String) model.getValueAt(i, 5);

							String BordaNumber1 = "" + model.getValueAt(i, 7);
							int BordaNumber = Integer.parseInt(BordaNumber1.trim());

							String BordaOrder1 = "" + model.getValueAt(i, 8);
							int BordaOrder = Integer.parseInt(BordaOrder1.trim());

							String RiskControl = "" + model.getValueAt(i, 9);

							String prijecetimformation = "insert into RISKINFORMATION (ProjectName,RiskName,RiskImpact,RiskImpactNumber,RiskProbability,RiskLevel,BordaNumber,BordaOrder,RiskControl) values ('"
									+ projectname + "','" + RiskName + "','" + RiskImpact + "'," + RiskImpactNumber
									+ "," + RiskProbability + ",'" + RiskLevel + "'," + BordaNumber + "," + BordaOrder
									+ ",'" + RiskControl + "')";
							mysql_.insert(prijecetimformation);

						}
						//JOptionPane.showConfirmDialog(null, "保存成功");
						JOptionPane.showMessageDialog(null, "保存成功！");
					}

					// String sql="select * from test";
					// String projcetsql="insert into test
					// (RANGESTART,RANGEOVER,LEVEL,VALUE)
					// values(1,10,100,1000)";

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						mysql_.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
				
				
			}
		});
		btnNewButton_save.setEnabled(false);
		panel_10.add(btnNewButton_save);
	}

	

	
	

	private List<Integer> unionList(List<Integer> list3, List<Integer> list4) {
		List<Integer> retList = new ArrayList<Integer>();
		for (int i = 0; i < list3.size(); i++) {
			retList.add(list3.size() + list4.size() - list3.get(i) - list4.get(i));
		}
		return retList;
	}

	private List<Integer> cloneList(List<Integer> list, int index) {
		List<Integer> retSumList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			if (i != index) {
				retSumList.add(list.get(i));
			}
		}
		return retSumList;
	}

	private List<Float> cloneListFloat(List<Float> list, int index) {
		List<Float> retSumList = new ArrayList<Float>();
		for (int i = 0; i < list.size(); i++) {
			if (i != index) {
				retSumList.add(list.get(i));
			}
		}
		return retSumList;
	}

	private int getBigThanSelf(List<Integer> list, int value) {
		int sum = 0;
		for (int i : list) {
			if (i > value) {
				sum++;
			}
		}
		return sum;
	}

	private int getBigThanSelfFloat(List<Float> list, Float value) {
		int sum = 0;
		for (Float i : list) {
			if (i > value) {
				sum++;
			}
		}
		return sum;
	}

	private List<Integer> getBigSumList(List<Integer> list) {
		List<Integer> retSumList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			int calcValue = getBigThanSelf(cloneList(list, i), list.get(i));
			retSumList.add(calcValue);
		}
		return retSumList;
	}

	private List<Integer> getBigSumListFloat(List<Float> list) {
		List<Integer> retSumList = new ArrayList<Integer>();
		for (int i = 0; i < list.size(); i++) {
			int calcValue = getBigThanSelfFloat(cloneListFloat(list, i), list.get(i));
			retSumList.add(calcValue);
		}
		return retSumList;
	}

	private int getInt(Object value) {
		if (value == null) {
			return 0;
		}
		return Integer.parseInt(value.toString());
	}

	private Float getFloat(Object value) {
		if (value == null) {
			return (float) 0;
		}
		// return Integer.parseInt(value.toString());
		return Float.parseFloat(value.toString());
	}

}
