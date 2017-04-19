package db;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.hibernate.cfg.Configuration;

import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.DefaultComboBoxModel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class SettingUI extends JDialog {

	//private final JPanel contentPanel = new JPanel();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JComboBox comSelDB;
	private sqliteIO configdb;
	private JTextField ip;
	private JButton okButt;
	private JButton cancelButt;
	
	private JTextField user;
	private JPasswordField pass;
	private JTextField ipNote;
	public boolean ok;
	Connection conn;
	private String drivers[] = { "com.mysql.jdbc.Driver", "org.sqlite.JDBC"}; 
	int driverIdx=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SettingUI dialog = new SettingUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			//System.out.println(MyPath.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SettingUI() {
		ok=false;
		driverIdx=0;
		init();
		this.pack();
        this.setSize(this.getWidth()*2,this.getHeight());
        //this.setVisible(true);
        setUiAction();
        configdb=new sqliteIO();
        loadV2ui();
	}
	
	private void saveV4ui(){				
		configdb.saveV("db_ip",ip.getText().trim());		
		configdb.saveV("user",user.getText().trim());
		configdb.saveV("pass",pass.getText().trim());
		configdb.saveV("db_type",Integer.toString(comSelDB.getSelectedIndex()));
		configdb.saveV("note_url",ipNote.getText().trim());
				
	}
	public String getNoteURL(){
		return ipNote.getText().trim();
	}
	private void loadV2ui(){
		String rv=null;
		String ipv="127.0.0.1:3306";
		rv=configdb.readV("db_ip");
		if(rv!=null)
			ipv=rv;
		ip.setText(ipv);
		String uv="root";
		rv=configdb.readV("user");
		if(rv!=null)
			uv=rv;
		user.setText(uv);
		String pv="wipm";
		rv=configdb.readV("pass");
		if(rv!=null)
			pv=rv;
		pass.setText(pv);
		String dbv="0";
		rv=configdb.readV("db_type");
		if(rv!=null)
			dbv=rv;
		comSelDB.setSelectedIndex(Integer.parseInt(dbv));
		String noteS="http://127.0.0.1:8888";
		
		rv=configdb.readV("note_url");
		if(rv!=null)
			noteS=rv;
		ipNote.setText(noteS);
	}
	
	private boolean writeConn_ini(){
		Properties props = new Properties();
        props.setProperty("jdbc.drivers", getDriver());
        String conn_url= ip.getText().trim();
		String username=user.getText().trim();
		String password=pass.getText().trim();
        String connCMD = String.format("jdbc:mysql://%s/db_zlpj",
				conn_url);	   
	    //Class.forName(driver);
	    String driver=getDriver();
	    if(driver.contains("sqlite")){	    	
	    		connCMD=String.format("jdbc:sqlite:db_zlpj.sqlite",conn_url);
	    }	            
        props.setProperty("jdbc.url", connCMD);
        props.setProperty("jdbc.username", username);
        props.setProperty("jdbc.password", password);
        try {
        	File f = new File(MyPath.getPath()+File.separator+"db"+File.separator+"conn.ini");
        	OutputStream out = new FileOutputStream( f );
			props.store(out,"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
        
		return true;
	}
	private boolean writeConn_xml(){
		
		return true;
	}
	
	
	 private void setUiAction() {
		// TODO Auto-generated method stub
		 cancelButt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ok=false;
					configdb.close();
					dispose();
				}
			});
		 okButt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ok=true;
					saveV4ui();
					writeConn_ini();
					writeConn_xml();
					configdb.close();
					dispose();
				}
			});
		 comSelDB.addActionListener(new ActionListener() {   
		      public void actionPerformed(ActionEvent e) {  
		    	driverIdx=((JComboBox)e.getSource()).getSelectedIndex();
		        if(driverIdx==1){
		        	user.setEnabled(false);
		        	pass.setEnabled(false);
		        }else{
		        	user.setEnabled(true);
		        	pass.setEnabled(true);
		        }
		        
		      }   
		    }); 
	}
	public String getDriver(){
		return drivers[driverIdx];
	}
	public String getConnURL(String dbName){
		String conn_url= ip.getText().trim();
		String username=user.getText().trim();
		String password=pass.getText().trim();
		String connCMD = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&useUnicode=true&characterEncoding=UTF8",
				conn_url,dbName,
				username, password);	   
	    //Class.forName(driver);
	    String driver=getDriver();
	    if(driver.contains("sqlite")){
	    	if(dbName.length()<1)
	    		connCMD=String.format("jdbc:sqlite:%s",conn_url);
	    	else
	    		connCMD=String.format("jdbc:sqlite:%s",dbName);
	    }
	    
	    return connCMD;
	}
	
	public void init() {
		 	JLabel labelDB = new JLabel("选择数据库");
		 	String[] str = { "MySQL", "Sqlite"};
		 	comSelDB=new JComboBox(str);
		 	JLabel labelIP = new JLabel("数据库连接地址");
		 	ip=new JTextField();
		 	JLabel labelUser = new JLabel("用户名");
		 	user=new JTextField();
		 	JLabel labelPass = new JLabel("密码");
		 	pass= new JPasswordField();
		 	
		 	JLabel labelNote = new JLabel("统计服务器地址");
		 	ipNote= new JTextField();
		 	
	        okButt = new JButton("确认");
	        cancelButt = new JButton("取消");
	       
	       
	        GridBagLayout layout = new GridBagLayout();
	        
	        getContentPane().setLayout(layout);
	        getContentPane().add(labelDB);//把组件添加进jframe
	        getContentPane().add(comSelDB);
	        getContentPane().add(labelIP);
	        getContentPane().add(ip);
	        getContentPane().add(labelUser);
	        getContentPane().add(user);
	        getContentPane().add(labelPass);
	        getContentPane().add(pass);
	        
	        getContentPane().add(labelNote);
	        getContentPane().add(ipNote);
	        getContentPane().add(okButt);
	        getContentPane().add(cancelButt);
	        
	        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
	        //是用来控制添加进的组件的显示位置
	        s.fill = GridBagConstraints.BOTH;
	        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
	        //NONE：不调整组件大小。
	        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
	        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
	        //BOTH：使组件完全填满其显示区域。
	        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
	        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        layout.setConstraints(labelDB, s);//设置组件
	        s.gridwidth=0;
	        s.weightx = 1;
	        s.weighty=0;
	        layout.setConstraints(comSelDB, s);
	        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
	        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        layout.setConstraints(labelIP, s);//设置组件
	        s.gridwidth=0;
	        s.weightx = 1;
	        s.weighty=0;
	        layout.setConstraints(ip, s);
	        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
	        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        layout.setConstraints(labelUser, s);//设置组件
	        s.gridwidth=0;
	        s.weightx = 1;
	        s.weighty=0;
	        layout.setConstraints(user, s);
	        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
	        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        layout.setConstraints(labelPass, s);//设置组件
	        s.gridwidth=0;
	        s.weightx = 1;
	        s.weighty=0;
	        layout.setConstraints(pass, s);
	        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
	        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        layout.setConstraints(labelNote, s);//设置组件
	        s.gridwidth=0;
	        s.weightx = 1;
	        s.weighty=0;
	        layout.setConstraints(ipNote, s);
	        s.gridwidth=0;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
	        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
	        layout.setConstraints(okButt, s);//设置组件
	        s.gridwidth=0;
	        s.weightx = 0;
	        s.weighty=0;
	        layout.setConstraints(cancelButt, s);
	    }
	    
}
