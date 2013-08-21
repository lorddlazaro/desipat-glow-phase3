// Author ( Sharmaine Lim )

package database.builders.history;

import database.builders.QueryBuilder;

public class HistoryQueryBuilder extends QueryBuilder {
	
	private boolean dateFrom;
	private boolean dateTo;
	
	public HistoryQueryBuilder() {
		super();
		dateFrom = false;
		dateTo = false;
	}
	
	public void populateSelectedColumns() {
		query.addSelectedColumn("*");
	}
	
	public void populateFromTables() {
		query.addFromTable("log");
		query.addFromTable("log_asset");
		query.addFromTable("log_reference");
		query.addFromTable("reference");
	}
	
	public void populateWhereConditions() {
		query.addWhereCondition("(`log`.`ID` = `log_asset`.`ID` OR `log`.ID = `log_reference`.`ID`" +
								" OR `log_reference`.`ID_Reference` = `reference`.`ID`)");
		
		if (dateFrom) {
			query.addWhereCondition("AND");
			query.addWhereCondition("`Timestamp` >= ?");
		}
		
		if (dateTo) {
			query.addWhereCondition("AND");
			query.addWhereCondition("`Timestamp` < ?");
		}
	}
	
	public void populateOrderSequence() {
		query.addOrderSequence("`Timestamp` DESC");
	}
	
	public void hasDateFrom() {
		dateFrom = true;
	}
	
	public void hasDateTo() {
		dateTo = true;
	}
	
}
