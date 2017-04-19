package zlbyzc.sub3.zlpj.dea;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;


public class DeaDataPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;
    private JPanel contentpane;
    private JScrollPane contentPaneC;
	private JPanel inputMatInPanel;// 投入矩阵只有一个
	private JPanel outputMatInPanel;  //产出矩阵有一个
	private JPanel buttonpanel;
	private JPanel ip1;
	private JPanel ip2;
	private JPanel op1;
	private JPanel op2;
	private JButton buttonconfirm;
	private JButton buttonreset;
	private JButton buttonexample;
	//AnpJtable_CompMatrix1 factorTable1;
	DeaJTable_CompMatrix inputTable;
	DeaJTable_CompMatrix outputTable;
	
	private int nameNum;
	private int inputNum;
	private int outputNum;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					String aim="银行分理处";
					String[] allnames={"分理处1","分理处2","分理处3","分理处4"};;
					String[] inputs={"职员数","营业面积"};
					String[] outputs={"储蓄存款","贷款","中间业务",};
					String time="2016-xx-xx";
					DeaDataPanel deadata = new DeaDataPanel(aim,allnames,inputs,outputs,time);
					JFrame frame = new JFrame();
					frame.setContentPane(deadata);
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
	public DeaDataPanel(String aim, String[] allnames, String[] input_names,String[] output_names,String time) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		contentpane = new JPanel();
		contentpane.setLayout(new BoxLayout(contentpane, BoxLayout.Y_AXIS));
		
		inputMatInPanel = new JPanel();
		outputMatInPanel = new JPanel();
		ip1 = new JPanel();
		ip2 = new JPanel();
		op1 = new JPanel();
		op2 = new JPanel();
		
		nameNum=allnames.length;
		inputNum = input_names.length;
		outputNum = output_names.length;
		
		//一个投入矩阵
		inputMatInPanel.setLayout(new GridLayout(1, 3));
		inputMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "输入投入矩阵"));
		inputTable = new DeaJTable_CompMatrix(allnames,input_names);
		inputTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);		
		
		//一个产出矩阵
		outputMatInPanel.setLayout(new GridLayout(1, 3));
		outputMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "输入产出矩阵"));
		outputTable = new DeaJTable_CompMatrix(allnames,output_names);
		outputTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		buttonpanel = new JPanel();
		buttonpanel.setLayout(new FlowLayout());
		buttonconfirm = new JButton("确定");
		buttonreset =new JButton("重置");
		buttonexample = new JButton("实例");
		
		inputMatInPanel.add(ip1);
		inputMatInPanel.add(inputTable);
		inputMatInPanel.add(ip2);
		
		outputMatInPanel.add(op1);
		outputMatInPanel.add(outputTable);
		outputMatInPanel.add(op2);
		
		
		buttonpanel.add(buttonexample);
		buttonpanel.add(buttonreset);
		buttonpanel.add(buttonconfirm);
		
		contentpane.add(inputMatInPanel);
		contentpane.add(outputMatInPanel);
		contentPaneC = new JScrollPane(contentpane);
		add("Center",contentPaneC);
		add("South",buttonpanel);
		
		buttonconfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				double[][] input_array2d = new double[nameNum][inputNum];
				double[][] output_array2d = new double[nameNum][outputNum];
				input_array2d = inputTable.getMat1();
				output_array2d = outputTable.getMat1();
				Dea dea = new Dea(aim,allnames,input_names,output_names);
				dea.read_inputMat(input_array2d);
				dea.read_outPutMat(output_array2d);
				double[] result = dea.result();
				String[] resultstr=new String[result.length];
				for(int i=0;i<resultstr.length;i++){
					resultstr[i]=Double.toString(result[i]);
				}
				removeAll();
				add(new DeaResultPanel(allnames,resultstr,dea.getdeaAim_name(),dea.toString(),time));
				revalidate();
				repaint(); 
			}
		});
        buttonreset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inputTable.reset();
				outputTable.reset();			
			}
		});
        buttonexample.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[][] inputvalues ={{"15","140"},
						{"20","130"},
						{"21","120"},
						{"20","135"}		
				};
				String[][] outputvalues ={{"1800","200","1600"},
						{"1000","350","1000"},
						{"800","450","1300"},
						{"900","420","1500"}		
				};
				inputTable.setMat(inputvalues);
				outputTable.setMat(outputvalues);			
			}
		});	
	}
	
}
