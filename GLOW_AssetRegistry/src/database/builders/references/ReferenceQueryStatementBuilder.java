// Author ( Sharmaine Lim )

package database.builders.references;

import database.builders.CustomStatementBuilder;
import database.builders.Query;
import database.builders.QueryDirector;

public class ReferenceQueryStatementBuilder extends CustomStatementBuilder {
	
	private String table;
	private String value;
	
	public ReferenceQueryStatementBuilder() {
		super();
		table = null;
		value = null;
	}
	
	@Override
	public void setStatement() {
		QueryDirector queryDirector = new QueryDirector();
		ReferenceQueryBuilder queryBuilder = new ReferenceQueryBuilder();
		queryBuilder.setTable(table);
		queryDirector.setBuilder(queryBuilder);
		queryDirector.constructQuery();
		Query query = queryDirector.getQuery();
		
		customStatement.setStatement(query.toString());
	}
	
	@Override
	public void populateValues() {
		customStatement.addValue("Value", value);
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
