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

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Window; 

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
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
import org.pushingpixels.flamingo.api.common.popup.PopupPanelManager.PopupEvent;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelManager.PopupListener;
import org.pushingpixels.flamingo.api.ribbon.*;
import org.pushingpixels.flamingo.api.ribbon.resize.*;
import org.pushingpixels.flamingo.internal.utils.RenderingUtils;

import zlbyzc.LookAndFeelSwitcher;
import zlbyzc.gui.ImageTask;
import zlbyzc.gui.SpringUtilities;
import zlbyzc.sub3.CommandExec;
import zlbyzc.sub3.stats.statPy;
import test.svg.transcoded.*;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

import icy.resource.ResourceUtil;

import java.net.MalformedURLException;
import java.net.URL;
 
import static javafx.concurrent.Worker.State.FAILED;

public class BasicRibbon extends JRibbonFrame {
	protected Locale currLocale;
    protected RibbonTask statsTask ;
	protected ResourceBundle resourceBundle;	 
	protected ResourceBundle messagesRes;
	JSplitPane mainPane;
	private final mainDesk maindesktop;
	JPanel centerPanel;
	protected CommandExec cmdexec;
	public mainDesk getDesktopPane()
    {
        return maindesktop;
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

	protected JRibbonBand getDocumentBand() {
		JRibbonBand result = new JRibbonBand("文档",
				new applications_office(), new ExpandActionListener());
		result.setExpandButtonKeyTip("FY");
		result.setCollapsedStateKeyTip("ZD");

		result.startGroup();
		
		JCommandButton localFolderButton = new JCommandButton("打开脚本文件", new folder());
		result.addCommandButton(localFolderButton, RibbonElementPriority.TOP);
		

		JCommandButton savedFolderButton = new JCommandButton("保存脚本文件", new folder_saved_search());
		result.addCommandButton(savedFolderButton, RibbonElementPriority.TOP);

		result.startGroup();

		JCommandButton docNewButton = new JCommandButton("新建脚本", new document_new());
		result.addCommandButton(docNewButton, RibbonElementPriority.MEDIUM);

		JCommandButton docOpenButton = new JCommandButton("最近使用文件", new document_open());
		result.addCommandButton(docOpenButton, RibbonElementPriority.MEDIUM);

		JCommandButton docSaveButton = new JCommandButton("保存所有文件", new document_save());
		result.addCommandButton(docSaveButton, RibbonElementPriority.MEDIUM);

		JCommandButton docPrintButton = new JCommandButton("打印", new document_print());
		result.addCommandButton(docPrintButton, RibbonElementPriority.MEDIUM);

		JCommandButton docPrintPreviewButton = new JCommandButton("打印预览",
				new document_print_preview());
		result.addCommandButton(docPrintPreviewButton,
				RibbonElementPriority.MEDIUM);

		JCommandButton docPropertiesButton = new JCommandButton("属性",
				new document_properties());
		result.addCommandButton(docPropertiesButton,
				RibbonElementPriority.MEDIUM);

		result.setResizePolicies(CoreRibbonResizePolicies
				.getCorePoliciesRestrictive(result));

		return result;
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
	
	protected JRibbonBand getAnaBand() {
		
		//做一次查询操作，为了解决初次访问数据库卡顿时间
		zlbyzc.sub3.analysis.services.SwotTaskDAO swotTaskDAO = new zlbyzc.sub3.analysis.services.SwotTaskDAO();		
		swotTaskDAO.getAllSwotTasks();		
		
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

		ResizableIcon py_icon=ImageTask.getResizableIconFromResource("/img/icons/fullgame.png");
		JCommandButton pythonButton = new JCommandButton("完全信息博弈博弈", py_icon);
		ResizableIcon py_icon2=ImageTask.getResizableIconFromResource("/img/icons/nofullgame.png");
		JCommandButton pythonButton2 = new JCommandButton("不完全信息博弈博弈", py_icon2);
		statsBand.addCommandButton(pythonButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton2, RibbonElementPriority.TOP);

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
		JCommandButton pythonButton = new JCommandButton("Python", py_icon);
		ResizableIcon r_icon=ImageTask.getResizableIconFromResource("/img/icons/rlang.png");
		JCommandButton rButton = new JCommandButton("R", r_icon);
		pythonButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartPy();	
			}
		});
		rButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				cmdexec.execStartR();	
			}
		});
		
		statsBand.addCommandButton(pythonButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(rButton, RibbonElementPriority.TOP);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}	

	protected JRibbonBand getStatsRunBand() {
		ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/stats.png");
		JRibbonBand statsBand = new JRibbonBand("脚本运行", stats_icon,
				new ExpandActionListener());
		statsBand.setExpandButtonKeyTip("FO");
		RichTooltip expandRichTooltip = new RichTooltip();
		expandRichTooltip.setTitle(resourceBundle
				.getString("Clipboard.textBandTitle"));
		expandRichTooltip.addDescriptionSection(resourceBundle
				.getString("Clipboard.textBandTooltipParagraph1"));
		statsBand.setExpandButtonRichTooltip(expandRichTooltip);
		statsBand.setCollapsedStateKeyTip("ZC");

		ResizableIcon py_icon=ImageTask.getResizableIconFromResource("/img/icons/run.png");
		JCommandButton pythonButton = new JCommandButton("运行", py_icon);
		ResizableIcon r_icon=ImageTask.getResizableIconFromResource("/img/icons/runas.png");
		JCommandButton rButton = new JCommandButton("以制定参数运行", r_icon);
		ResizableIcon im_icon=ImageTask.getResizableIconFromResource("/img/icons/data-import.png");
		JCommandButton imButton = new JCommandButton("导入数据", im_icon);
		ResizableIcon ex_icon=ImageTask.getResizableIconFromResource("/img/icons/export-data.png");
		JCommandButton exButton = new JCommandButton("导出数据", ex_icon);
		

		statsBand.addCommandButton(imButton, RibbonElementPriority.TOP);
		
		statsBand.addCommandButton(pythonButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(rButton, RibbonElementPriority.TOP);
		statsBand.addCommandButton(exButton, RibbonElementPriority.TOP);
		
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
		ResizableIcon py_icon3=ImageTask.getResizableIconFromResource("/img/icons/jctree.png");
		JCommandButton pythonButton3 = new JCommandButton("决策树", py_icon3);
		
		
		statsBand.addCommandButton(pythonButton2, RibbonElementPriority.TOP);
		statsBand.addCommandButton(pythonButton3, RibbonElementPriority.TOP);

		List<RibbonBandResizePolicy> resizePolicies = new ArrayList<RibbonBandResizePolicy>();
		resizePolicies.add(new CoreRibbonResizePolicies.Mirror(statsBand
				.getControlPanel()));
		resizePolicies.add(new CoreRibbonResizePolicies.Mid2Low(statsBand
				.getControlPanel()));
		statsBand.setResizePolicies(resizePolicies);

		return statsBand;
	}	

	
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
		JCommandButton lecButton = new JCommandButton("LEC法", lec_icon);
		ResizableIcon riskMat_icon=ImageTask.getResizableIconFromResource("/img/icons/riskMat.png");
		JCommandButton riskMatButton = new JCommandButton("风险矩阵法", riskMat_icon);
		
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
	

	protected RibbonContextualTaskGroup group1;
	protected RibbonContextualTaskGroup group2;

	protected JPanel statusBar;

	private JRibbonBand paragraphBand;

	public BasicRibbon() {
		super();
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
		
		JRibbonBand riskBand = this.getRiskBand();
		JRibbonBand anaBand = this.getAnaBand();
		JRibbonBand judgeBand = this.getJudgeBand();
		JRibbonBand jcBand = this.getJcBand();
		JRibbonBand gameBand = this.getGameBand();
		RibbonTask anaTask = new RibbonTask("战略分析", anaBand);
		
		RibbonTask riskTask = new RibbonTask("战略风险管控", riskBand);
		RibbonTask jcTask = new RibbonTask("战略决策", jcBand);
		RibbonTask judgeTask = new RibbonTask("战略评价", judgeBand);
		RibbonTask gameTask = new RibbonTask("战略博弈", gameBand);
		
		
		JRibbonBand statsBand = this.getStatsBand();
		JRibbonBand statsBandRun = this.getStatsRunBand();
		JRibbonBand statsBandDoc = this.getDocumentBand();
		JRibbonBand statsBandFind = this.getFindBand();
		JRibbonBand statsBandClip =this.getClipboardBand();
		statsTask = new RibbonTask("战略统计分析", statsBand,statsBandDoc,statsBandClip,statsBandFind,statsBandRun);
		
		LinkedList taskList=new LinkedList();
		taskList.add(anaTask);
		taskList.add(gameTask);
		taskList.add(jcTask);
		taskList.add(judgeTask);
		taskList.add(riskTask);
		taskList.add(statsTask);
		ImageTask task=new ImageTask(messagesRes,this.getRibbon(),taskList);
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
						JOptionPane.showMessageDialog(BasicRibbon.this,
								"Help button clicked");
					}
				});
        this.getRibbon().setHelpRichTooltip(new RichTooltip("Don't Get Excited", "This isn't the help documentation you are looking for."));

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

		configureTaskBar();

		// application menu
		configureApplicationMenu();

		JPanel controlPanel = new JPanel();
		controlPanel.setBorder(new EmptyBorder(20, 0, 0, 5));
		controlPanel.setBackground(new java.awt.Color(225, 225, 225));
		FormLayout lm = new FormLayout("right:pref, 4dlu, fill:pref:grow", "");
		DefaultFormBuilder builder = new DefaultFormBuilder(lm, controlPanel);		
		this.configureControlPanel(builder);
		
		JPanel leftPanel = new JPanel(new SpringLayout());
		
		final JFXPanel jfxPanel = new JFXPanel();
		
		builder.append(jfxPanel);
		Platform.runLater(() -> {
		    WebView webView = new WebView();
		    //int width = getParent().getWidth();
            //int height = getParent().getHeight();

            webView.setMinSize(300, 800);
            webView.setPrefSize(300, 1200);
            
            webView.getEngine().load(ImageTask.class.getResource("/doc/3.htm").toExternalForm());
		    Scene wwwscene=new Scene(webView);
		    jfxPanel.setScene(wwwscene);		    
		    Platform.setImplicitExit(false);
		    
		});
		
		leftPanel.add(new Label("Help:"));
		leftPanel.add(jfxPanel);
		leftPanel.add(controlPanel);
		SpringUtilities.makeCompactGrid(leftPanel, //parent
                3, 1,
                3, 3,  //initX, initY
                3, 3); //xPad, yPad
		centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        
        centerPanel.add(maindesktop, BorderLayout.CENTER);
        
		mainPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, null);
		mainPane.setContinuousLayout(true);
		mainPane.setRightComponent(centerPanel);
		mainPane.setResizeWeight(1);
		mainPane.setDividerLocation(300);
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

		RibbonApplicationMenuEntrySecondary amEntrySaveAsWord = new RibbonApplicationMenuEntrySecondary(
				new x_office_document(), resourceBundle
						.getString("AppMenuSaveAs.word.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySaveAsWord.setDescriptionText(resourceBundle
				.getString("AppMenuSaveAs.word.description"));
		amEntrySaveAsWord.setActionKeyTip("W");
		RibbonApplicationMenuEntrySecondary amEntrySaveAsHtml = new RibbonApplicationMenuEntrySecondary(
				new text_html(), resourceBundle
						.getString("AppMenuSaveAs.html.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySaveAsHtml.setDescriptionText(resourceBundle
				.getString("AppMenuSaveAs.html.description"));
		amEntrySaveAsHtml.setEnabled(false);
		amEntrySaveAsHtml.setActionKeyTip("H");
		RibbonApplicationMenuEntrySecondary amEntrySaveAsOtherFormats = new RibbonApplicationMenuEntrySecondary(
				new document_save_as(), resourceBundle
						.getString("AppMenuSaveAs.other.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySaveAsOtherFormats.setDescriptionText(resourceBundle
				.getString("AppMenuSaveAs.other.description"));
		amEntrySaveAsOtherFormats.setActionKeyTip("O");

		amEntrySaveAs
				.addSecondaryMenuGroup(resourceBundle
						.getString("AppMenuSaveAs.secondary.textGroupTitle1"),
						amEntrySaveAsWord, amEntrySaveAsHtml,
						amEntrySaveAsOtherFormats);

		RibbonApplicationMenuEntryPrimary amEntryPrint = new RibbonApplicationMenuEntryPrimary(
				new document_print(), resourceBundle
						.getString("AppMenuPrint.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked printing document");
					}
				}, CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
		amEntryPrint.setActionKeyTip("P");
		amEntryPrint.setPopupKeyTip("W");

		RibbonApplicationMenuEntrySecondary amEntryPrintSelect = new RibbonApplicationMenuEntrySecondary(
				new printer(), resourceBundle
						.getString("AppMenuPrint.print.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntryPrintSelect.setDescriptionText(resourceBundle
				.getString("AppMenuPrint.print.description"));
		amEntryPrintSelect.setActionKeyTip("P");
		RibbonApplicationMenuEntrySecondary amEntryPrintDefault = new RibbonApplicationMenuEntrySecondary(
				new document_print(), resourceBundle
						.getString("AppMenuPrint.quick.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntryPrintDefault.setDescriptionText(resourceBundle
				.getString("AppMenuPrint.quick.description"));
		amEntryPrintDefault.setActionKeyTip("Q");
		RibbonApplicationMenuEntrySecondary amEntryPrintPreview = new RibbonApplicationMenuEntrySecondary(
				new document_print_preview(), resourceBundle
						.getString("AppMenuPrint.preview.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntryPrintPreview.setDescriptionText(resourceBundle
				.getString("AppMenuPrint.preview.description"));
		amEntryPrintPreview.setActionKeyTip("V");

		amEntryPrint.addSecondaryMenuGroup(resourceBundle
				.getString("AppMenuPrint.secondary.textGroupTitle1"),
				amEntryPrintSelect, amEntryPrintDefault, amEntryPrintPreview);

		RibbonApplicationMenuEntrySecondary amEntryPrintMemo = new RibbonApplicationMenuEntrySecondary(
				new text_x_generic(), resourceBundle
						.getString("AppMenuPrint.memo.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntryPrintMemo.setActionKeyTip("M");

		amEntryPrint.addSecondaryMenuGroup(resourceBundle
				.getString("AppMenuPrint.secondary.textGroupTitle2"),
				amEntryPrintMemo);

		RibbonApplicationMenuEntryPrimary amEntrySend = new RibbonApplicationMenuEntryPrimary(
				new mail_forward(), resourceBundle
						.getString("AppMenuSend.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked sending document");
					}
				}, CommandButtonKind.POPUP_ONLY);
		amEntrySend.setPopupKeyTip("D");

		RibbonApplicationMenuEntrySecondary amEntrySendMail = new RibbonApplicationMenuEntrySecondary(
				new mail_message_new(), resourceBundle
						.getString("AppMenuSend.email.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySendMail.setDescriptionText(resourceBundle
				.getString("AppMenuSend.email.description"));
		amEntrySendMail.setActionKeyTip("E");
		RibbonApplicationMenuEntrySecondary amEntrySendHtml = new RibbonApplicationMenuEntrySecondary(
				new text_html(), resourceBundle
						.getString("AppMenuSend.html.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySendHtml.setDescriptionText(resourceBundle
				.getString("AppMenuSend.html.description"));
		amEntrySendHtml.setActionKeyTip("H");
		RibbonApplicationMenuEntrySecondary amEntrySendDoc = new RibbonApplicationMenuEntrySecondary(
				new x_office_document(), resourceBundle
						.getString("AppMenuSend.word.text"), null,
				CommandButtonKind.ACTION_ONLY);
		amEntrySendDoc.setDescriptionText(resourceBundle
				.getString("AppMenuSend.word.description"));
		amEntrySendDoc.setActionKeyTip("W");
		RibbonApplicationMenuEntrySecondary amEntrySendWireless = new RibbonApplicationMenuEntrySecondary(
				new network_wireless(), resourceBundle
						.getString("AppMenuSend.wireless.text"), null,
				CommandButtonKind.POPUP_ONLY);
		amEntrySendWireless.setPopupKeyTip("X");

		amEntrySendWireless.setPopupCallback(new PopupPanelCallback() {
			@Override
			public JPopupPanel getPopupPanel(JCommandButton commandButton) {
				JCommandPopupMenu wirelessChoices = new JCommandPopupMenu();

				JCommandMenuButton wiFiMenuButton = new JCommandMenuButton(
						resourceBundle
								.getString("AppMenuSend.wireless.wifi.text"),
						new EmptyResizableIcon(16));
				wiFiMenuButton.setActionKeyTip("W");
				wiFiMenuButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("WiFi activated");
					}
				});
				wirelessChoices.addMenuButton(wiFiMenuButton);

				JCommandMenuButton blueToothMenuButton = new JCommandMenuButton(
						resourceBundle
								.getString("AppMenuSend.wireless.bluetooth.text"),
						new EmptyResizableIcon(16));
				blueToothMenuButton.setActionKeyTip("B");
				blueToothMenuButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("BlueTooth activated");
					}
				});
				wirelessChoices.addMenuButton(blueToothMenuButton);
				return wirelessChoices;
			}
		});

		amEntrySendWireless.setDescriptionText(resourceBundle
				.getString("AppMenuSend.wireless.description"));

		amEntrySend.addSecondaryMenuGroup(resourceBundle
				.getString("AppMenuSend.secondary.textGroupTitle1"),
				amEntrySendMail, amEntrySendHtml, amEntrySendDoc,
				amEntrySendWireless);

		RibbonApplicationMenuEntryPrimary amEntryExit = new RibbonApplicationMenuEntryPrimary(
				new system_log_out(), resourceBundle
						.getString("AppMenuExit.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				}, CommandButtonKind.ACTION_ONLY);
		amEntryExit.setActionKeyTip("X");

		RibbonApplicationMenu applicationMenu = new RibbonApplicationMenu();
		applicationMenu.addMenuEntry(amEntryNew);
		applicationMenu.addMenuEntry(amEntryOpen);
		applicationMenu.addMenuEntry(amEntrySave);
		applicationMenu.addMenuEntry(amEntrySaveAs);
		applicationMenu.addMenuSeparator();
		applicationMenu.addMenuEntry(amEntryPrint);
		applicationMenu.addMenuEntry(amEntrySend);
		applicationMenu.addMenuSeparator();
		applicationMenu.addMenuEntry(amEntryExit);

		applicationMenu
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
						targetPanel.add(openHistoryPanel, BorderLayout.CENTER);
					}
				});

		RibbonApplicationMenuEntryFooter amFooterProps = new RibbonApplicationMenuEntryFooter(
				new document_properties(), resourceBundle
						.getString("AppMenuOptions.text"),
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Invoked Options");
					}
				});
		RibbonApplicationMenuEntryFooter amFooterExit = new RibbonApplicationMenuEntryFooter(
				new system_log_out(), resourceBundle
						.getString("AppMenuExit.text"), new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
		amFooterExit.setEnabled(true);
		applicationMenu.addFooterEntry(amFooterProps);
		applicationMenu.addFooterEntry(amFooterExit);

		this.getRibbon().setApplicationMenu(applicationMenu);

		RichTooltip appMenuRichTooltip = new RichTooltip();
		appMenuRichTooltip.setTitle(resourceBundle
				.getString("AppMenu.tooltip.title"));
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
	protected void configureControlPanel(DefaultFormBuilder builder) {
		

		final JCheckBox group1Visible = new JCheckBox("visible");
		final JCheckBox group2Visible = new JCheckBox("visible");
		group1Visible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						getRibbon().setVisible(group1,
								group1Visible.isSelected());
					}
				});
			}
		});
		group2Visible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						getRibbon().setVisible(group2,
								group2Visible.isSelected());
					}
				});
			}
		});
		builder.append("Group 1", group1Visible);
		builder.append("Group 2", group2Visible);

		builder.append("Look & feel", LookAndFeelSwitcher
				.getLookAndFeelSwitcher(this));
		JButton changeParagraph = new JButton("change");

		builder.append("Change 'Paragraph'", changeParagraph);
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

		JFrame.setDefaultLookAndFeelDecorated(true);
		
		System.out.println(new File(BasicRibbon.class.getResource("/").getFile())  
		        .getAbsolutePath());
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (Exception ignored) {
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
				c.setPreferredSize(new Dimension(r.width, r.height / 2));
				c.setMinimumSize(new Dimension(r.width / 10, r.height / 2));
				c.pack();
				c.setLocation(r.x, r.y);
				c.setVisible(true);
				c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
		statusBar.add(helper);

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

		statusBar.add(alignStrip);

		final Map<Integer, Boolean> selection = new TreeMap<Integer, Boolean>();
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

		this.add(statusBar, BorderLayout.SOUTH);
	}

	
}
