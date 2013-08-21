// Author ( Sharmaine Lim )

package database.builders;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomStatementDirector {
	
	private CustomStatementBuilder builder;
	
	public CustomStatementDirector() {
		builder = null;
	}
	
	public void constructCustomStatement() {
		try {
			builder.createNewCustomStatement();
		} catch (SQLException ex) {
			Logger.getLogger(CustomStatementDirector.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		builder.setStatement();
		builder.populateValues();
	}
	
	public CustomStatement getCustomStatement() {
		return builder.getCustomStatement();
	}
	
	public void setBuilder(CustomStatementBuilder builder) {
		this.builder = builder;
	}
	
}
