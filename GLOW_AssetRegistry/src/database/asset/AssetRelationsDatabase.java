package database.asset;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.abstracts.RecordableDatabase;
import database.builders.CustomStatementDirector;
import database.builders.references.AssetRelationInsertStatementBuilder;

public class AssetRelationsDatabase extends RecordableDatabase {
	
	private String table;
	private int assetID;
	private int referenceID;
	
	public AssetRelationsDatabase() {
		super();
		table = null;
		assetID = 0;
		referenceID = 0;
	}
	
	@Override
	public ArrayList<Object> select() throws SQLException {
		return null;
	}
	
	@Override
	public int insert() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		AssetRelationInsertStatementBuilder builder = new AssetRelationInsertStatementBuilder();
		builder.setTable(table);
		builder.setAssetID(assetID);
		builder.setReferenceID(referenceID);
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
	
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	
	public void setReferenceID(int referenceID) {
		this.referenceID = referenceID;
	}
	
}
