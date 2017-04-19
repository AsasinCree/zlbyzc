package zlbyzc.sub3;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import db.MyPath;

//import org.cef.CefApp;

import zlbyzc.BasicRibbon;
import zlbyzc.gui.ImageTask;
//import zlbyzc.sub3.stats.statPy;
import zlbyzc.sub3.stats.statR;
import zlbyzc.sub3.zlpj.ahp.AhpInputSub3Frame;
import zlbyzc.sub3.zlpj.ahp.AhpQuerySub3Frame;
import zlbyzc.sub3.zlpj.anp.AnpInitFrm;
import zlbyzc.sub3.zlpj.anp.AnpQueryFrm;
import zlbyzc.sub3.zlpj.dea.DeaInitFrm;
import zlbyzc.sub3.zlpj.dea.DeaQueryFrm;
import zlbyzc.sub3.zlpj.fuzzy.FuzzyInitFrm;
import zlbyzc.sub3.zlpj.fuzzy.FuzzyQueryFrm;
import zlbyzc.sub3.duozhunze.CopyOfchaxun;
import zlbyzc.sub3.duozhunze.Frame2;
import zlbyzc.sub3.gametree.GameMatrix;
import zlbyzc.sub3.gametree.GameTree;
import zlbyzc.sub3.gametree.gamematrixquery;
import zlbyzc.sub3.gametree.gametreequery;
import zlbyzc.sub3.riskcontrol.LecQueryPanel;
import zlbyzc.sub3.riskcontrol.riskcontrolLec;
import zlbyzc.sub3.riskcontrol.riskmatrix;
import zlbyzc.sub3.riskcontrol.riskmatrixQueryPanel;
import zlbyzc.sub3.analysis.panels.statScenario;
import zlbyzc.sub3.analysis.panels.statSearchScenario;
import zlbyzc.sub3.analysis.panels.statSearchSwot;
import zlbyzc.sub3.analysis.panels.statSwot;
import zlbyzc.sub3.juece.ui.MainFrame;
import zlbyzc.sub3.juece.ui.PanelA;
import zlbyzc.sub3.juece.ui.PanelA2;
import zlbyzc.sub3.juece.ui.PanelA2search;
import zlbyzc.sub3.juece.ui.PanelAA;
import zlbyzc.sub3.juece.ui.PanelAAsearch;
import zlbyzc.sub3.juece.ui.PanelAsearch;
public class CommandExec {
	private BasicRibbon BR;
	public GameMatrix gm;
	public GameTree gt;

	public gamematrixquery gmq;
	public gametreequery gtq;
	
	//public statPy f1;
	public statR f2;
	public Frame2 f3;
	public statSwot swot;
	public statSearchSwot searchSwot;
	public statScenario scenario;
	public statSearchScenario searchScenario;
	public riskcontrolLec lecFrame; //added by shenhui,2016-04-09
	public riskmatrix riskmatrixFrame;//added by lixuan,2016-04-19
	public riskmatrixQueryPanel matrixQueryPanel;//added by lixuan
	public LecQueryPanel riskLecFindPanel;//added by shenhui,2016-04-09
	public 	MainFrame mf;
	//public sub3inFrame ahpWindow;
	public AhpQuerySub3Frame fAhpQuery;
	public FuzzyInitFrm fFuzzy;
	public DeaInitFrm fDea;
	public AnpInitFrm fAnp;
	
	public AhpInputSub3Frame fAhp;
	public FuzzyQueryFrm fFuzzyrefer;
	public DeaQueryFrm fDearefer;
	public AnpQueryFrm fAnprefer;
	
	
	public CommandExec(BasicRibbon br){
		BR=br;
	}
	public String docpath="file:///"+MyPath.getPath()+File.separator+"doc"+File.separator;
	public HashMap<String,sub3inFrame> framesMap;
	public void dyn_me(){
		//System.out.println(docpath+"vensim.htm");
		BR.getHelpView().navigate(docpath+"vensim.htm");
		BR.getDesktopPane().repaint();
		try {
		      Runtime.getRuntime().exec(MyPath.getPath()+File.separator+
		    			"rlibs"+File.separator+"dynamical_model"+File.separator+
		    			"venple51.exe");
		    }
		    catch (IOException ex) {
		      System.out.println(ex);
		    }
		
	}
	public void mjc(){
		mf=new MainFrame(BR);
		mf.panelAA = new PanelAA(mf);
		mf.setContentPane(mf.panelAA);
		mf.revalidate();//更新
		mf.repaint();
		BR.getDesktopPane().add(mf);
	    try {
	    	mf.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mf.show();
	    BR.getHelpView().navigate(docpath+"jc.html");
	    BR.getDesktopPane().repaint();
	}	
	
	public void jcs(){
		mf=new MainFrame(BR);
		mf.panelA = new PanelA(mf);
		mf.setContentPane(mf.panelA);
		mf.revalidate();
		mf.repaint(); 
		BR.getDesktopPane().add(mf);
	    try {
	    	mf.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mf.show();
	    BR.getHelpView().navigate(docpath+"jc.html");
	    BR.getDesktopPane().repaint();
	}
	public void jcs2(){
		mf=new MainFrame(BR);
		mf.panelA2 = new PanelA2(mf);
		mf.setContentPane(mf.panelA2);
		mf.revalidate();
		mf.repaint(); 
		BR.getDesktopPane().add(mf);
	    try {
	    	mf.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mf.show();
	    BR.getHelpView().navigate(docpath+"jc.html");
	    BR.getDesktopPane().repaint();
	}
	//jcTree查询
	public void execJcTreeFind() {
		mf=new MainFrame(BR);
		mf.setContentPane(new PanelAsearch(mf));
		mf.revalidate();
		mf.repaint(); 
		BR.getDesktopPane().add(mf);
	    try {
	    	mf.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mf.show();
	    BR.getHelpView().navigate(docpath+"jc.html");
	    BR.getDesktopPane().repaint();
	}
	//jcTree2查询
		public void execJcTreeFind2() {
			mf=new MainFrame(BR);
			mf.setContentPane(new PanelA2search(mf));
			mf.revalidate();
			mf.repaint(); 
			BR.getDesktopPane().add(mf);
		    try {
		    	mf.setMaximum(true);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    mf.show();
		    BR.getHelpView().navigate(docpath+"jc.html");
		    BR.getDesktopPane().repaint();
		}
	//markov查询
	public void execMarkovFind() {
		mf=new MainFrame(BR);
		mf.setContentPane(new PanelAAsearch(mf));
		mf.revalidate();//更新
		mf.repaint();
		BR.getDesktopPane().add(mf);
	    try {
	    	mf.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    mf.show();
	    BR.getHelpView().navigate(docpath+"jc.html");
	    BR.getDesktopPane().repaint();
	}
	public void execStartSwot(){
		swot=new statSwot();		
		BR.getDesktopPane().add(swot);
	    try {
			swot.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    swot.show();
	    //System.out.println(docpath+"ana.html");
	    BR.getHelpView().navigate(docpath+"ana.html");
	    BR.getDesktopPane().repaint();
	}	
	public void execStartSearchSwot(){
		searchSwot=new statSearchSwot();		
		BR.getDesktopPane().add(searchSwot);
	    try {
	    	searchSwot.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    searchSwot.show();
	    BR.getHelpView().navigate(docpath+"ana.html");
	    BR.getDesktopPane().repaint();
	}	
	public void execStartScenario(){
		scenario=new statScenario();		
		BR.getDesktopPane().add(scenario);
	    try {
	    	scenario.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    scenario.show();
	    BR.getHelpView().navigate(docpath+"ana.html");
	    BR.getDesktopPane().repaint();
	}	
	public void execStartSearchScenario(){
		searchScenario=new statSearchScenario();		
		BR.getDesktopPane().add(searchScenario);
	    try {
	    	searchScenario.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    searchScenario.show();
	    BR.getHelpView().navigate(docpath+"ana.html");
	    BR.getDesktopPane().repaint();
	}
	//added by shenhui,lec，2016-04-09
		public void execriskcontrolLec(){
			lecFrame=new riskcontrolLec();		
			BR.getDesktopPane().add(lecFrame);
		    try {
		    	lecFrame.setMaximum(true);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    lecFrame.show();
		    BR.getHelpView().navigate(docpath+"lechelp.html");
		    BR.getDesktopPane().repaint();
		}
		

		public void execriskmatrix(){
			riskmatrixFrame=new riskmatrix();	
			BR.getDesktopPane().add(riskmatrixFrame);
			   try {
			    	riskmatrixFrame.setMaximum(true);
				} catch (PropertyVetoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    riskmatrixFrame.show();
			    BR.getHelpView().navigate(docpath+"riskmatrixhelp.html");
			    BR.getDesktopPane().repaint();
		}
		//added by shenhui，lec查询，2016-04-11
		public void execriskLecFind() {
			// TODO Auto-generated method stub
			sub3inFrame lecQueryFrame=new sub3inFrame("LEC查询 ", true, true, true, true);
			riskLecFindPanel = new LecQueryPanel();
			lecQueryFrame.add(riskLecFindPanel);
			BR.getDesktopPane().add(lecQueryFrame);
			lecQueryFrame.pack();
		    try {
		    	lecQueryFrame.setMaximum(true);
			} catch (PropertyVetoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    //
		    lecQueryFrame.show();
		    
		    BR.getHelpView().navigate(docpath+"lechelp.html");
		    BR.getDesktopPane().repaint();
		}
		//added by lixuan，riskmatrix查询，2016-05-04
				public void execriskmatrixFind() {
					// TODO Auto-generated method stub
					sub3inFrame riskmatrixQueryFrame=new sub3inFrame("风险矩阵查询 ", true, true, true, true);
					matrixQueryPanel = new riskmatrixQueryPanel();
					riskmatrixQueryFrame.add(matrixQueryPanel);
					riskmatrixQueryFrame.pack();
					BR.getDesktopPane().add(riskmatrixQueryFrame);
				    try {
				    	riskmatrixQueryFrame.setMaximum(true);
					} catch (PropertyVetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				    riskmatrixQueryFrame.show();
				    
				    BR.getHelpView().navigate(docpath+"riskmatrixhelp.html");
				    BR.getDesktopPane().repaint();
				}
	public void duozhunze(){
		f3=new Frame2();		
		BR.getDesktopPane().add(f3);
	    try {
			f3.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    f3.show();
	    BR.getHelpView().navigate(docpath+"duozhunze.html");
	    BR.getDesktopPane().repaint();
	}
	
	public void execStartR(){
		f2=new statR(BR);		
		BR.getDesktopPane().add(f2);
	    try {
			f2.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    f2.show();
	    BR.getHelpView().navigate(docpath+"stats.html");
	    BR.getDesktopPane().repaint();
	}	
	//zlpj
	
	public void execStartAhpQuery(){
		fAhpQuery=new AhpQuerySub3Frame(BR);		
		BR.getDesktopPane().add(fAhpQuery);
	    try {
	    	fAhpQuery.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    fAhpQuery.show();
	    BR.getHelpView().navigate(docpath+"ahp.html");
	    BR.getDesktopPane().repaint();
	}
	public void execStartfuzzy(){
		fFuzzy=new FuzzyInitFrm();		
		BR.getDesktopPane().add(fFuzzy);
	    try {
			fFuzzy.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    fFuzzy.show();
	    BR.getHelpView().navigate(docpath+"fuzzy.html");
	    BR.getDesktopPane().repaint();
	}	
	public void execStartdea(){
		fDea=new DeaInitFrm();		
		BR.getDesktopPane().add(fDea);
	    try {
			fDea.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    fDea.show();
	    BR.getHelpView().navigate(docpath+"dea.html");
	    BR.getDesktopPane().repaint();
	}	
	
	
	
	public void execStartfuzzyrefer(){
		fFuzzyrefer=new FuzzyQueryFrm();		
		BR.getDesktopPane().add(fFuzzyrefer);
	    try {
			fFuzzyrefer.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    fFuzzyrefer.show();
	    BR.getHelpView().navigate(docpath+"fuzzy.html");
	    BR.getDesktopPane().repaint();
	}	
	public void execStartdearefer(){
		fDearefer=new DeaQueryFrm();		
		BR.getDesktopPane().add(fDearefer);
	    try {
			fDearefer.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    fDearefer.show();
	    BR.getHelpView().navigate(docpath+"dea.html");
	    BR.getDesktopPane().repaint();
	}	
	
	public void execStartAnp(){
		fAnp=new AnpInitFrm(BR);		
		BR.getDesktopPane().add(fAnp);
	    try {
			fAnp.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    fAnp.show();
	    fAnp.setBR(BR.getDesktopPane());
	    BR.getHelpView().navigate(docpath+"anp.html");
	    BR.getDesktopPane().repaint();
	}	
	
	public void execStartanprefer(){
		fAnprefer=new AnpQueryFrm(BR);		
		BR.getDesktopPane().add(fAnprefer);
	    try {
			fAnprefer.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    fAnprefer.show();
	    BR.getHelpView().navigate(docpath+"anp.html");
	    BR.getDesktopPane().repaint();
	}
	public void execStartAhp(){
		fAhp = new AhpInputSub3Frame(BR);
		BR.getDesktopPane().add(fAhp);
	    try {
	    	fAhp.setMaximum(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	    fAhp.show();
	    BR.getHelpView().navigate(docpath+"ahp.html");
	    BR.getDesktopPane().repaint();
	}
	public void execGameMatrix(){
		gm=new GameMatrix(BR);		
		BR.getDesktopPane().add(gm);
	    try {
	    	gm.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    gm.connectdatabase();
	    gm.show();
	    BR.getHelpView().navigate(docpath+"gamematrix.html");
	    BR.getDesktopPane().repaint();
	}
	
	public void execGameTree(){
		gt=new GameTree(BR);		
		BR.getDesktopPane().add(gt);
	    try {
	    	gt.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    gt.connectdatabase();
	    gt.show();
	    BR.getHelpView().navigate(docpath+"gametree.html");
	    BR.getDesktopPane().repaint();
	}
	public void execGameMatrixQuery(){
		gmq=new gamematrixquery(BR);		
		BR.getDesktopPane().add(gmq);
	    try {
	    	gmq.setMaximum(true);
		} catch (PropertyVetoException e) {			
			e.printStackTrace();
		}
	    gmq.connectdatabase();
	    gmq.show();
	    BR.getHelpView().navigate(docpath+"gamematrixquery.html");
	    BR.getDesktopPane().repaint();
	}
	
	public void execGameTreeQuery(){
		gtq=new gametreequery(BR);		
		BR.getDesktopPane().add(gtq);
	    try {
	    	gtq.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    gtq.connectdatabase();
	    gtq.show();
	    BR.getHelpView().navigate(docpath+"gametreequery.html");
	    BR.getDesktopPane().repaint();
	}
	public CopyOfchaxun duozhunzechaxun1;
	public void duozhunzechaxun() {
		// TODO Auto-generated method stub
		//sub3inFrame riskmatrixQueryFrame=new sub3inFrame("多准则查询 ", true, true, true, true);
		duozhunzechaxun1 = new CopyOfchaxun();
		
		BR.getDesktopPane().add(duozhunzechaxun1);
	    try {
	    	duozhunzechaxun1.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    duozhunzechaxun1.show();
	    BR.getHelpView().navigate(docpath+"duozhunze.html");
	    BR.getDesktopPane().repaint();
	}
}
