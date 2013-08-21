// Author ( Sharmaine Lim )
// 
// Tables: person & storage_location

package database.builders.references;

import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class ReferenceInsertStatementBuilder extends CustomStatementBuilder {
	
	private String table;
	private String value;
	
	public ReferenceInsertStatementBuilder() {
		super();
		table = null;
		value = null;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		ReferenceInsertBuilder insertBuilder = new ReferenceInsertBuilder();
		insertBuilder.setTable(table);
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsertIgnore();
		Insert insert = insertDirector.getInsert();
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		if ( !(value == null || value.isEmpty()) ) {
			customStatement.addValue("Value", value);
		}
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
