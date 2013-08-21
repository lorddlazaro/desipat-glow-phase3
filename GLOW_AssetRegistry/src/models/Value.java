// Author ( Sharmaine Lim )

package models;

public class Value {
	
	private Reference type;
	private Reference level;
	
	public Value(Reference type, Reference level) {
		this.type = type;
		this.level = level;
	}
	
	public Value() {
		this.type = null;
		this.level = null;
	}
	
	public Reference getType() {
		return type;
	}
	public void setType(Reference type) {
		this.type = type;
	}
	
	public Reference getLevel() {
		return level;
	}
	
	public void setLevel(Reference level) {
		this.level = level;
	}
	
}
