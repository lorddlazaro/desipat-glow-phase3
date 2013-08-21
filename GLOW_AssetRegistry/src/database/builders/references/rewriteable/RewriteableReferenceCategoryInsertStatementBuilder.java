package database.builders.references.rewriteable;

import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class RewriteableReferenceCategoryInsertStatementBuilder extends CustomStatementBuilder {
	
	private int referenceID;
	private String table;
	
	public RewriteableReferenceCategoryInsertStatementBuilder() {
		super();
		referenceID = 0;
		table = null;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		RewriteableReferenceCategoryInsertBuilder insertBuilder = new RewriteableReferenceCategoryInsertBuilder();
		insertBuilder.setTable(table);
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsert();
		Insert insert = insertDirector.getInsert();
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		if (referenceID > 0) {
			customStatement.addValue("ID", referenceID);
		}
	}
	
	public void setID(int referenceID) {
		this.referenceID = referenceID;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
}
