package zlbyzc.gui.test;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
public class allFramesOfJDesktopPane {
	 public static void main(String[] argv) throws Exception {
		    JDesktopPane desktop = new JDesktopPane();
		    JInternalFrame[] frames = desktop.getAllFrames();

		    for (int i = 0; i < frames.length; i++) {
		      String title = frames[i].getTitle();
		      boolean isVisible = frames[i].isVisible();
		      boolean isCloseable = frames[i].isClosable();
		      boolean isResizeable = frames[i].isResizable();
		      boolean isIconifiable = frames[i].isIconifiable();
		      boolean isIcon = frames[i].isIcon();
		      boolean isMaximizable = frames[i].isMaximizable();
		      boolean isSelected = frames[i].isSelected();
		    }
		  }
}
