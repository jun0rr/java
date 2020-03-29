/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.mbean.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import us.pserver.mbean.sql.Column;
import us.pserver.mbean.sql.Schema;
import us.pserver.mbean.sql.Table;
import us.pserver.mbean.sql.spec.IColumn;
import us.pserver.mbean.sql.spec.ISchema;
import us.pserver.mbean.sql.spec.ITable;
import us.pserver.mbean.sql.type.Typed;


/**
 *
 * @author juno
 */
public class SchemaConfig {
	
	private final ISchema schema;
	
	private final Properties props;
	
	
	public SchemaConfig(Properties config) {
		if(config == null) {
			throw new IllegalArgumentException(
					"Properties config must be not null"
			);
		}
		this.props = config;
		if(!props.contains("schema")) {
			throw new IllegalArgumentException(
					"Invalid properties. Do not contains 'schema'"
			);
		}
		schema = new Schema(props.getProperty("schema"));
	}
	
	
	public ISchema getSchema() {
		return schema;
	}
	
	
	public Properties getProperties() {
		return props;
	}
	
	
	public ISchema readSchema() {
		Iterator<String> keys = props
				.stringPropertyNames().iterator();
		Map<String,ITable> tables = new HashMap<>();
		while(keys.hasNext()) {
			String key = keys.next();
			if(key.startsWith("column.")) {
				String[] column = key.split("\\.");
				String value = props.getProperty(key);
				if(column.length == 3) {
					ITable tb = tables.get(column[1]);
					if(tb == null) {
						tb = new Table(column[1], schema.getName());
					}
					tb.addColumn(new Column(
							column[2], 
							Typed.of(value).getType())
					);
					tables.put(column[1], tb);
				}
			}
		}
		Iterator<ITable> tbs = tables.values().iterator();
		schema.getTables().clear();
		while(tbs.hasNext()) {
			schema.addTable(tbs.next());
		}
		return schema;
	}
	
	
	public Properties writeTable() {
		props.setProperty("schema", table.getSchema());
		props.setProperty("table", table.getName());
		Iterator<IColumn<?>> cols = table.getColumns().iterator();
		while(cols.hasNext()) {
			IColumn<?> col = cols.next();
			props.setProperty("column."+ col.getName(), col.getType().getName());
		}
		return props;
	}
	
}
