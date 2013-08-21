// Original Author (Danny Cheng)

package database.builders;

import java.util.ArrayList;

public class Query {
	
	private ArrayList<String> selectedColumns;
	private ArrayList<String> fromTables;
	private ArrayList<String> whereConditions;
	private ArrayList<String> orderBySequence;
	
	public Query() {
		selectedColumns = new ArrayList<String>();
		fromTables = new ArrayList<String>();
		whereConditions = new ArrayList<String>();
		orderBySequence = new ArrayList<String>();
	}
	
	public String toString(){
		
		String result = "SELECT ";
		
		for (int i = 0; i < selectedColumns.size(); i++){
			result += selectedColumns.get(i) + ((i < selectedColumns.size() - 1) ? ", " : " ");
		}
		
		result += "FROM `";
		
		for (int i = 0; i < fromTables.size(); i++){
			result += fromTables.get(i) + ((i < fromTables.size() - 1) ? "`, `" : "` ");
		}
		
		if (whereConditions.size() > 0) {
			result += "WHERE ";
			
			for (int i = 0; i < whereConditions.size(); i++){
				result += whereConditions.get(i) + " ";
			}
		}
		
		if (orderBySequence.size() > 0) {
			result += "ORDER BY ";
			
			for (int i = 0; i < orderBySequence.size(); i++){
				result += orderBySequence.get(i) + ((i < orderBySequence.size() - 1) ? ", " : "");
			}
		}
		return result;
	}
	
	public void addSelectedColumn(String selectedColumn) {
		selectedColumns.add(selectedColumn);
	}
	
	public void addFromTable(String fromTable) {
		fromTables.add(fromTable);
	}
	
	public void addWhereCondition(String whereCondition) {
		whereConditions.add(whereCondition);
	}
	
	public void addOrderSequence(String orderSequence) {
		orderBySequence.add(orderSequence);
	}
	
}
