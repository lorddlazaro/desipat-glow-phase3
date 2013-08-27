// Author ( Sharmaine Lim )

package database.asset;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.abstracts.RewriteableDatabase;
import database.builders.CustomStatementDirector;
import database.builders.QueryDirector;
import database.builders.values.ValueInsertStatementBuilder;

// TODO Currently lacks: update asset_value table

public abstract class ValueDatabase extends RewriteableDatabase {
	
	private int assetID;
	private int valueTypeID;
	private int valueLevelID;
	
	public ValueDatabase() {
		super();
		assetID = 0;
		valueTypeID = 0;
		valueLevelID = 0;
	}
	
	@Override
	public int insert() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		ValueInsertStatementBuilder builder = new ValueInsertStatementBuilder();
		builder.setAssetID(assetID);
		builder.setValueTypeID(valueTypeID);
		builder.setValueLevelID(valueLevelID);
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
	
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	
	public void setValueTypeID(int valueTypeID) {
		this.valueTypeID = valueTypeID;
	}
	
	public void setValueLevelID(int valueLevelID) {
		this.valueLevelID = valueLevelID;
	}
	
}
