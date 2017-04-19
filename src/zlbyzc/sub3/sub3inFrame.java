package zlbyzc.sub3;

import javax.swing.JInternalFrame;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import zlbyzc.gui.ImageTask;

public class sub3inFrame extends JInternalFrame {
	public sub3inFrame(){
		super("战略博弈支持分系统",false,true,false,false);
		init();
	}
	public sub3inFrame(String title, boolean resizable, boolean closable, 
			boolean maximizable, boolean iconifiable){
		super(title,  resizable,  closable, 
				 maximizable,  iconifiable);
		init();
	}
	private void init(){
		setResizable(true);
		ResizableIcon stats_icon=ImageTask.
				getResizableIconFromResource("/img/logoICY.png");
		this.setFrameIcon(stats_icon);
	}
}
