// Author ( Sharmaine Lim )

package database.builders;

public class QueryDirector {
	
	private QueryBuilder builder;
	
	public QueryDirector() {
		builder = null;
	}
	
	public void constructQuery() {
		builder.createNewQuery();
		builder.populateSelectedColumns();
		builder.populateFromTables();
		builder.populateWhereConditions();
		builder.populateOrderSequence();
	}
	
	public Query getQuery() {
		return builder.getQuery();
	}
	
	public void setBuilder(QueryBuilder builder) {
		this.builder = builder;
	}
	
}
