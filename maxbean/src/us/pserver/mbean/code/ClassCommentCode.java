/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.mbean.code;

import us.pserver.mbean.code.spec.ICode;
import us.pserver.mbean.sql.spec.ITable;
import us.pserver.mbean.util.Ident;



/**
 *
 * @author juno
 */
public class ClassCommentCode extends AbstractTableCode implements ICode {
	
	public ClassCommentCode(ITable table, Ident ident) {
		super(table, ident);
	}
	
	
	public String commentCode() {
		StringBuilder sb = new StringBuilder();
		return new StringBuilder()
				.append("/**\n")
				.append(" * ").append("Generated JavaBean to encapsulate information\n")
				.append(" * ").append("from the Database table [")
				.append(table.getSchema())
				.append(".")
				.append(table.getName())
				.append("]\n */")
				.toString();
	}


	@Override
	public String getCode() {
		return commentCode();
	}
	
}
