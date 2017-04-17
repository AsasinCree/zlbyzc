package zlbyzc.sub3.zlpj.anp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class AnpfactorJtable extends JTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7873158084488747557L;
	
	private int row1; //判断矩阵的实际行列数。
	private int col1;
	//private JTable resulttblModel1;
	DefaultTableModel resulttblModel;
	
	DefaultTableCellRenderer resulttblCellRenderer;
	
	JTable cbox;
	String[] measureStrList={"0","0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1"};
	double[] measureList={0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	HashMap<String,Double> str_double_dict;  // "1/9" --> 1.0/9
	HashMap<String,String> reciprocalStr_dict;  // "1/9" --> "9"

	public AnpfactorJtable(String[] name){
		row1 = name.length;
		col1 = name.length;
		init();	
		
    	String[][] resultMos1=getDefaultTableValues(name);
    	resulttblModel = new DefaultTableModel(resultMos1,new String[name.length+1]){
    		/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column){
				if (row!=0 && column!=0) return true; //除第一行和第一列外都可编辑
	        	 else return false;        	 
	        } 
    	};
    	//resulttblModel1=new JTable(resulttblModel);
    	//resulttblModel1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE); // 加入此行代码 
    	super.setModel(resulttblModel);
    	setRowHeight(30);
    	
    	//背景颜色渲染
    	resulttblCellRenderer = new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column){
				Component cell = super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
//				cell.setBackground(Color.white);
//				Color Matrixbackc=new Color(47,128,194);
//				if (row==0 || column==0) {
//					cell.setBackground(Matrixbackc);
//				}
				return cell;
			}
		};		
		super.setDefaultRenderer(Object.class, resulttblCellRenderer);
	}
	private String callToString() {
		return toString();
	}
	
	private void init() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
		//str_double_dict赋值
		str_double_dict = new HashMap<String,Double>();
		for(int i=0;i<measureList.length;i++){
			str_double_dict.put(measureStrList[i], measureList[i]);
		}
		//cbox添加成员
		cbox = new JTable();
		for(String s: measureStrList){
			//cbox.addItem(s);
		}
		
	}
	
    //重置
	public void reset() {
		for (int r = 1; r < row1+1; r++) {
			for (int c = 1; c < col1+1; c++) {
				resulttblModel.setValueAt("0", r, c);
			}			
		}	
	}
	
	//为表格设置一组数据
	public void setMat(String[][] values) {
		
		for (int r = 0; r < row1; r++) {
			for (int c = 0; c < col1; c++) {
				resulttblModel.setValueAt(values[r][c], r+1, c+1);
			}			
		}		
	}	
	
	//得到初始化表格矩阵
	private String[][] getDefaultTableValues(String[] name) {
		int len = name.length;
		String[][] values = new String[len+1][len+1];
		for(int i=1;i<len+1;i++){
			values[0][i] = values[i][0] = name[i-1];
		}
		for(int i=1;i<len+1;i++){
			for(int j=1;j<len+1;j++){
				values[i][j] = "0";
			}
		}		
		return values;
	}
	public double[][] getMat1() {
		double[][] val = new double[row1][col1];
//		double[][] val1=new double[row1][col1];
		for(int i=1;i<row1+1;i++){
			for(int j=1;j<col1+1;j++){
				String s=getModel().getValueAt(i, j).toString();
				val[i-1][j-1] = Double.valueOf(s);
//				BigDecimal bg = new BigDecimal(val[i-1][j-1]);
//				val1[i-1][j-1] = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
		}		
		return val;
//		return val1;
	}
	@Override
	public  String toString() {
		String[][] val = new String[row1][col1];
		for(int i=1;i<row1+1;i++){
			for(int j=1;j<col1+1;j++){
				String s=getModel().getValueAt(i, j).toString();
				if (s==null) {
					val[i-1][j-1]=null;
				} else {
					val[i-1][j-1] = s;
				}
			}
		}		
		String s="";
		for(int i=0;i<row1;i++){
			for (int j = 0; j < col1; j++) {
				s += String.format("%-6s  ", val[i][j]);
			}
			s += "\n";
		}
		return s;
	}

	
    	public static void main(String[] args) {
    		JFrame frm = new JFrame();
    		String[] index={"China","USA","Japan","Russia"};
    		
    		//Matrix resultM = new Matrix(mat);
    		AnpfactorJtable tbl1 = new AnpfactorJtable(index);
    		frm.setLayout(new BorderLayout());
    		JPanel p = new JPanel();
    		p.add(tbl1);
    		frm.add(p,BorderLayout.NORTH);
    		JButton bt = new JButton("OK");
    		frm.add(bt,BorderLayout.SOUTH);
    		bt.addActionListener(new ActionListener() {			
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				System.out.println(tbl1);				
    				//double[][] mat = tbl1.getMat1();
    				//System.out.print(mat);
    			}
    		});
    		
    		frm.setVisible(true);
    		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frm.pack();
    		


    	}

}
