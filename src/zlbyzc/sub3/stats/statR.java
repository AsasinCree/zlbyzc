package zlbyzc.sub3.stats;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

import netscape.javascript.JSObject;
import zlbyzc.gui.ImageTask;
/*
import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowserWindow;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowFactory;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;*/
import zlbyzc.gui.SwingFXWebView;
import zlbyzc.gui.TestProgressBar;
import zlbyzc.sub3.stats.HttpDownloadUtility;
import zlbyzc.BasicRibbon;
//import zlbyzc.gui.test.NavigationControl;
import zlbyzc.sub3.sub3inFrame;

public class statR extends sub3inFrame {

	final JPanel contentPane;
	private BasicRibbon BR;
	//private JWebBrowser webBrowser;
	private String lastDir = null;
	private String downloadURL = null;
	private Thread thread;
	private String theURL="http://127.0.0.1:8888";
//
//	public void createWebB(String newResourceLocation, String Title) {
//		// Properties props = System.getProperties();
//		// props.setProperty("nativeswing.webbrowser.xulrunner.home",
//		// System.getProperty("user.dir")+File.separator+
//		// "libs"+File.separator+"xulrunner31");
//		// System.out.println();
//		JPanel webBrowserPanel = new JPanel(new BorderLayout());
//		webBrowser = new JWebBrowser(JWebBrowser.useXULRunnerRuntime());
//		webBrowser.setMenuBarVisible(false);
//		webBrowser.setLocationBarVisible(false);
//		webBrowser.navigate(newResourceLocation);
//		// webBrowser.setBorder(BorderFactory.createTitledBorder(
//		// new EtchedBorder(EtchedBorder.RAISED),
//		// "EAMobuild Browser"));
//		String patternEdit = "http(s?)://(.*)/edit/(.*)";
//		String patternNote = "http(s?)://(.*)/notebooks/(.*)";
//		// 创建 Pattern 对象
//		Pattern regxEdit = Pattern.compile(patternEdit);
//		Pattern regxNote = Pattern.compile(patternNote);
//		webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
//			@Override
//			public void locationChanging(WebBrowserNavigationEvent e) {
//				super.locationChanging(e);
//				// System.out.println(e.getNewResourceLocation());
//				final String newResourceLocation = e.getNewResourceLocation();
//				Matcher m = regxEdit.matcher(newResourceLocation);
//				Matcher m2 = regxNote.matcher(newResourceLocation);
//				if (m.find()) {// ||
//					// newResourceLocation.startsWith("http://127.0.0.1:8888/notebooks"))
//					// {
//					// e.consume();
//					SwingUtilities.invokeLater(new Runnable() {
//						public void run() {
//							// System.out.println(newResourceLocation);
//							BR.enableFormatDownload(true);
//							BR.enableScriptDownload(true);
//						}
//					});
//				} else if (m2.find()) {
//					SwingUtilities.invokeLater(new Runnable() {
//						public void run() {
//							// System.out.println(newResourceLocation);
//							BR.enableFormatDownload(true);
//							// BR.enableScriptDownload(true);
//						}
//					});
//				} else {
//					SwingUtilities.invokeLater(new Runnable() {
//						public void run() {
//							// System.out.println(newResourceLocation);
//							BR.enableFormatDownload(true);
//							// BR.enableScriptDownload(true);
//						}
//					});
//				}
//
//			}
//		});
//		webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
//		contentPane.add(webBrowserPanel, BorderLayout.CENTER);
//	}
	private SwingFXWebView sb;
	public statR(BasicRibbon _br) {
		super("统计分析模块", true, true, true, true);
		BR = _br;
		BR.enableStatsButt(false);
		BR.statInFra = this;
		// NativeInterface.open();
		// webBrowser= new JWebBrowser();
		contentPane = new JPanel(new BorderLayout());
		theURL=BR.setting.getNoteURL();
		SwingUtilities.invokeLater(new Runnable() {  	        
			@Override
	           public void run() {  	                 
	               sb=new SwingFXWebView(BR);
	               contentPane.add(sb);  	                	               
	               sb.navigate(theURL);
	           }  
	       });   
		// NativeInterface.runEventPump();
		
		InternalFrameHandler handler = new InternalFrameHandler(BR);
        addInternalFrameListener(handler);
        addPropertyChangeListener(handler);
		getContentPane().add(contentPane, BorderLayout.CENTER);
		pack();
		this.setVisible(true);
		//BR.enableFormatDownload(true);
		//BR.enableScriptDownload(true);
		BR.enableDir(false);
	}

	public void download(String type) {
		downloadURL = null;
		String webURL =sb.getWebEngine().getLocation();//= webBrowser.getResourceLocation();

		// System.out.println(webURL);
		String patternEdit = "http(s?)://(.*)/edit/(.*)";
		String patternNote = "http(s?)://(.*)/notebooks/(.*).ipynb(.*)";
		// 创建 Pattern 对象
		Pattern regxEdit = Pattern.compile(patternEdit);
		Pattern regxNote = Pattern.compile(patternNote);
		Matcher m = regxEdit.matcher(webURL);
		Matcher m2 = regxNote.matcher(webURL);
		if (m.find()) {
			if (type.equals("files")) {
				downloadURL = "http" + m.group(1) + "://" + m.group(2) + "/files/" + m.group(3) + "?download=1";
			}
			// System.out.println(downloadURL);
		} else if (m2.find()) {
			if (type.equals("notebook")) {
				downloadURL = "http" + m2.group(1) + "://" + m2.group(2) + "/files/" + m2.group(3)
						+ ".ipynb?download=1";
			} else if (type.equals("files")) {
				downloadURL = "http" + m2.group(1) + "://" + m2.group(2) + "/nbconvert/script/" + m2.group(3)
						+ ".ipynb?download=true";
			} else if (type.equals("html")) {
				downloadURL = "http" + m2.group(1) + "://" + m2.group(2) + "/nbconvert/html/" + m2.group(3)
						+ ".ipynb?download=true";
			} else if (type.equals("rst")) {
				downloadURL = "http" + m2.group(1) + "://" + m2.group(2) + "/nbconvert/rst/" + m2.group(3)
						+ ".ipynb?download=true";
			} else if (type.equals("markdown")) {
				downloadURL = "http" + m2.group(1) + "://" + m2.group(2) + "/nbconvert/markdown/" + m2.group(3)
						+ ".ipynb?download=true";
			} else if (type.equals("pdf")) {
				downloadURL = "http" + m2.group(1) + "://" + m2.group(2) + "/nbconvert/pdf/" + m2.group(3)
						+ ".ipynb?download=true";
			}
		} else {
			JOptionPane.showMessageDialog(null, "当前页面无保存功能");
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFileChooser fileChooser = null;
				if (lastDir == null)
					fileChooser = new JFileChooser();
				else
					fileChooser = new JFileChooser(lastDir);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Notebook Files", "ipynb", "md", "rst",
						"zip", "pdf", "html", "htm", "r", "py");
				fileChooser.setFileFilter(filter);
				fileChooser.setDialogTitle("无需输入文件扩展名");
				int returnValue = fileChooser.showDialog(null, "确认");
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					thread = new Thread() {
				          public void run() {
				        	  try {
									HttpDownloadUtility.downloadFile(downloadURL, selectedFile.getParent(), selectedFile.getName());
									lastDir = selectedFile.getParent();
								} catch (IOException ex) {
									ex.printStackTrace();
								}
				            }
				        };
				        SwingUtilities.invokeLater(new Runnable() {
							public void run() {
				   zlbyzc.gui.TestProgressBar.show((Frame)null, thread,
						   "正在连接下载...(下载pdf时耗时较长，请稍候)", "保存成功", "取消");
							}
						});

				}
			}
		});
	}

	public void saveAs(String string) {
		// TODO Auto-generated method stub
		
	}

	public void rename() {
		//TODO 重命名前保存
		//sb.executeScript("window.notebook.save_checkpoint();",1);
		
		//sb.executeScript("notebook.save_checkpoint();");
		//sb.executeScript("history.back()");
		String webURL="";
		try {
			webURL = URLDecoder.decode(sb.getWebEngine().getLocation(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		String patternEdit = "http(s?)://(.*)/edit/(.*)";
		String patternNote = "http(s?)://(.*)/notebooks/(.*).ipynb(.*)";
		// 创建 Pattern 对象
		String oldDir="";
		String newDir="";
		Pattern regxEdit = Pattern.compile(patternEdit);
		Pattern regxNote = Pattern.compile(patternNote);	
		Matcher mE = regxEdit.matcher(webURL);
		Matcher mN = regxNote.matcher(webURL);
		if (mE.find()) {
			String turl="";
			String newUrl="";
			ApiReq req = new ApiReq();
			oldDir=mE.group(3);
			turl=oldDir;
			int lastSp=oldDir.lastIndexOf("/");
			if(lastSp>0)
				turl=oldDir.substring(lastSp+1);
			if(JOptionPane.YES_OPTION!=JOptionPane.showInternalConfirmDialog
					(BR.getDesktopPane(),
							"重命名前请确认已保存文件！否则新录入内容可能会丢失\n(可通过点击文件菜单下的保存完成)",
							"请确认",JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE))
				return;
			newDir=JOptionPane.showInputDialog(BR.getDesktopPane(),"请输入变更后的文件名",
					turl);
			//JSObject window = sb.script_rv; 
			//System.out.println(window);
			if(newDir.length()<1){
				JOptionPane.showInternalMessageDialog(BR.getDesktopPane(),"请输入至少一个字符");
				return;
			}				
			String url="http" + mE.group(1) + "://" + mE.group(2) 
						+"/api/contents/"+oldDir;						
			String json="";
			if(lastSp>0){
				turl=oldDir.substring(1);
				json = req.patchDirJson(oldDir.substring(0, lastSp)+"/"+newDir);
				newUrl="http" + mE.group(1) + "://" +
						mE.group(2) +"/edit/"+oldDir.substring(0, lastSp)+"/"+newDir;
			}else{
				json = req.patchDirJson(newDir);
				newUrl="http" + mE.group(1) + "://" +
						mE.group(2) +"/edit/"+newDir;
			}
			try {
				System.out.println(req.patch(url, json)); 
				
				sb.navigate(newUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if (mN.find()) {
			String turl="";
			String newUrl="";
			ApiReq req = new ApiReq();
			oldDir=mN.group(3);
			turl=oldDir;
			int lastSp=oldDir.lastIndexOf("/");
			if(lastSp>0)
				turl=oldDir.substring(lastSp+1);
			if(JOptionPane.YES_OPTION!=JOptionPane.showInternalConfirmDialog
					(BR.getDesktopPane(),
							"重命名前请确认已保存文件！否则新录入内容可能会丢失\n(可通过点击文件区域左上角的保存按钮图标完成)"
							,"请确认",JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE))
					
				return;
			newDir=JOptionPane.showInputDialog(BR.getDesktopPane(),"请输入变更后的文件名",
					turl);
			if(newDir.length()<1){
				JOptionPane.showInternalMessageDialog(BR.getDesktopPane(),"请输入至少一个字符");
				return;
			}				
			String url="http" + mN.group(1) + "://" + mN.group(2) 
						+"/api/contents/"+oldDir+".ipynb";						
			String json="";
			if(lastSp>0){
				turl=oldDir.substring(1);
				json = req.patchDirJson(oldDir.substring(0, lastSp)+"/"+newDir+".ipynb");
				newUrl="http" + mN.group(1) + "://" +
						mN.group(2) +"/notebooks/"+oldDir.substring(0, lastSp)+"/"+newDir+".ipynb";
			}else{
				json = req.patchDirJson(newDir+".ipynb");
				newUrl="http" + mN.group(1) + "://" +
						mN.group(2) +"/notebooks/"+newDir+".ipynb";
			}
			try {
				System.out.println(req.patch(url, json)); 
				
				sb.navigate(newUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}  
		else {
			JOptionPane.showInternalMessageDialog(BR.getDesktopPane(), "请进入文件页面对文件更名");
			return;
		}		
		
	}
	public void mvDir(){
		String webURL="";
		try {
			webURL = URLDecoder.decode(sb.getWebEngine().getLocation(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		String patternTree = "http(s?)://(.*)/tree/(.*)";
		// 创建 Pattern 对象
		String oldDir="";
		String newDir="";
		Pattern regxTree = Pattern.compile(patternTree);		
		Matcher mT = regxTree.matcher(webURL);
		if (mT.find()) {
			String turl="";
			String newUrl="";
			ApiReq req = new ApiReq();
			oldDir=mT.group(3);
			turl=oldDir;
			int lastSp=oldDir.lastIndexOf("/");
			if(lastSp>0)
				turl=oldDir.substring(lastSp+1);
			
			newDir=JOptionPane.showInputDialog(BR.getDesktopPane(),"请输入变更后的文件夹名",
					turl);
			if(newDir.length()<1){
				JOptionPane.showInternalMessageDialog(BR.getDesktopPane(),"请输入至少一个字符");
				return;
			}				
			String url="http" + mT.group(1) + "://" + mT.group(2) 
						+"/api/contents/"+oldDir;						
			String json="";
			if(lastSp>0){
				turl=oldDir.substring(1);
				json = req.patchDirJson(oldDir.substring(0, lastSp)+"/"+newDir);
				newUrl="http" + mT.group(1) + "://" +
						mT.group(2) +"/tree/"+oldDir.substring(0, lastSp)+"/"+newDir;
			}else{
				json = req.patchDirJson(newDir);
				newUrl="http" + mT.group(1) + "://" +
						mT.group(2) +"/tree/"+newDir;
			}
			try {
				System.out.println(req.patch(url, json)); 
				
				sb.navigate(newUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			JOptionPane.showInternalMessageDialog(BR.getDesktopPane(), "请进入文件夹页面对文件夹更名");
			return;
		}		
	}
	public void newDir() {
		String webURL =sb.getWebEngine().getLocation();//= webBrowser.getResourceLocation();		
		String patternTree = "http(s?)://(.*)/tree(.*)";
		// 创建 Pattern 对象
		String oldDir="";
		String newDir="";
		Pattern regxTree = Pattern.compile(patternTree);		
		Matcher mT = regxTree.matcher(webURL);
		if (mT.find()) {
			ApiReq req = new ApiReq();
			oldDir=mT.group(3);
			newDir=JOptionPane.showInternalInputDialog(BR.getDesktopPane(),"请输入新的文件夹名");
			if(newDir.length()<1){
				JOptionPane.showInternalMessageDialog(BR.getDesktopPane(),"请输入至少一个字符");
				return;
			}				
			String url="http" + mT.group(1) + "://" + mT.group(2) +"/api/contents"+oldDir+"/"+newDir;
			String turl="";
			String json="";
			if(oldDir.startsWith("/")){
				turl=oldDir.substring(1);
				json = req.putDirJson(turl+"/"+newDir);
			}else{
				json = req.putDirJson(newDir);
			}
			try {
				System.out.println(req.put(url, json)); 
				String newUrl="http" + mT.group(1) + "://" + mT.group(2) +"/tree"+oldDir+"/"+newDir;
				sb.navigate(newUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			JOptionPane.showInternalMessageDialog(BR.getDesktopPane(), "请在目录页面新建目录");
			return;
		}
		
		
	}

}
class InternalFrameHandler implements PropertyChangeListener, InternalFrameListener {
	  private BasicRibbon BR;
	public InternalFrameHandler(BasicRibbon bR) {
		BR=bR;
	}
	//PropertyChangeListener
	  @Override public void propertyChange(PropertyChangeEvent e) {
	    if (JInternalFrame.IS_MAXIMUM_PROPERTY.equals(e.getPropertyName())) {
	      System.out.println("isMaximum: " + e.getNewValue());
	    }
	  }
	  //InternalFrameListener
	  @Override public void internalFrameClosing(InternalFrameEvent e) {
	    System.out.println("internalFrameClosing");
	  }
	  @Override public void internalFrameClosed(InternalFrameEvent e) {
	    System.out.println("internalFrameClosed");
	    BR.enableStatsButt(true);
		BR.enableFormatDownload(false);
		BR.enableScriptDownload(false);
		BR.enableFile(false);
		BR.enableDir(false);
		BR.statInFra = null;
	  }
	  @Override public void internalFrameOpened(InternalFrameEvent e) {
	    System.out.println("internalFrameOpened");
	  }
	  @Override public void internalFrameIconified(InternalFrameEvent e) {
	    System.out.println("internalFrameIconified");
	  }
	  @Override public void internalFrameDeiconified(InternalFrameEvent e) {
	    System.out.println("internalFrameDeiconified");
	    if (e.getInternalFrame().isMaximum()) {
	      System.out.println("isMaximum: " + e.getInternalFrame().isMaximum());
	    }
	  }
	  @Override public void internalFrameActivated(InternalFrameEvent e) {
	    System.out.println("internalFrameActivated");
	    
	  }
	  @Override public void internalFrameDeactivated(InternalFrameEvent e) {
	    System.out.println("internalFrameDeactivated");
	  }
	}

