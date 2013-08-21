// Author ( Sharmaine Lim )

package database.builders;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class CustomStatementBuilder {
	
	protected CustomStatement customStatement;
	private Connection conn;
	
	public CustomStatementBuilder() {
		customStatement = null;
	}
	
	public abstract void setStatement();
	public abstract void populateValues();
	
	public void createNewCustomStatement() throws SQLException {
		customStatement = new CustomStatement();
		customStatement.setConnection(conn);
	}
	
	public CustomStatement getCustomStatement() {
		return customStatement;
	}
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
}
