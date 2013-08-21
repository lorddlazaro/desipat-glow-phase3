// Author ( Sharmaine Lim )

package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.*;
import database.abstracts.RewriteableDatabase;
import database.asset.AssetDatabase;
import database.asset.AssetRelationsDatabase;
import database.asset.ValueDatabase;
import database.builders.QueryDirector;
import database.connection.ConnectionFactory;
import database.history.AssetHistoryDatabase;
import database.history.HistoryDatabase;
import database.history.ReferenceHistoryDatabase;
import database.referencetables.ReferenceDatabase;
import database.referencetables.RewriteableReferenceDatabase;

// TODO Currently lacks: select from asset; action history tables not returning reference history ;__;

public class Database {
	
	private RewriteableDatabase database;
	
	public Database() {
		database = null;
	}
	
	public void insertClassification(String classification) throws Exception {
		insertReference("classification", classification);
	}
	
	public void insertMaintenanceSchedule(String maintenanceSchedule) throws Exception {
		insertReference("maintenance_schedule", maintenanceSchedule);
	}
	
	public void insertType(String type) throws Exception {
		insertReference("type", type);
	}
	
	public void insertValueLevel(String valueLevel) throws Exception {
		insertReference("value_level", valueLevel);
	}
	
	public void insertValueType(String valueType) throws Exception {
		insertReference("value_type", valueType);
		// TODO Must add to asset_value db
	}
	
	public ArrayList<Reference> getAllClassification() {
		return selectReference("classification");
	}
	
	public ArrayList<Reference> getAllMaintenanceSchedule() {
		return selectReference("maintenance_schedule");
	}
	
	public ArrayList<Reference> getAllType() {
		return selectReference("type");
	}
	
	public ArrayList<Reference> getAllValueLevel() {
		return selectReference("value_level");
	}
	
	public ArrayList<Reference> getAllValueType() {
		return selectReference("value_type");
	}
	
	public void updateClassification(Reference oldReference, Reference newReference) throws Exception {
		updateReference("classification", oldReference, newReference);
	}
	
	public void updateMaintenanceSchedule(Reference oldReference, Reference newReference) throws Exception {
		updateReference("maintenance_schedule", oldReference, newReference);
	}
	
	public void updateType(Reference oldReference, Reference newReference) throws Exception {
		updateReference("type", oldReference, newReference);
	}
	
	public void updateValueLevel(Reference oldReference, Reference newReference) throws Exception {
		updateReference("value_level", oldReference, newReference);
	}
	
	public void updateValueType(Reference oldReference, Reference newReference) throws Exception {
		updateReference("value_type", oldReference, newReference);
	}
	
	public ArrayList<AssetLogEntry> getAllActions() {
		// TODO should actually be for asset and reference history, but mysql hates me
		
		ArrayList<AssetLogEntry> result = null;
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		
		try {
			ArrayList<Object> subresult = null;
			
			AssetHistoryDatabase db = new AssetHistoryDatabase();
			db.setConnection(conn);
			subresult = db.select();
			
			result = new ArrayList<AssetLogEntry>();
			for (Object item : subresult) {
				result.add((AssetLogEntry)item);
			}
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}
	
	public ArrayList<AssetLogEntry> getActionsFromPeriod(Calendar dateFrom, Calendar dateTo) throws SQLException {
		// TODO should actually be for asset and reference history, but mysql hates me
		
		String query = "";
		int n = 0;
		
		if (dateFrom != null) {
			query += "AND `Timestamp` >= ? ";
		}
		
		if (dateTo != null) {
			query += "AND `Timestamp` < ?";
		}
		
		ArrayList<AssetLogEntry> entryList = null;
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM `log`, `log_asset`" +
				" WHERE `log`.`ID` = `log_asset`.`ID` " + query);
		if (dateFrom != null) {
			ps.setTimestamp(++n, new Timestamp(dateFrom.getTimeInMillis()));
		}
		if (dateTo != null) {
			dateTo.set(Calendar.MONTH, dateTo.get(Calendar.MONTH) + 1);
			ps.setTimestamp(++n, new Timestamp(dateTo.getTimeInMillis()));
		}
		ResultSet rs = ps.executeQuery();
		
		entryList = new ArrayList<AssetLogEntry>();
		
		if (rs.next()) {
			int logID = 0;
			
			while (!rs.isAfterLast()) {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(rs.getTimestamp("Timestamp").getTime());
				
				logID = rs.getInt("ID");
				AssetLogEntry currentEntry = null;
				
				currentEntry = new AssetLogEntry();
				currentEntry.setTimestamp(cal);
				currentEntry.setAction(rs.getString("Action"));
				currentEntry.setAssetID(rs.getInt("ID_Asset"));
				
				ArrayList<String> affectedFields = new ArrayList<String>();
				while (!rs.isAfterLast() && logID == rs.getInt("ID")) {
					affectedFields.add(rs.getString("AffectedField"));
					rs.next();
				}
				currentEntry.setAffectedFields(affectedFields);
				
				entryList.add(currentEntry);
			}
		}
		
		rs.close();
		ps.close();
		conn.close();
		return entryList;
	}
	
	public ArrayList<AssetLogEntry> getAssetActions(int assetID) {
		ArrayList<AssetLogEntry> result = null;
		AssetLogEntry entry = new AssetLogEntry();
		entry.setAssetID(assetID);
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		
		try {
			ArrayList<Object> subresult = null;
			
			AssetHistoryDatabase db = new AssetHistoryDatabase();
			db.setConnection(conn);
			db.setEntry(entry);
			subresult = db.select();
			
			result = new ArrayList<AssetLogEntry>();
			for (Object item : subresult) {
				result.add((AssetLogEntry)item);
			}
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}
	
	public void insertAsset(Asset asset) throws Exception {
		// NOTE: return_generated_keys only returns one key if ps used twice
		// NOTE: commits only matter for updates; select doesn't need commit
		
		if (asset.getAssetName() == null || asset.getAssetName().isEmpty()) {
			throw new Exception("Invalid input.");
		}
		
		if (asset.getType() == null || asset.getType().getIdentifier() == 0) {
			throw new Exception("Invalid input.");
		}
		
		if (asset.getDateAcquired() == null || asset.getRetentionPeriod() == null) {
			throw new Exception("Invalid input.");
		}
		
		if (asset.getMaintenanceSchedule() == null || asset.getMaintenanceSchedule().getIdentifier() == 0) {
			throw new Exception("Invalid input.");
		}
		
		if (asset.getClassification() == null || asset.getClassification().getIdentifier() == 0) {
			throw new Exception("Invalid input.");
		}
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		
		try {
			conn.setAutoCommit(false);
			String referenceValue = null;
			
			referenceValue = asset.getOwner().get(asset.getOwner().size() - 1);
			int owner = getReferenceID(conn, "person", referenceValue);
			
			referenceValue = asset.getCustodian().get(asset.getCustodian().size() - 1);
			int custodian = getReferenceID(conn, "person", referenceValue);
			
			referenceValue = asset.getStorageLocation().get(asset.getStorageLocation().size() - 1);
			int storageLocation = getReferenceID(conn, "storage_location", referenceValue);
			
			AssetDatabase db = new AssetDatabase();
			db.setConnection(conn);
			db.setAssetName(asset.getAssetName());
			db.setDateAcquired(asset.getDateAcquired());
			db.setRetentionPeriod(asset.getRetentionPeriod());
			db.setTypeID(asset.getType().getIdentifier());
			db.setClassificationID(asset.getClassification().getIdentifier());
			db.setMaintenanceScheduleID(asset.getMaintenanceSchedule().getIdentifier());
			asset.setIdentifier(db.insert());
			
			AssetRelationsDatabase relationsDB = new AssetRelationsDatabase();
			relationsDB.setConnection(conn);
			relationsDB.setTable("owner");
			relationsDB.setAssetID(asset.getIdentifier());
			relationsDB.setReferenceID(owner);
			relationsDB.insert();
			
			relationsDB.setTable("custodian");
			relationsDB.setAssetID(asset.getIdentifier());
			relationsDB.setReferenceID(custodian);
			relationsDB.insert();
			
			relationsDB.setTable("asset_location");
			relationsDB.setAssetID(asset.getIdentifier());
			relationsDB.setReferenceID(storageLocation);
			relationsDB.insert();
			
			ValueDatabase valuesDB = new ValueDatabase();
			valuesDB.setConnection(conn);
			valuesDB.setAssetID(asset.getIdentifier());
			for (Value value : asset.getValue()) {
				valuesDB.setValueTypeID(value.getType().getIdentifier());
				valuesDB.setValueLevelID(value.getLevel().getIdentifier());
				valuesDB.insert();
			}
			
			database = db;
			
			AssetLogEntry entry = new AssetLogEntry();
			entry.setTimestamp(Calendar.getInstance());
			entry.setAssetID(asset.getIdentifier());
			entry.setAction("inserted asset");
			database.setEntry(entry);
			this.logAction(conn);
		}
		catch (Exception ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public ArrayList<Asset> getAllAssets() throws SQLException {
		ArrayList<Asset> assetList = null;
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM `asset`");
		ResultSet rs = ps.executeQuery();
		
		assetList = new ArrayList<Asset>();
		
		while (rs.next()) {
			Asset currentAsset = new Asset();
			currentAsset.setIdentifier(rs.getInt("ID"));
			currentAsset.setAssetName(rs.getString("Name"));
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(rs.getDate("DateAcquired").getTime());
			currentAsset.setDateAcquired(cal);
			
			cal = Calendar.getInstance();
			cal.setTimeInMillis(rs.getDate("RetentionPeriod").getTime());
			currentAsset.setRetentionPeriod(cal);
			
			Reference type = new Reference();
			type.setIdentifier(rs.getInt("ID_Type"));
			
			Reference classification = new Reference();
			classification.setIdentifier(rs.getInt("ID_Classification"));
			
			Reference maintenanceSchedule = new Reference();
			maintenanceSchedule.setIdentifier(rs.getInt("ID_MaintenanceSchedule"));
			
			PreparedStatement ps2 = conn.prepareStatement("SELECT * from `reference`" +
					"WHERE `ID` = ?");
			ps2.setInt(1, type.getIdentifier());
			ResultSet rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				type.setValue(rs2.getString("Value"));
				type.setHidden(rs2.getBoolean("Hidden"));
			}
			currentAsset.setType(type);
			
			ps2.setInt(1, classification.getIdentifier());
			rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				classification.setValue(rs2.getString("Value"));
				classification.setHidden(rs2.getBoolean("Hidden"));
			}
			currentAsset.setClassification(classification);
			
			ps2.setInt(1, maintenanceSchedule.getIdentifier());
			rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				maintenanceSchedule.setValue(rs2.getString("Value"));
				maintenanceSchedule.setHidden(rs2.getBoolean("Hidden"));
			}
			rs2.close();
			ps2.close();
			currentAsset.setMaintenanceSchedule(maintenanceSchedule);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `person`" +
					"WHERE `ID` IN (SELECT `ID_Person` FROM `owner` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, currentAsset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> owners = new ArrayList<String>();
			while (rs2.next()) {
				owners.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			currentAsset.setOwner(owners);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `person`" +
					"WHERE `ID` IN (SELECT `ID_Person` FROM `custodian` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, currentAsset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> custodians = new ArrayList<String>();
			while (rs2.next()) {
				custodians.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			currentAsset.setCustodian(custodians);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `storage_location`" +
					"WHERE `ID` IN (SELECT `ID_StorageLocation` FROM `asset_location` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, currentAsset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> storageLocations = new ArrayList<String>();
			while (rs2.next()) {
				storageLocations.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			currentAsset.setStorageLocation(storageLocations);
			
			// get Values
			ps2 = conn.prepareStatement("SELECT * FROM `reference`, `asset_value`" +
					" WHERE (`asset_value`.`ID_ValueType` = `reference`.`ID` OR" +
					" `asset_value`.`ID_ValueLevel` = `reference`.`ID`) AND `asset_value`.`ID_Asset` = ?");
			ps2.setInt(1, currentAsset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			rs2.next();
			
			ArrayList<Value> values = new ArrayList<Value>();
			Reference valueType = null;
			Reference valueLevel = null;
			
			while (!rs2.isAfterLast()) {
				if (valueType == null || valueLevel == null) {
					valueType = new Reference();
					valueType.setIdentifier(rs2.getInt("ID_ValueType"));
					valueLevel = new Reference();
					valueLevel.setIdentifier(rs2.getInt("ID_ValueLevel"));
				}
				
				if (valueType.getIdentifier() == rs2.getInt("ID")) {
					valueType.setValue(rs2.getString("Value"));
					valueType.setHidden(rs2.getBoolean("Hidden"));
				}
				
				if (valueLevel.getIdentifier() == rs2.getInt("ID")) {
					valueLevel.setValue(rs2.getString("Value"));
					valueLevel.setHidden(rs2.getBoolean("Hidden"));
				}
				
				if (valueType.getValue() != null && valueLevel.getValue() != null) {
					Value value = new Value();
					value.setType(valueType);
					value.setLevel(valueLevel);
					values.add(value);
					
					valueType = null;
					valueLevel = null;
				}
				rs2.next();
			}
			currentAsset.setValue(values);
			assetList.add(currentAsset);
		}
		
		conn.close();
		return assetList;
	}
	//added
	
	public ArrayList<Asset> getfilteredAssets(String from, String to, String datetype) throws SQLException {
		ArrayList<Asset> assetList = null;

		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM `asset` " +
				"WHERE "+datetype+" >= '"+from+"' and "+datetype+" <= '"+to+"'; ");
		
		ResultSet rs = ps.executeQuery();
		
		assetList = new ArrayList<Asset>();
	
		while (rs.next()) {
			Asset currentAsset = new Asset();
			currentAsset.setIdentifier(rs.getInt("ID"));
			currentAsset.setAssetName(rs.getString("Name"));
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(rs.getDate("DateAcquired").getTime());
			currentAsset.setDateAcquired(cal);
			
			cal = Calendar.getInstance();
			cal.setTimeInMillis(rs.getDate("RetentionPeriod").getTime());
			currentAsset.setRetentionPeriod(cal);
			
			Reference type = new Reference();
			type.setIdentifier(rs.getInt("ID_Type"));
			
			Reference classification = new Reference();
			classification.setIdentifier(rs.getInt("ID_Classification"));
			
			Reference maintenanceSchedule = new Reference();
			maintenanceSchedule.setIdentifier(rs.getInt("ID_MaintenanceSchedule"));
			
			PreparedStatement ps2 = conn.prepareStatement("SELECT * from `reference`" +
					"WHERE `ID` = ?");
			ps2.setInt(1, type.getIdentifier());
			ResultSet rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				type.setValue(rs2.getString("Value"));
				type.setHidden(rs2.getBoolean("Hidden"));
			}
			currentAsset.setType(type);
			
			ps2.setInt(1, classification.getIdentifier());
			rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				classification.setValue(rs2.getString("Value"));
				classification.setHidden(rs2.getBoolean("Hidden"));
			}
			currentAsset.setClassification(classification);
			
			ps2.setInt(1, maintenanceSchedule.getIdentifier());
			rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				maintenanceSchedule.setValue(rs2.getString("Value"));
				maintenanceSchedule.setHidden(rs2.getBoolean("Hidden"));
			}
			rs2.close();
			ps2.close();
			currentAsset.setMaintenanceSchedule(maintenanceSchedule);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `person`" +
					"WHERE `ID` IN (SELECT `ID_Person` FROM `owner` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, currentAsset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> owners = new ArrayList<String>();
			while (rs2.next()) {
				owners.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			currentAsset.setOwner(owners);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `person`" +
					"WHERE `ID` IN (SELECT `ID_Person` FROM `custodian` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, currentAsset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> custodians = new ArrayList<String>();
			while (rs2.next()) {
				custodians.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			currentAsset.setCustodian(custodians);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `storage_location`" +
					"WHERE `ID` IN (SELECT `ID_StorageLocation` FROM `asset_location` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, currentAsset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> storageLocations = new ArrayList<String>();
			while (rs2.next()) {
				storageLocations.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			currentAsset.setStorageLocation(storageLocations);
			
			// get Values
			ps2 = conn.prepareStatement("SELECT * FROM `reference`, `asset_value`" +
					" WHERE (`asset_value`.`ID_ValueType` = `reference`.`ID` OR" +
					" `asset_value`.`ID_ValueLevel` = `reference`.`ID`) AND `asset_value`.`ID_Asset` = ?");
			ps2.setInt(1, currentAsset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			rs2.next();
			
			ArrayList<Value> values = new ArrayList<Value>();
			Reference valueType = null;
			Reference valueLevel = null;
			
			while (!rs2.isAfterLast()) {
				if (valueType == null || valueLevel == null) {
					valueType = new Reference();
					valueType.setIdentifier(rs2.getInt("ID_ValueType"));
					valueLevel = new Reference();
					valueLevel.setIdentifier(rs2.getInt("ID_ValueLevel"));
				}
				
				if (valueType.getIdentifier() == rs2.getInt("ID")) {
					valueType.setValue(rs2.getString("Value"));
					valueType.setHidden(rs2.getBoolean("Hidden"));
				}
				
				if (valueLevel.getIdentifier() == rs2.getInt("ID")) {
					valueLevel.setValue(rs2.getString("Value"));
					valueLevel.setHidden(rs2.getBoolean("Hidden"));
				}
				
				if (valueType.getValue() != null && valueLevel.getValue() != null) {
					Value value = new Value();
					value.setType(valueType);
					value.setLevel(valueLevel);
					values.add(value);
					
					valueType = null;
					valueLevel = null;
				}
				rs2.next();
			}
			currentAsset.setValue(values);
			assetList.add(currentAsset);
		}
		
		conn.close();
		return assetList;
	}

	
	public Asset getAssetByID(int id) throws SQLException {
		Asset asset = null;
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM `asset` WHERE `ID` = ?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			asset = new Asset();
			asset.setIdentifier(id);
			asset.setAssetName(rs.getString("Name"));
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(rs.getDate("DateAcquired").getTime());
			asset.setDateAcquired(cal);
			
			cal = Calendar.getInstance();
			cal.setTimeInMillis(rs.getDate("RetentionPeriod").getTime());
			asset.setRetentionPeriod(cal);
			
			Reference type = new Reference();
			type.setIdentifier(rs.getInt("ID_Type"));
			
			Reference classification = new Reference();
			classification.setIdentifier(rs.getInt("ID_Classification"));
			
			Reference maintenanceSchedule = new Reference();
			maintenanceSchedule.setIdentifier(rs.getInt("ID_MaintenanceSchedule"));
			
			PreparedStatement ps2 = conn.prepareStatement("SELECT * from `reference`" +
					"WHERE `ID` = ?");
			ps2.setInt(1, type.getIdentifier());
			ResultSet rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				type.setValue(rs2.getString("Value"));
				type.setHidden(rs2.getBoolean("Hidden"));
			}
			asset.setType(type);
			
			ps2.setInt(1, classification.getIdentifier());
			rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				classification.setValue(rs2.getString("Value"));
				classification.setHidden(rs2.getBoolean("Hidden"));
			}
			asset.setClassification(classification);
			
			ps2.setInt(1, maintenanceSchedule.getIdentifier());
			rs2 = ps2.executeQuery();
			
			if (rs2.next()) {
				maintenanceSchedule.setValue(rs2.getString("Value"));
				maintenanceSchedule.setHidden(rs2.getBoolean("Hidden"));
			}
			rs2.close();
			ps2.close();
			asset.setMaintenanceSchedule(maintenanceSchedule);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `person`" +
					"WHERE `ID` IN (SELECT `ID_Person` FROM `owner` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, asset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> owners = new ArrayList<String>();
			while (rs2.next()) {
				owners.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			asset.setOwner(owners);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `person`" +
					"WHERE `ID` IN (SELECT `ID_Person` FROM `custodian` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, asset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> custodians = new ArrayList<String>();
			while (rs2.next()) {
				custodians.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			asset.setCustodian(custodians);
			
			ps2 = conn.prepareStatement("SELECT `Value` from `storage_location`" +
					"WHERE `ID` IN (SELECT `ID_StorageLocation` FROM `asset_location` WHERE `ID_Asset` = ? ORDER BY `ID`)");
			ps2.setInt(1, asset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			ArrayList<String> storageLocations = new ArrayList<String>();
			while (rs2.next()) {
				storageLocations.add(rs2.getString("Value"));
			}
			rs2.close();
			ps2.close();
			asset.setStorageLocation(storageLocations);
			
			// get Values
			ps2 = conn.prepareStatement("SELECT * FROM `reference`, `asset_value`" +
					" WHERE (`asset_value`.`ID_ValueType` = `reference`.`ID` OR" +
					" `asset_value`.`ID_ValueLevel` = `reference`.`ID`) AND `asset_value`.`ID_Asset` = ?");
			ps2.setInt(1, asset.getIdentifier());
			rs2 = ps2.executeQuery();
			
			rs2.next();
			
			ArrayList<Value> values = new ArrayList<Value>();
			Reference valueType = null;
			Reference valueLevel = null;
			
			while (!rs2.isAfterLast()) {
				if (valueType == null || valueLevel == null) {
					valueType = new Reference();
					valueType.setIdentifier(rs2.getInt("ID_ValueType"));
					valueLevel = new Reference();
					valueLevel.setIdentifier(rs2.getInt("ID_ValueLevel"));
				}
				
				if (valueType.getIdentifier() == rs2.getInt("ID")) {
					valueType.setValue(rs2.getString("Value"));
					valueType.setHidden(rs2.getBoolean("Hidden"));
				}
				
				if (valueLevel.getIdentifier() == rs2.getInt("ID")) {
					valueLevel.setValue(rs2.getString("Value"));
					valueLevel.setHidden(rs2.getBoolean("Hidden"));
				}
				
				if (valueType.getValue() != null && valueLevel.getValue() != null) {
					Value value = new Value();
					value.setType(valueType);
					value.setLevel(valueLevel);
					values.add(value);
					
					valueType = null;
					valueLevel = null;
				}
				rs2.next();
			}
			asset.setValue(values);
		}
		
		conn.close();
		return asset;
	}
	
	public void updateAsset(Asset oldAsset, Asset newAsset) throws Exception {
		
		ArrayList<String> affectedFields = new ArrayList<String>();
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		try {
			conn.setAutoCommit(false);
			
			// asset table
			if (!oldAsset.getAssetName().equals(newAsset.getAssetName())) {
				affectedFields.add("Name");
			}
			if (!oldAsset.getDateAcquired().equals(newAsset.getDateAcquired())) {
				affectedFields.add("Date Acquired");
			}
			if (!oldAsset.getRetentionPeriod().equals(newAsset.getRetentionPeriod())) {
				affectedFields.add("Retention Period");
			}
			if (!oldAsset.getType().getValue().equals(newAsset.getType().getValue())) {
				affectedFields.add("Type");
			}
			if (!oldAsset.getClassification().getValue().equals(newAsset.getClassification().getValue())) {
				affectedFields.add("Classification");
			}
			if (!oldAsset.getMaintenanceSchedule().getValue().equals(newAsset.getMaintenanceSchedule().getValue())) {
				affectedFields.add("Maintenance Schedule");
			}
			
			if (affectedFields.size() > 0) {
				PreparedStatement ps = conn.prepareStatement("UPDATE `asset` SET `Name` = ?, `DateAcquired` = ?," +
						" `RetentionPeriod` = ?, `ID_Type` = ?, `ID_Classification` = ?, `ID_MaintenanceSchedule` = ?" +
						" WHERE `ID` = ?");
				ps.setString(1, newAsset.getAssetName());
				ps.setDate(2, new Date(newAsset.getDateAcquired().getTimeInMillis()));
				ps.setDate(3, new Date(newAsset.getRetentionPeriod().getTimeInMillis()));
				ps.setInt(4, newAsset.getType().getIdentifier());
				ps.setInt(5, newAsset.getClassification().getIdentifier());
				ps.setInt(6, newAsset.getMaintenanceSchedule().getIdentifier());
				ps.setInt(7, newAsset.getIdentifier());
				ps.executeUpdate();
			}
			
			// owner table
			int oldSize = oldAsset.getOwner().size();
			int newSize = newAsset.getOwner().size();
			
			if (oldSize < newSize && !oldAsset.getOwner().get(oldSize - 1).equals(newAsset.getOwner().get(newSize - 1))) {
				affectedFields.add("Owner");
				
				String referenceValue = newAsset.getOwner().get(newSize - 1);
				int owner = getReferenceID(conn, "person", referenceValue);
				
				AssetRelationsDatabase relationsDB = new AssetRelationsDatabase();
				relationsDB.setConnection(conn);
				relationsDB.setTable("owner");
				relationsDB.setAssetID(newAsset.getIdentifier());
				relationsDB.setReferenceID(owner);
				relationsDB.insert();
			}
			
			// custodian table
			oldSize = oldAsset.getCustodian().size();
			newSize = newAsset.getCustodian().size();
			if (oldSize < newSize && !oldAsset.getCustodian().get(oldSize - 1).equals(newAsset.getCustodian().get(newSize - 1))) {
				affectedFields.add("Custodian");
				
				String referenceValue = newAsset.getCustodian().get(newSize - 1);
				int custodian = getReferenceID(conn, "person", referenceValue);
				
				AssetRelationsDatabase relationsDB = new AssetRelationsDatabase();
				relationsDB.setConnection(conn);
				relationsDB.setTable("custodian");
				relationsDB.setAssetID(newAsset.getIdentifier());
				relationsDB.setReferenceID(custodian);
				relationsDB.insert();
			}
			
			// storage table
			oldSize = oldAsset.getStorageLocation().size();
			newSize = newAsset.getStorageLocation().size();
			if (oldSize < newSize &&
				!oldAsset.getStorageLocation().get(oldSize - 1).equals(newAsset.getStorageLocation().get(newSize - 1))) {
				affectedFields.add("Storage Location");
				
				String referenceValue = newAsset.getStorageLocation().get(newSize - 1);
				int storageLocation = getReferenceID(conn, "storge_location", referenceValue);
				
				AssetRelationsDatabase relationsDB = new AssetRelationsDatabase();
				relationsDB.setConnection(conn);
				relationsDB.setTable("asset_location");
				relationsDB.setAssetID(newAsset.getIdentifier());
				relationsDB.setReferenceID(storageLocation);
				relationsDB.insert();
			}
			
			// value table
			for (Value oldValue : oldAsset.getValue()) {
				for (Value newValue : newAsset.getValue()) {
					if (oldValue.getType().getIdentifier() == newValue.getType().getIdentifier()) {
						if (oldValue.getLevel().getIdentifier() != newValue.getLevel().getIdentifier()) {
							affectedFields.add(newValue.getType().getValue());
							
							PreparedStatement ps = conn.prepareStatement("UPDATE `asset_value` SET `ID_ValueLevel` = ?" +
									" WHERE `ID_Asset` = ? AND `ID_ValueType` = ?");
							ps.setInt(1, newValue.getLevel().getIdentifier());
							ps.setInt(2, newAsset.getIdentifier());
							ps.setInt(3, newValue.getType().getIdentifier());
							ps.executeUpdate();
						}
					}
				}
			}
			
			AssetLogEntry entry = new AssetLogEntry();
			entry.setTimestamp(Calendar.getInstance());
			if (affectedFields.size() > 0) {
				entry.setAction("updated asset");
			}
			else {
				throw new Exception("Nothing updated.");
			}
			entry.setAssetID(newAsset.getIdentifier());
			entry.setAffectedFields(affectedFields);
			database = new AssetDatabase();
			database.setEntry(entry);
			this.logAction(conn);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private void insertReference(String table, String value) throws Exception {
		
		if (value == null || value.isEmpty()) {
			throw new Exception("Invalid input.");
		}
		
		Reference reference = new Reference();
		reference.setValue(value);
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		try {
			conn.setAutoCommit(false);
			
			RewriteableReferenceDatabase db = new RewriteableReferenceDatabase();
			db.setConnection(conn);
			db.setTable(table);
			db.setReference(reference);
			reference.setIdentifier(db.insert());
			
			database = db;
			
			ReferenceLogEntry entry = new ReferenceLogEntry();
			entry.setTimestamp(Calendar.getInstance());
			entry.setAction("inserted reference");
			entry.setTable(table.replace('_', ' '));
			entry.setReference(reference);
			
			database.setEntry(entry);
			this.logAction(conn);
		} catch (SQLException ex) {
			throw ex;
		}
	}
	
	private ArrayList<Reference> selectReference(String table) {
		ArrayList<Reference> result = null;
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		
		try {
			ArrayList<Object> subresult = null;
			
			RewriteableReferenceDatabase db = new RewriteableReferenceDatabase();
			db.setConnection(conn);
			db.setTable(table);
			subresult = db.select();
			
			result = new ArrayList<Reference>();
			for (Object item : subresult) {
				result.add((Reference)item);
			}
		} catch (SQLException ex) {
			Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}
	
	private int getReferenceID(Connection conn, String table, String value) throws Exception {
		int resultingID = 0;
		
		ReferenceDatabase db = new ReferenceDatabase();
		db.setConnection(conn);
		db.setTable(table);
		db.setValue(value);
		
		try {
			resultingID = db.insert();
			if (resultingID <= 0) {
				ArrayList<Object> result = db.select();
				resultingID = (Integer)result.get(0);
			}
		}
		catch (SQLException ex) {
			throw ex;
		}
		
		return resultingID;
	}
	
	private void updateReference(String table, Reference oldReference, Reference newReference) throws Exception {
		
		if (oldReference == null || newReference == null) {
			throw new Exception("Invalid input.");
		}
		
		if (oldReference.getValue() == null || newReference.getValue() == null) {
			throw new Exception("Invalid input.");
		}
		
		if (oldReference.getValue().isEmpty() || newReference.getValue().isEmpty()) {
			throw new Exception("Invalid input.");
		}
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		Connection conn = connectionFactory.getConnection();
		try {
			conn.setAutoCommit(false);
			
			RewriteableReferenceDatabase db = new RewriteableReferenceDatabase();
			db.setConnection(conn);
			db.setReference(newReference);
			db.update();
			
			database = db;
			
			ReferenceLogEntry entry = new ReferenceLogEntry();
			entry.setTimestamp(Calendar.getInstance());
			if (!oldReference.getValue().equals(newReference.getValue())) {
				entry.setAction("updated reference");
			}
			if (oldReference.isHidden() != newReference.isHidden()) {
				entry.setAction("deleted reference");
			}
			entry.setTable(table.replace('_', ' '));
			entry.setReference(newReference);
			
			database.setEntry(entry);
			this.logAction(conn);
		} catch (SQLException ex) {
			throw ex;
		}
	}
	
	private void logAction(Connection conn) throws SQLException {
		int logID = 0;
		
		try {
			LogEntry entry = database.getEntry();
			
			HistoryDatabase historyDatabase = new HistoryDatabase();
			historyDatabase.setEntry(entry);
			historyDatabase.setConnection(conn);
			logID = historyDatabase.insert();
			
			if (entry.getClass() == AssetLogEntry.class) {
				AssetHistoryDatabase assetHistoryDatabase = new AssetHistoryDatabase();
				assetHistoryDatabase.setLogID(logID);
				assetHistoryDatabase.setEntry((AssetLogEntry)entry);
				assetHistoryDatabase.setConnection(conn);
				assetHistoryDatabase.insert();
			}
			else {
				ReferenceHistoryDatabase referenceHistoryDatabase = new ReferenceHistoryDatabase();
				referenceHistoryDatabase.setLogID(logID);
				referenceHistoryDatabase.setEntry((ReferenceLogEntry)entry);
				referenceHistoryDatabase.setConnection(conn);
				referenceHistoryDatabase.insert();
			}
			conn.commit();
		}
		catch (SQLException ex) {
			throw ex;
		}
		database = null;
	}
	
}
