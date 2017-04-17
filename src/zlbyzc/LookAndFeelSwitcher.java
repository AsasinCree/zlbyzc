package zlbyzc;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class LookAndFeelSwitcher {
	static class LookAndFeelInfoObj {
		LookAndFeelInfo lafInfo;

		String displayName;

		public LookAndFeelInfoObj(LookAndFeelInfo lafInfo, String displayName) {
			this.lafInfo = lafInfo;
			this.displayName = displayName;
		}

		@Override
		public String toString() {
			return displayName;
		}
	}

	public interface LocaleCallback {
		public void onLocaleSelected(Locale selected);
	}

	public static JComboBox getLookAndFeelSwitcher(final JFrame frame) {
		LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();		
		int LandFrealNum=0;
		for (int i = 0; i < lafs.length; i++) {	
			//delete some L & F,which is not free 
			//todo delete !canBeDecoratedByLAF
			String lafName=lafs[i].getName();
			 
			if (lafName.startsWith("Synthetica")||lafName.endsWith("Windows")||
					lafName.startsWith("A03")||lafName.startsWith("Napkin")||
					lafName.startsWith("Windows")||lafName.startsWith("CDE")||
					lafName.startsWith("Nimbus")||lafName.startsWith("Liquid"))
				continue;			
			LandFrealNum++;
		}
		LookAndFeelInfoObj[] lafObjs = new LookAndFeelInfoObj[LandFrealNum];
		for (int i=0,j = 0; j < lafs.length; j++) {		
			String lafName=lafs[j].getName();			
			if (lafName.startsWith("Synthetica")||lafName.endsWith("Windows")||
					lafName.startsWith("A03")||lafName.startsWith("Napkin")||
					lafName.startsWith("Windows")||lafName.startsWith("CDE")||
					lafName.startsWith("Nimbus")||lafName.startsWith("Liquid"))
				continue;			
			lafObjs[i++] = new LookAndFeelInfoObj(lafs[j], lafName);
			
		}				
		final JComboBox result = new JComboBox(lafObjs);
		int i = 0;
		for (i = 0; i < lafs.length; i++) {
			if (lafs[i].getName().equals(UIManager.getLookAndFeel().getName())) {
				result.setSelectedIndex(i);
				break;
			}
		}
		if (i>=lafs.length)
			System.out.println("Did not find the "+UIManager.getLookAndFeel().getName());
		result.addItemListener(new ItemListener() {
			@Override
            public void itemStateChanged(ItemEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
                    public void run() {
						boolean wasDecoratedByOS = !frame.isUndecorated();
						try {
							LookAndFeelInfoObj selected = (LookAndFeelInfoObj) result
									.getSelectedItem();
							UIManager.setLookAndFeel(selected.lafInfo
									.getClassName());
							SwingUtilities.updateComponentTreeUI(frame);
						} catch (Exception exc) {
							exc.printStackTrace();
						}
						boolean canBeDecoratedByLAF = UIManager
								.getLookAndFeel()
								.getSupportsWindowDecorations();
						//System.out.println(canBeDecoratedByLAF);
						if (canBeDecoratedByLAF == wasDecoratedByOS) {
							boolean wasVisible = frame.isVisible();
							
							frame.setVisible(false);
							frame.dispose();
							if (!canBeDecoratedByLAF) {
								// see the java docs under the method
								// JFrame.setDefaultLookAndFeelDecorated(boolean
								// value) for description of these 2 lines:
								//frame.setUndecorated(false);
								//frame.getRootPane().setWindowDecorationStyle(
								//		JRootPane.NONE);
								frame.setUndecorated(true);
								frame.getRootPane().setWindowDecorationStyle(
										JRootPane.FRAME);
							} else {
								frame.setUndecorated(true);
								frame.getRootPane().setWindowDecorationStyle(
										JRootPane.FRAME);
							}
							frame.setVisible(wasVisible);
							wasDecoratedByOS = !frame.isUndecorated();
						}
					}
				});
			}
		});

		return result;
	}
}
