package zlbyzc.sub3.gametree;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.List;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.liukan.mgraph.mgraphxEx;
import org.liukan.mgraph.util.dbIO;

import com.mysql.jdbc.Connection;

import zlbyzc.sub3.gametree.GameMatrix.matrix_compute_table;
import zlbyzc.sub3.gametree.GameTree.tree_node;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.SwingConstants;

import zlbyzc.BasicRibbon;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;

public class gametreequery extends sub3inFrame {
	public static java.sql.Connection connect;
	public int game_tree_id = 0;
	public int gid = 0;
	public int game_type=0;
	public int is_complete = 0;
	public int is_result = 0;
	public int totalpage = 0;
	public int currentpage = 0;
	public int numpage = 5;
	public String query_str=""; 
	
	private JTextField tfGameName;
	public JComboBox cb_Basic_gametype;
	private JTextField tf_Basic_area;
	private JTextField tf_Basic_discusser;
	private JTextField tf_Basic_time;
	private JTable tb_Gametree_result;
	private JTable tb_Gametree_profit;	
    public DefaultTableModel result_model;
	public DefaultTableModel group_model;
	public DefaultTableModel gametree_query_model;
	private JLabel lb_end_time;
	private JTextField tf_end_time;
	private JTable tb_gametree_query;
	private BasicRibbon BR;
	private static mgraphxEx panel;
	private JButton btnDelete;
	private JButton btnPrepage;
	private JButton btnNextpage;
	
	public class tree_node
	{
		int game_tree_node_id =0;
		String game_tree_node_name;
		int node_layer=0;
		int node_position=0;
		int node_type=0;
		int father_node_id=0;
		int actor_id=0;
		int strategy_id=0;
		float probability=0;
        int profit_set_id=0;
        float compute_value=0;
        String probability_detail="";
	};
	/**
	 * Create the panel.
	 */
	public gametreequery(BasicRibbon _br) {
		super("其它博弈查询",true,true,true,true);		
		GridBagLayout gridBagLayout = new GridBagLayout();
		BR = _br;
		gridBagLayout.columnWidths = new int[]{10, 100, 100, 100, 100, 100, 100, 100,10};
		gridBagLayout.rowHeights = new int[]{10, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,25, 25, 25, 25, 25, 25,25, 25, 25,25,25,25};
		      
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
		
		String[] gametype={"全部","完全信息动态","不完全信息静态","不完全信息动态"};
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
		
		JButton btnGameNameSearch = new JButton("\u67E5\u8BE2");
		btnGameNameSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnGameNameSearch.setEnabled(false);
				game_tree_id =0;
				gid=0;
				panel.gpanel.clearAllElements();
				result_model.setRowCount(0);
				group_model.setRowCount(0);
				gametree_query_model.setRowCount(0);
				btnPrepage.setEnabled(false);
				btnNextpage.setEnabled(false);
				btnDelete.setEnabled(false);
				totalpage =0;
				currentpage=0;
				
				String query_name =  tfGameName.getText().trim();
				try 
				{
					Statement stmt= connect.createStatement();
					String gametype = cb_Basic_gametype.getSelectedItem().toString();
					String sql="select count(*)";
					query_str="";
					if(gametype == "全部")
						query_str = " from tb_game_tree where game_tree_type >1";
					else if(gametype == "完全信息动态")
						query_str = " from tb_game_tree where game_tree_type =2";
					else if(gametype == "不完全信息静态")
						query_str = " from tb_game_tree where game_tree_type =3";
					else 
						query_str = " from tb_game_tree where game_tree_type =4";
										
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
					// TODO Auto-generatered catch block
					e.printStackTrace();
				}
									
				btnGameNameSearch.setEnabled(true);
			}
		});
		
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
		
		btnGameNameSearch.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btnGameNameSearch = new GridBagConstraints();
		gbc_btnGameNameSearch.anchor = GridBagConstraints.WEST;
		gbc_btnGameNameSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnGameNameSearch.gridx = 0;
		gbc_btnGameNameSearch.gridy = 2;
		allcontent.add(btnGameNameSearch, gbc_btnGameNameSearch);
		
		
		
		 
		String[] col_Gametree_result = {"收益组名称","概率","纳什均衡类型"}; 
		result_model   =   new   DefaultTableModel(new Object[][] {},col_Gametree_result);		
		
		String[] col_gametree_query = {"博弈问题编号","图形编号","博弈问题名称","博弈问题类型","发生地点","研讨主体","发生时间"};
		gametree_query_model   =   new   DefaultTableModel(new Object[][] {},col_gametree_query);		
		tb_gametree_query = new JTable(gametree_query_model);
		tb_gametree_query.setPreferredScrollableViewportSize(new Dimension(200,25));//ע����У��������ܿ�������
		GridBagConstraints gbc_tb_gametree_query = new GridBagConstraints();
		gbc_tb_gametree_query.gridheight = 5;
		gbc_tb_gametree_query.insets = new Insets(0, 0, 5, 5);
		gbc_tb_gametree_query.fill = GridBagConstraints.BOTH;
		gbc_tb_gametree_query.gridwidth =8;
		gbc_tb_gametree_query.gridx = 0;
		gbc_tb_gametree_query.gridy = 3;
		JScrollPane sp_gametree_query = new JScrollPane(tb_gametree_query);
		tb_gametree_query.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String game_id =(String) tb_gametree_query.getValueAt(tb_gametree_query.getSelectedRow(), 0);
				game_tree_id = Integer.valueOf(game_id);
				if(game_tree_id >0)
					btnDelete.setEnabled(true);
				String g_id =(String) tb_gametree_query.getValueAt(tb_gametree_query.getSelectedRow(), 1);
				gid = Integer.valueOf(g_id);
				try 
				{
					Statement stmt= connect.createStatement();
										
					result_model.setRowCount(0);
					group_model.setRowCount(0);

					dbIO dbio=new dbIO(BR.setting.getDriver(),BR.setting.getConnURL("strategy_game"));	 
					panel.gpanel.readGfromDB(dbio,gid);
					
					String sql = "select * from tb_profit_set where pure_probability>0  and game_tree_id=" + game_tree_id;
					ResultSet rs = stmt.executeQuery(sql);
					String profit_set_name = null;
					String probability = null;
					while(rs.next())
					{
						profit_set_name = rs.getString("profit_set_name");
						probability = rs.getString("pure_probability");
						String[] result_set= new String[3];
						result_set[0] = profit_set_name;
						result_set[1] = probability;
						float p =  Float.parseFloat(probability);
						if(p == 1)
							result_set[2] = "子博弈精炼";
						else
							result_set[2] = "贝叶斯";
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
						int nash_equilibrium_type= rs_group.getInt("nash_equilibrium_type");
						if(nash_equilibrium_type == 3)
							result_group[3] = "子博弈精炼";
						else
							result_group[3] = "贝叶斯";
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
		allcontent.add(sp_gametree_query, gbc_tb_gametree_query);
		
		btnDelete = new JButton("删除当前博弈问题");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					gametree_query_model.setRowCount(0);
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
					sql = "delete from tb_game_tree_node where game_tree_id=" + game_tree_id;
				    stmt.executeUpdate(sql);
					sql = "delete from tb_result where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql);
					sql = "delete from graphs where id=" + gid;
					stmt.executeUpdate(sql);
					sql = "delete from nodes where gid=" + gid;
					stmt.executeUpdate(sql);
					sql = "delete from edges where gid=" + gid;
					stmt.executeUpdate(sql);
					panel.gpanel.clearAllElements();
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
				gametree_query_model.setRowCount(0);
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
		gbc_btnPrepage.anchor = GridBagConstraints.NORTH;
		gbc_btnPrepage.insets = new Insets(0, 0, 5, 5);
		gbc_btnPrepage.gridx = 6;
		gbc_btnPrepage.gridy = 8;
		allcontent.add(btnPrepage, gbc_btnPrepage);
		
		btnNextpage = new JButton("下一页");
		btnNextpage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gametree_query_model.setRowCount(0);
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
		gbc_btnNextpage.anchor = GridBagConstraints.NORTH;
		gbc_btnNextpage.insets = new Insets(0, 0, 5, 5);
		gbc_btnNextpage.gridx = 7;
		gbc_btnNextpage.gridy = 8;
		allcontent.add(btnNextpage, gbc_btnNextpage);
		
		panel =  new mgraphxEx(false,16,18,true);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 9;
		gbc_panel.gridwidth = 5;
		gbc_panel.gridheight = 15;
		allcontent.add(panel, gbc_panel);
		
		tb_Gametree_result = new JTable(result_model);
		tb_Gametree_result.setPreferredScrollableViewportSize(new Dimension(200,25));//ע����У��������ܿ�������
		GridBagConstraints gbc_tb_Gametree_result = new GridBagConstraints();
		gbc_tb_Gametree_result.gridheight = 7;
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
		tb_Gametree_profit.getColumnModel().getColumn(2).setPreferredWidth(20);
		GridBagConstraints gbc_tb_Gametree_profit = new GridBagConstraints();
		gbc_tb_Gametree_profit.insets = new Insets(0, 0, 0, 5);
		gbc_tb_Gametree_profit.gridheight = 8;
		gbc_tb_Gametree_profit.fill = GridBagConstraints.BOTH;
		gbc_tb_Gametree_profit.gridwidth =3;
		gbc_tb_Gametree_profit.gridx = 5;
		gbc_tb_Gametree_profit.gridy = 16;
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
				String g_id = current_rs.getString("gid");
				String game_tree_name = current_rs.getString("game_tree_name");
				int game_tree_type = current_rs.getInt("game_tree_type");
				String area = current_rs.getString("area");
				String person = current_rs.getString("person");
				String game_time = current_rs.getString("event_time");
				String[] result_set= new String[7];
				result_set[0] = game_id;
				result_set[1] = g_id;
				result_set[2] = game_tree_name;
				if(game_tree_type ==2)
					result_set[3] = "完全信息动态博弈";
				else if(game_tree_type ==3)
					result_set[3] = "不完全信息静态博弈";
				else 
					result_set[3] = "不完全信息动态博弈";
				result_set[4] = area;
				result_set[5] = person;
				result_set[6] = game_time;
				gametree_query_model.addRow(result_set);						
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
		         // "jdbc:mysql://localhost/strategy_game","root","wipm");  
		           //����URLΪ   jdbc:mysql//��������ַ/���ݿ���  �������2�������ֱ��ǵ�½�û���������  
		  
		      System.out.println("Success connect Mysql server!");  
		    }  
		    catch (Exception e) {  
		      System.out.print("get data error!");  
		      e.printStackTrace();  
		    }  
		
	}
	
	/*public  static  void  main(String[]  args) {
		gametreequery  game_tree_panel = new  gametreequery();
		
		JFrame frame = new JFrame(); 
		 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE ); 
		 game_tree_panel.setOpaque(true); 
		 frame.setSize(new Dimension(1024, 768)); 
		 frame.setContentPane(game_tree_panel); 
		 frame.setVisible(true); 
		 
		 connectdatabase();

	}*/
}

