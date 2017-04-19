/*
 * Copyright 2010-2015 Institut Pasteur.
 * 
 * This file is part of Icy.
 * 
 * Icy is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Icy is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Icy. If not, see <http://www.gnu.org/licenses/>.
 */
package zlbyzc.gui;


import icy.image.ImageUtil;
import icy.resource.ResourceUtil;
import zlbyzc.BasicRibbon;
import zlbyzc.gui.test.OutputThread;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import db.dbException;

/**
 * Animated ICY Logo.
 * 
 * @author Fab & Stephane
 */
public class SplashScreenFrame extends JFrame 
{
    /**
     * 
     */
    private static final long serialVersionUID = -519109094312389176L;

    public class SplashPanel extends JPanel
    {
        private static final long serialVersionUID = -6955085853269659076L;
        
        private static final int DEFAULT_WIDTH = 960;
        private static final int DEFAULT_HEIGTH = 300;

        private BufferedImage image;

        public SplashPanel()
        {
            
        	image=null;
            try {
				image = ImageIO.read(SplashPanel.class.getResource("/img/titleBar.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            if (image != null)
                setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            else
                setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGTH));
        }

        @Override
        public void paint(Graphics g)
        {
            super.paint(g);

            if (image != null)
                g.drawImage(image, 0, 0, this);
        }
    }

   
    private final SplashPanel splash;
    private JProgressBar progressBar;
	//private Timer timer;
	//private HibernateSwingWorker task;
    
	private Object lock;
    /**
	 * 
	 */
    public SplashScreenFrame(Object lock2)
    {
        super("zlbyzc");

        splash = new SplashPanel();
        this.lock=lock2;
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        progressBar.setStringPainted(true);
        progressBar.setString( "正在加载数据库");
        //timer = new Timer();  
        
        setUndecorated(true);
        setLayout(new BorderLayout());
        add(splash,BorderLayout.CENTER);
        add(progressBar,BorderLayout.SOUTH);
        setIconImages(ResourceUtil.getIcyIconImages());
        pack();
        
        setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    private class TwoWorker extends SwingWorker<Double, Double> {

        private static final int N = 100;        
        double x = 1;
        @Override
        protected Double doInBackground() throws Exception {
            for (int i = 1; i <= N; i++) {
                x = i;
                setProgress(i );
                publish(Double.valueOf(i));
                Thread.sleep(100); // simulate latency
            }
            return Double.valueOf(x);
        }
        /*@Override
        protected void process(List<Double> chunks) {
            for (double d : chunks) {
            	progressBar.setValue((int)(d));
            }
        }*/
    }
    public void startInit(){
    	progressBar.setIndeterminate(true);
    	/*task = new TwoWorker();    	
    	task.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if ("progress".equals(e.getPropertyName())) {
                    progressBar.setIndeterminate(true);
                    //progressBar.setValue((Integer) e.getNewValue());
                    //System.out.println(e.getNewValue());
                }
            }
        });
        task.execute();*/
    	Thread thread2 = new Thread(new  LoadThread( lock));
    	
    	thread2.start();    	
        
    }
    class LoadThread implements Runnable {

     
        private Object lock;
        
        public LoadThread( Object lock2) {
            super();
            
            this.lock = lock2;
        }

        public void run() {
            //while(true)
			{
			    synchronized(lock){			    			
					try {
						zlbyzc.sub3.analysis.services.SwotTaskDAO swotTaskDAO = new zlbyzc.sub3.analysis.services.SwotTaskDAO();
						swotTaskDAO.getAllSwotTasks();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						//System.out.println("=============dbException==========");
						JOptionPane.showMessageDialog(null, "数据库连接出错，请检查\n程序左上角菜单-》参数设置可修改数据库连接配置",
								"注意！",JOptionPane.ERROR_MESSAGE);
					}
					lock.notifyAll();
			    	                       
			    }
			}
            
        }
    }
}
