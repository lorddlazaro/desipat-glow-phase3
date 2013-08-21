// Author ( Sharmaine Lim )

package database.builders.history;

import database.builders.QueryBuilder;

public class ReferenceHistoryQueryBuilder extends QueryBuilder {
	
	public ReferenceHistoryQueryBuilder() {
		super();
	}
	
	@Override
	public void populateSelectedColumns() {
		query.addSelectedColumn("*");
	}
	
	@Override
	public void populateFromTables() {
		query.addFromTable("log");
		query.addFromTable("log_reference");
		query.addFromTable("reference");
	}
	
	@Override
	public void populateWhereConditions() {
		query.addWhereCondition("`log`.`ID` = `log_reference`.`ID`");
		query.addWhereCondition("AND");
		query.addWhereCondition("`log_reference`.`ID_Reference` = `reference`.`ID`");
	}
	
	@Override
	public void populateOrderSequence() {
		query.addOrderSequence("`Timestamp` DESC");
	}
	
}
