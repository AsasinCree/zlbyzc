package zlbyzc.gui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;
import zlbyzc.BasicRibbon;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;


/**
 * SwingFXWebView
 */
public class SwingFXWebView extends JPanel {

   /**
	 * 
	 */
	private static final long serialVersionUID = 6963341797923397350L;
private JFXPanel jfxPanel = null;
   private WebView webView;
   private WebEngine webEngine;
   private static final int PANEL_WIDTH_INT = 675;
   private static final int PANEL_HEIGHT_INT = 400;
   private JLabel lblStatus = new JLabel();
   private JProgressBar progressBar = new JProgressBar();   
   private BasicRibbon BR;

   /**
    * There are some restrictions related to JFXPanel. As a Swing component, it
    * should only be accessed from the event dispatch thread, except the 
    * setScene(javafx.scene.Scene) method, which can be called either on the 
    * event dispatch thread or on the JavaFX application thread. 
    */
   public SwingFXWebView() {
	   BR=null;
       SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               Platform.setImplicitExit(false);
                initAndShowGUI();
            }
        });
      
   }
   public SwingFXWebView(BasicRibbon bR) {
	   this();
	   BR=bR;
	   
}
public WebEngine getWebEngine(){
	   return webEngine;
   }
   public static void main(String ...args){  
       // Run this later:
       SwingUtilities.invokeLater(new Runnable() {  
           @Override
           public void run() {  
               final JFrame frame = new JFrame();  
               SwingFXWebView sb=new SwingFXWebView();
               frame.getContentPane().add(sb);  
                
               frame.setMinimumSize(new Dimension(640, 480));  
               frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
               frame.setVisible(true); 
               sb.navigate("http://127.0.0.1:8888");
               sb.executeScript("window",1);
           }  
       });     
   }  
   private void initAndShowGUI() {     
      jfxPanel = new JFXPanel();
      progressBar.setPreferredSize(new Dimension(150, 18));
      progressBar.setStringPainted(true);
      
      JPanel statusBar = new JPanel(new BorderLayout(5, 0));
      statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
      statusBar.add(lblStatus, BorderLayout.CENTER);
      statusBar.add(progressBar, BorderLayout.EAST);

       setLayout(new BorderLayout());
       add(jfxPanel, BorderLayout.CENTER);     
       add(statusBar, BorderLayout.SOUTH);
       
       

      Platform.runLater(new Runnable() {
         @Override
         public void run() {
            initFX();
         }
      });
   }

   private void initFX() {
      // This method is invoked on JavaFX thread
      Scene scene = createScene();
      jfxPanel.setScene(scene);
   }

   /**
    * createScene
    *    
    * Note: The Key ISSUE is that Scene needs to be created and run on 
    * "FX user thread" and NOT on the AWT-EventQueue Thread
    *    
*/
    
    
   private Scene createScene() {

      // Set up the embedded browser:
//	   Font.loadFont(
//			   getClass().getResource("msyh.ttf").toExternalForm(), 
//			      19
//			    );
      webView = new WebView();
      webView.setPrefSize(700, 500);
      Double widthDouble = new Integer(PANEL_WIDTH_INT).doubleValue();
      Double heightDouble = new Integer(PANEL_HEIGHT_INT).doubleValue();
      webView.setMinSize(widthDouble, heightDouble);
      webView.setPrefSize(widthDouble, heightDouble);
      webEngine = webView.getEngine();
      //System.out.println(getClass().getResource("style_html.css").toExternalForm());
      //webEngine.setUserStyleSheetLocation(getClass().getResource("style_html.css").toExternalForm());
      registerListeners();
      //webEngine.setUserStyleSheetLocation("data:,body { font-family:'Microsoft YaHei'; font-size:20;}");
      Scene scene = new Scene(webView);
      return (scene);
   }
   
    private void registerListeners() {
       
        webEngine.titleProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, 
                                    String oldValue, final String newValue) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override public void run() {
                            updateUI();
                        }
                    });
                }
      });
      //handle popup windows  
    /*  webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() { // todo should create a new tab.
         @Override
         public WebEngine call(PopupFeatures popupFeatures) {
            Stage popupStage = new Stage();
            final WebView popupWebView = new WebView();
            final Scene popupScene = new Scene(popupWebView);
            popupStage.setScene(popupScene);
            popupStage.setResizable(popupFeatures.isResizable());
            popupWebView.prefWidthProperty().bind(popupScene.widthProperty());
            popupWebView.prefHeightProperty().bind(popupScene.heightProperty());
            popupStage.show();

            return popupWebView.getEngine();
         }
      });*/
      
      webEngine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
                    @Override public void handle(final WebEvent<String> event) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override public void run() {
                                lblStatus.setText(event.getData());
                            }
                        });
                    }
                });
      
      webEngine.getLoadWorker().workDoneProperty().addListener(new ChangeListener<Number>() {
         @Override
         public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) {
            SwingUtilities.invokeLater(new Runnable() {
               @Override
               public void run() {
                  progressBar.setValue(newValue.intValue());
               }
            });
         }
      });

      webEngine.getLoadWorker()
              .exceptionProperty()
              .addListener(new ChangeListener<Throwable>() {
         public void changed(ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) {
            if (webEngine.getLoadWorker().getState() == FAILED) {
               SwingUtilities.invokeLater(new Runnable() {
                  @Override
                  public void run() {
                     JOptionPane.showMessageDialog(
                             jfxPanel,
                             (value != null)
                             ? webEngine.getLocation() + "\n" + value.getMessage()
                             : webEngine.getLocation() + "\nUnexpected error.",
                             "Loading error...",
                             JOptionPane.ERROR_MESSAGE);
                  }
               });
            }
         }
      });

      webEngine.locationProperty().addListener(new ChangeListener<String>() {
         @Override
         public void changed(ObservableValue<? extends String> observableValue, String oldLoc, String newLoc) {
        	String patternEdit = "http(s?)://(.*)/edit/(.*)";
     		String patternNote = "http(s?)://(.*)/notebooks/(.*)";
     		// 创建 Pattern 对象
     		Pattern regxEdit = Pattern.compile(patternEdit);
     		Pattern regxNote = Pattern.compile(patternNote);
     		String patternTree = "http(s?)://(.*)/tree(.*)";
     		Pattern regxTree = Pattern.compile(patternTree);
     		Matcher mT = regxTree.matcher(newLoc);
     		if(mT.find()){
     			SwingUtilities.invokeLater(new Runnable() {
						public void run() {							
							BR.enableDir(true);		
						}
					});
     		}else{
     			SwingUtilities.invokeLater(new Runnable() {
					public void run() {							
						BR.enableDir(false);								
					}
				});
     		}
     				final String newResourceLocation = newLoc;
     				Matcher m = regxEdit.matcher(newResourceLocation);
     				Matcher m2 = regxNote.matcher(newResourceLocation);
     				if (m.find()) {// ||
     					// newResourceLocation.startsWith("http://127.0.0.1:8888/notebooks"))
     					// {
     					// e.consume();
     					SwingUtilities.invokeLater(new Runnable() {
     						public void run() {
     							System.out.println("edit"+newResourceLocation);
     							BR.enableFormatDownload(false);
     							BR.enableScriptDownload(true);
     							BR.enableFile(true);
     						}
     					});
     				} else if (m2.find()) {
     					SwingUtilities.invokeLater(new Runnable() {
     						public void run() {
     							System.out.println("notebooks"+newResourceLocation);
     							BR.enableFormatDownload(true);
     							BR.enableScriptDownload(true);
     							BR.enableFile(true);
     						}
     					});
     				} else {
     					SwingUtilities.invokeLater(new Runnable() {
     						public void run() {
     							System.out.println(newResourceLocation);
     							BR.enableFormatDownload(false);
     							BR.enableScriptDownload(false);
     							BR.enableFile(false);
     						}
     					});
     				}
     
     			
     		
         } 
      });

    }
   
   public static void open(File document) throws IOException {
      Desktop dt = Desktop.getDesktop();
      dt.open(document);
   }

   public void navigate(final String url) {


	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	            PlatformImpl.runLater(new Runnable() {
	               @Override
	               public void run() {
	                  webEngine.load(url);
	               }
	            });
	         }
	      });

	   }
   public void loadContent(final String htmlContent) {


      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            PlatformImpl.runLater(new Runnable() {
               @Override
               public void run() {

                  webEngine.loadContent(htmlContent);

               }
            });
         }
      });

   }
  public int script_id=0;
  public JSObject script_rv;
   public void executeScript(final java.lang.String script,int id) {
	   //return webEngine.executeScript(script);
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            PlatformImpl.runLater(new Runnable() {
               @Override
               public void run() {
            	  script_rv= (JSObject) webEngine.executeScript(script);
                  script_id=id;
                  //System.out.println(script_rv.getMember("notebook"));
               }
            });
         }
      });
   }
}
