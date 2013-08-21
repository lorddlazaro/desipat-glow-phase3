package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controllers.manageFunc.manageInterface;

import models.Reference;
public class ManageSecondPageController {

	private JPanel secondPage;
	private JScrollPane mainScrollPane;
	private manageInterface manageInterface;
	private JPanel firstPage;
	JTable jTable;
	                                                       //depending on the button clicked in the
														   //first page, the manageInterface that
														  // contains the respective sql commands will
														  // be set.
	//We did this because the way we manage the references are all the same
	// Input - a button click to manage something       Output - a change in the database
	//The sql commmands are the only things changed
	public ManageSecondPageController(JPanel secondPage, JScrollPane mainScrollPane, 
									JPanel firstPage, manageInterface manageInterface){
		this.secondPage = secondPage;
		this.mainScrollPane = mainScrollPane;
		this.manageInterface = manageInterface;
		this.firstPage = firstPage;
		
		for (Component c : secondPage.getComponents()) {
		    if(c.getName().equals("Table")){
		    	   jTable = (JTable)c;
		       }
		}
		setJTableContentController();
		
		addListenersTo2ndForm();
		
	}
	
	public void addListenersTo2ndForm(){
			JButton cancelBtn = null,addBtn = null ,editBtn = null ,deleteBtn = null;
		
		for (Component c : secondPage.getComponents()) {
		    if (c instanceof JButton) { 
		    	if(c.getName().equals("btnAdd"))
		    		addBtn = (JButton) c;
		    	else if(c.getName().equals("btnEdit"))
		    		editBtn = (JButton) c;
		    	else if(c.getName().equals("btnDelete"))
		    		deleteBtn = (JButton) c;
		    	else if(c.getName().equals("btnBack"))
		    		cancelBtn = (JButton) c;
		    }
		}
		
		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToDBController();
			}
		});
		
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editToDBController();
			}
			
		});
		
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteFromDBController();
			}
			
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					mainScrollPane.setColumnHeaderView(firstPage);
					mainScrollPane.setViewportView(firstPage);
					mainScrollPane.setVisible(true);
			}
		});
	}
	
	public void addToDBController(){ //the common error checking needed in adding a reference
		String input =  JOptionPane.showInputDialog(null 
			       ,"Enter the word that you would like to add");
		if(input!=null){
			if(input.length()>0 && checkForDuplicates(input)){
				manageInterface.addToDB(input);
				setJTableContentController();
			}
			else if(input.length()==0)
				JOptionPane.showMessageDialog(null,"No characters found, Adding Failed!");
			else if(!checkForDuplicates(input)){
				JOptionPane.showMessageDialog(null,"Input is already in the database/list!");
			}
		}
	}
	public void editToDBController(){ //common error checking in managing references
		
		if(jTable.getSelectedRowCount()==0){
			JOptionPane.showMessageDialog(null,"Please select an entry to edit.");
		}
		else if (jTable.getSelectedRowCount()>1){
			JOptionPane.showMessageDialog(null,"You can only edit one at a time.");
		}
		else if(jTable.getSelectedRowCount()==1){
			String selected = jTable.getValueAt(jTable.getSelectedRow(), 0).toString();
			String input =  JOptionPane.showInputDialog(null 
				       ,"Edit '"+ selected +"' to: ");
			if (input!=null) {
				if(input.length()>0 && checkForDuplicates(input)){
					ArrayList<Reference> arrayReference = manageInterface.getListFromDB();
					Reference oldRef = arrayReference.get(jTable.getSelectedRow());
					Reference newRef = new Reference();
					newRef.setIdentifier(oldRef.getIdentifier());
					newRef.setValue(input);
					newRef.setHidden(oldRef.isHidden());
					manageInterface.editToDB(oldRef,newRef);
				}
				else if (input.length()==0){
					JOptionPane.showMessageDialog(null,"No characters found, Editing failed!");
				}
				else if(!checkForDuplicates(input)){
					JOptionPane.showMessageDialog(null,"Input is already in the database/list!");
				}
			}
			setJTableContentController();
		}
		
	}
	public void deleteFromDBController(){ //common delete algo for all references
		
		if(jTable.getSelectedRowCount()==0){
			JOptionPane.showMessageDialog(null,"Please select an entry to delete.");
		}
		else if (jTable.getSelectedRowCount()>1){
			JOptionPane.showMessageDialog(null,"You can only delete one at a time.");
		}
		else if(jTable.getSelectedRowCount()== 1){
			int response = JOptionPane.showConfirmDialog(null, "Deleting '"+jTable.getValueAt(jTable.getSelectedRow(), 0)+"' \n" +
					"Are you sure?","Confirmation",JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				ArrayList<Reference> arrayReference = manageInterface.getListFromDB();
				Reference ref = arrayReference.get(jTable.getSelectedRow());
				Reference newRef = new Reference();
				newRef.setIdentifier(ref.getIdentifier());
				newRef.setValue(ref.getValue());
				newRef.setHidden(true);
				manageInterface.editToDB(ref,newRef);
				setJTableContentController();
			}
		}
			
		
	}
	public void setJTableContentController(){
		ArrayList<Reference> arrayReference = manageInterface.getListFromDB();
		ArrayList<String> arrayContent = new ArrayList<String>();
		for(int i = 0;i<arrayReference.size();i++){
			arrayContent.add(arrayReference.get(i).getValue());
		}
		setTable(arrayContent);
	}
	
	protected boolean checkForDuplicates(String input){ //check if an input about to be placed in the DB
														// already has a duplicate
		
		
		for(int i=0;i<jTable.getRowCount();i++){
			if(input.equals(jTable.getValueAt(i, 0)))
				return false;
		}
		return true;
		
	}
	
	protected void setTable(ArrayList<String> arrayContent){  //since it will be used by Add,Edit, and Display
		if(arrayContent!=null){
			
			Vector newRow = new Vector();
			DefaultTableModel newModel = (DefaultTableModel) jTable.getModel();
			newModel.getDataVector().removeAllElements();
			
	        for(int i=0;i<arrayContent.size();i++){
	        	newRow = new Vector();
	        	newModel.addRow(newRow);
	        	newModel.setValueAt(arrayContent.get(i), i, 0);
	        }
	        jTable.setModel(newModel);
	        
	        JScrollPane scrollPane = null;
	        
	        for (Component c : secondPage.getComponents()) {
			    if (c instanceof JScrollPane) { 
			    	if(c.getName().equals("Scroll Pane"))
			    		scrollPane = (JScrollPane)c;
			    }
			}
	        
	        
	        scrollPane.setViewportView(jTable);
			
			
		}
		else{
			mainScrollPane.setColumnHeaderView(null);
			mainScrollPane.setViewportView(null);
			mainScrollPane.setVisible(true);
			JOptionPane.showMessageDialog(null,"Error Occured in loading table, Form is going to exit!");
			
		}
	}
}
