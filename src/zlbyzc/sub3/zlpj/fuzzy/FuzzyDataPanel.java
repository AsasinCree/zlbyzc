package zlbyzc.sub3.zlpj.fuzzy;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import Jama.Matrix;
import zlbyzc.sub3.zlpj.fuzzy.FuzzyResultPanel;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;

//import ahp.Test.MyTableCellRenderer;


public class FuzzyDataPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;

	private JPanel indexMatInPanel;// 指标权重向量只有一个
	private JPanel commentMatInPanel; // 隶属度矩阵
	private JPanel buttonpanel;
	
	private JButton buttonconfirm;
	private JButton buttonreset;
	private JButton buttonexample;

	FuzzyJTable_CompMatrix indexTable;
	FuzzyJTable_CompMatrix commentTable;



//	private JTextArea matInfo;
	private int commentNum;
	private int indexNum;
	String[] weight = { "权重" };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					String target = "服装评判";
					String[] index = { "花色", "式样", "耐穿程度","价格"};
					String[] comment = { "很好", "好", "一般", "差" };
					String time = "2016-xx-xx";

					FuzzyDataPanel fuzzydata = new FuzzyDataPanel(target, index, comment,time);
					JFrame frame = new JFrame();
					frame.setContentPane(fuzzydata);
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
	public FuzzyDataPanel(String target, String[] index, String[] comment,String time) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		

//		matInfo = new JTextArea(60, 40);
//		paintPanel = new JPanel();
		indexMatInPanel = new JPanel();
		commentMatInPanel = new JPanel();
//		indexMatInPanel.setBackground(Color.white);
//		commentMatInPanel.setBackground(Color.white);

		commentNum = comment.length;
		indexNum = index.length;

		// 画图区
//		paintPanel.add(matInfo);

		// 一个准则层判断矩阵
		indexMatInPanel.setLayout(new BoxLayout(indexMatInPanel, BoxLayout.X_AXIS));
		indexMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "输入各指标权重"));
		indexTable = new FuzzyJTable_CompMatrix(weight, index);
		indexTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//		indexTable.setRowHeight(30);
//		indexMatInPanel.add(indexTable);

		// 多个方案层判断矩阵
		commentMatInPanel.setLayout(new BoxLayout(commentMatInPanel, BoxLayout.X_AXIS));
		commentMatInPanel.setBorder(new TitledBorder(new EtchedBorder(), "输入隶属度矩阵"));
		commentTable = new FuzzyJTable_CompMatrix(index, comment);
		commentTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//		commentTable.setRowHeight(30);
//		commentMatInPanel.add(commentTable);

		buttonpanel =new JPanel();
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
		buttonconfirm  = new JButton("确定");
		buttonreset = new JButton("重置");
		buttonexample = new JButton("实例");
		
		indexMatInPanel.add(Box.createHorizontalGlue());
		indexMatInPanel.add(Box.createHorizontalStrut(500));
		indexMatInPanel.add(indexTable);
		indexMatInPanel.add(Box.createHorizontalStrut(500));
		indexMatInPanel.add(Box.createHorizontalGlue());
		
		commentMatInPanel.add(Box.createHorizontalGlue());
		commentMatInPanel.add(Box.createHorizontalStrut(500));
		commentMatInPanel.add(commentTable);
		commentMatInPanel.add(Box.createHorizontalStrut(500));
		commentMatInPanel.add(Box.createHorizontalGlue());
		
		buttonpanel.add(buttonexample);
		buttonpanel.add(Box.createRigidArea(new Dimension(20, 60)));
		buttonpanel.add(buttonreset);
		buttonpanel.add(Box.createRigidArea(new Dimension(20, 60)));
		buttonpanel.add(buttonconfirm);
		buttonpanel.add(Box.createRigidArea(new Dimension(20, 60)));
		
		add(indexMatInPanel);
		add(commentMatInPanel);
		add(buttonpanel);

//		contentPane.add(vBox, BorderLayout.CENTER);
//		contentPane.add(paintPanel, BorderLayout.EAST);

		buttonconfirm.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				double[][] index_array2d = new double[1][indexNum];
				double[][] comment_array2d = new double[indexNum][commentNum];
				index_array2d = indexTable.getMat();
				comment_array2d = commentTable.getMat();

				Fuzzy fuzzy = new Fuzzy(target,index,comment);
				fuzzy.read_indexMat(index_array2d);
				fuzzy.read_commentMat(comment_array2d);
				
				if(!fuzzy.checkone(new Matrix(index_array2d))){
					JOptionPane.showMessageDialog(indexMatInPanel,"指标权重向量不满足归一性");
					return;
				}
				if(!fuzzy.checkone(new Matrix(comment_array2d))){
					JOptionPane.showMessageDialog(commentMatInPanel,"隶属度矩阵不满足行归一");
					return;
				}

				//String result1 = fuzzy.formatMat(fuzzy.min2Max());
				//String result2 = fuzzy.formatMat(fuzzy.multiply2Max());
				//String result3 = fuzzy.formatMat(fuzzy.plus2Max());
				Matrix result4 = fuzzy.multiply2Plus();
				double[] mat1=result4.getColumnPackedCopy();
				String[] mat=new String[mat1.length];
				for(int i=0;i<mat.length;i++){
					mat[i]=Double.toString(mat1[i]);
				}
				removeAll();
				add(new FuzzyResultPanel(comment,mat,fuzzy.getfuzzyAim_name(),fuzzy.toString(),time));
				revalidate();
				repaint(); 
			}
		});
        buttonreset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				indexTable.reset();
				commentTable.reset();		
			}
		});
        buttonexample.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[][] insexvalues ={{"0.1","0.2","0.3","0.4"}};
				String[][] commentvalues ={{"0.2","0.5","0.2","0.1"},
						{"0.7","0.2","0.1","0"},
						{"0","0.4","0.5","0.1"},
						{"0.2","0.3","0.5","0"}		
				};
				indexTable.setMat(insexvalues);
				commentTable.setMat(commentvalues);			
			}
		});	

		
	}

	
}
