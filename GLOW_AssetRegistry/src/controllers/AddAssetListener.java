package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import views.formBuilders.TxtFieldWithErrorCheck;


import models.Reference;

import com.toedter.calendar.JDateChooser;

import database.Database;

import models.Asset;
import models.Value;


public class AddAssetListener implements ActionListener {

	protected JPanel panel;
	protected JScrollPane mainScrollPane;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(checkTextFieldsAndDateFields()){
			int response;
			String question = setConfirmationQuestion();
			response = JOptionPane.showConfirmDialog (null,question,"Confirm", 
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(response == JOptionPane.YES_OPTION){
				Asset newAsset;
				newAsset = collectFromFields();
				callSqlCommand(newAsset); 
				
			}
			
		}
	}
	
	protected Asset collectFromFields(){
		Asset newAsset = new Asset();
		//collect from fields here
		String assetName = null;
		ArrayList<String> owner = setOwners();; 
		ArrayList<String> custodian = setCustodians();
		Reference type = null;
		Calendar dateAcquired = null;
		Calendar retentionPeriod = null;
		Reference maintenanceSchedule = null;
		ArrayList<Value> values = new ArrayList<Value>();
		Reference classification = null;
		ArrayList<String> storageLocation = setStorageLocations();
		
		for (Component c : panel.getComponents()) {
		    if (c instanceof JDateChooser) { 
		       
		    	if(c.getName().equals("Date Acquired"))
					dateAcquired = ((JDateChooser)c).getCalendar();
				else if(c.getName().equals("Retention Period"))
					retentionPeriod = ((JDateChooser)c).getCalendar();
		    }
		    else if(c instanceof JTextField){
		    	if(c.getName().equals("Asset Name"))
			    	   assetName = ((JTextField)c).getText();
		    	else if(c.getName().equals("Owner")){
					if(!owner.contains(((JTextField)c).getText()))
						owner.add(((JTextField)c).getText());
				}
				else if(c.getName().equals("Custodian")){
					if(!custodian.contains(((JTextField)c).getText()))
						custodian.add(((JTextField)c).getText());
				}
				else if(c.getName().equals("Storage Location")){
					if(!storageLocation.contains(((JTextField)c).getText()))
						storageLocation.add(((JTextField)c).getText());
				}
		    }
		    else if(c instanceof JComboBox){
		    	 	if(c.getName().equals("Type")){
		    	 		ArrayList<Reference> refFromDB = new Database().getAllType();
		    	 		for(int i = 0 ; i<refFromDB.size();i++){
		    	 			if(refFromDB.get(i).getValue().equals(((JComboBox)c).getSelectedItem().toString()))
		    	 				type = refFromDB.get(i);
		    	 		}
		    	 	}
					else if(c.getName().equals("Maintenance Schedule")){
						ArrayList<Reference> refFromDB = new Database().getAllMaintenanceSchedule();
		    	 		for(int i = 0 ; i<refFromDB.size();i++){
		    	 			if(refFromDB.get(i).getValue().equals(((JComboBox)c).getSelectedItem().toString()))
		    	 				maintenanceSchedule = refFromDB.get(i);
		    	 		}
					}
					else if(c.getName().equals("Classification")){
						ArrayList<Reference> refFromDB = new Database().getAllClassification();
		    	 		for(int i = 0 ; i<refFromDB.size();i++){
		    	 			if(refFromDB.get(i).getValue().equals(((JComboBox)c).getSelectedItem().toString()))
		    	 				classification = refFromDB.get(i);
		    	 		}
					}
						
					else if(isValueTypes(c)!= null){
						if(((JComboBox)c).getSelectedItem()!=null){
							ArrayList<Reference> refFromDB = new Database().getAllValueLevel();
			    	 		for(int i = 0 ; i<refFromDB.size();i++){
			    	 			if(refFromDB.get(i).getValue().equals(((JComboBox)c).getSelectedItem().toString()))
			    	 				values.add(new Value(isValueTypes(c),refFromDB.get(i)));
			    	 		}
						}
					}
		    }
		}
		
		/*Add all into newAsset*/
		newAsset.setAssetName(assetName);
		newAsset.setClassification(classification);
		newAsset.setCustodian(custodian);
		newAsset.setDateAcquired(dateAcquired);
		newAsset.setMaintenanceSchedule(maintenanceSchedule);
		newAsset.setOwner(owner);
		newAsset.setRetentionPeriod(retentionPeriod);
		newAsset.setStorageLocation(storageLocation);
		newAsset.setType(type);
		newAsset.setValue(values);
		
		
		return newAsset;
	}
	
	protected ArrayList<String> setOwners(){
		return new ArrayList<String>();
	}
	protected ArrayList<String> setCustodians(){
		return new ArrayList<String>();
	}
	protected ArrayList<String> setStorageLocations(){
		return new ArrayList<String>();
	}
	
	protected Reference isValueTypes(Component c){
		ArrayList<Reference> valueTypes = null;
		
		try {
			valueTypes = new Database().getAllValueType();
		} catch (Exception e) {}
		for(int i = 0 ;i<valueTypes.size();i++){
			if(c.getName().equals(valueTypes.get(i).getValue()))
				return valueTypes.get(i);
		}
		return null;
	}
	
	protected String setConfirmationQuestion(){
		return "Are you sure you want to register this new Asset?";
	}
	
	protected void callSqlCommand(Asset newAsset){
		try {
			new Database().insertAsset(newAsset);
			JOptionPane.showMessageDialog(null,
				    "Adding Successful",
				    "Transaction Success",
				    JOptionPane.PLAIN_MESSAGE);
			mainScrollPane.setColumnHeaderView(null);
			mainScrollPane.setViewportView(null);
			mainScrollPane.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
				    "Failed to Add in SQL",
				    "Transaction Failed",
				    JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public boolean checkTextFieldsAndDateFields(){
		JDateChooser dateAcquired = null, retentionDate = null;
		//checks if all textfields will with the error policy added inside textfields set in the builder
		for (Component c : panel.getComponents()) {
		    if (c instanceof TxtFieldWithErrorCheck) { 
		       if(!((TxtFieldWithErrorCheck)c).getErrorCheck().containsAtLeast1Letter()){
		    	   JOptionPane.showMessageDialog(null,
						    "Fields should at least contain a letter",
						    "Transaction Failed",
						    JOptionPane.ERROR_MESSAGE);
		    	   return false;
		       }  
		    }
		    
		    else if(c instanceof JDateChooser){
		    	if(c.getName().equals("Date Acquired"))
		    		dateAcquired = (JDateChooser)c;
		    	else if(c.getName().equals("Retention Period"))
		    		retentionDate = (JDateChooser)c;
		    }
		}
		//checks date sequence
		if(dateAcquired.getCalendar().getTime().after(retentionDate.getCalendar().getTime())){
			JOptionPane.showMessageDialog(null,
				    "Date Sequence is Invalid",
				    "Transaction Failed",
				    JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	public void setPanel(JPanel panel){
		this.panel = panel;
	}
	
	public void setMainScrollPane(JScrollPane scrollPane){
		this.mainScrollPane = scrollPane;
	}
	

}
