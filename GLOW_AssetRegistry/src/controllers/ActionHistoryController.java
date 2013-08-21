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
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

import models.AssetLogEntry;

import com.toedter.calendar.JDateChooser;

import database.Database;

public class ActionHistoryController {

	JPanel panel;
	JTable table = null;
	
	public ActionHistoryController(JPanel panel){
		this.panel = panel;
		JButton searchBtn = null;
		
		for (Component c : panel.getComponents()) {
		    if (c instanceof JScrollPane) { 
		       if(c.getName().equals("Scroll Pane")){
		    	   JScrollPane scrollPane = (JScrollPane)c;
		    	   JViewport viewport = scrollPane.getViewport(); 
		    	   table = (JTable)viewport.getView();
		       }
		    }
		    else if(c instanceof JButton){
		    	if(c.getName().equals("btnSearch"))
		    		searchBtn = (JButton)c;
		    }
		}
		
		initializeTable();
		
		if(searchBtn!=null){
			searchBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					search();
				}
			});
		}
		
		
	}
	
	protected void initializeTable(){
		
		if(table !=null){
			try {
				ArrayList<AssetLogEntry> logs = new Database().getAllActions();
				setTableData(logs);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Error in fetching from database!!!!","ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(null,"Error in fetching from database!","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void search(){
		JDateChooser dateFrom=null,dateTo=null;
		
		for (Component c : panel.getComponents()) {
		    if (c instanceof JDateChooser) { 
		       if(c.getName().equals("Date From"))
		    	   dateFrom = (JDateChooser)c;
		       else if(c.getName().equals("Date To"))
		    	   dateTo = (JDateChooser)c;
		    }
		}
		
		if(!dateFrom.getCalendar().getTime().after(dateTo.getCalendar().getTime())){
			ArrayList<AssetLogEntry> logs = null;
			try {
				logs = new Database().getActionsFromPeriod(dateFrom.getCalendar(),dateTo.getCalendar());
				if(logs.size()==0){
					JOptionPane.showMessageDialog(null,"Result list is empty!","Empty List",JOptionPane.DEFAULT_OPTION);
				}
				else
					setTableData(logs);
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Error in fetching from database!","ERROR",JOptionPane.ERROR_MESSAGE);
			}
			
		}else{
			JOptionPane.showMessageDialog(null,"Invalid sequence of dates!","ERROR",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void setTableData(ArrayList<AssetLogEntry> logs){
		Vector newRow = new Vector();
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.getDataVector().removeAllElements();
		for(int i=0; i<logs.size() ;i++){
			newRow.add(null);
			tableModel.addRow(newRow);
			tableModel.setValueAt(logs.get(i).getTimestamp().getTime().toString().replace("00:00:00 CST ", ""), i, 0);
			String action = logs.get(i).getAction()+": ";
			
			for(int j = 0 ;j<logs.get(i).getAffectedFields().size();j++){
				if(j<logs.get(i).getAffectedFields().size()-1 && logs.get(i).getAffectedFields().get(j)!=null )
					action+=logs.get(i).getAffectedFields().get(j)+",";
				else if(j == logs.get(i).getAffectedFields().size()-1 && logs.get(i).getAffectedFields().get(j)!=null)
					action+=logs.get(i).getAffectedFields().get(j);
			}
			tableModel.setValueAt(action, i, 1);
			
			newRow = new Vector();
		}
	}
	
	
}
