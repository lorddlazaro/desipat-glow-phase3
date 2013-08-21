// Author ( Sharmaine Lim )

package models;

public class Reference {
	
	private int identifier;
	private String value;
	private boolean hidden;
	
	public Reference() {
		identifier = 0;
		value = null;
		hidden = false;
	}
	
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public int getIdentifier() {
		return identifier;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isHidden() {
		return hidden;
	}
	
}
