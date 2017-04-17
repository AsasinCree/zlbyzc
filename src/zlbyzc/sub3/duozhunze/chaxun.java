package zlbyzc.sub3.duozhunze;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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

import zlbyzc.sub3.sub3inFrame;



import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class chaxun extends sub3inFrame implements ActionListener{
	private JPanel contentPane= new JPanel();				//子容器
	private JTable chaxunliebiao= new JTable();
	private JScrollPane zzjgscrollPane = new JScrollPane();
	private JLabel zzjglabel_1 = new JLabel("最佳方案为：");
	private JLabel zuijiafangan = new JLabel("");
	private JLabel zzjglabel_10 = new JLabel("方案详情：");
	private JTextField chaxunguanjianzi = new JTextField();;
	private final JTextArea fanganxiangqing = new JTextArea();
	private JButton chaxunanniu = new JButton("查找");
	private String keyword="";
	private int i=0;
	private int length=0;
	private int yeshu=1;
	private JTextField yeshukuang = new JTextField();;
	private JButton shangyiye = new JButton("上一页 ");
	private JButton xiayiye = new JButton("下一页");
	private chaxun(){
		Color bg=new Color(226,226,226);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 683);
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setForeground(SystemColor.desktop);
		contentPane.setBorder(new EmptyBorder(100, 5, 5, 5));
		contentPane.setBackground(bg);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		zzjgscrollPane.setBounds(47, 149, 727, 163);
		getContentPane().add(zzjgscrollPane);
		chaxunliebiao.setFont(new Font("宋体", Font.PLAIN, 14));
		
		
		chaxunliebiao.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"决策名", "一致度伐值", "一致度值","最佳方案"
			}
		));
		zzjgscrollPane.setViewportView(chaxunliebiao);
		
		
		zzjglabel_1.setForeground(Color.BLACK);
		zzjglabel_1.setFont(new Font("宋体", Font.BOLD, 18));
		zzjglabel_1.setBounds(47, 384, 132, 34);
		zzjglabel_1.hide();
		getContentPane().add(zzjglabel_1);
		
		
		zuijiafangan.setForeground(Color.BLACK);
		zuijiafangan.setFont(new Font("宋体", Font.BOLD, 18));
		zuijiafangan.setBounds(189, 385, 287, 34);
		zuijiafangan.hide();
		getContentPane().add(zuijiafangan);
		
	
		zzjglabel_10.setForeground(Color.BLACK);
		zzjglabel_10.setFont(new Font("宋体", Font.BOLD, 18));
		zzjglabel_10.setBounds(52, 442, 113, 41);
		getContentPane().add(zzjglabel_10);
		zzjglabel_10.hide();
		contentPane.setLayout(null);
		
		
		chaxunguanjianzi.setBounds(50, 113, 189, 21);
		contentPane.add(chaxunguanjianzi);
		chaxunguanjianzi.setColumns(10);
		
		
		chaxunanniu.setFont(new Font("宋体", Font.PLAIN, 14));
		chaxunanniu.setBounds(262, 112, 93, 23);
		contentPane.add(chaxunanniu);
		fanganxiangqing.setFont(new Font("宋体", Font.PLAIN, 14));
		fanganxiangqing.setRows(10);
		fanganxiangqing.setBounds(176, 454, 598, 156);
		fanganxiangqing.hide();
		contentPane.add(fanganxiangqing);
		
		JLabel label = new JLabel("查询决策");
		label.setForeground(Color.BLACK);
		label.setFont(new Font("宋体", Font.BOLD, 20));
		label.setBounds(368, 21, 132, 34);
		contentPane.add(label);
		
		
		shangyiye.setFont(new Font("宋体", Font.PLAIN, 14));
		shangyiye.setBounds(280, 327, 93, 23);
		contentPane.add(shangyiye);
		
		yeshukuang.setColumns(10);
		yeshukuang.setBounds(383, 328, 76, 21);
		contentPane.add(yeshukuang);
		yeshukuang.setText(yeshu+"");
		yeshukuang.enable(false);
		
		xiayiye.setFont(new Font("宋体", Font.PLAIN, 14));
		xiayiye.setBounds(469, 327, 93, 23);
		contentPane.add(xiayiye);
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
							fanganxiangqing.setText((String) dao.queryscheme(d.getId(),d.getVarScheme()));
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
		new chaxun();
	}
}
