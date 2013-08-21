// Author ( Sharmaine Lim )

package database.builders.references.rewriteable;

import database.builders.CustomStatementBuilder;
import database.builders.Query;
import database.builders.QueryDirector;

public class RewriteableReferenceQueryStatementBuilder extends CustomStatementBuilder {
	
	private String table;
	
	public RewriteableReferenceQueryStatementBuilder() {
		super();
		table = null;
	}
	
	@Override
	public void setStatement() {
		QueryDirector queryDirector = new QueryDirector();
		RewriteableReferenceQueryBuilder queryBuilder = new RewriteableReferenceQueryBuilder();
		if ( !(table == null || table.isEmpty()) ) {
			queryBuilder.setTable(table);
		}
		queryDirector.setBuilder(queryBuilder);
		queryDirector.constructQuery();
		Query query = queryDirector.getQuery();
		
		customStatement.setStatement(query.toString());
	}
	
	@Override
	public void populateValues() {
		customStatement.addValue("Hidden", false);
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
}
