package database.builders.references;

import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class AssetRelationInsertStatementBuilder extends CustomStatementBuilder {
	
	private String table;
	private int assetID;
	private int referenceID;
	
	public AssetRelationInsertStatementBuilder() {
		super();
		table = null;
		assetID = 0;
		referenceID = 0;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		AssetRelationInsertBuilder insertBuilder = new AssetRelationInsertBuilder();
		insertBuilder.setTable(table);
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsertIgnore();
		Insert insert = insertDirector.getInsert();
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		customStatement.addValue("ID_Asset", assetID);
		customStatement.addValue(null, referenceID);
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	
	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}
	
}
