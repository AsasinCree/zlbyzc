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
import java.awt.Color;

import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;
import zlbyzc.sub3.gametree.GameMatrix.matrix_compute_table;

public class GameMatrix extends sub3inFrame {
	private JTextField tfGameName;
	private static JTextField tf_Basic_name;
	private static JTextField tf_Basic_area;
	private static JTextField tf_Basic_discusser;
	private static JTextField tf_Basic_time;
	private JTable tb_Gametree_result;
	private JTable tb_Gametree_profit;
	public static java.sql.Connection connect;
	public JButton btn_Basic_save;
	public JButton btn_Gametree_update;
	public JButton btn_Gametree_save;
	public JButton btn_Gametree_compute;
	public JButton btn_Gametree_result;
	public static int game_tree_id = 0;
	public int game_type=0;
	public int is_complete = 0;
	public int is_result = 0;
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
	private JLabel lbCheckBasic;
	/**
	 * Create the panel.
	 */
	public GameMatrix() {		
		super("完全信息静态博弈编辑",true,true,true,true);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{10, 100, 100, 100, 100, 100, 100, 100,10};
		gridBagLayout.rowHeights = new int[]{10, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,25, 25, 25};
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
				btn_Gametree_result.setEnabled(false);
				game_tree_id=0;
				is_complete=0;
				is_result=0;
				tf_Basic_name.setText(null);
				tf_Basic_area.setText(null);
				tf_Basic_discusser.setText(null);
				tf_Basic_time.setText(null);
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
				result_model.setRowCount(0);
				group_model.setRowCount(0);
				
				if(!tfGameName.getText().trim().equals(""))
				{										
					String query_name =  tfGameName.getText();
					try 
					{
						Statement stmt= connect.createStatement();
						String sql = "select * from tb_game_tree where game_tree_name='"+query_name+"' and game_tree_type =1";
						ResultSet rs = stmt.executeQuery(sql);
						if (rs.next())//��ʾ��������Ϣ
						{
							tf_Basic_name.setText(rs.getString("game_tree_name"));
							tf_Basic_area.setText(rs.getString("area"));
							tf_Basic_discusser.setText(rs.getString("person"));
							tf_Basic_time.setText(rs.getDate("event_time").toString());
							game_type = rs.getInt("game_tree_type");
							switch(game_type) {
							 case 1:
					        	 cb_Basic_gametype.setSelectedItem("完全信息静态");
					         break;	
							}
							game_tree_id = rs.getInt("game_tree_id");
							is_complete = rs.getInt("is_complete");
							if(is_complete == 1)
								btn_Gametree_update.setEnabled(true);
							is_result = rs.getInt("is_result");
							if(is_result == 1)
								btn_Gametree_result.setEnabled(true);
						}					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				btn_Basic_save.setEnabled(true);
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
		

		String[] gametype={"完全信息静态"};
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
				String checkresult = CheckBasic();
				if(checkresult =="Correct")
				{
					Statement stmt;
					try {
						stmt = connect.createStatement();
						String game_tree_name = tf_Basic_name.getText();
						String gametype = cb_Basic_gametype.getSelectedItem().toString();
						switch(gametype) {
						 case "完全信息静态":
				        	 game_type = 1;
				         break;			        
						}
						String area= tf_Basic_area.getText();
						String person =tf_Basic_discusser.getText();
						String event_time =tf_Basic_time.getText();
						if(game_tree_id == 0)
						 {
							 String sql = "insert into tb_game_tree (game_tree_name,game_tree_type,area,person,event_time) values ('"
						                  +game_tree_name+"',"+ String.valueOf(game_type)+",'"+area+"','"+person+"','"+event_time+"')";
							 int row=stmt.executeUpdate (sql,Statement.RETURN_GENERATED_KEYS);  
							 ResultSet rs = stmt.getGeneratedKeys ();  
						     if ( rs.next() ) {  
						    	 game_tree_id = rs.getInt(row); 
						     }
						 }
						else
						{
							String sql = "update tb_game_tree set game_tree_name='" + game_tree_name + "', game_tree_type = "+String.valueOf(game_type) +",area ='"
									     +area+"',person='"+person+"',event_time='"+event_time+"' where game_tree_id=" + String.valueOf(game_tree_id);
							stmt.executeUpdate(sql);  
						}
						btn_Gametree_update.setEnabled(true); 
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					lbCheckBasic.setText(checkresult);
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
		
		lbCheckBasic = new JLabel("");
		GridBagConstraints gbc_lbCheckBasic = new GridBagConstraints();
		gbc_lbCheckBasic.insets = new Insets(0, 0, 5, 5);
		gbc_lbCheckBasic.gridx = 5;
		gbc_lbCheckBasic.gridy = 3;
		gbc_lbCheckBasic.gridwidth=2;
		add(lbCheckBasic, gbc_lbCheckBasic);
		
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

					} 
					catch (SQLException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
					//保存博弈方
				    String actor_name[] = new String[2] ; 
				    actor_name[0] = tf_actor_1.getText();
				    actor_name[1] = tf_actor_2.getText();
				    int actor_1_id=0;
				    int actor_2_id=0;
				    Statement stmt;					
					stmt = connect.createStatement();	
				    for(int i=0;i<2;i++)
				    {
				    	if(actor_name[i].trim().equals(""))
						{
						}
				    	else
				    	{				
							String sql_query = "select * from tb_actor where game_tree_id="+String.valueOf(game_tree_id)+" and actor_detail="+String.valueOf(i+1);
							ResultSet rs = stmt.executeQuery(sql_query);
							
							if (rs.next())
							{
								String actor_id = rs.getString("actor_id");
								if(i==0)
						    		actor_1_id = rs.getInt("actor_id");
								if(i==1)
						    		actor_2_id = rs.getInt("actor_id");
								String sql_update = "update tb_actor set actor_name='" + actor_name[i] +"' where actor_id=" + actor_id;
								stmt.executeUpdate(sql_update);  
							}
							else
							{
								String sql_update = "insert into tb_actor (actor_name,actor_detail,game_tree_id) values ('"
								       +actor_name[i]+"',"+String.valueOf(i+1)+","+String.valueOf(game_tree_id)+")";
								int row=stmt.executeUpdate (sql_update,Statement.RETURN_GENERATED_KEYS);  
								ResultSet rs_actor = stmt.getGeneratedKeys ();
							    if (rs_actor.next()) 
							    {  
							    	if(i==0)
							    		actor_1_id = rs_actor.getInt(row);
							    	else
							    		actor_2_id = rs_actor.getInt(row);
							    }
							}
						}						
				    }
				    //������Ժ�������Ϣ
				    String matrix_value[][] = new String[3][3] ; 
				    for(int i=0;i<3;i++)
				    {
				    	for(int j=0;j<3;j++)
				    	{
				    		matrix_value[i][j]=(String) matrix_table.getValueAt(i,j);
				    		if(matrix_value[i][j].trim().equals(""))
							{
				    			//
							}
				    		else
				    		{				
								String sql_query = "select * from tb_game_matrix where game_tree_id="+String.valueOf(game_tree_id)+" and matrix_row="
								       +String.valueOf(i)+" and matrix_column="+String.valueOf(j);
								ResultSet rs = stmt.executeQuery(sql_query);
								if (rs.next())
								{
									if(i==0 || j==0)
									{
										String strategy_id = rs.getString("strategy_id");
										String sql_update = "update tb_strategy set strategy_name='" + matrix_value[i][j].trim() +"' where strategy_id=" + strategy_id;
										stmt.executeUpdate(sql_update);  
									}
									else
									{
										int profit_set_id = rs.getInt("profit_set_id");
										String sql_update = "update tb_profit_set set profit_set_name='" + matrix_value[i][j].trim() +"' where profit_set_id=" + profit_set_id;
										stmt.executeUpdate(sql_update);
										
										String[] profit_group = matrix_value[i][j].trim().split(",|，");
									    for (int k = 0 ; k <profit_group.length ; k++ ) 
									    {				
								    		int strategy_id=0;
								    		int actor_id=0;
								    		String matrix_query="";
									    	if(k==0)
									    	{
									    		matrix_query = "select * from tb_game_matrix where game_tree_id="+game_tree_id+" and matrix_row="
													               +i+" and matrix_column=0";
												actor_id=actor_1_id;
									    	}
									    	else if(k==1)
									    	{
									    		matrix_query = "select * from tb_game_matrix where game_tree_id="+game_tree_id
									    		               +" and matrix_row=0 and matrix_column="+j;
										        actor_id=actor_2_id;
									    	}
									    	ResultSet rs_matrix = stmt.executeQuery(matrix_query);
											if (rs_matrix.next())
											{
												strategy_id = rs_matrix.getInt("strategy_id");
											}
									    	String sql_value = "update tb_profit_group set profit_value="+profit_group[k]+" where profit_set_id="
											          + profit_set_id+" and actor_id="+actor_id+" and strategy_id="+strategy_id;
											stmt.executeUpdate(sql_value);
									    } 
									}
								}
								else
								{
									if(i==0 || j==0)
									{
										String sql_add = "insert into tb_strategy (strategy_name,game_tree_id) values ('"
									           + matrix_value[i][j].trim()+"',"+String.valueOf(game_tree_id)+")";
										int row=stmt.executeUpdate (sql_add,Statement.RETURN_GENERATED_KEYS);  
										ResultSet rs_strategy = stmt.getGeneratedKeys ();
										int strategy_id=0;
									    if (rs_strategy.next()) {  
									    	strategy_id = rs_strategy.getInt(row); 
									    }
									    sql_add = "insert into tb_game_matrix (game_tree_id,matrix_row,matrix_column,strategy_id) values ("
									    		  +String.valueOf(game_tree_id)+","+ String.valueOf(i)+","+String.valueOf(j)+","+String.valueOf(strategy_id)+")";
									    stmt.executeUpdate(sql_add);
									}
									else
									{
										String sql_add = "insert into tb_profit_set (profit_set_name,game_tree_id) values ('"
										           + matrix_value[i][j].trim()+"',"+String.valueOf(game_tree_id)+")";
										int row=stmt.executeUpdate (sql_add,Statement.RETURN_GENERATED_KEYS);  
										ResultSet rs_profit = stmt.getGeneratedKeys ();
										int profit_set_id=0;
										if (rs_profit.next()) {  
											profit_set_id = rs_profit.getInt(row); 
										}
										String[] profit_group = matrix_value[i][j].trim().split(",|，");
									    for (int k = 0 ; k <profit_group.length ; k++ ) 
									    {				
								    		int strategy_id=0;
								    		int actor_id=0;
								    		String matrix_query="";
									    	if(k==0)
									    	{
									    		matrix_query = "select * from tb_game_matrix where game_tree_id="+String.valueOf(game_tree_id)+" and matrix_row="
													               +String.valueOf(i)+" and matrix_column=0";
												actor_id=actor_1_id;
									    	}
									    	else if(k==1)
									    	{
									    		matrix_query = "select * from tb_game_matrix where game_tree_id="+String.valueOf(game_tree_id)
									    		               +" and matrix_row=0 and matrix_column="+String.valueOf(j);
										        actor_id=actor_2_id;
									    	}
									    	ResultSet rs_matrix = stmt.executeQuery(matrix_query);
											if (rs_matrix.next())
											{
												strategy_id = rs_matrix.getInt("strategy_id");
											}
									    	sql_add = "insert into tb_profit_group (profit_set_id,actor_id,strategy_id,profit_value) values ("+String.valueOf(profit_set_id)+","
											          + String.valueOf(actor_id)+","+String.valueOf(strategy_id)+","+profit_group[k]+")";
											stmt.executeUpdate(sql_add);
									    } 
										sql_add = "insert into tb_game_matrix (game_tree_id,matrix_row,matrix_column,profit_set_id) values ("
										    	  +String.valueOf(game_tree_id)+","+ String.valueOf(i)+","+String.valueOf(j)+","+String.valueOf(profit_set_id)+")";
										stmt.executeUpdate(sql_add);
									}
								}
				    		}
				    	}
				    }
				    //���沩��������Ϣ
				    String sql_update = "update tb_game_tree set is_complete=1 where game_tree_id=" + String.valueOf(game_tree_id);
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
					//ɾ��ԭ�н��
					String sql_update = "update tb_profit_set set pure_probability=0, mix_probability=0 where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql_update);
					
					sql_update = "update tb_game_tree set is_result=0 where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql_update);
					
					String sql_delete = "delete from tb_result where game_tree_id=" + game_tree_id;
					stmt.executeUpdate(sql_delete);
					//���߷��󴿲��Խ�		
					matrix_compute_table compute_table[][] = new matrix_compute_table[3][3];
					for(int i=0;i<3;i++)
					{
						for(int j=0;j<3;j++)
						{
							compute_table[i][j] = new matrix_compute_table();
						}
					}
					String sql = "select * from vi_matrix_compute where game_tree_id="+game_tree_id;
					ResultSet rs = stmt.executeQuery(sql);
					//��ȡ���ľ���
					while (rs.next())
					{
						int profit_set_id = rs.getInt("profit_set_id");
						int profit_value = rs.getInt("profit_value");
						int actor_id = rs.getInt("actor_id");
						int strategy_id = rs.getInt("strategy_id");
						int matrix_row = rs.getInt("matrix_row");
						int matrix_column = rs.getInt("matrix_column");
						int actor_detail = rs.getInt("actor_detail");
						compute_table[matrix_row-1][matrix_column-1].profit_set_id = profit_set_id;						
						if(actor_detail == 1)
						{
							compute_table[matrix_row-1][matrix_column-1].actor_1_id = actor_id;
							compute_table[matrix_row-1][matrix_column-1].actor_1_value = profit_value;
							compute_table[matrix_row-1][matrix_column-1].actor_1_strategy_id = strategy_id;
						}
						else if(actor_detail == 2)
						{
							compute_table[matrix_row-1][matrix_column-1].actor_2_id = actor_id;
							compute_table[matrix_row-1][matrix_column-1].actor_2_value = profit_value;
							compute_table[matrix_row-1][matrix_column-1].actor_2_strategy_id = strategy_id;
						}
						compute_table[matrix_row-1][matrix_column-1].is_not_null = true;
					}
					//���ķ�1����
					for(int j=0;j<3;j++)
					{
						int max_value = compute_table[0][j].actor_1_value;
						for(int i=0;i<3;i++)
						{
							if(compute_table[i][j].is_not_null == true)
							{
								if(compute_table[i][j].actor_1_value > max_value)
								{
									max_value = compute_table[i][j].actor_1_value;
								}
							}
						}
						for(int i=0;i<3;i++)
						{
							if(compute_table[i][j].is_not_null == true)
							{
								if(compute_table[i][j].actor_1_value == max_value)
								{
									compute_table[i][j].actor_1_max = true;
								}
							}
						}
					}
					//���ķ�2����
					for(int i=0;i<3;i++)
					{
						int max_value = compute_table[i][0].actor_2_value;
						for(int j=0;j<3;j++)
						{
							if(compute_table[i][j].is_not_null == true)
							{
								if(compute_table[i][j].actor_2_value > max_value)
								{
									max_value = compute_table[i][j].actor_2_value;
								}
							}
						}
						for(int j=0;j<3;j++)
						{
							if(compute_table[i][j].is_not_null == true)
							{
								if(compute_table[i][j].actor_2_value == max_value)
								{
									compute_table[i][j].actor_2_max = true;
								}
							}
						}
					}
                    
					ArrayList<matrix_compute_table> result_list= new ArrayList<matrix_compute_table>();   
					int result_number =0;
					for(int i=0;i<3;i++)
					{
						for(int j=0;j<3;j++)
						{
							if(compute_table[i][j].is_not_null == true)
							{
								if(compute_table[i][j].actor_1_max == true &&  compute_table[i][j].actor_2_max == true)
								{
									
									result_list.add(compute_table[i][j]);
								}
							}
						}
					}
					for (matrix_compute_table rs_item:result_list)
					{
						//���洿���Խ�
					    sql_update = "update tb_profit_set set pure_probability=1 where profit_set_id=" + rs_item.profit_set_id;
						stmt.executeUpdate(sql_update);
						
						String sql_query ="select * from tb_profit_group where profit_set_id=" + rs_item.profit_set_id;
						ResultSet rs_result = stmt.executeQuery(sql_query);
						
						Statement stmt_new;
						stmt_new = connect.createStatement();
						
						while(rs_result.next())
						{
							String act_id = rs_result.getString("actor_id");
							String strategy_id = rs_result.getString("strategy_id");
							
							String sql_add = "insert into tb_result (game_tree_id,actor_id,strategy_id,probability,nash_equilibrium_type) values ("
							    	  +game_tree_id+","+ act_id+","+strategy_id+",1,1)";
							stmt_new.executeUpdate(sql_add);
						}
						
						sql_update = "update tb_game_tree set is_result=1 where game_tree_id=" + game_tree_id;
						stmt.executeUpdate(sql_update);
						
						btn_Gametree_compute.setEnabled(true);
						btn_Gametree_result.setEnabled(true);
					}
					
					if(compute_table[0][2].is_not_null == false && compute_table[2][0].is_not_null == false)
					{
						//���ά�����ϲ�����ʲ����
						boolean mix_result = false;
						int actor_1_up = compute_table[1][1].actor_2_value-compute_table[1][0].actor_2_value;
						int actor_1_down = compute_table[0][0].actor_2_value - compute_table[0][1].actor_2_value - compute_table[1][0].actor_2_value + compute_table[1][1].actor_2_value;
						float actor_1_probability;
						if(actor_1_down != 0)
						{
							actor_1_probability = (float)actor_1_up /actor_1_down;
							BigDecimal b = new BigDecimal(actor_1_probability);  
							actor_1_probability = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
							if(actor_1_probability > 0 && actor_1_probability < 1)
							{
								for(int j=0;j<2;j++)
								{
									compute_table[0][j].actor_1_probability = actor_1_probability;
									compute_table[1][j].actor_1_probability = 1 - actor_1_probability;
								}
								mix_result = true;
							}
						}
						
						if(mix_result == true)
						{
							int actor_2_up = compute_table[1][1].actor_1_value-compute_table[0][1].actor_1_value;
							int actor_2_down = compute_table[0][0].actor_1_value - compute_table[0][1].actor_1_value - compute_table[1][0].actor_1_value + compute_table[1][1].actor_1_value;
							float actor_2_probability;
							if(actor_2_down != 0)
							{
								actor_2_probability =  (float)actor_2_up /actor_2_down;
								BigDecimal b = new BigDecimal(actor_2_probability);  
								actor_2_probability = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
								if(actor_2_probability > 0 && actor_2_probability < 1)
								{
									for(int i=0;i<2;i++)
									{
										compute_table[i][0].actor_2_probability = actor_2_probability;
										compute_table[i][1].actor_2_probability = 1 - actor_2_probability;
									}
								}
								else
									mix_result = false;
							}
							else
								mix_result = false;
						}
						
						
						//�����ϲ��Խ�
						if(mix_result == true)
						{
							for(int i=0;i<2;i++)
							{
								for(int j=0;j<2;j++)
								{
									float profit_set_value = compute_table[i][j].actor_1_probability * compute_table[i][j].actor_2_probability;
								    sql_update = "update tb_profit_set set mix_probability="+ String.valueOf(profit_set_value)+" where profit_set_id=" 
									                    + compute_table[i][j].profit_set_id;
									stmt.executeUpdate(sql_update);
								}
							}
	                        for(int i=0;i<2;i++)
	                        {
								String act_id = String.valueOf(compute_table[i][0].actor_1_id);
								String strategy_id =  String.valueOf(compute_table[i][0].actor_1_strategy_id);
								String probability = String.valueOf(compute_table[i][0].actor_1_probability);
								String sql_add = "insert into tb_result (game_tree_id,actor_id,strategy_id,probability,nash_equilibrium_type) values ("
								    	  +game_tree_id+","+ act_id+","+strategy_id+","+ probability +",2)";
								stmt.executeUpdate(sql_add);
	                        }
	                        for(int j=0;j<2;j++)
	                        {
								String act_id = String.valueOf(compute_table[0][j].actor_2_id);
								String strategy_id =  String.valueOf(compute_table[0][j].actor_2_strategy_id);
								String probability = String.valueOf(compute_table[0][j].actor_2_probability);
								String sql_add = "insert into tb_result (game_tree_id,actor_id,strategy_id,probability,nash_equilibrium_type) values ("
								    	  +game_tree_id+","+ act_id+","+strategy_id+","+ probability +",2)";
								stmt.executeUpdate(sql_add);
	                        }
	                        
	                        sql_update = "update tb_game_tree set is_result=1 where game_tree_id=" + game_tree_id;
							stmt.executeUpdate(sql_update);
						}
												
						btn_Gametree_compute.setEnabled(true);
						btn_Gametree_result.setEnabled(true);
					}

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
		
		JPanel matrix_panel = new JPanel();
		matrix_panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_matrix_panel = new GridBagConstraints();
		gbc_matrix_panel.insets = new Insets(0, 0, 5, 5);
		gbc_matrix_panel.fill = GridBagConstraints.BOTH;
		gbc_matrix_panel.gridx = 0;
		gbc_matrix_panel.gridy = 6;
		gbc_matrix_panel.gridwidth=5;
		gbc_matrix_panel.gridheight=10;
		add(matrix_panel, gbc_matrix_panel);
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
		gbc_tb_Gametree_result.gridy = 6;
		JScrollPane sp_Gametree_result = new JScrollPane(tb_Gametree_result);
		add(sp_Gametree_result, gbc_tb_Gametree_result);
		
		String[] col_Gametree_profit = {"博弈方名称","策略名称","概率","纳什均衡类型"};
		group_model   =   new   DefaultTableModel(new Object[][] {},col_Gametree_profit);	
		tb_Gametree_profit = new JTable(group_model);
		tb_Gametree_profit.setPreferredScrollableViewportSize(new Dimension(200,25));//ע����У��������ܿ�������
		GridBagConstraints gbc_tb_Gametree_profit = new GridBagConstraints();
		gbc_tb_Gametree_profit.insets = new Insets(0, 0, 5, 5);
		gbc_tb_Gametree_profit.gridheight = 5;
		gbc_tb_Gametree_profit.fill = GridBagConstraints.BOTH;
		gbc_tb_Gametree_profit.gridwidth =3;
		gbc_tb_Gametree_profit.gridx = 5;
		gbc_tb_Gametree_profit.gridy = 11;
		JScrollPane sp_Gametree_profit = new JScrollPane(tb_Gametree_profit);
		add(sp_Gametree_profit, gbc_tb_Gametree_profit);
		
		
	}
	
	public static String CheckBasic()
	{
		String result="Correct";
		
		if(tf_Basic_name.getText().trim().equals(""))
		{
			result = "博弈问题名称不能为空";
			return result;
		}
		else
		{
			try 
			{
				Statement stmt= connect.createStatement();
				String sql = "select * from tb_game_tree where game_tree_name='"+tf_Basic_name.getText().trim()+"' and game_tree_id !=" + game_tree_id;
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next())//��ʾ��������Ϣ
				{
					result = "博弈问题名称不能重复";
					return result;
				}
				if(tf_Basic_area.getText().trim().equals(""))
				{
					result = "发生地点不能为空";
					return result;
				}
				if(tf_Basic_discusser.getText().trim().equals(""))
				{
					result = "研讨主体不能为空";
					return result;
				}
				if(tf_Basic_time.getText().trim().equals(""))
				{
					result = "发生时间不能为空";
					return result;
				}
				else
				{
					try 
					{
						DateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");											
					    java.util.Date start_time = sdf.parse(tf_Basic_time.getText().trim()+" 00:00:00");					   
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						result = "发生时间格式应形如'2016-1-1'";
						return result;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return result;
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
		GameMatrix  strategy_game_panel = new  GameMatrix();
		
		JFrame frame = new JFrame(); 
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE ); 
		 strategy_game_panel.setOpaque(true); 
		 frame.setSize(new Dimension(1230, 746)); 
		 frame.setContentPane(strategy_game_panel); 
		 frame.setVisible(true); 
		 
		 connectdatabase();

	}*/
}
