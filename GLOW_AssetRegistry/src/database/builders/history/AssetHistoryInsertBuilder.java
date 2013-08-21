// Author ( Sharmaine Lim )

package database.builders.history;

import database.builders.InsertBuilder;

public class AssetHistoryInsertBuilder extends InsertBuilder {
	
	private boolean hasField;
	
	public AssetHistoryInsertBuilder() {
		super();
		hasField = false;
	}
	
	@Override
	public void setIntoTable() {
		insert.setIntoTable("log_asset");
	}
	
	@Override
	public void populateIntoColumns() {
		insert.addIntoColumns("ID");
		insert.addIntoColumns("ID_Asset");
		if (hasField) {
			insert.addIntoColumns("AffectedField");
		}
	}
	
	@Override
	public void setNumberOfInsertValues() {
		if (hasField) {
			insert.setNumberOfInsertValues(3);
		}
		else {
			insert.setNumberOfInsertValues(2);
		}
	}
	
	public void hasField() {
		hasField = true;
	}
	
}
