package zlbyzc.sub3.analysis.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.hibernate.hql.internal.ast.util.NodeTraverser;
import org.liukan.mgraph.mgraphx;

import jdk.nashorn.internal.ir.Flags;
import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;
import zlbyzc.sub3.analysis.services.SwotActorDAO;
import zlbyzc.sub3.analysis.services.SwotPropertyDAO;
import zlbyzc.sub3.analysis.services.SwotTaskDAO;
import zlbyzc.sub3.analysis.services.interfaces.SwotActorDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotPropertyDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotTaskDAOInterface;

public class SwotActorGraphPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	private SwotPropertyDAOInterface swotPropertyDAO;
	private SwotActor swotActor;

	private int flag = 0;		//panel初始化会刷新两次，第二次才是正确的大小
	private static mgraphx graph;
	private int nodeWidth;
	private int nodeHeight;
	
	//各属性个数的序号
	private int countAdvantage;
	private int countChance;
	private int countDisadvantage;
	private int countThreat;
	private int countMax;	//最大的行数
	
	private int panelWidth;
	private int panelHeight;
	
	//显示标签节点
	private Object nodeAdvantage;
	private Object nodeChance;
	private Object nodeDisadvantage;
	private Object nodeThreat;
	private Object nodeActorName;
	private List<SwotActorProperty> swotPropertyList;
	private int flagExample;		//判断是否是示例
	
	private int propertyTypeFlag;

	//右键菜单
	private JPopupMenu popupMenu;
	private JMenuItem popmenuItemAddProperty;
	private JMenuItem popmenuItemDeleteProperty;
	private JMenuItem popmenuItemModifyProperty;
	
//	//表格双击修改属性内容面板
//	private JFrame frameModifyProperty;
//	private JPanel panelModifyProperty;
//	private JPanel panelLayLabelInModifyProperty;
//	private JPanel panelLaytextAreaContentInModifyProperty;
//	private JPanel panelLayButtonInModifyProperty;
//	private JLabel lablePropertyContentInModifyProperty;
//	private JTextArea textAreaPropertyContentInModifyProperty;
//	private JButton buttonConfirmInModifyProperty;
//	private JButton buttonCancelInModifyProperty;
	
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
	
//	public static void main(String[] args)
//	{
//		JFrame  frame = new JFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLayout(new BorderLayout());
//		
//		SwotActor swotActor = new SwotActor();
//		SwotActorDAOInterface swotTaskDAO = new SwotActorDAO();
//		swotActor = swotTaskDAO.getActorByID(5);
//		SwotActorGraphPanel p = new SwotActorGraphPanel(swotActor);
//				
//		frame.getContentPane().add(p,BorderLayout.CENTER);
//				
//		frame.pack();
//		frame.setSize(500, 200);
//        frame.setLocationRelativeTo(null);
//		frame.setVisible(true);
//	}
	
	public SwotActorGraphPanel(SwotActor swotActor) {	
		swotPropertyDAO = new SwotPropertyDAO();
		this.swotActor = swotActor;
		flagExample = 0;
		initializeComponent();
		
		layoutComponent();
	}	
	
	public SwotActorGraphPanel(SwotActor swotActor, List<SwotActorProperty> swotPropertyList) {			//【示例所用】
		this.swotActor = swotActor;
		this.swotPropertyList = swotPropertyList;
		flagExample = 1;
		initializeComponent();
		
		layoutComponent();
	}	
	
	public void initializeComponent() {
		setLayout(new BorderLayout());
		
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent evt) {
				panelWidth = getSize().width;
				panelHeight = getSize().height;

				refreshPanel();
		      }
		});
		
		graph = new mgraphx(false,15,15,true);
		
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
		//在SwotEditPanel中装入JInternalFrame并不会触发该panel的componentResized，故要人为的做第一次刷新才会显示出
		refreshPanel();		
		
	    popupMenu.add(popmenuItemAddProperty);  
        popupMenu.add(popmenuItemDeleteProperty);  
        popupMenu.add(popmenuItemModifyProperty);  	
	}
	
	public void refreshPanel(){
		if(flag == 1){
			if(graph != null) {
				removeAll();
				graph = new mgraphx(false,15,15,true);		//默认，边字体大小，节点字体大小，默认
			}
				
			graph.setBounds(1, 1, panelWidth, panelHeight);
			graph.setBorder(new EmptyBorder(0, 0, 0, 0));
			
			nodeWidth = graph.getWidth()/3;	
			nodeHeight = 22;		//15号字体行高为22
						
	        //显示标签节点
			importData();       
	           
	        //添加边
	        graph.addEdge("优势", nodeActorName, nodeAdvantage);
	        graph.addEdge("机会", nodeActorName, nodeChance);
	        graph.addEdge("劣势", nodeActorName, nodeDisadvantage);
	        graph.addEdge("威胁", nodeActorName, nodeThreat);
	
			add(graph);
			revalidate();
			repaint();
		}else
			flag = 1;
	}
	
	public void importData(){
		SwotActorDAOInterface swotActorDAO = new SwotActorDAO();
		if(flagExample == 0)	//不是示例，从数据库载入
			swotPropertyList = swotActorDAO.getAllActorPropertys(swotActor);
		
		String reslutAdvantage = "";
		String reslutChance = "";
		String reslutDisadvantage = "";
		String reslutThreat = "";
		
		//重置序号
		countAdvantage = 1;
		countChance = 1;
		countDisadvantage = 1;
		countThreat = 1;
		
		//中间最大值
		int countMax1;
		int countMax2;

		if(swotPropertyList != null) {
			for(SwotActorProperty swotProperty:swotPropertyList) {
			//	System.out.println("Content:"+swotProperty.getPropertyContent());
		
			    String outputString = "";		
			    outputString = swotProperty.getPropertyContent();
			 
			    if(swotProperty.getPropertyType().equals("advantage")){
			    	if(reslutAdvantage.equals(""))
			    		reslutAdvantage = String.valueOf(countAdvantage++)+ "." + outputString;
			    	else {
			    		reslutAdvantage = reslutAdvantage + "\n" + String.valueOf(countAdvantage++)+ "." + outputString;
					}	
			    	
			    }else if(swotProperty.getPropertyType().equals("chance")){
			    	if(reslutChance.equals(""))
			    		reslutChance = String.valueOf(countChance++)+ "." + outputString;
			    	else
			    		reslutChance = reslutChance + "\n" + String.valueOf(countChance++)+ "." + outputString;
			    		
			    }else if(swotProperty.getPropertyType().equals("disadvantage")){
			    	if(reslutDisadvantage.equals(""))
			    		reslutDisadvantage = String.valueOf(countDisadvantage++)+ "." + outputString;
			    	else
			    		reslutDisadvantage = reslutDisadvantage + "\n" + String.valueOf(countDisadvantage++)+ "." + outputString;
			    	
			    }else if(swotProperty.getPropertyType().equals("threat")){
			    	if(reslutThreat.equals(""))
			    		reslutThreat = String.valueOf(countThreat++)+ "." + outputString;
			    	else
			    		reslutThreat = reslutThreat + "\n" + String.valueOf(countThreat++)+ "." + outputString;
			    }
			}
		}
		
		//求4个属性行数最大值
		if(countAdvantage > countChance){
			countMax1 = countAdvantage;
		}else{
			countMax1 = countChance;
		}
		if(countDisadvantage > countThreat){
			countMax2 = countAdvantage;
		}else{
			countMax2 = countChance;
		}
		if(countMax1 > countMax2){
			countMax = countMax1;
		}else{
			countMax = countMax2;
		}
		
		nodeHeight *= countMax;
		
		//节点的位置是中心位置
		nodeAdvantage = graph.addNode(reslutAdvantage, nodeWidth/2, nodeHeight/2, nodeWidth, nodeHeight);
		nodeChance = graph.addNode(reslutChance, graph.getWidth() - nodeWidth/2, nodeHeight/2, nodeWidth, nodeHeight);
		nodeDisadvantage = graph.addNode(reslutDisadvantage, nodeWidth/2, nodeHeight * 5/2, nodeWidth, nodeHeight);
		nodeThreat = graph.addNode(reslutThreat, graph.getWidth() - nodeWidth/2, nodeHeight * 5/2, nodeWidth, nodeHeight);
		nodeActorName = graph.addNode(swotActor.getActorName(), graph.getWidth()/2, nodeHeight * 3/2);
	}
	
	public Object getPropertyType(int propertyTypeFlag){
		if(propertyTypeFlag == 1){
			return nodeAdvantage;
		}else if(propertyTypeFlag == 2){
			return nodeChance;
		}else if(propertyTypeFlag == 3){
			return nodeDisadvantage;
		}else {
			return nodeThreat;
		}
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
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == popmenuItemAddProperty) {		//右键菜单添加属性
			
			addProperty();
			
		}
		
//		else if(e.getSource() == popmenuItemDeleteProperty) {
//			
//			deleteProperty();
//			
//		}
//		
//		else if(e.getSource() == popmenuItemModifyProperty) {
//			
//			if(getSelectedTable().getValueAt(0, 0) != "") 
//				modifyProperty();
//			else
//				JOptionPane.showMessageDialog(null, "当前表格无内容，无法做修改操作", "",JOptionPane.INFORMATION_MESSAGE);
//			
//		}
		
//		else if(e.getSource() == buttonConfirmInModifyProperty) {
//			
//			SwotActorProperty swotActorProperty = new SwotActorProperty();
//			swotActorProperty.setPropertyID((int)(getSelectedTable().getModel().getValueAt(selectedTableRow, 2)));		//获得remove掉的列的data必须使用TableModel
//			swotActorProperty.setPropertyContent(textAreaPropertyContentInModifyProperty.getText());
//	//		swotActorProperty.setPropertyMark1
//	//		swotActorProperty.setPropertyMark2
//	
//			swotPropertyDAO.updateProperty(swotActorProperty);
//				
//			getSelectedTable().setValueAt(swotActorProperty.getPropertyContent(), selectedTableRow, 1);
//			
//		}
//		
//		else if(e.getSource() == buttonCancelInModifyProperty) {
//			
//			frameModifyProperty.dispose();
//			
//		}
		
		else if(e.getSource() == buttonConfirmInAddProperty) {
			
//			SwotActorProperty swotActorProperty = new SwotActorProperty();
//			swotActorProperty.setPropertyType(propertyType);
//			swotActorProperty.setPropertyContent(textareaContentInAddProperty.getText());
//
//			swotPropertyDAO.addProperty(swotActor, swotActorProperty);
//			
//			dtm = (DefaultTableModel)getSelectedTable().getModel();
//			if(dtm.getValueAt(0, 0) == "") {
//				dtm.setValueAt(1, 0, 0);
//				dtm.setValueAt(swotActorProperty.getPropertyContent(), 0, 1);
//				dtm.setValueAt(swotActorProperty.getPropertyID(), 0, 2);
//			} else{	
//				dtm.addRow(new Object[]{dtm.getRowCount()+1, swotActorProperty.getPropertyContent(), swotActorProperty.getPropertyID()});		
//			}
			
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
