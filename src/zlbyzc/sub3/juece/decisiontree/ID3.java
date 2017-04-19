package zlbyzc.sub3.juece.decisiontree;

import java.awt.BorderLayout;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.liukan.mgraph.mgraphx;
import org.liukan.mgraph.mgraphxEx;
import org.liukan.mgraph.util.dbIO;

import zlbyzc.sub3.juece.ui.MainFrame;

public class ID3 {
	MainFrame mf = null;
	// 根节点
	public TreeNode root; // 改成可视

	// 可见性数组
	private boolean[] visiable;

	// 未找到节点
	private static final int NO_FOUND = -1;

	// 训练集
	private Object[] trainingArray;
	
	 mgraphx c;
	// JFrame  frame;
	 Map<String, String> pointMap = new HashMap<>();

	 Map<String, Object> valueMap = new HashMap<>();

	public mgraphx getC() {
		return c;
	}

	public void setC(mgraphx c) {
		this.c = c;
	}

	// 节点索引
	private int nodeIndex;

	// String[] printData = new String[]{"攻击敌军制胜率","减少我方损失","国际影响","全局制胜率"};
	public String[] printData;

	// 构造方法，引入主窗口
	public ID3(MainFrame mf) {
		this.mf = mf;
		addPrintData();
		initPrintTree();
	}

	private List<String> pathList = new ArrayList<String>();

	// /////自己修改的部分
	public void addPrintData() {
		// 把准则全部装进去
		int ideaCount = mf.panelA.lie - 2;
		printData = new String[ideaCount];
		for (int i = 0; i < ideaCount; i++) {
			this.printData[i] = this.mf.panelA.defaultModel.getValueAt(0, i + 1).toString();
		}
	}

	/*
	 * //主函数 public static void main(String[] args) { //初始化训练集数组 Object[] array
	 * = new Object[]{ /*new String[]{"Sunny","Hot","High","Weak","No"}, new
	 * String[]{"Sunny","Hot","High","Strong","No"}, new
	 * String[]{"Overcast","Hot","High","Weak","Yes"}, new
	 * String[]{"Rain","Mild","High","Weak","Yes"}, new
	 * String[]{"Rain","Cool","Normal","Weak","Yes"}, new
	 * String[]{"Rain","Cool","Normal","Strong","No"}, new
	 * String[]{"Overcast","Cool","Normal","Strong","Yes"}, new
	 * String[]{"Sunny","Mild","High","Weak","No"}, new
	 * String[]{"Sunny","Cool","Normal","Weak","Yes"}, new
	 * String[]{"Rain","Mild","Normal","Weak","Yes"}, new
	 * String[]{"Sunny","Mild","Normal","Strong","Yes"}, new
	 * String[]{"Overcast","Mild","High","Strong","Yes"}, new
	 * String[]{"Overcast","Hot","Normal","Weak","Yes"}, new
	 * String[]{"Rain","Mild","High","Strong","No"}
	 */
	/*
	 * new String[]{"不赞成","不赞成","赞成","赞成","不赞成开战"}, new
	 * String[]{"不赞成","赞成","不赞成","不赞成","不赞成开战"}, new
	 * String[]{"赞成","不赞成","赞成","赞成","赞成开战"}, new
	 * String[]{"赞成","不赞成","赞成","不赞成","不赞成开战"}, new
	 * String[]{"不赞成","不赞成","赞成","不赞成","不赞成开战"}, new
	 * String[]{"赞成","不赞成","不赞成","赞成","不赞成开战"}, new
	 * String[]{"不赞成","赞成","赞成","不赞成","不赞成开战"}, new
	 * String[]{"赞成","不赞成","赞成","赞成","赞成开战"},// new
	 * String[]{"不赞成","不赞成","不赞成","赞成","不赞成开战"},
	 * 
	 * };
	 * 
	 * //建决策树 ID3 ID3Tree = new ID3();
	 * 
	 * //这里array是训练集数组，4是训练集属性的个数 ID3Tree.create(array, 4); System.out.println(
	 * "==========END PRINT TREE==========");
	 * 
	 * //测试数据 //String[] printData = new
	 * String[]{"Overcast","Cool","Normal","Weak"}; String[] printData = new
	 * String[]{"攻击敌军制胜率","减少我方损失","国际影响","全局制胜率"};
	 * 
	 * System.out.println("==========DECISION RESULT==========");
	 * 
	 * //预测 ID3Tree.compare(printData, ID3Tree.root); }
	 */

	// 根据传入数据进行预测
	public void compare(String[] printData, TreeNode node) {
		int index = getNodeIndex(node.nodeName);
		if (index == NO_FOUND) {
			System.out.println(node.nodeName);
			System.out.println((node.percent * 100) + "%");
		}
		TreeNode[] childs = node.childNodes;
		for (int i = 0; i < childs.length; i++) {
			if (childs[i] != null) {
				if (childs[i].parentAttribute.equals(printData[index])) {
					compare(printData, childs[i]);
				}
			}
		}
	}

	// 创建
	public void create(Object[] array, int index) {
		this.trainingArray = array;
		init(array, index);
		createDTree(array);
		printDTree(root);
		producePathList(root);
	}

	// 初始化
	public void init(Object[] dataArray, int index) {
		this.nodeIndex = index;

		System.out.println(((String[]) dataArray[0]).length);
		// 数据初始化
		this.visiable = new boolean[((String[]) dataArray[0]).length];
		for (int i = 0; i < this.visiable.length; i++) {
			if (i == index) {
				this.visiable[i] = true;
			} else {
				this.visiable[i] = false;
			}
		}
	}

	// 创建决策树
	public void createDTree(Object[] array) {
		Object[] maxgain = getMaxGain(array);
		if (this.root == null) {
			this.root = new TreeNode();
			root.parent = null;
			root.parentAttribute = null;
			root.attributes = getAttributes(((Integer) maxgain[1]).intValue());
			root.nodeName = getNodeName(((Integer) maxgain[1]).intValue());
			root.childNodes = new TreeNode[root.attributes.length];
			insertTree(array, root);
		}
	}

	// 插入决策树
	public void insertTree(Object[] array, TreeNode parentNode) {
		String[] attributes = parentNode.attributes;
		for (int i = 0; i < attributes.length; i++) {
			Object[] pickArray = pickUpAndCreateArray(array, attributes[i], getNodeIndex(parentNode.nodeName));
			Object[] info = getMaxGain(pickArray);
			double gain = ((Double) info[0]).doubleValue();
			if (gain != 0) {
				int index = ((Integer) info[1]).intValue();
				TreeNode currentNode = new TreeNode();
				currentNode.parent = parentNode;
				currentNode.parentAttribute = attributes[i];
				currentNode.attributes = getAttributes(index);
				currentNode.nodeName = getNodeName(index);
				currentNode.childNodes = new TreeNode[currentNode.attributes.length];
				parentNode.childNodes[i] = currentNode;
				insertTree(pickArray, currentNode);
			} else {
				TreeNode leafNode = new TreeNode();
				leafNode.parent = parentNode;
				leafNode.parentAttribute = attributes[i];
				leafNode.attributes = new String[0];
				leafNode.nodeName = getLeafNodeName(pickArray);
				leafNode.childNodes = new TreeNode[0];
				parentNode.childNodes[i] = leafNode;

				double percent = 0;
				String[] arrs = getAttributes(this.nodeIndex);
				for (int j = 0; j < arrs.length; j++) {
					System.out.println(arrs[j]);
					if (leafNode.nodeName.equals(arrs[j])) {
						Object[] subo = pickUpAndCreateArray(pickArray, arrs[j], this.nodeIndex);
						Object[] o = pickUpAndCreateArray(this.trainingArray, arrs[i], this.nodeIndex);
						double subCount = subo.length;
						percent = subCount / o.length;
					}
				}
				leafNode.percent = percent;
			}
		}
	}

	public static boolean flag = true;
	
	private void  initPrintTree(){
	
		c = new mgraphx(false, 20, 20, true);
		c.setSize(580, 800);
		 
		
	}

	// 输出决策树
	public void printDTree(TreeNode node) {
		
		if (node.parent == null) {
			System.out.println("根节点 :" +node.nodeName);
			this.mf.output.append("根节点 :" + node.nodeName + "\r\n");
			System.out.println(node.nodeName);
			Object n1=c.addNode(node.nodeName, 600, 40);
			
			String value =380+ "_40";
			pointMap.put(node.nodeName, value);
			
			valueMap.put(node.nodeName, n1);

		}
		else {
			System.out.println("【属性"+node.parentAttribute+"】"+"==>"+node.nodeName);
			this.mf.output.append("【属性" + node.parentAttribute + "】" + "==>" + node.nodeName + "\r\n");

		}
		if (node.childNodes.length > 0) {
			int jiange = 300 / node.childNodes.length;
			String nodeName = node.nodeName;
			System.out.println("this is node children parent nodename"+nodeName);
			
			System.out.println("this is chirlDren nodename "+nodeName);
			TreeNode[] childrens = node.childNodes;
			
			
			for(String key : pointMap.keySet()){
				System.out.println("this is pointMap key "+key);
			}
			String pointValue = pointMap.get(nodeName);
			//System.out.println(x);
			
			int parentWidth = Integer.parseInt(pointValue.trim().split("_")[0]);
			
			
			System.out.println("this is parentwidth"+parentWidth);
			int parentHigh = Integer.parseInt(pointValue.trim().split("_")[1]);
			System.out.println("this is parentheight"+parentHigh);
			
			Object parentPoint = valueMap.get(nodeName);

			for (int i = 0; i < childrens.length; i++) {
				if (childrens[i] != null) {

					Object childrenPoint = c.addNode(childrens[i].nodeName, parentWidth + (i - 1) * jiange,
							parentHigh +100);
					
					pointMap.put(childrens[i].nodeName,""+(parentWidth + (i - 1) * jiange)+"_"+
							(parentHigh +100));
					this.mf.output.append("添加了节点");

					valueMap.put(childrens[i].nodeName, childrenPoint);

					c.addEdge(childrens[i].parentAttribute, parentPoint, childrenPoint);

					System.out.println(node.nodeName + ":分裂属性 " + childrens[i].parentAttribute);

					this.mf.output.append(node.nodeName + ":分裂属性 " + childrens[i].parentAttribute + "\r\n");
					printDTree(childrens[i]);
					
				}
				}
			}
					//printDTree(childrens[i]);}}}

		/*if (node.parent == null) {
			// System.out.println("根节点 :" +node.nodeName);
			this.mf.output.append("根节点 :" + node.nodeName + "\r\n");
			System.out.println(node.nodeName);
			root = c.addNode("shenlei", 280, 40);
			
			String value = 280 + "_40";
			
			pointMap.put(node.nodeName, value);
			
			valueMap.put(node.nodeName, root);

		} else {
			// System.out.println("【属性"+node.parentAttribute+"】"+"==>"+node.nodeName);
			this.mf.output.append("【属性" + node.parentAttribute + "】" + "==>" + node.nodeName + "\r\n");

		}

		if (node.childNodes.length > 0) {
			int jiange = 300 / node.childNodes.length;
			String nodeName = node.nodeName;
			TreeNode[] childrens = node.childNodes;
			String pointValue = pointMap.get(nodeName);
			int parentWidth = Integer.parseInt(pointValue.trim().split("")[0]);
			int parentHigh = Integer.parseInt(pointValue.trim().split("")[1]);

			Object parentPoint = valueMap.get(nodeName);

			for (int i = 0; i < childrens.length; i++) {
				if (childrens[i] != null) {

					Object childrenPoint = c.addNode(childrens[i].nodeName, parentWidth + (i - 1) * jiange,
							parentHigh + 80);
					
					this.mf.output.append("添加了节点");

					valueMap.put(childrens[i].nodeName, childrenPoint);

					c.addEdge(childrens[i].parentAttribute, parentPoint, childrenPoint);

					System.out.println(node.nodeName + ":分裂属性 " + childrens[i].parentAttribute);

					this.mf.output.append(node.nodeName + ":分裂属性 " + childrens[i].parentAttribute + "\r\n");
					printDTree(childrens[i]);

					try {
						
						 dbIO dbio=new dbIO(BR.setting.getDriver(),BR.setting.getConnURL("testshenlei"));	
						 c.saveG2DB("hoh33o",3,dbio);			    	
					    	dbio.close();		    	
					    } catch ( Exception ess ) {
					      System.err.println( ess.getClass().getName() + ": " + ess.getMessage() );
					      //System.exit(0);
					    }
				//panel_button.add(btnSaveButton);
					frame.getContentPane().add(c,BorderLayout.CENTER);
					frame.pack();
					frame.setSize(600, 820);
			        frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
					break;
				}*/
	
		
	}

	// 剪取数组
	public Object[] pickUpAndCreateArray(Object[] array, String attribute, int index) {
		List<String[]> list = new ArrayList<String[]>();
		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			if (strs[index].equals(attribute)) {
				list.add(strs);
			}
		}
		return list.toArray();
	}

	// 取得节点名
	public String getNodeName(int index) {
		// String[] strs = new
		// String[]{"Outlook","Temperature","Humidity","Wind","PlayTennis"};
		// String[] strs = new
		// String[]{"攻击敌军制胜率","减少我方损失","国际影响","全局制胜率","赞成开战"};
		/*
		 * int ideaCount =
		 * Integer.parseInt(mf.panelA.schemeNumTxt.getText());//保存准则数 printData
		 * = new String[ideaCount]; for (int i = 0; i < ideaCount; i++) {
		 * this.printData[i] = this.mf.panelC.ideaMap.get(new String("准则" + i));
		 * }
		 */
		String[] strs = new String[printData.length + 1];
		for (int i = 0; i < printData.length; i++) {
			strs[i] = this.printData[i];
		}
		strs[printData.length] = this.mf.panelA.defaultModel.getValueAt(0, printData.length + 1).toString();
		for (int i = 0; i < strs.length; i++) {
			if (i == index) {
				return strs[i];
			}
		}
		return null;
	}

	// 取得叶子节点名
	public String getLeafNodeName(Object[] array) {
		if (array != null && array.length > 0) {
			String[] strs = (String[]) array[0];
			return strs[nodeIndex];
		}
		return null;
	}

	// 取得节点索引
	public int getNodeIndex(String name) {
		// String[] strs = new
		// String[]{"Outlook","Temperature","Humidity","Wind","PlayTennis"};
		// String[] strs = new
		// String[]{"攻击敌军制胜率","减少我方损失","国际影响","全局制胜率","赞成开战"};
		/*
		 * int ideaCount =
		 * Integer.parseInt(mf.panelA.schemeNumTxt.getText());//保存准则数 printData
		 * = new String[ideaCount]; for (int i = 0; i < ideaCount; i++) {
		 * this.printData[i] = this.mf.panelC.ideaMap.get(new String("准则" + i));
		 * }
		 */
		String[] strs = new String[printData.length + 1];
		for (int i = 0; i < printData.length; i++) {
			strs[i] = this.printData[i];
		}
		strs[printData.length] = this.mf.panelA.defaultModel.getValueAt(0, printData.length + 1).toString();

		for (int i = 0; i < strs.length; i++) {
			if (name.equals(strs[i])) {
				return i;
			}
		}
		return NO_FOUND;
	}

	// 得到最大信息增益
	public Object[] getMaxGain(Object[] array) {
		Object[] result = new Object[2];
		double gain = 0;
		int index = -1;
		for (int i = 0; i < this.visiable.length; i++) {
			if (!this.visiable[i]) {
				double value = gain(array, i);
				if (gain < value) {
					gain = value;
					index = i;
				}
			}
		}
		result[0] = gain;
		result[1] = index;
		if (index != -1) {
			this.visiable[index] = true;
		}
		return result;
	}

	// 取得属性数组
	public String[] getAttributes(int index) {
		TreeSet<String> set = new TreeSet<String>(new SequenceComparator());
		for (int i = 0; i < this.trainingArray.length; i++) {
			String[] strs = (String[]) this.trainingArray[i];
			set.add(strs[index]);
		}
		String[] result = new String[set.size()];
		return set.toArray(result);

	}

	// 计算信息增益
	public double gain(Object[] array, int index) {
		String[] playBalls = getAttributes(this.nodeIndex);
		int[] counts = new int[playBalls.length];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = 0;
		}

		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			for (int j = 0; j < playBalls.length; j++) {
				if (strs[this.nodeIndex].equals(playBalls[j])) {
					counts[j]++;
				}
			}
		}

		double entropyS = 0;
		for (int i = 0; i < counts.length; i++) {
			entropyS = entropyS + DTreeUtil.sigma(counts[i], array.length);
		}

		String[] attributes = getAttributes(index);
		double sv_total = 0;
		for (int i = 0; i < attributes.length; i++) {
			sv_total = sv_total + entropySv(array, index, attributes[i], array.length);
		}
		return entropyS - sv_total;
	}

	public double entropySv(Object[] array, int index, String attribute, int allTotal) {
		String[] playBalls = getAttributes(this.nodeIndex);
		int[] counts = new int[playBalls.length];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = 0;
		}

		for (int i = 0; i < array.length; i++) {
			String[] strs = (String[]) array[i];
			if (strs[index].equals(attribute)) {
				for (int k = 0; k < playBalls.length; k++) {
					if (strs[this.nodeIndex].equals(playBalls[k])) {
						counts[k]++;
					}
				}
			}
		}

		int total = 0;
		double entropySv = 0;
		for (int i = 0; i < counts.length; i++) {
			total = total + counts[i];
		}

		for (int i = 0; i < counts.length; i++) {
			entropySv = entropySv + DTreeUtil.sigma(counts[i], total);
		}
		return DTreeUtil.getPi(total, allTotal) * entropySv;
	}

	private String getLongString(TreeNode node, String hello) {

		if (node.parent != null) {
			// String values = bus.toString();
			// System.out.println("this is values "+values);
			// bus.append("["+node.parentAttribute+"] "+node.nodeName+"
			// "+hello);
			String hshs = "[" + "value:" + node.parentAttribute + "] ==> " + node.nodeName + "" + hello;
			// String reallyValue =bus.toString();
			// System.out.println("this is bus "+bus.toString());
			// String values = bus.toString();
			getLongString(node.parent, hshs);
		} else {
			// String valuess = bus.toString();
			// System.out.println("this is valuess "+valuess);
			// System.out.println("this is node name"+node.nodeName);
			// bus.append(node.nodeName+"------- "+hello);
			String heheString = node.nodeName + hello;
			pathList.add(heheString);

			// System.out.println("this is Result =====> "+heheString);

			return heheString;
		}
		return "";
	}

	private void producePathList(TreeNode node) {
		if (node.childNodes.length == 0) {
			String value = getLongString(node, "");
			System.out.println(value);

		}
		TreeNode[] childrens = node.childNodes;
		for (TreeNode nodesNode : childrens) {
			if (nodesNode != null) {
				producePathList(nodesNode);
			}
		}

	}

	public List<String> getPathList() {
		return pathList;
	}

}