﻿package zlbyzc.sub3.analysis.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;
import zlbyzc.sub3.analysis.services.SwotPropertyDAO;
import zlbyzc.sub3.analysis.services.interfaces.SwotPropertyDAOInterface;

public class SwotActorPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	private SwotPropertyDAOInterface swotPropertyDAO;
	private SwotActor swotActor;

	private JPanel panelLabelAdvantageANDChance;
	private JPanel panelTableAdvantageANDChance;
	private JPanel panelLabelActorName;
	private JPanel panelTableDisadvantageANDThreat;
	private JPanel panelLabelDisadvantageANDThreat;
	
	//显示标签
	private JLabel labelAdvantage;
	private JLabel labelChance;
	private JLabel labelDisadvantage;
	private JLabel labelThreat;
	private JLabel labelActorName;
	
	//四个属性table
	private DefaultTableModel dtm;
	private String propertyType;		//属性名称标志
	private int selectedTableRow;
	private JTable tableAdvantage;
	private JTable tableChance;
	private JTable tableDisadvantage;
	private JTable tableThreat;
	private JScrollPane scrollpaneTableAdvantage;
	private JScrollPane scrollpaneTableChance;
	private JScrollPane scrollpaneTableDisadvantage;
	private JScrollPane scrollpaneTableThreat;
	
	//右键菜单
	private JPopupMenu popupMenu;
	private JMenuItem popmenuItemAddProperty;
	private JMenuItem popmenuItemDeleteProperty;
	private JMenuItem popmenuItemModifyProperty;
	
	//表格双击修改属性内容面板
	private JFrame frameModifyProperty;
	private JPanel panelModifyProperty;
	private JPanel panelLayLabelInModifyProperty;
	private JPanel panelLaytextAreaContentInModifyProperty;
	private JPanel panelLayButtonInModifyProperty;
	private JLabel lablePropertyContentInModifyProperty;
	private JTextArea textAreaPropertyContentInModifyProperty;
	private JButton buttonConfirmInModifyProperty;
	private JButton buttonCancelInModifyProperty;
	
	//表格右键菜单添加属性内容面板
	private JFrame frameAddProperty;
	private JPanel panelAddProperty;
	private JPanel panelLayLabelInAddProperty;
	private JPanel panelLayTextareaInAddProperty;
	private JPanel panelLayButtonInAddProperty;
	private JLabel lableContentInAddProperty;
//	private JLabel lableRemark1InAddProperty;
//	private JLabel lableRemark2InPopmenuAddProperty;
	private JTextArea textareaContentInAddProperty;
//	private JTextArea textareaRemark1InAddProperty;
//	private JTextArea textareaRemark2InAddProperty;
	private JButton buttonConfirmInAddProperty;
	private JButton buttonCancelInAddProperty;
	
	public SwotActorPanel(SwotActor swotActor) {	//参与者编号作为参数

		swotPropertyDAO = new SwotPropertyDAO();
		this.swotActor = swotActor;
		
		initializeComponent();
		
		layoutComponent();
		
	}	

	public void initializeComponent() {
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panelLabelAdvantageANDChance = new JPanel();
		panelTableAdvantageANDChance = new JPanel();
		panelLabelActorName = new JPanel();
		panelTableDisadvantageANDThreat = new JPanel();
		panelLabelDisadvantageANDThreat = new JPanel();
		
		panelLabelAdvantageANDChance.setLayout(new BoxLayout(panelLabelAdvantageANDChance, BoxLayout.X_AXIS));
		panelTableAdvantageANDChance.setLayout(new BoxLayout(panelTableAdvantageANDChance, BoxLayout.X_AXIS));
		panelLabelActorName.setLayout(new BoxLayout(panelLabelActorName, BoxLayout.X_AXIS));
		panelTableDisadvantageANDThreat.setLayout(new BoxLayout(panelTableDisadvantageANDThreat, BoxLayout.X_AXIS));
		panelLabelDisadvantageANDThreat.setLayout(new BoxLayout(panelLabelDisadvantageANDThreat, BoxLayout.X_AXIS));
		
        //显示标签
        labelAdvantage = new JLabel("优势");
        labelChance = new JLabel("机会");
        labelDisadvantage = new JLabel("劣势");
        labelThreat = new JLabel("威胁");
        labelActorName = new JLabel(swotActor.getActorName());
        
        tableAdvantage = initializeTable("advantage");
		tableChance = initializeTable("chance");
		tableDisadvantage = initializeTable("disadvantage");
		tableThreat = initializeTable("threat");
		
		//将table装入JSplitPane
		scrollpaneTableAdvantage = new JScrollPane(tableAdvantage);
		scrollpaneTableChance = new JScrollPane(tableChance);
		scrollpaneTableDisadvantage = new JScrollPane(tableDisadvantage);
		scrollpaneTableThreat = new JScrollPane(tableThreat);

		//右键菜单
		popupMenu = new JPopupMenu();
		popmenuItemAddProperty = new JMenuItem("添加");  
	    popmenuItemDeleteProperty = new JMenuItem("删除");  
	    popmenuItemModifyProperty = new JMenuItem("修改");
 
		popmenuItemAddProperty.addActionListener(this);
		popmenuItemDeleteProperty.addActionListener(this);
		popmenuItemModifyProperty.addActionListener(this);
		
	}

	public void layoutComponent() {
		
		panelLabelAdvantageANDChance.add(labelAdvantage);
		panelLabelAdvantageANDChance.add(Box.createHorizontalGlue());
		panelLabelAdvantageANDChance.add(labelChance);
		panelLabelAdvantageANDChance.add(Box.createHorizontalGlue());
		
		panelTableAdvantageANDChance.add(scrollpaneTableAdvantage);
		panelTableAdvantageANDChance.add(scrollpaneTableChance);
		
		panelLabelActorName.add(labelActorName);
		
		panelLabelDisadvantageANDThreat.add(labelDisadvantage);
		panelLabelDisadvantageANDThreat.add(Box.createHorizontalGlue());
		panelLabelDisadvantageANDThreat.add(labelThreat);
		panelLabelDisadvantageANDThreat.add(Box.createHorizontalGlue());
		
		panelTableDisadvantageANDThreat.add(scrollpaneTableDisadvantage);
		panelTableDisadvantageANDThreat.add(scrollpaneTableThreat);
		
		add(panelLabelAdvantageANDChance);
		add(panelTableAdvantageANDChance);
		add(panelLabelActorName);
		add(panelLabelDisadvantageANDThreat);
		add(panelTableDisadvantageANDThreat);
		
	    popupMenu.add(popmenuItemAddProperty);  
        popupMenu.add(popmenuItemDeleteProperty);  
        popupMenu.add(popmenuItemModifyProperty);  
		
	}
	
	public JTable initializeTable(final String tableType) {
						
	    dtm = new DefaultTableModel() {
		      /**
			 * 
			 */
	    	private static final long serialVersionUID = 1L;
			public Class<String> getColumnClass(int columnIndex) {
		        return String.class;
		    }
		    public boolean isCellEditable(int row, int column) {		//不可编辑，变成双击弹出修改框
		        return false;
		    }
	    };
	    dtm.setDataVector(
	    	new Object[][]{
		    	{"", "", -1}
		    }, 
		    new Object[] {"编号", "内容", "PropertyID"}
	    );

	    final JTable table = new JTable(dtm);
	    
	    TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setMaxWidth(40);		
		columnModel.getColumn(0).setPreferredWidth(40);
		columnModel.removeColumn(columnModel.getColumn(2));		//第三列为隐藏列，存储属性ID，便于deleteProperty。获得remove掉的列的data必须使用TableModel
	    table.setDefaultRenderer(String.class, new MultiLineTableCellRender());		//渲染成可换行
	    
	    table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				
				selectedTableRow = table.rowAtPoint(e.getPoint());		//getSelectedRow()对于右键无效，值为-1
				
				if(e.getButton() == MouseEvent.BUTTON3) {	//监听右键菜单
					
					popupMenu.show(e.getComponent(), e.getX(), e.getY());	 //在鼠标点击位置弹出右键菜单
	                
				} else if(e.getClickCount() == 2) {		//监听双击菜单

					if(getSelectedTable().getValueAt(0, 0) != "") 
						modifyProperty();
					else
						JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
				}
				
				table.changeSelection(table.getSelectedRow(), table.getSelectedColumn(), true, false);
				table.requestFocus();
				
                propertyType = tableType;		//标记当前选中的属性表格
			}
		});
	   
		return table;
		
	}
	
	public void addProperty() {
		
		/*-------------初始化Start--------------*/
		frameAddProperty = new JFrame("添加属性");
		frameAddProperty.setSize(500, 400);
		frameAddProperty.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameAddProperty.setLocationRelativeTo( null ); 
		frameAddProperty.getContentPane().setLayout(new BorderLayout());
		
		panelAddProperty = new JPanel();
		panelLayLabelInAddProperty = new JPanel();
		panelLayTextareaInAddProperty = new JPanel();
		panelLayButtonInAddProperty = new JPanel();
		panelAddProperty.setLayout(new BoxLayout(panelAddProperty, BoxLayout.Y_AXIS));
		panelLayTextareaInAddProperty.setLayout(new BoxLayout(panelLayTextareaInAddProperty, BoxLayout.X_AXIS));
		panelLayButtonInAddProperty.setLayout(new BoxLayout(panelLayButtonInAddProperty, BoxLayout.X_AXIS));
		
		lableContentInAddProperty = new JLabel("内容");
//		lableRemark1InAddProperty = new JLabel("备注1");
//		lableRemark2InPopmenuAddProperty = new JLabel("备注2");
		
		textareaContentInAddProperty = new JTextArea(5,5);
		textareaContentInAddProperty.setLineWrap(true);		//支持自动换行
		textareaContentInAddProperty.setBorder(BorderFactory.createTitledBorder(""));
//		textareaRemark1InAddProperty = new JTextArea();
//		textareaRemark2InAddProperty = new JTextArea();		
//		textareaRemark1InAddProperty.setLineWrap(true);		//支持自动换行
//		textareaRemark2InAddProperty.setLineWrap(true);		//支持自动换行
		
//		textareaContentInAddProperty.setMinimumSize(new Dimension(200,200));
//		textareaContentInAddProperty.setMaximumSize(new Dimension(900,900));
//		textareaRemark1InAddProperty.setMinimumSize(new Dimension(100,250));
//		textareaRemark1InAddProperty.setMaximumSize(new Dimension(200,200));
//		textareaRemark2InAddProperty.setMinimumSize(new Dimension(100,250));
//		textareaRemark2InAddProperty.setMaximumSize(new Dimension(200,200));
	

		buttonConfirmInAddProperty = new JButton("确定");
		buttonCancelInAddProperty = new JButton("取消");		
		
		buttonConfirmInAddProperty.addActionListener(this);
		buttonCancelInAddProperty.addActionListener(this);
		/*-------------初始化End--------------*/
		
		/*-------------布局Start--------------*/
		panelLayLabelInAddProperty.add(lableContentInAddProperty);
		panelLayTextareaInAddProperty.add(Box.createHorizontalStrut(20));
		panelLayTextareaInAddProperty.add(textareaContentInAddProperty);
		panelLayTextareaInAddProperty.add(Box.createHorizontalStrut(20));
//		panelLayTextareaInAddProperty.add(lableRemark1InAddProperty);	
//		panelLayTextareaInAddProperty.add(textareaRemark1InAddProperty);
//		panelLayTextareaInAddProperty.add(lableRemark2InPopmenuAddProperty);	
//		panelLayTextareaInAddProperty.add(textareaRemark2InAddProperty);	
		panelLayButtonInAddProperty.add(buttonConfirmInAddProperty);
		panelLayButtonInAddProperty.add(Box.createHorizontalStrut(10));
		panelLayButtonInAddProperty.add(buttonCancelInAddProperty);
		
		panelAddProperty.add(Box.createVerticalStrut(20));
		panelAddProperty.add(panelLayLabelInAddProperty);
		panelAddProperty.add(Box.createVerticalStrut(5));
		panelAddProperty.add(panelLayTextareaInAddProperty);
		panelAddProperty.add(Box.createVerticalStrut(10));
		panelAddProperty.add(panelLayButtonInAddProperty);
		panelAddProperty.add(Box.createVerticalStrut(20));
		
		frameAddProperty.setContentPane(panelAddProperty);			
		frameAddProperty.setVisible(true);
		/*-------------布局End--------------*/
		
	}
	
	public void deleteProperty() {

		if(getSelectedTable().getValueAt(0, 0) != "") {		
			swotPropertyDAO.deletePropertyByID((int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));		//获得remove掉的列的data必须使用TableModel
			
			dtm = (DefaultTableModel)getSelectedTable().getModel();
			dtm.removeRow(selectedTableRow);
			
			if(getSelectedTable().getRowCount() == 0) {	
				dtm.addRow(new Object[]{"", "", -1});		
			}else {
				for(int i = 0; i < dtm.getRowCount(); i++) {
					dtm.setValueAt(i + 1, i, 0);
				}
			}
		}else {
			JOptionPane.showMessageDialog(null, "当前表格无内容，无法做删除内容操作", "",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//修改属性内容面板START（双击单元格）
	public void modifyProperty() {
		
		/*-------------初始化Start--------------*/
		frameModifyProperty = new JFrame("修改属性");
		frameModifyProperty.setSize(500, 400);
		frameModifyProperty.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameModifyProperty.setLocationRelativeTo( null ); 
		
		panelModifyProperty = new JPanel();
		panelModifyProperty = new JPanel();
		panelLayLabelInModifyProperty = new JPanel();
		panelLaytextAreaContentInModifyProperty = new JPanel();
		panelLayButtonInModifyProperty = new JPanel();
		panelModifyProperty.setLayout(new BoxLayout(panelModifyProperty, BoxLayout.Y_AXIS));
		panelLaytextAreaContentInModifyProperty.setLayout(new BoxLayout(panelLaytextAreaContentInModifyProperty, BoxLayout.X_AXIS));
		panelLayButtonInModifyProperty.setLayout(new BoxLayout(panelLayButtonInModifyProperty, BoxLayout.X_AXIS));
		
		lablePropertyContentInModifyProperty=new JLabel("内容");
		
		textAreaPropertyContentInModifyProperty = new JTextArea(5,5);
		textAreaPropertyContentInModifyProperty.setText((String) getSelectedTable().getValueAt(selectedTableRow, 1));
		textAreaPropertyContentInModifyProperty.setLineWrap(true);		//支持自动换行
		textAreaPropertyContentInModifyProperty.setBorder(BorderFactory.createTitledBorder(""));
//		textAreaPropertyContentInModifyProperty.setMinimumSize(new Dimension(100,80));
//		textAreaPropertyContentInModifyProperty.setMaximumSize(new Dimension(150,130));
		
		buttonConfirmInModifyProperty = new JButton("确定");
		buttonCancelInModifyProperty = new JButton("取消");		
		
		buttonConfirmInModifyProperty.addActionListener(this);
		buttonCancelInModifyProperty.addActionListener(this);
		/*-------------初始化End--------------*/
		
		/*-------------布局Start--------------*/
		panelLayLabelInModifyProperty.add(lablePropertyContentInModifyProperty);
		panelLaytextAreaContentInModifyProperty.add(Box.createHorizontalStrut(20));
		panelLaytextAreaContentInModifyProperty.add(textAreaPropertyContentInModifyProperty);
		panelLaytextAreaContentInModifyProperty.add(Box.createHorizontalStrut(20));
		
		panelLayButtonInModifyProperty.add(buttonConfirmInModifyProperty);
		panelLayButtonInModifyProperty.add(Box.createHorizontalStrut(10));
		panelLayButtonInModifyProperty.add(buttonCancelInModifyProperty);
		
		panelModifyProperty.add(Box.createVerticalStrut(20));
		panelModifyProperty.add(panelLayLabelInModifyProperty);
		panelModifyProperty.add(Box.createVerticalStrut(5));
		panelModifyProperty.add(panelLaytextAreaContentInModifyProperty);
		panelModifyProperty.add(Box.createVerticalStrut(10));
		panelModifyProperty.add(panelLayButtonInModifyProperty);
		panelModifyProperty.add(Box.createVerticalStrut(20));
		
		frameModifyProperty.setContentPane(panelModifyProperty);			
		frameModifyProperty.setVisible(true);
		/*-------------布局End--------------*/
		
	}
	//修改属性内容面板END
	
	//返回当前选中的table
	public JTable getSelectedTable() {
		
		if(propertyType.equals("advantage")) {
			return tableAdvantage;
		}
		else if(propertyType.equals("chance")) {
			return tableChance;
		}
		else if(propertyType.equals("disadvantage"))  {
			return tableDisadvantage;
		}
		else if(propertyType.equals("threat")) {
			return tableThreat;
		}
		
		return null;
		
	}	
	
	//查询面板所用
	public JTable getInsertTable(String PropertyType) {
		
		if(PropertyType.equals("advantage")) {
			return tableAdvantage;
		}
		else if(PropertyType.equals("chance")) {
			return tableChance;
		}
		else if(PropertyType.equals("disadvantage"))  {
			return tableDisadvantage;
		}
		else if(PropertyType.equals("threat")) {
			return tableThreat;
		}
		
		return null;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == popmenuItemAddProperty) {		//右键菜单添加属性
			
			addProperty();
			
		}
		
		else if(e.getSource() == popmenuItemDeleteProperty) {
			
			deleteProperty();
			
		}
		
		else if(e.getSource() == popmenuItemModifyProperty) {
			
			if(getSelectedTable().getValueAt(0, 0) != "") 
				modifyProperty();
			else
				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		else if(e.getSource() == buttonConfirmInModifyProperty) {
			
			SwotActorProperty swotActorProperty = new SwotActorProperty();
			swotActorProperty.setPropertyID((int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));		//获得remove掉的列的data必须使用TableModel
			swotActorProperty.setPropertyContent(textAreaPropertyContentInModifyProperty.getText());
	//		swotActorProperty.setPropertyMark1
	//		swotActorProperty.setPropertyMark2
	
			swotPropertyDAO.updateProperty(swotActorProperty);
				
			getSelectedTable().setValueAt(swotActorProperty.getPropertyContent(), selectedTableRow, 1);
			
		}
		
		else if(e.getSource() == buttonCancelInModifyProperty) {
			
			frameModifyProperty.dispose();
			
		}
		
		else if(e.getSource() == buttonConfirmInAddProperty) {
			
			SwotActorProperty swotActorProperty = new SwotActorProperty();
			swotActorProperty.setPropertyType(propertyType);
			swotActorProperty.setPropertyContent(textareaContentInAddProperty.getText());

			swotPropertyDAO.addProperty(swotActor, swotActorProperty);
			
			dtm = (DefaultTableModel)getSelectedTable().getModel();
			if(dtm.getValueAt(0, 0) == "") {
				dtm.setValueAt(1, 0, 0);
				dtm.setValueAt(swotActorProperty.getPropertyContent(), 0, 1);
				dtm.setValueAt(swotActorProperty.getPropertyID(), 0, 2);
			} else{	
				dtm.addRow(new Object[]{dtm.getRowCount()+1, swotActorProperty.getPropertyContent(), swotActorProperty.getPropertyID()});		
			}
			
			buttonConfirmInAddProperty.setText("继续添加");
			textareaContentInAddProperty.setText("");
//			textareaRemark1InAddProperty.setText("");
//			textareaRemark2InAddProperty.setText("");
			
		}
		
		else if(e.getSource() == buttonCancelInAddProperty) {
			
			frameAddProperty.dispose();
			
		}
		
	}
	
}
