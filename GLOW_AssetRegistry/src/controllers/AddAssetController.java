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

import database.Database;

import models.Reference;



public class AddAssetController {
	public AddAssetController(JPanel panel,final JScrollPane mainScrollPane) throws Exception{
		AddAssetListener btnAddListener = new AddAssetListener();
		btnAddListener.setPanel(panel); //passes the form to the listener class	
		btnAddListener.setMainScrollPane(mainScrollPane);
		JButton addBtn = null;
		JButton cancelBtn = null;
		
		for (Component c : panel.getComponents()) {
		    if (c instanceof JButton) { 
		       if(c.getName().equals("btnAdd"))
		    	   addBtn = (JButton)c;
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
		
		addBtn.addActionListener(btnAddListener);
		
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
