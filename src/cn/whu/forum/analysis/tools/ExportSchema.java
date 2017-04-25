package cn.whu.forum.analysis.tools;

import java.io.IOException;

import org.hibernate.MappingException;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * 用于数据库创建
 * @author cree
 *
 */
public class ExportSchema {
	public static void main(String[] args) throws MappingException, IOException {
		ServiceRegistry serviceRegistry = (ServiceRegistry) new StandardServiceRegistryBuilder().configure().build();
		MetadataImplementor metadataImplementor = (MetadataImplementor) new MetadataSources(serviceRegistry)
				.buildMetadata();
		SchemaExport export = new SchemaExport(serviceRegistry, metadataImplementor);
		export.create(true, true);
	}
}
