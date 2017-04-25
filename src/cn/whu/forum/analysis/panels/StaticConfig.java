package cn.whu.forum.analysis.panels;

import java.awt.Font;

import javax.swing.UIManager;

public class StaticConfig {
	// 表格字体
	public static final Font FONT_TABLEHEADER = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_TABLE_CONTENT = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_TABLE_RIGHT_CLICK_MENU = new Font("瀹嬩綋", 0, 18); // 鍙抽敭鑿滃崟

	// 瀵硅瘽妗�
	public static final Font FONT_DIALOG_LABEL = new Font("瀹嬩綋", 0, 25);
	public static final Font FONT_DIALOG_TEXTFIELD = new Font("瀹嬩綋", 0, 20);
	public static final Font FONT_DIALOG_TEXTAREA = new Font("瀹嬩綋", 0, 20);
	public static final Font FONT_DIALOG_BUTTON = new Font("瀹嬩綋", 0, 20);

	// 鏂板缓浠诲姟闈㈡澘
	public static final Font FONT_NEWTASK_TITLE = new Font("榛戜綋", 0, 30);
	public static final Font FONT_NEWTASK_LABEL = new Font("瀹嬩綋", 0, 25);
	public static final Font FONT_NEWTASK_TEXTFIELD = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_NEWTASK_BUTTON = new Font("瀹嬩綋", 0, 20);
	public static final int WIDTH_JDATEPICKER_BUTTON = 15;

	// 缂栬緫闈㈡澘
	public static final Font FONT_EDITVIEW_TITLE = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_EDITVIEW_LABEL = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_EDITVIEW_TEXTFIELD = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_EDITVIEW_TEXTAREA = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_EDITVIEW_BUTTON = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_EDITVIEW_LIST = new Font("瀹嬩綋", 0, 18); // SWOT鐙湁
	public static final Font FONT_EDITVIEW_PROPERTY = new Font("瀹嬩綋", 0, 18); // SWOT鐙湁
	public static final Font FONT_EDITVIEW_ACTORNAME = new Font("瀹嬩綋", 0, 20); // SWOT鐙湁
	public static final int FONTSIZE_EDITVIEW_NODE_CONTENT = 20; // SWOT鐙湁
	public static final int FONTSIZE_EDITVIEW_NODE_EDGE = 20; // SWOT鐙湁

	// 鏌ユ壘闈㈡澘
	public static final Font FONT_SEARCHVIEW_TITLE = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_SEARCHVIEW_LABEL = new Font("瀹嬩綋", 0, 20);
	public static final Font FONT_SEARCHVIEW_TEXTFIELD = new Font("瀹嬩綋", 0, 18);
	public static final Font FONT_SEARCHVIEW_BUTTON = new Font("瀹嬩綋", 0, 18);

	public static void setJOptionPaneFont() {
		UIManager.put("OptionPane.font", StaticConfig.FONT_DIALOG_LABEL);
		UIManager.put("OptionPane.messageFont", StaticConfig.FONT_DIALOG_LABEL);
		UIManager.put("OptionPane.buttonFont", StaticConfig.FONT_DIALOG_BUTTON);
	}
}
