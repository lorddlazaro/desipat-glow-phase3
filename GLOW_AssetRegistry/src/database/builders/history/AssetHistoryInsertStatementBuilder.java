// Author ( Sharmaine Lim )

package database.builders.history;

import models.AssetLogEntry;
import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class AssetHistoryInsertStatementBuilder extends CustomStatementBuilder {
	
	private int logID;
	private AssetLogEntry entry;
	
	public AssetHistoryInsertStatementBuilder() {
		super();
		logID = 0;
		entry = null;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		AssetHistoryInsertBuilder insertBuilder = new AssetHistoryInsertBuilder();
		
		if (entry != null && entry.getAffectedFields() != null) {
			insertBuilder.hasField();
		}
		
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsert();
		Insert insert = insertDirector.getInsert();
		
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		if (entry != null) {
			customStatement.addValue("ID", logID);
			customStatement.addValue("ID_Asset", entry.getAssetID());
			if (entry.getAffectedFields() != null) {
				customStatement.addValue("AffectedField", entry.getAffectedFields().get(0));
			}
		}
	}
	
	public void setLogID(int logID) {
		this.logID = logID;
	}
	
	public void setEntry(AssetLogEntry entry) {
		this.entry = entry;
	}
	
}
