package database.builders.values;

import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class ValueInsertStatementBuilder extends CustomStatementBuilder {
	
	private int assetID;
	private int valueTypeID;
	private int valueLevelID;
	
	public ValueInsertStatementBuilder() {
		super();
		assetID = 0;
		valueTypeID = 0;
		valueLevelID = 0;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		ValueInsertBuilder insertBuilder = new ValueInsertBuilder();
		
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsert();
		Insert insert = insertDirector.getInsert();
		
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		customStatement.addValue("ID_Asset", assetID);
		customStatement.addValue("ID_ValueType", valueTypeID);
		customStatement.addValue("ID_ValueLevel", valueLevelID);
	}
	
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	
	public void setValueTypeID(int valueTypeID) {
		this.valueTypeID = valueTypeID;
	}
	
	public void setValueLevelID(int valueLevelID) {
		this.valueLevelID = valueLevelID;
	}
	
}
