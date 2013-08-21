package controllers;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import database.Database;

import models.Asset;

//extends AddAssetListener because there are only two things different between edit and add.
// 1. the SQL command   2. there should be an oldasset pre-loaded into the fields.

public class EditAssetListener extends AddAssetListener {

	Asset oldAsset;
	
	@Override
	protected ArrayList<String> setOwners(){
		ArrayList<String> arrListOwners = new ArrayList<String>();
		for(int i = 0;i<oldAsset.getOwner().size();i++)
			arrListOwners.add(oldAsset.getOwner().get(i));
		return arrListOwners;
	}
	
	@Override
	protected ArrayList<String> setCustodians(){
		ArrayList<String> arrListCustodians = new ArrayList<String>();
		for(int i = 0;i<oldAsset.getCustodian().size();i++)
			arrListCustodians.add(oldAsset.getCustodian().get(i));
		return arrListCustodians;
	}
	
	@Override
	protected ArrayList<String> setStorageLocations(){
		ArrayList<String> arrListStorLoc = new ArrayList<String>();
		for(int i = 0;i<oldAsset.getStorageLocation().size();i++)
			arrListStorLoc.add(oldAsset.getStorageLocation().get(i));
		return arrListStorLoc;
	}
	
	
	
	@Override
	protected void callSqlCommand(Asset newAsset){
		//call add to sql here
		newAsset.setIdentifier(oldAsset.getIdentifier());
		
		try {
			new Database().updateAsset(oldAsset, newAsset);
			JOptionPane.showMessageDialog(null,
				    "Editing Successful",
				    "Transaction Success",
				    JOptionPane.PLAIN_MESSAGE);
			mainScrollPane.setColumnHeaderView(null);
			mainScrollPane.setViewportView(null);
			mainScrollPane.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				    "Transaction With SQL failed",
				    "Transaction Failed",
				    JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	@Override
	protected String setConfirmationQuestion(){
		return "Confirm these changes?";
	}
	
	public void getAssetAndSetFields(Asset oldAsset){
		this.oldAsset = oldAsset;
		setFieldsWithOldAsset();
	}
	
	public void setFieldsWithOldAsset(){
		for (Component c : panel.getComponents()) {
		    if (c.getName()!=null) { 
		       if(c.getName().equals("Asset Name"))
		    	   ((JTextField)c).setText(oldAsset.getAssetName());
		       else if(c.getName().equals("Type")){
		    	   ((JComboBox)c).setSelectedItem(oldAsset.getType().getValue());
		       }
				else if(c.getName().equals("Date Acquired"))
					((JDateChooser)c).setDate(oldAsset.getDateAcquired().getTime());
				else if(c.getName().equals("Retention Period"))
					((JDateChooser)c).setDate(oldAsset.getRetentionPeriod().getTime());
				else if(c.getName().equals("Maintenance Schedule"))
					((JComboBox)c).setSelectedItem(oldAsset.getMaintenanceSchedule().getValue());
				else if(c.getName().equals("Classification"))
					((JComboBox)c).setSelectedItem(oldAsset.getClassification().getValue());
				else if(c.getName().equals("Owner")){
					((JTextField)c).setText(oldAsset.getOwner().get(oldAsset.getOwner().size()-1));
				}
				else if(c.getName().equals("Custodian")){
					((JTextField)c).setText(oldAsset.getCustodian().get(oldAsset.getCustodian().size()-1));
				}
				else if(c.getName().equals("Storage Location")){
					((JTextField)c).setText(oldAsset.getStorageLocation().get(oldAsset.getStorageLocation().size()-1));
				}
				for(int i= 0; i<oldAsset.getValue().size();i++){
					if(c.getName().equals(oldAsset.getValue().get(i).getType().getValue()))
						((JComboBox)c).setSelectedItem(oldAsset.getValue().get(i).getLevel().getValue());
				}
		    }
		}
	}
}
