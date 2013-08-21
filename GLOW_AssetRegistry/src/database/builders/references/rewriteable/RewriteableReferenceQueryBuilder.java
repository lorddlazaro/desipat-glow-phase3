// Author ( Sharmaine Lim )

package database.builders.references.rewriteable;

import database.builders.Query;
import database.builders.QueryBuilder;
import database.builders.QueryDirector;

public class RewriteableReferenceQueryBuilder extends QueryBuilder {
	
	protected String table;
	
	public RewriteableReferenceQueryBuilder() {
		super();
		table = null;
	}
	
	public void populateSelectedColumns() {
		query.addSelectedColumn("*");
	}
	
	public void populateFromTables() {
		query.addFromTable("reference");
	}
	
	public void populateWhereConditions() {
		if ( !(table == null || table.isEmpty()) ) {
			QueryDirector director = new QueryDirector();
			RewriteableReferenceSubqueryBuilder builder = new RewriteableReferenceSubqueryBuilder();
			builder.setTable(table);
			director.setBuilder(builder);
			director.constructQuery();
			Query subquery = director.getQuery();
			
			query.addWhereCondition("`ID` IN (" + subquery.toString() + ")");
			query.addWhereCondition("AND");
		}
		query.addWhereCondition("`Hidden` = ?");
	}
	
	public void populateOrderSequence() {}
	
	public void setTable(String table) {
		this.table = table;
	}
	
}
