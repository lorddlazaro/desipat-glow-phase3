// Author ( Sharmaine Lim )

package models;

import java.util.Calendar;

public abstract class LogEntry {
	
	private Calendar timestamp;
	private String action;
	
	public LogEntry() {
		timestamp = null;
		action = null;
	}
	
	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public Calendar getTimestamp() {
		return timestamp;
	}
	
	public String getAction() {
		return action;
	}
	
}
