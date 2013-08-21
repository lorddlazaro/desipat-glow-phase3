// Author ( Sharmaine Lim )

package database.builders;

public class UpdateDirector {
	
	private UpdateBuilder builder;
	
	public UpdateDirector() {
		builder = null;
	}
	
	public void constructUpdate() {
		builder.createNewUpdate();
		builder.setUpdateTable();
		builder.populateSetColumns();
		builder.populateWhereConditions();
	}
	
	public Update getUpdate() {
		return builder.getUpdate();
	}
	
	public void setBuilder(UpdateBuilder builder) {
		this.builder = builder;
	}
	
}
