package zlbyzc.sub3.analysis.panels;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;

public class statSearchScenario extends sub3inFrame {
	final ScenarioSearchPanel scenarioSearchPanel = new ScenarioSearchPanel();
	public statSearchScenario(){
		super("情景检索",true,true,true,true);
		javax.swing.Icon r_icon=ImageTask.
				getResizableIconFromResource("/img/icons/qjfx-eg.png");
		this.setFrameIcon(r_icon);

		getContentPane().add(scenarioSearchPanel);
				
		pack();	    
		this.setVisible(true);
		
	}
		

}
