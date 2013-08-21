// Author ( Sharmaine Lim )

package database.builders;

import java.util.ArrayList;

public class Update {
	
	private String updateTable;
	private ArrayList<String> setColumns;
	private ArrayList<String> whereConditions;
	
	public Update() {
		updateTable = null;
		setColumns = new ArrayList<String>();
		whereConditions = new ArrayList<String>();
	}
	
	public String toString() {
		if (updateTable == null || updateTable.isEmpty() || setColumns.size() <= 0) {
			return null;
		}
		
		String result = "UPDATE `" + updateTable + "` SET `";
		
		for (int i = 0; i < setColumns.size(); i++) {
			result += setColumns.get(i) + "` = ?" + ((i < setColumns.size() - 1) ? ", `" : " ");
		}
		
		if (whereConditions.size() > 0) {
			result += "WHERE ";
			
			for (String condition : whereConditions) {
				result += condition + " ";
			}
		}
		
		return result;
	}
	
	public void setUpdateTable(String updateTable) {
		this.updateTable = updateTable;
	}
	
	public void addSetColumn(String setColumn) {
		setColumns.add(setColumn);
	}
	
	public void addWhereCondition(String whereCondition) {
		whereConditions.add(whereCondition);
	}
	
}
