package database.builders.values;

import database.builders.InsertBuilder;

public class ValueInsertBuilder extends InsertBuilder {
	
	public ValueInsertBuilder() {
		super();
	}
	
	@Override
	public void setIntoTable() {
		insert.setIntoTable("asset_value");
	}
	
	@Override
	public void populateIntoColumns() {}
	
	@Override
	public void setNumberOfInsertValues() {
		insert.setNumberOfInsertValues(3);
	}
	
}
