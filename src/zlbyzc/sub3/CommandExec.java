package zlbyzc.sub3;

import java.beans.PropertyVetoException;
import java.util.HashMap;

import zlbyzc.BasicRibbon;
import zlbyzc.sub3.analysis.panels.statScenario;
import zlbyzc.sub3.analysis.panels.statSearchScenario;
import zlbyzc.sub3.analysis.panels.statSearchSwot;
import zlbyzc.sub3.analysis.panels.statSwot;
import zlbyzc.sub3.stats.statPy;
import zlbyzc.sub3.stats.statR;

public class CommandExec {
	private BasicRibbon BR;
	public statPy f1;
	public statR f2;
	public statSwot swot;
	public statSearchSwot searchSwot;
	public statScenario scenario;
	public statSearchScenario searchScenario;
	public CommandExec(BasicRibbon br){
		BR=br;
	}
	public HashMap<String,sub3inFrame> framesMap;
	public void execStartPy(){
		f1=new statPy();		
		BR.getDesktopPane().add(f1);
	    try {
			f1.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    f1.show();
	    BR.getDesktopPane().repaint();
	}
	public void execStartR(){
		f2=new statR();		
		BR.getDesktopPane().add(f2);
	    try {
			f2.setMaximum(true);
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    f2.show();
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
	    BR.getDesktopPane().repaint();
	}		
}
