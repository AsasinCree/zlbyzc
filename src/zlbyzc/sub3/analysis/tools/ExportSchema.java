package zlbyzc.sub3.analysis.tools;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.hibernate.MappingException;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import db.MyPath;

public class ExportSchema {
	
    @SuppressWarnings("unused")
	public static void main(String[] args) throws MappingException, IOException {
    	File f = new File(MyPath.getPath()+File.separator+"db"+File.separator+"hibernate.cfg.xml");
    	StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
    	ssrb.configure(f);
    	
    	
		String username="root";
		String password="wipm";
		String driver="com.mysql.jdbc.Driver";
		String dialect="org.hibernate.dialect.MySQL5InnoDBDialect";
		String connCMD = "jdbc:mysql://127.0.0.1:3306/mm";
				
    	ssrb.applySetting("hibernate.connection.driver_class",driver);
		ssrb.applySetting("hibernate.connection.username", username);
		ssrb.applySetting("hibernate.connection.password",password );
		ssrb.applySetting("hibernate.connection.url",connCMD);
		ssrb.applySetting("hibernate.dialect",dialect);
    	
    	
    	@SuppressWarnings("unchecked")
		Map<String, String> m=ssrb.getSettings();
    	System.out.println(m);
    	ServiceRegistry serviceRegistry = ssrb.build();  
    	MetadataImplementor metadataImplementor = (MetadataImplementor) new MetadataSources(serviceRegistry).buildMetadata();
    	SchemaExport export = new SchemaExport(metadataImplementor);
    	export.create(true, true);
    }

}
