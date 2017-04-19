package zlbyzc.sub3.zlpj.anp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.liukan.mgraph.util.dbIO;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import Jama.Matrix;
import zlbyzc.BasicRibbon;
import zlbyzc.sub3.zlpj.anp.AnpJTableMatrix;

//import ahp.Test.MyTableCellRenderer;

public class AnpDataPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JFrame frame;
	
	
    private JPanel contentPane;
    private JScrollPane contentPaneC;
	private JPanel factorMatInPanel;//准则层判断矩阵只有一个
	private JPanel plan2factorMatInPanel;  //方案对准则层判断矩阵有多个
	private JPanel factor2planMatInPanel; //准则对方案层判断矩阵
	
	private JPanel fp1;
	private JPanel fp2;
	private JPanel wp1;
	private JPanel wp2;
	private JPanel weightMatInPanel;
	private JPanel panelLayButton;
	
	//AnpJtable_CompMatrix1 factorTable1;
	private AnpfactorJtable factorTable1;
	//AnpJtable_CompMatrix1 factorTable2;
	private Vector<AnpJTableMatrix> plan2factorMatTableVec;
	private Vector<AnpJTableMatrix> factor2planMatTableVec;
	private AnpfactorJtable factorTable2;

	private int planNum;
	private int factorNum;
	
	//private Anp anp;
	private JButton buttonexample;
	private JButton buttonConfirm;
	private JButton buttonReset;
	/**
	 * Launch the application.
	 */
/*	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					String aim="选车";
					String[] factors={"成本","维修","耐用性"};
					String[] plans={"美国车","欧洲车","日本车"};
					String time="2016-xx-xx";
					AnpDataPanel anpdata = new AnpDataPanel(aim,factors,plans,time);
					JFrame frame = new JFrame();
					frame.setContentPane(anpdata);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
	}*/

	private BasicRibbon BR;

	/**
	 * Create the frame.
	 * @param bR 
	 */
	
	public AnpDataPanel(String aim, String[] factors,String[] plans,String time, BasicRibbon _BR) {
		setLayout(new BorderLayout());
		
		//contentPaneC.setLayout(new ScrollPaneLayout());
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
//		setLayout(new GridLayout(5,1));
		BR=_BR;
		factorMatInPanel = new JPanel();
		plan2factorMatInPanel = new JPanel();
		factor2planMatInPanel = new JPanel();
		weightMatInPanel=new JPanel();
		fp1= new JPanel();
		fp2= new JPanel();
		wp1= new JPanel();
		wp2= new JPanel();
		
		plan2factorMatTableVec = new Vector<AnpJTableMatrix>();
		factor2planMatTableVec = new Vector<AnpJTableMatrix>();
		planNum = plans.length;
		factorNum = factors.length;
		
		
		//画图区			
//		paintPanel.add(matInfo);
		
		//一个准则层判断矩阵
//		factorMatInPanel.setLayout(new BoxLayout(factorMatInPanel, BoxLayout.X_AXIS));
		factorMatInPanel.setLayout(new GridLayout(1,3));
		factorMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "输入准则权重矩阵"));	
		//factorTable1 = new AnpJtable_CompMatrix1(factors);
		factorTable1 = new AnpfactorJtable(factors);
		factorTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		//多个方案对准则层判断矩阵
		for(int i=0;i<factors.length;i++){
			AnpJTableMatrix planTable = new AnpJTableMatrix(factors[i],plans);
			plan2factorMatTableVec.add(planTable);	
		}
		//多个准则对方案层判断矩阵
		for(int i=0;i<plans.length;i++){
			AnpJTableMatrix factorTable = new AnpJTableMatrix(plans[i],factors);
			factor2planMatTableVec.add(factorTable);
		}
		
		int gridRow = 1; int gridCol = 1;
		if (factors.length<=5) {
			gridRow = 1;
			gridCol = factors.length;
		} else {
			gridRow = 2; //2行3列
			gridCol = 3;
		}
		plan2factorMatInPanel.setLayout(new GridLayout(gridRow, gridCol, 60, 60));		
		plan2factorMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "方案对准则判断矩阵"));
		
		int gridRow1 = 1; int gridCol1 = 1;
		if (plans.length<=5) {
			gridRow1 = 1;
			gridCol1 = plans.length;
		} else {
			gridRow1 = 2; //2行3列
			gridCol1 = 3;
		}
		factor2planMatInPanel.setLayout(new GridLayout(gridRow, gridCol, 60, 60));		
		factor2planMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "准则对方案判断矩阵"));
		
//		weightMatInPanel.setLayout(new BoxLayout(weightMatInPanel, BoxLayout.X_AXIS));
		weightMatInPanel.setLayout(new GridLayout(1, 3));
		weightMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "输入超矩阵权重矩阵"));	
		String[] factors1 = {"准则","方案"};
		factorTable2 = new AnpfactorJtable(factors1);
		factorTable2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		
		panelLayButton = new JPanel();
		panelLayButton.setLayout(new FlowLayout());
		buttonConfirm = new JButton("确定");
		buttonReset = new JButton("重置");
		buttonexample = new JButton("实例");
		
		//准则判断矩阵panel
//		int factorMatInPanelwide = (int) 1/3*factorMatInPanel.getWidth();
//		factorMatInPanel.add(Box.createHorizontalGlue());
//		factorMatInPanel.add(Box.createHorizontalStrut(factorMatInPanelwide));
//		factorMatInPanel.add(factorTable1);
//		factorMatInPanel.add(Box.createHorizontalStrut(factorMatInPanelwide));
//		factorMatInPanel.add(Box.createHorizontalGlue());
		factorMatInPanel.add(fp1);
		factorMatInPanel.add(factorTable1);
		factorMatInPanel.add(fp2);
		
		
		//方案对准则判断矩阵panel
		for (AnpJTableMatrix planTable : plan2factorMatTableVec) {
			plan2factorMatInPanel.add(planTable);
		}
		
		//准则对方案判断矩阵panel
		for (AnpJTableMatrix factorTable : factor2planMatTableVec) {
			factor2planMatInPanel.add(factorTable);
		}
		
		//超矩阵权重矩阵panel
//		weightMatInPanel.add(Box.createHorizontalGlue());
//		weightMatInPanel.add(Box.createHorizontalStrut(500));
//		weightMatInPanel.add(factorTable2);
//		weightMatInPanel.add(Box.createHorizontalStrut(500));
//		weightMatInPanel.add(Box.createHorizontalGlue());
		weightMatInPanel.add(wp1);
		weightMatInPanel.add(factorTable2);
		weightMatInPanel.add(wp2);
		
		panelLayButton.add(buttonexample);
//		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60))); 
		panelLayButton.add(buttonReset);
//		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonConfirm);
//		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));
		
		contentPane.add(factorMatInPanel);
		contentPane.add(plan2factorMatInPanel);
		contentPane.add(factor2planMatInPanel);
		contentPane.add(weightMatInPanel);
		contentPaneC = new JScrollPane(contentPane);
		//contentPaneC.add();
		add("Center",contentPaneC);
		add("South",panelLayButton);
				
		buttonConfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				double[][] factor_array2d = new double[factorNum][factorNum];
				double[][] weight_array2d = new double[2][2];
				double[][][] plan2factorArray3d = new double[factorNum][planNum][planNum];
				double[][][] factor2planArray3d = new double[planNum][factorNum][factorNum];
				factor_array2d = factorTable1.getMat1();
				for(int i=0;i<factorNum;i++){
					plan2factorArray3d[i] = plan2factorMatTableVec.get(i).getMat();
				}
				for(int i=0;i<planNum;i++){
					factor2planArray3d[i] = factor2planMatTableVec.get(i).getMat();
				}
					
		
				weight_array2d = factorTable2.getMat1();
				Anp anp = new Anp(aim,factors,plans);
				anp.read_factorMat(factor_array2d);
				anp.setPlan2factorMat(anp.read_matrix(plan2factorArray3d, factorNum));
				anp.setFactor2planMat(anp.read_matrix(factor2planArray3d, planNum));
				anp.read_weightMat(weight_array2d);
				
				if(!anp.checkone(new Matrix(factor_array2d))){
					JOptionPane.showMessageDialog(factorMatInPanel,"准则权重矩阵不满足列归一");
					return;
				}
				
				for(int i=0;i<factorNum;i++){
					if (!Anp.check_one_consistency(anp.getPlan2factorMat()[i])) {
						JOptionPane.showMessageDialog(plan2factorMatTableVec.get(i), "方案对准则打分矩阵不满足一致性！");
						return;
					}
				}
				
				for(int i=0;i<planNum;i++){
					if (!Anp.check_one_consistency(anp.getFactor2planMat()[i])) {
						JOptionPane.showMessageDialog(factor2planMatTableVec.get(i), "准则对方案打分矩阵不满足一致性！");
						return;
					}
				}
				
				if(!anp.checkone(new Matrix(weight_array2d))){
					JOptionPane.showMessageDialog(weightMatInPanel,"超矩阵权重矩阵不满足列归一");
					return;
				}
				
				Matrix result = anp.run_ahp(10);
				String msg = "所有判断矩阵均通过一致性检验...\n";
				msg += "各方案权重为：\n";
				for(int i = 0;i < factorNum + planNum;i++){
					if(i < factorNum){
						msg += String.format("%s: %.3f\n", factors[i],result.get(i, 0));
					}else{
						msg += String.format("%s: %.3f\n", plans[i - factorNum],result.get(i, 0));
					}
					
				}
				String[] mat=new String[factorNum + planNum];
				for(int i=0;i<factorNum + planNum;i++){
					mat[i]=String.format("%.3f",result.get(i, 0));	
				}
				
				int gid=-1;
				 try {
					 dbIO dbio=new dbIO(BR.setting.getDriver(),BR.setting.getConnURL("db_zlpj"));	    	      			    	
					    gid = Anpconstruct.c.gpanel.saveG2DB("",0,dbio);	
					    System.out.println("save as gid:" +gid);
				    	dbio.close();		    	
				    } catch ( Exception e1 ) {
				      System.err.println( e1.getClass().getName() + ": " + e1.getMessage() );
				      System.exit(0);
				    }
				 AnpInitFrm.conframe.dispose();
				 
				 removeAll();
				 add(new AnpResultPanel(factors,plans,mat,anp.getAim_name(),anp.toString(),time,gid));
				 revalidate();
				 repaint(); 
			}
		});
        buttonReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				factorTable1.reset();
				for (AnpJTableMatrix tableCmp : plan2factorMatTableVec) {
					tableCmp.reset();
				}
				for (AnpJTableMatrix tableCmp : factor2planMatTableVec) {
					tableCmp.reset();
				}
				factorTable2.reset();
			}
        });
        buttonexample.addActionListener(new ActionListener() {
        	
			 @Override
			 public void actionPerformed(ActionEvent e) {
				 String[][] factorvalues={{"0.3","0.2","0.6"},
							{"0.4","0.25","0.3"},
							{"0.3","0.55","0.1"}
							};
				 factorTable1.setMat(factorvalues);
				 String[][][] plan2factorvalues ={{{"1","5","3"},{"1/5","1","1/3"},{"1/3","3","1"}},
						 {{"1","5","2"},{"1/5","1","1/3"},{"1/2","3","1"}},
					     {{"1","1/5","1/3"},{"5","1","3"},{"3","1/3","1"}}		 						 
				 }; 
				 String[][][] factor2planvalues ={{{"1","3","4"},{"1/3","1","1"},{"1/4","1","1"}},
						 {{"1","1","1/2"},{"1","1","1/2"},{"2","2","1"}},
					     {{"1","2","1"},{"1/2","1","1/2"},{"1","2","1"}}		 						 
				 }; 
				 for(int i=0;i<factorNum;i++){
				 plan2factorMatTableVec.get(i).setMat(plan2factorvalues[i]);
				 }
				 for(int i=0;i<planNum;i++){
					 factor2planMatTableVec.get(i).setMat(factor2planvalues[i]);
					 }
				 String[][] weightvalues={{"0.5","1"},
						 {"0.5","0"}			 
				 };
				 factorTable2.setMat(weightvalues);
			}
         });
       
	}

}
