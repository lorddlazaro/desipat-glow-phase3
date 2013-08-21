// Author ( Sharmaine Lim )

package database.builders.history;

import models.ReferenceLogEntry;
import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class ReferenceHistoryInsertStatementBuilder extends CustomStatementBuilder {
	
	private int logID;
	private ReferenceLogEntry entry;
	
	public ReferenceHistoryInsertStatementBuilder() {
		super();
		logID = 0;
		entry = null;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		ReferenceHistoryInsertBuilder insertBuilder = new ReferenceHistoryInsertBuilder();
		
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsert();
		Insert insert = insertDirector.getInsert();
		
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		if (entry != null) {
			customStatement.addValue("ID", logID);
			customStatement.addValue("Field_Reference", entry.getTable());
			customStatement.addValue("ID_Reference", entry.getReference().getIdentifier());
		}
	}
	
	public void setLogID(int logID) {
		this.logID = logID;
	}
	
	public void setEntry(ReferenceLogEntry entry) {
		this.entry = entry;
	}
	
}
