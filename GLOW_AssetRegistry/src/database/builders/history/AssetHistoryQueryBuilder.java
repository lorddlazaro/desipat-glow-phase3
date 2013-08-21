// Author ( Sharmaine Lim )

package database.builders.history;

import database.builders.QueryBuilder;

public class AssetHistoryQueryBuilder extends QueryBuilder {
	
	private boolean hasAssetID;
	
	public AssetHistoryQueryBuilder() {
		super();
		hasAssetID = false;
	}
	
	@Override
	public void populateSelectedColumns() {
		query.addSelectedColumn("*");
	}
	
	@Override
	public void populateFromTables() {
		query.addFromTable("log");
		query.addFromTable("log_asset");
	}
	
	@Override
	public void populateWhereConditions() {
		query.addWhereCondition("`log`.`ID` = `log_asset`.`ID`");
		if (hasAssetID) {
			query.addWhereCondition("AND");
			query.addWhereCondition("`ID_Asset` = ?");
		}
	}
	
	@Override
	public void populateOrderSequence() {
		query.addOrderSequence("`Timestamp` DESC");
	}
	
	public void hasAssetID() {
		hasAssetID = true;
	}
	
}
