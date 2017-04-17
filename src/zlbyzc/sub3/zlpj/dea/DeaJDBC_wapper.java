package zlbyzc.sub3.zlpj.dea;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//该类用于操作一个数据库里的多个tables。
//警告：该类没有对SQL异常过多考虑，仅仅打印异常！！！
public class DeaJDBC_wapper {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	String[] tables;
	
	//构造函数连接数据库，并创建Statement对象 stmt。
	//sIniPath为sql配置文件，包含database,user,password信息，位于当前目录。
	public DeaJDBC_wapper(String sIniPath){
		try {
			conn = makeConnection(sIniPath);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			System.out.println("MySQL连接不成功连接不成功连接不成功连接不成功连接不成功连接不成功");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("未知异常");
			e.printStackTrace();
		}	
	}
	
	//插入记录，并去除重复记录。
	//试图在插入前判断table中是否有该记录，但没有成功，只好退而求其次，插入后去重。
	//insertCMD 如 insert mytable values(....)
	public boolean insert(String insertCMD) {
		try {
			stmt.executeUpdate(insertCMD);
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("未知异常");
			e.printStackTrace();
			return false;
		}		
		//提取tablename，然后对该table去重。
		String tablename = getTablenameFromInertCMD(insertCMD);
		if (tablename.length()!=0) { 
			delDupRecords(tablename);
		}
		return true;
	}
	
	public ResultSet query(String queryCMD) {
		try {
			rs = stmt.executeQuery(queryCMD);	
			return rs;
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("未知异常");
			e.printStackTrace();
		}		
		return null;
	}
	
	//从如下插入命令中提取table名称
	//insert xxx values(...)  -->  xxx
	private String getTablenameFromInertCMD(String insertCMD) {
		String tablename = "";
	    Pattern p = Pattern.compile("insert\\s+(.*)\\s+values",Pattern.CASE_INSENSITIVE);  
	    // 匹配器初始化  
	    Matcher m = p.matcher(insertCMD);  
	    if (m.find()) {
	    	tablename = m.group(1);
		}
		return tablename;
	}
	
	//删除表的重复记录：创建一个新表来放旧表中不重复的记录，再把旧表删除，将新表改成旧表的名字。
	//主要利用了这条命令 CREATE TABLE table_new SELECT DISTINCT * FROM table_old
	//该函数在insert函数中被调用，也可在外面调用。
	public void delDupRecords(String tablename) {
		try {
			String queryCMD = String.format("CREATE TABLE %s_tmp SELECT DISTINCT * FROM %s", tablename,tablename);
			stmt.executeUpdate(queryCMD); //取tablename里所有不重复数据，复制到新表tablename_tmp
			String dropOldTableCMD = String.format("DROP TABLE %s", tablename);
			stmt.executeUpdate(dropOldTableCMD); //删除旧表tablename
			String renameCMD = String.format("RENAME TABLE %s_tmp to %s", tablename,tablename);
			stmt.executeUpdate(renameCMD); //改名tablename_tmp to tablename
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("未知异常");
			e.printStackTrace();
		}
		
	}
	
	//依次关闭ResultSet,Statement,Connection.
	//该函数需要在外面使用完该类后显式调用。
	public void close() {
		if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
		if (stmt != null){
	        try {
	        	stmt.close();
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        }
		}
		if (conn != null){
		    try {
		    	conn.close();
		    } catch (SQLException e) {
		    	e.printStackTrace();
		    }
		}		
	}
	
	//数据库建立连接
	private  Connection makeConnection(String sIniPath)
			throws SQLException, IOException ,ClassNotFoundException{
		//通过加载conn.ini文件来获取数据库连接的详细信息
		Properties props = new Properties();
		//String sIniPath1=getClass().getResource("/").getPath()+sIniPath;
		FileInputStream in = new FileInputStream(System.getProperty("user.dir")+File.separator+sIniPath);
		props.load(in);
		in.close();
		String drivers = props.getProperty("jdbc.drivers");
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");
		//加载数据库驱动
		Class.forName(drivers);
		//取得数据库连接
		String connCMD = String.format("%s?user=%s&password=%s&useUnicode=true&characterEncoding=UTF8&useSSL=true",url,username, password);
		return DriverManager.getConnection(connCMD);
	}
	
	public Connection getConn() {
		return conn;
	}

	public Statement getStmt() {
		return stmt;
	}
	
	public static void main(String[] args) {
		//testInsertAndQuery();
		testDatetime();
	}
	
	public static void testInsertAndQuery(){
		try {
			DeaJDBC_wapper mysql_ = new DeaJDBC_wapper("conn.ini");
			String insertCMD = "insert dea values('南海问题','20000302','一些结论等等','一些结论等等')";
			mysql_.insert(insertCMD);
			
			String queryCMD = "select * from dea";
			ResultSet rs = mysql_.query(queryCMD);
			
			while(rs.next()){
				System.out.println(rs.getString(1)+"      "+rs.getString(2));
			}
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("未知异常");
		}
	}

	public static void  testDatetime(){
		try {

			DeaJDBC_wapper mysql_ = new DeaJDBC_wapper("conn.ini");
			
			Calendar ca = Calendar.getInstance();
			int year = ca.get(Calendar.YEAR);//获取年份
			int month=ca.get(Calendar.MONTH);//获取月份
			int day=ca.get(Calendar.DATE);//获取日
			 
			//int year = 2012-1900;
			//int month = 0;
			//int day = 12;
			//Date date = new Date(year,month,day);

			String insertCMD;	 
			insertCMD = String.format("insert dea values('a1问题','%s','一些结论等等','一些结论等等')", "2000-2-3");mysql_.insert(insertCMD);
			insertCMD = String.format("insert dea values('a2问题','%s','一些结论等等','一些结论等等')", "2000-2-4");mysql_.insert(insertCMD);
			insertCMD = String.format("insert dea values('a3问题','%s','一些结论等等','一些结论等等')", "2001-2-3");mysql_.insert(insertCMD);
			insertCMD = String.format("insert dea values('a4问题','%s','一些结论等等','一些结论等等')", "2010-12-3");mysql_.insert(insertCMD);
			insertCMD = String.format("insert dea values('a5问题','%s','一些结论等等','一些结论等等')", "2003-2-13");mysql_.insert(insertCMD);
			
			//System.out.println(insertCMD);
			//mysql_.insert(insertCMD);
			
			String queryCMD = "select * from dea where deaaim like '%%%问题%%%' and deaargueTime>'20050202' and deaargueTime<'20150302'";
			ResultSet rs = mysql_.query(queryCMD);
			
			while(rs.next()){
				System.out.println(rs.getString(1)+"      "+rs.getString(2)+rs.getString(3)+rs.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("MySQL操作错误");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("未知异常");
		}
		
		


	}
}
