// Author ( Sharmaine Lim )

package database.builders.references.rewriteable;

import database.builders.UpdateBuilder;

public class RewriteableReferenceUpdateBuilder extends UpdateBuilder {
	
	public RewriteableReferenceUpdateBuilder() {
		super();
	}
	
	@Override
	public void setUpdateTable() {
		update.setUpdateTable("reference");
	}
	
	@Override
	public void populateSetColumns() {
		update.addSetColumn("Value");
		update.addSetColumn("Hidden");
	}
	
	@Override
	public void populateWhereConditions() {
		update.addWhereCondition("`ID` = ?");
	}
	
}
