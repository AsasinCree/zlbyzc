package zlbyzc.sub3.duozhunze;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JButton;



import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CopyOfchaxun extends JFrame implements ActionListener{
	private JTextField textField;
	private JTable chaxunliebiao = new JTable();
	private JTextField yeshukuang = new JTextField();;
	private JTextField chaxunguanjianzi = new JTextField();
	JLabel lblNewLabel_3 = new JLabel("页");
	JLabel label = new JLabel("查找决策 ");
	JButton chaxunanniu = new JButton("查找");
	JScrollPane zzjgscrollPane = new JScrollPane();
	JButton shangyiye = new JButton("上一页");
	JLabel zzjglabel_1 = new JLabel("最佳方案为：");
	JLabel lblNewLabel_2 = new JLabel("第");
	JButton xiayiye = new JButton("下一页");
	JLabel zuijiafangan = new JLabel("                 ");
	JLabel labeldf = new JLabel("              ");
	JLabel zzjglabel_10 = new JLabel("方案详情：");
	JTextArea fanganxiangqing = new JTextArea();
	private String keyword="";
	private int i=0;
	private int length=0;
	private int yeshu=1;
	
	private CopyOfchaxun(){
		getContentPane().setFont(new Font("宋体", Font.PLAIN, 16));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 683);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 136, 72, -31, 23, 29, 28, 29, 71, 140, 69, 0, 20};
		gridBagLayout.rowHeights = new int[]{20, 26, 32, 0, 16, 191, 42, 0, 0, 0, 143, 51, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		label.setFont(new Font("宋体", Font.BOLD, 24));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 13;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		getContentPane().add(label, gbc_label);
		
		
		GridBagConstraints gbc_chaxunguanjianzi = new GridBagConstraints();
		gbc_chaxunguanjianzi.insets = new Insets(0, 0, 5, 5);
		gbc_chaxunguanjianzi.fill = GridBagConstraints.HORIZONTAL;
		gbc_chaxunguanjianzi.gridx = 1;
		gbc_chaxunguanjianzi.gridy = 3;
		getContentPane().add(chaxunguanjianzi, gbc_chaxunguanjianzi);
		chaxunguanjianzi.setColumns(10);
		
		
		chaxunanniu.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_chaxunanniu = new GridBagConstraints();
		gbc_chaxunanniu.fill = GridBagConstraints.HORIZONTAL;
		gbc_chaxunanniu.insets = new Insets(0, 0, 5, 5);
		gbc_chaxunanniu.gridx = 2;
		gbc_chaxunanniu.gridy = 3;
		getContentPane().add(chaxunanniu, gbc_chaxunanniu);
		
		
		GridBagConstraints gbc_zzjgscrollPane = new GridBagConstraints();
		gbc_zzjgscrollPane.fill = GridBagConstraints.BOTH;
		gbc_zzjgscrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_zzjgscrollPane.gridwidth = 11;
		gbc_zzjgscrollPane.gridx = 1;
		gbc_zzjgscrollPane.gridy = 5;
		getContentPane().add(zzjgscrollPane, gbc_zzjgscrollPane);
		
		
		chaxunliebiao.setFont(new Font("宋体", Font.PLAIN, 16));
		chaxunliebiao.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\u51B3\u7B56\u540D", "\u4E00\u81F4\u5EA6\u4F10\u503C ", "\u4E00\u81F4\u5EA6\u503C", "\u6700\u4F73\u65B9\u6848"
			}
		));
		chaxunliebiao.getColumnModel().getColumn(1).setPreferredWidth(94);
		chaxunliebiao.getColumnModel().getColumn(3).setPreferredWidth(101);
		zzjgscrollPane.setViewportView(chaxunliebiao);
		
		
		GridBagConstraints gbc_shangyiye = new GridBagConstraints();
		gbc_shangyiye.insets = new Insets(0, 0, 5, 5);
		gbc_shangyiye.gridx = 3;
		gbc_shangyiye.gridy = 6;
		getContentPane().add(shangyiye, gbc_shangyiye);
		
		
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 4;
		gbc_lblNewLabel_2.gridy = 6;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		
		yeshukuang.setText("1");
		GridBagConstraints gbc_yeshukuang = new GridBagConstraints();
		gbc_yeshukuang.fill = GridBagConstraints.HORIZONTAL;
		gbc_yeshukuang.insets = new Insets(0, 0, 5, 5);
		gbc_yeshukuang.gridx = 5;
		gbc_yeshukuang.gridy = 6;
		getContentPane().add(yeshukuang, gbc_yeshukuang);
		yeshukuang.setColumns(10);
		
		
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 6;
		gbc_lblNewLabel_3.gridy = 6;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		
		GridBagConstraints gbc_xiayiye = new GridBagConstraints();
		gbc_xiayiye.insets = new Insets(0, 0, 5, 5);
		gbc_xiayiye.gridx = 7;
		gbc_xiayiye.gridy = 6;
		getContentPane().add(xiayiye, gbc_xiayiye);
		
		
		zzjglabel_1.setForeground(Color.BLACK);
		zzjglabel_1.setFont(new Font("宋体", Font.BOLD, 18));
		GridBagConstraints gbc_zzjglabel_1 = new GridBagConstraints();
		gbc_zzjglabel_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_zzjglabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_zzjglabel_1.gridx = 1;
		gbc_zzjglabel_1.gridy = 7;
		getContentPane().add(zzjglabel_1, gbc_zzjglabel_1);
		
		
		GridBagConstraints gbc_zuijiafangan = new GridBagConstraints();
		gbc_zuijiafangan.gridwidth = 4;
		gbc_zuijiafangan.insets = new Insets(0, 0, 5, 5);
		gbc_zuijiafangan.gridx = 2;
		gbc_zuijiafangan.gridy = 7;
		getContentPane().add(zuijiafangan, gbc_zuijiafangan);
		
		
		GridBagConstraints gbc_labeldf = new GridBagConstraints();
		gbc_labeldf.insets = new Insets(0, 0, 5, 5);
		gbc_labeldf.gridx = 1;
		gbc_labeldf.gridy = 8;
		getContentPane().add(labeldf, gbc_labeldf);
		
		
		zzjglabel_10.setForeground(Color.BLACK);
		zzjglabel_10.setFont(new Font("宋体", Font.BOLD, 18));
		GridBagConstraints gbc_zzjglabel_10 = new GridBagConstraints();
		gbc_zzjglabel_10.anchor = GridBagConstraints.NORTHWEST;
		gbc_zzjglabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_zzjglabel_10.gridx = 1;
		gbc_zzjglabel_10.gridy = 9;
		getContentPane().add(zzjglabel_10, gbc_zzjglabel_10);
		
		
		fanganxiangqing.setEditable(false);
		fanganxiangqing.setForeground(Color.BLACK);
		fanganxiangqing.setColumns(10);
		fanganxiangqing.setRows(10);
		fanganxiangqing.setFont(new Font("宋体", Font.PLAIN, 16));
		GridBagConstraints gbc_fanganxiangqing = new GridBagConstraints();
		gbc_fanganxiangqing.gridheight = 2;
		gbc_fanganxiangqing.fill = GridBagConstraints.BOTH;
		gbc_fanganxiangqing.insets = new Insets(0, 0, 5, 5);
		gbc_fanganxiangqing.gridwidth = 8;
		gbc_fanganxiangqing.gridx = 2;
		gbc_fanganxiangqing.gridy = 9;
		getContentPane().add(fanganxiangqing, gbc_fanganxiangqing);
		
		this.addListener();
		this.setVisible(true);
	}
	public void addListener()
	{	chaxunanniu.addActionListener(this);
		shangyiye.addActionListener(this);
		chaxunliebiao.addMouseListener(new MouseAdapter()
			{ 
				 public void mouseClicked(MouseEvent e) 
				 	{ if(e.getClickCount() == 2)
									  //实现双击 
				 		{ 
							int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
							int  col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置 String cellVal=(String)(tbModel.getValueAt(row,col)); //获得点击单元格数据 txtboxRow.setText((row+1)+""); txtboxCol.setText((col+1)+""); 
							Dao dao=new Dao();
							Descition d=(Descition) dao.queryDescitionbyName2((String) chaxunliebiao.getValueAt(row, 0));
							zuijiafangan.setText(d.getVarScheme());
							zzjglabel_1.setVisible(true);
							zuijiafangan.setVisible(true);
							zzjglabel_10.setVisible(true);
							fanganxiangqing.setText(((Scheme) dao.queryscheme(d.getId(),d.getVarScheme())).getDescripition()+"");
							fanganxiangqing.setVisible(true);
				 		} else return; } });
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==chaxunanniu){
			Dao dao=new Dao();
			keyword=chaxunguanjianzi.getText();
			length=dao.querydescition(keyword);
			this.settable();
		}
		if(e.getSource()==shangyiye&&i>11){
			i=i-20;
			Dao dao=new Dao();
			yeshu--;
			this.settable();
		}
		if(e.getSource()==xiayiye&&i<length){
			Dao dao=new Dao();
			keyword=chaxunguanjianzi.getText();
			length=dao.querydescition(keyword);
			yeshu++;
			this.settable();
		}
	}
	public void settable(){
		Dao dao=new Dao();
		List<Descition> s=dao.querydescition(keyword, i);
		i=i+10;
		Vector v_data=new Vector();
		for(Descition d:s){
			Vector temp=new Vector();//创建临时变量
			temp.add(d.getName());
			temp.add(d.getConsistency());
			temp.add(d.getVarLastConsistency());
			temp.add(d.getVarScheme());
			v_data.add(temp);
		}
		Vector v_head=new Vector();
		v_head.add("决策名");
		v_head.add("一致度伐值");
		v_head.add("一致度值");
		v_head.add("最佳方案");
		chaxunliebiao.setModel(new DefaultTableModel(
				v_data,v_head
			) {
				Class[] columnTypes = new Class[] {
						Object.class,Object.class,Object.class,Object.class
				};
				 @Override
		            public boolean isCellEditable(int row, int column) {
					 return false;
		            }
				 public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					} 
			});
//		lxzjtable.getValueAt(row, column);
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		// tcr.setHorizontalAlignment(JLabel.CENTER);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		chaxunliebiao.setDefaultRenderer(Object.class, tcr);	
		((DefaultTableModel)chaxunliebiao.getModel()).fireTableStructureChanged();//更新显示
		//最后编辑数据可录入
		//chaxunliebiao.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	}
	public static void main(String[] args) {
		new CopyOfchaxun();
	}
}
