package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import views.formFactories.UpdateAssetViewFactory;
import views.formFactories.ViewFactory;


import database.Database;

import models.Asset;
import models.Reference;

public class EditAssetController {
	
	
	public EditAssetController(final JScrollPane mainScrollPane, int assetID){
		
		ViewFactory viewFact = new UpdateAssetViewFactory();
        JPanel panel = viewFact.producePanel("Edit");
        mainScrollPane.setColumnHeaderView(panel);
		mainScrollPane.setViewportView(panel);
		mainScrollPane.setVisible(true);
		
		EditAssetListener btnEditListener = new EditAssetListener();
		btnEditListener.setPanel(panel);
		btnEditListener.setMainScrollPane(mainScrollPane);
		Asset oldAsset = null;
		try {
			oldAsset = new Database().getAssetByID(assetID);
		} catch (Exception e) {}
		
		JButton editBtn = null;
		JButton cancelBtn = null;
		
		for (Component c : panel.getComponents()) {
		    if (c instanceof JButton) { 
		       if(c.getName().equals("btnEdit"))
		    	   editBtn = (JButton)c;
		       else if(c.getName().equals("btnCancel"))
		    	   cancelBtn = (JButton)c;
		    }
		    else if (c instanceof JComboBox){
			   	if(c.getName().equals("Type"))
			    	   fillTypeComboBox((JComboBox)c);
		    	else if(c.getName().equals("Maintenance Schedule"))
			    	   fillMaintenanceScheduleComboBox((JComboBox)c);
		    	else if(c.getName().equals("Classification"))
			    	   fillClassificationComboBox((JComboBox)c);
		    }
		}
		
		btnEditListener.getAssetAndSetFields(oldAsset);
		
		editBtn.addActionListener(btnEditListener);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int response = JOptionPane.showConfirmDialog (null,"All these changes will be lost. Continue?","Confirm", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(response == JOptionPane.YES_OPTION){
					mainScrollPane.setColumnHeaderView(null);
					mainScrollPane.setViewportView(null);
					mainScrollPane.setVisible(true);
				}
			}
		});
	}
	
	private void fillTypeComboBox(JComboBox cmbType){
		try {
			ArrayList<Reference> refList =  new Database().getAllType();
			for(int i = 0;i<refList.size();i++){
				cmbType.addItem(refList.get(i).getValue());
			}
		} catch (Exception e1) {
			System.out.println("Initializing CMB in Panel failed");
		}
	}
	
	private void fillMaintenanceScheduleComboBox(JComboBox cmbMaintenanceSched){
		try {
			ArrayList<Reference> refList =  new Database().getAllMaintenanceSchedule();
			for(int i = 0;i<refList.size();i++){
				cmbMaintenanceSched.addItem(refList.get(i).getValue());
			}
		} catch (Exception e1) {
			System.out.println("Initializing CMB in Panel failed");
		}
	}
	
	private void fillClassificationComboBox(JComboBox cmbClassification){
		try {
			ArrayList<Reference> refList =  new Database().getAllClassification();
			for(int i = 0;i<refList.size();i++){
				cmbClassification.addItem(refList.get(i).getValue());
			}
		} catch (Exception e1) {
			System.out.println("Initializing CMB in Panel failed");
		}
	}
}
