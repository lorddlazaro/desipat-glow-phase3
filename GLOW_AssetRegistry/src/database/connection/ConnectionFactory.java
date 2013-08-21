// Original Author (Danny Cheng)

package database.connection;

import java.sql.Connection;
import java.util.ResourceBundle;

public abstract class ConnectionFactory {
	private static String driver = "";
	private static String url = "";
	private static String username = "";
	private static String password = "";
	
	public abstract Connection getConnection();
	
	public static ConnectionFactory getInstance(){
		ResourceBundle resourceBundle = ResourceBundle.getBundle("resources/database");
		driver = resourceBundle.getString("driver");
		url = resourceBundle.getString("url");
		username = resourceBundle.getString("username");
		password = resourceBundle.getString("password");
		return new ConcreteConnectionFactory();
	} 
	
	public String getDriver() {
		return driver;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
}
