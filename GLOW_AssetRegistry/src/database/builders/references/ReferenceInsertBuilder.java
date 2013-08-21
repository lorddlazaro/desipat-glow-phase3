// Author ( Sharmaine Lim )
// 
// Tables: person & storage_location

package database.builders.references;

import database.builders.InsertBuilder;

public class ReferenceInsertBuilder extends InsertBuilder {
	
	private String table;
	
	public ReferenceInsertBuilder() {
		super();
	}
	
	@Override
	public void setIntoTable() {
		insert.setIntoTable(table);
	}
	
	@Override
	public void populateIntoColumns() {
		insert.addIntoColumns("Value");
	}
	
	@Override
	public void setNumberOfInsertValues() {
		insert.setNumberOfInsertValues(1);
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
}
