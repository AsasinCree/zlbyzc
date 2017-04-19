package zlbyzc.sub3.zlpj.fuzzy;

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

import zlbyzc.sub3.zlpj.fuzzy.FuzzyDataPanel;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;


//import ahp.Test.MyTableCellRenderer;

public class FuzzyInitFrm extends sub3inFrame {

	private JPanel contentPane;
	
	private JPanel panelLayInputTips;
	private JPanel panelLayAimName;
	private JPanel panelLayIndexNames;
	private JPanel panelLayCommentNames;
	private JPanel paintPanel;
	private JPanel paraInPanel;
	private JPanel panelLayArgueTime;
//	private JPanel panelLayTaskLocation;
	private JPanel panelLayButton;
//	private Box vBox;
	
	private JLabel labelInputTips;
	private JLabel labelAimName;
	private JLabel labelIndexNames;
	private JLabel labelCommentNames;
//	private JLabel labelArgueTime;
//	private JLabel labelTaskLocation;

	private JTextField textfieldAimName;
	private JTextField textfieldIndexNames;
	private JTextField textfieldCommentNames;
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
					FuzzyInitFrm frame = new FuzzyInitFrm();
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
	public FuzzyInitFrm() {
		super("模糊综合评判模块",true,true,true,true);
		javax.swing.Icon r_icon=ImageTask.
				getResizableIconFromResource("/img/icons/fu.png");
		this.setFrameIcon(r_icon);

		
//		setDefaultCloseOperation(sub3inFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		setContentPane(contentPane);
//		contentPane.setBackground(Color.white);
		
		
		panelLayInputTips = new JPanel();
		panelLayAimName = new JPanel();
		panelLayIndexNames = new JPanel();
		panelLayCommentNames = new JPanel();
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
		panelLayIndexNames.setLayout(new BoxLayout(panelLayIndexNames, BoxLayout.X_AXIS));
		panelLayCommentNames.setLayout(new BoxLayout(panelLayCommentNames, BoxLayout.X_AXIS));
//		panelLayArgueTime.setLayout(new BoxLayout(panelLayArgueTime, BoxLayout.X_AXIS));
//		panelLayTaskLocation.setLayout(new BoxLayout(panelLayTaskLocation, BoxLayout.X_AXIS));
		panelLayButton.setLayout(new BoxLayout(panelLayButton, BoxLayout.X_AXIS));
		
		labelInputTips = new JLabel(" 提示： 目标只能有一个,指标与评语中间以空格隔开！");
		labelAimName = new JLabel("战略目标");
		labelIndexNames = new JLabel("评价指标");
		labelCommentNames = new JLabel("评判评语");
//		labelArgueTime = new JLabel("研讨日期");
//		labelTaskLocation = new JLabel("研讨地点");
		
		textfieldAimName = new JTextField();
		textfieldIndexNames = new JTextField();
		textfieldCommentNames = new JTextField();
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
		textfieldIndexNames.setMinimumSize(new Dimension(250,40));
		textfieldIndexNames.setMaximumSize(new Dimension(350,40));
		textfieldCommentNames.setMinimumSize(new Dimension(250,40));
		textfieldCommentNames.setMaximumSize(new Dimension(350,40));
//		((Component) datePickerArgueTime).setMinimumSize(new Dimension(150,25));
//		((Component) datePickerArgueTime).setMaximumSize(new Dimension(200,30));
//		comboBoxTaskLocation.setMinimumSize(new Dimension(100,25));
//		comboBoxTaskLocation.setMaximumSize(new Dimension(150,30));
		
        panelLayInputTips.add(labelInputTips);
		
		panelLayAimName.add(labelAimName);		
		panelLayAimName.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayAimName.add(textfieldAimName);
		
		panelLayIndexNames.add(labelIndexNames);		
		panelLayIndexNames.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayIndexNames.add(textfieldIndexNames);
		
		panelLayCommentNames.add(labelCommentNames);		
		panelLayCommentNames.add(Box.createRigidArea(new Dimension(20, 60)));  		
		panelLayCommentNames.add(textfieldCommentNames);
		
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
		contentPane.add(panelLayAimName);
		contentPane.add(Box.createVerticalStrut(10));  		
		contentPane.add(panelLayIndexNames);
		contentPane.add(Box.createVerticalStrut(10));  	
		contentPane.add(panelLayCommentNames);
		contentPane.add(Box.createVerticalStrut(10)); 		
//		contentPane.add(panelLayArgueTime);
//		contentPane.add(Box.createVerticalStrut(10));  		
//		contentPane.add(panelLayTaskLocation);
		contentPane.add(Box.createVerticalStrut(10));  		
		contentPane.add(panelLayButton);
		contentPane.add( Box.createVerticalGlue() );
		
		buttonconfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(checkInput()){//输入正确
					String target_name = textfieldAimName.getText().trim();
					String[] index_names = textfieldIndexNames.getText().trim().split("\\s+");
					//int factorNum = factor_names.length;
					String[] comment_names = textfieldCommentNames.getText().trim().split("\\s+");
					//int planNum = plan_names.length;
//					disposeThisFrm();
//					FuzzyDataFrm dataFrm = new FuzzyDataFrm(target_name,index_names,comment_names);
//					dataFrm.setVisible(true);
					String time = new String();
					Calendar ca = Calendar.getInstance();
					time = String.format("%s-%s-%s", ca.get(Calendar.YEAR), ca.get(Calendar.MONTH) + 1,
							ca.get(Calendar.DATE));
					setContentPane(new FuzzyDataPanel(target_name,index_names,comment_names,time));
					revalidate();
					repaint(); 
				}
			}
		});
		buttonempty.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textfieldAimName.setText("");
				textfieldIndexNames.setText("");
				textfieldCommentNames.setText("");
//				datePickerArgueTime.getModel().setValue(null);
//				comboBoxTaskLocation.setSelectedItem(null);
			}
		});
		buttonexample.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				textfieldAimName.setText("东风26导弹评价");
				textfieldIndexNames.setText("费用 射程 打击范围  精确度");
				textfieldCommentNames.setText("很好 好 一般 差");
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
		String target_name = textfieldAimName.getText();
		String index_name = textfieldIndexNames.getText();
		String comment_name = textfieldCommentNames.getText();
		if(target_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "目标不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(target_name.trim().split("\\s+").length!=1)
			JOptionPane.showMessageDialog(paraInPanel, "目标只能有一个！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(index_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "指标不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
//		else if(index_name.trim().split("\\s+").length>6)
//			JOptionPane.showMessageDialog(paraInPanel, "指标不能超过6个！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else if(comment_name.trim().isEmpty())
			JOptionPane.showMessageDialog(paraInPanel, "评语不能为空！", "参数错误",JOptionPane.WARNING_MESSAGE);
//		else if(comment_name.trim().split("\\s+").length>6)
//			JOptionPane.showMessageDialog(paraInPanel, "评语不能超过6个！", "参数错误",JOptionPane.WARNING_MESSAGE);
		else inputIsOk=true;
		return inputIsOk;
	}
	
   

}
