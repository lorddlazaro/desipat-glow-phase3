// Author ( Sharmaine Lim )

package database.builders.asset;

import java.sql.Date;

import java.util.Calendar;

import database.builders.CustomStatementBuilder;
import database.builders.Query;
import database.builders.QueryDirector;

// TODO Currently unfinished and untouched; copied from Action History

public class AssetQueryStatementBuilder extends CustomStatementBuilder {
	
	private Date dateFrom;
	private Date dateTo;
	private int assetID;
	
	public AssetQueryStatementBuilder() {
		super();
		dateFrom = null;
		dateTo = null;
		assetID = 0;
	}
	
	@Override
	public void setStatement() {
		/*QueryDirector queryDirector = new QueryDirector();
		AssetQueryBuilder queryBuilder = new AssetQueryBuilder();
		if (assetID > 0) {
			queryBuilder.hasAssetID();
		}
		if (dateFrom != null) {
			queryBuilder.hasDateFrom();
		}
		if (dateTo != null) {
			queryBuilder.hasDateTo();
		}
		queryDirector.setBuilder(queryBuilder);
		queryDirector.constructQuery();
		Query query = queryDirector.getQuery();
		
		customStatement.setStatement(query.toString());*/
	}
	
	@Override
	public void populateValues() {
		if (dateFrom != null) {
			customStatement.addValue(null, dateFrom);
		}
		if (dateTo != null) {
			customStatement.addValue(null, dateTo);
		}
		if (assetID > 0) {
			customStatement.addValue(null, assetID);
		}
	}
	
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	
	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = new Date(dateFrom.getTimeInMillis());
	}
	
	public void setDateTo(Calendar dateTo) {
		dateTo.set(Calendar.DATE, dateTo.get(Calendar.DATE) + 1);
		this.dateTo = new Date(dateTo.getTimeInMillis());
	}
	
}
