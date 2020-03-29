/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.mbean.code;

import us.pserver.mbean.code.spec.ICode;
import us.pserver.mbean.sql.spec.ITable;
import us.pserver.mbean.util.CamelCaseName;
import us.pserver.mbean.util.Ident;



/**
 *
 * @author juno
 */
public class BeanReaderComment extends AbstractTableCode implements ICode {
	
	public BeanReaderComment(ITable table, Ident ident) {
		super(table, ident);
	}
	
	
	public String commentCode() {
		StringBuilder sb = new StringBuilder();
		return new StringBuilder()
				.append("/**\n")
				.append(" * ").append("BeanReader utility class to generate ")
				.append(CamelCaseName.of(table.getName())).append("\n")
				.append(" * ").append("JavaBean from a given java.sql.ResultSet.\n")
				.append(" */")
				.toString();
	}


	@Override
	public String getCode() {
		return commentCode();
	}
	
}
