package zlbyzc.sub3.analysis.panels;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;

public class statSearchSwot extends sub3inFrame {
	final SwotSearchPanel swotSearchPanel = new SwotSearchPanel();
	public statSearchSwot(){
		super("SWOT检索",true,true,true,true);
		javax.swing.Icon r_icon=ImageTask.
				getResizableIconFromResource("/img/icons/swot-eg.png");
		this.setFrameIcon(r_icon);

		getContentPane().add(swotSearchPanel);
		
		pack();	    
		this.setVisible(true);
		
	}
		

}
