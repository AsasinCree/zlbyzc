package zlbyzc.sub3.zlpj.ahp;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.beans.PropertyVetoException;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.util.Vector;

import Jama.Matrix;
import db.MyPath;
import zlbyzc.BasicRibbon;
import zlbyzc.sub3.sub3inFrame;
import zlbyzc.sub3.zlpj.util.JDBC_wapper;
import zlbyzc.sub3.zlpj.util.JTable_CompMatrix;

import java.util.Calendar;
import java.util.Date;

import org.liukan.mgraph.mgraphx;
import org.liukan.mgraph.mgraphxEx;
import org.liukan.mgraph.mgraphxEx.*;
import org.liukan.mgraph.util.dbIO;

//import com.jogamp.newt.event.WindowAdapter;
import com.mxgraph.model.mxCell;
import org.liukan.mgraph.mnode;

public class AhpMatPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	JInternalFrame interframe;
	private String aimName;
	private String[] factorNames;
	private String[] planNames;

	private JPanel panelTop; //左边  panelLayFactorMat, 右边 panelDisp
	private JPanel panelLayFactorMat; //只装tableFactorMat
	private JPanel panelLayPlanMats;
	private JPanel panelLayButton;
	
	private JPanel panelDisp; //内含panelLayDispTitle和panelLayDistrict
	private mgraphx panelMgraphx; //层次图panel
	private JPanel panelLayDispTitle;
	private JPanel panelLayDistrict; //用来装panelMgraphx

	// table
	private JTable_CompMatrix tableFactorMat;
	private Vector<JTable_CompMatrix> tablePlanMatVec;

	// 三个按钮
	private JButton buttonConfirm;
	private JButton buttonReset;
	private JButton buttonSave;
	private JButton buttonExample;

	private AHP ahp;

	private boolean saveflag;

	private BasicRibbon BR;

	public AhpMatPanel(JInternalFrame interfrm, String aim, String[] factors, String[] plans,BasicRibbon _br) {
		interframe = interfrm;
		BR = _br;
		this.aimName = aim;
		this.factorNames = factors;
		this.planNames = plans;
		
		initializeComponent();
		layoutComponent();
		
		interframe.setContentPane(this);
 		interframe.pack();
 		interframe.setVisible(true);
 		try {
			interframe.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
 		
//		System.out.println("interframe w: "+interframe.getWidth()+"  h: "+interframe.getHeight());
//		System.out.println("panelLayFactorMat w: "+panelLayFactorMat.getWidth()+"  h: "+panelLayFactorMat.getHeight());
//		System.out.println("panelDisp w: "+panelDisp.getWidth()+"  h: "+panelDisp.getHeight());
//		System.out.println("panelLayDistrict w: "+panelLayDistrict.getWidth()+"  h: "+panelLayDistrict.getHeight());
	
		panelMgraphx = AhpMatPanel.creatDispPanel(panelLayDistrict.getWidth(), panelLayDistrict.getHeight(), aim, factors, plans);

		//updateMgraphx(panelMgraphx, panelMgraphx.getWidth(),  panelMgraphx.getHeight(), aim, factors, plans);
//		System.out.println("panelMgraphx w: "+panelMgraphx.getWidth()+"  h: "+panelMgraphx.getHeight());
		panelLayDistrict.add(panelMgraphx,BorderLayout.CENTER);
		
		setEvents();
	}

	private boolean isExample(){
		String aim = "研发新武器";
		String[] factors = { "杀伤力", "射程", "资金投入", "研发周期", "技术难度" };
		String[] plans = { "武器甲", "武器乙", "武器丙"};	
		if (!this.aimName.equals(aim) || this.factorNames.length!=factors.length || this.planNames.length!=plans.length) {
			return false;
		}
		for (int i = 0; i < factors.length; i++) {
			if (!this.factorNames[i].equals(factors[i])) {
				return false;
			} 		
		}
		for (int i = 0; i < plans.length; i++) {
			if (!this.planNames[i].equals(plans[i])) {
				return false;
			} 		
		}
		return true;
	}
	
	private void initializeComponent() {

		setLayout(new BorderLayout());
		
		panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());
		
		panelDisp = new JPanel();
		panelDisp.setLayout(new BorderLayout());
		
		panelLayDistrict = new JPanel();	
		panelLayDistrict.setLayout(new BorderLayout());

		panelLayDistrict.setPreferredSize(new Dimension(600, 300));	
		panelLayDispTitle = new JPanel();
		panelLayDispTitle.setLayout(new BorderLayout());		

		// 准则层判断矩阵 panel
		panelLayFactorMat = new JPanel();
		panelLayFactorMat.setLayout(new BorderLayout());

		panelLayFactorMat.setPreferredSize(new Dimension(600, 300));	
		tableFactorMat = new JTable_CompMatrix(aimName, factorNames);

		panelLayFactorMat.setLayout(new BoxLayout(panelLayFactorMat, BoxLayout.X_AXIS));
		panelLayFactorMat.setBorder(new TitledBorder(new EtchedBorder(), "准则层判断矩阵"));

		// 方案层判断矩阵 panel
		panelLayPlanMats = new JPanel();
		tablePlanMatVec = new Vector<JTable_CompMatrix>();
		for (int i = 0; i < factorNames.length; i++) {
			JTable_CompMatrix tableCmpMat = new JTable_CompMatrix(factorNames[i], planNames);
			tablePlanMatVec.add(tableCmpMat);
		}

		int gridRow = 1;
		int gridCol = 1;
		if (factorNames.length <= 4) {
			gridRow = 1;
			gridCol = factorNames.length;
		} else {
			gridRow = 2; // 2行3列
			gridCol = 3;
		}
		panelLayPlanMats.setLayout(new GridLayout(gridRow, gridCol, 60, 60));
		panelLayPlanMats.setBorder(new TitledBorder(new EtchedBorder(), "方案层判断矩阵"));

		// 保存/重置/确定 panel
		panelLayButton = new JPanel();
		panelLayButton.setLayout(new FlowLayout());

		buttonExample = new JButton("实例");
		buttonReset = new JButton("重置");
		buttonConfirm = new JButton("确定");
		buttonSave = new JButton("保存");
		
		saveflag = false;
		buttonSave.setEnabled(false);

		buttonExample.setEnabled(true); // 保存按钮初始状态	
		
		if (isExample()) {
			buttonExample.setEnabled(true);
		}
		else{
			buttonExample.setEnabled(false);
		}
	}

	public void layoutComponent() {		
		JLabel labelDisplay = new JLabel("层次结构图",JLabel.CENTER);
		Font font=new Font("宋体",Font.BOLD,25);   
		labelDisplay.setFont(font);  
		panelLayDispTitle.add(labelDisplay);

		// 准则层判断矩阵 panel
//		panelLayFactorMat.add(Box.createHorizontalGlue());
//		panelLayFactorMat.add(Box.createHorizontalStrut(50));
		panelLayFactorMat.add(tableFactorMat,BorderLayout.CENTER);
//		panelLayFactorMat.add(Box.createHorizontalStrut(50));
//		panelLayFactorMat.add(Box.createHorizontalGlue());		
		//panelLayFactorMat.add(panelDisp,BorderLayout.EAST);
		
		panelDisp.add(panelLayDispTitle,BorderLayout.NORTH);
		panelDisp.add(panelLayDistrict,BorderLayout.EAST);
		
		panelTop.add(panelLayFactorMat,BorderLayout.CENTER);
		panelTop.add(panelDisp,BorderLayout.EAST);

		// 方案层判断矩阵 panel
		for (JTable_CompMatrix tableCmpMat : tablePlanMatVec) {
			panelLayPlanMats.add(tableCmpMat);
		}

		// 保存/重置/确定 panel
		panelLayButton.add(buttonExample);
		panelLayButton.add(buttonReset);
		panelLayButton.add(buttonConfirm);
		panelLayButton.add(buttonSave);

		add(panelTop,BorderLayout.NORTH);
		add(panelLayPlanMats,BorderLayout.CENTER);
		add(panelLayButton,BorderLayout.SOUTH);	
	}
	
	public void buttonExampleEnable() {
		buttonExample.setEnabled(true);
	}

	private void setEvents() {

		// 监听确定按钮
		buttonConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int factorNum = factorNames.length;
				int planNum = planNames.length;
				double[][] factor_array2d = new double[factorNum][factorNum];
				double[][][] plan_array3d = new double[factorNum][planNum][planNum];
				factor_array2d = tableFactorMat.getMat();
				for (int i = 0; i < factorNum; i++) {
					plan_array3d[i] = tablePlanMatVec.get(i).getMat();
				}

				ahp = new AHP(aimName, factorNames, planNames);
				ahp.read_factorMat(factor_array2d);
				ahp.read_planMat(plan_array3d);

				if (!ahp.getFactorMat().check_consistency()) {
					JOptionPane.showMessageDialog(tableFactorMat, "准则层判断矩阵不满足一致性！");
					return;
				}
				for (int i = 0; i < ahp.getFactorNum(); i++) {
					if (!ahp.getPlanMat()[i].check_consistency()) {
						JOptionPane.showMessageDialog(tablePlanMatVec.get(i), "该判断矩阵不满足一致性！");
						return;
					}
				}

				Matrix weightMat = ahp.run_ahp();

				String msg = "所有判断矩阵均通过一致性检验!\n\n";
				msg += "各方案权重为：\n";
				for (int i = 0; i < planNames.length; i++) {
					msg += String.format("%s: %.2f\n", planNames[i], weightMat.get(i, 0));
				}
				JOptionPane.showMessageDialog(panelLayFactorMat, msg);

				// 使能 buttonSave
				saveflag = true;
				buttonSave.setEnabled(true);
			}
		});

		// 监听重置按钮
		buttonReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tableFactorMat.reset();
				for (JTable_CompMatrix tableCmp : tablePlanMatVec) {
					tableCmp.reset();
				}
				buttonExample.setEnabled(true);
			}
		});

		// 监听保存按钮
		buttonSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!saveflag) {
					return;
				}
				//保存ahp层次结构图	
				int gid=-1;
				 try {
					 dbIO dbio=new dbIO(BR.setting.getDriver(),BR.setting.getConnURL("db_zlpj"));	
					    	      			    	
					    gid = panelMgraphx.saveG2DB("",0,dbio);	
					    System.out.println("save as gid:" +gid);
				    	dbio.close();		    	
				    } catch ( Exception e1 ) {
				      System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
				      System.exit(0);
				    }
				 
				//保存ahp模型
				JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");

				Calendar ca = Calendar.getInstance();// 自动获取当前日期

				// Date argueDate = new Date( ca.get(Calendar.YEAR),
				// ca.get(Calendar.MONTH),
				// ca.get(Calendar.DATE));
				 
				String argueDate = String.format("%s-%s-%s", ca.get(Calendar.YEAR), ca.get(Calendar.MONTH) + 1,
						ca.get(Calendar.DATE));
				String insertCmd = String.format("INSERT INTO `%s` (`aim`,`argueDate`,`ahpModel`,`gid`) values ('%s','%s','%s',%d)", "ahp", ahp.getAim_name(), argueDate, ahp, gid);
				if (mysql_.insert(insertCmd)) {// 插入记录 成功
					JOptionPane.showMessageDialog(panelLayPlanMats, "保存成功！");
				} else {
					JOptionPane.showMessageDialog(panelLayPlanMats, "没有保存成功，请检查数据库配置！");
				}

				//mysql_.delDupRecords("ahp"); // 记录去重。注意：使用该函数时，会将auto-increment和primaryKey的属性消除！
				mysql_.close(); // 关闭mysql				

				buttonSave.setEnabled(false);
				saveflag = false;
			}
		});
		
		//示例
		buttonExample.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String aim = "研发新武器";
				String[] factors = { "杀伤力", "射程", "资金投入", "研发周期", "技术难度" };
				String[] plans = { "武器甲", "武器乙", "武器丙"};	


				String[][] factor_array ={{"1"   ,"1/2", "4", "3",  "3"},
						{"2"    ,"1" , "7", "5",  "5"},
						{"1/4" ,"1/7", "1", "1/2","1/3"},
						{"1/3" ,"1/5", "2", "1",  "1"},
						{"1/3" ,"1/5", "3", "1",  "1"}};
				
				String[][][] plan_array3d={{{"1","2","5"},      {"1/2","1","2"},  {"1/5","1/2","1"}},
						{{"1","1/3","1/8"},{"3","1","1/3"},   {"8","3","1"}},
						{{"1","1","3"},      {"1","1","3"},      {"1/3","1/3","1"}},
						{{"1","3","4"},      {"1/3","1","1"},   {"1/4","1","1"}},
						{{"1","1","1/4"},   {"1","1","1/4"},   {"4","4","1"}}  
						};
				get_tableFactorMat().setMat(factor_array);
				for (int i = 0; i < plan_array3d.length; i++) {
					get_tablePlanMatVec().get(i).setMat(plan_array3d[i]);
				}
				
				buttonExample.setEnabled(false);
				saveflag = false;
				buttonSave.setEnabled(false);
			}
		});
	}

	public JTable_CompMatrix get_tableFactorMat(){return tableFactorMat;}
	public Vector<JTable_CompMatrix> get_tablePlanMatVec() {return tablePlanMatVec;}
	
	static public mgraphx creatDispPanel(int gpanel_width,int gpanel_height,String aimName, String[] factorNames, String[] planNames){
		
		int factorsNum = factorNames.length;
		int plansNum = planNames.length;

		int factor_xs[] = new int[factorsNum];
		int factor_ys[] = new int[factorsNum];
		int plan_xs[] = new int[plansNum];
		int plan_ys[] = new int[plansNum];
		int aim_x = gpanel_width/2;
		int aim_y = (int)(gpanel_height/16.0);
		
		computeLayout(gpanel_width, gpanel_height, factorsNum, plansNum, factor_xs, factor_ys, plan_xs, plan_ys);
		
		mgraphx c=new mgraphx(false,22,22,true);
		
		Object aimNode = c.addNode(aimName,aim_x,aim_y);
		
		Object[] factorNodes = new Object[factorsNum];
		for (int i = 0; i < factorsNum; i++) {
			factorNodes[i] = c.addNode(factorNames[i],factor_xs[i],factor_ys[i]);
		}
		
		Object[] planNodes = new Object[plansNum];
		for (int i = 0; i < plansNum; i++) {
			planNodes[i] = c.addNode(planNames[i],plan_xs[i],plan_ys[i]);
		}
		
		for (int i = 0; i < factorsNum; i++) {
			c.addEdge("",(mxCell)factorNodes[i],(mxCell)aimNode);
			for (int j = 0; j < plansNum; j++) {
				c.addEdge("",(mxCell)planNodes[j],(mxCell)factorNodes[i]);				
			}
		}
		return c;
	}
	
private void updateMgraphx(mgraphx panelMgraphx,int gpanel_width,int gpanel_height,String aimName, String[] factorNames, String[] planNames){
		
		int factorsNum = factorNames.length;
		int plansNum = planNames.length;

		int factor_xs[] = new int[factorsNum];
		int factor_ys[] = new int[factorsNum];
		int plan_xs[] = new int[plansNum];
		int plan_ys[] = new int[plansNum];
		int aim_x = gpanel_width/2;
		int aim_y = (int)(gpanel_height/4.0);
		
		computeLayout(gpanel_width, gpanel_height, factorsNum, plansNum, factor_xs, factor_ys, plan_xs, plan_ys);
		
		//mgraphx c=new mgraphx(false,22,45,true);
		
		Object aimNode = panelMgraphx.addNode(aimName,aim_x,aim_y);
		
		Object[] factorNodes = new Object[factorsNum];
		for (int i = 0; i < factorsNum; i++) {
			factorNodes[i] = panelMgraphx.addNode(factorNames[i],factor_xs[i],factor_ys[i]);
		}
		
		Object[] planNodes = new Object[plansNum];
		for (int i = 0; i < plansNum; i++) {
			planNodes[i] = panelMgraphx.addNode(planNames[i],plan_xs[i],plan_ys[i]);
		}
		
		for (int i = 0; i < factorsNum; i++) {
			panelMgraphx.addEdge("",(mxCell)factorNodes[i],(mxCell)aimNode);
			for (int j = 0; j < plansNum; j++) {
				panelMgraphx.addEdge("",(mxCell)planNodes[j],(mxCell)factorNodes[i]);				
			}
		}
	}
	
	static void computeLayout(int gpanel_width, int gpanel_height, int factorsNum, int plansNum, int[] factor_xs,
			int[] factor_ys, int[] plan_xs, int[] plan_ys) {
		
		int w = (int)(gpanel_width/4.0*3);
		int hGap;
		if (factorsNum>=plansNum) {
			hGap = (int)(gpanel_width/factorsNum);
		} else {
			hGap = (int)(gpanel_width/plansNum);
		}

		int firstX = (gpanel_width / 2) - factorsNum / 2 * hGap;
		if (factorsNum % 2 == 0)
			firstX += hGap / 2;
		for (int i = 0; i < factorsNum; i++) {
			factor_xs[i] = firstX + i * hGap;
			factor_ys[i] = gpanel_height / 2;
		}

		firstX = (gpanel_width / 2) - plansNum / 2 * hGap;
		if (plansNum % 2 == 0)
			firstX += hGap / 2;
		for (int i = 0; i < plansNum; i++) {
			plan_xs[i] = firstX + i * hGap;
			plan_ys[i] = (int) (gpanel_height *7 / 8.0);
		}
		
	}

	

}
