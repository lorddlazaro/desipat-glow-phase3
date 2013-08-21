// Author ( Sharmaine Lim )

package database.builders;

public class InsertDirector {
	
	private InsertBuilder builder;
	
	public InsertDirector() {
		builder = null;
	}
	
	public void constructInsert() {
		builder.createNewInsert();
		builder.setIntoTable();
		builder.populateIntoColumns();
		builder.setNumberOfInsertValues();
	}
	
	public void constructInsertIgnore() {
		builder.createNewInsertIgnore();
		builder.setIntoTable();
		builder.populateIntoColumns();
		builder.setNumberOfInsertValues();
	}
	
	public Insert getInsert() {
		return builder.getInsert();
	}
	
	public void setBuilder(InsertBuilder builder) {
		this.builder = builder;
	}
	
}
