package zlbyzc.sub3.zlpj.anp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import zlbyzc.sub3.zlpj.util.JDBC_wapper;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;

public class AnpResultPanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JFrame frame;
	private JPanel contentPane;
	private JScrollPane contentPaneC;
	
//	private JPanel paintPanel;
	private JPanel resultMatInPanel;//结果权重向量显示
	private JPanel conclusionMatInPanel;  //结论录入框
	
	private JPanel rp1;
	private JPanel rp2;
	private JPanel cp1;
	private JPanel cp2;
	private JTextArea jtf_conclusion;
	private JPanel buttonpanel;
	private JButton buttonsave;
	private JLabel resultweightLabel;
	private JLabel jlb_conclusion;
	private Box resultbox;
	private Box conclusionbox;
	Anpresulttable anpresulttable;

	private boolean hasSaved;

//	private Box vBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					String[] factors={"成本","维修","耐用性"};
					String[] plans={"美国车","欧洲车","日本车"};
					String[] mat={"0","0","0","0","0","0"};
					String anpaim="选车";
					String anp="表格如下";
					String time="2016-xx-xx";
					int gid =-1;
					AnpResultPanel anpresult = new AnpResultPanel(factors,plans,mat,anpaim,anp,time,gid);
					JFrame frame = new JFrame();
					frame.setContentPane(anpresult);
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();		
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AnpResultPanel(String[] factors,String[] plans,String[] mat, String anpaim, String anp,String time,int gid) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		contentPane =new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
//		setContentPane(contentPane);
		
//		matInfo = new JTextArea(30,40);	
//		paintPanel = new JPanel();	
		resultMatInPanel = new JPanel();
		conclusionMatInPanel = new JPanel();
		buttonpanel = new JPanel();
		rp1= new JPanel();
		rp2= new JPanel();
		cp1= new JPanel();
		cp2= new JPanel();

		//结果显示
		resultMatInPanel.setLayout(new GridLayout(1,3));
		resultMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "结果权重向量"));
		anpresulttable = new Anpresulttable(factors, plans, mat);
		resultweightLabel = new JLabel(String.format("%s结果权重向量",anpaim));
		resultbox = Box.createVerticalBox();
		resultbox.add(resultweightLabel);
		resultbox.add(anpresulttable);
		
		//结论录入框
		conclusionMatInPanel.setLayout(new GridLayout(1, 3));
		conclusionMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "结论录入"));
		jlb_conclusion = new JLabel("结论录入：");
		jtf_conclusion = new JTextArea(10,40);
		jtf_conclusion.setMinimumSize(new Dimension(300,80));
		jtf_conclusion.setMaximumSize(new Dimension(400,100));
		conclusionbox =Box.createHorizontalBox();
		conclusionbox.add(jlb_conclusion);
		conclusionbox.add(jtf_conclusion);
		
		buttonpanel.setLayout(new FlowLayout());
		buttonsave = new JButton("保存");
		
		
		resultMatInPanel.add(rp1);
		resultMatInPanel.add(resultbox);
		resultMatInPanel.add(rp2);

		conclusionMatInPanel.add(cp1);		 		
		conclusionMatInPanel.add(conclusionbox);
		conclusionMatInPanel.add(cp2);	
		
		buttonpanel.add(buttonsave);
		contentPane.add(resultMatInPanel);
		contentPane.add(conclusionMatInPanel);
		contentPaneC = new JScrollPane(contentPane);
		add("Center",contentPaneC);
		add("South",buttonpanel);
		
		hasSaved=false;
		buttonsave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String anpconclusion = jtf_conclusion.getText().trim();
				JDBC_wapper mysql_ = new JDBC_wapper("conn.ini");
				
				Calendar ca = Calendar.getInstance();//自动获取当前日期

//				Date argueDate = new Date( ca.get(Calendar.YEAR),
//										   ca.get(Calendar.MONTH),
//										   ca.get(Calendar.DATE));
				String argueDate = String.format("%s-%s-%s", ca.get(Calendar.YEAR),ca.get(Calendar.MONTH)+1,ca.get(Calendar.DATE));
				if(!hasSaved){
					String insertCmd = String.format("INSERT INTO `%s` (`anpaim`,`anpargueTime`,`anpModel`,`anpconclusion`,`gid`) values ('%s','%s','%s','%s',%d)", "anp",anpaim,time,anp,anpconclusion,gid);
					//System.out.println(insertCmd);	
					if (mysql_.insert(insertCmd)) {//插入记录 成功
						JOptionPane.showMessageDialog(conclusionMatInPanel, "保存成功！");	
					} else {
						JOptionPane.showMessageDialog(conclusionMatInPanel, "没有保存成功，请检查数据库配置！");	
					}
				}else{
					String insertCmd = String.format
							("UPDATE `%s` set  anpaim='%s',anpargueTime='%s',anpModel='%s',anpconclusion='%s' where gid=%d", 
									"anp",anpaim,time,anp,anpconclusion,gid);
					//System.out.println(insertCmd);	
					if (mysql_.insert(insertCmd)) {//插入记录 成功
						JOptionPane.showMessageDialog(conclusionMatInPanel, "保存成功！");	
					} else {
						JOptionPane.showMessageDialog(conclusionMatInPanel, "没有保存成功，请检查数据库配置！");	
					}
				}
				hasSaved=true;
//				mysql_.delDupRecords("anp");  //记录去重
				mysql_.close(); //关闭mysql
				
			}
		});	
	}
}
