package zlbyzc.sub3.stats;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
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

	public statR(BasicRibbon _br) {
		super("统计分析模块", true, true, true, true);
		BR = _br;
		BR.enableStatsButt(false);
		BR.statInFra = this;
		// NativeInterface.open();
		// webBrowser= new JWebBrowser();
		contentPane = new JPanel(new BorderLayout());

		 SwingUtilities.invokeLater(new Runnable() {  
	           @Override
	           public void run() {  
	                 
	               SwingFXWebView sb=new SwingFXWebView();
	               contentPane.add(sb);  	                	               
	               sb.navigate("http://192.168.0.2:8888");
	           }  
	       });   

		// NativeInterface.runEventPump();
		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosing(InternalFrameEvent e) {
				BR.enableStatsButt(true);
				BR.enableFormatDownload(false);
				BR.statInFra = null;
			}
		});
		getContentPane().add(contentPane, BorderLayout.CENTER);
		pack();
		this.setVisible(true);

	}

	public void download(String type) {
		downloadURL = null;
		String webURL =null;//= webBrowser.getResourceLocation();

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

}
