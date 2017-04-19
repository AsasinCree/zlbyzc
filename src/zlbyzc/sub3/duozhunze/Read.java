package zlbyzc.sub3.duozhunze;

import java.io.FileInputStream;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Read {
	private jxl.Workbook rwb = null;
	private int colCnt = 0;

	public int getColCount(int sheetIndex) {

		try {
			jxl.Sheet rs = rwb.getSheet(sheetIndex);
			colCnt = rs.getColumns();
		} catch (Exception e) {
			colCnt = 0;
		} finally {
			try {

			} catch (Exception e) {
				colCnt = 0;
			}
		}
		return colCnt;
	}

	public int getRowCount(int sheetIndex) {

		try {
			jxl.Sheet rs = rwb.getSheet(sheetIndex);
			colCnt = rs.getRows();
		} catch (Exception e) {
			colCnt = 0;
		} finally {
			try {

			} catch (Exception e) {
				colCnt = 0;
			}

		}
		return colCnt;

	}

	public boolean openExcel(String fileName) {
		boolean Rtn = false;
		try {
			InputStream is = new FileInputStream(fileName);
			rwb = Workbook.getWorkbook(is);
			Rtn = true;
		} catch (Exception e) {
			Rtn = false;
		} finally {
			try {
			} catch (Exception e) {
			}
		}
		return Rtn;
	}

	public String getCellContent(int col, int row) {
		String cellContent = "";
		try {
			Sheet rs = rwb.getSheet(0);
			Cell c00 = rs.getCell(col, row);
			cellContent = c00.getContents();
		} catch (Exception e) {
			cellContent = "";
		} finally {
			try {

			} catch (Exception e) {
				cellContent = "";
			}
		}
		return cellContent;
	
	}
}
