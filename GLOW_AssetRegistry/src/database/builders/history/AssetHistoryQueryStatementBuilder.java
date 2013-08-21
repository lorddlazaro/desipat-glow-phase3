// Author ( Sharmaine Lim )

package database.builders.history;

import database.builders.CustomStatementBuilder;
import database.builders.Query;
import database.builders.QueryDirector;

public class AssetHistoryQueryStatementBuilder extends CustomStatementBuilder {
	
	private int assetID;
	
	public AssetHistoryQueryStatementBuilder() {
		super();
		assetID = 0;
	}
	
	@Override
	public void setStatement() {
		QueryDirector queryDirector = new QueryDirector();
		AssetHistoryQueryBuilder queryBuilder = new AssetHistoryQueryBuilder();
		if (assetID > 0) {
			queryBuilder.hasAssetID();
		}
		queryDirector.setBuilder(queryBuilder);
		queryDirector.constructQuery();
		Query query = queryDirector.getQuery();
		
		customStatement.setStatement(query.toString());
	}
	
	@Override
	public void populateValues() {
		if (assetID > 0) {
			customStatement.addValue(null, assetID);
		}
	}
	
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	
}
