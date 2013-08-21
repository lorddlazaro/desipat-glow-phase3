// Author ( Sharmaine Lim )

package database.builders;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomStatement {
	
	private Connection conn;
	private PreparedStatement ps;
	private String statement;
	private ArrayList<ColumnValue> values;
	
	public CustomStatement() {
		statement = null;
		values = new ArrayList<ColumnValue>();
		ps = null;
	}
	
	public PreparedStatement prepareGivenStatement() throws SQLException {
		ps = conn.prepareStatement(statement, PreparedStatement.RETURN_GENERATED_KEYS);
		
		for (int i = 0; i < values.size(); i++) {
			this.setObject(i + 1, values.get(i).getValue());
		}
		
		return ps;
	}
	
	public void setConnection(Connection conn) throws SQLException {
		this.conn = conn;
	}
	
	public void setStatement(String statement) {
		if (statement != null) {
			this.statement = statement;
		}
	}
	
	public void addValue(String column, Object value) {
		ColumnValue columnValue = new ColumnValue();
		columnValue.setColumn(column);
		columnValue.setValue(value);
		values.add(columnValue);
	}
	
	private void setObject(int no, Object object) throws SQLException {
		if (object.getClass() == String.class) {
			ps.setString(no, (String)object);
		}
		else if (object.getClass() == Integer.class) {
			String i = Integer.toString((Integer)object);
			ps.setInt(no, Integer.parseInt(i));
		}
		else if (object.getClass() == Timestamp.class) {
			ps.setTimestamp(no, (Timestamp)object);
		}
		else if (object.getClass() == Date.class) {
			ps.setDate(no, (Date)object);
		}
		else if (object.getClass() == Boolean.class) {
			ps.setBoolean(no, (Boolean)object);
		}
	}
	
}
