// Author ( Sharmaine Lim )

package database.builders.history;

import java.sql.Date;

import java.util.Calendar;

import database.builders.CustomStatementBuilder;
import database.builders.Query;
import database.builders.QueryDirector;
import database.builders.history.HistoryQueryBuilder;

public class HistoryQueryStatementBuilder extends CustomStatementBuilder {
	
	private Date dateFrom;
	private Date dateTo;
	
	public HistoryQueryStatementBuilder() {
		super();
		dateFrom = null;
		dateTo = null;
	}
	
	@Override
	public void setStatement() {
		QueryDirector queryDirector = new QueryDirector();
		HistoryQueryBuilder queryBuilder = new HistoryQueryBuilder();
		if (dateFrom != null) {
			queryBuilder.hasDateFrom();
		}
		if (dateTo != null) {
			queryBuilder.hasDateTo();
		}
		queryDirector.setBuilder(queryBuilder);
		queryDirector.constructQuery();
		Query query = queryDirector.getQuery();
		
		customStatement.setStatement(query.toString());
	}
	
	@Override
	public void populateValues() {
		if (dateFrom != null) {
			customStatement.addValue(null, dateFrom);
		}
		if (dateTo != null) {
			customStatement.addValue(null, dateTo);
		}
	}
	
	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = new Date(dateFrom.getTimeInMillis());
	}
	
	public void setDateTo(Calendar dateTo) {
		dateTo.set(Calendar.DATE, dateTo.get(Calendar.DATE) + 1);
		this.dateTo = new Date(dateTo.getTimeInMillis());
	}
	
}
