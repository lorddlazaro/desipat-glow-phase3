// Author ( Sharmaine Lim )

package database.builders.references.rewriteable;

public class RewriteableReferenceSubqueryBuilder extends RewriteableReferenceQueryBuilder {
	
	@Override
	public void populateFromTables() {
		query.addFromTable(table);
	}
	
	@Override
	public void populateWhereConditions() {}
	
}
