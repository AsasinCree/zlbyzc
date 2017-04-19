package zlbyzc.sub3;

import java.time.LocalDateTime;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import zlbyzc.gui.ImageTask;

public class sub3inFrame extends JInternalFrame {
	//JInternalFrame
	public String createdTime;
	public sub3inFrame(){
		super();
		init();
	}
//	public sub3inFrame(JComponent parent, String title, boolean resizable, boolean closeable,
//            boolean maximizable,
//            boolean iconifiable){
//		super(parent,title,  resizable,  closeable, 
//				 maximizable,  iconifiable);
//		init();
//	}
//	
	
	public sub3inFrame(String title, boolean resizable, boolean closable, 
			boolean maximizable, boolean iconifiable){
		super(title,  resizable,  closable, 
				 maximizable,  iconifiable);
		init();
	}
	protected void init(){
		setResizable(true);
		ResizableIcon stats_icon=ImageTask.
				getResizableIconFromResource("/img/logoICY.png");
		this.setFrameIcon(stats_icon);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setClosable(true);
		LocalDateTime currentTime = LocalDateTime.now();
		createdTime=" - "+currentTime;
	}
}
