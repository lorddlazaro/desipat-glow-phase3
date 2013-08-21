// Author ( Sharmaine Lim )

package database.builders;

public class ColumnValue {
	
	private String column;
	private Object value;
	
	public ColumnValue() {
		column = null;
		value = null;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
}
