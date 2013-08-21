package models;

import java.util.ArrayList;

public class AssetLogEntry extends LogEntry {
	
	private int assetID;
	private ArrayList<String> affectedFields;
	
	public AssetLogEntry() {
		super();
		assetID = 0;
		affectedFields = null;
	}
	
	public void setAssetID(int assetID) {
		this.assetID = assetID;
	}
	
	public void setAffectedFields(ArrayList<String> affectedFields) {
		this.affectedFields = affectedFields;
	}
	
	public int getAssetID() {
		return assetID;
	}
	
	public ArrayList<String> getAffectedFields() {
		return affectedFields;
	}
	
}
