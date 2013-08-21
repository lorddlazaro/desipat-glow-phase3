// Author ( Sharmaine Lim )

package database.builders.asset;

import java.sql.Date;
import java.util.Calendar;

import database.builders.CustomStatementBuilder;
import database.builders.Insert;
import database.builders.InsertDirector;

public class AssetInsertStatementBuilder extends CustomStatementBuilder {
	
	private String assetName;
	private Date dateAcquired;
	private Date retentionPeriod;
	private int typeID;
	private int classificationID;
	private int maintenanceScheduleID;
	
	public AssetInsertStatementBuilder() {
		super();
		assetName = null;
		dateAcquired = null;
		retentionPeriod = null;
		typeID = 0;
		classificationID = 0;
		maintenanceScheduleID = 0;
	}
	
	@Override
	public void setStatement() {
		InsertDirector insertDirector = new InsertDirector();
		AssetInsertBuilder insertBuilder = new AssetInsertBuilder();
		
		insertDirector.setBuilder(insertBuilder);
		insertDirector.constructInsert();
		Insert insert = insertDirector.getInsert();
		
		customStatement.setStatement(insert.toString());
	}
	
	@Override
	public void populateValues() {
		customStatement.addValue("Name", assetName);
		customStatement.addValue("DateAcquired", dateAcquired);
		customStatement.addValue("RetentionPeriod", retentionPeriod);
		customStatement.addValue("ID_Type", typeID);
		customStatement.addValue("ID_Classification", classificationID);
		customStatement.addValue("ID_MaintenanceSchedule", maintenanceScheduleID);
	}
	
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	public void setDateAcquired(Calendar dateAcquired) {
		this.dateAcquired = new Date(dateAcquired.getTimeInMillis());
	}
	
	public void setRetentionPeriod(Calendar retentionPeriod) {
		this.retentionPeriod = new Date(retentionPeriod.getTimeInMillis());
	}
	
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	
	public void setClassificationID(int classificationID) {
		this.classificationID = classificationID;
	}
	
	public void setMaintenanceScheduleID(int maintenanceScheduleID) {
		this.maintenanceScheduleID = maintenanceScheduleID;
	}
	
}
