package zlbyzc.sub3.duozhunze;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import zlbyzc.sub3.sub3inFrame;
public class Frame2 extends sub3inFrame implements ActionListener {

	private int id;			//决策id
	private Vector<Vector> v=new Vector<Vector>();//创建存放返回结果的Vector
	private Vector v_data=new Vector();
	private int i=1;					//查询记录起始位置
	private Map<Integer, List<Integer>> map=new HashMap<Integer, List<Integer>>();//分页中间变量
	private int yeshu=1;					//页数
	private int ii=1;						//第ii为专家打分录入
	private Map<Integer,Map<Integer,Map<Integer,Double>>>map3=new HashMap();
	private Descition descition ;//决策对象
	private Scheme scheme;			//方案对象
	private BaseDao basedao=new BaseDao();
	private Dao dao;
	private Rule rule;
	private int length;							//专家总数
	private int El=0;							//参会专家个数
	private int row=0;							//打分表的行
	private int col=0;							//打分表的列
	private List <String>mn=new ArrayList<String>();							//存储准则的权重
	private List <Integer>Maxx=new ArrayList();				//存储差异值最大的两个专家序号
	private int ll=0;										//需要修改的属性
	private List <Integer>fangan =new ArrayList<Integer>();	//需要修改的方案
	private int zuidaxiugaicishu=0;
	private int muqianxiugaicishu=0;
	private String best="";									//最佳方案
	private int hgf=0;										//设置的准则个数
	private int fgh=0;										//设置的方案个数
	private int yhn=0;										//已存准则个数
	private int nhy=0;										//已存方案个数
	private String keyWord="";								//通过关键字查找专家
	private List<Expert>okm=new ArrayList<Expert>();		//存储已选专家
	private double jlk=0.0;									//准则权重总数
	//private Frame frame=new Frame
	
	
	private JPanel contentPane= new JPanel();				//子容器
	private JTextField textField = new JTextField();		//	主题录入
	private JLabel label_2 = new JLabel("设置准则个数：");		
	private JTextField textField_1 = new JTextField();		//最大修改次数
	private JTextField textField_2 = new JTextField();		//群体意见一致度
	private JLabel label_1 = new JLabel("   主题录入：");
	private JLabel label = new JLabel("新建决策");
	private JLabel label_3 = new JLabel("    最大修改次数：");
	private JLabel label_4 = new JLabel("  备选方案个数：");
	private JLabel label_51 = new JLabel("            群体意见一致度：");
	private JButton button_1 = new JButton("下一步");
	private JTextField textField_3 = new JTextField();				//准则个数
	private JTextField textField_4 = new JTextField();				//方案个数
	private JPanel xjpanel1 = new JPanel();//新建决策
	private JPanel xjpanel2 = new JPanel();//主题录入
	private JPanel xjpanel3 = new JPanel();//最大修改次数
	private JPanel xjpanel4 = new JPanel();//最群体意见一致度、
	private JPanel xjpanel5 = new JPanel();//最群体意见一致度
	//设置决策相关属性界面
	public  Frame2(){
		super("多准则决策",true,true,true,true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 855, 683);
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		xjpanel1.setLayout(new BoxLayout(xjpanel1, BoxLayout.X_AXIS));
		xjpanel2.setLayout(new BoxLayout(xjpanel2, BoxLayout.X_AXIS));
		xjpanel3.setLayout(new BoxLayout(xjpanel3, BoxLayout.X_AXIS));
		xjpanel4.setLayout(new BoxLayout(xjpanel4, BoxLayout.X_AXIS));
		xjpanel5.setLayout(new BoxLayout(xjpanel5, BoxLayout.X_AXIS));
		
		label.setFont(new Font("宋体", Font.PLAIN, 30));
		xjpanel1.add(label);
		
		label_1.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel2.add(label_1);
		xjpanel2.add(Box.createRigidArea(new Dimension(20, 60)));  
		textField.setMaximumSize(new Dimension(100,25));
		xjpanel2.add(textField);
		xjpanel2.add(Box.createRigidArea(new Dimension(150, 60)));  
		label_2.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel2.add(label_2);
		xjpanel2.add(Box.createRigidArea(new Dimension(20, 60)));
		textField_3.setMaximumSize(new Dimension(100,25));
		xjpanel2.add(textField_3);
		
		label_3.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel3.add(label_3);
		xjpanel3.add(Box.createRigidArea(new Dimension(10, 60)));  
		textField_1.setMaximumSize(new Dimension(100,25));
		xjpanel3.add(textField_1);
		xjpanel3.add(Box.createRigidArea(new Dimension(110, 60)));  
		label_4.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel3.add(label_4);
		xjpanel3.add(Box.createRigidArea(new Dimension(20, 60)));
		textField_4.setMaximumSize(new Dimension(100,25));
		xjpanel3.add(textField_4);
		
		
		label_51.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel4.add(label_51);
		xjpanel4.add(Box.createRigidArea(new Dimension(20, 60))); 
		textField_2.setMaximumSize(new Dimension(100,25));
		xjpanel4.add(textField_2);
		xjpanel4.add(Box.createRigidArea(new Dimension(420, 60))); 
	
		xjpanel5.add(Box.createRigidArea(new Dimension(550, 60)));
		button_1.setFont(new Font("宋体", Font.PLAIN, 18));
		//button_1.setBackground(new Color(21,65,129));
		xjpanel5.add(button_1);
		 
		
		contentPane.add(Box.createVerticalGlue());
		contentPane.add(Box.createVerticalStrut(20)); 
		contentPane.add(xjpanel1);
		contentPane.add(Box.createVerticalStrut(60)); 
		contentPane.add(xjpanel2);
		contentPane.add(Box.createVerticalStrut(25));
		contentPane.add(xjpanel3);
		contentPane.add(Box.createVerticalStrut(10));
		contentPane.add(xjpanel4);
		contentPane.add(Box.createVerticalStrut(100));
		contentPane.add(xjpanel5);
		contentPane.add(Box.createVerticalStrut(400));
		contentPane.add( Box.createVerticalGlue());
		
		this.addListener();
		this.setVisible(true);
	}
	private JLabel labelfanganming = new JLabel("方案名：");
	private JTextField textField1 = new JTextField();
	private JTextField textField_11 = new JTextField();
	private JLabel label2 = new JLabel("设置准则与方案");
	private JButton button_11 = new JButton("下一步");
	private JTextField fanganmingtextField = new JTextField();//方案名
	private JLabel label_21 = new JLabel("方案信息描述：");
	private JTextArea textArea = new JTextArea();
	private JLabel label_31 = new JLabel("准则名：");
	private JLabel label_5 = new JLabel("权值");
	private JButton button_21 = new JButton("保存");
	private JButton button_31 = new JButton("保存");
	private JTable fanganliebiaotable= new JTable();;
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTable table = new JTable();//准则表
	private JButton sssssbutton = new JButton("删除方案");
	private JButton dfffbutton = new JButton("删除准则");
	private JPanel xjpanel11 = new JPanel();//设置准则与方案
	private JPanel xjpanel12 = new JPanel();//方案名
	private JPanel xjpanel13 = new JPanel();//方案信息描述
	private JPanel xjpanel14 = new JPanel();//方案文本框
	private JPanel xjpanel15 = new JPanel();//准则名
	private JPanel xjpanel16 = new JPanel();//方案列表
	private JPanel xjpanel17 = new JPanel();//方案表格
	private JPanel xjpanel18 = new JPanel();//下一步
	//设置准则和方案的界面
	public void initialtwo(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 855, 683);
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		xjpanel11.setLayout(new BoxLayout(xjpanel11, BoxLayout.X_AXIS));
		xjpanel12.setLayout(new BoxLayout(xjpanel12, BoxLayout.X_AXIS));
		xjpanel13.setLayout(new BoxLayout(xjpanel13, BoxLayout.X_AXIS));
		xjpanel14.setLayout(new BoxLayout(xjpanel14, BoxLayout.X_AXIS));
		xjpanel15.setLayout(new BoxLayout(xjpanel15, BoxLayout.X_AXIS));
		xjpanel16.setLayout(new BoxLayout(xjpanel16, BoxLayout.X_AXIS));
		xjpanel17.setLayout(new BoxLayout(xjpanel17, BoxLayout.X_AXIS));
		
		label2.setFont(new Font("宋体", Font.PLAIN, 30));
		xjpanel11.add(label2);
		
		
		labelfanganming.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel12.add(labelfanganming);
		xjpanel12.add(Box.createRigidArea(new Dimension(20, 60))); 
		fanganmingtextField.setMaximumSize(new Dimension(100,25));
		xjpanel12.add(fanganmingtextField);
		xjpanel12.add(Box.createRigidArea(new Dimension(400, 60))); 
		
		label_21.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel13.add(label_21);
		xjpanel13.add(Box.createRigidArea(new Dimension(400, 60)));
		button_31.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel13.add(button_31);
		
		textArea.setMaximumSize(new Dimension(500, 150));
		textArea.setMinimumSize(new Dimension(500, 150));
		xjpanel14.add(textArea);
		
		label_31.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel15.add(label_31);
		xjpanel15.add(Box.createRigidArea(new Dimension(10, 20)));
		textField1.setMaximumSize(new Dimension(60, 20));
		xjpanel15.add(textField1);
		xjpanel15.add(Box.createRigidArea(new Dimension(30, 20)));
		label_5.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel15.add(label_5);
		xjpanel15.add(Box.createRigidArea(new Dimension(10, 20)));
		textField_11.setMaximumSize(new Dimension(60, 20));
		xjpanel15.add(textField_11);
		xjpanel15.add(Box.createRigidArea(new Dimension(300, 20)));
		button_21.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel15.add(button_21);
		
		JLabel fanganliebiao = new JLabel("    方案列表");
		fanganliebiao.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel16.add(fanganliebiao);
		xjpanel16.add(Box.createRigidArea(new Dimension(100, 20)));
		sssssbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel16.add(sssssbutton);
		xjpanel16.add(Box.createRigidArea(new Dimension(60, 20)));
		JLabel label = new JLabel("  准则列表    ");
		label.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel16.add(label);
		xjpanel16.add(Box.createRigidArea(new Dimension(90, 20)));
		dfffbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel16.add(dfffbutton);
		
		
		JScrollPane fanganliebiaoscrollPane = new JScrollPane();
		fanganliebiaoscrollPane.setEnabled(false);
		fanganliebiaoscrollPane.setMaximumSize(new Dimension(300, 100));
		fanganliebiaoscrollPane.setMinimumSize(new Dimension(300, 100));
		xjpanel17.add(fanganliebiaoscrollPane);
		xjpanel17.add(Box.createRigidArea(new Dimension(60, 20)));
		scrollPane.setEnabled(false);
		scrollPane.setMaximumSize(new Dimension(300, 100));
		scrollPane.setMinimumSize(new Dimension(300, 100));
		xjpanel17.add(scrollPane);
		
		xjpanel18.add(Box.createRigidArea(new Dimension(600, 20)));
		button_11.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel18.add(button_11);
		
		
		fanganliebiaotable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
			},
			new String[] {
				"New column", "New column"
			}
		));
		fanganliebiaoscrollPane.setViewportView(fanganliebiaotable);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		scrollPane.setViewportView(table);
		
		contentPane.add(Box.createVerticalGlue());
		contentPane.add(Box.createVerticalStrut(20)); 
		contentPane.add(xjpanel11);
		contentPane.add(Box.createVerticalStrut(20)); 
		contentPane.add(xjpanel12);
		contentPane.add(Box.createVerticalStrut(10));
		contentPane.add(xjpanel13);
		contentPane.add(Box.createVerticalStrut(10));
		contentPane.add(xjpanel14);
		contentPane.add(Box.createVerticalStrut(20));
		contentPane.add(xjpanel15);
		contentPane.add(Box.createVerticalStrut(25));
		contentPane.add(xjpanel16);
		contentPane.add(Box.createVerticalStrut(25));
		contentPane.add(xjpanel17);
		contentPane.add(Box.createVerticalStrut(10));
		contentPane.add(xjpanel18);
		contentPane.add(Box.createVerticalStrut(400));
		contentPane.add( Box.createVerticalGlue());
		
		this.setT();
		this.setTs();
		this.setVisible(true);
	}
	
	private JLabel lxzjlabel2 = new JLabel("遴选专家");
	private final JTextField lxzjtextField = new JTextField();
	private final JButton novnjnvbutton = new JButton("添加");
	private final JButton lxzjbutton_11 = new JButton("下一步");
	private final JButton btnShanchu = new JButton("删除");
	private final JButton lxzjbutton = new JButton("查找");
	private final JButton lxzjbutton_1 = new JButton("上一页");
	private final JLabel lxzjlabel_1 = new JLabel("第");
	private final JTextField lxzjtextField_1 = new JTextField();
	private final JLabel lxzjlabel_2 = new JLabel("页");
	private final JButton lxzjbutton_2 = new JButton("下一页");
	private final JScrollPane lxzjscrollPane = new JScrollPane();
	private final JTable lxzjtable = new JTable();
	private final JScrollPane lingxuanzhuanjia1 = new JScrollPane();
	private final JTable lingxuanzhanjiatable = new JTable();
	private JPanel xjpanel21 = new JPanel();//设置准则与方案
	private JPanel xjpanel22 = new JPanel();//方案名
	private JPanel xjpanel23 = new JPanel();//方案信息描述
	private JPanel xjpanel24 = new JPanel();//方案文本框
	private JPanel xjpanel25 = new JPanel();//准则名
	private JPanel xjpanel26 = new JPanel();//方案列表
	private JPanel xjpanel27 = new JPanel();//方案表格
	//选取专家界面
	public void initialthree(){
		
	//	setBounds(100, 100, 855, 683);
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		xjpanel21.setLayout(new BoxLayout(xjpanel21, BoxLayout.X_AXIS));
		xjpanel22.setLayout(new BoxLayout(xjpanel22, BoxLayout.X_AXIS));
		xjpanel23.setLayout(new BoxLayout(xjpanel23, BoxLayout.X_AXIS));
		xjpanel24.setLayout(new BoxLayout(xjpanel24, BoxLayout.X_AXIS));
		xjpanel25.setLayout(new BoxLayout(xjpanel25, BoxLayout.X_AXIS));
		xjpanel26.setLayout(new BoxLayout(xjpanel26, BoxLayout.X_AXIS));
		xjpanel27.setLayout(new BoxLayout(xjpanel27, BoxLayout.X_AXIS));
		
		lxzjlabel2.setFont(new Font("宋体", Font.PLAIN, 24));
		xjpanel21.add(lxzjlabel2);
		
		//xjpanel22.add(Box.createRigidArea(new Dimension(10, 20)));
		lxzjtextField.setMaximumSize(new Dimension(150, 20));
		xjpanel22.add(lxzjtextField);
		xjpanel22.add(Box.createRigidArea(new Dimension(20, 20)));
		lxzjbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel22.add(lxzjbutton);
		xjpanel22.add(Box.createRigidArea(new Dimension(300, 20)));
		novnjnvbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel22.add(novnjnvbutton);
		
		lxzjscrollPane.setMaximumSize(new Dimension(600, 200));
		lxzjscrollPane.setMinimumSize(new Dimension(600, 200));
		xjpanel23.add(lxzjscrollPane);
		
		lxzjbutton_1.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel24.add(lxzjbutton_1);
		xjpanel24.add(Box.createRigidArea(new Dimension(10, 20)));
		lxzjlabel_1.setFont(new Font("宋体", Font.PLAIN, 14));
		xjpanel24.add(lxzjlabel_1);
		lxzjtextField_1.setMaximumSize(new Dimension(60, 20));
		//lxzjtextField_1.setMinimumSize(new Dimension(100, 30));
		lxzjtextField_1.setText("1");
		xjpanel24.add(lxzjtextField_1);
		xjpanel24.add(Box.createRigidArea(new Dimension(10, 20)));
		lxzjlabel_2.setFont(new Font("宋体", Font.PLAIN, 14));
		xjpanel24.add(lxzjlabel_2);
		lxzjbutton_2.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel24.add(lxzjbutton_2);
		
		xjpanel25.add(Box.createRigidArea(new Dimension(500, 20)));
		btnShanchu.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel25.add(btnShanchu);
		
		
		JScrollPane lingxuanzhuanjia1 = new JScrollPane();
		lingxuanzhuanjia1.setMaximumSize(new Dimension(600, 200));
		lingxuanzhuanjia1.setMinimumSize(new Dimension(600, 200));
		xjpanel26.add(lingxuanzhuanjia1);
		
		xjpanel27.add(Box.createRigidArea(new Dimension(520, 20)));
		lxzjbutton_11.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel27.add(lxzjbutton_11);
		
		
		lxzjtable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null,null},
				{null, null, null, null,null},
				{null, null, null, null,null},
				{null, null, null, null,null},
				{null, null, null, null,null},
				{null, null, null, null,null},
				{null, null, null, null,null},
				{null, null, null, null,null},
				{null, null, null, null,null},
				{null, null, null, null,null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		) {
			Class[] columnTypes = new Class[] {
				Object.class, Object.class, Object.class,Object.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		lxzjtable.setFont(new Font("宋体", Font.PLAIN, 18));
		lxzjscrollPane.setViewportView(lxzjtable);
	
		
		lingxuanzhanjiatable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
				},
				new String[] {
					"New column", "New column", "New column", "New column"
				}
			) {
				Class[] columnTypes = new Class[] {
						Boolean.class,Object.class, Object.class, Object.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		lingxuanzhanjiatable.setFont(new Font("宋体", Font.PLAIN, 18));
		
		lingxuanzhuanjia1.setViewportView(lingxuanzhanjiatable);
		contentPane.add(Box.createVerticalGlue());
		contentPane.add(Box.createVerticalStrut(20)); 
		contentPane.add(xjpanel21);
		contentPane.add(Box.createVerticalStrut(20)); 
		contentPane.add(xjpanel22);
		contentPane.add(Box.createVerticalStrut(10));
		contentPane.add(xjpanel23);
		contentPane.add(Box.createVerticalStrut(10));
		contentPane.add(xjpanel24);
		contentPane.add(Box.createVerticalStrut(20));
		contentPane.add(xjpanel25);
		contentPane.add(Box.createVerticalStrut(25));
		contentPane.add(xjpanel26);
		contentPane.add(Box.createVerticalStrut(25));
		contentPane.add(xjpanel27);
		contentPane.add(Box.createVerticalStrut(400));
		contentPane.add( Box.createVerticalGlue());		
		this.setVisible(true);
		//this.getExpert();
		this.settable4();
	}
	
	private JTable qrzjtable = new JTable();;				//表
	private JLabel qrzjlabel2 = new JLabel("确认专家");		
	private JButton qrzjbutton_11 = new JButton("下一步");
	private JScrollPane qrzjscrollPane = new JScrollPane();
	
	//确认专家界面
	private void initialFour() {
			// TODO Auto-generated method stub
			
			qrzjlabel2.setBounds(316, 10, 189, 21);
			qrzjlabel2.setFont(new Font("宋体", Font.PLAIN, 18));
			contentPane.add(qrzjlabel2);
			
			
			qrzjbutton_11.setFont(new Font("宋体", Font.PLAIN, 12));
			qrzjbutton_11.setBounds(636, 407, 84, 23);
			contentPane.add(qrzjbutton_11);
			
		
			qrzjscrollPane.setBounds(20, 107, 718, 186);
			contentPane.add(qrzjscrollPane);
			
			
			qrzjtable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
				},
				new String[] {
					"New column", "New column", "New column", "New column"
				}
			) {
				Class[] columnTypes = new Class[] {
					Object.class, Object.class, Object.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			
			
			qrzjscrollPane.setViewportView(qrzjtable);

		}
		
	private JTable zjdftable = new JTable();
	private JLabel zjdflabel2 = new JLabel("专家打分");
	private JButton zjdfbutton_11 = new JButton("决策");
	private JScrollPane zjdfscrollPane = new JScrollPane();
	private JLabel zjdfLabel=new JLabel("专家1");;
	private JButton zjdfxywbutton = new JButton("下一位专家");	
	private JPanel xjpanel31 = new JPanel();//设置准则与方案
	private JPanel xjpanel32 = new JPanel();//方案名
	private JPanel xjpanel33 = new JPanel();//方案信息描述
	private JPanel xjpanel34 = new JPanel();//方案文本框
	//第五模块专家打分
	private void initialFive() {
			// TODO Auto-generated method stub
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 855, 683);
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		xjpanel31.setLayout(new BoxLayout(xjpanel31, BoxLayout.X_AXIS));
		xjpanel32.setLayout(new BoxLayout(xjpanel32, BoxLayout.X_AXIS));
		xjpanel33.setLayout(new BoxLayout(xjpanel33, BoxLayout.X_AXIS));
		xjpanel34.setLayout(new BoxLayout(xjpanel34, BoxLayout.X_AXIS));
		
		
		zjdflabel2.setFont(new Font("宋体", Font.PLAIN, 30));
		xjpanel31.add(zjdflabel2);
		
		
		
		zjdfLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		zjdfLabel.setText("专家  "+okm.get(0).getName()+":");
		xjpanel32.add(zjdfLabel);
		xjpanel32.add(Box.createRigidArea(new Dimension(450, 20)));
		zjdfxywbutton.setFont(new Font("宋体", Font.PLAIN, 14));
		xjpanel32.add(zjdfxywbutton);
		
		zjdfscrollPane.setMaximumSize(new Dimension(650, 200));
		zjdfscrollPane.setMinimumSize(new Dimension(650, 200));
		xjpanel33.add(zjdfscrollPane);
		
		xjpanel34.add(Box.createRigidArea(new Dimension(550, 20)));
		zjdfbutton_11.setFont(new Font("宋体", Font.PLAIN, 14));
		xjpanel34.add(zjdfbutton_11);
		
		
		
		
		
		zjdftable.setModel(new DefaultTableModel(
			new Object[][] {
				{"\u65B9\u68481", null, null, "", null, null, null, null},
				{"\u65B9\u68482", null, null, "", null, null, null, null},
				{"\u65B9\u68483", null, null, "", null, null, null, null},
				{"\u65B9\u68484", null, null, "", null, null, null, null},
				{"\u65B9\u68485", null, null, "", null, null, null, null},
			},
			new String[] {
				"", "\u51C6\u52191", "\u51C6\u52192", "\u51C6\u52193", "\u51C6\u52194", "\u51C6\u52195", "\u51C6\u52196", "\u51C6\u52197"
			}
		));
		zjdftable.setFont(new Font("宋体", Font.PLAIN, 18));
		zjdfscrollPane.setViewportView(zjdftable);
		
		
		contentPane.add(Box.createVerticalGlue());
		contentPane.add(Box.createVerticalStrut(20)); 
		contentPane.add(xjpanel31);
		contentPane.add(Box.createVerticalStrut(30)); 
		contentPane.add(xjpanel32);
		contentPane.add(Box.createVerticalStrut(20));
		contentPane.add(xjpanel33);
		contentPane.add(Box.createVerticalStrut(100));
		contentPane.add(xjpanel34);
		
		contentPane.add(Box.createVerticalStrut(400));
		contentPane.add( Box.createVerticalGlue());		
		
			//获取参会专家人数
			dao=new Dao();
			List<Expert_Descition> ss=dao.queryexpertByDescitionId(id);
			for(Expert_Descition p:ss){
				El++;
			}
			System.out.println("参会专家人数为"+El);
			this.setVisible(true);
		}
	
	private JTable jcjgtable = new JTable();
	private JLabel jcjglabel4444 = new JLabel(" 修改次数：");
	private JLabel jcjglblNewLabel = new JLabel("决策结果");
	private JLabel jcjglabel = new JLabel("专家差异度表");
	private JScrollPane jcjgscrollPane = new JScrollPane();
	private JLabel jcjglabel_1 = new JLabel("修改意见：");
	private JTextArea jcjgtextArea = new JTextArea();
	private JLabel jcjglabel_2 = new JLabel("   需修改专家为：");
	private JLabel jcjglabel_3 = new JLabel("专家1");
	private JLabel jcjglabel_4 = new JLabel("        群体意见一致度为：");
	private JLabel jcjglabel_5 = new JLabel("专家1");
	private JButton jcjgbutton = new JButton("下一轮决策");
	private JLabel jcjglabel_14444 = new JLabel("4");
	private JPanel xjpanel41 = new JPanel();//设置准则与方案
	private JPanel xjpanel42 = new JPanel();//方案名
	private JPanel xjpanel43 = new JPanel();//方案信息描述
	private JPanel xjpanel44 = new JPanel();//方案文本框
	private JPanel xjpanel45 = new JPanel();//方案文本框
	private JPanel xjpanel46 = new JPanel();//方案文本框
	private JPanel xjpanel47 = new JPanel();//方案文本框
	private JPanel xjpanel48 = new JPanel();//方案文本框
	private JPanel xjpanel49 = new JPanel();//方案文本框
	private JPanel xjpanel410= new JPanel();//方案文本框
	private JPanel xjpanel411= new JPanel();//方案文本框
	private JPanel xjpanel412= new JPanel();//方案文本框
	//决策结果 一致度未达标界面
	private void initialSixone(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setBounds(100, 100, 855, 683);
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		xjpanel41.setLayout(new BoxLayout(xjpanel41, BoxLayout.X_AXIS));
		xjpanel42.setLayout(new BoxLayout(xjpanel42, BoxLayout.X_AXIS));
		xjpanel43.setLayout(new BoxLayout(xjpanel43, BoxLayout.X_AXIS));
		xjpanel44.setLayout(new BoxLayout(xjpanel44, BoxLayout.Y_AXIS));
		xjpanel45.setLayout(new BoxLayout(xjpanel45, BoxLayout.X_AXIS));
		xjpanel46.setLayout(new BoxLayout(xjpanel46, BoxLayout.X_AXIS));
		xjpanel47.setLayout(new BoxLayout(xjpanel47, BoxLayout.X_AXIS));
		xjpanel48.setLayout(new BoxLayout(xjpanel48, BoxLayout.Y_AXIS));
		xjpanel49.setLayout(new BoxLayout(xjpanel49, BoxLayout.X_AXIS));
		xjpanel410.setLayout(new BoxLayout(xjpanel410, BoxLayout.X_AXIS));
		xjpanel411.setLayout(new BoxLayout(xjpanel411, BoxLayout.X_AXIS));
		xjpanel412.setLayout(new BoxLayout(xjpanel412, BoxLayout.X_AXIS));
		
		
		jcjglblNewLabel.setFont(new Font("宋体", Font.BOLD, 24));
		xjpanel41.add(jcjglblNewLabel);
		
		
		jcjglabel.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel42.add(jcjglabel);
		xjpanel42.add(Box.createRigidArea(new Dimension(550, 20)));
		
		
		jcjgscrollPane.setMaximumSize(new Dimension(694, 163));
		jcjgscrollPane.setMinimumSize(new Dimension(694, 163));
		xjpanel43.add(jcjgscrollPane);
		
		jcjglabel_1.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel411.add(jcjglabel_1);
		xjpanel411.add(Box.createRigidArea(new Dimension(278, 20)));
		jcjgtextArea.setFont(new Font("宋体", Font.PLAIN, 14));
		jcjgtextArea.setEditable(false);
		jcjgtextArea.setMaximumSize(new Dimension(350,250));
		jcjgtextArea.setMinimumSize(new Dimension(350, 250));
		xjpanel412.add(Box.createRigidArea(new Dimension(20, 20)));
		xjpanel412.add(jcjgtextArea);
		xjpanel412.add(Box.createRigidArea(new Dimension(70, 20)));
		xjpanel44.add(xjpanel411);
		xjpanel44.add(Box.createRigidArea(new Dimension(20, 20)));
		xjpanel44.add(xjpanel412);
		
		jcjglabel_2.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel45.add(jcjglabel_2);
		xjpanel45.add(Box.createRigidArea(new Dimension(40, 20)));
		jcjglabel_3.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel45.add(jcjglabel_3);
		
		jcjglabel_4.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel46.add(jcjglabel_4);
		xjpanel46.add(Box.createRigidArea(new Dimension(40, 20)));
		jcjglabel_5.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel46.add(jcjglabel_5);
		
		jcjglabel4444.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel47.add(jcjglabel4444);
		xjpanel47.add(Box.createRigidArea(new Dimension(40, 20)));
		jcjglabel_14444.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel47.add(jcjglabel_14444);
		xjpanel47.add(Box.createRigidArea(new Dimension(40, 20)));
		xjpanel48.add(xjpanel45);
		xjpanel48.add(Box.createRigidArea(new Dimension(40, 20)));
		xjpanel48.add(xjpanel46);
		xjpanel48.add(Box.createRigidArea(new Dimension(40, 20)));
		xjpanel48.add(xjpanel47);
		
		xjpanel49.add(xjpanel44);
		xjpanel49.add(xjpanel48);
		
		xjpanel410.add(Box.createRigidArea(new Dimension(578, 20)));
		jcjgbutton.setFont(new Font("宋体", Font.PLAIN, 14));
		xjpanel410.add(jcjgbutton);
		
		jcjgtable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
				{null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		jcjgtable.setFont(new Font("宋体", Font.BOLD, 18));
		jcjgscrollPane.setViewportView(jcjgtable);

		contentPane.add(Box.createVerticalGlue());
		contentPane.add(Box.createVerticalStrut(20)); 
		contentPane.add(xjpanel41);
		contentPane.add(Box.createVerticalStrut(30)); 
		contentPane.add(xjpanel42);
		contentPane.add(Box.createVerticalStrut(20));
		contentPane.add(xjpanel43);
		contentPane.add(Box.createVerticalStrut(40));
		contentPane.add(xjpanel49);
		contentPane.add(Box.createVerticalStrut(20));
		contentPane.add(xjpanel410);
		contentPane.add(Box.createVerticalStrut(400));
		contentPane.add( Box.createVerticalGlue());		
		this.setVisible(true);
	}
	
	private JTable zzjgtable= new JTable();
	JLabel zzjglblNewLabel = new JLabel("最终决策结果");
	JScrollPane zzjgscrollPane = new JScrollPane();
	JLabel zzjglabel_1 = new JLabel("    最佳方案为：");
	JButton zzjgbutton = new JButton("决策结束");
	JLabel zzjglabel = new JLabel("方案1");
	JLabel zzjglabel8 = new JLabel("      群体意见一致度为：");
	JLabel zzjglabel_10 = new JLabel("方案2");
	private JPanel xjpanel51 = new JPanel();//设置准则与方案
	private JPanel xjpanel52 = new JPanel();//方案名
	private JPanel xjpanel53 = new JPanel();//方案信息描述
	private JPanel xjpanel54 = new JPanel();//方案文本框
	private JPanel xjpanel55 = new JPanel();//方案文本框
	private void initialSixotow(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	setBounds(100, 100, 855, 683);
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		xjpanel51.setLayout(new BoxLayout(xjpanel51, BoxLayout.X_AXIS));
		xjpanel52.setLayout(new BoxLayout(xjpanel52, BoxLayout.X_AXIS));
		xjpanel53.setLayout(new BoxLayout(xjpanel53, BoxLayout.X_AXIS));
		xjpanel54.setLayout(new BoxLayout(xjpanel54, BoxLayout.X_AXIS));
		xjpanel55.setLayout(new BoxLayout(xjpanel55, BoxLayout.X_AXIS));
		
		
		zzjglblNewLabel.setFont(new Font("宋体", Font.BOLD, 24));
		xjpanel51.add(zzjglblNewLabel);
		
		
		
		zzjgscrollPane.setMaximumSize(new Dimension(727,163));
		zzjgscrollPane.setMinimumSize(new Dimension(727,163));
		xjpanel52.add(zzjgscrollPane);
		
		zzjgtable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		zzjgtable.setFont(new Font("宋体", Font.BOLD, 18));
		zzjgscrollPane.setViewportView(zzjgtable);
		
		zzjglabel_1.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel53.add(zzjglabel_1);
		xjpanel53.add(Box.createRigidArea(new Dimension(40, 20)));
		zzjglabel.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel53.add(zzjglabel);
		xjpanel53.add(Box.createRigidArea(new Dimension(580, 20)));
		
		zzjglabel8.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel54.add(zzjglabel8);
		xjpanel54.add(Box.createRigidArea(new Dimension(40, 20)));
		zzjglabel_10.setFont(new Font("宋体", Font.BOLD, 18));
		xjpanel54.add(zzjglabel_10);
		xjpanel54.add(Box.createRigidArea(new Dimension(580, 20)));
		
		xjpanel55.add(Box.createRigidArea(new Dimension(620, 20)));
		zzjgbutton.setFont(new Font("宋体", Font.PLAIN, 18));
		xjpanel55.add(zzjgbutton);
		
		contentPane.add(Box.createVerticalGlue());
		contentPane.add(Box.createVerticalStrut(20)); 
		contentPane.add(xjpanel51);
		contentPane.add(Box.createVerticalStrut(50)); 
		contentPane.add(xjpanel52);
		contentPane.add(Box.createVerticalStrut(50));
		contentPane.add(xjpanel53);
		contentPane.add(Box.createVerticalStrut(20));
		contentPane.add(xjpanel54);
		contentPane.add(Box.createVerticalStrut(40));
		contentPane.add(xjpanel55);
		contentPane.add(Box.createVerticalStrut(400));
		contentPane.add( Box.createVerticalGlue());		
		this.setVisible(true);
		
	}
	public void addListener(){
		button_1.addActionListener(this);  //模块1下一步按钮
		
		button_21.addActionListener(this); //模块二方案描述信息保存按钮
		button_31.addActionListener(this); //模块二准则描述信息保存按钮
		button_11.addActionListener(this);//模块二下一步按钮
		sssssbutton.addActionListener(this);//删除方案
		dfffbutton.addActionListener(this);//删除准则
		
		lxzjbutton.addActionListener(this);//模块三查找按钮
		lxzjbutton_1.addActionListener(this);//模块三上一页 按钮
		lxzjbutton_2.addActionListener(this);//模块三下一页按钮
		lxzjbutton_11.addActionListener(this);//模块三下一步按钮
		novnjnvbutton.addActionListener(this);//添加按钮
		btnShanchu.addActionListener(this);	//删除专家
		
		qrzjbutton_11.addActionListener(this);//模块四下一步按钮
		zjdfxywbutton.addActionListener(this);//下一位专家按钮
		zjdfbutton_11.addActionListener(this);//模块五下一步按钮
		
		jcjgbutton.addActionListener(this);//下一轮决策按钮
	}
	
	  public boolean IsNum(String s)
      {
		  String regex = "^[1-9][0-9]*\\.[0-9]+$|^[1-9][0-9]*$|^0+\\.[0-9]+$";  
          Pattern pattern = Pattern.compile(regex);  
          char c = s.charAt(0);  
          if(c=='+'||c=='-'){  
              s = s.substring(1);  
          }  
          Matcher matcher = pattern.matcher(s);  
          boolean bool = matcher.matches();  
          return bool;
      }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//设置决策界面 点击保存按钮
		//设置决策界面 点击下一步按钮
		if(e.getSource()==button_1){
			dao=new Dao();
			descition=new Descition();
			if(!textField.getText().equals("")&&!textField_1.getText().equals("")&&!textField_2.getText().equals("")&&!textField_4.getText().equals("")
					&&!textField_3.getText().equals("")){
				if(!dao.queryDescitionbyName(textField.getText())){
					if(this.IsNum(textField_2.getText())&&this.IsNum(textField_4.getText())&&this.IsNum(textField_3.getText())){
					descition.setName(textField.getText());
					descition.setConsistency(textField_2.getText());
					fgh=Integer.parseInt(textField_4.getText());
					hgf=Integer.parseInt(textField_3.getText());
					boolean  s=basedao.saveOrUpdateObject(descition);
					if(s){
						zuidaxiugaicishu=Integer.parseInt(textField_1.getText());
						id=descition.getId();
						//显示下一个板块
						contentPane.removeAll();//移除界面显示的所有控件
						this.initialtwo();//模块二布局
						contentPane.repaint();//重绘画板
							}
						}else{
							JOptionPane.showMessageDialog(null, "输入信息有误");
							}
				}
				else{
					JOptionPane.showMessageDialog(null, "决策名已存在");
			}
			}else{
				JOptionPane.showMessageDialog(null, "信息输入不全");
			}
		}
		//设置方案和准则界面点击保存方案
		if(e.getSource()==button_31){
			if(nhy<fgh){
			scheme=new Scheme();
			scheme.setDescition_id(id);
			scheme.setDescripition(textArea.getText());
			scheme.setName(fanganmingtextField.getText());
			scheme.setDescition_id(id);
			basedao.saveObject(scheme);
			fanganmingtextField.setText("");
			textArea.setText("");
			nhy++;
			this.setT();
			}else{
				JOptionPane.showMessageDialog(null, "方案数已达到设定值");
				textArea.setText("");
				fanganmingtextField.setText("");
				
			}
		}
		if(e.getSource()==sssssbutton){	//删除方案
			dao=new Dao();//删除方案
			System.out.println(fanganliebiaotable.getRowCount());
			for(int i=0;i<fanganliebiaotable.getRowCount();i++){
				try {
					if(fanganliebiaotable.getValueAt(i, 0).equals(true))
					{
						dao.delefangan((int)fanganliebiaotable.getValueAt(i, 1));
						if(nhy>=0)
							nhy--;
						}
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
			this.setT();
		}
		if(e.getSource()==dfffbutton){	//删除准则
			dao=new Dao();//删除准则
			
			System.out.println(table.getRowCount());
			for(int i=0;i<table.getRowCount();i++){
				try {
					if(table.getValueAt(i, 0).equals(true)){
						dao.delezhunze((int) table.getValueAt(i, 1));
						jlk=jlk-Double.parseDouble((String) table.getValueAt(i, 3));
						System.out.println(yhn);
						if(yhn>0)
							yhn--;
						System.out.println(yhn);
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			this.setTs();
		}
		
	//	设置方案和准则界面点击保存准则
		if(e.getSource()==button_21){	//模块二保存准则
			if(this.IsNum(textField_11.getText())&&Double.parseDouble(textField_11.getText())>0.0){
			if(yhn<hgf){
				jlk=jlk+Double.parseDouble(textField_11.getText());
				if(jlk<=1.0){
				rule=new Rule();
				rule.setName(textField1.getText());
				rule.setDescripition("");
				rule.setWeight(textField_11.getText());
				rule.setDescrition_id(id);
				basedao.saveObject(rule);
				textField1.setText("");
				textField_11.setText("");
				this.setTs();
				System.out.println(yhn);
				yhn++;
				System.out.println(yhn);
				}else{
					jlk=jlk-Integer.parseInt(textField_11.getText());
					JOptionPane.showMessageDialog(null, "总权重值超过1");
					textField1.setText("");
					textField_11.setText("");
					}
			}else{
				JOptionPane.showMessageDialog(null, "准则数已达到设定值");
				textField1.setText("");
				textField_11.setText("");
			}
			}else{
				JOptionPane.showMessageDialog(null, "权重值输入错误");
				textField1.setText("");
				textField_11.setText("");
				}
		}
		//设置方案和准则界面点击下一步
		if(e.getSource()==button_11){
//			if(jlk!=1.0){
//				JOptionPane.showMessageDialog(null, "总权重值不等于1");
//			}else{
			contentPane.removeAll();				//移除界面显示的所有控件
			this.initialthree();					//模块三布局
			contentPane.repaint();					//重绘画板
	//		this.getExpert();
			dao=new Dao();
			List <Expert> d=dao.queryExpert(i);
			Vector data=new Vector();
			i=i+10;
			for(Expert s:d){
				Vector temp=new Vector();//创建临时变量
				temp.add(s.getNumber());
				temp.add(s.getName());
				temp.add(s.getTitle());
				temp.add(s.getWorkUnit());
				data.add(temp);
			}
			Vector head=new Vector();
			head.add("编号");
			head.add("姓名");
			head.add("职称");
			head.add("工作单位");
			head.add("操作");
			lxzjtable.setModel(new DefaultTableModel(
					data,head
				) {
					Class[] columnTypes = new Class[] {
							Object.class,Object.class,Object.class,Object.class,Boolean.class
					};
					 @Override
			            public boolean isCellEditable(int row, int column) {
						 if(column==4)
							 return true;
						 return false;
			            }
					 public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						} 
				});
			//内容居中
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
			// tcr.setHorizontalAlignment(JLabel.CENTER);
			tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
			lxzjtable.setDefaultRenderer(Object.class, tcr);	
//			lxzjtable.getValueAt(row, column);
			((DefaultTableModel)lxzjtable.getModel()).fireTableStructureChanged();//更新显示
			
			length=dao.queryExpertLength(keyWord);
//			}
			}
		if(e.getSource()==lxzjbutton){				//查找专家
			i=1;
			yeshu=1;
			keyWord=lxzjtextField.getText();
			length=dao.queryExpertLength(keyWord);
			this.getExpert();
		}
		if(e.getSource()==novnjnvbutton){		//添加按钮
			dao=new Dao();
			for(int m=0;m<10;m++){
				try {
					if(!lxzjtable.getValueAt(m, 4).equals("")){
						System.out.println("m"+m);
						System.out.println((String)lxzjtable.getValueAt(m, 0));
							if(!okm.contains((Expert)dao.queryexpertByNumber((String)lxzjtable.getValueAt(m, 0))))
								//若勾选添加编号
								okm.add((Expert)dao.queryexpertByNumber((String)lxzjtable.getValueAt(m, 0)));
								lxzjtable.setValueAt(null, m, 4);
						}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			((DefaultTableModel)lxzjtable.getModel()).fireTableStructureChanged();//更新显示
			this.settable4();
		}
		if(e.getSource()==btnShanchu){		//删除按钮
			for(int m=0;m<lingxuanzhanjiatable.getRowCount();m++){
				try {
					if(lingxuanzhanjiatable.getValueAt(m, 0).equals(true)){
						//若勾选添加编号
						System.out.println(okm);
						System.out.println(okm.get(m));
						okm.remove(m);
						System.out.println(okm);
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
			this.settable4();
		}
		//模块三选专家下一页按钮
		if(e.getSource()==lxzjbutton_2&&i<length){	
			System.out.println(length);
			//记录将当前页面的勾选状态
			//this.jilugouxuanzhuangtai(10);
			this.getExpert();
			++yeshu;
			//设置上一个页面的勾选状态
		//	this.shezhishangyegeyemian();
		}
		//模块三上一页按钮
		if(e.getSource()==lxzjbutton_1&&i>11){	
			i=i-20;
			//记录将当前页面的勾选状态
			//this.jilugouxuanzhuangtai(10);
			this.getExpert();
			--yeshu;
			//设置上一个页面的勾选状态
			//this.shezhishangyegeyemian();
			//System.out.println("点击上一页");
		}
		if(e.getSource()==lxzjbutton_11){
			//模块三下一步按钮
			dao=new Dao();
			//存储参会专家
			for(Expert s:okm){
				Expert_Descition expert=new Expert_Descition();
		          //查找专家
				 expert.setDescition_id(id);
				 expert.setExpert_id(s.getId());
				 System.out.println(dao.saveObject(expert));
				
			}
			contentPane.removeAll();				//移除界面显示的所有控件
			this.initialFive();					//模块五布局
			contentPane.repaint();					//重绘画板
			this.setTable();
		}
		if(e.getSource()==qrzjbutton_11)			//模块四下一步
		{
			contentPane.removeAll();				//移除界面显示的所有控件
			this.initialFive();					//模块五布局
			contentPane.repaint();					//重绘画板
			this.setTable();
		}
													//下一位专家
		if(e.getSource()==zjdfxywbutton&&ii<El){
			System.out.println("进入下一位专家打分");
			this.saveES();
			ii++;
			String s=okm.get(ii-1).getName();
			zjdfLabel.setText("专家  "+s+":");
			this.setTable();
		}
		if(e.getSource()==zjdfbutton_11){	//第五个界面 专家打分 下一步按钮
			if(ii==El){
			muqianxiugaicishu++;
			dao=new Dao();
			this.saveES();
			contentPane.removeAll();				//移除界面显示的所有控件
			this.initialSixone();					//模块五布局
			contentPane.repaint();					//重绘画板
			List ss=dao.queryruleByNumber(id);
			//遍历全部结果
			for(Iterator pit=ss.iterator();pit.hasNext();){
				Rule p=(Rule)pit.next();
				col++;
			}
			List s=dao.queryschemeByNumber(id);
			for(Iterator pis=s.iterator();pis.hasNext();){
				Scheme p=(Scheme)pis.next();
				row++;
			}
			double nn=this.same();
			if(nn<Double.parseDouble(((Descition) dao.queryDescitionById(id)).getConsistency())&&muqianxiugaicishu<zuidaxiugaicishu){
				 this.setTable2();
				 BigDecimal   bn   =   new   BigDecimal(nn); 
				 jcjglabel_5.setText(bn.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"");
			}else{
				contentPane.removeAll();				//移除界面显示的所有控件
				this.initialSixotow();					//模块五布局
				contentPane.repaint();					//重绘画板
				this.setTable3();
				System.out.println(zzjgtable.getRowCount());
				descition.setVarScheme(best);
				BigDecimal   bn   =   new   BigDecimal(Double.parseDouble(zzjglabel_10.getText()));  
				descition.setVarLastConsistency(bn.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"");
				dao.updateObject(descition);
			}
			}else{JOptionPane.showMessageDialog(null, "专家打分未录入完毕");}
		}
		if(e.getSource()==jcjgbutton){				//转到打分页面
			mn.clear();
			Maxx.clear();
			ll=0;
			map3.clear();
			fangan.clear();
			ii=1;
			El=0;
			zjdfLabel.setText("专家1");
			row=0;
			col=0;
			contentPane.removeAll();				//移除界面显示的所有控件
			this.initialFive();					//模块五布局
			contentPane.repaint();					//重绘画板
			this.setTable();
		}
	}
	
	private void settable4() {
		// TODO Auto-generated method stub
		v.clear();
		for(Expert s:okm){
			Vector temp=new Vector();//创建临时变量
			temp.add(false);
			temp.add(s.getNumber());
			temp.add(s.getName());
			temp.add(s.getSex());
			temp.add(s.getTitle());
			v.add(temp);
			v_data=v;
		}
		Vector v_head=new Vector();
		v_head.add("");
		v_head.add("专家编号");
		v_head.add("专家名");
		v_head.add("性别");
		v_head.add("职称");
		lingxuanzhanjiatable.setModel(new DefaultTableModel(
				v_data,v_head
			) {
				Class[] columnTypes = new Class[] {
						Boolean.class,Object.class,Object.class,Object.class,Object.class
				};
				 @Override
		            public boolean isCellEditable(int row, int column) {
					 if(column==0)
						 return true;
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
		lingxuanzhanjiatable.setDefaultRenderer(Object.class, tcr);	
		((DefaultTableModel)lingxuanzhanjiatable.getModel()).fireTableStructureChanged();//更新显示
		lingxuanzhanjiatable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	}
	/*
	 * 设置模块2的方案表
	 */
	private void setT(){
		dao=new Dao();
		List<Scheme> ss =dao.queryschemeByNumber(id);
		v.clear();
		for(Iterator pit=ss.iterator();pit.hasNext();){
			Vector temp=new Vector();//创建临时变量
			Scheme p=(Scheme)pit.next();
			temp.add(false);
			temp.add(p.getId());
			temp.add(p.getName());
			v.add(temp);
			v_data=v;
		}
		Vector v_head=new Vector();
		v_head.add("");
		v_head.add("方案Id");
		v_head.add("方案名");
		fanganliebiaotable.setModel(new DefaultTableModel(
				v_data,v_head
			) {
				Class[] columnTypes = new Class[] {
						Boolean.class,Object.class,Object.class
				};
				 @Override
		            public boolean isCellEditable(int row, int column) {
					 if(column==0)
						 return true;
					 return false;
		            }
				
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		//内容居中 
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		 // tcr.setHorizontalAlignment(JLabel.CENTER);
		  tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		  fanganliebiaotable.setDefaultRenderer(Object.class, tcr);
//		lxzjtable.getValueAt(row, column);
		((DefaultTableModel)fanganliebiaotable.getModel()).fireTableStructureChanged();//更新显示
		//fanganliebiaotable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	}
	private void setTs(){
		dao=new Dao();
		List<Rule> ss =dao.queryruleByNumber(id);
		v.clear();
		for(Iterator pit=ss.iterator();pit.hasNext();){
			Vector temp=new Vector();//创建临时变量
			Rule p=(Rule)pit.next();
			temp.add(false);
			temp.add(p.getId());
			temp.add(p.getName());
			temp.add(p.getWeight());
			v.add(temp);
			v_data=v;
		}
		Vector v_head=new Vector();
		v_head.add("");
		v_head.add("准则Id");
		v_head.add("准则名");
		v_head.add("权重");
		table.setModel(new DefaultTableModel(
				v_data,v_head
			) {
				Class[] columnTypes = new Class[] {
						Boolean.class,Object.class,Object.class,Object.class
				};
				 @Override
		            public boolean isCellEditable(int row, int column) {
					if(column==0) 
						 return true;
					 return false;
		            }
				
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		//内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		 // tcr.setHorizontalAlignment(JLabel.CENTER);
		  tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		  table.setDefaultRenderer(Object.class, tcr);
		((DefaultTableModel)table.getModel()).fireTableStructureChanged();//更新显示
		//table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//		lxzjtable.getValueAt(row, column);
	}
	
	/*
	 * 获取专家列表
	 */
	private void getExpert(){
		dao=new Dao();
		List <Expert> d;
		if(!keyWord.equals(""))
		d=dao.queryExpert(i,keyWord);
		else{d =dao.queryExpert(i);}
		Vector data=new Vector();
		i=i+10;
		for(Expert s:d){
			Vector temp=new Vector();//创建临时变量
			temp.add(s.getNumber());
			temp.add(s.getName());
			temp.add(s.getTitle());
			temp.add(s.getWorkUnit());
			data.add(temp);
		}
		Vector head=new Vector();
		head.add("编号");
		head.add("姓名");
		head.add("职称");
		head.add("工作单位");
		head.add("操作");
		lxzjtable.setModel(new DefaultTableModel(
				data,head
			) {
				Class[] columnTypes = new Class[] {
						Object.class,Object.class,Object.class,Object.class,Boolean.class
				};
				 @Override
		            public boolean isCellEditable(int row, int column) {
					 if(column==4)
						 return true;
					 return false;
		            }
				 public Class getColumnClass(int columnIndex) {
						return columnTypes[columnIndex];
					} 
			});
		//内容居中
				DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
				 // tcr.setHorizontalAlignment(JLabel.CENTER);
				  tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
				  lxzjtable.setDefaultRenderer(Object.class, tcr);
//		lxzjtable.getValueAt(row, column);
		((DefaultTableModel)lxzjtable.getModel()).fireTableStructureChanged();//更新显示
	}
	/*
	 * 记录勾选状态
	 */
	private void jilugouxuanzhuangtai(int H){
		//记录将当前页面的勾选状态
		List R=new ArrayList(); 
		for(int m=0;m<H;m++){
			try {
				if(lxzjtable.getValueAt(m, 4).equals(true)){
					//若勾选添加编号
				R.add((String)lxzjtable.getValueAt(m, 0));
				}else{
					R.add("null");
				}
				} catch (Exception e) {
					// TODO: handle exception
					R.add("null");
				}		
		}		
		map.put(yeshu, R);
		System.out.println("map存储成功");
	}
	/*
	 * 设置上一个页面
	 */
	private void shezhishangyegeyemian(){
		if(map.containsKey(yeshu)){
			List R=new ArrayList();
			R=map.get(yeshu);
			for(int N=0;N<10;N++){
				String S=(String) R.get(N);
				if(S!="null"){
					//以勾选
					lxzjtable.setValueAt(true, N, 4);
					System.out.println("设置成功");
				}
			}
			}
			//显示页数
			lxzjtextField_1.setText(""+yeshu);
	}
	/*
	 * 添加参会专家
	 */
	private void tianjiacanhuizhuanjia(){
		//map里装载了所有参会专家的专家编号，通过set集合 读取map的所有key键从而读取相应key中的value值
		Set set=new HashSet();
		set=map.keySet();
		//用于装载每一个key值对应的value，注意：map的value是一个List集合
		List value =new ArrayList();
		//遍历set
		for (Iterator iterator = set.iterator(); iterator.hasNext();)
			{ 
			//获取相对应key的value集合
				value=map.get(iterator.next());
			//输出value的大小，一定为10，因为之前存储每一页有几个专家被勾选时事这样记录的，如果勾选存储用户名，没有勾选则存储null，一页10条记录，所以一个value集合一定是10条记录
				System.out.println(value.size());
			//遍历value集合
				for(int p=0;p<value.size();p++){
					//如果value值不为null时，及为编号时
					if(!value.get(p).equals("null")){
						//读取用户名，赋给name变量
				          String number=(String) value.get(p);
				           //打印number变量值
						  System.out.println(number);
				          Expert_Descition expert=new Expert_Descition();
				          dao=new Dao();
				          //查找专家
				         Expert Exp= (Expert) dao.queryexpertByNumber(number);
						 expert.setDescition_id(id);
						 expert.setExpert_id(Exp.getId());
						 System.out.println(dao.saveObject(expert));
						}   
				}  
			 } 
		//存储成功
		System.out.println("参会专家存储完毕");
	} 
	/*
	 * 专家确认，输出已选专家
	 */
	public void getcanhuiExp(){
		v.removeAllElements();//清空Vector
		dao=new Dao();
		int j=0;
		//通过决策id查找出已选参会专家
		List ss=dao.queryexpertByDescitionId(id);
		//遍历全部结果
		for(Iterator pit=ss.iterator();pit.hasNext();){
			Expert_Descition e=(Expert_Descition)pit.next();
			Vector temp=new Vector();//创建临时变量
			Expert p=(Expert) dao.queryexpertByid(e.getExpert_id());;
			temp.add(p.getNumber());
			temp.add(p.getName());
			temp.add(p.getTitle());
			temp.add(p.getWorkUnit());
			v.add(temp);
			v_data=v;
			j++;
		}
		Vector v_head=new Vector();
		v_head.add("编号");
		v_head.add("姓名");
		v_head.add("职称");
		v_head.add("单位");
		v_head.add("操作");
		qrzjtable.setModel(new DefaultTableModel(
				v_data,v_head
			) {
				Class[] columnTypes = new Class[] {
						Object.class,Object.class, Object.class, Object.class,Object.class
				};
				 @Override
		            public boolean isCellEditable(int row, int column) {
		             if(column==4) {
		            	 return true;
		             }
					 
					 return false;
		            }
				
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
		((DefaultTableModel)qrzjtable.getModel()).fireTableStructureChanged();//更新显示
		for(int k=0;k<j;k++){
			qrzjtable.setValueAt("删除", k, 4);
		}
		((DefaultTableModel)qrzjtable.getModel()).fireTableStructureChanged();//更新显示
	}
	/*
	 * 专家打分表格设置
	 */
	public void setTable(){
		dao=new Dao();
		v_data.clear();
		Vector v_head=new Vector();
		v_head.add("");
		List ss=dao.queryruleByNumber(id);
		//遍历全部结果
		for(Iterator pit=ss.iterator();pit.hasNext();){
			Vector temp=new Vector();//创建临时变量
			Rule p=(Rule)pit.next();
			temp.add(p.getName());
			mn.add(p.getWeight());
			v_head.add(temp);
			col++;
		}
		List s=dao.queryschemeByNumber(id);
		for(Iterator pis=s.iterator();pis.hasNext();){
			Scheme p=(Scheme)pis.next();
			Vector temp=new Vector();//创建临时变量
			temp.add(p.getName());
			v_data.add(temp);
			row++;
		}
		System.out.println("方案数"+row);
		System.out.println("准则数"+col);
		//加入这一行就可以读取最后一行最后一列的数据
		zjdftable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		zjdftable.setModel(new DefaultTableModel(
				v_data,v_head
			) {
				 @Override
		            public boolean isCellEditable(int row, int column) {
		             if(column==0) {
		            	 return false;
		             }
					 
					 return true;
		            }
			});
//		lxzjtable.getValueAt(row, column);
		//内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		 // tcr.setHorizontalAlignment(JLabel.CENTER);
		  tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		  zjdftable.setDefaultRenderer(Object.class, tcr);	
		((DefaultTableModel)zjdftable.getModel()).fireTableStructureChanged();//更新显示
	}
	/*
	 * 专家差异值计算表格设置
	 */
	public void setTable2(){
		dao=new Dao();
		List ss=dao.queryruleByNumber(id);
		List<Double> m=new ArrayList();
		v_data.clear();
		Vector v_head=new Vector();
		v_head.add("");
		for(Iterator pit=ss.iterator();pit.hasNext();){
			Vector temp=new Vector();//创建临时变量
			Rule p=(Rule)pit.next();
			temp.add(p.getName());
			v_head.add(temp);
		}
		//遍历专家
		for(int cc=1;cc<El;cc++){
			//遍历专家
			for(int jj=cc+1;jj<=El;jj++){
				Vector tem=new Vector();//创建临时变量
				tem.add("d("+cc+","+jj+")");
				//遍历准则
				for(int dd=1;dd<=col;dd++){
					tem.add(this.difference(cc, jj, dd));
				}
				v_data.add(tem);
			}
		}
		System.out.println(v_data);
		//遍历准则
		
		jcjgtable.setModel(new DefaultTableModel(
				v_data,v_head
			) {
				 @Override
		            public boolean isCellEditable(int row, int column) {
					 return false;
		            }
			});
//		lxzjtable.getValueAt(row, column);
		//内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
		// tcr.setHorizontalAlignment(JLabel.CENTER);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
		jcjgtable.setDefaultRenderer(Object.class, tcr);	
		((DefaultTableModel)jcjgtable.getModel()).fireTableStructureChanged();//更新显示
		Map df=this.xiugai2();
		String kl = "";
		System.out.println(kl);
		for(int a:fangan){
			dao=new Dao();
			List<Scheme> ff=dao.queryschemeByNumber(id);
			kl=kl+"方案 "+ff.get(a-1).getName()+"  属性"+ll+"向"+df.get(a)+"靠拢\n";
		}
		System.out.println(kl);
		jcjglabel_14444.setText(muqianxiugaicishu+"");
		jcjgtextArea.setText(kl);
	}
	/*
	 * 存储专家打分（中间变量）
	 */
	public void saveES(){
		//方案序号-所有准则打分集合
			zjdftable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			Map<Integer, Map<Integer,Double>> map2=new HashMap<Integer, Map<Integer,Double>>();//记录专家的打分
			//记录特定列（准则）的记录
			for(int i=1;i<=col;i++){
				//记录特定行（方案）的分数，即方案j准则i的记录
				Map <Integer,Double>m=new HashMap<Integer,Double>();
				System.out.println(map2);
				for(int j=0;j<row;j++){
					//方案j 准则i的分数
					System.out.println("第"+(j+1)+"行第"+i+"列"+Double.parseDouble((String) zjdftable.getValueAt(j, i)));
					//m是存放指定准则i的所有方案的分数的Map
					m.put(j+1, Double.parseDouble((String)zjdftable.getValueAt(j, i)));
				}
				map2.put(i, m);
				System.out.println(map2);
				}
				System.out.println(map2);
				map3.put(ii, map2);
				System.out.println(map3);
				row=col=0;
				System.out.println("专家"+ii+"准则1方案一:"+map3.get(ii).get(1).get(1));
	}
	/*
	 * 决策计算,jj获得专家，关于准则d的所有打分集合s
	 */
	public List doit(int jj,int d){
		List <Double>s=new ArrayList();
		//方案f
		System.out.println("row:"+row);
		for(int f=1;f<=row;f++){
			double a=map3.get(jj).get(d).get(f);
			System.out.println("专家"+jj+"准则"+d+"方案"+f+":"+a);
			s.add(a);
		}
		System.out.println(s);
		return s;
	}
	/*
	 * 取出各准则的最大最小值之差
	 */
	public List dm(){
		 List <Double>mm=new ArrayList();							//准则的最大值与最小值的差
			//准则循环
			for(int dd=1;dd<=col;dd++){
				System.out.println("col"+col);
				System.out.println("dd"+dd);
				double max=0,min=100000;
				//专家循环
				for(int jj=1;jj<=El;jj++){
					List <Double>a=this.doit(jj, dd);
					//准则分数循环
					for(int kk=0;kk<a.size();kk++){
						//关于准则dd的专家cc与专家jj的所有方案差的和
						max=Math.max(max,a.get(kk));
						min=Math.min(min,a.get(kk));
					}
				}
				System.out.println("max="+max);
				System.out.println("min="+min);
				System.out.println("max-min:"+ (max-min));
				mm.add(max-min);
			}
			return mm;
	}
	/*
	 * 专家cc与其他专家的关于准则的差异值集合
	 * List t 调用dm（）函数取得每个属性的最大值最小值之差
	 */
	public double difference(int cc,int jj,int dd){
//		//专家cc和专家jj的差异值
//		List <List<Double>>m=new ArrayList();
		//获取最大值最小值之差集合
		List<Double> t=this.dm();
//		double f=0;
//		double c=0;
//		//准则循环
//		for(int dd=1;dd<=col;dd++){
//			double bb=0;
//			List <Double>G=new ArrayList();
//			//专家循环
//			for(int cc=1;cc<El;cc++){
//				for(int jj=cc+1;jj<=El;jj++){
//					double xx=0;
//					List <Double>a=this.doit(jj, dd);
//					List <Double>b=this.doit(cc, dd);
//					//最大值最小值的差
//					f=t.get(dd-1);
//					//准则分数循环
//					for(int kk=0;kk<a.size();kk++){
//						//关于准则dd的专家cc与专家jj的所有方案差的和
//						xx=+Math.abs(a.get(kk)-b.get(kk));
//					}
//				
//				bb=xx/(row*f);
//				System.out.println("bb"+bb);
//				G.add(bb);
//				}
//			}
//		m.add(G);
//		System.out.println(m);
//		}
//		System.out.println("m"+m);
//		return m;
		
		double xx=0;
		List <Double>a=this.doit(jj, dd);
		List <Double>b=this.doit(cc, dd);
		//最大值最小值的差
		double f=t.get(dd-1);
		//准则分数循环
		for(int kk=0;kk<a.size();kk++){
			//关于准则dd的专家cc与专家jj的所有方案差的和
			System.out.println(a.get(kk)-b.get(kk));
			xx=xx+Math.abs(a.get(kk)-b.get(kk));
			System.out.println("xx:"+xx);
		}
	//取两位小数点 4舍5入 
		BigDecimal   bn   =   new   BigDecimal(xx/(row*f)); 
		System.out.println(bn.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
		return (bn.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
	}
	/*
	 *决策的一致性 
	 */
	public double same(){
		List<Double> t=this.dm();
		double f=0;
		double max=0;
		int p=0,r=0;
		//专家循环
		for(int cc=1;cc<El;cc++){
			for(int jj=cc+1;jj<=El;jj++){
				double c=0;
				double bb=0;
				double xx=0;
				//准则循环
				for(int dd=1;dd<=col;dd++){
					xx=0;
					List <Double>a=this.doit(jj, dd);
					List <Double>b=this.doit(cc, dd);
					//最大值最小值的差
					f=t.get(dd-1);
					System.out.println(f);
					//准则分数循环
					for(int kk=0;kk<a.size();kk++){
						//关于准则dd的专家cc与专家jj的所有方案差的和
						xx=xx+Math.abs(a.get(kk)-b.get(kk));
						System.out.println(Math.abs(a.get(kk)-b.get(kk)));
						System.out.println(xx);
					}
					bb=xx/(row*f);
					System.out.println(bb);
					//专家cc与jj关于属性dd的差异值乘以权重
					bb=bb*(Double.parseDouble(mn.get(dd-1)));
					System.out.println(Double.parseDouble(mn.get(dd-1)));
					System.out.println(bb);
					c=c+bb;
					System.out.println(c);
				}
				max=Math.max(max, c);
				if(c==max){
					p=cc;
					r=jj;
				}
			}
		}
		Maxx.add(p);
		Maxx.add(r);
		System.out.println("Maxx"+Maxx);
		System.out.println("一致度"+(1-max));
		return (1-max);
	}
	/*
	 * 修改意见，计算出需要修改的专家与属性
	 */
	public int xiugai(){
		List<Double> t=this.dm();
		double f=0;
		double ss=0;
		double max=0;
		Map<Integer,Double> g=new HashMap<Integer,Double>();
		for(int dd=1;dd<col;dd++){
			double xx=0;
			List <Double>a=this.doit(Maxx.get(0), dd);
			List <Double>b=this.doit(Maxx.get(1), dd);
			//最大值最小值的差
			f=t.get(dd-1);
			//准则分数循环
			for(int kk=0;kk<a.size();kk++){
				//关于准则dd的专家cc与专家jj的所有方案差的和
				xx=xx+Math.abs(a.get(kk)-b.get(kk));
			}
			ss=xx/(row*f);
			if(ss>max){
				ll=dd;
			}
		}
		for(int cc=0;cc<Maxx.size();cc++){
			int kk=Maxx.get(cc);
			double bb=0;
			for(int jj=1;jj<=El;jj++){
				if(kk!=jj){
					List <Double>a=this.doit(jj, ll);
					List <Double>b=this.doit(kk, ll);
					//最大值最小值的差
					f=t.get(ll-1);
					//准则分数循环
					double hhj=0.0;
					for(int hh=0;hh<a.size();hh++){
						//关于准则dd的专家cc与专家jj的所有方案差的和
						hhj=hhj+Math.abs(a.get(hh)-b.get(hh));
					}
					bb=bb+hhj/(row*f);
					}
				}
			g.put(kk, bb);
			}
		if(g.get(Maxx.get(0))>g.get(Maxx.get(1))){
			return Maxx.get(0);
		}else{
			return Maxx.get(1);
		}
		
	}
	/*
	 * 修改意见，建议修改分数值
	 */
	public Map xiugai2(){
		Map<Integer,Double> hhq=new HashMap<Integer,Double>();
		int s=this.xiugai();
		Dao dao=new Dao();
		List<Expert_Descition> g=dao.queryexpertByDescitionId(id);
		jcjglabel_3.setText(((Expert)dao.queryexpertByid((g.get(s-1).getExpert_id()))).getName());
		for(int f=1;f<row;f++){
			boolean a=true;
			double b=0.0;
			for(int r=1;r<=El;){
				if(map3.get(s).get(ll).get(f)==Math.max(map3.get(s).get(ll).get(f),map3.get(r).get(ll).get(f))||map3.get(s).get(ll).get(f)==Math.min(map3.get(s).get(ll).get(f),map3.get(r).get(ll).get(f)))
					{
						r++;
					}else{
						r=El+1;
						a=false;
					}
			}
			if(a)
			{
				for(int h=1;h<=El;h++)
				{
					b=b+map3.get(h).get(ll).get(f);
				}
				b=b/El;
				BigDecimal   bn   =   new   BigDecimal(b);  
				hhq.put(f,bn.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue());
				fangan.add(f);
			}
		}
		return hhq;
	}
	//专家 方案
	public double savejf(int jj,int f){
			double a=0;
			System.out.println("row:"+row);
			//遍历准则
			for(int d=1;d<=col;d++){
				//System.out.println("专家"+jj+"准则"+d+"方案"+f+":");
				System.out.println("准则"+d+"  " + mn.get(d-1));
				System.out.println("专家"+jj+"准则"+d+"方案"+f+":"+map3.get(jj).get(d).get(f));
					 a=a+(map3.get(jj).get(d).get(f)*Double.parseDouble(mn.get(d-1)));
				System.out.println(map3.get(jj).get(d).get(f)*Double.parseDouble(mn.get(d-1)));
				System.out.println(a);
				}
			return a;
	    }
	 public List<Double> savef(){
		 List<Double> a=new ArrayList();
	    	int hg=0;
	    	int io=1;
	    	double max=0;
	    	//遍历方案
	    	for(int f=1;f<=row;f++){
	    		double b=0;
	    		//遍历专家
	    		for(int i=1;i<=El;i++){
	    			b=b+this.savejf(i, f);
	    		}
	    		BigDecimal   bn   =   new   BigDecimal(b/El);  
	    		a.add(bn.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
	    		if(b/El==Math.max(max, b/El)){
	    			max=Math.max(max, b/El);
	    			hg=f;
	    		}
	    	}
	    	dao=new Dao();
	    	List <Scheme>kkk=dao.queryschemeByNumber(id);
	    	for(Scheme s:kkk){
	    		if(io==hg){
	    			best=s.getName();
	    		}
	    		io++;
	    	}
	    	return a;
	    }

	 public void setTable3(){
		 int yy=0;
		 	dao=new Dao();
			List<Scheme> ss=dao.queryschemeByNumber(id);
			List<Double> m=new ArrayList();
			v_data.clear();
			Vector v_head=new Vector();
			Vector temp3=new Vector();//创建临时变量
			temp3.add("方案名");
			v_head.add(temp3);
			for(Iterator pit=ss.iterator();pit.hasNext();){
				Vector temp1=new Vector();//创建临时变量
				Scheme p=(Scheme)pit.next();
				temp1.add(p.getName());
				v_head.add(temp1);
			}
			Vector temp2=new Vector();//创建临时变量
			temp2.add("得分");
			for(double s:this.savef()){
				System.out.println(s);
				ss.get(yy).setScore(s+"");
				dao.updateObject(ss.get(yy));
				yy++;
				temp2.add(s);
			}
			v_data.add(temp2);
			zzjgtable.setModel(new DefaultTableModel(
					v_data,v_head
				) {
					 @Override
			            public boolean isCellEditable(int row, int column) {
			             if(column==0) {
			            	 return false;
			             }
						 return true;
			            }
				});	
			//内容居中
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
			// tcr.setHorizontalAlignment(JLabel.CENTER);
			tcr.setHorizontalAlignment(SwingConstants.CENTER);// 这句和上句作用一样
			zzjgtable.setDefaultRenderer(Object.class, tcr);	
			((DefaultTableModel)zzjgtable.getModel()).fireTableStructureChanged();//更新显示
			zzjglabel.setText(best);
			BigDecimal   bn   =   new   BigDecimal((this.same()));  
			zzjglabel_10.setText(bn.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue()+"");
	 }
	public static void main(String[] args) {
		new Frame2();
	}
}
