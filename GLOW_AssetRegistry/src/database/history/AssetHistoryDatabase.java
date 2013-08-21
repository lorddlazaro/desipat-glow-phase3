package database.history;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import models.AssetLogEntry;
import database.abstracts.RecordableDatabase;
import database.builders.CustomStatementDirector;
import database.builders.history.AssetHistoryInsertStatementBuilder;
import database.builders.history.AssetHistoryQueryStatementBuilder;

public class AssetHistoryDatabase extends RecordableDatabase {
	
	private int logID;
	private AssetLogEntry entry;
	
	public AssetHistoryDatabase() {
		super();
		logID = 0;
		entry = null;
	}
	
	@Override
	public ArrayList<Object> select() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		AssetHistoryQueryStatementBuilder builder = new AssetHistoryQueryStatementBuilder();
		builder.setConnection(conn);
		if (entry != null && entry.getAssetID() > 0) {
			builder.setAssetID(entry.getAssetID());
		}
		director.setBuilder(builder);
		director.constructCustomStatement();
		
		ArrayList<Object> entries = new ArrayList<Object>();
		
		PreparedStatement ps = director.getCustomStatement().prepareGivenStatement();
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			
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
				
				entries.add(currentEntry);
			}
		}
		
		rs.close();
		ps.close();
		
		return entries;
	}
	
	@Override
	public int insert() throws SQLException {
		Calendar cal = Calendar.getInstance();
		entry.setTimestamp(cal);
		
		CustomStatementDirector director = new CustomStatementDirector();
		AssetHistoryInsertStatementBuilder builder = new AssetHistoryInsertStatementBuilder();
		if (logID > 0) {
			builder.setLogID(logID);
		}
		if (entry != null) {
			builder.setEntry(entry);
		}
		builder.setConnection(conn);
		director.setBuilder(builder);
		
		int resultingID = 0;
		
		director.constructCustomStatement();
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
	
	public void setEntry(AssetLogEntry entry) {
		this.entry = entry;
	}
	
	public void setLogID(int logID) {
		this.logID = logID;
	}
	
}
