package zlbyzc.sub3.zlpj.anp;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;

import zlbyzc.BasicRibbon;
import zlbyzc.mainDesk;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;


//import ahp.Test.MyTableCellRenderer;

public class AnpInitFrm extends sub3inFrame {

    private JPanel contentPane;
	private mainDesk desktopPane;
	private JPanel panelLayInputTips;
	private JPanel panelLayAimName;
	private JPanel panelLayFactorNames;
	private JPanel panelLayPlanNames;
	
	private JPanel paraInPanel;
//	private JPanel panelLayArgueTime;
//	private JPanel panelLayTaskLocation;
	private JPanel panelLayButton;
//	private Box vBox;
	
	private JLabel labelInputTips;
	private JLabel labelAimName;
	private JLabel labelFactorNames;
	private JLabel labelPlanNames;
//	private JLabel labelArgueTime;
//	private JLabel labelTaskLocation;

	private JTextField textfieldAimName;
	private JTextField textfieldFactorNames;
	private JTextField textfieldPlanNames;
	private JDatePicker datePickerArgueTime;
//	private JComboBox<String> comboBoxTaskLocation;
	
	private JButton buttonexample;
	private JButton buttonconfirm;
	private JButton buttonempty;
	private BasicRibbon BR;
//	private JTextArea paraInfo;
	//final JFXPanel jfxPanel = new JFXPanel();
	static Anpconstruct conframe;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					AnpInitFrm frame = new AnpInitFrm(BR);
					frame.setVisible(true);
					frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 * @return 
	 */
	public AnpInitFrm(BasicRibbon _br) {
		super("ANP网络分析模块",true,true,true,true);
		BR = _br;
		javax.swing.Icon r_icon=ImageTask.
				getResizableIconFromResource("/img/icons/anp.png");
		this.setFrameIcon(r_icon);

		
//		setDefaultCloseOperation(sub3inFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
//		contentPane.setBackground(Color.white);
	
		
		panelLayInputTips = new JPanel();
		panelLayAimName = new JPanel();
		panelLayFactorNames = new JPanel();
		panelLayPlanNames = new JPanel();
//		panelLayArgueTime = new JPanel();
//		panelLayTaskLocation = new JPanel();
		panelLayButton = new JPanel();
		
//		panelLayInputTips.setBackground(Color.white);
//		panelLayAimName.setBackground(Color.white);
//		panelLayIndexNames.setBackground(Color.white);
//		panelLayCommentNames.setBackground(Color.white);
//		panelLayArgueTime.setBackground(Color.white);
//		panelLayTaskLocation.setBackground(Color.white);
//		panelLayButton.setBackground(Color.white);
		
		panelLayInputTips.setLayout(new BoxLayout(panelLayInputTips, BoxLayout.X_AXIS));
		panelLayAimName.setLayout(new BoxLayout(panelLayAimName, BoxLayout.X_AXIS));
		panelLayFactorNames.setLayout(new BoxLayout(panelLayFactorNames, BoxLayout.X_AXIS));
		panelLayPlanNames.setLayout(new BoxLayout(panelLayPlanNames, BoxLayout.X_AXIS));
//		panelLayArgueTime.setLayout(new BoxLayout(panelLayArgueTime, BoxLayout.X_AXIS));
//		panelLayTaskLocation.setLayout(new BoxLayout(panelLayTaskLocation, BoxLayout.X_AXIS));
//		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));
		
		labelInputTips = new JLabel(" 提示： 目标只能有一个, 准则与方案中间以空格隔开！");
		labelAimName = new JLabel("战略目标");
		labelFactorNames = new JLabel("评价准则");
		labelPlanNames = new JLabel("备选方案");
//		labelArgueTime = new JLabel("研讨日期");
//		labelTaskLocation = new JLabel("研讨地点");
		
		textfieldAimName = new JTextField();
		textfieldFactorNames = new JTextField();
		textfieldPlanNames = new JTextField();
//		datePickerArgueTime = new JDatePicker();
		
//		comboBoxTaskLocation = new JComboBox<String> ();
//		comboBoxTaskLocation.setEditable(true);	//可输入。得到输入值的方法： getEditor().getItem(); 
//		comboBoxTaskLocation.addItem("武汉");
//		comboBoxTaskLocation.addItem("北京");
//		comboBoxTaskLocation.addItem("深圳");
//		comboBoxTaskLocation.addItem("上海");
//		comboBoxTaskLocation.setSelectedItem(null);
		
		textfieldAimName.setMinimumSize(new Dimension(250,40));
		textfieldAimName.setMaximumSize(new Dimension(350,40));
		textfieldFactorNames.setMinimumSize(new Dimension(250,40));
		textfieldFactorNames.setMaximumSize(new Dimension(350,40));
		textfieldPlanNames.setMinimumSize(new Dimension(250,40));
		textfieldPlanNames.setMaximumSize(new Dimension(350,40));
//		((Component) datePickerArgueTime).setMinimumSize(new Dimension(150,25));
//		((Component) datePickerArgueTime).setMaximumSize(new Dimension(200,30));
//		comboBoxTaskLocation.setMinimumSize(new Dimension(100,25));
//		comboBoxTaskLocation.setMaximumSize(new Dimension(150,30));
		
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
		
//		panelLayArgueTime.add(labelArgueTime);
//		panelLayArgueTime.add(Box.createRigidArea(new Dimension(20, 60)));  
//		panelLayArgueTime.add(datePickerArgueTime);
		
//		panelLayTaskLocation.add(labelTaskLocation);
//		panelLayTaskLocation.add(Box.createRigidArea(new Dimension(20, 60)));  
//		panelLayTaskLocation.add(comboBoxTaskLocation);
		
		buttonexample = new JButton("实例");
		buttonconfirm = new JButton("确定");
		buttonempty = new JButton("重置");
		panelLayButton.add(buttonexample);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));
		panelLayButton.add(buttonempty);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonconfirm);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));
		
		contentPane.add(Box.createVerticalGlue() );
		contentPane.add(panelLayInputTips);
		contentPane.add(Box.createVerticalStrut(10));  	
		contentPane.add(panelLayAimName);
		contentPane.add(Box.createVerticalStrut(10));  		
		contentPane.add(panelLayFactorNames);
		contentPane.add(Box.createVerticalStrut(10));  	
		contentPane.add(panelLayPlanNames);
		contentPane.add(Box.createVerticalStrut(10)); 		
//		contentPane.add(panelLayArgueTime);
//		contentPane.add(Box.createVerticalStrut(10));  		
//		add(panelLayTaskLocation);
		contentPane.add(Box.createVerticalStrut(10)); 
		contentPane.add(panelLayButton);
		contentPane.add( Box.createVerticalGlue() );
		
		
		buttonconfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(checkInput()){//输入正确
					String aim_name = textfieldAimName.getText().trim();
					String[] factor_names = textfieldFactorNames.getText().trim().split("\\s+");
					//int factorNum = factor_names.length;
					String[] plan_names = textfieldPlanNames.getText().trim().split("\\s+");
					//int planNum = plan_names.length;
					String time = new String();
					Calendar ca = Calendar.getInstance();			
					time = String.format("%s-%s-%s", ca.get(Calendar.YEAR), ca.get(Calendar.MONTH) + 1,
							ca.get(Calendar.DATE));
					setContentPane(new AnpDataPanel(aim_name,factor_names,plan_names,time,BR));
					//pack();
					revalidate();
					repaint(); 
					conframe = new Anpconstruct();
					desktopPane.add(conframe);
					conframe.show();
					conframe.drawG(aim_name, factor_names, plan_names);

				}
			}
		});
		buttonempty.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textfieldAimName.setText("");
				textfieldFactorNames.setText("");
				textfieldPlanNames.setText("");
				datePickerArgueTime.getModel().setValue(null);
//				comboBoxTaskLocation.setSelectedItem(null);
			}
		});
		buttonexample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textfieldAimName.setText("导弹部署地选择");
				textfieldFactorNames.setText("震慑力 费用 地理条件");
				textfieldPlanNames.setText("南海 东北 西南");
//				datePickerArgueTime.getModel().setValue(null);
//				comboBoxTaskLocation.setSelectedItem(null);
			}
		});
		pack();	    
		this.setVisible(true);
	}
	
	private void disposeThisFrm(){
		this.dispose();	
	}
	
	private JTable creatTable(String[] strArray){

        int num = strArray.length;
		JTable jtb = new JTable();	
		Object[][] tabledata = new Object[num+1][num+1];
		for(int i=0;i<num;i++){
			tabledata[0][i+1] = strArray[i];
			tabledata[i+1][0] = strArray[i];
		}		
		Object[] columnNames = new Object[num+1];
		DefaultTableModel tableModel = new DefaultTableModel(tabledata,columnNames);
		jtb.setModel(tableModel);
		
		/*
        TableModel model = jtb.getModel();
        Set<Class> classes = new HashSet<Class>();
        for (int i = 0; i < num+1; i++) {
            classes.add(model.getColumnClass(i));
        }
        int[] rows={0};
        int[] cols={0};
        TableCellRenderer renderer = new MyTableCellRenderer(rows, cols);
        for (Class clazz : classes) {
        	jtb.setDefaultRenderer(clazz, renderer);
        }

		*/
        jtb.updateUI();
		return  jtb;
	}

	//输入参数正确时返回true，否则返回false，且会弹出信息提示窗口。
	private boolean checkInput(){
		boolean inputIsOk=false;
		String aim_name = textfieldAimName.getText();
		String factor_name = textfieldFactorNames.getText();
		String plan_name = textfieldPlanNames.getText();
		if(aim_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "目标不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(aim_name.trim().split("\\s+").length!=1)
			JOptionPane.showMessageDialog(paraInPanel, "目标只能有一个！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(factor_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "准则不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(factor_name.trim().split("\\s+").length>5)
			JOptionPane.showMessageDialog(paraInPanel, "准则不能超过5个！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(plan_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "方案不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(plan_name.trim().split("\\s+").length>5)
			JOptionPane.showMessageDialog(paraInPanel, "方案不能超过5个！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else inputIsOk=true;
		return inputIsOk;
	}

	public void setBR(mainDesk _desktopPane) {
		// TODO Auto-generated method stub
		desktopPane=_desktopPane;
	}
	
   

}
