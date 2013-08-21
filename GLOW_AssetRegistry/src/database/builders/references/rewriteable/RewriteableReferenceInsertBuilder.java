// Author ( Sharmaine Lim )

package database.builders.references.rewriteable;

import database.builders.InsertBuilder;

public class RewriteableReferenceInsertBuilder extends InsertBuilder {
	
	@Override
	public void setIntoTable() {
		insert.setIntoTable("reference");
	}
	
	@Override
	public void populateIntoColumns() {
		insert.addIntoColumns("Value");
		insert.addIntoColumns("Hidden");
	}
	
	@Override
	public void setNumberOfInsertValues() {
		insert.setNumberOfInsertValues(2);
	}
	
}
