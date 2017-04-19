package zlbyzc.sub3.zlpj.dea;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

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

import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;


//import ahp.Test.MyTableCellRenderer;

public class DeaInitFrm extends sub3inFrame {

	private JPanel contentPane;
	
	private JPanel panelLayInputTips;
	private JPanel panelLayDeaAimName;
	private JPanel panelLayObjectNames;
	private JPanel panelLayInputNames;
	private JPanel panelLayOutuptNames;
//	private JPanel paintPanel;
	private JPanel paraInPanel;
//	private JPanel panelLayArgueTime;
//	private JPanel panelLayTaskLocation;
	private JPanel panelLayButton;
//	private Box vBox;
	
	private JLabel labelInputTips;
	private JLabel labelDeaAimName;
	private JLabel labelObjectNames;
	private JLabel labelInputNames;
	private JLabel labelOutputNames;
//	private JLabel labelArgueTime;
//	private JLabel labelTaskLocation;

	private JTextField textfieldDeaAimName;
	private JTextField textfieldObjectNames;
	private JTextField textfieldInputNames;
	private JTextField textfieldOutputNames;
//	private JDatePicker datePickerArgueTime;
//	private JComboBox<String> comboBoxTaskLocation;
	
	private JButton buttonconfirm;
	private JButton buttonempty;
	private JButton buttonexample;
//	private JTextArea paraInfo;
	//final JFXPanel jfxPanel = new JFXPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					DeaInitFrm frame = new DeaInitFrm();
					frame.setVisible(true);
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
	public DeaInitFrm() {
		super("DEA数据包络模块",true,true,true,true);
		javax.swing.Icon r_icon=ImageTask.
				getResizableIconFromResource("/img/icons/dea.png");
		this.setFrameIcon(r_icon);

		
//		setDefaultCloseOperation(sub3inFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
		
		panelLayInputTips = new JPanel();
		panelLayDeaAimName = new JPanel();
		panelLayObjectNames = new JPanel();
		panelLayInputNames = new JPanel();
		panelLayOutuptNames = new JPanel();
//		panelLayArgueTime = new JPanel();
//		panelLayTaskLocation = new JPanel();
		panelLayButton = new JPanel();
		
		panelLayInputTips.setLayout(new BoxLayout(panelLayInputTips, BoxLayout.X_AXIS));
		panelLayDeaAimName.setLayout(new BoxLayout(panelLayDeaAimName, BoxLayout.X_AXIS));
		panelLayObjectNames.setLayout(new BoxLayout(panelLayObjectNames, BoxLayout.X_AXIS));
		panelLayInputNames.setLayout(new BoxLayout(panelLayInputNames, BoxLayout.X_AXIS));
		panelLayOutuptNames.setLayout(new BoxLayout(panelLayOutuptNames, BoxLayout.X_AXIS));
//		panelLayArgueTime.setLayout(new BoxLayout(panelLayArgueTime, BoxLayout.X_AXIS));
//		panelLayTaskLocation.setLayout(new BoxLayout(panelLayTaskLocation, BoxLayout.X_AXIS));
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));
		
		labelInputTips = new JLabel(" 提示： 目标只能有一个,其他输入各单元之间以空格隔开！");
		labelDeaAimName = new JLabel("战略目标");
		labelObjectNames = new JLabel("决策单元");
		labelInputNames = new JLabel("投入项目");
		labelOutputNames = new JLabel("产出项目");
//		labelArgueTime = new JLabel("研讨日期");
//		labelTaskLocation = new JLabel("研讨地点");
		
		textfieldDeaAimName = new JTextField();
		textfieldObjectNames = new JTextField();
		textfieldInputNames = new JTextField();
		textfieldOutputNames = new JTextField();
//		datePickerArgueTime = new JDatePicker();
		
//		comboBoxTaskLocation = new JComboBox<String> ();
//		comboBoxTaskLocation.setEditable(true);	//可输入。得到输入值的方法： getEditor().getItem(); 
//		comboBoxTaskLocation.addItem("武汉");
//		comboBoxTaskLocation.addItem("北京");
//		comboBoxTaskLocation.addItem("深圳");
//		comboBoxTaskLocation.addItem("上海");
//		comboBoxTaskLocation.setSelectedItem(null);
		
		textfieldDeaAimName.setMinimumSize(new Dimension(250,40));
		textfieldDeaAimName.setMaximumSize(new Dimension(350,40));
		textfieldObjectNames.setMinimumSize(new Dimension(250,40));
		textfieldObjectNames.setMaximumSize(new Dimension(350,40));
		textfieldInputNames.setMinimumSize(new Dimension(250,40));
		textfieldInputNames.setMaximumSize(new Dimension(350,40));
		textfieldOutputNames.setMinimumSize(new Dimension(250,40));
		textfieldOutputNames.setMaximumSize(new Dimension(350,40));
//		((Component) datePickerArgueTime).setMinimumSize(new Dimension(150,25));
//		((Component) datePickerArgueTime).setMaximumSize(new Dimension(200,30));
//		comboBoxTaskLocation.setMinimumSize(new Dimension(100,25));
//		comboBoxTaskLocation.setMaximumSize(new Dimension(150,30));
		
        panelLayInputTips.add(labelInputTips);
		
		panelLayDeaAimName.add(labelDeaAimName);		
		panelLayDeaAimName.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayDeaAimName.add(textfieldDeaAimName);
		
		panelLayObjectNames.add(labelObjectNames);		
		panelLayObjectNames.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayObjectNames.add(textfieldObjectNames);
		
		panelLayInputNames.add(labelInputNames);		
		panelLayInputNames.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayInputNames.add(textfieldInputNames);
		
		panelLayOutuptNames.add(labelOutputNames);		
		panelLayOutuptNames.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayOutuptNames.add(textfieldOutputNames);
		
//		panelLayArgueTime.add(labelArgueTime);
//		panelLayArgueTime.add(Box.createRigidArea(new Dimension(20, 60)));  
//		panelLayArgueTime.add(datePickerArgueTime);
		
//		panelLayTaskLocation.add(labelTaskLocation);
//		panelLayTaskLocation.add(Box.createRigidArea(new Dimension(20, 60)));  
//		panelLayTaskLocation.add(comboBoxTaskLocation);
		
		buttonconfirm = new JButton("确定");
		buttonempty = new JButton("重置");
		buttonexample = new JButton("实例");
		
		panelLayButton.add(buttonexample);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonempty);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));  
		panelLayButton.add(buttonconfirm);
		panelLayButton.add(Box.createRigidArea(new Dimension(20, 60)));
		
		contentPane.add(Box.createVerticalGlue() );
		contentPane.add(panelLayInputTips);
		contentPane.add(Box.createVerticalStrut(10));  	
		contentPane.add(panelLayDeaAimName);
		contentPane.add(Box.createVerticalStrut(10)); 
		contentPane.add(panelLayObjectNames);
		contentPane.add(Box.createVerticalStrut(10)); 
		contentPane.add(panelLayInputNames);
		contentPane.add(Box.createVerticalStrut(10));  	
		contentPane.add(panelLayOutuptNames);
		contentPane.add(Box.createVerticalStrut(10)); 		
//		add(panelLayArgueTime);
//		add(Box.createVerticalStrut(10));  		
//		add(panelLayTaskLocation);
		add(Box.createVerticalStrut(10));  		
		contentPane.add(panelLayButton);
		contentPane.add( Box.createVerticalGlue() );
		
		buttonconfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(checkInput()){//输入正确
					String aim_name = textfieldDeaAimName.getText().trim();
					String[] allnames = textfieldObjectNames.getText().trim().split("\\s+");
					//int factorNum = factor_names.length;
					String[] input_names = textfieldInputNames.getText().trim().split("\\s+");
					String[] output_names = textfieldOutputNames.getText().trim().split("\\s+");
					String time = new String();
					Calendar ca = Calendar.getInstance();
					time = String.format("%s-%s-%s", ca.get(Calendar.YEAR), ca.get(Calendar.MONTH) + 1,
							ca.get(Calendar.DATE));
					setContentPane(new DeaDataPanel(aim_name,allnames,input_names,output_names,time));
					revalidate();
					repaint(); 
				}
			}
		});
		
		buttonempty.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textfieldDeaAimName.setText("");
				textfieldObjectNames.setText("");
				textfieldInputNames.setText("");
				textfieldOutputNames.setText("");
//				datePickerArgueTime.getModel().setValue(null);
//				comboBoxTaskLocation.setSelectedItem(null);
			}
		});
		buttonexample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textfieldDeaAimName.setText("导弹基地对比");
				textfieldObjectNames.setText("52基地 53基地 54基地 55基地");
				textfieldInputNames.setText("军人 基地面积");
				textfieldOutputNames.setText("震慑力 覆盖范围 其他");
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
		String Deaaim_name = textfieldDeaAimName.getText();
		String objects = textfieldObjectNames.getText();
		String input_name = textfieldInputNames.getText();
		String output_name = textfieldOutputNames.getText();
		if(Deaaim_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "目标不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(Deaaim_name.trim().split("\\s+").length!=1)
			JOptionPane.showMessageDialog(paraInPanel, "目标只能有一个！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(objects.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "对象不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(input_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "投入不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(output_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "产出不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else inputIsOk=true;
		return inputIsOk;
	}
	
   

}