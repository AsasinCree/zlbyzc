package zlbyzc.sub3.analysis.panels;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;

public class statScenario extends sub3inFrame {
	final ScenarioNewPanel scenarioNewPanel = new ScenarioNewPanel(this);
	public statScenario(){
		super("情景分析",true,true,true,true);
		javax.swing.Icon py_icon=ImageTask.
				getResizableIconFromResource("/img/icons/qjfx.png");
		this.setFrameIcon(py_icon);

		getContentPane().add(scenarioNewPanel);
		
		
		pack();	    
		this.setVisible(true);
		
	}
		

}
