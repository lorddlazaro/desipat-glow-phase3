package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import java.awt.event.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.table.TableRowSorter;

import views.AssetHistoryFrame;
import views.formBuilders.AssetHistoryPanelBuilder;
import views.formBuilders.PanelBuilder;
import views.formBuilders.PanelBuilderDirector;


import models.Asset;

import controllers.EditAssetController;
import controllers.ViewAssetHistoryController;
import database.Database;

public class SearchAssetController {

	private JPanel searchPanel;
	private JScrollPane mainScrollPane;
	private JTable table;
	
	private JButton btnAssetHistory = null ,btnSearch = null,btnEdit = null,btnRefresh = null;
	private JTextField searchBox = null;
	private JComboBox dateTypeComboBox = null;
	private JDateChooser dateFrom = null, dateTo = null;
	
	private TableRowSorter<TableModel> sorter = null;
	private Object[][] tableData = {{},{}};

	private String[] tableColumnName = {"ID","Name","Owner","Custodian","Date Acquired","Retention Period","Type","Maintenance Period","Classification"}; // changed
	private String startDateString = null, endDateString = null;
	
	public SearchAssetController(JPanel searchPanel, JScrollPane mainScrollPane){
		this.searchPanel = searchPanel;
		this.mainScrollPane = mainScrollPane;
		this.table = getTableFromPanel();
		if(table == null){
			mainScrollPane.setColumnHeaderView(null);
			mainScrollPane.setViewportView(null);
			mainScrollPane.setVisible(true);
			JOptionPane.showMessageDialog(null,
				    "Failed to Load Table, Search function gonna close!",
				    "Load Failed",
				    JOptionPane.ERROR_MESSAGE);
			this.mainScrollPane.setColumnHeaderView(null);
            this.mainScrollPane.setViewportView(null);
            this.mainScrollPane.setVisible(true);
		}
		findComponentsAndAddListeners();
		
		String[][] data = {};
		TableModel tableModel = new DefaultTableModel(tableData,tableColumnName){
			 public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
		};
		table.setModel(tableModel);
		
		//Sorter for all table // initially set to the DefaultTableModel model
		sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		setJTableData();
	}
	
	public void findComponentsAndAddListeners(){
		
		for (Component c : searchPanel.getComponents()) {
		    if (c instanceof JButton) { 
		       if(c.getName().equals("btnAdd"))
		    	   btnSearch = (JButton)c;
		       else if(c.getName().equals("btnEdit"))
		    	   btnEdit = (JButton)c;
		       else if(c.getName().equals("btnRefresh"))
		    	   btnRefresh = (JButton)c;
		       else if(c.getName().equals("btnAssetHistory"))
		    	   btnAssetHistory = (JButton)c;
		       else if(c.getName().equals("btnSearch"))
		    	   btnSearch = (JButton)c;
		    }
		    else if(c instanceof JTextField){
		    	if(c.getName().equals("Search Field"))
		    		searchBox = (JTextField)c;
		    }
		    else if(c instanceof JComboBox){
		    	if(c.getName().equals("Date Type Combo Box"))
		    		dateTypeComboBox = (JComboBox)c;
		    }
		    else if(c instanceof JDateChooser){
		    	if(c.getName().equals("Date From"))
		    		dateFrom = (JDateChooser)c;
		    	else if(c.getName().equals("Date To"))
		    		dateTo = (JDateChooser)c;
		    }
		}
		
		searchBox.addKeyListener(new searchBoxKeyListener());
		btnSearch.addActionListener(new search());
		dateTypeComboBox.addActionListener(new comboBoxListener());
		table.addMouseListener(new jtableMouseListener());
		btnRefresh.addActionListener(new btnRefreshActionListener());
		btnEdit.addActionListener(new btnEditActionListener());
		btnAssetHistory.addActionListener(new btnViewAssetHistoryActionListener());
		
	}
	
	class searchBoxKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				searchButton();
			}
		}
	}

	class search implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			searchButton();
		}
	}

	class comboBoxListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			if(dateTypeComboBox.getSelectedIndex() == 2){
				dateFrom.setEnabled(false);
				dateTo.setEnabled(false);
			}
			else{
				dateFrom.setEnabled(true);
				dateTo.setEnabled(true);
			}
		}
	}
	class jtableMouseListener extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2)
				try {
					
					if(table.getSelectedRow()>=0 && table.getSelectedRow()<table.getRowCount()){
						int assetID = getAssetIdFromSelectedRow();
						new EditAssetController(mainScrollPane,assetID);
					}

				} catch (Exception e1) {
					e1.printStackTrace();
				}
		}
	} 
	
	class btnEditActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(table.getSelectedRow()>=0 && table.getSelectedRow()<table.getRowCount()){
				int assetID;
				try {
					assetID = getAssetIdFromSelectedRow();
					new EditAssetController(mainScrollPane,assetID);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null,
						    "Cannot Open Edit Function!",
						    "Load Failed",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
	}
	
	class btnRefreshActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			searchBox.setText("");
			DefaultTableModel newModel = (DefaultTableModel) table.getModel();
			newModel.getDataVector().removeAllElements();
			newFilter();
			setJTableData();
		}
	}
		
	class btnViewAssetHistoryActionListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			showAssetHistory();
		}
	}	
	
	public void showAssetHistory(){
		
		PanelBuilder builder = new AssetHistoryPanelBuilder();
		PanelBuilderDirector director = new PanelBuilderDirector(builder);
		JPanel panel = director.makePanel();
		AssetHistoryFrame assetHistoryFrame = new AssetHistoryFrame(panel);
			
		if(table.getSelectedRowCount()==0)
			JOptionPane.showMessageDialog(null,"Please select an entry to edit");
		else if(table.getSelectedRowCount()>1)
			JOptionPane.showMessageDialog(null,"You can only edit one entry at a time");
		else if (table.getSelectedRow()>=0 && table.getSelectedRow()<table.getRowCount()){
			try {
				new ViewAssetHistoryController(panel,getAssetIdFromSelectedRow());
				assetHistoryFrame.setVisible(true);

			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null,
					    "Failed to Load Asset History Table!",
					    "Load Failed",
					    JOptionPane.ERROR_MESSAGE);
			} 
		}
	}
	
	private int getAssetIdFromSelectedRow() throws Exception{
		return Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
		
	}
	
	private void newFilter()  
	{  
		RowFilter< TableModel  , Object> rf = null;   
		//declare a row filter for your table model  
		try  
		{  
			rf = RowFilter.regexFilter("(?i)" + searchBox.getText());
		}  
		catch (java.util.regex.PatternSyntaxException e)  
		{ }  
		sorter.setRowFilter(rf);  
	}
	
	
	public void searchButton(){

		//System.out.println("Search is clicked");
		//restartTable(); // sets the original contents of the table
		setJTableData();
		if(checkDate()) // checks if date range is valid
			setFilteredJTableData();
		newFilter(); // filters the table based on the keyword typed

	}
	
public void setJTableData() {
		
		DefaultTableModel tempModel = /*new DefaultTableModel();*/(DefaultTableModel)table.getModel();
		tempModel.getDataVector().removeAllElements();
		tempModel.setColumnIdentifiers(tableColumnName);
		try {
			ArrayList<Asset> data = new Database().getAllAssets();
			if(data.size() == 0){
				JOptionPane.showMessageDialog(null,
					    "No Assets To Load!!",
					    "Load Failed",
					    JOptionPane.ERROR_MESSAGE);
			this.mainScrollPane.setColumnHeaderView(null);
            this.mainScrollPane.setViewportView(null);
            this.mainScrollPane.setVisible(true);
			}
			else{
				Vector<String> newRow = new Vector();
				//DefaultTableModel newModel = (DefaultTableModel) table.getModel();
				//newModel.getDataVector().removeAllElements();
				for (int i = 0; i < data.size(); i++) {
					tempModel.setValueAt(data.get(i).getIdentifier(), i, 0);
					tempModel.setValueAt(data.get(i).getAssetName(), i, 1);
					tempModel.setValueAt(getLast(data.get(i).getOwner()),i,2);
					tempModel.setValueAt(getLast(data.get(i).getCustodian()),i,3);
					tempModel.setValueAt(formatDate(data.get(i).getDateAcquired()), i, 4);
					tempModel.setValueAt(formatDate(data.get(i).getRetentionPeriod()), i, 5);
					tempModel.setValueAt(data.get(i).getType().getValue(), i, 6);
					tempModel.setValueAt(data.get(i).getMaintenanceSchedule().getValue(), i, 7);
					tempModel.setValueAt(data.get(i).getClassification().getValue(), i, 8);
				}
			}
		
		} catch (Exception e) {}
	
		table.setModel(tempModel);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		
		 JScrollPane scrollPane = null;
		 for (Component c : searchPanel.getComponents()) {
			    if (c instanceof JScrollPane) { 
			    	if(c.getName().equals("Scroll Pane"))
			    		scrollPane = (JScrollPane)c;
			    }
			}
	     scrollPane.setViewportView(table);
	}


public Boolean checkDate() { // checks if the date range is valid
	Boolean flag = false;
	try{
		
		if(((dateFrom != null && dateTo != null) 
		&& (dateTypeComboBox.getSelectedIndex() == 0 || dateTypeComboBox.getSelectedIndex() == 1))){
	
			Date start = dateFrom.getCalendar().getTime();
			Date end = dateTo.getCalendar().getTime();
			System.out.println("Start: "+start);
			System.out.println("End: "+end);
		
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("Formatted time: "+dateFormat.format(start));
			startDateString = dateFormat.format(start);
			endDateString = dateFormat.format(end);
		
			flag = true;
		}
		else if (dateTypeComboBox.getSelectedIndex() == 2){
			
		}
		else{
			JOptionPane.showMessageDialog(null,
				    "No date selected.",
				    "Invalid Request",
				    JOptionPane.ERROR_MESSAGE);
		}
	}catch(Exception e){};
	
	System.out.println("flag"+flag );
	return flag;
}

// populates the JTable with the filtered data from the database
public void setFilteredJTableData() {
	
	DefaultTableModel tempModel = (DefaultTableModel)table.getModel();
	tempModel.setColumnIdentifiers(tableColumnName);
	ArrayList<Asset> data = null;
	try {
		
		if(dateTypeComboBox.getSelectedIndex()== 0){
			System.out.println("Start Date: " +startDateString);
			System.out.println("End Date: " +endDateString);

			data = new Database().getfilteredAssets(startDateString, endDateString,"DateAcquired");
		}
		else if (dateTypeComboBox.getSelectedItem()== "Retention Period"){
			data = new Database().getfilteredAssets(startDateString, endDateString,"RetentionPeriod");
		}
		else data = new Database().getAllAssets();

		System.out.println("size: "+data.size());
		
		if(data.size() == 0){
			JOptionPane.showMessageDialog(null,
				    "No Assets To Load!!",
				    "Load Failed",
				    JOptionPane.ERROR_MESSAGE);
		this.mainScrollPane.setColumnHeaderView(null);
        this.mainScrollPane.setViewportView(null);
        this.mainScrollPane.setVisible(true);
		}
		else{
			Vector<String> newRow = new Vector();
		//	DefaultTableModel newModel = (DefaultTableModel) table.getModel();
		//	newModel.getDataVector().removeAllElements();
		//	DefaultTableModel newModel = new DefaultTableModel();
		//	newModel.setColumnIdentifiers(tableColumnName);
			
			for (int i = 0; i < data.size(); i++) {
	        //	newModel.addRow(newRow);
				newRow.add(null);
				tempModel.addRow(newRow);
				tempModel.setValueAt(data.get(i).getIdentifier(), i, 0);
				tempModel.setValueAt(data.get(i).getAssetName(), i, 1);
				tempModel.setValueAt(getLast(data.get(i).getOwner()),i,2);
				tempModel.setValueAt(getLast(data.get(i).getCustodian()),i,3);
				tempModel.setValueAt(formatDate(data.get(i).getDateAcquired()), i, 4);
				tempModel.setValueAt(formatDate(data.get(i).getRetentionPeriod()), i, 5);
				tempModel.setValueAt(data.get(i).getType().getValue(), i, 6);
				tempModel.setValueAt(data.get(i).getMaintenanceSchedule().getValue(), i, 7);
				tempModel.setValueAt(data.get(i).getClassification().getValue(), i, 8);
				newRow = new Vector();
			}
		}
	
	} catch (Exception e) {}

	table.setModel(tempModel);
	table.getColumnModel().getColumn(0).setMinWidth(0);
	table.getColumnModel().getColumn(0).setMaxWidth(0);
	
	 JScrollPane scrollPane = null;
	 for (Component c : searchPanel.getComponents()) {
		    if (c instanceof JScrollPane) { 
		    	if(c.getName().equals("Scroll Pane"))
		    		scrollPane = (JScrollPane)c;
		    }
		}
     scrollPane.setViewportView(table);
}

public String formatDate(Calendar date){

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	return dateFormat.format(date.getTime());
}

//Gets the last element in the ArrayList<String>
public String getLast(ArrayList<String> list){
	if(!list.isEmpty())
		return list.get(list.size()-1);
	else
		return null;

} 


public JTable getTableFromPanel(){
	for (Component c : searchPanel.getComponents()) {
		if (c instanceof JTable) { 
			if(c.getName().equals("Table")){
				return (JTable)c;
			}
		}
	}
	return null;

}
	
	
	
}
