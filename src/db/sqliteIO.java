package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class sqliteIO {
	public sqliteIO(){
		loadDB();
	}
	Connection conn;
	private void loadDB(){
		String dbUrl = MyPath.getPath()+File.separator+"db"+File.separator+"config.sqlite";
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+dbUrl);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	}
	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean saveV(String key,String v){
		PreparedStatement statement=null;
		boolean rv=true;
		try {						
			String sql = "UPDATE config SET value=? WHERE item=?";
			 
			statement = conn.prepareStatement(sql);
			statement.setString(1, v);
			statement.setString(2, key);
			
			 
			statement.executeUpdate();
			
			
			statement.close();
		} catch (SQLException|java.lang.NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rv=false;
		}		
		return rv;				
	}
	public String readV(String key){
		Statement stmt;
		String rv=null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT * FROM config where item=\""+key+"\";" );
			
			if (!rs.isBeforeFirst() ) {    
				 System.out.println("No data"); 
				 rv= null;
				} 		
			else{ 
				while ( rs.next() ) {			         
			         rv = rs.getString("value");		         		         
			      }
			}
			rs.close();
			stmt.close();
		} catch (SQLException|java.lang.NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		return rv;				
	}
}
