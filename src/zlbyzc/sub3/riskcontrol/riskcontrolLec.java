package zlbyzc.sub3.riskcontrol;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.Box;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
//import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.jdatepicker.JDatePanel;

import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ScrollPaneConstants;
import javax.swing.DropMode;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import java.awt.Dimension;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.JRadioButton;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Connection;

import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

import zlbyzc.sub3.sub3inFrame;

public class riskcontrolLec extends sub3inFrame {

	private JPanel contentPane;
	protected Object frame;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblNewLabel;
	private JTextField textField_name;
	private JLabel lblNewLabel_1;
	
	//ren
	private JTextField textField_time;
	//private JDatePanel datapanel_projectDate;
	
	private JLabel lblNewLabel_2;
	private JTextField textField_people;
	private JPanel panel_4;
	private JLabel lblNewLabel_3;
	private JPanel panel_5;
	private JPanel panel_6;
	private JPanel panel_7;
	private JPanel panel_8;
	private JPanel panel_9;
	private JPanel panel_10;
	private JRadioButton rdbtn_defaultRiskLevel;
	private JRadioButton rdbtn_userdefineRiskLevel;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTable tb_defaultRiskLevel;
	private JTable tb_userdefineRiskLevel;
	private JPanel panel_11;
	private JPanel panel_12;
	private JSeparator separator;
	private JSeparator separator_1;
	private JPanel panel_13;
	private JLabel lbllec;
	private JPanel panel_14;
	private JLabel lblNewLabel_4;
	private JTextField tf_riskname;
	private JLabel lblL;
	private JLabel lblC;
	private JLabel lblE;
	private JButton button_sure;
	private JComboBox cb_likelihood;
	private JComboBox cb_exposure;
	private JComboBox cb_consequence;
	private JScrollPane scrollPane_2;
	private JTable tb_lecd;
	private JPanel panel_15;
	private JButton btnNewButton_delete;
	private JButton btnNewButton_save;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private Component horizontalStrut_4;
	private Component horizontalStrut_5;
	private boolean saveFlag = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					riskcontrolLec frame = new riskcontrolLec();
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
	public riskcontrolLec() {
		super("\u98CE\u9669\u7BA1\u63A7\u5B50\u7CFB\u7EDF--LEC\u5206\u6790\u6CD5",true,true,true,true);
		//setTitle("\u98CE\u9669\u7BA1\u63A7\u5B50\u7CFB\u7EDF--LEC\u5206\u6790\u6CD5");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 620);
		contentPane = new JPanel();
		//contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setAlignmentY(1.0f);
		contentPane.setAlignmentX(1.0f);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));

		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(3, 1, 0, 0));

		panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		lblNewLabel = new JLabel("\u9879\u76EE\u540D\u79F0\uFF1A");
		panel_3.add(lblNewLabel);

		textField_name = new JTextField();
		panel_3.add(textField_name);
		textField_name.setColumns(10);

		horizontalStrut_4 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_4);

		lblNewLabel_1 = new JLabel("\u8BA8\u8BBA\u65F6\u95F4\uFF1A");
		
		panel_3.add(lblNewLabel_1);

		//ren
		//自动获取日期
		Calendar ca = Calendar.getInstance();//自动获取当前日期
		String projectTime = String.format("%s-%s-%s", ca.get(Calendar.YEAR),ca.get(Calendar.MONTH)+1,ca.get(Calendar.DATE));
		//textField_time = new JTextField();
		textField_time = new JTextField(projectTime);
		panel_3.add(textField_time);
		textField_time.setColumns(10);
		//textField_time.setEnabled(false);
		textField_time.setEditable(false);

		
		//ren
		//datapanel_projectDate = new JDatePanel();
		//panel_3.add(datapanel_projectDate);
		//((Component) datapanel_projectDate).setMinimumSize(new Dimension(100,35));
		//((Component) datapanel_projectDate).setMaximumSize(new Dimension(150,60));

		

		horizontalStrut_5 = Box.createHorizontalStrut(20);
		panel_3.add(horizontalStrut_5);

		lblNewLabel_2 = new JLabel("\u53C2\u4E0E\u4EBA\uFF1A");
		panel_3.add(lblNewLabel_2);

		textField_people = new JTextField();
		panel_3.add(textField_people);
		textField_people.setColumns(10);

		panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(5, 5));
		panel_2.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		separator_1 = new JSeparator();
		panel_4.add(separator_1);

		panel_6 = new JPanel();
		panel_2.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));

		lblNewLabel_3 = new JLabel("\u8BF7\u9009\u62E9\u98CE\u9669\u7B49\u7EA7\u5212\u5206\u6807\u51C6\uFF1A");
		panel_6.add(lblNewLabel_3);

		panel_5 = new JPanel();
		panel.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));

		panel_7 = new JPanel();
		panel_5.add(panel_7, BorderLayout.NORTH);
		panel_7.setLayout(new GridLayout(0, 2, 0, 0));

		panel_9 = new JPanel();
		panel_7.add(panel_9);
		panel_9.setLayout(new BoxLayout(panel_9, BoxLayout.X_AXIS));

		rdbtn_defaultRiskLevel = new JRadioButton("\u9ED8\u8BA4\u98CE\u9669\u7B49\u7EA7");
		rdbtn_defaultRiskLevel.setSelected(true);
		panel_9.add(rdbtn_defaultRiskLevel);

		panel_10 = new JPanel();
		panel_7.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.X_AXIS));

		rdbtn_userdefineRiskLevel = new JRadioButton("\u81EA\u5B9A\u4E49\u98CE\u9669\u7B49\u7EA7");
		panel_10.add(rdbtn_userdefineRiskLevel);

		// 加入组，实现单选
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtn_defaultRiskLevel);
		bg.add(rdbtn_userdefineRiskLevel);

		panel_8 = new JPanel();
		panel_5.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.X_AXIS));

		scrollPane = new JScrollPane();
		panel_8.add(scrollPane);

		tb_defaultRiskLevel = new JTable();
		tb_defaultRiskLevel.setRowHeight(30);
		DefaultTableCellRenderer t = new DefaultTableCellRenderer();
		t.setHorizontalAlignment(JLabel.CENTER);
		tb_defaultRiskLevel.setDefaultRenderer(Object.class, t);

		tb_defaultRiskLevel.setModel(new DefaultTableModel(
				new Object[][] {
						{ "20", "1\u7EA7",
								"\u5C0F\u4E8E20\uFF0C\u7A0D\u6709\u5371\u9669\uFF0C\u53EF\u4EE5\u63A5\u53D7" },
						{ "70", "2\u7EA7", "20-70\uFF0C\u4E00\u822C\u5371\u9669\uFF0C\u9700\u8981\u6CE8\u610F" },
						{ "160", "3\u7EA7", "70-160\uFF0C\u663E\u8457\u5371\u9669\uFF0C\u9700\u8981\u6574\u6539" },
						{ "320", "4\u7EA7",
								"160-320\uFF0C\u9AD8\u5EA6\u5371\u9669\uFF0C\u9700\u7ACB\u5373\u6574\u6539" },
						{ ">320", "5\u7EA7",
								"\u5927\u4E8E320\uFF0C\u53CA\u5176\u5371\u9669\uFF0C\u9700\u505C\u6B62\u4F5C\u4E1A" }, },
				new String[] { "\u4E34\u754C\u98CE\u9669\u503C", "\u98CE\u9669\u7B49\u7EA7", "\u8BF4\u660E" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tb_defaultRiskLevel.getColumnModel().getColumn(0).setPreferredWidth(30);
		tb_defaultRiskLevel.getColumnModel().getColumn(1).setPreferredWidth(30);
		scrollPane.setViewportView(tb_defaultRiskLevel);

		scrollPane_1 = new JScrollPane();
		panel_8.add(scrollPane_1);

		tb_userdefineRiskLevel = new JTable();
		tb_userdefineRiskLevel.setRowHeight(30);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		tb_userdefineRiskLevel.setDefaultRenderer(Object.class, r);

		tb_userdefineRiskLevel.setModel(new DefaultTableModel(
				new Object[][] { { null, "1\u7EA7", null }, { null, "2\u7EA7", null }, { null, "3\u7EA7", null },
						{ null, "4\u7EA7", null }, { null, "5\u7EA7", null }, },
				new String[] { "\u4E34\u754C\u98CE\u9669\u503C", "\u98CE\u9669\u7B49\u7EA7", "\u8BF4\u660E" }) {
			Class[] columnTypes = new Class[] { Float.class, String.class, Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tb_userdefineRiskLevel.getColumnModel().getColumn(0).setPreferredWidth(30);
		tb_userdefineRiskLevel.getColumnModel().getColumn(1).setPreferredWidth(30);
		scrollPane_1.setViewportView(tb_userdefineRiskLevel);

		panel_12 = new JPanel();
		panel_12.setPreferredSize(new Dimension(10, 20));
		panel.add(panel_12, BorderLayout.SOUTH);
		panel_12.setLayout(new BoxLayout(panel_12, BoxLayout.X_AXIS));

		separator = new JSeparator();
		panel_12.add(separator);

		panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		panel_11 = new JPanel();
		panel_1.add(panel_11, BorderLayout.NORTH);
		panel_11.setLayout(new GridLayout(2, 1, 0, 0));

		panel_13 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_13.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_11.add(panel_13);

		lbllec = new JLabel("\u8BF7\u8F93\u5165LEC\u53C2\u6570\uFF1A");
		lbllec.setHorizontalAlignment(SwingConstants.LEFT);
		panel_13.add(lbllec);

		panel_14 = new JPanel();
		panel_11.add(panel_14);
		panel_14.setLayout(new BoxLayout(panel_14, BoxLayout.X_AXIS));

		lblNewLabel_4 = new JLabel("\u98CE\u9669\u540D\u79F0\uFF1A");
		panel_14.add(lblNewLabel_4);

		tf_riskname = new JTextField();
		panel_14.add(tf_riskname);
		tf_riskname.setColumns(10);

		horizontalStrut = Box.createHorizontalStrut(50);
		panel_14.add(horizontalStrut);

		lblL = new JLabel("L\uFF1A");
		panel_14.add(lblL);

		cb_likelihood = new JComboBox();
		cb_likelihood.setModel(new DefaultComboBoxModel(new String[] { "0.1", "0.2", "0.5", "1", "3", "6", "10" }));
		panel_14.add(cb_likelihood);

		horizontalStrut_1 = Box.createHorizontalStrut(40);
		panel_14.add(horizontalStrut_1);

		lblE = new JLabel("E\uFF1A");
		panel_14.add(lblE);

		cb_exposure = new JComboBox();
		cb_exposure.setModel(new DefaultComboBoxModel(new String[] { "0.5", "1", "2", "3", "6", "10" }));
		panel_14.add(cb_exposure);

		horizontalStrut_2 = Box.createHorizontalStrut(40);
		panel_14.add(horizontalStrut_2);

		lblC = new JLabel("C\uFF1A");
		panel_14.add(lblC);

		cb_consequence = new JComboBox();
		cb_consequence.setModel(new DefaultComboBoxModel(new String[] { "1", "3", "7", "15", "40", "100" }));
		panel_14.add(cb_consequence);

		// “确定”按钮响应事件
		button_sure = new JButton("\u786E  \u5B9A");
		button_sure.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String risk_name = tf_riskname.getText();
				if (risk_name.length() == 0) {
					// String inputValue =
					// JOptionPane.showInputDialog("请输入风险名称");
					JOptionPane.showMessageDialog(null, "请输入风险名称", "错误提示", JOptionPane.ERROR_MESSAGE);
				}

				// tf_riskname.setText("");
				else {

					String likelihood = (String) cb_likelihood.getSelectedItem();
					Float l = Float.parseFloat(likelihood);
					// cb_likelihood.setSelectedIndex(0);

					String exposure = (String) cb_exposure.getSelectedItem();
					Float e = Float.parseFloat(exposure);
					// cb_exposure.setSelectedIndex(0);

					String consequence = (String) cb_consequence.getSelectedItem();
					Float c = Float.parseFloat(consequence);
					// cb_consequence.setSelectedIndex(0);

					Float d = l * e * c;

					// java.text.DecimalFormat df = new
					// java.text.DecimalFormat("#.00");
					// df.format(d);
					String result = String.format("%.2f", d);
					String risk_level = "";
					// 使用默认风险等级
					if (rdbtn_defaultRiskLevel.isSelected()) {

						if (d > 320) {
							risk_level = "5级";
						} else if (d > 160 && d <= 320) {
							risk_level = "4级";
						} else if (d > 70 && d <= 160) {
							risk_level = "3级 ";
						} else if (d > 20 && d <= 70) {
							risk_level = "2级 ";
						} else {
							risk_level = "1级 ";
						}
					}
					// 使用用户自定义的风险等级
					else {
						if (tb_userdefineRiskLevel.getValueAt(0, 0) == null) {
							JOptionPane.showMessageDialog(null, "请输入自定义风险等级", "错误提示", JOptionPane.ERROR_MESSAGE);
							return;
						} else {
							for (int i = 0; i < tb_userdefineRiskLevel.getRowCount(); i++) {
								float m = (float) tb_userdefineRiskLevel.getValueAt(i, 0);
								if (d < m || d == m) {
									risk_level = (String) tb_userdefineRiskLevel.getValueAt(i, 1);
									break;
								} else if (i == tb_userdefineRiskLevel.getRowCount() - 2) {
									risk_level = (String) tb_userdefineRiskLevel.getValueAt(i + 1, 1);
									break;
								}
							}

						}

					}

					DefaultTableModel model = (DefaultTableModel) tb_lecd.getModel();
					// model.addRow(new Object[] {null, null, null, null, null,
					// null, null, null});
					int newRow = tb_lecd.getRowCount();
					model.setRowCount(tb_lecd.getRowCount() + 1);

					tb_lecd.setValueAt(newRow + 1, newRow, 1);
					tb_lecd.setValueAt(risk_name, newRow, 2);
					tb_lecd.setValueAt(l, newRow, 3);
					tb_lecd.setValueAt(e, newRow, 4);
					tb_lecd.setValueAt(c, newRow, 5);
					tb_lecd.setValueAt(result, newRow, 6);
					tb_lecd.setValueAt(risk_level, newRow, 7);
					tf_riskname.setText("");
					cb_likelihood.setSelectedIndex(0);
					cb_exposure.setSelectedIndex(0);
					cb_consequence.setSelectedIndex(0);

					btnNewButton_save.setEnabled(true);
					saveFlag = true;
				}
			}
		});

		horizontalStrut_3 = Box.createHorizontalStrut(50);
		panel_14.add(horizontalStrut_3);
		panel_14.add(button_sure);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel_1.add(scrollPane_2, BorderLayout.CENTER);

		tb_lecd = new JTable();
		tb_lecd.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\u9009\u62E9", "\u5E8F\u53F7",
				"\u98CE\u9669\u540D\u79F0", "L", "E", "C", "\u98CE\u9669\u503C", "\u98CE\u9669\u7B49\u7EA7" }) {
			Class[] columnTypes = new Class[] { Boolean.class, Object.class, Object.class, Object.class, Object.class,
					Object.class, Object.class, Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tb_lecd.getColumnModel().getColumn(0).setPreferredWidth(15);
		tb_lecd.getColumnModel().getColumn(1).setPreferredWidth(15);
		tb_lecd.getColumnModel().getColumn(2).setPreferredWidth(50);
		tb_lecd.getColumnModel().getColumn(3).setPreferredWidth(30);
		tb_lecd.getColumnModel().getColumn(4).setPreferredWidth(30);
		tb_lecd.getColumnModel().getColumn(5).setPreferredWidth(30);
		tb_lecd.getColumnModel().getColumn(6).setPreferredWidth(30);
		scrollPane_2.setViewportView(tb_lecd);

		panel_15 = new JPanel();
		panel_15.setEnabled(false);
		panel_15.setOpaque(false);
		panel_15.setRequestFocusEnabled(false);
		FlowLayout flowLayout_1 = (FlowLayout) panel_15.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel_1.add(panel_15, BorderLayout.SOUTH);

		// 删除按钮响应，删除所选行
		btnNewButton_delete = new JButton("\u5220  \u9664");
		btnNewButton_delete.setSelected(true);
		btnNewButton_delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				for (int i = 0; i < riskcontrolLec.this.tb_lecd.getRowCount(); i++) {
					try {
						boolean t = ((Boolean) riskcontrolLec.this.tb_lecd.getValueAt(i, 0)).booleanValue();
						if (t) {
							DefaultTableModel model = (DefaultTableModel) riskcontrolLec.this.tb_lecd.getModel();
							model.removeRow(i);

							i--;
						}
					} catch (Exception localException) {
					}
				}
				for (int k = 0; k < riskcontrolLec.this.tb_lecd.getRowCount(); k++) {
					riskcontrolLec.this.tb_lecd.setValueAt(Integer.valueOf(k + 1), k, 1);
				}
				riskcontrolLec.this.tb_lecd.revalidate();
			}
		});
		btnNewButton_delete.setHorizontalAlignment(SwingConstants.LEADING);
		panel_15.add(btnNewButton_delete);

		// 保存按钮响应，将数据保存到数据库中
		btnNewButton_save = new JButton("\u4FDD  \u5B58");
		btnNewButton_save.setEnabled(false);

		btnNewButton_save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!saveFlag) {
					return;
				}

				// 先获取界面上的数据信息
				String projectName = textField_name.getText();
				
				String projectTime = textField_time.getText();
				
				//ren: 从控件获取日期
//				int year = datapanel_projectDate.getModel().getYear()-1900;
//				int month = datapanel_projectDate.getModel().getMonth() ;
//				int day = datapanel_projectDate.getModel().getDay();
//				String projectTime = String.format("'%d'-'%d'-'%d'", year,month,day);
				
				//自动获取日期
				//Calendar ca = Calendar.getInstance();//自动获取当前日期
				//String projectTime = String.format("%s-%s-%s", ca.get(Calendar.YEAR),ca.get(Calendar.MONTH)+1,ca.get(Calendar.DATE));
				
				
				String people = textField_people.getText();
				// DefaultTableModel model = (DefaultTableModel)
				// tb_lecd.getModel();
				if (projectName.length() == 0) {
					JOptionPane.showMessageDialog(panel_1, "请输入议题名称！");
					return;
				} 
//				else if (projectTime.length() == 0) {
//					JOptionPane.showMessageDialog(panel_1, "请输入时间！");
//					return;
//				} 
				else if (people.length() == 0) {
					JOptionPane.showMessageDialog(panel_1, "请输入参与人！");
					return;
				}

				JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");

				// int lastRow = tb_lecd.getRowCount();
				for (int i = 0; i < tb_lecd.getRowCount(); i++) {
					String riskName = (String) tb_lecd.getValueAt(i, 2);
					String riskLevel = (String) tb_lecd.getValueAt(i, 7);

					float l = (float) tb_lecd.getValueAt(i, 3);
					// Float l = Float.parseFloat(l1.trim());

					float e2 = (float) tb_lecd.getValueAt(i, 4);
					// Float e3 = Float.parseFloat(e2.trim());

					float c = (float) tb_lecd.getValueAt(i, 5);
					// Float c = Float.parseFloat(c1.trim());

					String d1 = (String) tb_lecd.getValueAt(i, 6);
					Float d = Float.parseFloat(d1.trim());

					// ren: insert into 改为 insert
					String lecdsql = "insert lec_table values(" + "'" + projectName + "'," + "'" + projectTime + "',"
							+ "'" + people + "'," + "'" + riskName + "'," + l + "," + e2 + "," + c + "," + d + "," + "'"
							+ riskLevel + "') ";

					// ren: 插入不成功，提示并return。
					if (!mysql_.insert(lecdsql)) {
						JOptionPane.showMessageDialog(panel_1, "没有保存成功，请检查数据库配置！");
						mysql_.close();
						return;
					}
				}

				JOptionPane.showMessageDialog(panel_1, "保存成功！");

				mysql_.close();

				btnNewButton_save.setEnabled(false);
				saveFlag = false;

			}
		});
		btnNewButton_save.setHorizontalAlignment(SwingConstants.LEADING);
		panel_15.add(btnNewButton_save);

		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		// tcr.setHorizontalAlignment(JLabel.CENTER);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);

	}

}
