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
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;

import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;


public class GameTree extends sub3inFrame {
	public static java.sql.Connection connect;
	public int game_tree_id = 0;
	public int game_type=0;
	public int gid = 0;
	public int is_complete = 0;
	public int is_result = 0;
	
	private JTextField tfGameName;
	private JTextField tf_Basic_name;
	public JComboBox cb_Basic_gametype;
	private JTextField tf_Basic_area;
	private JTextField tf_Basic_discusser;
	private JTextField tf_Basic_time;
	public JButton btn_Basic_save;
	public JButton btn_Gametree_update;
	public JButton btn_Gametree_save;
	public JButton btn_Gametree_compute;
	private JTable tb_Gametree_result;
	private JTable tb_Gametree_profit;	
    public JButton btn_Gametree_result;
    public DefaultTableModel result_model;
	public DefaultTableModel group_model;
	private static mgraphxEx panel;
	
	public class tree_node
	{
		int n_id =0;
		int father_n_id=0;
		int game_tree_node_id =0;
		String game_tree_node_name;
		int node_layer=0;
		int node_position=0;
		int node_type=0;
		int father_node_id=0;
		int actor_id=0;
		int strategy_id=0;
		String strategy_name="";
		float probability=0;
        int profit_set_id=0;
        float compute_value=0;
        String probability_detail="";
	};
    
	public class edge
	{
		int source=0;
		int target=0;
		String side_label="";
	};

	/**
	 * Create the panel.
	 */
	public GameTree() {
		super("其它博弈编辑",true,true,true,true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 100, 100, 100, 100, 100, 100, 100,10};
		gridBagLayout.rowHeights = new int[]{10, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,25, 25, 25, 25, 25, 25,25, 25, 25};
		setLayout(gridBagLayout);

		JLabel lbGameName = new JLabel("\u535A\u5F08\u95EE\u9898\u540D\u79F0:");
		lbGameName.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lbGameName = new GridBagConstraints();
		gbc_lbGameName.anchor = GridBagConstraints.WEST;
		gbc_lbGameName.fill = GridBagConstraints.VERTICAL;
		gbc_lbGameName .insets = new Insets(0, 0, 5, 5);
		gbc_lbGameName .gridx = 0;
		gbc_lbGameName .gridy = 0;
		add(lbGameName, gbc_lbGameName );
		
		tfGameName = new JTextField();
		tfGameName.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_tfGameName = new GridBagConstraints();
		gbc_tfGameName.insets = new Insets(0, 0, 5, 5);
		gbc_tfGameName.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfGameName.gridx = 1;
		gbc_tfGameName.gridy = 0;
		add(tfGameName, gbc_tfGameName);
		tfGameName.setColumns(10);
		
		JButton btnGameNameSearch = new JButton("\u67E5\u8BE2");
		btnGameNameSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnGameNameSearch.setEnabled(false);
				btn_Basic_save.setEnabled(false);
				btn_Gametree_update.setEnabled(false);
				btn_Gametree_save.setEnabled(false);
				btn_Gametree_compute.setEnabled(false);
				game_tree_id=0;
				gid=0;
				game_type =0;
				is_complete=0;
				is_result=0;
				result_model.setRowCount(0);
				group_model.setRowCount(0);
				
				String query_name =  tfGameName.getText().trim();
				try {
					Statement stmt= connect.createStatement();
					String sql = "select * from tb_game_tree where game_tree_name='"+query_name+"' and game_tree_type !=1";
					ResultSet rs = stmt.executeQuery(sql);
					if (rs.next())//��ʾ��������Ϣ
					{
						tf_Basic_name.setText(rs.getString("game_tree_name"));
						tf_Basic_area.setText(rs.getString("area"));
						tf_Basic_discusser.setText(rs.getString("person"));
						tf_Basic_time.setText(rs.getDate("event_time").toString());
						game_type = rs.getInt("game_tree_type");
						switch(game_type) {
					     case 2:
					        cb_Basic_gametype.setSelectedItem("完全信息动态");
					        break;			
					     case 3:
					        cb_Basic_gametype.setSelectedItem("不完全信息静态");
					        break;		
					     case 4:
					        cb_Basic_gametype.setSelectedItem("不完全信息动态");
					        break;		
						}
						game_tree_id = rs.getInt("game_tree_id");
						gid = rs.getInt("gid");
						is_complete = rs.getInt("is_complete");
						if(is_complete == 1)
							btn_Gametree_update.setEnabled(true);
						is_result = rs.getInt("is_result");
						if(is_result == 1)
							btn_Gametree_result.setEnabled(true);
					}
					else
					{
						tf_Basic_name.setText(null);
						tf_Basic_area.setText(null);
						tf_Basic_discusser.setText(null);
						tf_Basic_time.setText(null);
					}
					btn_Basic_save.setEnabled(true);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				btnGameNameSearch.setEnabled(true);					
			}
		});
		btnGameNameSearch.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btnGameNameSearch = new GridBagConstraints();
		gbc_btnGameNameSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnGameNameSearch.gridx = 2;
		gbc_btnGameNameSearch.gridy = 0;
		add(btnGameNameSearch, gbc_btnGameNameSearch);
		
		JLabel lbBasicInfo = new JLabel("\u57FA\u672C\u4FE1\u606F\u7F16\u8F91");
		lbBasicInfo.setFont(new Font("黑体", Font.PLAIN, 14));
		GridBagConstraints gbc_lbBasicInfo = new GridBagConstraints();
		gbc_lbBasicInfo.anchor = GridBagConstraints.WEST;
		gbc_lbBasicInfo.insets = new Insets(0, 0, 5, 5);
		gbc_lbBasicInfo.gridx = 0;
		gbc_lbBasicInfo.gridy = 1;
		add(lbBasicInfo, gbc_lbBasicInfo);
		
		JLabel lb_Basic_name = new JLabel("\u535A\u5F08\u95EE\u9898\u540D\u79F0\uFF1A");
		lb_Basic_name.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_Basic_name = new GridBagConstraints();
		gbc_lb_Basic_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_lb_Basic_name.insets = new Insets(0, 0, 5, 5);
		gbc_lb_Basic_name.gridx = 0;
		gbc_lb_Basic_name.gridy = 2;
		add(lb_Basic_name, gbc_lb_Basic_name);
		
		tf_Basic_name = new JTextField();
		tf_Basic_name.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_tf_Basic_name = new GridBagConstraints();
		gbc_tf_Basic_name.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Basic_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Basic_name.gridx = 1;
		gbc_tf_Basic_name.gridy = 2;
		add(tf_Basic_name, gbc_tf_Basic_name);
		tf_Basic_name.setColumns(10);
		
		JLabel lb_Basic_gametype = new JLabel("\u535A\u5F08\u6811\u7C7B\u578B\uFF1A");
		lb_Basic_gametype.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_Basic_gametype = new GridBagConstraints();
		gbc_Basic_gametype.anchor = GridBagConstraints.EAST;
		gbc_Basic_gametype.insets = new Insets(0, 0, 5, 5);
		gbc_Basic_gametype.gridx = 2;
		gbc_Basic_gametype.gridy = 2;
		add(lb_Basic_gametype, gbc_Basic_gametype);
		
		String[] gametype={"完全信息动态","不完全信息静态","不完全信息动态"};
		cb_Basic_gametype = new JComboBox(gametype);
		cb_Basic_gametype.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_cb_Basic_gametype = new GridBagConstraints();
		gbc_cb_Basic_gametype.insets = new Insets(0, 0, 5, 5);
		gbc_cb_Basic_gametype.fill = GridBagConstraints.HORIZONTAL;
		gbc_cb_Basic_gametype.gridx = 3;
		gbc_cb_Basic_gametype.gridy = 2;
		gbc_cb_Basic_gametype.gridwidth=2;		
		add(cb_Basic_gametype, gbc_cb_Basic_gametype);
		
		JLabel lb_Basic_area = new JLabel("\u53D1\u751F\u5730\u70B9\uFF1A");
		lb_Basic_area.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_Basic_area = new GridBagConstraints();
		gbc_lb_Basic_area.anchor = GridBagConstraints.EAST;
		gbc_lb_Basic_area.insets = new Insets(0, 0, 5, 5);
		gbc_lb_Basic_area.gridx = 5;
		gbc_lb_Basic_area.gridy = 2;
		add(lb_Basic_area, gbc_lb_Basic_area);
		
		tf_Basic_area = new JTextField();
		tf_Basic_area.setFont(new Font("宋体", Font.PLAIN, 14));
		tf_Basic_area.setColumns(10);
		GridBagConstraints gbc_tf_Basic_area = new GridBagConstraints();
		gbc_tf_Basic_area.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Basic_area.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Basic_area.gridx = 6;
		gbc_tf_Basic_area.gridy = 2;
		add(tf_Basic_area, gbc_tf_Basic_area);
		
		JLabel lb_Basic_discusser = new JLabel("\u7814\u8BA8\u4E3B\u4F53\uFF1A");
		lb_Basic_discusser.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_Basic_discusser = new GridBagConstraints();
		gbc_lb_Basic_discusser.anchor = GridBagConstraints.WEST;
		gbc_lb_Basic_discusser.insets = new Insets(0, 0, 5, 5);
		gbc_lb_Basic_discusser.gridx = 0;
		gbc_lb_Basic_discusser.gridy = 3;
		add(lb_Basic_discusser, gbc_lb_Basic_discusser);
		
		tf_Basic_discusser = new JTextField();
		tf_Basic_discusser.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_tf_Basic_discusser = new GridBagConstraints();
		gbc_tf_Basic_discusser.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Basic_discusser.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Basic_discusser.gridx = 1;
		gbc_tf_Basic_discusser.gridy = 3;
		add(tf_Basic_discusser, gbc_tf_Basic_discusser);
		tf_Basic_discusser.setColumns(10);
		
		JLabel lb_Basic_time = new JLabel("\u53D1\u751F\u65F6\u95F4\uFF1A");
		lb_Basic_time.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_Basic_time = new GridBagConstraints();
		gbc_lb_Basic_time.anchor = GridBagConstraints.EAST;
		gbc_lb_Basic_time.insets = new Insets(0, 0, 5, 5);
		gbc_lb_Basic_time.gridx = 2;
		gbc_lb_Basic_time.gridy = 3;
		add(lb_Basic_time, gbc_lb_Basic_time);

		tf_Basic_time = new JTextField();
		tf_Basic_time.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_tf_Basic_time = new GridBagConstraints();
		gbc_tf_Basic_time.insets = new Insets(0, 0, 5, 5);
		gbc_tf_Basic_time.fill = GridBagConstraints.HORIZONTAL;
		gbc_tf_Basic_time.gridx = 3;
		gbc_tf_Basic_time.gridy = 3;
		add(tf_Basic_time, gbc_tf_Basic_time);
		tf_Basic_time.setColumns(10);
		
		btn_Basic_save= new JButton("\u4FDD\u5B58");
		btn_Basic_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btn_Basic_save.setEnabled(false);
				Statement stmt;
				try {
					stmt = connect.createStatement();
					String game_tree_name = tf_Basic_name.getText();
					String gametype = cb_Basic_gametype.getSelectedItem().toString();
					switch(gametype) {		        
			         case "完全信息动态":
			        	 game_type = 2;
			         break;
			         case "不完全信息静态":
			        	 game_type = 3;
			         break;
			         case "不完全信息动态":
			        	 game_type = 4;
			         break;
					}
					String area= tf_Basic_area.getText();
					String person =tf_Basic_discusser.getText();
					String event_time =tf_Basic_time.getText();
					if(game_tree_id == 0)
					 {
						 String sql = "insert into tb_game_tree (game_tree_name,game_tree_type,area,person,event_time) values ('"
					                  +game_tree_name+"',"+game_type +",'"+area+"','"+person+"','"+event_time+"')";
						 int row=stmt.executeUpdate (sql,Statement.RETURN_GENERATED_KEYS);  
						 ResultSet rs = stmt.getGeneratedKeys ();  
					     if ( rs.next() ) {  
					    	 game_tree_id = rs.getInt(row); 
					     }
					 }
					else
					{
						String sql = "update tb_game_tree set game_tree_name='" + game_tree_name + "', game_tree_type = "+game_type +",area ='"
								     +area+"',person='"+person+"',event_time='"+event_time+"' where game_tree_id=" + game_tree_id;
						stmt.executeUpdate(sql);  
					}
					btn_Gametree_update.setEnabled(true); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				btn_Basic_save.setEnabled(true); 
			}
		});
		btn_Basic_save.setEnabled(false);
		btn_Basic_save.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btn_Basic_save = new GridBagConstraints();
		gbc_btn_Basic_save.insets = new Insets(0, 0, 5, 5);
		gbc_btn_Basic_save.gridx = 4;
		gbc_btn_Basic_save.gridy = 3;
		add(btn_Basic_save, gbc_btn_Basic_save);

		JLabel lb_Gametree = new JLabel("\u535A\u5F08\u7ED3\u6784\u7F16\u8F91");
		lb_Gametree.setFont(new Font("黑体", Font.PLAIN, 14));
		GridBagConstraints gbc_lb_Gametree = new GridBagConstraints();
		gbc_lb_Gametree.anchor = GridBagConstraints.WEST;
		gbc_lb_Gametree.insets = new Insets(0, 0, 5, 5);
		gbc_lb_Gametree.gridx = 0;
		gbc_lb_Gametree.gridy = 4;
		add(lb_Gametree, gbc_lb_Gametree);
        
		btn_Gametree_update = new JButton("\u7F16\u8F91\u535A\u5F08\u7ED3\u6784");
		btn_Gametree_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btn_Gametree_save.setEnabled(true);
				if(is_complete == 1)
				{
					dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://192.168.0.2:3336/strategy_game","root","wipm");	 
					panel.gpanel.readGfromDB(dbio,gid);
				}
			}
		});
		btn_Gametree_update.setEnabled(false);
		btn_Gametree_update.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btn_Gametree_update = new GridBagConstraints();
		gbc_btn_Gametree_update.anchor = GridBagConstraints.WEST;
		gbc_btn_Gametree_update.insets = new Insets(0, 0, 5, 5);
		gbc_btn_Gametree_update.gridx = 0;
		gbc_btn_Gametree_update.gridy = 5;
		add(btn_Gametree_update, gbc_btn_Gametree_update);
		
		btn_Gametree_save = new JButton("\u4FDD\u5B58");
		btn_Gametree_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dbIO dbio=new dbIO("com.mysql.jdbc.Driver","jdbc:mysql://192.168.0.2:3336/strategy_game","root","wipm");	 
				    int temp_gid =panel.gpanel.saveG2DB(tf_Basic_name.getText().trim(), gid, dbio);
				    if(gid==0)
				    	gid = temp_gid;
				    Statement stmt;	
				    stmt = connect.createStatement();
				    ArrayList<edge> side_list = new ArrayList<edge>();
				    String sql_query = "select * from edges where gid="+gid;
					ResultSet rs = stmt.executeQuery(sql_query);
					while (rs.next())
					{
						String source = rs.getString("source");
						String target = rs.getString("target");
						String label = rs.getString("label");
						edge temp_edge = new edge();
						temp_edge.source = Integer.parseInt(source);
						temp_edge.target = Integer.parseInt(target);
						temp_edge.side_label = label;
						side_list.add(temp_edge);
					}
				    
				    ArrayList<tree_node> node_list = new ArrayList<tree_node>();				    				    
				    sql_query = "select * from nodes where gid="+gid;
					rs = stmt.executeQuery(sql_query);
					while (rs.next())
					{
						int id = rs.getInt("id");
						String label = rs.getString("label");
						tree_node temp_node = new tree_node();
						temp_node.game_tree_node_name = label;
						temp_node.node_position = id;
						temp_node.n_id = id;
						boolean has_son = false;
						for (edge t_edge : side_list) 
						{
					        if(t_edge.target == id)
					        {
					        	temp_node.father_n_id = t_edge.source;
					        	temp_node.strategy_name = t_edge.side_label;
					        }
					        if(t_edge.source == id)
					        {
					        	has_son = true;
					        }
					    }
						if(temp_node.father_n_id==0)
						{
							temp_node.node_type = 1;
							temp_node.node_layer = 1;
						}
						else if(has_son == true)
						{
							temp_node.node_type = 2;
						}
						else
						{
							temp_node.node_type = 3;
						}
						node_list.add(temp_node);
					}
					
					for(tree_node temp_node:node_list)
					{
						int actor_detail =1;
						int node_layer =1;
						
						tree_node current_node = temp_node;
						String current_actor = temp_node.game_tree_node_name;
						while(current_node.father_n_id !=0)
						{
							node_layer++;
							if(current_actor.equals(searchgraphfather(current_node,node_list).game_tree_node_name))
							{
								actor_detail=1;
							}
							else
							{
								actor_detail++;
				        	}	
							current_node = searchgraphfather(current_node,node_list);
						}
						
						temp_node.node_layer = node_layer;
						//保存博弈方
						if(temp_node.node_type != 3)
						{
							sql_query = "select * from tb_actor where game_tree_id="+game_tree_id+" and actor_detail="+actor_detail;
							rs = stmt.executeQuery(sql_query);
							
							if (rs.next())
							{
								String actor_id = rs.getString("actor_id");
								temp_node.actor_id = rs.getInt("actor_id");
								String sql_update = "update tb_actor set actor_name='" + temp_node.game_tree_node_name +"' where actor_id=" + actor_id;
								stmt.executeUpdate(sql_update);
							}
							else 
							{
								String sql_update = "insert into tb_actor (actor_name,actor_detail,game_tree_id) values ('"
									                +temp_node.game_tree_node_name +"',"+actor_detail+","+game_tree_id+")";
										
								int row=stmt.executeUpdate (sql_update,Statement.RETURN_GENERATED_KEYS);  
								ResultSet rs_actor = stmt.getGeneratedKeys ();
								if (rs_actor.next()) 
								{
									temp_node.actor_id = rs_actor.getInt(row);
								}  
																	
							}	
						}
						
						//保存策略
						int profit_set_id =0;
						if(temp_node.node_type != 1)
						{
							if(temp_node.node_layer==2 && game_type ==3)
		    				{
								temp_node.probability = Float.parseFloat(temp_node.strategy_name);
		    				}			    					
		    				else
		    				{		    					    						
		    					if(temp_node.node_layer == 3 && game_type ==3)
		    					{
		    						sql_query = "select * from tb_strategy where game_tree_id="+game_tree_id+" and strategy_name='"
										       +temp_node.strategy_name.trim()+"'";
									rs = stmt.executeQuery(sql_query);
									if (rs.next())
									{
										int strategy_id = rs.getInt("strategy_id");
										temp_node.strategy_id = strategy_id;									
									}
									else
									{
										String sql_add = "insert into tb_strategy (strategy_name,game_tree_id) values ('"
										           + temp_node.strategy_name.trim()+"',"+game_tree_id+")";
										int row=stmt.executeUpdate (sql_add,Statement.RETURN_GENERATED_KEYS);  
										ResultSet rs_strategy = stmt.getGeneratedKeys ();
										int strategy_id=0;
										if (rs_strategy.next()) 
										{  
										    strategy_id = rs_strategy.getInt(row);
										    temp_node.strategy_id = strategy_id;
										}
									}
									
									sql_query = "select * from tb_game_tree_node where game_tree_id="+game_tree_id+" and node_position="
										       +temp_node.node_position;
									rs = stmt.executeQuery(sql_query);
									if (rs.next())
									{
										temp_node.profit_set_id = rs.getInt("profit_set_id");									
									}
		    					}
		    					else
		    					{
		    						sql_query = "select * from tb_game_tree_node where game_tree_id="+game_tree_id+" and node_position="
										       +temp_node.node_position;
								    rs = stmt.executeQuery(sql_query);
								    if (rs.next())
								    {
								    	int strategy_id = rs.getInt("strategy_id");
								    	temp_node.profit_set_id = rs.getInt("profit_set_id");									
									    if(strategy_id>0)
									    {
									    	temp_node.strategy_id = strategy_id;
										    String sql_update = "update tb_strategy set strategy_name='" + temp_node.strategy_name +"' where strategy_id=" + strategy_id;
										    stmt.executeUpdate(sql_update);	
									    }
									    else
									    {
										    String sql_add = "insert into tb_strategy (strategy_name,game_tree_id) values ('"
										           + temp_node.strategy_name+"',"+game_tree_id+")";
										    int row=stmt.executeUpdate (sql_add,Statement.RETURN_GENERATED_KEYS);  
										    ResultSet rs_strategy = stmt.getGeneratedKeys ();
										    if (rs_strategy.next()) 
										    {
										    	strategy_id = rs_strategy.getInt(row);
										        temp_node.strategy_id = strategy_id;
										    }
									    }
								    }
								    else
								    {
								    	String sql_add = "insert into tb_strategy (strategy_name,game_tree_id) values ('"
										           + temp_node.strategy_name+"',"+game_tree_id+")";
									    int row=stmt.executeUpdate (sql_add,Statement.RETURN_GENERATED_KEYS);  
									    ResultSet rs_strategy = stmt.getGeneratedKeys ();
									    int strategy_id=0;
									    if (rs_strategy.next()) 
									    {
									    	strategy_id = rs_strategy.getInt(row);
										    temp_node.strategy_id = strategy_id;
									    }
								    }

		    					}
		    					
		    				}
						}
					}
					
					//保存收益组
					for(tree_node temp_node:node_list)
					{
						if(temp_node.node_type ==3)
						{
							if(temp_node.profit_set_id>0)
							{
								String sql_update = "update tb_profit_set set profit_set_name='" +temp_node.game_tree_node_name.trim() 
											+"' where profit_set_id=" + temp_node.profit_set_id;
								stmt.executeUpdate(sql_update);
							}
							else
							{
								String sql_add = "insert into tb_profit_set (profit_set_name,game_tree_id) values ('"
									           + temp_node.game_tree_node_name.trim()+"',"+game_tree_id+")";
								int row=stmt.executeUpdate (sql_add,Statement.RETURN_GENERATED_KEYS);  
								ResultSet rs_profit = stmt.getGeneratedKeys ();
								if (rs_profit.next()) 
								{  
									temp_node.profit_set_id = rs_profit.getInt(row);
								}
							}
							String[] profit_group = temp_node.game_tree_node_name.trim().split(",|，");
							for (int k = 0 ; k <profit_group.length ; k++ ) 
							{				
						    	int strategy_id=0;
						    	int actor_id=0;
						    	int detail = k+1;
						    	if(game_type==3)
						    	{
						    		detail = k+2;
						    	}
						    	sql_query = "select * from tb_actor where game_tree_id="+game_tree_id+" and actor_detail="+detail;
							    rs = stmt.executeQuery(sql_query);
							    if (rs.next())
							    {
							    	actor_id =  rs.getInt("actor_id");
							    }
						    	tree_node current_node = temp_node;
						    	while(current_node.father_n_id >0)
						    	{
						    		if(searchgraphfather(current_node,node_list).actor_id ==  actor_id)
						    		{
						    			strategy_id = current_node.strategy_id;
					    				break;
					    			}
					    			else
					    			{
					    				current_node= searchgraphfather(current_node,node_list);
						    		}						  
						    	}
						    	if(strategy_id>0)
						    	{
						    		sql_query = "select * from tb_profit_group where profit_set_id="+temp_node.profit_set_id+" and actor_id="+actor_id+" and strategy_id ="
											       +strategy_id;
									rs = stmt.executeQuery(sql_query);
									if (rs.next())
									{
										int profit_group_id = rs.getInt("profit_group_id");
										String sql_update = "update tb_profit_group set profit_value=" + profit_group[k] +" where profit_group_id=" + profit_group_id;
										stmt.executeUpdate(sql_update);										
									}
									else
									{
										String sql_add = "insert into tb_profit_group (profit_set_id,actor_id,strategy_id,profit_value) values ("
											           +temp_node.profit_set_id+","+actor_id+","+strategy_id+","+profit_group[k]+")";
										stmt.executeUpdate (sql_add);  
									}
						    	}
							}
					    }
						//保存博弈树节点
						sql_query = "select * from tb_game_tree_node where game_tree_id="+game_tree_id+" and node_position="
							       +temp_node.node_position;
						rs = stmt.executeQuery(sql_query);
						if (rs.next())
						{
							int game_tree_node_id = rs.getInt("game_tree_node_id");
							temp_node.game_tree_node_id = game_tree_node_id;
							String sql_update = "update tb_game_tree_node set game_tree_node_name='" + temp_node.game_tree_node_name +"',node_type="+
									temp_node.node_type+",node_layer ="+temp_node.node_layer+",actor_id="+temp_node.actor_id+",strategy_id="+temp_node.strategy_id+",probability="+
									String.valueOf(temp_node.probability) +",profit_set_id="+temp_node.profit_set_id+" where game_tree_node_id=" + game_tree_node_id;
							stmt.executeUpdate(sql_update);										
						}
						else
						{
							if(temp_node.father_n_id >0)
							{
								temp_node.father_node_id = searchgraphfather(temp_node,node_list).game_tree_node_id;
							}								
							String sql_add = "insert into tb_game_tree_node (game_tree_node_name,game_tree_id,node_layer,node_position,node_type,father_node_id,actor_id,strategy_id,probability,profit_set_id) values ('"
							           + temp_node.game_tree_node_name.trim()+"',"+game_tree_id+","+temp_node.node_layer+","+temp_node.node_position+","
									   + temp_node.node_type+","+temp_node.father_node_id+","+temp_node.actor_id+","+temp_node.strategy_id+","
							           + String.valueOf(temp_node.probability)+","+temp_node.profit_set_id+")";
							int row=stmt.executeUpdate (sql_add,Statement.RETURN_GENERATED_KEYS);  
							ResultSet rs_profit = stmt.getGeneratedKeys ();
							if (rs_profit.next()) 
							{  
								int game_tree_node_id = rs_profit.getInt(row);
								temp_node.game_tree_node_id=game_tree_node_id;
							}
						}
					}
					 //���沩��������Ϣ
				    String sql_update = "update tb_game_tree set is_complete=1,gid=" + gid +" where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql_update);
					btn_Gametree_compute.setEnabled(true);					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btn_Gametree_save.setEnabled(false);
		btn_Gametree_save.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btn_Gametree_save = new GridBagConstraints();
		gbc_btn_Gametree_save.anchor = GridBagConstraints.WEST;
		gbc_btn_Gametree_save.insets = new Insets(0, 0, 5, 5);
		gbc_btn_Gametree_save.gridx = 1;
		gbc_btn_Gametree_save.gridy = 5;
		add(btn_Gametree_save, gbc_btn_Gametree_save);
		
		btn_Gametree_compute = new JButton("\u535A\u5F08\u5747\u8861\u8BA1\u7B97");
		btn_Gametree_compute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				btn_Gametree_compute.setEnabled(false);
				btn_Gametree_result.setEnabled(false);
				try 
				{
					Statement stmt;
					stmt = connect.createStatement();
					//清空结果集
					String sql_update = "update tb_profit_set set pure_probability=0, mix_probability=0 where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql_update);
					
					sql_update = "update tb_game_tree set is_result=0 where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql_update);
					
					String sql_delete = "delete from tb_result where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql_delete);
					//逆向归纳法求解						
					ArrayList<tree_node> node_list = new ArrayList<tree_node>();
					String sql = "select * from tb_game_tree_node where game_tree_id="+game_tree_id;
					ResultSet rs = stmt.executeQuery(sql);
					int max_layer =0;
					//获取博弈树
					while (rs.next())
					{
						tree_node temp_node = new tree_node();
						temp_node.father_node_id = rs.getInt("father_node_id");
						temp_node.game_tree_node_id =rs.getInt("game_tree_node_id");
						temp_node.node_layer=rs.getInt("node_layer");
						if(temp_node.node_layer> max_layer)
						{
							max_layer = temp_node.node_layer;
						}
						temp_node.node_type=rs.getInt("node_type");
						temp_node.actor_id=rs.getInt("actor_id");
						temp_node.profit_set_id=rs.getInt("profit_set_id");
						temp_node.probability= rs.getFloat("probability");
						temp_node.strategy_id=rs.getInt("strategy_id");
						node_list.add(temp_node);
					}
					//博弈树求解
					for(int layer=max_layer;layer>1;layer--)
					{
						for(tree_node temp_node:node_list)
						{
							if(temp_node.node_layer == layer)
							{
								int actor_id = 0;
								if(layer==2 && game_type == 3)
									actor_id = temp_node.actor_id;
								else
									actor_id = searchtreefather(temp_node,node_list).actor_id;
								
								int value =0;
								String sql_value = "select * from tb_profit_group where profit_set_id="+temp_node.profit_set_id +" and actor_id ="+actor_id;
								ResultSet rs_value = stmt.executeQuery(sql_value);
								if (rs_value.next())
								{
									value = rs_value.getInt("profit_value");
								}
								
								if(searchtreefather(temp_node,node_list).profit_set_id == 0)
								{
									searchtreefather(temp_node,node_list).profit_set_id = temp_node.profit_set_id;
									if(game_type == 3)
									{
										if(layer==3)
										{
											int strategy_id=temp_node.strategy_id;
											float result_value =0;
											for(tree_node j_node:node_list)
											{
												if(j_node.node_layer == 3)
												{
													String sql_other_value = "select * from tb_profit_group where profit_set_id="+j_node.profit_set_id 
															+" and strategy_id ="+strategy_id;
													ResultSet rs_other_value = stmt.executeQuery(sql_other_value);
													if (rs_other_value.next())
													{
														result_value = result_value +rs_other_value.getInt("profit_value") * searchtreefather(j_node,node_list).probability ;
														searchtreefather(j_node,node_list).profit_set_id = j_node.profit_set_id;
													}
												}												
											}
											searchtreefather(temp_node,node_list).compute_value = result_value;
										}
										else if(layer>3)
											searchtreefather(temp_node,node_list).compute_value = value;
									}
									else
										searchtreefather(temp_node,node_list).compute_value = value;
								}
								else
								{
									if(game_type == 3)
									{
										if(layer==3)
										{
											int strategy_id=temp_node.strategy_id;
											float result_value =0;
											for(tree_node j_node:node_list)
											{
												if(j_node.node_layer == 3)
												{
													String sql_other_value = "select * from tb_profit_group where profit_set_id="+j_node.profit_set_id 
															+" and strategy_id ="+strategy_id;
													ResultSet rs_other_value = stmt.executeQuery(sql_other_value);
													if (rs_other_value.next())
													{
														result_value = result_value +rs_other_value.getInt("profit_value") * searchtreefather(j_node,node_list).probability ;
													}
												}
												
											}
											if(searchtreefather(temp_node,node_list).compute_value < result_value)
											{
												searchtreefather(temp_node,node_list).profit_set_id = temp_node.profit_set_id;
												searchtreefather(temp_node,node_list).compute_value = result_value;
											}
											
										}
										else if(layer>3)
										{
											if(searchtreefather(temp_node,node_list).compute_value < value)
											{
												searchtreefather(temp_node,node_list).profit_set_id = temp_node.profit_set_id;
												searchtreefather(temp_node,node_list).compute_value = value;
											}
										}										
									}
									else
									{
										if(searchtreefather(temp_node,node_list).compute_value < value)
										{
											searchtreefather(temp_node,node_list).profit_set_id = temp_node.profit_set_id;
											searchtreefather(temp_node,node_list).compute_value = value;
										}
									}
								}
							}
						}
					}

					//保存结果
					if(game_type ==3)
					{
						for(tree_node temp_node:node_list)
						{
							if(temp_node.node_layer ==2)
							{
								sql_update = "update tb_profit_set set pure_probability="+String.valueOf(temp_node.probability)+" where profit_set_id=" + temp_node.profit_set_id;
								stmt.executeUpdate(sql_update);
									
								String sql_query ="select * from tb_profit_group where profit_set_id=" + temp_node.profit_set_id;
								ResultSet rs_result = stmt.executeQuery(sql_query);
									
								Statement stmt_new;
								stmt_new = connect.createStatement();
								
								while(rs_result.next())
								{
									String act_id = rs_result.getString("actor_id");
									String strategy_id = rs_result.getString("strategy_id");
										
									String sql_add = "insert into tb_result (game_tree_id,actor_id,strategy_id,probability,nash_equilibrium_type) values ("
										    	     +game_tree_id+","+ act_id+","+strategy_id+","+String.valueOf(temp_node.probability)+",4)";
									stmt_new.executeUpdate(sql_add);
								}
							}							
						}
					}
					else
					{
						for(tree_node temp_node:node_list)
						{
							if(temp_node.node_layer ==1)
							{
								sql_update = "update tb_profit_set set pure_probability=1 where profit_set_id=" + temp_node.profit_set_id;
								stmt.executeUpdate(sql_update);
									
								String sql_query ="select * from tb_profit_group where profit_set_id=" + temp_node.profit_set_id;
								ResultSet rs_result = stmt.executeQuery(sql_query);
									
								Statement stmt_new;
								stmt_new = connect.createStatement();
									
								while(rs_result.next())
								{
									String act_id = rs_result.getString("actor_id");
									String strategy_id = rs_result.getString("strategy_id");
										
									String sql_add = "insert into tb_result (game_tree_id,actor_id,strategy_id,probability,nash_equilibrium_type) values ("
										    	     +game_tree_id+","+ act_id+","+strategy_id+",1,3)";
									stmt_new.executeUpdate(sql_add);
								}
								break;
							}
						}					
					}
											
					sql_update = "update tb_game_tree set is_result=1 where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql_update);
						
					btn_Gametree_compute.setEnabled(true);
					btn_Gametree_result.setEnabled(true);
					

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_Gametree_compute.setFont(new Font("宋体", Font.PLAIN, 14));
		btn_Gametree_compute.setEnabled(false);
		GridBagConstraints gbc_btn_Gametree_compute = new GridBagConstraints();
		gbc_btn_Gametree_compute.anchor = GridBagConstraints.WEST;
		gbc_btn_Gametree_compute.insets = new Insets(0, 0, 5, 5);
		gbc_btn_Gametree_compute.gridx = 5;
		gbc_btn_Gametree_compute.gridy = 5;
		add(btn_Gametree_compute, gbc_btn_Gametree_compute);
		
		btn_Gametree_result = new JButton("\u663E\u793A\u8BA1\u7B97\u7ED3\u679C");
		btn_Gametree_result.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					result_model.setRowCount(0);
					group_model.setRowCount(0);
					Statement stmt;
					stmt = connect.createStatement();
					String sql = "select * from tb_profit_set where pure_probability>0 and game_tree_id=" + game_tree_id;
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
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btn_Gametree_result.setEnabled(false);
		btn_Gametree_result.setFont(new Font("宋体", Font.PLAIN, 14));
		GridBagConstraints gbc_btn_Gametree_result = new GridBagConstraints();
		gbc_btn_Gametree_result.anchor = GridBagConstraints.WEST;
		gbc_btn_Gametree_result.insets = new Insets(0, 0, 5, 5);
		gbc_btn_Gametree_result.gridx = 6;
		gbc_btn_Gametree_result.gridy = 5;
		add(btn_Gametree_result, gbc_btn_Gametree_result);
		
		 
		String[] col_Gametree_result = {"收益组名称","概率","纳什均衡类型"}; 
		result_model   =   new   DefaultTableModel(new Object[][] {},col_Gametree_result);		
		tb_Gametree_result = new JTable(result_model);
		tb_Gametree_result.setPreferredScrollableViewportSize(new Dimension(200,25));//ע����У��������ܿ�������
		GridBagConstraints gbc_tb_Gametree_result = new GridBagConstraints();
		gbc_tb_Gametree_result.gridheight = 7;
		gbc_tb_Gametree_result.insets = new Insets(0, 0, 5, 5);
		gbc_tb_Gametree_result.fill = GridBagConstraints.BOTH;
		gbc_tb_Gametree_result.gridwidth =3;
		gbc_tb_Gametree_result.gridx = 5;
		gbc_tb_Gametree_result.gridy = 6;
		JScrollPane sp_Gametree_result = new JScrollPane(tb_Gametree_result);
		add(sp_Gametree_result, gbc_tb_Gametree_result);
		
		panel = new mgraphxEx(false,16,18,true);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridwidth = 5;
		gbc_panel.gridheight = 15;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		add(panel, gbc_panel);
		
		String[] col_Gametree_profit = {"博弈方名称","策略名称","概率","纳什均衡类型"};
		group_model   =   new   DefaultTableModel(new Object[][] {},col_Gametree_profit);	
		tb_Gametree_profit = new JTable(group_model);
		tb_Gametree_profit.setPreferredScrollableViewportSize(new Dimension(200,25));//ע����У��������ܿ�������
		GridBagConstraints gbc_tb_Gametree_profit = new GridBagConstraints();
		gbc_tb_Gametree_profit.insets = new Insets(0, 0, 5, 5);
		gbc_tb_Gametree_profit.gridheight = 7;
		gbc_tb_Gametree_profit.fill = GridBagConstraints.BOTH;
		gbc_tb_Gametree_profit.gridwidth =3;
		gbc_tb_Gametree_profit.gridx = 5;
		gbc_tb_Gametree_profit.gridy = 14;
		JScrollPane sp_Gametree_profit = new JScrollPane(tb_Gametree_profit);
		add(sp_Gametree_profit, gbc_tb_Gametree_profit);
	}
	
	public static tree_node searchgraphfather(tree_node m,ArrayList<tree_node> node_list){
		for(tree_node temp_node:node_list)
		{
			if(m.father_n_id == temp_node.n_id)
			{
				return temp_node;
			}
		}
		return m;
	}
	
	public static tree_node searchtreefather(tree_node m,ArrayList<tree_node> node_list){
		for(tree_node temp_node:node_list)
		{
			if(m.father_node_id == temp_node.game_tree_node_id)
			{
				return temp_node;
			}
		}
		return m;
	}
    
	public static void connectdatabase(){
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
		    	   connect = DriverManager.getConnection(  
		          "jdbc:mysql://localhost/strategy_game","root","wipm");  
		           //����URLΪ   jdbc:mysql//��������ַ/���ݿ���  �������2�������ֱ��ǵ�½�û���������  
		  
		      System.out.println("Success connect Mysql server!");  
		    }  
		    catch (Exception e) {  
		      System.out.print("get data error!");  
		      e.printStackTrace();  
		    }  
		
	}
	
	/*public  static  void  main(String[]  args) {
		GameTree  game_tree_panel = new  GameTree();
		
		JFrame frame = new JFrame(); 
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE ); 
		 game_tree_panel.setOpaque(true); 
		 frame.setSize(new Dimension(1024, 768)); 
		 frame.setContentPane(game_tree_panel); 
		 frame.setVisible(true); 
		 
		 connectdatabase();

	}*/
}
