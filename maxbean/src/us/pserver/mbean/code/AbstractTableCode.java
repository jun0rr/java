/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.mbean.code;

import us.pserver.mbean.code.spec.IIdentCode;
import us.pserver.mbean.code.spec.ITableCode;
import us.pserver.mbean.sql.spec.ITable;
import us.pserver.mbean.util.Ident;



/**
 *
 * @author juno
 */
public abstract class AbstractTableCode implements ITableCode, IIdentCode {
	
	protected final ITable table;
	
	protected final Ident ident;
	
	
	protected AbstractTableCode(ITable col, Ident ident) {
		if(col == null) {
			throw new IllegalArgumentException(
					"Column must be not null"
			);
		}
		if(ident == null) {
			throw new IllegalArgumentException(
					"Ident must be not null"
			);
		}
		this.table = col;
		this.ident = ident;
	}

	
	@Override
	public ITable getTable() {
		return table;
	}
	

	@Override
	public Ident getIdent() {
		return ident;
	}
	
}
