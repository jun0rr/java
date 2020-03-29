/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.mbean.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import us.pserver.mbean.SchemaInspector;
import us.pserver.mbean.sql.spec.ISchema;
import us.pserver.mbean.sql.spec.ITable;



/**
 *
 * @author juno
 */
public class TestSchemaInspector {
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://172.29.14.107", "F6036477", "6036577");
		SchemaInspector inspector = new SchemaInspector(conn, "intranet");
		ISchema schema = inspector.inspect();
		System.out.println("* "+ schema);
		for(ITable t : schema.getTables()) {
			System.out.println(" - "+ t);
		}
	}
	
}
