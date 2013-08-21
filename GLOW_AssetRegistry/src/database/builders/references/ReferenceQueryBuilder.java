// Author ( Sharmaine Lim )
// 
// Tables: person & storage_location

package database.builders.references;

import database.builders.QueryBuilder;

public class ReferenceQueryBuilder extends QueryBuilder {
	
	private String table;
	
	public ReferenceQueryBuilder() {
		super();
		table = null;
	}
	
	@Override
	public void populateSelectedColumns() {
		query.addSelectedColumn("ID");
	}
	
	@Override
	public void populateFromTables() {
		query.addFromTable(table);
	}
	
	@Override
	public void populateWhereConditions() {
		query.addWhereCondition("`Value` = ?");
	}
	
	@Override
	public void populateOrderSequence() {}
	
	public void setTable(String table) {
		this.table = table;
	}
	
}
