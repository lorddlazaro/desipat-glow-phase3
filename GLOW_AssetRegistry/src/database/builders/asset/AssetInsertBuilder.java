// Author ( Arianne Ong )

package database.builders.asset;

import database.builders.InsertBuilder;

public class AssetInsertBuilder extends InsertBuilder {
	
	public AssetInsertBuilder() {
		super();
	}
	
	public void setIntoTable() {
		insert.setIntoTable("asset");
	}
	
	public void populateIntoColumns() {
		insert.addIntoColumns("Name");
		insert.addIntoColumns("DateAcquired");
		insert.addIntoColumns("RetentionPeriod");
		insert.addIntoColumns("ID_Type");
		insert.addIntoColumns("ID_Classification");
		insert.addIntoColumns("ID_MaintenanceSchedule");
	}
	
	public void setNumberOfInsertValues() {
		insert.setNumberOfInsertValues(6);
		
	}
	
}
