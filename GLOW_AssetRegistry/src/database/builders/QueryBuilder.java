// Original Author (Danny Cheng)

package database.builders;

public abstract class QueryBuilder {
	
	protected Query query;
	
	public QueryBuilder() {
		query = null;
	}
	
	public abstract void populateSelectedColumns();
	public abstract void populateFromTables();
	public abstract void populateWhereConditions();
	public abstract void populateOrderSequence();
	
	public void createNewQuery() {
		query = new Query();
	}
	
	public Query getQuery() {
		return query;
	}
	
}
