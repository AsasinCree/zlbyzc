package zlbyzc.sub3.zlpj.ahp;

import zlbyzc.BasicRibbon;
import zlbyzc.sub3.*;

import java.awt.BorderLayout;
import java.awt.Component;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.JDatePicker;

public class AhpInputSub3Frame extends sub3inFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	//private BasicRibbon frame;
	private JPanel panelContent; //
	
	private JPanel panelLayInputTips;
	private JPanel panelLayAimName;
	private JPanel panelLayFactorNames;
	private JPanel panelLayPlanNames;
	private JPanel panelLayArgueTime;
	private JPanel panelLayTaskLocation;
	private JPanel panelLayButton;
	
	//四个标签
	private JLabel labelInputTips;
	private JLabel labelAimName;
	private JLabel labelFactorNames;
	private JLabel labelPlanNames;
	private JLabel labelArgueTime;
	private JLabel labelTaskLocation;
	
	//三个文本框、日期选取框、地点下拉框
	private JTextField textfieldAimName;
	private JTextField textfieldFactorNames;
	private JTextField textfieldPlanNames;
	private JDatePicker datePickerArgueTime;
	private JComboBox<String> comboBoxTaskLocation;
	
	//三个按钮
	private JButton buttonConfirm;
	private JButton buttonReset;
	private JButton buttonExample;
	

	
	public AhpInputSub3Frame( ){
		super("",true,true,true,true);
		initializeComponent();
		layoutComponent();

		this.setVisible(true);
		this.pack();

	}
	
	
	private void initializeComponent() {
		
		//JPanel设置
		panelContent = new JPanel();
		panelContent.setLayout(new BoxLayout(panelContent, BoxLayout.Y_AXIS));
		
		panelLayInputTips = new JPanel();
		panelLayAimName = new JPanel();
		panelLayFactorNames = new JPanel();
		panelLayPlanNames = new JPanel();
		panelLayArgueTime = new JPanel();
		panelLayTaskLocation = new JPanel();
		panelLayButton = new JPanel();
		
		panelLayInputTips.setLayout(new BoxLayout(panelLayInputTips, BoxLayout.X_AXIS));
		panelLayAimName.setLayout(new BoxLayout(panelLayAimName, BoxLayout.X_AXIS));
		panelLayFactorNames.setLayout(new BoxLayout(panelLayFactorNames, BoxLayout.X_AXIS));
		panelLayPlanNames.setLayout(new BoxLayout(panelLayPlanNames, BoxLayout.X_AXIS));
		panelLayArgueTime.setLayout(new BoxLayout(panelLayArgueTime, BoxLayout.X_AXIS));
		panelLayTaskLocation.setLayout(new BoxLayout(panelLayTaskLocation, BoxLayout.X_AXIS));
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));

		
		//labelInputTips = new JLabel("<html>提示：<br>目标只能有一个，准则与方案不能超过6个，中间以空格隔开！<html>");
		labelInputTips = new JLabel(" 提示： 目标只能有一个,中间以空格隔开！");
		labelAimName = new JLabel("目标");
		labelFactorNames = new JLabel("准则");
		labelPlanNames = new JLabel("方案");
		labelArgueTime = new JLabel("研讨日期");
		labelTaskLocation = new JLabel("研讨地点");
		
		textfieldAimName = new JTextField();
		textfieldFactorNames = new JTextField();
		textfieldPlanNames = new JTextField();
		
		datePickerArgueTime = new JDatePicker();
		
		comboBoxTaskLocation = new JComboBox<String> ();
		comboBoxTaskLocation.setEditable(true);	//可输入。得到输入值的方法： getEditor().getItem(); 
		comboBoxTaskLocation.addItem("武汉");
		comboBoxTaskLocation.addItem("北京");
		comboBoxTaskLocation.addItem("深圳");
		comboBoxTaskLocation.addItem("上海");
		comboBoxTaskLocation.setSelectedItem(null);
		
		textfieldAimName.setMinimumSize(new Dimension(200,40));
		textfieldAimName.setMaximumSize(new Dimension(250,40));
		textfieldFactorNames.setMinimumSize(new Dimension(200,40));
		textfieldFactorNames.setMaximumSize(new Dimension(250,40));
		textfieldPlanNames.setMinimumSize(new Dimension(200,40));
		textfieldPlanNames.setMaximumSize(new Dimension(250,40));
		((Component) datePickerArgueTime).setMinimumSize(new Dimension(100,25));
		((Component) datePickerArgueTime).setMaximumSize(new Dimension(150,30));
		comboBoxTaskLocation.setMinimumSize(new Dimension(100,25));
		comboBoxTaskLocation.setMaximumSize(new Dimension(150,30));
		
		buttonConfirm = new JButton("确定");
		buttonReset = new JButton("重置");
		buttonExample = new JButton("示例");
		
		buttonConfirm.addActionListener(this);
		buttonReset.addActionListener(this);
		buttonExample.addActionListener(this);
		
	}

	public void layoutComponent() {

		//panelLayInputTips.add(Box.createHorizontalStrut(88));
		panelLayInputTips.add(labelInputTips);
		
		panelLayAimName.add(labelAimName);		
		panelLayAimName.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayAimName.add(textfieldAimName);
		
		panelLayFactorNames.add(labelFactorNames);		
		panelLayFactorNames.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayFactorNames.add(textfieldFactorNames);
		
		panelLayPlanNames.add(labelPlanNames);		
		panelLayPlanNames.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayPlanNames.add(textfieldPlanNames);		
		
		panelLayArgueTime.add(labelArgueTime);
		panelLayArgueTime.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayArgueTime.add((Component) datePickerArgueTime);
		
		panelLayTaskLocation.add(labelTaskLocation);
		panelLayTaskLocation.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayTaskLocation.add(comboBoxTaskLocation);
		
		panelLayButton.add(buttonConfirm);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonReset);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonExample);						
	
		panelContent.add(Box.createVerticalGlue() );
		panelContent.add(panelLayInputTips);
		panelContent.add(Box.createVerticalStrut(10));  	
		panelContent.add(panelLayAimName);
		panelContent.add(Box.createVerticalStrut(10));  		
		panelContent.add(panelLayFactorNames);
		panelContent.add(Box.createVerticalStrut(10));  	
		panelContent.add(panelLayPlanNames);
		panelContent.add(Box.createVerticalStrut(10)); 		
//		panelContent.add(panelLayArgueTime);
//		panelContent.add(Box.createVerticalStrut(10));  		
//		panelContent.add(panelLayTaskLocation);
//		panelContent.add(Box.createVerticalStrut(10));  		
		panelContent.add(panelLayButton);
		panelContent.add( Box.createVerticalGlue() );		
		
		this.add(panelContent);

	}
	
	//输入参数正确时返回true，否则返回false，且会弹出信息提示窗口。
	private boolean checkInput(){
		boolean inputIsOk=false;
		String aim_name = textfieldAimName.getText();
		String factor_name = textfieldFactorNames.getText();
		String plan_name = textfieldPlanNames.getText();
		if(aim_name.trim().isEmpty())
			JOptionPane.showMessageDialog(null, "目标不能为空！", "",JOptionPane.WARNING_MESSAGE);//"参数错误"去掉
		else if(aim_name.trim().split("\\s+").length!=1)
			JOptionPane.showMessageDialog(this, "目标只能有一个！", "",JOptionPane.WARNING_MESSAGE);
		else if(factor_name.trim().isEmpty())
			JOptionPane.showMessageDialog(this, "准则不能为空！", "",JOptionPane.WARNING_MESSAGE);
		else if(factor_name.trim().split("\\s+").length>10)
			JOptionPane.showMessageDialog(this, "准则不能超过10个！", "",JOptionPane.WARNING_MESSAGE);
		else if(plan_name.trim().isEmpty())
			JOptionPane.showMessageDialog(this, "方案不能为空！", "",JOptionPane.WARNING_MESSAGE);
		else if(plan_name.trim().split("\\s+").length>10)
			JOptionPane.showMessageDialog(this, "方案不能超过10个！", "",JOptionPane.WARNING_MESSAGE);
		else inputIsOk=true;
		return inputIsOk;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == buttonConfirm) {
			//frame.setContentPane(new AhpInputPannel(swotTask));
			
			int year = datePickerArgueTime.getModel().getYear()-1900;
			int month = datePickerArgueTime.getModel().getMonth();
			int day = datePickerArgueTime.getModel().getDay();
			
			Date dateTemp = new Date(year,month,day);
			
			//System.out.println(dateTemp);

			
			if(checkInput()){//输入正确
				String aim_name = textfieldAimName.getText().trim();
				String[] factor_names = textfieldFactorNames.getText().trim().split("\\s+");
				String[] plan_names = textfieldPlanNames.getText().trim().split("\\s+");
				//this.dispose();
				AhpMatPanel panelAhpMat = new AhpMatPanel(this, aim_name, factor_names, plan_names);
				setContentPane(panelAhpMat);
				
//				try {
//					this.setMaximum(true);
//				} catch (PropertyVetoException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}

				this.revalidate();
				this.repaint(); 
			}

		}
		else if(e.getSource() == buttonReset) {
			
			//清空
			textfieldAimName.setText("");
			textfieldFactorNames.setText("");
			textfieldPlanNames.setText("");
			datePickerArgueTime.getModel().setValue(null);
			comboBoxTaskLocation.setSelectedItem(null);
			
		}
		else if(e.getSource() == buttonExample) {
			
//			//取消新建后续步骤
//			this.setContentPane(new JPanel());
//			this.revalidate();
//			this.repaint();
			String aim = "选择旅游地";
			String[] factors = { "风景", "费用", "时间花费", "路程", "风险" };
			String[] plans = { "成都", "武汉", "杭州", };	


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
			AhpMatPanel ahpMatPanel = new AhpMatPanel(this, aim, factors, plans);
			
			ahpMatPanel.get_tableFactorMat().setMat(factor_array);
			for (int i = 0; i < plan_array3d.length; i++) {
				ahpMatPanel.get_tablePlanMatVec().get(i).setMat(plan_array3d[i]);
			}

			setContentPane(ahpMatPanel);
			try {
				this.setMaximum(true);
			} catch (PropertyVetoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.revalidate();
			this.repaint(); 
			
		}		
		
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					BasicRibbon Frm = new BasicRibbon();
					//Frm.setPreferredSize(new Dimension(1000, 800));
					
					AhpInputSub3Frame sub3Frm = new AhpInputSub3Frame();

					//sub3Frm.pack();
					//sub3Frm.show();
					Frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
									
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
