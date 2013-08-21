// Author ( Sharmaine Lim )

package database.builders.history;

import database.builders.InsertBuilder;

public class ReferenceHistoryInsertBuilder extends InsertBuilder {
	
	@Override
	public void setIntoTable() {
		insert.setIntoTable("log_reference");
	}
	
	@Override
	public void populateIntoColumns() {}
	
	@Override
	public void setNumberOfInsertValues() {
		insert.setNumberOfInsertValues(3);
	}
	
}
