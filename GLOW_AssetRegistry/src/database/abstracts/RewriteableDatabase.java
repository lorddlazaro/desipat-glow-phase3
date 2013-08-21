// Author ( Sharmaine Lim )

package database.abstracts;

import java.sql.SQLException;

import models.LogEntry;

public abstract class RewriteableDatabase extends RecordableDatabase {
	
	protected LogEntry entry;
	
	public RewriteableDatabase() {
		super();
	}
	
	public abstract void update() throws SQLException;
	
	public void setEntry(LogEntry entry) {
		this.entry = entry;
	}
	
	public LogEntry getEntry() {
		return entry;
	}
	
}
