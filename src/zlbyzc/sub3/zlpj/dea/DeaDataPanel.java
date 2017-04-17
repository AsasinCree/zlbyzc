package zlbyzc.sub3.zlpj.dea;

import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;


public class DeaDataPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;

	private JPanel inputMatInPanel;// 投入矩阵只有一个
	private JPanel outputMatInPanel;  //产出矩阵有一个
	private JPanel buttonpanel;
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
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		inputMatInPanel = new JPanel();
		outputMatInPanel = new JPanel();
		
		nameNum=allnames.length;
		inputNum = input_names.length;
		outputNum = output_names.length;
		
		//一个投入矩阵
		inputMatInPanel.setLayout(new BoxLayout(inputMatInPanel, BoxLayout.X_AXIS));
		inputMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "输入投入矩阵"));
		//inputMatInPanel.setFont(new Font("宋体", Font.PLAIN, 14));
		//factorTable1 = new AnpJtable_CompMatrix1(factors);
		inputTable = new DeaJTable_CompMatrix(allnames,input_names);
		inputTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//		inputTable.setRowHeight(30);
//		inputMatInPanel.add(inputTable);
		
		
		//一个产出矩阵
		outputMatInPanel.setLayout(new BoxLayout(outputMatInPanel, BoxLayout.X_AXIS));
		outputMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "输入产出矩阵"));
		//outputMatInPanel.setFont(new Font("宋体", Font.PLAIN, 14));
		outputTable = new DeaJTable_CompMatrix(allnames,output_names);
		outputTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//		outputTable.setRowHeight(30);
//		outputMatInPanel.add(outputTable);

		buttonpanel = new JPanel();
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
		buttonconfirm = new JButton("确定");
		buttonreset =new JButton("重置");
		buttonexample = new JButton("实例");
		
		inputMatInPanel.add(Box.createHorizontalGlue());
		inputMatInPanel.add(Box.createHorizontalStrut(500));
		inputMatInPanel.add(inputTable);
		inputMatInPanel.add(Box.createHorizontalStrut(500));
		inputMatInPanel.add(Box.createHorizontalGlue());
		
		outputMatInPanel.add(Box.createHorizontalGlue());
		outputMatInPanel.add(Box.createHorizontalStrut(500));
		outputMatInPanel.add(outputTable);
		outputMatInPanel.add(Box.createHorizontalStrut(500));
		outputMatInPanel.add(Box.createHorizontalGlue());
		
		buttonpanel.add(buttonexample);
		buttonpanel.add(Box.createRigidArea(new Dimension(20, 60)));
		buttonpanel.add(buttonreset);
		buttonpanel.add(Box.createRigidArea(new Dimension(20, 60)));
		buttonpanel.add(buttonconfirm);
		buttonpanel.add(Box.createRigidArea(new Dimension(20, 60)));
		
		add(inputMatInPanel);
		add(outputMatInPanel);
		add(buttonpanel);
		
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
