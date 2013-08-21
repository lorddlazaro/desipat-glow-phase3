package database.history;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import models.Reference;
import models.ReferenceLogEntry;
import database.abstracts.RecordableDatabase;
import database.builders.CustomStatementDirector;
import database.builders.history.ReferenceHistoryInsertStatementBuilder;
import database.builders.history.ReferenceHistoryQueryStatementBuilder;

public class ReferenceHistoryDatabase extends RecordableDatabase {
	
	private int logID;
	private ReferenceLogEntry entry;
	
	public ReferenceHistoryDatabase() {
		super();
		logID = 0;
		entry = null;
	}
	
	@Override
	public ArrayList<Object> select() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		ReferenceHistoryQueryStatementBuilder builder = new ReferenceHistoryQueryStatementBuilder();
		builder.setConnection(conn);
		director.setBuilder(builder);
		director.constructCustomStatement();
		
		ArrayList<Object> entries = new ArrayList<Object>();
		
		PreparedStatement ps = director.getCustomStatement().prepareGivenStatement();
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Reference reference = new Reference();
			reference.setIdentifier(rs.getInt("ID_Reference"));
			reference.setValue(rs.getString("Value"));
			reference.setHidden(rs.getBoolean("Hidden"));
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(rs.getTimestamp("Timestamp").getTime());
			
			ReferenceLogEntry currentEntry = null;
			
			currentEntry = new ReferenceLogEntry();
			currentEntry.setTimestamp(cal);
			currentEntry.setAction(rs.getString("Action"));
			currentEntry.setTable(rs.getString("Field_Reference"));
			currentEntry.setReference(reference);
			
			entries.add(currentEntry);
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
		ReferenceHistoryInsertStatementBuilder builder = new ReferenceHistoryInsertStatementBuilder();
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
	
	public void setEntry(ReferenceLogEntry entry) {
		this.entry = entry;
	}
	
	public void setLogID(int logID) {
		this.logID = logID;
	}
	
}
