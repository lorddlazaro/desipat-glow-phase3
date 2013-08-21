package models;

public class ReferenceLogEntry extends LogEntry {
	
	private String table;
	private Reference reference;
	
	public ReferenceLogEntry() {
		super();
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	
	public String getTable() {
		return table;
	}
	
	public Reference getReference() {
		return reference;
	}
	
}
