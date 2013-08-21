// Author ( Sharmaine Lim )

package database.abstracts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class RecordableDatabase {
	
	protected Connection conn;
	
	public RecordableDatabase() {}
	
	public abstract ArrayList<Object> select() throws SQLException;
	public abstract int insert() throws SQLException;
	
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
}
