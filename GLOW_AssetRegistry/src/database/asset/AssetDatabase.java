// Author ( Sharmaine Lim )

package database.asset;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.abstracts.RewriteableDatabase;
import database.builders.CustomStatementDirector;
import database.builders.Query;
import database.builders.asset.AssetInsertStatementBuilder;

// TODO Currently lacks: search/get/select from asset table, and update asset table

public abstract class AssetDatabase extends RewriteableDatabase {
	
	private String assetName;
	private Calendar dateAcquired;
	private Calendar retentionPeriod;
	private int typeID;
	private int classificationID;
	private int maintenanceScheduleID;
	
	public AssetDatabase() {
		super();
		assetName = null;
		dateAcquired = null;
		retentionPeriod = null;
		typeID = 0;
		classificationID = 0;
		maintenanceScheduleID = 0;
	}
	
	@Override
	public int insert() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		AssetInsertStatementBuilder builder = new AssetInsertStatementBuilder();
		builder.setAssetName(assetName);
		builder.setDateAcquired(dateAcquired);
		builder.setRetentionPeriod(retentionPeriod);
		builder.setTypeID(typeID);
		builder.setClassificationID(classificationID);
		builder.setMaintenanceScheduleID(maintenanceScheduleID);
		builder.setConnection(conn);
		director.setBuilder(builder);
		director.constructCustomStatement();
		
		int resultingID = 0;
		
		PreparedStatement ps = director.getCustomStatement().prepareGivenStatement();
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			resultingID = rs.getInt(1);
		}
		rs.close();
		ps.close();
		
		return resultingID;
	}
	
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	public void setDateAcquired(Calendar dateAcquired) {
		this.dateAcquired = dateAcquired;
	}
	
	public void setRetentionPeriod(Calendar retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
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
