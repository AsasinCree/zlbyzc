package zlbyzc.sub3.stats;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import zlbyzc.gui.ImageTask;
import zlbyzc.sub3.sub3inFrame;

public class statPy extends sub3inFrame {
	final JFXPanel jfxPanel = new JFXPanel();
	public statPy(){
		super("Python统计模块",true,true,true,true);
		javax.swing.Icon py_icon=ImageTask.
				getResizableIconFromResource("/img/icons/python-144x144.png");
		this.setFrameIcon(py_icon);
		//this.setTitle("Python统计模块");
		//"Python统计模块",false,true,false,false
		Platform.runLater(()-> {
		    WebView webView = new WebView();
		    //int width = getParent().getWidth();
            //int height = getParent().getHeight();

            webView.setMinSize(300, 800);
            webView.setPrefSize(300, 1200);
            
            webView.getEngine().load("https://try.jupyter.org/");
		    Scene wwwscene=new Scene(webView);
		    jfxPanel.setScene(wwwscene);		    
		    Platform.setImplicitExit(false);
		    
		});
		getContentPane().add(jfxPanel);
		
		
		pack();	    
		this.setVisible(true);
		
	}
		

}
