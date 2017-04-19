/*
 * Copyright (c) 2005-2010 Flamingo / Substance Kirill Grouchnikov. All Rights Reserved.
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.pushingpixels.flamingo.api.common.*;
import org.pushingpixels.flamingo.api.common.CommandButtonDisplayState;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.lafwidget.LafWidgetRepository;
import org.pushingpixels.lafwidget.LafWidgetSupport;
import org.pushingpixels.substance.api.ComponentState;
import org.pushingpixels.substance.api.DecorationAreaType;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.SubstanceConstants.SubstanceWidgetType;
import zlbyzc.gui.ChallengerLessDeepSkin;

import org.pushingpixels.substance.api.skin.BusinessBlueSteelSkin;
import org.pushingpixels.substance.api.skin.OfficeBlack2007Skin;
import org.pushingpixels.substance.flamingo.ribbon.gallery.oob.SubstanceRibbonTask;
import org.pushingpixels.substance.internal.utils.SubstanceWidgetManager;

import zlbyzc.LookAndFeelSwitcher;
import zlbyzc.gui.ImageTask;
import zlbyzc.gui.SplashScreenFrame;
import zlbyzc.gui.TitlePane;

//import test.common.IconWrapperResizableIcon;


import com.jgoodies.forms.builder.DefaultFormBuilder;

public class guiSubstanceRibbon extends BasicRibbon {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 829759884638719070L;
	private JLabel saveLabel;
    public guiSubstanceRibbon() {
    	
        super();
        
        //this.setTitle(messagesRes.getString("guiTitle"));
        //TitlePane.editTitleBar(this, messagesRes.getString("guiTitle"));
    }

	@Override
	public void configureRibbon() {
		
		super.configureRibbon();
		
        LafWidgetSupport lws = LafWidgetRepository.getRepository()
					.getLafSupport();
        final ResizableIcon north = new IconWrapperResizableIcon(lws.getArrowIcon(SwingConstants.NORTH));
        final ResizableIcon south = new IconWrapperResizableIcon(lws.getArrowIcon(SwingConstants.SOUTH));
        final JCommandButton toggleButton = new JCommandButton("", north);
        toggleButton.setDisplayState(CommandButtonDisplayState.SMALL);
        toggleButton.setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
        toggleButton.getActionModel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean state = !getRibbon().isMinimized();
                getRibbon().setMinimized(state);
                toggleButton.setIcon(state?south:north);
            }
        });
        getRibbon().addHelpPanelComponent(toggleButton);
        //LookAndFeelSwitcher.setLookAndFeel("JGoodies PlasticXP", this);
        //LookAndFeelSwitcher.setFont();
	}

	@Override
	protected void configureStatusBar() {
		super.configureStatusBar();
		SubstanceLookAndFeel.setDecorationType(this.statusBar,
				DecorationAreaType.FOOTER);
	}

	
	/**
	 * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
	 */
	@SuppressWarnings("unchecked")
	public static void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}
	public static void main(String[] args) {
		
		UIManager.installLookAndFeel("JGoodies Plastic",
				"com.jgoodies.looks.plastic.PlasticLookAndFeel");
		UIManager.installLookAndFeel("JGoodies PlasticXP",
				"com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
		UIManager.installLookAndFeel("JGoodies Plastic3D",
				"com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
		UIManager.installLookAndFeel("JGoodies Windows",
				"com.jgoodies.looks.windows.WindowsLookAndFeel");


		UIManager.installLookAndFeel("Liquid",
				"com.birosoft.liquid.LiquidLookAndFeel");

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
		JDialog.setDefaultLookAndFeelDecorated(true);
		synchronized(lock){            
			 try {
				 	//lock.wait();
					UIManager.setLookAndFeel(new MetalLookAndFeel());
				} catch (Exception ignored) {
				}
        }
		SwingUtilities.invokeLater(new Runnable() {
			@Override
            public void run() {
				
				SubstanceLookAndFeel.setSkin(new BusinessBlueSteelSkin());
				
				if(zUtil.getOSname()!=zUtil.EPlatform.Windows)
					InitGlobalFont(new Font("Dialog", Font.PLAIN, 16));
				guiSubstanceRibbon c = new guiSubstanceRibbon();
				
				c.configureRibbon();
				c.applyComponentOrientation(ComponentOrientation
						.getOrientation(Locale.getDefault()));
				Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment()
						.getMaximumWindowBounds();
				c.setPreferredSize(new Dimension(r.width, r.height));
				c.setMinimumSize(new Dimension(r.width / 10, r.height / 2));	
				c.pack();
				c.setLocation(r.x, r.y);
				
				try {
					//zlbyzc.sub3.analysis.services.SwotTaskDAO swotTaskDAO = new zlbyzc.sub3.analysis.services.SwotTaskDAO();		
					//swotTaskDAO.getAllSwotTasks();
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
						//System.out.println("Size " + c.getSize());
					}
				});

//				
				//swotTaskDAO.getSwotTaskByID(1);
	    				
			    
//				c.getRootPane().getInputMap(
//						JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
//						KeyStroke.getKeyStroke("alt shift E"),
//						"installTracingRepaintManager");
//				c.getRootPane().getActionMap().put(
//						"installTracingRepaintManager", new AbstractAction() {
//							@Override
//							public void actionPerformed(ActionEvent e) {
//								RepaintManager
//										.setCurrentManager(new TracingRepaintManager());
//							}
//						});
				
			}
		});
	}

}
