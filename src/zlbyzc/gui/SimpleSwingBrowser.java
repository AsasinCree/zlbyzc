package zlbyzc.gui;

	import com.sun.javafx.application.PlatformImpl;
import com.sun.jna.platform.FileUtils;

import java.awt.BorderLayout;
	import java.awt.Dimension;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
	import javafx.embed.swing.JFXPanel;
	import javafx.scene.Group;
	import javafx.scene.Node;
	import javafx.scene.Scene;
	import javafx.scene.web.WebEngine;
	import javafx.scene.web.WebView;
	import javafx.stage.Stage;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.SwingUtilities;
	  
	/** 
	 * SwingFXWebView 
	 */  
	public class SimpleSwingBrowser extends JPanel {  
	     
	    private Stage stage;  
	    private WebView browser;  
	    private JFXPanel jfxPanel;  
	    private JButton swingButton;  
	    private WebEngine webEngine;  
	    private String url,title;
	  
	    public SimpleSwingBrowser(String _url,String _title){  
	        url=_url;
	        title=_title;
	    	initComponents();  
	        
	    }  
	  
	    public static void main(String ...args){  
	        // Run this later:
	        SwingUtilities.invokeLater(new Runnable() {  
	            @Override
	            public void run() {  
	                final JFrame frame = new JFrame();  
	                SimpleSwingBrowser sb=new SimpleSwingBrowser("file:///D:/liuk/n1/2110/src/zlbyzc/doc/fuzzy.html","Help");
	                frame.getContentPane().add(sb);  
	                 
	                frame.setMinimumSize(new Dimension(640, 480));  
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	                frame.setVisible(true); 
	                //sb.navigate("D:/liuk/n1/2110/src/zlbyzc/doc/fuzzy.html");
	            }  
	        });     
	    }  
	     
	    private void initComponents(){  
	         
	        jfxPanel = new JFXPanel();  
	        createScene();  
	         
	        setLayout(new BorderLayout());  
	        add(jfxPanel, BorderLayout.CENTER);  
	         
	        swingButton = new JButton();  
	        swingButton.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                Platform.runLater(new Runnable() {

	                    @Override
	                    public void run() {
	                        webEngine.reload();
	                    }
	                });
	            }
	        });  
	        swingButton.setText("Reload");  
	         
	        //add(swingButton, BorderLayout.SOUTH);  
	    }     
	    public void navigate(String _url){
	    	 url=_url;
	    	 Platform.runLater(new Runnable() {
	    	        @Override public void run() {
	    	            

	    	        	webEngine.load(url);
	    	        }
	    	    });
	     }
	    /** 
	     * createScene 
	     * 
	     * Note: Key is that Scene needs to be created and run on "FX user thread" 
	     *       NOT on the AWT-EventQueue Thread 
	     * 
	     */  
	    private void createScene() {  
	        PlatformImpl.startup(new Runnable() {  
	            @Override
	            public void run() {  
	                 
	                stage = new Stage();  
	                 
	                stage.setTitle(title);  
	                stage.setResizable(true);  
	             // Set up the embedded browser:
	                browser = new WebView();
	                //Group root = new Group();  
	                Scene scene = new Scene(browser);  
	                stage.setScene(scene);  
	                 
	                
	                webEngine = browser.getEngine();
	                webEngine.load(url);
	                
	                
	                jfxPanel.setScene(scene);  
	            }  
	        });  
	    }
	}