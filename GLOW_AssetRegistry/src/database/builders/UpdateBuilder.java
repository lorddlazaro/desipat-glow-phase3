// Author ( Sharmaine Lim )

package database.builders;

public abstract class UpdateBuilder {
	
	protected Update update;
	
	public UpdateBuilder() {
		update = null;
	}
	
	public abstract void setUpdateTable();
	public abstract void populateSetColumns();
	public abstract void populateWhereConditions();
	
	public void createNewUpdate() {
		update = new Update();
	}
	
	public Update getUpdate() {
		return update;
	}
	
}
