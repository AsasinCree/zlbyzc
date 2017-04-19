package zlbyzc.sub3.duozhunze;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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

public class CopyOfchaxun extends sub3inFrame implements ActionListener{
	private JTextField textField;
	private int frist=0;
	private JTable chaxunliebiao = new JTable();
	private JTextField yeshukuang = new JTextField();;
	private JTextField chaxunguanjianzi = new JTextField();
	JLabel lblNewLabel_3 = new JLabel("页");
	JLabel label = new JLabel("查找决策 ");
	JButton chaxunanniu = new JButton("查找");
	JScrollPane zzjgscrollPane = new JScrollPane();
	JButton shangyiye = new JButton("上一页");
	JLabel lblNewLabel_2 = new JLabel("第");
	JButton xiayiye = new JButton("下一页");
	JLabel zzjglabel_10 = new JLabel("方案详情：");
	JTextArea fanganxiangqing = new JTextArea();
	private String keyword="";
	private int i=0;
	private int length=0;
	private int yeshu=1;
	private JPanel con=new JPanel();
	private JPanel con2=new JPanel();
	DefaultListModel listModel = new DefaultListModel();
	DefaultListModel listModel2 = new DefaultListModel();
	DefaultListModel listModel3 = new DefaultListModel();
	DefaultListModel listModel4 = new DefaultListModel();
	DefaultListModel listModel5 = new DefaultListModel();
	DefaultListModel listModel6 = new DefaultListModel();
	private final JButton button = new JButton("\u5220\u9664");
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private final JLabel label_1 = new JLabel("\u65B9\u6848\u5217\u8868");
	private final JList list = new JList(listModel);
	private final JLabel label_2 = new JLabel("\u51C6\u5219\u5217\u8868");
	private final JScrollPane scrollPane_2 = new JScrollPane();
	private final JList list_1 = new JList(listModel2);
	private final JLabel label_3 = new JLabel("\u53C2\u4E0E\u4E13\u5BB6");
	private final JScrollPane scrollPane_3 = new JScrollPane();
	private final JList list_2 = new JList(listModel3);
	private final JLabel label_4 = new JLabel("\u4E13\u5BB6\u4FE1\u606F\uFF1A");
	private final JScrollPane scrollPane_4 = new JScrollPane();
	private final JList list_3 = new JList(listModel4);
	private final JList list_4= new JList(listModel5);//专家id
	private final JList list_5= new JList(listModel6);//方案id
	private final JScrollPane scrollPane_5 = new JScrollPane();
	private int juece=0;
	public CopyOfchaxun(){
		super("多准则决策",true,true,true,true);
		con=new JPanel();
        con.setBorder(new EmptyBorder(5,5,5,5));
        con.setLayout(new BorderLayout(0,0));
        this.setContentPane(con);
        JScrollPane scrollPane=new JScrollPane();
        con.add(scrollPane,BorderLayout.CENTER);
       // textArea=new JTextArea();
        //scrollPane.add(textArea); 
        scrollPane.setViewportView(con2);
		
        list.setVisibleRowCount(6);//设置列表一打开时所能看到的数据项个数。 
		   //t.setSize(12,12);
		list.setFixedCellHeight(40);//设置一个固定值，将用于列表中每个单元的高度。如果 height 为 -1，(此属性的默认值为 -1。)
		list.setFixedCellWidth(100);//设置一个固定值，将用于列表中每个单元的宽度。如果 width 为 -1，(此属性的默认值为 -1。)
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
        list_1.setVisibleRowCount(6);//设置列表一打开时所能看到的数据项个数。 
		   //t.setSize(12,12);
		list_1.setFixedCellHeight(40);//设置一个固定值，将用于列表中每个单元的高度。如果 height 为 -1，(此属性的默认值为 -1。)
		list_1.setFixedCellWidth(100);//设置一个固定值，将用于列表中每个单元的宽度。如果 width 为 -1，(此属性的默认值为 -1。)
		list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		 list_2.setVisibleRowCount(6);//设置列表一打开时所能看到的数据项个数。 
		   //t.setSize(12,12);
		list_2.setFixedCellHeight(40);//设置一个固定值，将用于列表中每个单元的高度。如果 height 为 -1，(此属性的默认值为 -1。)
		list_2.setFixedCellWidth(100);//设置一个固定值，将用于列表中每个单元的宽度。如果 width 为 -1，(此属性的默认值为 -1。)
		list_2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		 list_3.setVisibleRowCount(6);//设置列表一打开时所能看到的数据项个数。 
		   //t.setSize(12,12);
		list_3.setFixedCellHeight(40);//设置一个固定值，将用于列表中每个单元的高度。如果 height 为 -1，(此属性的默认值为 -1。)
		list_3.setFixedCellWidth(100);//设置一个固定值，将用于列表中每个单元的宽度。如果 width 为 -1，(此属性的默认值为 -1。)
		list_3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		con2.setFont(new Font("宋体", Font.PLAIN, 16));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1311, 810);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 136, 53, -31, 0, 23, 38, 41, 29, 28, 64, 44, 29, 38, 41, 32};
		gridBagLayout.rowHeights = new int[]{20, 26, 32, 0, 16, 191, 42, 0, 32, 0, 0, 143, 51, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		con2.setLayout(gridBagLayout);
		
		
		
		label.setFont(new Font("宋体", Font.BOLD, 24));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 16;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		con2.add(label, gbc_label);
		
		
		GridBagConstraints gbc_chaxunguanjianzi = new GridBagConstraints();
		gbc_chaxunguanjianzi.insets = new Insets(0, 0, 5, 5);
		gbc_chaxunguanjianzi.fill = GridBagConstraints.HORIZONTAL;
		gbc_chaxunguanjianzi.gridx = 1;
		gbc_chaxunguanjianzi.gridy = 3;
		con2.add(chaxunguanjianzi, gbc_chaxunguanjianzi);
		
		chaxunguanjianzi.setColumns(10);
		
		
		chaxunanniu.setFont(new Font("宋体", Font.PLAIN, 15));
		GridBagConstraints gbc_chaxunanniu = new GridBagConstraints();
		gbc_chaxunanniu.fill = GridBagConstraints.HORIZONTAL;
		gbc_chaxunanniu.insets = new Insets(0, 0, 5, 5);
		gbc_chaxunanniu.gridx = 2;
		gbc_chaxunanniu.gridy = 3;
		con2.add(chaxunanniu, gbc_chaxunanniu);
		
		
		GridBagConstraints gbc_zzjgscrollPane = new GridBagConstraints();
		gbc_zzjgscrollPane.fill = GridBagConstraints.BOTH;
		gbc_zzjgscrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_zzjgscrollPane.gridwidth = 15;
		gbc_zzjgscrollPane.gridx = 1;
		gbc_zzjgscrollPane.gridy = 5;
		con2.add(zzjgscrollPane, gbc_zzjgscrollPane);
		zzjgscrollPane.setPreferredSize(new Dimension(100, 100));
		
		
		chaxunliebiao.setFont(new Font("宋体", Font.PLAIN, 16));
		chaxunliebiao.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
				},
				new String[] {
					"\u51B3\u7B56\u540D", "\u4E00\u81F4\u5EA6\u4F10\u503C ", "\u4E00\u81F4\u5EA6\u503C", "\u6700\u4F73\u65B9\u6848", ""
				}
			) {
				Class[] columnTypes = new Class[] {
					Object.class, Object.class, Object.class, Object.class, Boolean.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		chaxunliebiao.getColumnModel().getColumn(1).setPreferredWidth(94);
		chaxunliebiao.getColumnModel().getColumn(3).setPreferredWidth(101);
		zzjgscrollPane.setViewportView(chaxunliebiao);
		
		
		GridBagConstraints gbc_shangyiye = new GridBagConstraints();
		gbc_shangyiye.fill = GridBagConstraints.HORIZONTAL;
		gbc_shangyiye.insets = new Insets(0, 0, 5, 5);
		gbc_shangyiye.gridx = 6;
		gbc_shangyiye.gridy = 6;
		con2.add(shangyiye, gbc_shangyiye);
		
		
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 7;
		gbc_lblNewLabel_2.gridy = 6;
		con2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		yeshukuang.setHorizontalAlignment(SwingConstants.CENTER);
		yeshukuang.setEditable(false);
		
		
		yeshukuang.setText("1");
		GridBagConstraints gbc_yeshukuang = new GridBagConstraints();
		gbc_yeshukuang.fill = GridBagConstraints.HORIZONTAL;
		gbc_yeshukuang.insets = new Insets(0, 0, 5, 5);
		gbc_yeshukuang.gridx = 8;
		gbc_yeshukuang.gridy = 6;
		con2.add(yeshukuang, gbc_yeshukuang);
		yeshukuang.setColumns(3);
		
		
		lblNewLabel_3.setFont(new Font("宋体", Font.PLAIN, 16));
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 9;
		gbc_lblNewLabel_3.gridy = 6;
		con2.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		
		GridBagConstraints gbc_xiayiye = new GridBagConstraints();
		gbc_xiayiye.insets = new Insets(0, 0, 5, 5);
		gbc_xiayiye.gridx = 10;
		gbc_xiayiye.gridy = 6;
		con2.add(xiayiye, gbc_xiayiye);
		
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 7;
		label_1.setFont(new Font("宋体", Font.PLAIN, 14));
		con2.add(label_1, gbc_label_1);
		
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 10;
		gbc_label_2.gridy = 7;
		label_2.setFont(new Font("宋体", Font.PLAIN, 14));
		con2.add(label_2, gbc_label_2);
		
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.WEST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 14;
		gbc_label_3.gridy = 7;
		label_3.setFont(new Font("宋体", Font.PLAIN, 14));
		con2.add(label_3, gbc_label_3);
		
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 8;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 8;
		con2.add(scrollPane_1, gbc_scrollPane_1);
		
		scrollPane_1.setColumnHeaderView(list);
		
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridwidth = 3;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 10;
		gbc_scrollPane_2.gridy = 8;
		con2.add(scrollPane_2, gbc_scrollPane_2);
		
		scrollPane_2.setColumnHeaderView(list_1);
		
		GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
		gbc_scrollPane_3.gridwidth = 2;
		gbc_scrollPane_3.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_3.gridx = 14;
		gbc_scrollPane_3.gridy = 8;
		con2.add(scrollPane_3, gbc_scrollPane_3);
		
		scrollPane_3.setColumnHeaderView(list_2);
		
		
		//zzjglabel_10.setForeground(Color.BLACK);
		zzjglabel_10.setFont(new Font("宋体", Font.BOLD, 18));
		GridBagConstraints gbc_zzjglabel_10 = new GridBagConstraints();
		gbc_zzjglabel_10.anchor = GridBagConstraints.NORTHWEST;
		gbc_zzjglabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_zzjglabel_10.gridx = 1;
		gbc_zzjglabel_10.gridy = 10;
		con2.add(zzjglabel_10, gbc_zzjglabel_10);
		
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.WEST;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 12;
		gbc_label_4.gridy = 10;
		label_4.setFont(new Font("宋体", Font.BOLD, 18));
		con2.add(label_4, gbc_label_4);
		
		GridBagConstraints gbc_scrollPane_5 = new GridBagConstraints();
		gbc_scrollPane_5.gridwidth = 10;
		gbc_scrollPane_5.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_5.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_5.gridx = 1;
		gbc_scrollPane_5.gridy = 11;
		con2.add(scrollPane_5, gbc_scrollPane_5);
		scrollPane_5.setColumnHeaderView(fanganxiangqing);
		
		fanganxiangqing.setLineWrap(true);        //激活自动换行功能 
		fanganxiangqing.setWrapStyleWord(true);            // 激活断行不断字功能
		
		
		fanganxiangqing.setEditable(false);
		fanganxiangqing.setForeground(Color.WHITE);
		fanganxiangqing.setColumns(18);
		
		fanganxiangqing.setRows(8);
		fanganxiangqing.setFont(new Font("宋体", Font.PLAIN, 16));
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 15;
		gbc_button.gridy = 4;
		con2.add(button, gbc_button);
		
		GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
		gbc_scrollPane_4.gridwidth = 4;
		gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_4.gridx = 12;
		gbc_scrollPane_4.gridy = 11;
		con2.add(scrollPane_4, gbc_scrollPane_4);
		scrollPane_4.setColumnHeaderView(list_3);
		this.addListener();
		this.settable();
		//chaxunliebiao.setRowSelectionInterval(0,0); 
		this.init();
		frist=1;
		this.setVisible(true);
	}
	public void addListener()
	{	chaxunanniu.addActionListener(this);
		shangyiye.addActionListener(this);
		xiayiye.addActionListener(this);
		button.addActionListener(this);
		chaxunliebiao.addMouseListener(new MouseAdapter()
			{ 
				 public void mouseClicked(MouseEvent e) 
				 	{ if(e.getClickCount() == 1)
									  //实现双击 
				 		{ 
				 			listModel.clear();
				 			listModel2.clear();
				 			listModel3.clear();
				 			listModel4.clear();
				 			listModel5.clear();
				 			listModel6.clear();
				 			
				 			try {
								
						    fanganxiangqing.setText("");
							int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
							int  col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置 String cellVal=(String)(tbModel.getValueAt(row,col)); //获得点击单元格数据 txtboxRow.setText((row+1)+""); txtboxCol.setText((col+1)+""); 
							Dao dao=new Dao();
							int dd=(int)chaxunliebiao.getValueAt(row, 0);
							Descition d=(Descition) dao.queryDescitionbyName2(dd);
							juece =d.getId();
							List<Scheme> sc=dao.queryschemeByNumber(d.getId());
							List<Expert> sd=dao.findExpertbyid(d.getId());
							List<Rule>sv=dao.queryruleByNumber(d.getId());
							for(Scheme c:sc){
							listModel.addElement(c.getName()+"         加权得分： "+c.getScore());
							listModel6.addElement(c.getId());
							}
							for(Expert g:sd)
								{listModel3.addElement(g.getName()+"    "); 
								 listModel5.addElement(g.getId()); }
							for(Rule h:sv){
								listModel2.addElement(h.getName()+"     权重： "+h.getWeight());
							}
							Expert hb=(Expert) dao.queryexpertByid((int)listModel5.get(0));
							listModel4.addElement("编号：  "+hb.getNumber());
							listModel4.addElement("姓名：  "+hb.getName());
							listModel4.addElement("性别：  "+hb.getSex());
							listModel4.addElement("职称：  "+hb.getTitle());
							listModel4.addElement("单位：  "+hb.getWorkUnit());
							Scheme b=(Scheme) dao.queryschemeByNumber2((int)listModel6.get(0));
							fanganxiangqing.setText("方案名： "+b.getName()+"    加权得分"+b.getScore()+"\n"+"    "+b.getDescripition());
							
				 			} catch (Exception e2) {
								// TODO: handle exception
							}	
				 		} else return; } });
		
		list.addMouseListener(new MouseAdapter()
		{ 
			 public void mouseClicked(MouseEvent e) 
			 	{ if(e.getClickCount() == 1)
								  //实现双击 
			 		{ 
			 			try {
					    fanganxiangqing.setText("");
						Dao dao=new Dao();
						int s=(int) listModel6.get(((JList)e.getSource()).getSelectedIndex());
						Scheme d=(Scheme) dao.queryschemeByNumber2(s);
						
						fanganxiangqing.setText("方案名： "+d.getName()+"   加权得分："+d.getScore()+"\n"+"    "+d.getDescripition());
						
			 			} catch (Exception e2) {
							// TODO: handle exception
						}	
			 		} else return; } });
		
		list_2.addMouseListener(new MouseAdapter()
		{ 
			 public void mouseClicked(MouseEvent e) 
			 	{ if(e.getClickCount() == 1)
								  //实现双击 
			 		{ 
			 			try {
						Dao dao=new Dao();
						int s=(int) listModel5.get(((JList)e.getSource()).getSelectedIndex());
						Expert hb=(Expert) dao.queryexpertByid(s);
						listModel4.clear();
						listModel4.addElement("编号：  "+hb.getNumber());
						listModel4.addElement("姓名：  "+hb.getName());
						listModel4.addElement("性别：  "+hb.getSex());
						listModel4.addElement("职称：  "+hb.getTitle());
						listModel4.addElement("单位：  "+hb.getWorkUnit());
						
			 			} catch (Exception e2) {
							// TODO: handle exception
						}	
			 		} else return; } });
	}
	public void init(){
//			listModel.clear();
//			listModel2.clear();
//			listModel3.clear();
//			listModel4.clear();
//			listModel5.clear();
//			listModel6.clear();
			
			try {
			
	    fanganxiangqing.setText("");
//		int row =((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
//		int  col=((JTable)e.getSource()).columnAtPoint(e.getPoint()); //获得列位置 String cellVal=(String)(tbModel.getValueAt(row,col)); //获得点击单元格数据 txtboxRow.setText((row+1)+""); txtboxCol.setText((col+1)+""); 
		Dao dao=new Dao();
		int dd=(int)chaxunliebiao.getValueAt(0, 0);
		Descition d=(Descition) dao.queryDescitionbyName2(dd);
		List<Scheme> sc=dao.queryschemeByNumber(d.getId());
		juece =d.getId();
		List<Expert> sd=dao.findExpertbyid(d.getId());
		List<Rule>sv=dao.queryruleByNumber(d.getId());
		for(Scheme c:sc){
		listModel.addElement(c.getName()+"         加权得分： "+c.getScore());
		listModel6.addElement(c.getId());
		}
		for(Expert g:sd)
			{listModel3.addElement(g.getName()+"    "); 
			 listModel5.addElement(g.getId()); }
		for(Rule h:sv){
			listModel2.addElement(h.getName()+"     权重： "+h.getWeight());
		}
		Expert hb=(Expert) dao.queryexpertByid((int)listModel5.get(0));
		listModel4.addElement("编号：  "+hb.getNumber());
		listModel4.addElement("姓名：  "+hb.getName());
		listModel4.addElement("性别：  "+hb.getSex());
		listModel4.addElement("职称：  "+hb.getTitle());
		listModel4.addElement("单位：  "+hb.getWorkUnit());
		Scheme b=(Scheme) dao.queryschemeByNumber2((int)listModel6.get(0));
		fanganxiangqing.setText("方案名： "+b.getName()+"  加权得分："+b.getScore()+"\n"+"    "+b.getDescripition());
		
			} catch (Exception e2) {
			// TODO: handle exception
		}	
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==chaxunanniu){
			Dao dao=new Dao();
			i=0;
			keyword=chaxunguanjianzi.getText();
			length=dao.querydescition(keyword);
			this.settable();
			yeshu=1;
			yeshukuang.setText(yeshu+"");
		}
		if(e.getSource()==button){
			Dao dao=new Dao();
			for(int m=0;m<chaxunliebiao.getRowCount();m++){
				try {
					System.out.println(chaxunliebiao.getValueAt(m, 0));
					if(chaxunliebiao.getValueAt(m, 5).equals(true)){
						//若勾选添加编号
						dao.deletFromName((int)chaxunliebiao.getValueAt(m, 0));
					}
				} catch (Exception e2) {
					// TODO: handle exception
					//e2.printStackTrace();
					
				}
			}
		//	Dao dao=new Dao();
			
			//keyword=null;
			i=i-10;
			List<Descition> s=dao.querydescition(keyword, i);
			i=i+10;
			Vector v_data=new Vector();
			for(Descition d:s){
				Vector temp=new Vector();//创建临时变量
				temp.add(d.getId());
				temp.add(d.getName());
				temp.add(d.getConsistency());
				temp.add(d.getVarLastConsistency());
				temp.add(d.getVarScheme());
				v_data.add(temp);
			}
			Vector v_head=new Vector();
			v_head.add("决策编号");
			v_head.add("决策名");
			v_head.add("一致度阈值");
			v_head.add("一致度值");
			v_head.add("最佳方案");
			v_head.add("");
			chaxunliebiao.setModel(new DefaultTableModel(
					v_data,v_head
				) {
					Class[] columnTypes = new Class[] {
							Object.class,Object.class,Object.class,Object.class,Object.class,Boolean.class
					};
					 @Override
			            public boolean isCellEditable(int row, int column) {
						 if(column==5)return true;
						 return false;
			            }
					 public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						} 
				});
//			lxzjtable.getValueAt(row, column);
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
			// tcr.setHorizontalAlignment(JLabel.CENTER);
			tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
			chaxunliebiao.setDefaultRenderer(Object.class, tcr);	
			((DefaultTableModel)chaxunliebiao.getModel()).fireTableStructureChanged();//更新显示
		}
		if(e.getSource()==shangyiye&&i>11){
			i=i-20;
			Dao dao=new Dao();
			yeshu--;
			this.settable();
			yeshukuang.setText(yeshu+"");
		}
		if(e.getSource()==xiayiye&&i<length){
			Dao dao=new Dao();
			keyword=chaxunguanjianzi.getText();
			length=dao.querydescition(keyword);
			yeshu++;
			yeshukuang.setText(yeshu+"");
			this.settable();
		}
	}
	public void settable(){
		Dao dao=new Dao();
		if(frist==0)
		{keyword=chaxunguanjianzi.getText();
		length=dao.querydescition(keyword);}
		List<Descition> s=dao.querydescition(keyword, i);
		i=i+10;
		Vector v_data=new Vector();
		for(Descition d:s){
			Vector temp=new Vector();//创建临时变量
			temp.add(d.getId());
			temp.add(d.getName());
			temp.add(d.getConsistency());
			temp.add(d.getVarLastConsistency());
			temp.add(d.getVarScheme());
			v_data.add(temp);
		}
		Vector v_head=new Vector();
		v_head.add("决策编号");
		v_head.add("决策名");
		v_head.add("一致度阈值");
		v_head.add("一致度值");
		v_head.add("最佳方案");
		v_head.add("");
		chaxunliebiao.setModel(new DefaultTableModel(
				v_data,v_head
			) {
				Class[] columnTypes = new Class[] {
						Object.class,Object.class,Object.class,Object.class,Object.class,Boolean.class
				};
				 @Override
		            public boolean isCellEditable(int row, int column) {
					 if(column==5)return true;
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
	public void set(){
		
	}
}
