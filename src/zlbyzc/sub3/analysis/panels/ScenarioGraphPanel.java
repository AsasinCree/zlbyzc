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
import java.util.ArrayList;
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
import zlbyzc.sub3.analysis.entities.ScenarioLogic;
import zlbyzc.sub3.analysis.entities.ScenarioProperty;
import zlbyzc.sub3.analysis.entities.ScenarioResult;
import zlbyzc.sub3.analysis.entities.ScenarioTask;
import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotActorProperty;
import zlbyzc.sub3.analysis.services.ScenarioLogicDAO;
import zlbyzc.sub3.analysis.services.ScenarioPropertyDAO;
import zlbyzc.sub3.analysis.services.ScenarioResultDAO;
import zlbyzc.sub3.analysis.services.SwotActorDAO;
import zlbyzc.sub3.analysis.services.SwotPropertyDAO;
import zlbyzc.sub3.analysis.services.SwotTaskDAO;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioLogicDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioPropertyDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.ScenarioResultDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotActorDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotPropertyDAOInterface;
import zlbyzc.sub3.analysis.services.interfaces.SwotTaskDAOInterface;

public class ScenarioGraphPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	private ScenarioTask scenarioTask;

	private int flag = 0;		//panel初始化会刷新两次，第二次才是正确的大小
	private static mgraphx graph;
	private int nodeWidth;
	private int nodeHeight;
	
	//各属性个数的序号
	private int countFocus;
	private int countKeyFactor;
	private int countDrivingpower;
	private int countDevelopment;
	private int countUncertainties;
	private int countResult;
	private int countMax;	//最大的行数
	
	//示例
	private List<ScenarioProperty> scenarioPropertyList;
	private List<ScenarioLogic> scenarioLogicList;
	private List<ScenarioResult> scenarioResultList;
	private int flagExample;		//判断是否是示例
	
	private int panelWidth;
	private int panelHeight;
	
	//显示标签节点
	private Object nodeFocus;
	private Object nodeKeyFactor;
	private Object nodeDrivingpower;
	private Object nodeUncertainties;
	private Object nodeDevelopment;
	private Object nodeResult;
	
	private int propertyTypeFlag;
	
	public static void main(String[] args)
	{
		JFrame  frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		SwotActor swotActor = new SwotActor();
		SwotActorDAOInterface swotTaskDAO = new SwotActorDAO();
		swotActor = swotTaskDAO.getActorByID(5);
	//  ScenarioGraphPanel p = new ScenarioGraphPanel(swotActor);
				
	//	frame.getContentPane().add(p,BorderLayout.CENTER);
				
		frame.pack();
		frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public ScenarioGraphPanel(ScenarioTask scenarioTask) {	
		this.scenarioTask = scenarioTask;
		flagExample = 0;
		initializeComponent();
		
		layoutComponent();
	}	
	
	public ScenarioGraphPanel(ScenarioTask scenarioTask, List<ScenarioProperty> scenarioPropertyList, 
			List<ScenarioLogic> scenarioLogicList, List<ScenarioResult> scenarioResultList) {	
		this.scenarioTask = scenarioTask;
		
		if(scenarioPropertyList.size() == 0 && scenarioLogicList.size() == 0 &&
				scenarioResultList.size() == 0)	{	//不是示例
			flagExample = 0;
		}else {
			this.scenarioPropertyList = scenarioPropertyList;
			this.scenarioLogicList = scenarioLogicList;
			this.scenarioResultList = scenarioResultList;
			flagExample = 1;
		}
		
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
	}

	public void layoutComponent() {
		//在SwotEditPanel中装入JInternalFrame并不会触发该panel的componentResized，故要人为的做第一次刷新才会显示出
		refreshPanel();		
	}
	
	public void refreshPanel(){
		if(flag == 1){
			if(graph != null) {
				removeAll();
				graph = new mgraphx(false,15,15,true);		//默认，边字体大小，节点字体大小，默认
			}
				
			graph.setBounds(1, 1, panelWidth, panelHeight);
			graph.setBorder(new EmptyBorder(0, 0, 0, 0));
			
			nodeWidth = graph.getWidth();	
			nodeHeight = 21;		//15号字体行高为22
						
	        //显示标签节点
			importData();       
	           
	        //添加边
	        graph.addEdge("", nodeFocus, nodeKeyFactor);
	        graph.addEdge("", nodeKeyFactor, nodeDrivingpower);
	        graph.addEdge("", nodeDrivingpower, nodeUncertainties);
	        graph.addEdge("", nodeUncertainties, nodeDevelopment);
	        graph.addEdge("", nodeDevelopment, nodeResult);
	
			add(graph);
			revalidate();
			repaint();
		}else
			flag = 1;
	}
	
	public void importData(){
		String reslutFocus = "";
		String reslutKeyFactor = "";
		String reslutDrivingpower = "";
		String reslutUncertainties = "";
		String reslutDevelopment = "";
		String reslutResult = "";
		
		//重置序号
		countFocus = 1;
		countKeyFactor = 1;
		countDrivingpower = 1;
		countUncertainties = 1;
		countDevelopment = 1;
		countResult = 1;
		
		int heightFocus;
		int heightKeyFactor;
		int heightDrivingpower;
		int heightUncertainties;
		int heightDevelopment;
		int heightResult;
		int space = 20;		//箭头间隔
		
		ScenarioPropertyDAOInterface scenarioPropertyDAO = new ScenarioPropertyDAO();
		
		if(flagExample == 0)		//不是示例
			scenarioPropertyList = scenarioPropertyDAO.getAllPropertys(scenarioTask);	
		if(scenarioPropertyList != null) {
			for(ScenarioProperty scenarioProperty:scenarioPropertyList) {
				String outputString = "";		
			    outputString = scenarioProperty.getPropertyContent();
			    
			    if(scenarioProperty.getPropertyType().equals("focus")){
				    String[] strings = outputString.split("\n");	//检测换行符的个数
				    countFocus += strings.length;	countFocus--;	//多余一行
			    	if(reslutFocus.equals(""))
			    		reslutFocus = String.valueOf(countFocus++)+ "." + outputString;
			    	else {
			    		reslutFocus = reslutFocus + "\n" + String.valueOf(countFocus++)+ "." + outputString;
					}	    	
			    }else if(scenarioProperty.getPropertyType().equals("keyfactor")){
				    String[] strings = outputString.split("\n");	//检测换行符的个数
				    countKeyFactor += strings.length;	countKeyFactor--;	//多余一行
			    	if(reslutKeyFactor.equals(""))
			    		reslutKeyFactor = String.valueOf(countKeyFactor++)+ "." + outputString;
			    	else
			    		reslutKeyFactor = reslutKeyFactor + "\n" + String.valueOf(countKeyFactor++)+ "." + outputString;		
			    }else if(scenarioProperty.getPropertyType().equals("drivingpower")){
				    String[] strings = outputString.split("\n");	//检测换行符的个数
				    countDrivingpower += strings.length;	countDrivingpower--;	//多余一行
			    	if(reslutDrivingpower.equals(""))
			    		reslutDrivingpower = String.valueOf(countDrivingpower++)+ "." + outputString;
			    	else
			    		reslutDrivingpower = reslutDrivingpower + "\n" + String.valueOf(countDrivingpower++)+ "." + outputString;		    	
			    }else if(scenarioProperty.getPropertyType().equals("uncertainties")){
				    String[] strings = outputString.split("\n");	//检测换行符的个数
				    countUncertainties += strings.length;	countUncertainties--;	//多余一行
			    	if(reslutUncertainties.equals(""))
			    		reslutUncertainties = String.valueOf(countUncertainties++)+ "." + outputString;
			    	else
			    		reslutUncertainties = reslutUncertainties + "\n" + String.valueOf(countUncertainties++)+ "." + outputString;
			    }
			}
		}

		ScenarioLogicDAOInterface scenarioLogicDAO = new ScenarioLogicDAO();
		if(flagExample == 0)
			scenarioLogicList = scenarioLogicDAO.getAllLogics(scenarioTask);
		if(scenarioLogicList != null) {
			for(ScenarioLogic scenarioLogic:scenarioLogicList) {
				String outputString = "";		
			    outputString = scenarioLogic.getLogicContent();
			    String[] strings = outputString.split("\n");	//检测换行符的个数
			    countDevelopment += strings.length;	countDevelopment--;	//多余一行
			    if(reslutDevelopment.equals(""))
			    	reslutDevelopment = String.valueOf(countDevelopment++)+ "." + outputString;
		    	else {
		    		reslutDevelopment = reslutDevelopment + "\n" + String.valueOf(countDevelopment++)+ "." + outputString;
				}
			}
		}
		
		ScenarioResultDAOInterface scenarioResultDAO = new ScenarioResultDAO();
		if(flagExample == 0)
			scenarioResultList = scenarioResultDAO.getAllResults(scenarioTask);
		if(scenarioResultList != null) {
			for(ScenarioResult scenarioResult:scenarioResultList) {
				String outputString = "";		
			    outputString = scenarioResult.getResultContent();
			    String[] strings = outputString.split("\n");	//检测换行符的个数
			    countResult += strings.length;	countResult--;	//多余一行
			    if(reslutResult.equals(""))
			    	reslutResult = String.valueOf(countResult++)+ "." + outputString;
		    	else {
		    		reslutResult = reslutResult + "\n" + String.valueOf(countResult++)+ "." + outputString;
				}
			}
		}
		
		heightFocus = nodeHeight * countFocus;
		heightKeyFactor = nodeHeight * countKeyFactor;
		heightDrivingpower = nodeHeight * countDrivingpower;
		heightUncertainties = nodeHeight * countUncertainties;
		heightDevelopment = nodeHeight * countDevelopment;
		heightResult = nodeHeight * countResult;
	//	System.out.println(nodeWidth);
		//节点的位置是中心位置
		nodeFocus = graph.addNode(reslutFocus, nodeWidth/2, heightFocus/2, nodeWidth, heightFocus);
		nodeKeyFactor = graph.addNode(reslutKeyFactor, nodeWidth/2, heightFocus + space + heightKeyFactor/2, nodeWidth, heightKeyFactor);
		nodeDrivingpower = graph.addNode(reslutDrivingpower, nodeWidth/2, heightFocus + 2*space + heightKeyFactor + heightDrivingpower/2, nodeWidth, heightDrivingpower);
		nodeUncertainties = graph.addNode(reslutUncertainties, nodeWidth/2, heightFocus + 3*space + heightKeyFactor + heightDrivingpower + heightUncertainties/2, nodeWidth, heightUncertainties);
		nodeDevelopment = graph.addNode(reslutDevelopment, nodeWidth/2, heightFocus + 4*space + heightKeyFactor + heightDrivingpower + heightUncertainties + heightDevelopment/2, nodeWidth, heightDevelopment);
		nodeResult = graph.addNode(reslutResult, nodeWidth/2, heightFocus + 5*space + heightKeyFactor + heightDrivingpower + heightUncertainties + heightDevelopment + heightResult/2, nodeWidth, heightResult);	
	}
		
	public void addProperty() {}
		
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() ==null/* popmenuItemAddProperty*/) {		//右键菜单添加属性
			
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
		
		else if(e.getSource() == null/*buttonConfirmInAddProperty*/) {
			
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
			
		//	buttonConfirmInAddProperty.setText("继续添加");
		//	textareaContentInAddProperty.setText("");
//			textareaRemark1InAddProperty.setText("");
//			textareaRemark2InAddProperty.setText("");
			
		}
		
		else if(e.getSource() == null/*buttonCancelInAddProperty*/) {
			
		//	frameAddProperty.dispose();
			
		}
		
	}
	
}
