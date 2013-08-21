// Author ( Sharmaine Lim )

package database.history;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import models.AssetLogEntry;
import models.LogEntry;
import models.Reference;
import models.ReferenceLogEntry;
import database.abstracts.RecordableDatabase;
import database.builders.CustomStatementDirector;
import database.builders.history.HistoryInsertStatementBuilder;
import database.builders.history.HistoryQueryStatementBuilder;

// TODO Huhuhu, ayaw niya ipagsama yung log, log_asset and log_reference ng maayos

public class HistoryDatabase extends RecordableDatabase {
	
	private LogEntry entry;
	private Calendar dateFrom;
	private Calendar dateTo;
	
	public HistoryDatabase() {
		entry = null;
		dateFrom = null;
		dateTo = null;
	}
	
	public ArrayList<Object> select() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		HistoryQueryStatementBuilder builder = new HistoryQueryStatementBuilder();
		builder.setConnection(conn);
		if (dateFrom != null) {
			builder.setDateFrom(dateFrom);
		}
		if (dateTo != null) {
			builder.setDateTo(dateTo);
		}
		director.setBuilder(builder);
		director.constructCustomStatement();
		
		ArrayList<Object> entries = new ArrayList<Object>();
		
		PreparedStatement ps = director.getCustomStatement().prepareGivenStatement();
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		
		while (!rs.isAfterLast()) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(rs.getTimestamp("Timestamp").getTime());
			
			String action = rs.getString("Action");
			
			LogEntry currentEntry = null;
			
			rs.getString("Field_Reference");
			
			if (rs.wasNull()) {
				int currentAssetID = 0;
				currentAssetID = rs.getInt("ID_Asset");
				
				currentEntry = new AssetLogEntry();
				((AssetLogEntry)currentEntry).setAssetID(rs.getInt("ID_Asset"));
				
				ArrayList<String> affectedFields = new ArrayList<String>();
				if (currentAssetID == rs.getInt("ID_Asset")) {
					affectedFields.add(rs.getString("AffectedField"));
					rs.next();
				}
				
				((AssetLogEntry)currentEntry).setAffectedFields(affectedFields);
			}
			else {
				Reference reference = new Reference();
				reference.setIdentifier(rs.getInt("ID_Reference"));
				reference.setValue(rs.getString("Value"));
				reference.setHidden(rs.getBoolean("Hidden"));
				
				currentEntry = new ReferenceLogEntry();
				((ReferenceLogEntry)currentEntry).setTable(rs.getString("Field_Reference"));
				((ReferenceLogEntry)currentEntry).setReference(reference);
				rs.next();
			}
			
			currentEntry.setTimestamp(cal);
			currentEntry.setAction(action);
			
			entries.add(currentEntry);
		}
		
		rs.close();
		ps.close();
		
		return entries;
	}
	
	public int insert() throws SQLException {
		Calendar cal = Calendar.getInstance();
		entry.setTimestamp(cal);
		
		CustomStatementDirector director = new CustomStatementDirector();
		HistoryInsertStatementBuilder builder = new HistoryInsertStatementBuilder();
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
		ps.close();
		
		return resultingID;
	}
	
	public void setEntry(LogEntry entry) {
		this.entry = entry;
	}
	
	public void setDateFrom(Calendar dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	public void setDateTo(Calendar dateTo) {
		this.dateTo = dateTo;
	}
	
}
