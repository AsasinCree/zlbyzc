package zlbyzc.sub3.zlpj.fuzzy;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Fuzzyresulttable extends JTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7873158084488747557L;
	DefaultTableModel resulttblModel;
	DefaultTableCellRenderer resulttblCellRenderer;

	public Fuzzyresulttable(String[] comment,String[] mat){
    	String[][] resultMos1=getDefaultTableValues(comment,mat);
    	resulttblModel=new DefaultTableModel(resultMos1,new String[mat.length]){
    		/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column){
    			return false;
    		}
    	};
    	super.setModel(resulttblModel);
    	setRowHeight(40);
    	
    	resulttblCellRenderer = new DefaultTableCellRenderer(){
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                                                           boolean hasFocus, int row, int column){
				Component cell = super.getTableCellRendererComponent(table, value,isSelected,hasFocus,row,column);
//				cell.setBackground(Color.white);
//				Color Matrixbackc=new Color(47,128,194);
//				if (row==0 ) {
//					cell.setBackground(Matrixbackc);
//				}
				return cell;
			}
		};		
		super.setDefaultRenderer(Object.class, resulttblCellRenderer);
	}
	private String[][] getDefaultTableValues(String[] comment,String[] mat) {
		int len = comment.length;
		String[][] values = new String[2][len];
		for(int i=0;i<len;i++){
			
				values[0][i] = comment[i];
			
		}
		
		for(int i=0;i<len;i++){
			values[1][i] = mat[i];
		}		
		return values;
	}
    	public static void main(String[] args) {
    		JFrame frm = new JFrame();
    		
    		String[] comment={"best","good","bad"};
    		String[] mat={"0","0","0"};
    		//Matrix resultM = new Matrix(mat);
    		Fuzzyresulttable tbl1 = new Fuzzyresulttable(comment,mat);
    		frm.setLayout(new BorderLayout());
    		JPanel p = new JPanel();
    		p.add(tbl1);
            frm.add(p);
    		frm.setVisible(true);
    		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		frm.pack();
    		


    	}
	


	private void Matrix() {
		// TODO Auto-generated method stub
		
	}

}
