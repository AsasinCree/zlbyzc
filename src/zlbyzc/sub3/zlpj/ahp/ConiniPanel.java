package zlbyzc.sub3.zlpj.ahp;


import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class ConiniPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	JInternalFrame frame;
	
	private JPanel panelLayInputTips;
	private JPanel panelLayAddress;
	private JPanel panelLayUser;
	private JPanel panelLayPin;
	private JPanel panelLayButton;
	
	private JLabel labelInputTips;
	private JLabel labelAddress;
	private JLabel labelUser;
	private JLabel labelPin;
	

	private JTextField textfieldAddress;
	private JTextField textfieldUser;
	private JTextField textfieldPin;
	
	//三个按钮
	private JButton buttonConfirm;
	private JButton buttonReset;
	private JButton buttonCancel;
	
	public ConiniPanel(){
		initializeComponent();
		layoutComponent();
		setDefaultValues();
	}
	
//	public AhpInputPanel(JFrame frame){
//		this();
//		this.frame = frame;
//	}
	
	public ConiniPanel(JInternalFrame frame){
		this();
		this.frame = frame;
	}
	
	
	private void initializeComponent() {
		
		//JPanel设置
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelLayInputTips = new JPanel();
		panelLayAddress = new JPanel();
		panelLayUser = new JPanel();
		panelLayPin = new JPanel();
		panelLayButton = new JPanel();
		
		panelLayInputTips.setLayout(new BoxLayout(panelLayInputTips, BoxLayout.X_AXIS));
		panelLayAddress.setLayout(new BoxLayout(panelLayAddress, BoxLayout.X_AXIS));
		panelLayUser.setLayout(new BoxLayout(panelLayUser, BoxLayout.X_AXIS));
		panelLayPin.setLayout(new BoxLayout(panelLayPin, BoxLayout.X_AXIS));
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));

		
		//labelInputTips = new JLabel("<html>提示：<br>目标只能有一个，准则与方案不能超过6个，中间以空格隔开！<html>");
		labelInputTips = new JLabel(" 数据库配置");
		labelAddress = new JLabel("地   址");
		labelUser = new JLabel("用户名");
		labelPin = new JLabel("密   码");
		
		textfieldAddress = new JTextField();
		textfieldUser = new JTextField();
		textfieldPin = new JTextField();
		
		
		textfieldAddress.setMinimumSize(new Dimension(200,30));
		textfieldAddress.setMaximumSize(new Dimension(250,30));
		textfieldUser.setMinimumSize(new Dimension(200,30));
		textfieldUser.setMaximumSize(new Dimension(250,30));
		textfieldPin.setMinimumSize(new Dimension(200,30));
		textfieldPin.setMaximumSize(new Dimension(250,30));

		
		buttonConfirm = new JButton("确定");
		buttonReset = new JButton("默认值");
		buttonCancel = new JButton("取消");
		
		buttonConfirm.addActionListener(this);
		buttonReset.addActionListener(this);
		buttonCancel.addActionListener(this);
		
	}

	public void layoutComponent() {

		//panelLayInputTips.add(Box.createHorizontalStrut(88));
		panelLayInputTips.add(labelInputTips);
		
		panelLayAddress.add(labelAddress);		
		panelLayAddress.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayAddress.add(textfieldAddress);
		
		panelLayUser.add(labelUser);		
		panelLayUser.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayUser.add(textfieldUser);
		
		panelLayPin.add(labelPin);		
		panelLayPin.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayPin.add(textfieldPin);		
		

		panelLayButton.add(buttonConfirm);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonReset);
//		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
//		panelLayButton.add(buttonCancel);						
	
		add(Box.createVerticalGlue() );
		add(panelLayInputTips);
		//add(Box.createVerticalStrut(10));  	
		add(panelLayAddress);
		//add(Box.createVerticalStrut(10));  		
		add(panelLayUser);
		//add(Box.createVerticalStrut(10));  	
		add(panelLayPin);
		//add(Box.createVerticalStrut(10)); 		
//		add(panelLayArgueTime);
//		add(Box.createVerticalStrut(10));  		
//		add(panelLayTaskLocation);
//		add(Box.createVerticalStrut(10));  		
		add(panelLayButton);
		add( Box.createVerticalGlue() );		
	}
	
	private void setDefaultValues() {
		textfieldAddress.setText("mysql://192.168.0.2:3336/db_zlpj");
		textfieldUser.setText("root");
		textfieldPin.setText("wipm");
	
	}
	
	//输入参数正确时返回true，否则返回false，且会弹出信息提示窗口。
	private boolean checkInput(){
		boolean inputIsOk=false;
		String address = textfieldAddress.getText();
		String user = textfieldUser.getText();
		String pin = textfieldPin.getText();
		if(address.trim().isEmpty())
			JOptionPane.showMessageDialog(this, "地址不能为空！", "",JOptionPane.WARNING_MESSAGE); 
		else if(user.trim().isEmpty())
			JOptionPane.showMessageDialog(this, "用户名不能为空！", "",JOptionPane.WARNING_MESSAGE);
		else if(pin.trim().isEmpty())
			JOptionPane.showMessageDialog(this, "密码能为空！", "",JOptionPane.WARNING_MESSAGE);
		else 
			inputIsOk=true;
		return inputIsOk;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*  con.ini 格式：
		 * jdbc.drivers=com.mysql.jdbc.Driver
		 * jdbc.url=jdbc:mysql://192.168.0.2:3336/db_zlpj
		 * jdbc.username=root
		 * jdbc.password=wipm
		 */
		if(e.getSource() == buttonConfirm) {
			if(checkInput()){//输入正确
				try {
					String path="con.ini";
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
					String s="";
					s += "jdbc.drivers=com.mysql.jdbc.Driver\n";
					s += String.format("jdbc.url=jdbc:%s\n", textfieldAddress.getText());
					s += String.format("jdbc.username=%s\n", textfieldUser.getText());
					s += String.format("jdbc.password=%s\n", textfieldPin.getText());
					bw.write(s);
					bw.close();
				} catch (Exception e0) {
					e0.printStackTrace();
				}
				try {
					frame.setClosed(true);
				} catch (PropertyVetoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}
		else if(e.getSource() == buttonReset) {
			setDefaultValues();
		}
//		else if(e.getSource() == buttonCancel) {
//			
//			//取消新建后续步骤
//			frame.setContentPane(new JPanel());
//			frame.revalidate();
//			frame.repaint();
//			
//		}		
		
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					JFrame Frm = new JFrame();
					JInternalFrame sub3Frm = new JInternalFrame("",false,true);
					ConiniPanel coniniPanel = new ConiniPanel(sub3Frm);
					sub3Frm.setLayout(new BorderLayout());
					sub3Frm.add(coniniPanel);
					Frm.setContentPane(sub3Frm);
					Frm.setVisible(true);
					sub3Frm.pack();
					sub3Frm.show();
					Frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Frm.pack();					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
