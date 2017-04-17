package zlbyzc.sub3.zlpj.dea;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;


public class DeaResultPanel extends JPanel{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;

    private JPanel contentPane;
	
//	private JPanel paintPanel;
	private JPanel resultMatInPanel;//结果权重向量显示
	private JPanel conclusionMatInPanel;  //结论录入框
	private JPanel buttonpanel;
	private JTextArea jtf_conclusion;
	private JLabel resultlabel;
	private Box resultbox;

	private JButton buttonsave;
//	private JTextArea matInfo;
	Dearesulttable dearesulttable;

//	private Box vBox;
	
	



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					
		    		String[] names={"分理处1","分理处2","分理处3"};
		    		String[] resultstr={"0","0","0"};
					String deaaim="银行各分理处";
					String dea="各矩阵值";
					String time="2016-xx-xx";
					DeaResultPanel dearesult = new DeaResultPanel(names,resultstr,deaaim,dea,time);
					JFrame frame = new JFrame();
					frame.setContentPane(dearesult);
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
	public DeaResultPanel(String[] names,String[] resultstr,String deaaim,String dea,String time) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		setContentPane(contentPane);
		
		
//		matInfo = new JTextArea(30,40);
//		paintPanel = new JPanel();	
		resultMatInPanel = new JPanel();
		conclusionMatInPanel = new JPanel();
		buttonpanel = new JPanel();
	
		
		
		
		//画图区			
//		paintPanel.add(matInfo);
		
		
		//结果显示
		resultMatInPanel.setLayout(new BoxLayout(resultMatInPanel, BoxLayout.X_AXIS));
		resultMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "结果权重向量"));
		dearesulttable = new Dearesulttable(names,resultstr);
		resultlabel = new JLabel(String.format("%sDEA有效性值",deaaim));
		
		//结论录入框
		conclusionMatInPanel.setLayout(new BoxLayout(conclusionMatInPanel, BoxLayout.X_AXIS));
		conclusionMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "结论录入"));
			
		JLabel jlb_conclusion = new JLabel("结论录入：");
		jtf_conclusion = new JTextArea(10,40);
		jtf_conclusion.setMinimumSize(new Dimension(300,80));
		jtf_conclusion.setMaximumSize(new Dimension(400,100));

		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
		buttonsave = new JButton("保存");
		
		resultbox = Box.createVerticalBox();
		resultbox.add(resultlabel);
		resultbox.add(dearesulttable);
		resultMatInPanel.add(Box.createHorizontalGlue());
		resultMatInPanel.add(Box.createHorizontalStrut(500));
		resultMatInPanel.add(resultbox);
		resultMatInPanel.add(Box.createHorizontalStrut(500));
		resultMatInPanel.add(Box.createHorizontalGlue());
		
		conclusionMatInPanel.add(Box.createHorizontalGlue());
		conclusionMatInPanel.add(Box.createHorizontalStrut(500));
		conclusionMatInPanel.add(jlb_conclusion);		 		
		conclusionMatInPanel.add(jtf_conclusion);
		conclusionMatInPanel.add(Box.createHorizontalStrut(500));
		conclusionMatInPanel.add(Box.createHorizontalGlue());
//		contentPane.add(vBox,BorderLayout.CENTER);
//		contentPane.add(paintPanel, BorderLayout.EAST);
		buttonpanel.add(buttonsave);
		buttonpanel.add(Box.createRigidArea(new Dimension(20, 60)));
		
		
		add(resultMatInPanel);
		  	
		add(conclusionMatInPanel);
		
		add(buttonpanel);
		
		
		buttonsave.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent arg0) {
				String deaconclusion = jtf_conclusion.getText().trim();
				DeaJDBC_wapper mysql_ = new DeaJDBC_wapper("conn.ini");
//				Calendar ca = Calendar.getInstance();//自动获取当前日期

//				Date argueDate = new Date( ca.get(Calendar.YEAR),
//										   ca.get(Calendar.MONTH),
//										   ca.get(Calendar.DATE));
//				String argueDate = String.format("%s-%s-%s", ca.get(Calendar.YEAR),ca.get(Calendar.MONTH)+1,ca.get(Calendar.DATE));
				String insertCmd = String.format("INSERT %s values('%s','%s','%s','%s')", "dea",deaaim,time,dea,deaconclusion);
				//System.out.println(insertCmd);	
				if (mysql_.insert(insertCmd)) {//插入记录 成功
					JOptionPane.showMessageDialog(conclusionMatInPanel, "保存成功！");	
				} else {
					JOptionPane.showMessageDialog(conclusionMatInPanel, "没有保存成功，请检查数据库配置！");	
				}
				
				mysql_.delDupRecords("fuzzy");  //记录去重
				mysql_.close(); //关闭mysql
				
			}
		});

		
		
	}
	

}
