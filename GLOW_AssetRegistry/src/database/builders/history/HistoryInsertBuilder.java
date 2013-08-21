// Author ( Sharmaine Lim )

package database.builders.history;

import database.builders.InsertBuilder;

public class HistoryInsertBuilder extends InsertBuilder {
	
	@Override
	public void setIntoTable() {
		insert.setIntoTable("log");
	}
	
	@Override
	public void populateIntoColumns() {
		insert.addIntoColumns("Timestamp");
		insert.addIntoColumns("Action");
	}
	
	@Override
	public void setNumberOfInsertValues() {
		insert.setNumberOfInsertValues(2);
	}
	
}
