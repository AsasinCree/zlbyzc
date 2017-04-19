package db;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTool {

	private static Configuration configuration;
	private static SessionFactory sessionFactory;
	private static String drivers[] = { "com.mysql.jdbc.Driver", "org.sqlite.JDBC"};
	private HibernateTool() {

	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			 File f = new File(MyPath.getPath()+File.separator+"db"+File.separator+"hibernate.cfg.xml");
			 configuration = new Configuration().configure(f);
			 sqliteIO configdb=new sqliteIO();
			 String conn_url= configdb.readV("db_ip");
				String username=configdb.readV("user");
				String password=configdb.readV("pass");
				String dbv="0";
				String rv=configdb.readV("db_type");
				if(rv!=null)
					dbv=rv;				
				String driver=drivers[Integer.parseInt(dbv)];
				String dialect="org.hibernate.dialect.MySQL5InnoDBDialect";
				String connCMD = String.format("jdbc:mysql://%s/mm",
						conn_url);	   
			    //Class.forName(driver);	    
			    if(driver.contains("sqlite")){	    	
			    		connCMD=String.format("jdbc:sqlite:mm.sqlite",conn_url);
			    		dialect="org.hibernate.dialect.SQLiteDialect";
			    }	   				
				configuration.setProperty("hibernate.connection.driver_class",driver);
				configuration.setProperty("hibernate.connection.username", username);
				configuration.setProperty("hibernate.connection.password",password );
				configuration.setProperty("hibernate.connection.url",connCMD);
				configuration.setProperty("hibernate.dialect",dialect);
			 
			 //System.out.println(driver+'#'+username+'#'+password+'#'+connCMD+'#'+dialect);
			 
			 sessionFactory = configuration.buildSessionFactory();
			return sessionFactory;
		} else {
			return sessionFactory;
		}
	}

	public static Session getSession() {
		return getSessionFactory().openSession();

	}

	public static void closeSession(Session session) {
		if (session != null) {
			session.close();
		}
	}

}
