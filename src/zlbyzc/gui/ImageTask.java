package zlbyzc.gui;

import java.awt.BorderLayout;
import java.net.URL;
import java.text.MessageFormat;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
//import javax.swing.JPanel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandMenuButton;
import org.pushingpixels.flamingo.api.common.RichTooltip;
import org.pushingpixels.flamingo.api.common.JCommandButton.CommandButtonKind;
import org.pushingpixels.flamingo.api.common.icon.EmptyResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.common.popup.JCommandPopupMenu;
import org.pushingpixels.flamingo.api.common.popup.JPopupPanel;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelCallback;
import org.pushingpixels.flamingo.api.ribbon.AbstractRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbonComponent;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;

import test.svg.transcoded.edit_paste;
import zlbyzc.sub3.CommandExec;

import java.awt.Graphics;  
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException; 
//import zlbyzc.MainFrame;

//import zlbyzc.gui.welcomePanel;
//import zlbyzc.BasicRibbon.SamplePopupMenu;


public class ImageTask extends RibbonTask{
	private ResourceBundle mesg;
	public static final String NAME = "Start";
    public JCommandButton statsButton;

    public static ResizableIcon getResizableIconFromResource(String resource) {
    	java.net.URL imUrl = ImageTask.class.getResource(resource);
    	//System.out.println(imUrl);
        return ImageWrapperResizableIcon.getIcon(
        		imUrl, 
                new Dimension(46, 46));
    }
    
    private static class ImageRibbonBand extends JRibbonBand
    {
        /**
         * 
         */
        private static final long serialVersionUID = 4431425194428863269L;
        
        public static final String NAME = " ";

        // internal
        JRibbonComponent imageJComp;

        final static ResizableIcon icon=ImageTask.getResizableIconFromResource("/img/space.png");
        ImgBarPanel swingPanel;
        
        public ImageRibbonBand()
        {
            super(NAME, icon);
            intImageRibbonBand();
                     
        }  
        public ImageRibbonBand(ResourceBundle mesg)
        {
        	super(NAME, icon);
            intImageRibbonBand();
            setTitle(mesg.getString("ui.Welcome"));
        }
		private void intImageRibbonBand() {
			swingPanel = new ImgBarPanel();
            swingPanel.setLayout(new BorderLayout());            
            String imgURL = "/img/titleBar.png";  
            swingPanel.setImagePath(imgURL);
            swingPanel.setPreferredSize(new Dimension(swingPanel.getImgWidth(), swingPanel  
                    .getImgHeight()));           
            imageJComp = new JRibbonComponent(swingPanel);
            addRibbonComponent(imageJComp, 3);
            this.setResizePolicies((List) Arrays.asList(
                    new CoreRibbonResizePolicies.None(this.getControlPanel())
                    ));   
		}		      
        
    }
    private static class SubSystemBand extends JRibbonBand{
    	ResourceBundle mesg;
    	JCommandButton statsButton;
        //private JRibbon jr;
        //private RibbonTask tasks;
        
    	final static ResizableIcon stats_icon=ImageTask.getResizableIconFromResource("/img/icons/stats.png");
    	final static ResizableIcon game_icon=ImageTask.getResizableIconFromResource("/img/icons/game_sub.png");
    	final static ResizableIcon risk_icon=ImageTask.getResizableIconFromResource("/img/icons/risk_sub.png");
    	final static ResizableIcon ana_icon=ImageTask.getResizableIconFromResource("/img/icons/analysis_sub.png");
    	final static ResizableIcon judge_icon=ImageTask.getResizableIconFromResource("/img/icons/judge_sub.png");
    	final static ResizableIcon sim_icon=ImageTask.getResizableIconFromResource("/img/icons/sim_sub.png");
    	final static ResizableIcon jc_icon=ImageTask.getResizableIconFromResource("/img/icons/jc-sub.png");
        public SubSystemBand(ResourceBundle mesg, JRibbon jr, LinkedList tasks
        		,CommandExec cmdexec)
        {
        	super("请选择您要使用的子系统", ana_icon);
        	this.mesg=mesg;
    		JCommandButton simButton = new JCommandButton("战略仿真",
    				sim_icon);
    		simButton.addActionListener(new ActionListener() {
    			@Override 
    			public void actionPerformed(ActionEvent e) {
    				cmdexec.dyn_me();
    			}
    		}); 
    		JCommandButton anaButton = new JCommandButton("战略分析",
    				ana_icon);
    		anaButton.addActionListener(new ActionListener() {
    			@Override 
    			public void actionPerformed(ActionEvent e) {
    				//System.out.println("Taskbar statsButton activated");
    				jr.setSelectedTask((RibbonTask)tasks.getFirst());
    				
    				//做一次查询操作，为了解决初次访问数据库卡顿时间
    				SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
    		    	zlbyzc.sub3.analysis.services.SwotTaskDAO swotTaskDAO = new zlbyzc.sub3.analysis.services.SwotTaskDAO();		
    				swotTaskDAO.getAllSwotTasks();
                        }
                    });
    			}
    		});    	
    
    		JCommandButton judgeButton = new JCommandButton("战略评价",
    				judge_icon);
    		judgeButton.addActionListener(new ActionListener() {
    			@Override 
    			public void actionPerformed(ActionEvent e) {
    				//System.out.println("Taskbar statsButton activated");
    				jr.setSelectedTask((RibbonTask)tasks.get(3));
    			}
    		});    	
    		JCommandButton jcButton = new JCommandButton("战略决策",
    				jc_icon);
    		jcButton.addActionListener(new ActionListener() {
    			@Override 
    			public void actionPerformed(ActionEvent e) {
    				//System.out.println("Taskbar statsButton activated");
    				jr.setSelectedTask((RibbonTask)tasks.get(2));
    			}
    		});    	
    		JCommandButton gameButton = new JCommandButton(mesg
    				.getString("game.text"),
    				game_icon);
    		gameButton
    				.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
    		gameButton.addActionListener(new ActionListener() {
    			@Override 
    			public void actionPerformed(ActionEvent e) {
    				jr.setSelectedTask((RibbonTask)tasks.get(1));
    			}
    		});
    		JCommandButton riskButton = new JCommandButton("战略风险管控",
    				risk_icon);
    		riskButton
    				.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
    		riskButton.addActionListener(new ActionListener() {
    			@Override 
    			public void actionPerformed(ActionEvent e) {
    				jr.setSelectedTask((RibbonTask)tasks.get(4));
    			}
    		});    		
    		
    		statsButton = new JCommandButton(mesg
    				.getString("stats.text"),
    				stats_icon);
    		statsButton
    				.setCommandButtonKind(CommandButtonKind.ACTION_ONLY);
    		statsButton.addActionListener(new ActionListener() {
    			@Override 
    			public void actionPerformed(ActionEvent e) {
    				//System.out.println("Taskbar statsButton activated");
    				jr.setSelectedTask((RibbonTask)tasks.getLast());
    			}
    		});    	

    		statsButton.setActionRichTooltip(new RichTooltip(mesg
    				.getString("stats.text"), mesg
    				.getString("stats.tooltip.actionParagraph1")));
    		statsButton.setPopupRichTooltip(new RichTooltip(mesg
    				.getString("stats.text"), mesg
    				.getString("stats.tooltip.popupParagraph1")));
    		statsButton.setActionKeyTip("1");
    		addCommandButton(anaButton, RibbonElementPriority.TOP);
    		addCommandButton(gameButton, RibbonElementPriority.TOP);
    		addCommandButton(jcButton, RibbonElementPriority.TOP);
    		addCommandButton(judgeButton, RibbonElementPriority.MEDIUM);
    		addCommandButton(simButton, RibbonElementPriority.MEDIUM);
    		addCommandButton(riskButton, RibbonElementPriority.MEDIUM);
    		addCommandButton(statsButton, RibbonElementPriority.MEDIUM);
            setResizePolicies((List) Arrays.asList(
                    new CoreRibbonResizePolicies.None(this.getControlPanel())
                    ));   

        }    	

    }
	public ImageTask() {		
        super(NAME, new ImageRibbonBand()
        		//,zlbyzc.gui.SubstanceRibbonBand.getSubstanceRibbonTask()
        		);        
	}
	public ImageTask(ResourceBundle mesg, JRibbon jr, LinkedList tasks,
			CommandExec cmdexec) {		
        super(NAME, new ImageRibbonBand(mesg),
        		new SubSystemBand(mesg,  jr,  tasks,cmdexec)
        		//,zlbyzc.gui.SubstanceRibbonBand.getSubstanceRibbonTask(mesg)
        		); 
        this.mesg=mesg;//
        setTitle(mesg.getString("ui.Start"));
	}
}
