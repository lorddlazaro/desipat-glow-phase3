// Author ( Sharmaine Lim )

package models;

import java.util.ArrayList;
import java.util.Calendar;

public class Asset {

	private int identifier;
	private String assetName;
	private ArrayList<String> owner; 
	private ArrayList<String> custodian;
	private Reference type;
	private Reference classification;
	private Reference maintenanceSchedule;
	private Calendar dateAcquired;
	private Calendar retentionPeriod;
	private ArrayList<Value> values;
	private ArrayList<String> storageLocation;
	
	// Sample: dateAcquired.set(year, month, date);
	
	public Asset() {
		identifier = 0;
		assetName = null;
		owner = null;
		custodian = null;
		type = null;
		classification = null;
		maintenanceSchedule = null;
		dateAcquired = null;
		retentionPeriod = null;
		values = null;
		storageLocation = null;
	}
	
	public int getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	public String getAssetName() {
		return assetName;
	}
	
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	public ArrayList<String> getOwner() {
		return owner;
	}
	
	public void setOwner(ArrayList<String> owner) {
		this.owner = owner;
	}
	
	public ArrayList<String> getCustodian() {
		return custodian;
	}
	
	public void setCustodian(ArrayList<String> custodian) {
		this.custodian = custodian;
	}
	
	public Reference getType() {
		return type;
	}
	
	public void setType(Reference type) {
		this.type = type;
	}
	
	public Calendar getDateAcquired() {
		return dateAcquired;
	}
	
	public void setDateAcquired(Calendar dateAcquired) {
		this.dateAcquired = dateAcquired;
	}
	
	public Calendar getRetentionPeriod() {
		return retentionPeriod;
	}
	
	public void setRetentionPeriod(Calendar retentionPeriod) {
		this.retentionPeriod = retentionPeriod;
	}
	
	public Reference getMaintenanceSchedule() {
		return maintenanceSchedule;
	}
	
	public void setMaintenanceSchedule(Reference maintenanceSchedule) {
		this.maintenanceSchedule = maintenanceSchedule;
	}
	
	public ArrayList<Value> getValue() {
		return values;
	}
	
	public void setValue(ArrayList<Value> values) {
		this.values = values;
	}
	
	public Reference getClassification() {
		return classification;
	}
	
	public void setClassification(Reference classification) {
		this.classification = classification;
	}
	
	public ArrayList<String> getStorageLocation() {
		return storageLocation;
	}
	
	public void setStorageLocation(ArrayList<String> storageLocation) {
		this.storageLocation = storageLocation;
	}
	
}
