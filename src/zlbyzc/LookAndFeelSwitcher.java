package zlbyzc;

import java.awt.Font;
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
	public static boolean setLookAndFeel(String name,final JFrame frame){
		LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();				
		LookAndFeelInfoObj[] lafObjs = new LookAndFeelInfoObj[lafs.length];
		for (int j = 0; j < lafs.length; j++) {		
			String lafName=lafs[j].getName();								
			lafObjs[j] = new LookAndFeelInfoObj(lafs[j], lafName);
			
		}	
		int i = 0;
		boolean rv=false;
		int selected=0 ;
		System.out.println("============");
		for (i = 0; i < lafs.length; i++) {
			System.out.println(lafs[i].getName());
			if (lafs[i].getName().equals(name)) {
				rv=true;
				selected=i;
				System.out.println(selected+","+lafs.length);
				break;
			}
		}		
		if (i>=lafs.length)
			return rv;
		final LookAndFeelInfoObj selLook=lafObjs[selected];
		SwingUtilities.invokeLater(new Runnable() {
			@Override
            public void run() {
				boolean wasDecoratedByOS = !frame.isUndecorated();
				try {
					//LookAndFeelInfoObj selected = result;
					UIManager.setLookAndFeel(selLook.lafInfo
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
		return true;
		
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

	public static void setFont(){
		Font font = new Font("宋体",Font.PLAIN,50);  
		UIManager.put("Button.font",font); 
		UIManager.put("ToggleButton.font",font);  
		UIManager.put("RadioButton.font",font);  
		UIManager.put("CheckBox.font",font);  
		UIManager.put("ColorChooser.font",font);  
		UIManager.put("ToggleButton.font",font);  
		UIManager.put("ComboBox.font",font);  
		UIManager.put("ComboBoxItem.font",font);  
		UIManager.put("InternalFrame.titleFont",font);  
		UIManager.put("Label.font",font);  
		UIManager.put("List.font",font);  
		UIManager.put("MenuBar.font",font);  
		UIManager.put("Menu.font",font);  
		UIManager.put("MenuItem.font",font);  
		UIManager.put("RadioButtonMenuItem.font",font);  
		UIManager.put("CheckBoxMenuItem.font",font);  
		UIManager.put("PopupMenu.font",font);  
		UIManager.put("OptionPane.font",font);  
		UIManager.put("Panel.font",font);  
		UIManager.put("ProgressBar.font",font);  
		UIManager.put("ScrollPane.font",font);  
		UIManager.put("Viewport",font);  
		UIManager.put("TabbedPane.font",font);  
		UIManager.put("TableHeader.font",font);  
		UIManager.put("TextField.font",font);  
		UIManager.put("PasswordFiled.font",font);  
		UIManager.put("TextArea.font",font);  
		UIManager.put("TextPane.font",font);  
		UIManager.put("EditorPane.font",font);  
		UIManager.put("TitledBorder.font",font);  
		UIManager.put("ToolBar.font",font);  
		UIManager.put("ToolTip.font",font);  
		UIManager.put("Tree.font",font);  
	}
}
