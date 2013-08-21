// Author ( Sharmaine Lim )

package database.builders.history;

import database.builders.CustomStatementBuilder;
import database.builders.Query;
import database.builders.QueryDirector;

public class ReferenceHistoryQueryStatementBuilder extends CustomStatementBuilder {
	
	public ReferenceHistoryQueryStatementBuilder() {
		super();
	}
	
	@Override
	public void setStatement() {
		QueryDirector queryDirector = new QueryDirector();
		ReferenceHistoryQueryBuilder queryBuilder = new ReferenceHistoryQueryBuilder();
		queryDirector.setBuilder(queryBuilder);
		queryDirector.constructQuery();
		Query query = queryDirector.getQuery();
		
		customStatement.setStatement(query.toString());
	}
	
	@Override
	public void populateValues() {}
	
}
