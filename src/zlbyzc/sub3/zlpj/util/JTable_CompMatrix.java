package zlbyzc.sub3.zlpj.util;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;

import java.util.HashMap;

//JTable for pair-wise comparison matrix 
//判断矩阵行列为(row,col)，这里的Jtable增加了一行一列，用来显示行列标签。
public class JTable_CompMatrix extends JTable {	
	private static final long serialVersionUID = 1L;
	
	private int row; //判断矩阵的实际行列数。
	private int col;
    private String factor = "";
    private String[] names;
    DefaultTableModel tblModel;
    DefaultTableCellRenderer tblCellRenderer;
    
    //static
	final public static String[] measureStrList={"9","8","7","6","5","4","3","2","1","1/2","1/3","1/4","1/5","1/6","1/7","1/8","1/9"};
	final public static double[] measureList={9,8,7,6,5,4,3,2,1,1.0/2,1.0/3,1.0/4,1.0/5,1.0/6,1.0/7,1.0/8,1.0/9};
	static HashMap<String,Double> str_double_dict;  // "1/9" --> 1.0/9
	static HashMap<String,String> reciprocalStr_dict;  // "1/9" --> "9"
	
	static{
		//str_double_dict赋值
		str_double_dict = new HashMap<String,Double>();
		for(int i=0;i<measureList.length;i++){
			str_double_dict.put(measureStrList[i], measureList[i]);
		}
		//reciprocalStr_dict赋值
		reciprocalStr_dict = new HashMap<String,String>();
		for(int i=0;i<measureList.length;i++){
			reciprocalStr_dict.put(measureStrList[i], measureStrList[measureList.length-i-1]);
		}
	}
	
	//factor为判断矩阵的准则，即两两比较的标准
	public JTable_CompMatrix(String factor,String[] names){
		row = names.length;
		col = names.length;		
		this.factor = factor;
		this.names = names;
		
		String[][] values = getDefaultTableValues(factor,names); //values为整个table，增加了一行一列。		
		tblModel = new DefaultTableModel(values, new String[col+1]){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column){  
				if (row!=0 && column!=0 && row<column) return true; //上三角矩阵可编辑
				else return false;        	 
	        } 
		};		
		super.setModel(tblModel);
		setRowHeight(30);
		setFont( new Font("宋体", Font.BOLD, 18)); //设置字体
				 
		//渲染。让第一行第一列为黄色，下三角（含对角线）为灰色，上三角白色。
		tblCellRenderer = new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column){
				Component cell = super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
				cell.setBackground(Color.WHITE); //上三角背景色	
				cell.setForeground(Color.DARK_GRAY);
				if (row!=0 && column!=0 && row>=column) {
					cell.setBackground(Color.gray); //下三角背景色
				}
				else if(row==0 || column==0){
					cell.setBackground(Color.yellow); //第一行第一列背景色
				}
				return cell;
			}
		};		
		super.setDefaultRenderer(Object.class, tblCellRenderer);  	
		//让table所有cell都为JComboBox
		super.setDefaultEditor(Object.class, new DefaultCellEditor(new JComboBox<String>(measureStrList)));
	} 
	
	public JTable_CompMatrix(String[] names){
		this("", names);
	}
	
	public JTable_CompMatrix(String factor,String[] names,String[][] values){
		this(factor,names);
		setMat(values);
	}
	
	public void reset() {
		for (int r = 0; r < row; r++) {
			for (int c = r+1; c < col; c++) {
				tblModel.setValueAt("1", r+1, c+1);
			}			
		}	
	}
	
	//只提取values的上三角，而并不对values的对称互倒性作检验。
	public void setMat(String[][] values) {
		if (row!=values.length || col!=values[0].length) {
			System.out.println("values dims error!");
			return;
		}
		for (int r = 0; r < row; r++) {
			for (int c = r+1; c < col; c++) {
				tblModel.setValueAt(values[r][c], r+1, c+1);
			}			
		}		
	}	
	
	@Override
	//setValueAt会触发tableChanged，从而引起连锁反应。这里判断下三角变化时，直接return！！！
    public void tableChanged(TableModelEvent e) {
    	super.tableChanged(e);
    	int selRow = e.getFirstRow();
    	int selCol = e.getColumn();
    	if (selRow==-1 || selCol==-1 || selRow>=selCol)
    		return;
        Object obj = tblModel.getValueAt(selRow, selCol);
        if (obj != null) {
        	//System.out.println(String.format("(%d,%d) changed", selRow,selCol));
        	tblModel.setValueAt(JTable_CompMatrix.reciprocalStr_dict.get(obj.toString()),selCol,selRow);
        }
	}  

	//返回一个默认的values，names为len个，values为(len+1,len+1)。
	//第一行第一列为标签，其余值默认为“1”。
	private String[][] getDefaultTableValues(String factor,String[] names) {
		int len = names.length;
		String[][] values = new String[len+1][len+1];
		if (factor=="") {
			values[0][0] = "";
		} else {
			values[0][0] = "< "+factor+" >";
		}
		
		for(int i=1;i<len+1;i++){
			values[0][i] = values[i][0] = names[i-1];
		}

		for(int i=1;i<len+1;i++){
			for(int j=1;j<len+1;j++){
				if (i==j) {
					values[i][j] = "1"; //对角线初值
				} else if(i>j){
					values[i][j] = "1"; //下三角初值
				}
				else {
					values[i][j] = "1"; //上三角初值
				}
			}
		}	
		return values;
	}
	
	//返回判断矩阵
	//只提取上三角，下三角推导得出。
	public double[][] getMat() {
		double[][] val = new double[row][col];
		for(int i=1;i<row+1;i++){
			for(int j=i+1;j<col+1;j++){
				val[i-1][j-1] = str_double_dict.get(tblModel.getValueAt(i, j).toString());
				val[j-1][i-1] = 1/val[i-1][j-1];
			}
			val[i-1][i-1] = 1;
		}		
		return val;
	}
	
	//table的值直接由tblModel得到。与toString1的区别是，后者的下三角是推导得出。
	@Override
	public  String toString() {
		String s = "";
		for(int i=1;i<row+1;i++){
			for(int j=1;j<col+1;j++){
				s += String.format("%-4s  ", tblModel.getValueAt(i, j).toString());
			}
			s += "\n";
		}
		return s;
	}
	
	//取上三角的值，下三角由上三角推导得出。
	public  String toString1() {
		String[][] val = new String[row][col];
		for(int i=1;i<row+1;i++){
			for(int j=i+1;j<col+1;j++){
				String s = tblModel.getValueAt(i, j).toString();
				if (s==null) {
					val[i-1][j-1] =val[j-1][i-1] =null;
				} else {
					val[i-1][j-1] = s;
					val[j-1][i-1] = reciprocalStr_dict.get(s);
				}
			}
			val[i-1][i-1] = "1";
		}		
		String s="";
		for(int i=0;i<row;i++){
			for (int j = 0; j < col; j++) {
				s += String.format("%-4s  ", val[i][j]);
			}
			s += "\n";
		}
		return s;
	}

	public static void main(String[] args) {
		JFrame frm = new JFrame();
		String factor ="GDP";
		String[] names={"China","USA","Japan","Russia"};
		String[][] values={{"1","2","1/4","3"},
						{"1","2","1/4","3"},
						{"1","2","1/4","3"},
						{"1","2","1/4","3"},};
		JTable_CompMatrix tbl = new JTable_CompMatrix(factor,names);
		frm.setLayout(new BorderLayout());
		JPanel panelTable = new JPanel();
		panelTable.add(tbl);
		frm.add(panelTable,BorderLayout.CENTER);
		
		JButton buttonSetvalue = new JButton("setValue");
		JButton buttonReset = new JButton("reset");
		JButton buttonPrint = new JButton("print");
		Box hBox = Box.createHorizontalBox();
		hBox.add(buttonSetvalue);
		hBox.add(Box.createHorizontalStrut(20));
		hBox.add(buttonReset);
		hBox.add(Box.createHorizontalStrut(20));
		hBox.add(buttonPrint);

		frm.add(hBox,BorderLayout.SOUTH);
		
		buttonSetvalue.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				tbl.setMat(values);
			}
		});
		
		buttonReset.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				tbl.reset();	
			}
		});
		
		buttonPrint.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(tbl);	
				//System.out.println(tbl.getMat());
			}
		});
		

		
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.pack();
	}

}
