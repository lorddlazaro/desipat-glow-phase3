// Author ( Sharmaine Lim )
// 
// Tables: person & storage_location

package database.referencetables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.abstracts.RecordableDatabase;
import database.builders.CustomStatementDirector;
import database.builders.references.ReferenceInsertStatementBuilder;
import database.builders.references.ReferenceQueryStatementBuilder;

public class ReferenceDatabase extends RecordableDatabase {
	
	private String table;
	private String value;
	
	public ReferenceDatabase() {
		super();
		table = null;
		value = null;
	}
	
	@Override
	public ArrayList<Object> select() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		ReferenceQueryStatementBuilder builder = new ReferenceQueryStatementBuilder();
		builder.setConnection(conn);
		builder.setTable(table);
		builder.setValue(value);
		director.setBuilder(builder);
		director.constructCustomStatement();
		
		ArrayList<Object> peopleID = new ArrayList<Object>();
		
		PreparedStatement ps = director.getCustomStatement().prepareGivenStatement();
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			peopleID.add(rs.getInt("ID"));
		}
		
		rs.close();
		ps.close();
		
		return peopleID;
	}
	
	@Override
	public int insert() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		ReferenceInsertStatementBuilder builder = new ReferenceInsertStatementBuilder();
		builder.setTable(table);
		builder.setValue(value);
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
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
