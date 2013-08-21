// Author ( Sharmaine Lim )

package database.builders.history;

import java.sql.Timestamp;

import models.LogEntry;
import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class HistoryInsertStatementBuilder extends CustomStatementBuilder {
	
	private LogEntry entry;
	
	public HistoryInsertStatementBuilder() {
		super();
		entry = null;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		HistoryInsertBuilder insertBuilder = new HistoryInsertBuilder();
		
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsert();
		Insert insert = insertDirector.getInsert();
		
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		if (entry != null) {
			Timestamp timestamp = new Timestamp(entry.getTimestamp().getTimeInMillis());
			customStatement.addValue("Timestamp", timestamp);
			customStatement.addValue("Action", entry.getAction());
		}
	}
	
	public void setEntry(LogEntry entry) {
		this.entry = entry;
	}
	
}
