// Author ( Sharmaine Lim )

package database.builders;

public abstract class InsertBuilder {
	
	protected Insert insert;
	
	public InsertBuilder() {
		insert = null;
	}
	
	public abstract void setIntoTable();
	public abstract void populateIntoColumns();
	public abstract void setNumberOfInsertValues();
	
	public void createNewInsert() {
		insert = new Insert();
	}
	
	public void createNewInsertIgnore() {
		insert = new InsertIgnore();
	}
	
	public Insert getInsert() {
		return insert;
	}
	
}
