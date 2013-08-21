package database.builders;

public class InsertIgnore extends Insert {
	
	@Override
	public String toString() {
		
		if (intoTable == null || intoTable.isEmpty() || nInsertValues <= 0 ||
			(intoColumns.size() > 0 && intoColumns.size() != nInsertValues)) {
			return null;
		}
		
		String result = "INSERT IGNORE INTO `" + intoTable + "` ";
		
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
	
}
