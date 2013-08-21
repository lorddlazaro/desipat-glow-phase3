package database.builders.references;

import database.builders.InsertBuilder;

public class AssetRelationInsertBuilder extends InsertBuilder {
	
	public String table;
	
	public AssetRelationInsertBuilder() {
		super();
		table = null;
	}
	
	@Override
	public void setIntoTable() {
		insert.setIntoTable(table);
	}
	
	@Override
	public void populateIntoColumns() {
		insert.addIntoColumns("ID_Asset");
		if (table.equals("asset_location")) {
			insert.addIntoColumns("ID_StorageLocation");
		}
		else {
			insert.addIntoColumns("ID_Person");
		}
	}
	
	@Override
	public void setNumberOfInsertValues() {
		insert.setNumberOfInsertValues(2);
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
}
