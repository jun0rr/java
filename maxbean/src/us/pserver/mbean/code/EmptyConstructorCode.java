/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.mbean.code;

import us.pserver.mbean.code.spec.IConstructorCode;
import us.pserver.mbean.sql.spec.ITable;
import us.pserver.mbean.util.CamelCaseName;
import us.pserver.mbean.util.Ident;



/**
 *
 * @author juno
 */
public class EmptyConstructorCode 
		extends AbstractTableCode 
		implements IConstructorCode {
	
	
	public EmptyConstructorCode(ITable table, Ident ident) {
		super(table, ident);
	}
	
	
	@Override
	public String constructorCode() {
		return new StringBuilder()
				.append(ident.getIdentation())
				.append("public ")
				.append(CamelCaseName.of(table.getName()))
				.append("() {}\n")
				.toString();
	}


	@Override
	public String getCode() {
		return constructorCode();
	}
	
}
