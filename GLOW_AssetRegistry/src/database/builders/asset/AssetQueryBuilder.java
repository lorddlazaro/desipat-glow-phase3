// Author ( Sharmaine Lim )

package database.builders.asset;

import database.builders.QueryBuilder;
import database.builders.QueryDirector;

// TODO Currently lacks: everything

public class AssetQueryBuilder extends QueryBuilder {

	public AssetQueryBuilder() {
		super();
	}

	@Override
	public void populateSelectedColumns() {
		query.addSelectedColumn("*");
	}

	@Override
	public void populateFromTables() {
		query.addFromTable("asset");
	}

	@Override
	public void populateWhereConditions() {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateOrderSequence() {
		// TODO Auto-generated method stub

	}

}
