package zlbyzc.sub3.zlpj.fuzzy;

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
import zlbyzc.sub3.zlpj.fuzzy.FuzzyJDBC_wapper;


public class FuzzyResultPanel extends JPanel{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JFrame frame;
    private JPanel contentPane;
	
//	private JPanel paintPanel;
	private JPanel resultMatInPanel;//结果权重向量显示
	private JPanel conclusionMatInPanel;  //结论录入框
	private JTextArea jtf_conclusion;
	private JPanel buttonpanel;
	private JButton buttonsave;
	private JLabel jlb_conclusion;
	private JLabel resultweightlabel;
	private Box resultbox;
//	private JTextArea matInfo;
	Fuzzyresulttable fuzzyresulttable;

//	private Box vBox;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					
		    		String[] comment={"best","good","bad"};
		    		String[] mat={"0","0","0"};
		    		String fuzzyaim="服装评价";
		    		String fuzzy="各矩阵";
		    		String time = "2016-xx-xx";
					
					FuzzyResultPanel fuzzyresult = new FuzzyResultPanel(comment,mat,fuzzyaim,fuzzy,time);
					JFrame frame = new JFrame();
					frame.setContentPane(fuzzyresult);
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
	public FuzzyResultPanel(String[] comment,String[] mat,String fuzzyaim,String fuzzy,String time) {
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
		fuzzyresulttable = new Fuzzyresulttable(comment,mat);
		resultweightlabel = new JLabel(String.format("%s结果权重向量",fuzzyaim));

		
		//结论录入框
		conclusionMatInPanel.setLayout(new BoxLayout(conclusionMatInPanel, BoxLayout.X_AXIS));
		conclusionMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "结论录入"));
		jlb_conclusion = new JLabel("结论录入：");
		jtf_conclusion = new JTextArea(10,40);
		jtf_conclusion.setMinimumSize(new Dimension(300,80));
		jtf_conclusion.setMaximumSize(new Dimension(400,100));
		
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
		buttonsave = new JButton("保存");
		
		resultbox = Box.createVerticalBox();
		resultbox.add(resultweightlabel);
		resultbox.add(fuzzyresulttable);
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
				
				String fuzzyconclusion = jtf_conclusion.getText().trim();
                FuzzyJDBC_wapper mysql_ = new FuzzyJDBC_wapper("conn.ini");
				
//				Calendar ca = Calendar.getInstance();//自动获取当前日期

//				Date argueDate = new Date( ca.get(Calendar.YEAR),
//										   ca.get(Calendar.MONTH),
//										   ca.get(Calendar.DATE));
//				String argueDate = String.format("%s-%s-%s", ca.get(Calendar.YEAR),ca.get(Calendar.MONTH)+1,ca.get(Calendar.DATE));
				String insertCmd = String.format("INSERT %s values('%s','%s','%s','%s')", "fuzzy",fuzzyaim,time,fuzzy,fuzzyconclusion);
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




