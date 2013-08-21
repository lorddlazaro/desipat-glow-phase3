// Author ( Sharmaine Lim )

package database.builders.references.rewriteable;

import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class RewriteableReferenceInsertStatementBuilder extends CustomStatementBuilder {
	
	private String referenceValue;
	
	public RewriteableReferenceInsertStatementBuilder() {
		super();
		referenceValue = null;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		RewriteableReferenceInsertBuilder insertBuilder = new RewriteableReferenceInsertBuilder();
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsert();
		Insert insert = insertDirector.getInsert();
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		if ( !(referenceValue == null || referenceValue.isEmpty()) ) {
			customStatement.addValue("Value", referenceValue);
			customStatement.addValue("Hidden", false);
		}
	}
	
	public void setValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}
	
}
