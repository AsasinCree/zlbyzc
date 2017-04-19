package zlbyzc.sub3.zlpj.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

//扩展AbstractTableModel，用于将一个ResultSet包装成TableModel
public class ResultSetTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private String[] columnNames;

	// 构造器，初始化rs和rsmd两个属性
	public ResultSetTableModel(ResultSet aResultSet) {
		rs = aResultSet;
		try {
			rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			columnNames = new String[colCount];
			for (int i = 0; i < colCount; i++) {
				columnNames[i] = rsmd.getColumnName(i+1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//增加的方法，可以设置列名（因为mysql字段的变量名可能意思不清晰）
	public void setColumnNames(String[] colNames) {
		if(colNames.length!=columnNames.length){
			System.out.println("colNames has error length!");
			return;
		}
		for (int i = 0; i < columnNames.length; i++) {
			columnNames[i] = colNames[i];
		}
		super.fireTableStructureChanged(); 		
	}

	// 重写getColumnName方法，用于为该TableModel设置列名
	@Override
	public String getColumnName(int c) {
		return columnNames[c];
	}

	// 重写getColumnCount方法，用于设置该TableModel的列数
	@Override
	public int getColumnCount() {
		try {
			return rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 重写getValueAt方法，用于设置该TableModel指定单元格的值
	@Override
	public Object getValueAt(int r, int c) {
		try {
			rs.absolute(r + 1);
			return rs.getObject(c + 1);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 重写getColumnCount方法，用于设置该TableModel的行数
	@Override
	public int getRowCount() {
		try {
			rs.last();
			return rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	// 重写isCellEditable返回true，让每个单元格可编辑
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	// 重写setValueAt方法，用于实现用户编辑单元格时，程序做出对应的动作
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		try {
			// 结果集定位到对应的行数
			rs.absolute(row + 1);
			// 修改单元格多对应的值
			rs.updateObject(column + 1, aValue);
			// 提交修改
			rs.updateRow();
			// 触发单元格的修改事件
			fireTableCellUpdated(row, column);
		} catch (SQLException evt) {
			evt.printStackTrace();
		}
	}
}