/*
 * Copyright (c) 2005-2010 Flamingo Kirill Grouchnikov. All Rights Reserved.
 * 
 * Contributors: Kan Liu 2015
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package zlbyzc;



import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.pushingpixels.flamingo.api.common.*;
import org.pushingpixels.flamingo.api.common.JCommandButton.CommandButtonKind;
import org.pushingpixels.flamingo.api.common.icon.*;
import org.pushingpixels.flamingo.api.common.popup.*;

import org.pushingpixels.flamingo.api.ribbon.*;
import org.pushingpixels.flamingo.api.ribbon.resize.*;
import org.pushingpixels.flamingo.internal.utils.RenderingUtils;

import db.MyPath;
import db.SettingUI;
import zlbyzc.gui.ImageTask;

import zlbyzc.gui.SimpleSwingBrowser;

import zlbyzc.sub3.CommandExec;

import zlbyzc.sub3.stats.statR;
import test.svg.transcoded.*;
import zlbyzc.gui.SplashScreenFrame;
import zlbyzc.gui.mangeFrames;
import zlbyzc.gui.test.TwoRoot;
//import chrriis.dj.nativeswing.swtimpl.NativeInterface;
//import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import icy.resource.ResourceUtil;



 


public class BasicRibbon extends JRibbonFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static SplashScreenFrame splashScreen;
	protected Locale currLocale;
    protected RibbonTask statsTask ;
    protected RibbonTask riskTask ;
	protected ResourceBundle resourceBundle;	 
	protected ResourceBundle messagesRes;
	JSplitPane mainPane;
	private final mainDesk maindesktop;
	JPanel centerPanel;
	protected CommandExec cmdexec;
	public SettingUI setting;
	mangeFrames inFrameManger;
	BasicRibbon me;
	public mainDesk getDesktopPane()
    {
        return maindesktop;
    }
	private List<JCommandButton> downloadButton ;
	public statR statInFra;
	//final JWebBrowser webBrowser;
	private JCommandButton theStatsButton;
	public void enableScriptDownload(boolean e){
		downloadButton.get(0).setEnabled(e);
	}
	public void enableFormatDownload(boolean e){
		for (JCommandButton jb:downloadButton)
			jb.setEnabled(e);
	}
	public void enableDir(boolean e){
		filesButton.get(1).setEnabled(e);
		filesButton.get(2).setEnabled(e);
	}
	public void enableFile(boolean e){
		filesButton.get(0).setEnabled(e);
		//filesButton.get(2).setEnabled(e);
	}
	public void enableStatsButt(boolean e){
		theStatsButton.setEnabled(e);
	}
	SimpleSwingBrowser sSwingBrowser;
	private ArrayList<JCommandButton> filesButton;
	public SimpleSwingBrowser getHelpView()
    {
        return sSwingBrowser;
    }
	protected class QuickStylesPanel extends JCommandButtonPanel {
		public QuickStylesPanel() {
			super(32);

			MessageFormat mfGroupTitle = new MessageFormat(resourceBundle
					.getString("PanelStyles.text"));
			mfGroupTitle.setLocale(currLocale);
			MessageFormat mfTooltipTitle = new MessageFormat(resourceBundle
					.getString("PanelStyles.tooltip.textActionTitle"));
			mfTooltipTitle.setLocale(currLocale);
			MessageFormat mfTooltipParagraph = new MessageFormat(resourceBundle
					.getString("PanelStyles.tooltip.textActionParagraph1"));
			mfTooltipParagraph.setLocale(currLocale);

			for (int groupIndex = 0; groupIndex < 4; groupIndex++) {
				String iconGroupName = mfGroupTitle
						.format(new Object[] { groupIndex });
				this.addButtonGroup(iconGroupName, groupIndex);
				for (int i = 0; i < 15; i++) {
					final int index = i;
					ResizableIcon fontIcon = new font_x_generic();
					ResizableIcon finalIcon = new DecoratedResizableIcon(
							fontIcon,
							new DecoratedResizableIcon.IconDecorator() {
								@Override
								public void paintIconDecoration(Component c,
										Graphics g, int x, int y, int width,
										int height) {
									Graphics2D g2d = (Graphics2D) g.create();
									g2d.setColor(Color.black);
									g2d
											.setFont(UIManager
													.getFont("Label.font"));
									RenderingUtils.installDesktopHints(g2d);
									g2d.drawString("" + index, x + 2, y
											+ height - 2);
									g2d.dispose();
								}
							});
					JCommandToggleButton jrb = new JCommandToggleButton(null,
							finalIcon);
					jrb.setName("Group " + groupIndex + ", index " + i);
					jrb.addActionListener(new ActionListener() {
						@Override
                        public void actionPerformed(ActionEvent e) {
							System.out.println("Invoked action on " + index);
						}
					});
					String actionTooltipTitle = mfTooltipTitle
							.format(new Object[] { i });
					String actionTooltipParagraph1 = mfTooltipParagraph
							.format(new Object[] { i });
					jrb.setActionRichTooltip(new RichTooltip(
							actionTooltipTitle, actionTooltipParagraph1));
					this.addButtonToLastGroup(jrb);
				}
			}
			this.setSingleSelectionMode(true);
		}
	}

	private class ExpandActionListener implements ActionListener {
		@Override
        public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(BasicRibbon.this,
					"Expand button clicked");
		}
	}

	private class SamplePopupMenu extends JCommandPopupMenu {
        public SamplePopupMenu() {
            this(new String[] {"1", "2", "3", null, "4", "5", null, "mm", "mmm", "mmmm", "mmmmm", "mmmmmm"});
        }

		public SamplePopupMenu(String[] items) {
			MessageFormat mf = new MessageFormat(resourceBundle
					.getString("TestMenuItem.text"));
			mf.setLocale(currLocale);

            for (final String s : items) {
                if (s == null) {
                    this.addMenuSeparator();
                } else {
                    JCommandMenuButton menuButton1 = new JCommandMenuButton(mf
                            .format(new Object[] { s }), new EmptyResizableIcon(16));
                    menuButton1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("Test menu item " + s + " activated");
                        }
                    });
                    menuButton1.setActionKeyTip(s);
                    if (s.length() > 1) {
                        menuButton1.setCommandButtonKind(CommandButtonKind.values()[s.length() % CommandButtonKind.values().length]);
                    }
                    this.addMenuButton(menuButton1);
                }
            }
		}
	}

	private AbstractCommandButton getIconButton(final Icon icon,
			boolean isToggle, boolean isSelected, boolean hasPopup) {
		ResizableIcon resizableIcon = new ResizableIcon() {
			int width = icon.getIconWidth();
			int height = icon.getIconHeight();

			@Override
			public int getIconHeight() {
				return this.height;
			}

			@Override
			public int getIconWidth() {
				return this.width;
			}

			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				icon.paintIcon(c, g, x, y);
			}

			@Override
			public void setDimension(Dimension newDimension) {
				this.width = newDimension.width;
				this.height = newDimension.height;
			}
		};
		AbstractCommandButton button = isToggle ? new JCommandToggleButton("",
				resizableIcon) : new JCommandButton("", resizableIcon);
		button.setDisplayState(CommandButtonDisplayState.SMALL);
		button.setGapScaleFactor(0.5);
		if (isSelected)
			button.getActionModel().setSelected(true);

		// make the button narrower by stripping away some of the right-left
		// insets
		Insets currInsets = button.getInsets();
		button.setBorder(new EmptyBorder(currInsets.top, currInsets.top / 2,
				currInsets.bottom, currInsets.bottom / 2));

		if (hasPopup) {
			((JCommandButton) button)
					.setPopupCallback(new PopupPanelCallback() {
						@Override
						public JPopupPanel getPopupPanel(
								JCommandButton commandButton) {
							return new SamplePopupMenu();
						}
					});
		}
		return button;
	}

	

	protected JRibbonBand getClipboardBand() {
		JRibbonBand clipboardBand = new JRibbonBand("脚本编辑", new edit_paste(),
				new ExpandActionListener());
		clipboardBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		clipboardBand.setExpandButtonRichTooltip(expandRichTooltip);
		clipboardBand.setCollapsedStateKeyTip("ZC");

		JCommandButton mainButton = new JCommandButton("粘贴", new edit_paste());
		mainButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				System.out.println("Pasted!");
			}
		});
		mainButton.setPopupCallback(new PopupPanelCallback() {
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new SamplePopupMenu();
			}
		});
		mainButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		RichTooltip mainRichTooltip = new RichTooltip();
		mainRichTooltip.setTitle(resourceBundle.getString("Paste.text"));
		mainRichTooltip.addDescriptionSection(resourceBundle
				.getString("Paste.tooltip.actionParagraph1"));
		mainButton.setActionRichTooltip(mainRichTooltip);
		mainButton.setPopupKeyTip("V");

		RichTooltip mainPopupRichTooltip = new RichTooltip();
		mainPopupRichTooltip.setTitle(resourceBundle.getString("Paste.text"));
		mainPopupRichTooltip.addDescriptionSection(resourceBundle
				.getString("Paste.tooltip.popupParagraph1"));
		mainButton.setPopupRichTooltip(mainPopupRichTooltip);

		clipboardBand.addCommandButton(mainButton, RibbonElementPriority.TOP);

		JCommandButton cutButton = new JCommandButton("剪切", new edit_cut());
		cutButton.setPopupCallback(new PopupPanelCallback() {
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new SamplePopupMenu();
			}
		});
		cutButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		RichTooltip cutRichTooltip = new RichTooltip();
		cutRichTooltip.setTitle(resourceBundle.getString("Cut.text"));
		cutRichTooltip.addDescriptionSection(resourceBundle
				.getString("Cut.tooltip.actionParagraph1"));
		cutButton.setActionRichTooltip(cutRichTooltip);
		cutButton.setPopupKeyTip("X");

		clipboardBand.addCommandButton(cutButton, RibbonElementPriority.MEDIUM);

		JCommandButton copyButton = new JCommandButton("复制", new edit_copy());
		copyButton.setPopupCallback(new PopupPanelCallback() {
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new SamplePopupMenu();
			}
		});
		copyButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
		copyButton.setPopupKeyTip("C");

		clipboardBand
				.addCommandButton(copyButton, RibbonElementPriority.MEDIUM);

		JCommandButton formatButton = new JCommandButton("格式化源文件", new edit_paste());
		formatButton.setPopupCallback(new PopupPanelCallback() {
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				JCommandPopupMenu popupMenu = new JCommandPopupMenu(
						new QuickStylesPanel(), 5, 3);
				JCommandMenuButton saveSelectionButton = new JCommandMenuButton(
						resourceBundle
								.getString("Format.menuSaveSelection.text"),
						new EmptyResizableIcon(16));
				saveSelectionButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Save Selection activated");
					}
				});
				saveSelectionButton.setActionKeyTip("SS");
				popupMenu.addMenuButton(saveSelectionButton);

				JCommandMenuButton clearSelectionButton = new JCommandMenuButton(
						resourceBundle
								.getString("Format.menuClearSelection.text"),
						new EmptyResizableIcon(16));
				clearSelectionButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Clear Selection activated");
					}
				});
				clearSelectionButton.setActionKeyTip("SC");
				popupMenu.addMenuButton(clearSelectionButton);

				popupMenu.addMenuSeparator();
				JCommandMenuButton applyStylesButton = new JCommandMenuButton(
						resourceBundle.getString("Format.applyStyles.text"),
						new EmptyResizableIcon(16));
				applyStylesButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Apply Styles activated");
					}
				});
				applyStylesButton.setActionKeyTip("SA");
				popupMenu.addMenuButton(applyStylesButton);
				return popupMenu;
			}
		});

		formatButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		// pasteButton.addPopupActionListener(new SamplePopupActionListener());
		formatButton
				.setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
		formatButton.setPopupKeyTip("FP");

		clipboardBand.addCommandButton(formatButton,
				RibbonElementPriority.MEDIUM);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(clipboardBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(clipboardBand
				.getControlPanel()));
		clipboardBand.setResizePolicies(resizePolicies);

		return clipboardBand;
	}

	protected JRibbonBand getFindBand() {
		JRibbonBand findBand = new JRibbonBand("搜索", new edit_find());
		findBand.setCollapsedStateKeyTip("ZY");

		JCommandToggleButton findButton = new JCommandToggleButton(
				"查找", new system_search());
		findButton.setActionKeyTip("FD");
		findBand.addCommandButton(findButton, RibbonElementPriority.TOP);

		JCommandToggleButton replaceButton = new JCommandToggleButton(
				"在文件中查找", new edit_find());
		findBand.addCommandButton(replaceButton, RibbonElementPriority.MEDIUM);

		JCommandToggleButton findReplaceButton = new JCommandToggleButton(
				"替换",
				new edit_find_replace());
		findReplaceButton.setEnabled(false);
		findBand.addCommandButton(findReplaceButton,
				RibbonElementPriority.MEDIUM);

		JCommandToggleButton selectAllButton = new JCommandToggleButton(
				"全选",
				new edit_select_all());
		findBand
				.addCommandButton(selectAllButton, RibbonElementPriority.MEDIUM);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(findBand
				.getControlPanel()));
		resizePolicies.add(new IconRibbonBandResizePolicy(findBand
				.getControlPanel()));
		findBand.setResizePolicies(resizePolicies);

		return findBand;
	}

	
	protected JRibbonBand getJudgeBand() {
		ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/judge_sub.png");
		JRibbonBand statsBand = new JRibbonBand("请选择子模块", stats_icon);
		
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		ResizableIcon swot_icon=ImageTask.getResizableIconFromResource("/img/icons/ahpcc.png");
		JCommandButton swotButton = new JCommandButton("AHP层次分析", swot_icon);
		ResizableIcon swoteg_icon=ImageTask.getResizableIconFromResource("/img/icons/anp.png");
		JCommandButton swotegButton = new JCommandButton("ANP网络分析", swoteg_icon);
		
		ResizableIcon py_icon=ImageTask.getResizableIconFromResource("/img/icons/fu.png");
		JCommandButton pythonButton = new JCommandButton("模糊综合评判", py_icon);
		ResizableIcon egpy_icon=ImageTask.getResizableIconFromResource("/img/icons/dea.png");
		JCommandButton egpythonButton = new JCommandButton("DEA数据包络", egpy_icon);
		swotButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				cmdexec.execStartAhp();	
				
			}
		});
		swotegButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartAnp();	
			}
		});
		
		pythonButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartfuzzy();	
			}
		});
		
		egpythonButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartdea();	
			}
		});
		statsBand.addCommandButton(swotButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(swotegButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(egpythonButton, RibbonElementPriority.TOP);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}	
	protected JRibbonBand getJudgeRunBand() {
		ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/qjfx.png");
		JRibbonBand statsBand = new JRibbonBand("查询", stats_icon);
		statsBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		ResizableIcon ahprefer_icon=ImageTask.getResizableIconFromResource("/img/icons/refer.png");
		JCommandButton ahpreferButton = new JCommandButton("AHP结果查询", ahprefer_icon);
		ResizableIcon anprefer_icon=ImageTask.getResizableIconFromResource("/img/icons/refer.png");
		JCommandButton anpreferButton = new JCommandButton("ANP结果查询", anprefer_icon);
		ResizableIcon fuzzyrefer_icon=ImageTask.getResizableIconFromResource("/img/icons/refer.png");
		JCommandButton fuzzyreferButton = new JCommandButton("模糊结果查询", fuzzyrefer_icon);
		ResizableIcon dearefer_icon=ImageTask.getResizableIconFromResource("/img/icons/refer.png");
		JCommandButton deareferButton = new JCommandButton("DEA结果查询", dearefer_icon);
		ahpreferButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartAhpQuery();	
			}
		});
		fuzzyreferButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartfuzzyrefer();	
			}
		});
		deareferButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartdearefer();	
			}
		});
		anpreferButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartanprefer();	
			}
		});
		
		statsBand.addCommandButton(ahpreferButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(anpreferButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(fuzzyreferButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(deareferButton, RibbonElementPriority.TOP);
		
		
		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}
	protected JRibbonBand getAnaBand() {
		
				
		ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/analysis_sub.png");
		JRibbonBand statsBand = new JRibbonBand("请选择子模块", stats_icon,
				new ExpandActionListener());
		statsBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		ResizableIcon swot_icon=ImageTask.getResizableIconFromResource("/img/icons/swot.png");
		JCommandButton swotButton = new JCommandButton("SWOT分析法", swot_icon);
		ResizableIcon swoteg_icon=ImageTask.getResizableIconFromResource("/img/icons/swot-eg.png");
		JCommandButton swotegButton = new JCommandButton("SWOT分析法案例库", swoteg_icon);
		swotButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartSwot();	
			}
		});
		swotegButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartSearchSwot();	
			}
		});
		ResizableIcon py_icon=ImageTask.getResizableIconFromResource("/img/icons/qjfx.png");
		JCommandButton pythonButton = new JCommandButton("情景分析法", py_icon);
		ResizableIcon egpy_icon=ImageTask.getResizableIconFromResource("/img/icons/qjfx-eg.png");
		JCommandButton egpythonButton = new JCommandButton("情景分析法案例库", egpy_icon);
		pythonButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartScenario();	
			}
		});
		egpythonButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartSearchScenario();	
			}
		});
		statsBand.addCommandButton(swotButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(swotegButton, RibbonElementPriority.MEDIUM);
		statsBand.addCommandButton(egpythonButton, RibbonElementPriority.MEDIUM);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}	

	
	protected JRibbonBand getGameBand() {
		ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/game_sub.png");
		JRibbonBand statsBand = new JRibbonBand("请选择子模块", stats_icon,
				new ExpandActionListener());
		statsBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		ResizableIcon py_icon=ImageTask.getResizableIconFromResource("/img/icons/matrixupdate.png");
		JCommandButton pythonButton = new JCommandButton("完全信息静态博弈编辑", py_icon);
		ResizableIcon py_icon2=ImageTask.getResizableIconFromResource("/img/icons/treeupdate.png");
		JCommandButton pythonButton2 = new JCommandButton("其它博弈编辑", py_icon2);
		ResizableIcon py_icon3=ImageTask.getResizableIconFromResource("/img/icons/matrixquery.png");
		JCommandButton pythonButton3 = new JCommandButton("完全信息静态博弈查询", py_icon3);
		ResizableIcon py_icon4=ImageTask.getResizableIconFromResource("/img/icons/treequery.png");
		JCommandButton pythonButton4 = new JCommandButton("其它博弈查询", py_icon4);
		
		
		pythonButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execGameMatrix();	
			}
		});
		pythonButton2.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execGameTree();	
			}
		});
		pythonButton3.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execGameMatrixQuery();	
			}
		});
		pythonButton4.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execGameTreeQuery();	
			}
		});
		statsBand.addCommandButton(pythonButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton2, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton3, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton4, RibbonElementPriority.TOP);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}		
	
	
	protected JRibbonBand getStatsBand() {
		ResizableIcon stats_icon=ImageTask.
				getResizableIconFromResource("/img/icons/stats.png");
		JRibbonBand statsBand = new JRibbonBand("请选择子模块", stats_icon,
				new ExpandActionListener());
		statsBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		ResizableIcon py_icon=ImageTask.getResizableIconFromResource("/img/icons/python-144x144.png");
		theStatsButton = new JCommandButton("统计分析", py_icon);
		theStatsButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartR();	
			}
		});
		
		
		statsBand.addCommandButton(theStatsButton, RibbonElementPriority.TOP);
		//statsBand.addCommandButton(rButton, RibbonElementPriority.TOP);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}	
	protected JRibbonBand getDocumentBand() {
		filesButton= new ArrayList<JCommandButton>();
		ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/stats.png");
		JRibbonBand statsBand = new JRibbonBand("文件操作", stats_icon,
				new ExpandActionListener());
		statsBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		
		
		JCommandButton mvFileButton = new JCommandButton("文件更名", new document_save());
		mvFileButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.rename();	
			}
		});
		mvFileButton.setEnabled(false);
		statsBand.addCommandButton(mvFileButton, RibbonElementPriority.TOP);
		JCommandButton newFileButton = new JCommandButton("新建文件夹", new folder());
		newFileButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.newDir();	
			}
		});
		newFileButton.setEnabled(true);
		statsBand.addCommandButton(newFileButton, RibbonElementPriority.MEDIUM);
		JCommandButton mvDir = new JCommandButton("文件夹更名", new folder_remote());
		mvDir.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.mvDir();	
			}
		});
		mvDir.setEnabled(false);
		statsBand.addCommandButton(mvDir, RibbonElementPriority.MEDIUM);
		
		
		
		filesButton.add(mvFileButton);
		filesButton.add(newFileButton);
		filesButton.add(mvDir);
		
												
		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);
		enableDir(false);
		return statsBand;
	}
	protected JRibbonBand getStatsRunBand() {
		downloadButton= new ArrayList<JCommandButton>();
		ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/stats.png");
		JRibbonBand statsBand = new JRibbonBand("按指定格式将统计资料下载到本地", stats_icon,
				new ExpandActionListener());
		statsBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		ResizableIcon py_icon=ImageTask.getResizableIconFromResource("/img/icons/script.png");
		JCommandButton pythonButton = new JCommandButton("程序脚本原始格式", py_icon);
		pythonButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.download("files");	
			}
		});
		ResizableIcon r_icon=ImageTask.getResizableIconFromResource("/img/icons/html.png");
		JCommandButton rButton = new JCommandButton("网页格式", r_icon);
		rButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.download("html");	
			}
		});
		ResizableIcon im_icon=ImageTask.getResizableIconFromResource("/img/icons/rst.png");
		JCommandButton imButton = new JCommandButton("reStructuredText", im_icon);
		imButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.download("rst");	
			}
		});
		ResizableIcon ex_icon=ImageTask.getResizableIconFromResource("/img/icons/export-data.png");
		JCommandButton exButton = new JCommandButton("Notebook格式", ex_icon);
		exButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.download("notebook");	
			}
		});
		
		ResizableIcon md_icon=ImageTask.getResizableIconFromResource("/img/icons/md.png");
		JCommandButton mdButton = new JCommandButton("MarkDown", md_icon);
		mdButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.download("markdown");	
			}
		});
		ResizableIcon pdf_icon=ImageTask.getResizableIconFromResource("/img/icons/pdf.png");
		JCommandButton pdfButton = new JCommandButton("PDF", pdf_icon);
		pdfButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(statInFra!=null)
				statInFra.download("pdf");	
			}
		});
		
		
		pdfButton.setEnabled(false);
		exButton.setEnabled(false);
		pythonButton.setEnabled(false);
		rButton.setEnabled(false);
		imButton.setEnabled(false);
		mdButton.setEnabled(false);
		downloadButton.add(pythonButton);
		downloadButton.add(exButton);
		
		downloadButton.add(rButton);
		downloadButton.add(imButton);
		downloadButton.add(mdButton);
		downloadButton.add(pdfButton);
		
		statsBand.addCommandButton(pythonButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(exButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(rButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(imButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(mdButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pdfButton, RibbonElementPriority.TOP);
										
		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}	
	
	
	protected JRibbonBand getJcBand() {
		ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/jc-sub.png");
		JRibbonBand statsBand = new JRibbonBand("请选择子模块", stats_icon,
				new ExpandActionListener());
		statsBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		
		ResizableIcon py_icon2=ImageTask.getResizableIconFromResource("/img/icons/mjc.png");
		JCommandButton pythonButton2 = new JCommandButton("马尔科夫决策", py_icon2);
		pythonButton2.addActionListener(new ActionListener() {
			@Override
	         public void actionPerformed(ActionEvent e) {
			cmdexec.mjc();	
				}
			});
		ResizableIcon py_icon3=ImageTask.getResizableIconFromResource("/img/icons/jctree.png");
		JCommandButton pythonButton3 = new JCommandButton("决策树(1)", py_icon3);
		pythonButton3.addActionListener(new ActionListener() {
			@Override
	         public void actionPerformed(ActionEvent e) {
			cmdexec.jcs();	
				}
			});
		ResizableIcon py_icon33=ImageTask.getResizableIconFromResource("/img/icons/jctree.png");
		JCommandButton pythonButton33 = new JCommandButton("决策树(2)", py_icon33);
		pythonButton33.addActionListener(new ActionListener() {
			@Override
	         public void actionPerformed(ActionEvent e) {
			cmdexec.jcs2();	
				}
			});
		ResizableIcon py_icon4=ImageTask.getResizableIconFromResource("/img/icons/duozhunze.png");
		JCommandButton pythonButton4 = new JCommandButton("多准则", py_icon4);
		pythonButton4.addActionListener(new ActionListener() {
			@Override
	         public void actionPerformed(ActionEvent e) {
			cmdexec.duozhunze();	
				}
			});
		
		statsBand.addCommandButton(pythonButton2, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton3, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton33, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton4, RibbonElementPriority.TOP);
		
		
		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}	

	//决策查询
		protected JRibbonBand getJcfindBand(){
			ResizableIcon jc_icon=ImageTask.getResizableIconFromResource("/img/icons/risk_sub.png");
			JRibbonBand jcBandfind = new JRibbonBand("查询", jc_icon,
					new ExpandActionListener());
			jcBandfind.setExpandButtonKeyTip("FO");
			RichTooltip expandRichTooltip = new RichTooltip();
			expandRichTooltip.setTitle(resourceBundle
					.getString("Clipboard.textBandTitle"));
			expandRichTooltip.addDescriptionSection(resourceBundle
					.getString("Clipboard.textBandTooltipParagraph1"));
			jcBandfind.setExpandButtonRichTooltip(expandRichTooltip);
			jcBandfind.setCollapsedStateKeyTip("ZC");
			
			// 决策树查询
			ResizableIcon jcTreeFind_icon=ImageTask.getResizableIconFromResource("/img/icons/risk_lecfind.png");
			JCommandButton jcTreeFindButton = new JCommandButton("决策树(1)查询", jcTreeFind_icon);
			
			jcTreeFindButton.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					cmdexec.execJcTreeFind();	
				}
			});
			
			// 决策树2查询
			ResizableIcon jcTreeFind_icon2=ImageTask.getResizableIconFromResource("/img/icons/risk_lecfind.png");
			JCommandButton jcTreeFindButton2 = new JCommandButton("决策树(2)查询", jcTreeFind_icon2);
			
			jcTreeFindButton2.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					cmdexec.execJcTreeFind2();	
				}
			});
			
			
			//马尔科夫查询
			ResizableIcon markovFind_icon=ImageTask.getResizableIconFromResource("/img/icons/risk_lecfind.png");
			JCommandButton markovFindButton = new JCommandButton("马尔科夫查询", markovFind_icon);
			markovFindButton.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					cmdexec.execMarkovFind();	
				}
			});
			ResizableIcon py_icon5=ImageTask.getResizableIconFromResource("/img/icons/duozhunzechaxun.png");
			JCommandButton pythonButton5 = new JCommandButton("多准则查询", py_icon5);
			pythonButton5.addActionListener(new ActionListener() {
				@Override
		         public void actionPerformed(ActionEvent e) {
				cmdexec.duozhunzechaxun();	
					}
				});
			
			jcBandfind.addCommandButton(markovFindButton, RibbonElementPriority.TOP);
			jcBandfind.addCommandButton(jcTreeFindButton, RibbonElementPriority.TOP);
			jcBandfind.addCommandButton(jcTreeFindButton2, RibbonElementPriority.TOP);
			
			jcBandfind.addCommandButton(pythonButton5, RibbonElementPriority.TOP);
			List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
			resizePolicies.add(new CoreRibbonResizePolicies.Mirror(jcBandfind
					.getControlPanel()));
			resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(jcBandfind
					.getControlPanel()));
			jcBandfind.setResizePolicies(resizePolicies);
			
			return jcBandfind;
			
		}
		/////////////////
	protected JRibbonBand getRiskBand() {
		ResizableIcon risk_icon=ImageTask.getResizableIconFromResource("/img/icons/risk_sub.png");
		JRibbonBand riskBand = new JRibbonBand("请选择子模块", risk_icon,
				new ExpandActionListener());
		riskBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		riskBand.setExpandButtonRichTooltip(expandRichTooltip);
		riskBand.setCollapsedStateKeyTip("ZC");

		ResizableIcon lec_icon=ImageTask.getResizableIconFromResource("/img/icons/lec.png");
JCommandButton lecButton = new JCommandButton("LEC分析法", lec_icon);
		
		// added by shenhui 添加LEC actionlistener, 2016-04-09
		lecButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execriskcontrolLec();	
			}
		});
		ResizableIcon riskMat_icon=ImageTask.getResizableIconFromResource("/img/icons/riskMat.png");
		JCommandButton riskMatButton = new JCommandButton("风险矩阵法", riskMat_icon);
		riskMatButton.addActionListener(new ActionListener() {
			@Override
         public void actionPerformed(ActionEvent e) {
		cmdexec.execriskmatrix();	
			}
		});
		riskBand.addCommandButton(lecButton, RibbonElementPriority.TOP);
		riskBand.addCommandButton(riskMatButton, RibbonElementPriority.TOP);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(riskBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(riskBand
				.getControlPanel()));
		riskBand.setResizePolicies(resizePolicies);

		return riskBand;
	}	
	//added by shenhui 风险查询按钮及功能，2016-04-11
		protected JRibbonBand getRiskfindBand(){
			ResizableIcon lec_icon=ImageTask.getResizableIconFromResource("/img/icons/risk_sub.png");
			JRibbonBand riskBandfind = new JRibbonBand("查询", lec_icon,
					new ExpandActionListener());
			riskBandfind.setExpandButtonKeyTip("FO");
			RichTooltip expandRichTooltip = new RichTooltip();
			expandRichTooltip.setTitle(resourceBundle
					.getString("Clipboard.textBandTitle"));
			expandRichTooltip.addDescriptionSection(resourceBundle
					.getString("Clipboard.textBandTooltipParagraph1"));
			riskBandfind.setExpandButtonRichTooltip(expandRichTooltip);
			riskBandfind.setCollapsedStateKeyTip("ZC");
			
			// LEC查询
			ResizableIcon riskLecFind_icon=ImageTask.getResizableIconFromResource("/img/icons/risk_lecfind.png");
			JCommandButton riskLecFindButton = new JCommandButton("LEC查询", riskLecFind_icon);
			
			riskLecFindButton.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					cmdexec.execriskLecFind();	
				}
			});
			
			
			
			//风险矩阵查询
			ResizableIcon riskMatFind_icon=ImageTask.getResizableIconFromResource("/img/icons/risk_lecfind.png");
			JCommandButton riskMatFindButton = new JCommandButton("风险矩阵查询", riskMatFind_icon);
			riskMatFindButton.addActionListener(new ActionListener() {
				@Override
	            public void actionPerformed(ActionEvent e) {
					cmdexec.execriskmatrixFind();	
				}
			});
			
			riskBandfind.addCommandButton(riskLecFindButton, RibbonElementPriority.TOP);
			riskBandfind.addCommandButton(riskMatFindButton, RibbonElementPriority.TOP);
		
			List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
			resizePolicies.add(new CoreRibbonResizePolicies.Mirror(riskBandfind
					.getControlPanel()));
			resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(riskBandfind
					.getControlPanel()));
			riskBandfind.setResizePolicies(resizePolicies);
			
			return riskBandfind;
			
		}

	protected RibbonContextualTaskGroup group1;
	protected RibbonContextualTaskGroup group2;

	protected JPanel statusBar;

	private JRibbonBand paragraphBand;

	public BasicRibbon() {
		super();
		setting = new SettingUI();
		statInFra=null;
		me=this;
		Properties props = System.getProperties();
    	props.setProperty("nativeswing.webbrowser.xulrunner.home",
    			MyPath.getPath()+File.separator+
    			"libs"+File.separator+"xulrunner31");
    	//System.out.println();
    	//NativeInterface.open();
		//webBrowser = new JWebBrowser(JWebBrowser.useXULRunnerRuntime());
		maindesktop=new mainDesk(this);
		setApplicationIcon(new applications_internet());
		currLocale = Locale.getDefault();        
        currLocale = new Locale("zh", "CN");        
        messagesRes = ResourceBundle.getBundle("i18n.MessagesBundle", currLocale);
		resourceBundle = ResourceBundle.getBundle("test.resource.Resources",
				currLocale);
		
		setTitle(messagesRes.getString("guiTitle"));
		setIconImages(ResourceUtil.getIcyIconImages());
		cmdexec=new CommandExec(this);
	}

	public void configureRibbon() {
		
		
		JRibbonBand anaBand = this.getAnaBand();
		JRibbonBand judgeBand = this.getJudgeBand();
		JRibbonBand judgeBandRun = this.getJudgeRunBand();
		JRibbonBand jcBand = this.getJcBand();
		JRibbonBand gameBand = this.getGameBand();
		RibbonTask anaTask = new RibbonTask("战略分析", anaBand);
		JRibbonBand riskBand = this.getRiskBand();
		//RibbonTask riskTask = new RibbonTask("战略风险管控", riskBand);
		//RibbonTask jcTask = new RibbonTask("战略决策", jcBand);
		JRibbonBand jcBandFind = this.getJcfindBand();
		RibbonTask jcTask = new RibbonTask("战略决策", jcBand,jcBandFind);
		
		RibbonTask judgeTask = new RibbonTask("战略评价", judgeBand,judgeBandRun);
		RibbonTask gameTask = new RibbonTask("战略博弈", gameBand);
		
		JRibbonBand riskBandFind = this.getRiskfindBand(); //added by shenhui,增加风险查询，2016-04-11
		riskTask = new RibbonTask("战略风险管控", riskBand,riskBandFind);
		JRibbonBand statsBand = this.getStatsBand();
		JRibbonBand statsBandRun = this.getStatsRunBand();
		JRibbonBand statsBandDoc = this.getDocumentBand();
		JRibbonBand statsBandFind = this.getFindBand();
		JRibbonBand statsBandClip =this.getClipboardBand();
		statsTask = new RibbonTask("战略统计分析", statsBand,statsBandDoc,statsBandRun);
		
		LinkedList taskList=new LinkedList();
		taskList.add(anaTask);
		taskList.add(gameTask);
		taskList.add(jcTask);
		taskList.add(judgeTask);
		taskList.add(riskTask);
		taskList.add(statsTask);
		ImageTask task=new ImageTask(messagesRes,this.getRibbon(),taskList,cmdexec);
		this.getRibbon().addTask(task);
		
		this.getRibbon().addTask(anaTask);
		this.getRibbon().addTask(gameTask);
		this.getRibbon().addTask(jcTask);
		this.getRibbon().addTask(judgeTask);
		this.getRibbon().addTask(riskTask);
		this.getRibbon().addTask(statsTask);
		
		
		this.getRibbon().configureHelp(new help_browser(),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(mainPane.getDividerLocation()>10)
							mainPane.setDividerLocation(0);
						else
							mainPane.setDividerLocation(300);
					}
				});
        this.getRibbon().setHelpRichTooltip(new RichTooltip("上下文帮助",
        		"程序左侧会提示与现行内容相关的信息"));

		group1 = new RibbonContextualTaskGroup(resourceBundle
				.getString("Group1.textTaskGroupTitle"), Color.red,
				getContextualRibbonTask(resourceBundle
						.getString("Task11.textTaskTitle"), "XA"),
				getContextualRibbonTask(resourceBundle
						.getString("Task12.textTaskTitle"), "XB"));
		System.out.println(resourceBundle
						.getString("Task11.textTaskTitle"));
		group2 = new RibbonContextualTaskGroup(resourceBundle
				.getString("Group2.textTaskGroupTitle"), Color.green,
				getContextualRibbonTask(resourceBundle
						.getString("Task21.textTaskTitle"), "YA"));
		this.getRibbon().addContextualTaskGroup(group1);
		this.getRibbon().addContextualTaskGroup(group2);

		//configureTaskBar();// TODO

		// application menu
		configureApplicationMenu(); //TODO

//		JPanel controlPanel = new JPanel();
//		controlPanel.setBorder(new EmptyBorder(20, 0, 0, 5));
//		controlPanel.setBackground(new java.awt.Color(225, 225, 225));
//		FormLayout lm = new FormLayout("right:pref, 4dlu, fill:pref:grow", "");
//		DefaultFormBuilder builder = new DefaultFormBuilder(lm, controlPanel);		
//		this.configureControlPanel(builder);
		
		JPanel leftPanel = new JPanel(new BorderLayout());
		
//		final JFXPanel jfxPanel = new JFXPanel();
/*
		webBrowser.setMenuBarVisible(false);
        webBrowser.setLocationBarVisible(false);
		webBrowser.navigate(System.getProperty("user.dir")+File.separator+"doc"
				+File.separator+"templet.html");*/
		
		sSwingBrowser=new SimpleSwingBrowser("file:///"+MyPath.getPath()+File.separator+"doc"
				+File.separator+"templet.html","Help");
		//builder.append(webBrowser);

		//leftPanel.add(new Label("Help:"),BorderLayout.PAGE_START);
		leftPanel.add(sSwingBrowser, BorderLayout.CENTER);
		//leftPanel.add(controlPanel);
		
		centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        
        centerPanel.add(maindesktop, BorderLayout.CENTER);
        
		mainPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, null);
		mainPane.setContinuousLayout(true);
		mainPane.setRightComponent(centerPanel);
		mainPane.setResizeWeight(1);
		mainPane.setDividerLocation(0);
		mainPane.setDividerSize(6);
		this.add(mainPane, BorderLayout.CENTER);
		validate();
		//this.add(leftPanel, BorderLayout.WEST);
		//this.add(new mainDesk(), BorderLayout.CENTER);

		this.configureStatusBar();
	}

	protected void configureTaskBar() {
		// taskbar components 标题栏下快捷按钮
		JCommandButton taskbarButtonPaste = new JCommandButton("",
				new edit_paste());
		taskbarButtonPaste
				.setCommandButtonKind(CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		taskbarButtonPaste.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("标题栏下快捷按钮");
			}
		});
		taskbarButtonPaste.setPopupCallback(new PopupPanelCallback() {
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				return new SamplePopupMenu();
			}
		});
		taskbarButtonPaste.setActionRichTooltip(new RichTooltip(resourceBundle
				.getString("Paste.text"), resourceBundle
				.getString("Paste.tooltip.actionParagraph1")));
		taskbarButtonPaste.setPopupRichTooltip(new RichTooltip(resourceBundle
				.getString("Paste.text"), resourceBundle
				.getString("Paste.tooltip.popupParagraph1")));
		taskbarButtonPaste.setActionKeyTip("1");
		this.getRibbon().addTaskbarComponent(taskbarButtonPaste);

		JCommandButton taskbarButtonClear = new JCommandButton("",
				new edit_clear());
		taskbarButtonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Taskbar Clear activated");
			}
		});
		taskbarButtonClear.setEnabled(false);
		taskbarButtonClear.setActionKeyTip("2");
		this.getRibbon().addTaskbarComponent(taskbarButtonClear);

		JCommandButton taskbarButtonCopy = new JCommandButton("",
				new edit_copy());
		taskbarButtonCopy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Taskbar Copy activated");
			}
		});
		taskbarButtonCopy.setActionKeyTip("3");
		this.getRibbon().addTaskbarComponent(taskbarButtonCopy);

		this.getRibbon().addTaskbarComponent(
				new JSeparator(JSeparator.VERTICAL));

		JCommandButton taskbarButtonFind = new JCommandButton("",
				new edit_find());
		taskbarButtonFind.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Taskbar Find activated");
			}
		});
		taskbarButtonFind.setActionKeyTip("4");
		this.getRibbon().addTaskbarComponent(taskbarButtonFind);
	}

	protected void configureApplicationMenu() {
		RibbonApplicationMenuEntryPrimary amEntryNew = new RibbonApplicationMenuEntryPrimary(
				new document_new(),
				resourceBundle.getString("AppMenuNew.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked creating new document");
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryNew.setActionKeyTip("N");

		RibbonApplicationMenuEntryPrimary amEntryOpen = new RibbonApplicationMenuEntryPrimary(
				new document_open(), resourceBundle
						.getString("AppMenuOpen.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked opening document");
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryOpen
				.setRolloverCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {
					@Override
					public void menuEntryActivated(JPanel targetPanel) {
						targetPanel.removeAll();
						JCommandButtonPanel openHistoryPanel = new JCommandButtonPanel(
								CommandButtonDisplayState.MEDIUM);
						String groupName = resourceBundle
								.getString("AppMenuOpen.secondary.textGroupTitle1");
						openHistoryPanel.addButtonGroup(groupName);

						MessageFormat mf = new MessageFormat(resourceBundle
								.getString("AppMenuOpen.secondary.textButton"));
						mf.setLocale(currLocale);
						for (int i = 0; i < 5; i++) {
							JCommandButton historyButton = new JCommandButton(
									mf.format(new Object[] { i }),
									new text_html());
							historyButton
									.setHorizontalAlignment(SwingUtilities.LEFT);
							openHistoryPanel
									.addButtonToLastGroup(historyButton);
						}
						openHistoryPanel.setMaxButtonColumns(1);
						targetPanel.setLayout(new BorderLayout());
						targetPanel.add(openHistoryPanel, BorderLayout.CENTER);
					}
				});
		amEntryOpen.setActionKeyTip("O");

		RibbonApplicationMenuEntryPrimary amEntrySave = new RibbonApplicationMenuEntryPrimary(
				new document_save(), resourceBundle
						.getString("AppMenuSave.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked saving document");
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntrySave.setEnabled(false);
		amEntrySave.setActionKeyTip("S");

		RibbonApplicationMenuEntryPrimary amEntrySaveAs = new RibbonApplicationMenuEntryPrimary(
				new document_save_as(), resourceBundle
						.getString("AppMenuSaveAs.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked saving document as");
					}
				}, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		amEntrySaveAs.setActionKeyTip("A");
		amEntrySaveAs.setPopupKeyTip("F");


		RibbonApplicationMenuEntryPrimary amEntryExit = new RibbonApplicationMenuEntryPrimary(
				new system_log_out(), resourceBundle
						.getString("AppMenuExit.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryExit.setActionKeyTip("X");

		RibbonApplicationMenuEntryPrimary amEntryHelp =
				new RibbonApplicationMenuEntryPrimary(
				new text_x_generic(), "上下文帮助开关", new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(mainPane.getDividerLocation()>10)
							mainPane.setDividerLocation(0);
						else
							mainPane.setDividerLocation(300);
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryExit.setActionKeyTip("F1");
		
		RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu();
		//applicationMenu.addMenuEntry(amEntryNew);
		//applicationMenu.addMenuEntry(amEntryOpen);
		//applicationMenu.addMenuEntry(amEntrySave);
		//applicationMenu.addMenuEntry(amEntrySaveAs);
		//applicationMenu.addMenuSeparator();
		//applicationMenu.addMenuEntry(amEntryPrint);
		//applicationMenu.addMenuEntry(amEntrySend);
		//applicationMenu.addMenuSeparator();
		//applicationMenu.addMenuEntry(amEntryExit);
		applicationMenu.addMenuEntry(amEntryHelp);
		/*applicationMenu
				.setDefaultCallback(new RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback() {
					@Override
					public void menuEntryActivated(JPanel targetPanel) {
						targetPanel.removeAll();
						JCommandButtonPanel openHistoryPanel = new JCommandButtonPanel(
								CommandButtonDisplayState.MEDIUM);
						String groupName = resourceBundle
								.getString("AppMenu.default.textGroupTitle1");
						openHistoryPanel.addButtonGroup(groupName);

						MessageFormat mf = new MessageFormat(resourceBundle
								.getString("AppMenu.default.textButton"));
						mf.setLocale(currLocale);
						for (int i = 0; i < 5; i++) {
							JCommandButton historyButton = new JCommandButton(
									mf.format(new Object[] { i }),
									new text_html());
							historyButton
									.setHorizontalAlignment(SwingUtilities.LEFT);
							openHistoryPanel
									.addButtonToLastGroup(historyButton);
						}
						openHistoryPanel.setMaxButtonColumns(1);
						targetPanel.setLayout(new BorderLayout());
						//targetPanel.add(openHistoryPanel, BorderLayout.CENTER);
					}
				});
		 */
		RibbonApplicationMenuEntryPrimary amFooterProps = new RibbonApplicationMenuEntryPrimary(
				new document_properties(), "参数设置",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						final Toolkit toolkit = Toolkit.getDefaultToolkit();
						final Dimension screenSize = toolkit.getScreenSize();
						final int x = (screenSize.width - setting.getWidth()) / 2;
						final int y = (screenSize.height - setting.getHeight()) / 2;
						setting.setLocation(x, y);
						setting.setVisible(true);
					}
				}, CommandButtonKind.ACTION_ONLY);
		RibbonApplicationMenuEntryPrimary amWinProps = new RibbonApplicationMenuEntryPrimary(
				new document_properties(), "窗口列表",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						inFrameManger=new mangeFrames(me);
						final Toolkit toolkit = Toolkit.getDefaultToolkit();
						final Dimension screenSize = toolkit.getScreenSize();
						final int x = (screenSize.width - setting.getWidth()) / 2;
						final int y = (screenSize.height - setting.getHeight()) / 2;
						inFrameManger.setLocation(x, y);
						inFrameManger.setVisible(true);
					}
				}, CommandButtonKind.ACTION_ONLY);
		RibbonApplicationMenuEntryPrimary amFooterExit = new RibbonApplicationMenuEntryPrimary(
				new system_log_out(), "退出", new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				}, CommandButtonKind.ACTION_ONLY);
		//amFooterExit.setEnabled(true);
		//applicationMenu.addFooterEntry(amFooterProps);
		//applicationMenu.addFooterEntry(amFooterExit);
		applicationMenu.addMenuEntry(amFooterProps);
		applicationMenu.addMenuEntry(amWinProps);
		applicationMenu.addMenuEntry(amFooterExit);
		this.getRibbon().setApplicationMenu(applicationMenu);

		RichTooltip appMenuRichTooltip = new RichTooltip();
		appMenuRichTooltip.setTitle(resourceBundle.getString("AppMenu.tooltip.title"));
		appMenuRichTooltip.addDescriptionSection(resourceBundle
				.getString("AppMenu.tooltip.paragraph1"));
		try {
			appMenuRichTooltip
					.setMainImage(ImageIO
							.read(BasicRibbon.class
									.getResource("/test/ribbon/appmenubutton-tooltip-main.png")));
			appMenuRichTooltip.setFooterImage(ImageIO
					.read(BasicRibbon.class
							.getResource("/test/ribbon/help-browser.png")));
		} catch (IOException ignored) {
		}
		appMenuRichTooltip.addFooterSection(resourceBundle
				.getString("AppMenu.tooltip.footer1"));
		this.getRibbon().setApplicationMenuRichTooltip(appMenuRichTooltip);
		this.getRibbon().setApplicationMenuKeyTip("F");
	}

	protected RibbonTask getContextualRibbonTask(String title, String keyTip) {
		JRibbonBand actionBand = this.getFindBand();
		JRibbonBand arrangeBand = this.getTransitionNextBand();
		
		RibbonTask task = new RibbonTask(title, actionBand, arrangeBand);
		task.setKeyTip(keyTip);
		return task;
	}
	
	protected JRibbonBand getTransitionNextBand() {
		JRibbonBand transitionBand = new JRibbonBand(resourceBundle
				.getString("TransitionToNext.textBandTitle"),
				new SimpleResizableIcon(RibbonElementPriority.TOP, 32, 32));

		JCheckBox mouseClick = new JCheckBox(resourceBundle
				.getString("OnMouseClick.text"));
		mouseClick.setSelected(true);
		JRibbonComponent mouseClickWrapper = new JRibbonComponent(mouseClick);
		transitionBand.addRibbonComponent(mouseClickWrapper);

		JCheckBox autoAfter = new JCheckBox(resourceBundle
				.getString("AutoAfter.text"));
		JRibbonComponent autoAfterWrapper = new JRibbonComponent(autoAfter);
		transitionBand.addRibbonComponent(autoAfterWrapper);

		transitionBand.addRibbonComponent(new JRibbonComponent(
				new SimpleResizableIcon(RibbonElementPriority.TOP, 16, 16), "",
				new JSpinner(new SpinnerDateModel())));

		return transitionBand;
	}


	protected JFlowRibbonBand getFontBand() {
		JFlowRibbonBand fontBand = new JFlowRibbonBand(resourceBundle
				.getString("Font.textBandTitle"),
				new preferences_desktop_font(), new ExpandActionListener());
		fontBand.setExpandButtonKeyTip("FN");
		fontBand.setCollapsedStateKeyTip("ZF");

		JComboBox fontCombo = new JComboBox(new Object[] {
				"+ Minor (Calibri)   ", "+ Minor (Columbus)   ",
				"+ Minor (Consolas)   ", "+ Minor (Cornelius)   ",
				"+ Minor (Cleopatra)   ", "+ Minor (Cornucopia)   ",
				"+ Minor (Candella)   ", "+ Minor (Cambria)   " });
		JRibbonComponent fontComboWrapper = new JRibbonComponent(fontCombo);
		fontComboWrapper.setKeyTip("SF");
		fontBand.addFlowComponent(fontComboWrapper);

		JComboBox sizeCombo = new JComboBox(new Object[] { "11  " });
		JRibbonComponent sizeComboWrapper = new JRibbonComponent(sizeCombo);
		sizeComboWrapper.setKeyTip("SS");
		fontBand.addFlowComponent(sizeComboWrapper);

		JCommandButtonStrip indentStrip = new JCommandButtonStrip();

		JCommandButton indentLeftButton = new JCommandButton("",
				new format_indent_less());
		indentLeftButton.setActionKeyTip("AO");
		indentStrip.add(indentLeftButton);

		JCommandButton indentRightButton = new JCommandButton("",
				new format_indent_more());
		indentRightButton.setActionKeyTip("AI");
		indentStrip.add(indentRightButton);

		fontBand.addFlowComponent(indentStrip);

		JCommandButtonStrip styleStrip = new JCommandButtonStrip();

		JCommandToggleButton styleBoldButton = new JCommandToggleButton("",
				new format_text_bold());
		styleBoldButton.getActionModel().setSelected(true);
		styleBoldButton.setActionRichTooltip(new RichTooltip(resourceBundle
				.getString("FontBold.tooltip.textActionTitle"), resourceBundle
				.getString("FontBold.tooltip.textActionParagraph1")));
		styleBoldButton.setActionKeyTip("1");
		styleStrip.add(styleBoldButton);

		JCommandToggleButton styleItalicButton = new JCommandToggleButton("",
				new format_text_italic());
		styleItalicButton.setActionRichTooltip(new RichTooltip(resourceBundle
				.getString("FontItalic.tooltip.textActionTitle"),
				resourceBundle
						.getString("FontItalic.tooltip.textActionParagraph1")));
		styleItalicButton.setActionKeyTip("2");
		styleStrip.add(styleItalicButton);

		JCommandToggleButton styleUnderlineButton = new JCommandToggleButton(
				"", new format_text_underline());
		styleUnderlineButton
				.setActionRichTooltip(new RichTooltip(
						resourceBundle
								.getString("FontUnderline.tooltip.textActionTitle"),
						resourceBundle
								.getString("FontUnderline.tooltip.textActionParagraph1")));
		styleUnderlineButton.setActionKeyTip("3");
		styleStrip.add(styleUnderlineButton);

		JCommandToggleButton styleStrikeThroughButton = new JCommandToggleButton(
				"", new format_text_strikethrough());
		styleStrikeThroughButton
				.setActionRichTooltip(new RichTooltip(
						resourceBundle
								.getString("FontStrikethrough.tooltip.textActionTitle"),
						resourceBundle
								.getString("FontStrikethrough.tooltip.textActionParagraph1")));
		styleStrikeThroughButton.setActionKeyTip("4");
		styleStrip.add(styleStrikeThroughButton);

		fontBand.addFlowComponent(styleStrip);

		JCommandButtonStrip alignStrip = new JCommandButtonStrip();
		CommandToggleButtonGroup alignGroup = new CommandToggleButtonGroup();

		JCommandToggleButton alignLeftButton = new JCommandToggleButton("",
				new format_justify_left());
		alignLeftButton.setActionKeyTip("AL");
		alignLeftButton.getActionModel().setSelected(true);
		alignGroup.add(alignLeftButton);
		alignStrip.add(alignLeftButton);

		JCommandToggleButton alignCenterButton = new JCommandToggleButton("",
				new format_justify_center());
		alignCenterButton.setActionKeyTip("AC");
		alignGroup.add(alignCenterButton);
		alignStrip.add(alignCenterButton);

		JCommandToggleButton alignRightButton = new JCommandToggleButton("",
				new format_justify_right());
		alignRightButton.setActionKeyTip("AR");
		alignGroup.add(alignRightButton);
		alignStrip.add(alignRightButton);

		JCommandToggleButton alignFillButton = new JCommandToggleButton("",
				new format_justify_fill());
		alignFillButton.setActionKeyTip("AF");
		alignGroup.add(alignFillButton);
		alignStrip.add(alignFillButton);

		fontBand.addFlowComponent(alignStrip);

		return fontBand;
	}

	/**
	 * Main method for testing.
	 * 
	 * @param args
	 *            Ignored.
	 */
	public static void main(String[] args) {
		UIManager.installLookAndFeel("JGoodies Plastic",
				"com.jgoodies.looks.plastic.PlasticLookAndFeel");
		UIManager.installLookAndFeel("JGoodies PlasticXP",
				"com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		UIManager.installLookAndFeel("JGoodies Plastic3D",
				"com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		UIManager.installLookAndFeel("JGoodies Windows",
				"com.jgoodies.looks.windows.WindowsLookAndFeel");

		UIManager.installLookAndFeel("Synthetica base",
				"de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlackEye",
				"de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlackMoon",
				"de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlackStar",
				"de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlueIce",
				"de.javasoft.plaf.synthetica.SyntheticaBlueIceLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlueMoon",
				"de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel");
		UIManager.installLookAndFeel("Synthetica BlueSteel",
				"de.javasoft.plaf.synthetica.SyntheticaBlueSteelLookAndFeel");
		UIManager.installLookAndFeel("Synthetica GreenDream",
				"de.javasoft.plaf.synthetica.SyntheticaGreenDreamLookAndFeel");
		UIManager
				.installLookAndFeel("Synthetica MauveMetallic",
						"de.javasoft.plaf.synthetica.SyntheticaMauveMetallicLookAndFeel");
		UIManager
				.installLookAndFeel("Synthetica OrangeMetallic",
						"de.javasoft.plaf.synthetica.SyntheticaOrangeMetallicLookAndFeel");
		UIManager.installLookAndFeel("Synthetica SkyMetallic",
				"de.javasoft.plaf.synthetica.SyntheticaSkyMetallicLookAndFeel");
		UIManager.installLookAndFeel("Synthetica SilverMoon",
				"de.javasoft.plaf.synthetica.SyntheticaSilverMoonLookAndFeel");
		UIManager.installLookAndFeel("Synthetica WhiteVision",
				"de.javasoft.plaf.synthetica.SyntheticaWhiteVisionLookAndFeel");
		UIManager.installLookAndFeel("Synthetica Simple2D",
				"de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");

		UIManager.installLookAndFeel("A03", "a03.swing.plaf.A03LookAndFeel");
		UIManager.installLookAndFeel("Liquid",
				"com.birosoft.liquid.LiquidLookAndFeel");
		UIManager.installLookAndFeel("Napkin",
				"net.sourceforge.napkinlaf.NapkinLookAndFeel");
		UIManager.installLookAndFeel("Pagosoft",
				"com.pagosoft.plaf.PgsLookAndFeel");
		UIManager.installLookAndFeel("Squareness",
				"net.beeger.squareness.SquarenessLookAndFeel");
		final Object lock = new Object();
				
		try {
			EventQueue.invokeAndWait(new Runnable()
			{
			    @Override
			    public void run()
			    {
			        // display splash screen
			    	splashScreen = new SplashScreenFrame(lock);            	
			        splashScreen.startInit();
			        
			    }
			});
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		JFrame.setDefaultLookAndFeelDecorated(true);
		
		System.out.println(new File(BasicRibbon.class.getResource("/").getFile())  
		        .getAbsolutePath());
		
		 synchronized(lock){
             {
			 try {
				 	
					UIManager.setLookAndFeel(new MetalLookAndFeel());
				} catch (Exception ignored) {
				}
             
             }
         }
		 
		 
		 
		SwingUtilities.invokeLater(new Runnable() {
			@Override
            public void run() {
				final BasicRibbon c = new BasicRibbon();
				c.configureRibbon();
				c.applyComponentOrientation(ComponentOrientation
						.getOrientation(Locale.getDefault()));
				Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getMaximumWindowBounds();
				c.setPreferredSize(new Dimension(r.width, r.height ));
				c.setMinimumSize(new Dimension(r.width / 10, r.height / 2));
				c.pack();
				c.setLocation(r.x, r.y);
				try {
					
				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if (splashScreen != null)
		        {
		            // then do less important stuff later
		            ThreadUtil.invokeLater(new Runnable()
		            {
		                @Override
		                public void run()
		                {
		                    // we can now hide splash as we have interface
		                    splashScreen.dispose();
		                    splashScreen = null;
		                }
		            });
		        }
				
				c.setVisible(true);
				c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				c.addComponentListener(new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
						System.out.println("Size " + c.getSize());
					}
				});

				c.getRootPane().getInputMap(
						JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
						KeyStroke.getKeyStroke("alt shift E"),
						"installTracingRepaintManager");
				c.getRootPane().getActionMap().put(
						"installTracingRepaintManager", new AbstractAction() {
							@Override
							public void actionPerformed(ActionEvent e) {
								RepaintManager
										.setCurrentManager(new TracingRepaintManager());
							}
						});
			}
		});
	}

	protected void configureStatusBar() {
		statusBar = new JPanel(new FlowLayout(FlowLayout.TRAILING));

		JLabel helper = new JLabel("Right click to show menu");
		//statusBar.add(helper);

		JCommandButtonStrip alignStrip = new JCommandButtonStrip();
		CommandToggleButtonGroup alignGroup = new CommandToggleButtonGroup();

		JCommandToggleButton alignLeftButton = new JCommandToggleButton("",
				new format_justify_left());
		alignLeftButton.getActionModel().setSelected(true);
		alignGroup.add(alignLeftButton);
		alignStrip.add(alignLeftButton);

		JCommandToggleButton alignCenterButton = new JCommandToggleButton("",
				new format_justify_center());
		alignGroup.add(alignCenterButton);
		alignStrip.add(alignCenterButton);

		JCommandToggleButton alignRightButton = new JCommandToggleButton("",
				new format_justify_right());
		alignGroup.add(alignRightButton);
		alignStrip.add(alignRightButton);

		JCommandToggleButton alignFillButton = new JCommandToggleButton("",
				new format_justify_fill());
		alignGroup.add(alignFillButton);
		alignStrip.add(alignFillButton);

		//statusBar.add(alignStrip);

		final Map<Integer, Boolean> selection = new TreeMap<Integer, Boolean>();
		/*
		statusBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					processPopup(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					processPopup(e);
				}
			}

			private void processPopup(MouseEvent e) {
				Point pt = new Point(e.getPoint());
				SwingUtilities.convertPointToScreen(pt, statusBar);
				final JCommandPopupMenu menu = new JCommandPopupMenu();
				for (int i = 0; i < 10; i++) {
					final int ind = i;
					final JCommandToggleMenuButton button = new JCommandToggleMenuButton(
							"option " + i, null);
					button.getActionModel().addActionListener(
							new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									selection.put(ind, button.getActionModel()
											.isSelected());
								}
							});
					if (selection.containsKey(i)) {
						button.getActionModel().setSelected(selection.get(i));
					}
					menu.addMenuButton(button);
					if (i == 3 || i == 7) {
						menu.addMenuSeparator();
					}
				}
				menu.setToDismissOnChildClick(false);

				Popup popup = PopupFactory.getSharedInstance().getPopup(
						statusBar, menu, pt.x,
						pt.y - menu.getPreferredSize().height);
				PopupPanelManager.defaultManager().addPopup(statusBar, popup,
						menu);

				PopupListener tracker = new PopupListener() {
					@Override
					public void popupShown(PopupEvent event) {
					}

					@Override
					public void popupHidden(PopupEvent event) {
						if (event.getSource() == menu) {
							System.out.print("Current selection: ");
							for (Map.Entry<Integer, Boolean> e : selection
									.entrySet()) {
								if (e.getValue()) {
									System.out.print(e.getKey() + " ");
								}
							}
							System.out.println();
						}
						PopupPanelManager.defaultManager().removePopupListener(
								this);
					}
				};
				PopupPanelManager.defaultManager().addPopupListener(tracker);
			}
		});
*/
		this.add(statusBar, BorderLayout.SOUTH);
	}

	
}
