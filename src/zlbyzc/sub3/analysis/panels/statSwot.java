package zlbyzc.sub3.analysis.panels;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;

public class statSwot extends sub3inFrame {
	final SwotNewPanel swotNewPanel = new SwotNewPanel(this);
	public statSwot(){
		super("SWOT分析",true,true,true,true);
		javax.swing.Icon py_icon=ImageTask.
				getResizableIconFromResource("/img/icons/swot.png");
		this.setFrameIcon(py_icon);

		getContentPane().add(swotNewPanel);
		
		
		pack();	    
		this.setVisible(true);
		
	}
		

}
