package database.builders.references.rewriteable;

import database.builders.InsertBuilder;

public class RewriteableReferenceCategoryInsertBuilder extends InsertBuilder {
	
	private String table;
	
	public RewriteableReferenceCategoryInsertBuilder() {
		super();
		table = null;
	}
	
	@Override
	public void setIntoTable() {
		insert.setIntoTable(table);
	}
	
	@Override
	public void populateIntoColumns() {}
	
	@Override
	public void setNumberOfInsertValues() {
		insert.setNumberOfInsertValues(1);
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
}
