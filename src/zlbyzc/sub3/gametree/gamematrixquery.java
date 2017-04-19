package zlbyzc.sub3.gametree;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.List;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;

import zlbyzc.BasicRibbon;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;
import zlbyzc.sub3.gametree.GameMatrix.matrix_compute_table;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gamematrixquery extends sub3inFrame {
	private JTextField tfGameName;
	private static JTextField tf_Basic_area;
	private static JTextField tf_Basic_discusser;
	private static JTextField tf_Basic_time;
	private JTable tb_Gametree_result;
	private JTable tb_Gametree_profit;
	public static java.sql.Connection connect;
	public static int game_tree_id = 0;
	public int game_type=0;
	public int is_complete = 0;
	public int is_result = 0;
	public int totalpage = 0;
	public int currentpage = 0;
	public int numpage = 5;
	public String query_str=""; 
	
	public JComboBox cb_Basic_gametype;
	private JTextField tf_actor_1;
	private JTextField tf_actor_2;
	private JTable matrix_table;
	public class matrix_compute_table
	{
		int profit_set_id;
		int actor_1_value;
		int actor_1_id;
		int actor_1_strategy_id;
		float actor_1_probability;
		int actor_2_value;
		int actor_2_id;
		int actor_2_strategy_id;
		float actor_2_probability;
        boolean actor_1_max=false;
        boolean actor_2_max=false;
        boolean is_not_null=false;
	};
	public DefaultTableModel result_model;
	public DefaultTableModel group_model;
	public DefaultTableModel gamematrix_query_model;
	private JLabel lb_end_time;
	private JTextField tf_end_time;
	private JTable tb_gamematrix_query;
	private BasicRibbon BR;
	private JButton btnDelete;
	private JButton btnPrepage;
	private JButton btnNextpage;
	/**
	 * Create the panel.
	 */
	public gamematrixquery(BasicRibbon _br) {
		super("完全信息静态博弈查询",true,true,true,true);
		BR = _br;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 100, 100, 100, 100, 100, 100, 100,10};
		gridBagLayout.rowHeights = new int[]{10, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,25, 25, 25,25,25,25,25};
		
		JPanel allcontent = new JPanel(gridBagLayout);
		JScrollPane scrollpage = new JScrollPane(allcontent);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scrollpage,BorderLayout.CENTER) ;
		
		JLabel lbGameName = new JLabel("\u535A\u5F08\u95EE\u9898\u540D\u79F0:");
		lbGameName.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lbGameName = new GridBagConstraints();
		gbc_lbGameName.anchor = GridBagConstraints.WEST;
		gbc_lbGameName.fill = GridBagConstraints.VERTICAL;
		gbc_lbGameName .insets = new Insets(0, 0, 5, 5);
		gbc_lbGameName .gridx = 0;
		gbc_lbGameName .gridy = 0;
		allcontent.add(lbGameName, gbc_lbGameName );
		
		tfGameName = new JTextField();
		tfGameName.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_tfGameName = new GridBagConstraints();
		gbc_tfGameName.insets = new Insets(0, 0, 5, 5);
		gbc_tfGameName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGameName.gridx = 1;
		gbc_tfGameName.gridy = 0;
		allcontent.add(tfGameName, gbc_tfGameName);
		tfGameName.setColumns(10);
		
		
		JLabel lb_Basic_gametype = new JLabel("\u535A\u5F08\u6811\u7C7B\u578B\uFF1A");
		lb_Basic_gametype.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_Basic_gametype = new GridBagConstraints();
		gbc_Basic_gametype.anchor = GridBagConstraints.EAST;
		gbc_Basic_gametype.insets = new Insets(0, 0, 5, 5);
		gbc_Basic_gametype.gridx = 2;
		gbc_Basic_gametype.gridy = 0;
		allcontent.add(lb_Basic_gametype, gbc_Basic_gametype);
		
		String[] gametype={"完全信息静态"};
		cb_Basic_gametype = new JComboBox(gametype);
		cb_Basic_gametype.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_cb_Basic_gametype = new GridBagConstraints();
		gbc_cb_Basic_gametype.insets = new Insets(0, 0, 5, 5);
		gbc_cb_Basic_gametype.fill = GridBagConstraints.HORIZONTAL;
		gbc_cb_Basic_gametype.gridx = 3;
		gbc_cb_Basic_gametype.gridy = 0;
		gbc_cb_Basic_gametype.gridwidth=2;		
		allcontent.add(cb_Basic_gametype, gbc_cb_Basic_gametype);
		
		JLabel lb_Basic_area = new JLabel("\u53D1\u751F\u5730\u70B9\uFF1A");
		lb_Basic_area.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_Basic_area = new GridBagConstraints();
		gbc_lb_Basic_area.anchor = GridBagConstraints.EAST;
		gbc_lb_Basic_area.insets = new Insets(0, 0, 5, 5);
		gbc_lb_Basic_area.gridx = 5;
		gbc_lb_Basic_area.gridy = 0;
		allcontent.add(lb_Basic_area, gbc_lb_Basic_area);
		
		tf_Basic_area = new JTextField();
		tf_Basic_area.setFont(new Font("宋体", Font.PLAIN, 14));
		tf_Basic_area.setColumns(10);
		GridBagConstraints gbc_tf_Basic_area = new GridBagConstraints();
		gbc_tf_Basic_area.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Basic_area.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Basic_area.gridx = 6;
		gbc_tf_Basic_area.gridy = 0;
		allcontent.add(tf_Basic_area, gbc_tf_Basic_area);
		
		JLabel lb_Basic_discusser = new JLabel("\u7814\u8BA8\u4E3B\u4F53\uFF1A");
		lb_Basic_discusser.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_Basic_discusser = new GridBagConstraints();
		gbc_lb_Basic_discusser.anchor = GridBagConstraints.WEST;
		gbc_lb_Basic_discusser.insets = new Insets(0, 0, 5, 5);
		gbc_lb_Basic_discusser.gridx = 0;
		gbc_lb_Basic_discusser.gridy = 1;
		allcontent.add(lb_Basic_discusser, gbc_lb_Basic_discusser);
		
		tf_Basic_discusser = new JTextField();
		tf_Basic_discusser.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_tf_Basic_discusser = new GridBagConstraints();
		gbc_tf_Basic_discusser.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Basic_discusser.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Basic_discusser.gridx = 1;
		gbc_tf_Basic_discusser.gridy = 1;
		allcontent.add(tf_Basic_discusser, gbc_tf_Basic_discusser);
		tf_Basic_discusser.setColumns(10);
		
		JLabel lb_Basic_time = new JLabel("开始时间：");
		lb_Basic_time.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_Basic_time = new GridBagConstraints();
		gbc_lb_Basic_time.anchor = GridBagConstraints.EAST;
		gbc_lb_Basic_time.insets = new Insets(0, 0, 5, 5);
		gbc_lb_Basic_time.gridx = 2;
		gbc_lb_Basic_time.gridy = 1;
		allcontent.add(lb_Basic_time, gbc_lb_Basic_time);
		
		tf_Basic_time = new JTextField();
		tf_Basic_time.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_tf_Basic_time = new GridBagConstraints();
		gbc_tf_Basic_time.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Basic_time.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Basic_time.gridx = 3;
		gbc_tf_Basic_time.gridy = 1;
		allcontent.add(tf_Basic_time, gbc_tf_Basic_time);
		tf_Basic_time.setColumns(10);
		
		lb_end_time = new JLabel("结束时间：");
		lb_end_time.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_end_time = new GridBagConstraints();
		gbc_lb_end_time.anchor = GridBagConstraints.EAST;
		gbc_lb_end_time.insets = new Insets(0, 0, 5, 5);
		gbc_lb_end_time.gridx = 5;
		gbc_lb_end_time.gridy = 1;
		allcontent.add(lb_end_time, gbc_lb_end_time);
		
		tf_end_time = new JTextField();
		tf_end_time.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_tf_end_time = new GridBagConstraints();
		gbc_tf_end_time.insets = new Insets(0, 0, 5, 5);
		gbc_tf_end_time.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_end_time.gridx = 6;
		gbc_tf_end_time.gridy = 1;
		allcontent.add(tf_end_time, gbc_tf_end_time);
		tf_end_time.setColumns(10);
		
		JButton btnGameNameSearch = new JButton("\u67E5\u8BE2");
		btnGameNameSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnGameNameSearch.setEnabled(false);				
				tf_actor_1.setText("");
				tf_actor_2.setText("");
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
					{
						String celltext="";
						matrix_table.setValueAt (celltext, i, j);
					}
				}
				gamematrix_query_model.setRowCount(0);
				result_model.setRowCount(0);
				group_model.setRowCount(0);
				btnPrepage.setEnabled(false);
				btnNextpage.setEnabled(false);
				btnDelete.setEnabled(false);
				totalpage =0;
				currentpage=0;
				
				String query_name =  tfGameName.getText();
				try 
				{
					Statement stmt= connect.createStatement();
					String sql = "select count(*)";
					query_str=" from tb_game_tree where game_tree_type =1";
					if(!tfGameName.getText().trim().equals(""))
					{
						query_str = query_str+" and game_tree_name like '%"+tfGameName.getText().trim()+"%'";
					}
					if(!tf_Basic_area.getText().trim().equals(""))
					{
						query_str = query_str+" and area like '%"+tf_Basic_area.getText().trim()+"%'";
					}
					if(!tf_Basic_discusser.getText().trim().equals(""))
					{
						query_str = query_str+" and person like '%"+tf_Basic_discusser.getText().trim()+"%'";
					}		
					if(!tf_Basic_time.getText().trim().equals(""))
					{
						query_str = query_str+" and event_time >='"+tf_Basic_time.getText().trim()+"'";
					}
					if(!tf_end_time.getText().trim().equals(""))
					{
						query_str = query_str+" and event_time <='"+tf_end_time.getText().trim()+"'";
					}
					
					sql = sql + query_str;
					ResultSet rs = stmt.executeQuery(sql);
					int totalnumber =0;
					if (rs.next()) 
				    {
						totalnumber = rs.getInt(1); 
				    }
					totalpage = totalnumber/numpage;
					int remain = totalnumber%numpage;
					if(remain!=0)
						totalpage++;
					
					showcurrentpage();
					if(currentpage+1 < totalpage)
						btnNextpage.setEnabled(true);		
				} catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
				
				btnGameNameSearch.setEnabled(true);
			}
		});
		btnGameNameSearch.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btnGameNameSearch = new GridBagConstraints();
		gbc_btnGameNameSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnGameNameSearch.gridx = 0;
		gbc_btnGameNameSearch.gridy = 2;
		allcontent.add(btnGameNameSearch, gbc_btnGameNameSearch);
		
		String[] col_gamematrix_query = {"博弈问题编号","博弈问题名称","博弈问题类型","发生地点","研讨主体","发生时间"};
		gamematrix_query_model   =   new   DefaultTableModel(new Object[][] {},col_gamematrix_query);		
		tb_gamematrix_query = new JTable(gamematrix_query_model);
		tb_gamematrix_query.setPreferredScrollableViewportSize(new Dimension(200,25));//ע����У��������ܿ�������
		GridBagConstraints gbc_tb_gamematrix_query = new GridBagConstraints();
		gbc_tb_gamematrix_query.gridheight = 5;
		gbc_tb_gamematrix_query.insets = new Insets(0, 0, 5, 5);
		gbc_tb_gamematrix_query.fill = GridBagConstraints.BOTH;
		gbc_tb_gamematrix_query.gridwidth =8;
		gbc_tb_gamematrix_query.gridx = 0;
		gbc_tb_gamematrix_query.gridy = 3;
		JScrollPane sp_gamematrix_query = new JScrollPane(tb_gamematrix_query);
		tb_gamematrix_query.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String gameid =(String) tb_gamematrix_query.getValueAt(tb_gamematrix_query.getSelectedRow(), 0);
				game_tree_id = Integer.valueOf(gameid);
				if(game_tree_id >0)
					btnDelete.setEnabled(true);
				tf_actor_1.setText("");
				tf_actor_2.setText("");
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
					{
						String celltext="";
						matrix_table.setValueAt (celltext, i, j);
					}
				}
				try 
				{
					Statement stmt= connect.createStatement();
					String sql = "select * from tb_actor where game_tree_id="+game_tree_id;
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next())//��ʾ��������Ϣ
					{
						String actor_name = rs.getString("actor_name");
						int actor_detail = rs.getInt("actor_detail");
						if(actor_detail == 1)
						{
							tf_actor_1.setText(actor_name);
						}
						else if(actor_detail == 2)
						{
							tf_actor_2.setText(actor_name);
						}							
					}
					sql = "select * from vi_matrix_strategy where game_tree_id="+game_tree_id;
					ResultSet rs_matrix_strategy = stmt.executeQuery(sql);
					while (rs_matrix_strategy.next())//��ʾ��������Ϣ
					{
						int row = rs_matrix_strategy.getInt("matrix_row");
						int column = rs_matrix_strategy.getInt("matrix_column");
						String celltext;
						celltext = rs_matrix_strategy.getString("strategy_name");
						matrix_table.setValueAt (celltext, row, column);
					}
					
					sql = "select * from vi_matrix_profit where game_tree_id="+game_tree_id;
					ResultSet rs_matrix_profit = stmt.executeQuery(sql);
					while (rs_matrix_profit.next())//��ʾ��������Ϣ
					{
						int row = rs_matrix_profit.getInt("matrix_row");
						int column = rs_matrix_profit.getInt("matrix_column");
						String celltext;
						celltext = rs_matrix_profit.getString("profit_set_name");
						matrix_table.setValueAt (celltext, row, column);
					}
					
					result_model.setRowCount(0);
					group_model.setRowCount(0);
					sql = "select * from tb_profit_set where pure_probability>0  and game_tree_id=" + game_tree_id;
					rs = stmt.executeQuery(sql);
					String profit_set_name = null;
					String probability = null;
					while(rs.next())
					{
						profit_set_name = rs.getString("profit_set_name");
						probability = rs.getString("pure_probability");
						String[] result_set= new String[3];
						result_set[0] = profit_set_name;
						result_set[1] = probability;
						result_set[2] = "纯策略";
						result_model.addRow(result_set);
					}
					
					String sql_mix = "select * from tb_profit_set where mix_probability>0  and game_tree_id=" + game_tree_id;
					ResultSet rs_mix = stmt.executeQuery(sql_mix);
					String mix_profit_set_name = null;
					String mix_probability = null;
					while(rs_mix.next())
					{
						mix_profit_set_name = rs_mix.getString("profit_set_name");
						mix_probability = rs_mix.getString("mix_probability");
						String[] result_set= new String[3];
						result_set[0] = mix_profit_set_name;
						result_set[1] = mix_probability;
						result_set[2] = "混合策略";
						result_model.addRow(result_set);
					}
										
					sql = "select * from vi_result_group where game_tree_id=" + game_tree_id;
					ResultSet rs_group = stmt.executeQuery(sql);
					while(rs_group.next())
					{
						String[] result_group= new String[4];
						result_group[0] = rs_group.getString("actor_name");
						result_group[1] = rs_group.getString("strategy_name");
						result_group[2] = rs_group.getString("probability");
						int t= rs_group.getInt("nash_equilibrium_type");
						if(t == 1)
							result_group[3] = "纯策略";
						else
							result_group[3] = "混合策略";
						group_model.addRow(result_group);
					}

				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		allcontent.add(sp_gamematrix_query, gbc_tb_gamematrix_query);
		
		btnDelete = new JButton("删除当前博弈问题");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					gamematrix_query_model.setRowCount(0);
					tf_actor_1.setText("");
					tf_actor_2.setText("");
					for(int i=0;i<3;i++)
					{
						for(int j=0;j<3;j++)
						{
							String celltext="";
							matrix_table.setValueAt (celltext, i, j);
						}
					}
					Statement stmt;
					stmt = connect.createStatement();
					String sql = "delete from tb_game_tree where game_tree_id=" + game_tree_id;
				    stmt.executeUpdate(sql); 
				    sql = "delete from tb_actor where game_tree_id=" + game_tree_id;
				    stmt.executeUpdate(sql);
				    sql = "delete from tb_strategy where game_tree_id=" + game_tree_id;
				    stmt.executeUpdate(sql);
				    sql = "select * from tb_profit_set where game_tree_id=" + game_tree_id;
					ResultSet rs_profit = stmt.executeQuery(sql);
					while(rs_profit.next())
					{
						Statement stmt_p= connect.createStatement();
						int profit_set_id = rs_profit.getInt("profit_set_id");
						String sql_profit = "delete from tb_profit_group where profit_set_id=" + profit_set_id;
						stmt_p.executeUpdate(sql_profit);
						sql_profit = "delete from tb_profit_set where profit_set_id=" + profit_set_id;
						stmt_p.executeUpdate(sql_profit);
					}
					sql = "delete from tb_game_matrix where game_tree_id=" + game_tree_id;
				    stmt.executeUpdate(sql);
					sql = "delete from tb_result where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql);
					result_model.setRowCount(0);
					group_model.setRowCount(0);
					
				    try {
				    	String num_sql = "select count(*)" + query_str;
						ResultSet rs = stmt.executeQuery(num_sql);
						int totalnumber =0;
						if (rs.next()) 
					    {
							totalnumber = rs.getInt(1); 
					    }
						totalpage = totalnumber/numpage;
						int remain = totalnumber%numpage;
						if(remain!=0)
							totalpage++;
						if((currentpage+1) >= totalpage)
						{
							btnNextpage.setEnabled(false);
						}												
						
						showcurrentpage();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.anchor = GridBagConstraints.WEST;
		gbc_btnDelete.insets = new Insets(0, 0, 5, 5);
		gbc_btnDelete.gridx = 0;
		gbc_btnDelete.gridy = 8;
		gbc_btnDelete.gridwidth = 2;
		allcontent.add(btnDelete, gbc_btnDelete);
		
		btnPrepage = new JButton("上一页");
		btnPrepage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamematrix_query_model.setRowCount(0);
				currentpage--;
				if(currentpage <= 0)
					btnPrepage.setEnabled(false);
				btnNextpage.setEnabled(true);
				showcurrentpage();
			}
		});
		btnPrepage.setEnabled(false);
		btnPrepage.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btnPrepage = new GridBagConstraints();
		gbc_btnPrepage.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrepage.gridx = 6;
		gbc_btnPrepage.gridy = 8;
		allcontent.add(btnPrepage, gbc_btnPrepage);
		
		btnNextpage = new JButton("下一页");
		btnNextpage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamematrix_query_model.setRowCount(0);
				currentpage++;
				if((currentpage+1) >= totalpage)
					btnNextpage.setEnabled(false);
				btnPrepage.setEnabled(true);
				showcurrentpage();				
			}
		});
		btnNextpage.setEnabled(false);
		btnNextpage.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btnNextpage = new GridBagConstraints();
		gbc_btnNextpage.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextpage.gridx = 7;
		gbc_btnNextpage.gridy = 8;
		allcontent.add(btnNextpage, gbc_btnNextpage);
		
		JPanel matrix_panel = new JPanel();
		matrix_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_matrix_panel = new GridBagConstraints();
		gbc_matrix_panel.insets = new Insets(0, 0, 0, 5);
		gbc_matrix_panel.fill = GridBagConstraints.BOTH;
		gbc_matrix_panel.gridx = 0;
		gbc_matrix_panel.gridy = 9;
		gbc_matrix_panel.gridwidth=5;
		gbc_matrix_panel.gridheight=10;
		allcontent.add(matrix_panel, gbc_matrix_panel);
		GridBagLayout gbl_matrix_panel = new GridBagLayout();
		gbl_matrix_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0};
		gbl_matrix_panel.columnWidths = new int[]{100, 100, 100, 100, 100};
		gbl_matrix_panel.rowHeights = new int[]{25, 25, 25, 25, 25, 25, 25, 25, 25, 25};
		matrix_panel.setLayout(gbl_matrix_panel);
		
		tf_actor_2 = new JTextField();
		tf_actor_2.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_tf_actor_2 = new GridBagConstraints();
		gbc_tf_actor_2.insets = new Insets(0, 0, 5, 5);
		gbc_tf_actor_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_actor_2.gridx = 2;
		gbc_tf_actor_2.gridy = 0;
		matrix_panel.add(tf_actor_2, gbc_tf_actor_2);
		tf_actor_2.setColumns(10);
		
		Object[][] values={
				{"","","",""},
				{"","","",""},
                {"","","",""},
                {"","","",""}
        };
		String[] value={"","","",""};	
		matrix_table = new JTable(values,value);
		matrix_table.setFont(new Font("宋体", Font.PLAIN, 12));
		matrix_table.setBorder(new LineBorder(new Color(0, 0, 0)));
		matrix_table.setRowHeight(27);
		GridBagConstraints gbc_matrix_table = new GridBagConstraints();
		gbc_matrix_table.insets = new Insets(0, 0, 5, 0);
		gbc_matrix_table.fill = GridBagConstraints.BOTH;
		gbc_matrix_table.gridx = 1;
		gbc_matrix_table.gridy = 1;
		gbc_matrix_table.gridwidth=4;
		gbc_matrix_table.gridheight=4;
		matrix_panel.add(matrix_table, gbc_matrix_table);
		
		tf_actor_1 = new JTextField();
		tf_actor_1.setFont(new Font("宋体", Font.PLAIN, 12));
		GridBagConstraints gbc_tf_actor_1 = new GridBagConstraints();
		gbc_tf_actor_1.insets = new Insets(0, 0, 5, 5);
		gbc_tf_actor_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_actor_1.gridx = 0;
		gbc_tf_actor_1.gridy = 2;
		matrix_panel.add(tf_actor_1, gbc_tf_actor_1);
		tf_actor_1.setColumns(10);
		
		String[] col_Gametree_result = {"收益组名称","概率","纳什均衡类型"}; 
		result_model   =   new   DefaultTableModel(new Object[][] {},col_Gametree_result);		
		tb_Gametree_result = new JTable(result_model);
		tb_Gametree_result.setPreferredScrollableViewportSize(new Dimension(200,25));//ע����У��������ܿ�������
		GridBagConstraints gbc_tb_Gametree_result = new GridBagConstraints();
		gbc_tb_Gametree_result.gridheight = 5;
		gbc_tb_Gametree_result.insets = new Insets(0, 0, 5, 5);
		gbc_tb_Gametree_result.fill = GridBagConstraints.BOTH;
		gbc_tb_Gametree_result.gridwidth =3;
		gbc_tb_Gametree_result.gridx = 5;
		gbc_tb_Gametree_result.gridy = 9;
		JScrollPane sp_Gametree_result = new JScrollPane(tb_Gametree_result);
		allcontent.add(sp_Gametree_result, gbc_tb_Gametree_result);
		
		String[] col_Gametree_profit = {"博弈方名称","策略名称","概率","纳什均衡类型"};
		group_model   =   new   DefaultTableModel(new Object[][] {},col_Gametree_profit);	
		tb_Gametree_profit = new JTable(group_model);
		tb_Gametree_profit.setPreferredScrollableViewportSize(new Dimension(200,25));//ע����У��������ܿ�������
		GridBagConstraints gbc_tb_Gametree_profit = new GridBagConstraints();
		gbc_tb_Gametree_profit.insets = new Insets(0, 0, 0, 5);
		gbc_tb_Gametree_profit.gridheight = 5;
		gbc_tb_Gametree_profit.fill = GridBagConstraints.BOTH;
		gbc_tb_Gametree_profit.gridwidth =3;
		gbc_tb_Gametree_profit.gridx = 5;
		gbc_tb_Gametree_profit.gridy = 14;
		JScrollPane sp_Gametree_profit = new JScrollPane(tb_Gametree_profit);
		allcontent.add(sp_Gametree_profit, gbc_tb_Gametree_profit);
		
		
	}
	
	public void showcurrentpage(){
		try {
			Statement stmt = connect.createStatement();
			String current_sql = "select * "+ query_str+" order by game_tree_id desc limit "+numpage+" offset "+currentpage*numpage;
			ResultSet current_rs = stmt.executeQuery(current_sql);
			while (current_rs.next())//��ʾ��������Ϣ
			{

				String game_id = current_rs.getString("game_tree_id");
				String game_tree_name = current_rs.getString("game_tree_name");
				String area = current_rs.getString("area");
				String person = current_rs.getString("person");
				String game_time = current_rs.getString("event_time");
				String[] result_set= new String[6];
				result_set[0] = game_id;
				result_set[1] = game_tree_name;
				result_set[2] = "完全信息静态博弈";
				result_set[3] = area;
				result_set[4] = person;
				result_set[5] = game_time;
				gamematrix_query_model.addRow(result_set);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
	public void connectdatabase(){
		try {  
		      Class.forName("com.mysql.jdbc.Driver");     //����MYSQL JDBC��������     
		      //Class.forName("org.gjt.mm.mysql.Driver");  
		     System.out.println("Success loading Mysql Driver!");  
		    }  
		    catch (Exception e) {  
		      System.out.print("Error loading Mysql Driver!");  
		      e.printStackTrace();  
		    }  
		    try {  
		    	connect = DriverManager.getConnection(BR.setting.getConnURL("strategy_game"));
		          //"jdbc:mysql://localhost/strategy_game","root","wipm");  
		           //����URLΪ   jdbc:mysql//��������ַ/���ݿ���  �������2�������ֱ��ǵ�½�û���������  
		  
		      System.out.println("Success connect Mysql server!");  
		    }  
		    catch (Exception e) {  
		      System.out.print("get data error!");  
		      e.printStackTrace();  
		    }  
		
	}
	/*public  static  void  main(String[]  args) {
		gamematrixquery  strategy_game_panel = new gamematrixquery();
		
		JFrame frame = new JFrame(); 
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
		 strategy_game_panel.setOpaque(true); 
		 frame.setSize(new Dimension(900, 640)); 
		 frame.setContentPane(strategy_game_panel); 
		 frame.setVisible(true); 
		 
		 connectdatabase();

	}*/
}
