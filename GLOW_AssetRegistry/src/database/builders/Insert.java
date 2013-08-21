// Author ( Sharmaine Lim )

package database.builders;

import java.util.ArrayList;

public class Insert {
	
	protected String intoTable;
	protected ArrayList<String> intoColumns;
	protected int nInsertValues;
	protected boolean addColumns;
	
	public Insert() {
		intoTable = null;
		intoColumns = new ArrayList<String>();
		nInsertValues = 0;
		addColumns = true;
	}
	
	public String toString() {
		
		if (intoTable == null || intoTable.isEmpty() || nInsertValues <= 0 ||
			(intoColumns.size() > 0 && intoColumns.size() != nInsertValues)) {
			return null;
		}
		
		String result = "INSERT INTO `" + intoTable + "` ";
		
		if (addColumns && intoColumns.size() > 0) {
			result += "(`";
			
			for (int i = 0; i < intoColumns.size(); i++) {
				result += intoColumns.get(i) + ((i < intoColumns.size() - 1) ? "`, `" : "`) ");
			}
		}
		
		result += "VALUES (";
		
		for (int i = 0; i < nInsertValues; i++) {
			result += ((i < nInsertValues - 1) ? "?, " : "?)");
		}
		
		return result;
	}
	
	public void setIntoTable(String intoTable) {
		if ( !(intoTable == null || intoTable.isEmpty()) ) {
			this.intoTable = intoTable;
		}
	}
	
	public void addIntoColumns(String intoColumn) {
		if ( !(intoColumn == null || intoColumn.isEmpty()) ) {
			intoColumns.add(intoColumn);
		}
	}
	
	public void setNumberOfInsertValues(int nInsertValues) {
		if (nInsertValues > 0) {
			this.nInsertValues = nInsertValues;
		}
	}
	
	public void noColumns() {
		addColumns = false;
	}
	
}
