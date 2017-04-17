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

public class SwotActorGrapghPanel extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	private SwotPropertyDAOInterface swotPropertyDAO;
	private SwotActor swotActor;

	private int flag = 0;
	private static mgraphx graph;
	private int nodeWidth;
	private int nodeHeight;
	
	private int zhLength;
	private int egLength;
	private int symbolLength;
	
	private int panelWidth;
	private int panelHeight;
	
	//显示标签节点
	private Object nodeAdvantage;
	private Object nodeChance;
	private Object nodeDisadvantage;
	private Object nodeThreat;
	private Object nodeActorName;
	
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
	
	public static void main(String[] args)
	{
		JFrame  frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		SwotActor swotActor = new SwotActor();
		SwotActorDAOInterface swotTaskDAO = new SwotActorDAO();
		swotActor = swotTaskDAO.getActorByID(5);
		SwotActorGrapghPanel p = new SwotActorGrapghPanel(swotActor);
		

		
		frame.getContentPane().add(p,BorderLayout.CENTER);
		
		
		frame.pack();
		frame.setSize(500, 200);
        frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public SwotActorGrapghPanel(SwotActor swotActor) {	//参与者编号作为参数
		
		swotPropertyDAO = new SwotPropertyDAO();
		this.swotActor = swotActor;
		
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
		

			
	    popupMenu.add(popmenuItemAddProperty);  
        popupMenu.add(popmenuItemDeleteProperty);  
        popupMenu.add(popmenuItemModifyProperty);  
		
	}
	
	public void refreshPanel(){
		System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
		if(flag == 1){
			if(graph != null) {
				removeAll();
				graph = new mgraphx(false,15,15,true);		//默认，边字体大小，节点字体大小，默认
			}
				
			graph.setBounds(1, 1, panelWidth - 15, panelHeight - 15);
			
			nodeWidth = graph.getWidth()/3;		
			nodeHeight = graph.getHeight()/3;
			
			zhLength = 12;
			egLength = 7;
			symbolLength = 3;
			
	        //显示标签节点
			importData();       
	        nodeActorName = graph.addNode("测试", graph.getWidth()/2, graph.getHeight()/2);
	           
	        //添加边
	        graph.addEdge("优势", nodeActorName, nodeAdvantage);
	        graph.addEdge("机会", nodeActorName, nodeChance);
	        graph.addEdge("劣势", nodeActorName, nodeDisadvantage);
	        graph.addEdge("威胁", nodeActorName, nodeThreat);
	
			add(graph);
			validate();
			repaint();
		}else
			flag = 1;
	}
	
	public void importData(){
		SwotActorDAOInterface swotActorDAO = new SwotActorDAO();
		List<SwotActorProperty> swotPropertyList = swotActorDAO.getAllActorPropertys(swotActor);
		
		String reslutAdvantage = "";
		String reslutChance = "";
		String reslutDisadvantage = "";
		String reslutThreat = "";
		char noAdvantage = '1';
		char noChance = '1';
		char noDisadvantage = '1';
		char noThreat = '1';
		if(swotPropertyList != null) {
			for(SwotActorProperty swotProperty:swotPropertyList) {
			//	System.out.println("Content:"+swotProperty.getPropertyContent());
				String aString = "2222222aa色非色东扥东方算点点点点点点22222aaaaaaa222222222";
			    
				//分段属性内容
			    char[] sourceChar = swotProperty.getPropertyContent().toCharArray();  		//源字符串
			    char[] resultChar = new char[1000];  		//转换后的字符串数组
			    String outputString = "";		//转换后的字符串
			    
			    if(swotProperty.getPropertyType().equals("advantage")){
			    	resultChar[0] = noAdvantage++;
			    	resultChar[1] = '.';
			    }else if(swotProperty.getPropertyType().equals("chance")){
			    	resultChar[0] = noChance++;
			    	resultChar[1] = '.';
			    }else if(swotProperty.getPropertyType().equals("disadvantage")){
			    	resultChar[0] = noDisadvantage++;
			    	resultChar[1] = '.';
			    }else if(swotProperty.getPropertyType().equals("threat")){
			    	resultChar[0] = noThreat++;
			    	resultChar[1] = '.';
			    }

			    //【大写英文字母还未解决】
			    int resultSubScript = 2, characterLength = 0;  
			    int displayWidth = 0;		//算上标号
			    for (int sourceSubScript = 0; sourceSubScript < sourceChar.length; resultSubScript++, sourceSubScript++) {  
			   // 	System.out.println("textLength:"+nodeWidth+"    "+"displayWidth:"+displayWidth);
			        // 如果源字符串中有换行符，此处要将工作过程中计算的长度清零  
			    	characterLength++;
			        if (sourceChar[sourceSubScript] == '\n') {  
			            displayWidth = 0;  
			        }  
			        try {  
			            resultChar[resultSubScript] = sourceChar[sourceSubScript];  
			            if (new Character(sourceChar[sourceSubScript]).toString().getBytes("GBK").length == 2 /*&& sourceChar[sourceSubScript] != '”' && sourceChar[sourceSubScript] != '“'*/) {  
			            	//对汉字字符进行处理
			            
			            	displayWidth += 12;     
			            } else if (new Character(sourceChar[sourceSubScript]).toString().getBytes("GBK").length == 1) {  
			                //对空格、英文字符和数字进行处理。  
			    
			                if (sourceChar[sourceSubScript] == ' ' || sourceChar[sourceSubScript] == ',') {  
			                    displayWidth += 3;   
			                }else {  
			                    displayWidth += 7;  
			                }  
			            }  
			        } catch (UnsupportedEncodingException e) {  
			            // TODO Auto-generated catch block  
			            e.printStackTrace();  
			        }  
			        
			        //长度超过给定的长度，插入\n  
			        if (displayWidth >= (float)nodeWidth) {  
			            if (sourceChar[sourceSubScript + 1] != '\n') {       System.out.println("textLength:"+nodeWidth+"    "+"displayWidth:"+displayWidth);     
			                resultChar[++resultSubScript] = '\n';
			                characterLength++; 
			            }  
			            displayWidth = 0;  
			        }      
			    } 
			    
//			    FontMetrics metrics =  graph.getFontMetrics(graph.getFont());
//			    float textW = metrics.stringWidth("a");//字符串的宽
			    
			    
			    //最后一行加空格，解除水平居中【空格宽度有问题，会多出来】
//			    while(displayWidth <= (float)nodeWidth){	
//			    //	System.out.println("textLength:"+nodeWidth+"    "+"displayWidth:"+displayWidth);
//			    	displayWidth += 3; 
//			    	characterLength++;
//			    	resultChar[++resultSubScript] = ' ';
//			    }
			    
			    outputString = new String(resultChar).substring(0, characterLength)/* .trim() */;
			    System.out.println(outputString);
			    if(swotProperty.getPropertyType().equals("advantage")){
			    	if(reslutAdvantage.equals(""))
			    		reslutAdvantage = outputString;
			    	else {
			    		reslutAdvantage = reslutAdvantage + "\n" + outputString;
					}
			    	
			    }else if(swotProperty.getPropertyType().equals("chance")){
			    	if(reslutChance.equals(""))
			    		reslutChance = outputString;
			    	else
			    		reslutChance = reslutChance + "\n" + outputString;
			    	
			    }else if(swotProperty.getPropertyType().equals("disadvantage")){
			    	if(reslutDisadvantage.equals(""))
			    		reslutDisadvantage = outputString;
			    	else
			    		reslutDisadvantage = reslutDisadvantage + "\n" + outputString;
			    	
			    }else if(swotProperty.getPropertyType().equals("threat")){
			    	if(reslutThreat.equals(""))
			    		reslutThreat = outputString;
			    	else
			    		reslutThreat = reslutThreat + "\n" + outputString;
			    }
			}
		}
		nodeAdvantage = graph.addNode(reslutAdvantage, 1, 1);
		nodeChance = graph.addNode(reslutChance, graph.getWidth() - nodeWidth, 1);
		nodeDisadvantage = graph.addNode(reslutDisadvantage, 2, graph.getHeight() - nodeHeight);
		nodeThreat = graph.addNode(reslutThreat, graph.getWidth() - nodeWidth, graph.getHeight() - nodeHeight);
		
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
