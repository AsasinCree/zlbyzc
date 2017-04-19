package zlbyzc.sub3.zlpj.anp;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

import org.liukan.mgraph.mgraphxEx;
import zlbyzc.sub3.sub3inFrame;


public class Anpconstruct extends sub3inFrame{
	private JPanel contentPane;
	static mgraphxEx c;
	static void computeLayout(int gpanel_width, int gpanel_height, int factorsNum, int plansNum, 
			                  int[] factor_xs,int[] factor_ys,int[] plan_xs,int[] plan_ys){
		int hGap = 80; //每层节点横向间隔
		
		int firstX = (gpanel_width/2) - (factorsNum-1)/2*hGap;
		if ((factorsNum-1)%2 == 0)  
			firstX += hGap/2;
		int radius = (int)(Math.min(gpanel_width,gpanel_height) * 0.2);
		for (int i = 0; i < factorsNum; i++) {	
				factor_xs[i]= (int)(gpanel_width/2 -radius* Math.cos(Math.PI/2)+ radius * Math.cos(Math.PI/2+i * 2 * Math.PI / factorsNum));
				factor_ys[i] = (int)(gpanel_height/2 - radius * Math.sin(Math.PI/2+i * 2 * Math.PI / factorsNum));	
		}
	
		firstX = (gpanel_width/2) - plansNum/2*hGap;
		if (plansNum%2 == 0)  
			firstX += hGap/2;		
		for (int i = 0; i < plansNum; i++) {
			plan_xs[i] = firstX + i*hGap;
			plan_ys[i] = (int)(gpanel_height*3/4.0);
		}			
	}
	
	public Anpconstruct(){
		//JFrame  frame = new JFrame();
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super("ANP图示",true,true,true,true);
		setLayout(new BorderLayout());
		
		c=new mgraphxEx(false,22,22,true);
		c.setSize(500,800);
		
		add(c.gpanel,BorderLayout.CENTER);
		c.gpanel.setPreferredSize(new Dimension(500,600));
		pack();

		
        //setLocation(0,screenHeight/4);
        //setVisible(true);
        //frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	  
    public int getMax(int[] arr) {  
	        int max = arr[0];  
	          
	        for(int i = 0; i < arr.length; i++) {  
	            if(arr[i] > max)  
	                max = arr[i];  
	        }  
	          
	        return max;  
	}  
	      
    public int getMin(int[] arr) {  
	        int min = arr[0];  
	          
	        for(int i = 0; i < arr.length; i++) {  
	            if(arr[i] < min)  
	                min = arr[i];  
	        }  
	          
	        return min;  
    }   
	
	public void drawG(String aim, String[] factors, String[] plans){

		int gpanel_width = (c.gpanel.getWidth());   
		int gpanel_height = (c.gpanel.getHeight());
		System.out.println("gpanel_width:"+gpanel_width+
				"gpanel_height:"+gpanel_height);
		int factorsNum = factors.length;
		int plansNum = plans.length;

		int factor_xs[] = new int[factorsNum];
		int factor_ys[] = new int[factorsNum];
		int plan_xs[] = new int[plansNum];
		int plan_ys[] = new int[plansNum];
		int aim_x = gpanel_width/2;
		int aim_y = (int)(gpanel_height/4.0);
		
		computeLayout(gpanel_width, gpanel_height, factorsNum, plansNum, factor_xs, factor_ys, plan_xs, plan_ys);
		
		Object aimNode = c.gpanel.addNode(aim,aim_x,aim_y);
		
//		int plan_xsmin = getMin(plan_xs);
//		int plan_xsmax = getMax(plan_xs);
//		int planxc = (int)((plan_xsmin+plan_xsmax)/2);
//		int plan_xsd = (int)(gpanel_width-plan_xsmin/2);
//
//	    
//		Object plan = c.gpanel.addNode("",planxc,plan_ys[0],plan_xsd,40);
		Object[] factorNodes = new Object[factorsNum];
		for (int i = 0; i < factorsNum; i++) {
			factorNodes[i] = c.gpanel.addNode(factors[i],factor_xs[i],factor_ys[i]);
		}
		
		
		Object[] planNodes = new Object[plansNum];
		for (int i = 0; i < plansNum; i++) {
			planNodes[i] = c.gpanel.addNode(plans[i],plan_xs[i],plan_ys[i]);
		}
		
		c.gpanel.addEdge("",factorNodes[0],aimNode);
		for (int i = 0; i < factorsNum; i++) {
			if(i!=factorsNum-1){
			c.gpanel.addEdge("",factorNodes[i],factorNodes[i+1]);
			c.gpanel.addEdge("",factorNodes[i+1],factorNodes[i]);
			}
			else{
				c.gpanel.addEdge("",factorNodes[i],factorNodes[0]);
				c.gpanel.addEdge("",factorNodes[0],factorNodes[i]);
			}
		}
		
//		frame.getContentPane().add(c.gpanel,BorderLayout.CENTER);
//		frame.pack();
		//frame.setSize(w,h);
	}


}

