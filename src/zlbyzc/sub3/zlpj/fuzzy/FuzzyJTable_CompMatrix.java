package zlbyzc.sub3.zlpj.fuzzy;

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

public class FuzzyJTable_CompMatrix extends JTable{
    private static final long serialVersionUID = 1L;
	
	private int row1; 
	private int col1;
    
    DefaultTableModel tblModel1;
    DefaultTableCellRenderer tblCellRenderer1;
    
    JTable cbox;
	String[] measureStrList={"0","0.1","0.2","0.3","0.4","0.5","0.6","0.7","0.8","0.9","1"};
	double[] measureList={0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	HashMap<String,Double> str_double_dict;  // "1/9" --> 1.0/9
	HashMap<String,String> reciprocalStr_dict;  // "1/9" --> "9"
	
	public FuzzyJTable_CompMatrix(String[] index,String[] comment){
		row1 = index.length;
		col1 = comment.length;		
		init();		
		
		String[][] values = getDefaultTableValues(index,comment); //valuesΪ����table��������һ��һ�С�	
		tblModel1 = new DefaultTableModel(values, new String[col1+1]){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column){  
	        	 if (row!=0 && column!=0 ) return true; //��������ɱ༭
	        	 else return false;        	 
	         } 
		};		
		super.setModel(tblModel1);
		setRowHeight(40);
	
		//背景颜色渲染
		tblCellRenderer1 = new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column){
				Component cell = super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
//				cell.setBackground(Color.white);	
//				if (row!=0 && column!=0 && row>=column) {
//					cell.setBackground(Color.gray);
//				}
				//else if(row==0 || column==0){
				//	cell.setBackground(Color.yellow);
				//}
//				Color Matrixbackc=new Color(47,128,194);
//				if(row==0 || column==0){
//					cell.setBackground(Matrixbackc);
//				}
				return cell;
			}
		};		
		super.setDefaultRenderer(Object.class, tblCellRenderer1);  
		
	}
	private String callToString() {
		return toString();
	}
	private void init(){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
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
				tblModel1.setValueAt("0", r, c);
			}			
		}	
	}
	
	//为表格设置一组数据
	public void setMat(String[][] values) {
				
		for (int r = 0; r < row1; r++) {
			for (int c = 0; c < col1; c++) {
				tblModel1.setValueAt(values[r][c], r+1, c+1);
			}			
		}		
	}	

	//得到初始化表格矩阵
	private String[][] getDefaultTableValues(String[] index,String[] comment) {
		int indexnum = index.length;
		int commentnum=comment.length;
		String[][] values = new String[indexnum+1][commentnum+1];
		for(int i=1;i<commentnum+1;i++){
			values[0][i] = comment[i-1];
		}
		for(int i=1;i<indexnum+1;i++){
			values[i][0] = index[i-1];
		}
		for(int i=1;i<indexnum+1;i++){
			for(int j=1;j<commentnum+1;j++){
				values[i][j] = "0";
			}
		}		
		return values;
	}
	
	//得到矩阵
	public double[][] getMat() {
		double[][] val = new double[row1][col1];
		for(int i=1;i<row1+1;i++){
			for(int j=1;j<col1+1;j++){
				String s=getModel().getValueAt(i, j).toString();
				val[i-1][j-1] = Double.valueOf(s);
				
			}
		}		
		return val;
	
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
		String[] comment={"best","good","bad"};
		FuzzyJTable_CompMatrix tbl = new FuzzyJTable_CompMatrix(index,comment);
		frm.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.add(tbl);
		frm.add(p,BorderLayout.NORTH);
		JButton bt = new JButton("OK");
		frm.add(bt,BorderLayout.SOUTH);
		bt.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(tbl);				
				//double[][] mat = tbl.getMat();
			}
		});
		
		frm.setVisible(true);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.pack();
	}


}
