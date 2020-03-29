/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.mbean.sql.type;

import us.pserver.mbean.sql.spec.Named;



/**
 *
 * @author juno
 */
public interface SqlType<T> extends Named {
	
	public Class<T> getJavaClass();
	
}
