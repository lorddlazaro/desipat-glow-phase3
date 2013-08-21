// Original Author (Danny Cheng)

package database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcreteConnectionFactory extends ConnectionFactory {
	
	@Override
	public Connection getConnection() {
		try {
			Class.forName(getDriver());
			
			Connection connection = DriverManager.getConnection(getUrl(), getUsername(), getPassword());
			
			return connection;
		}
		catch (SQLException ex) {
			Logger.getLogger(ConcreteConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
		catch (ClassNotFoundException ex) {
			Logger.getLogger(ConcreteConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
}
