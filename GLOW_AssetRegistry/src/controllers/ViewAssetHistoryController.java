package controllers;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import models.AssetLogEntry;
import database.Database;

public class ViewAssetHistoryController {
	
	JPanel panel;
	DefaultTableModel historyModel;
	
	
	public ViewAssetHistoryController(JPanel panel, int id) throws Exception{
		this.panel = panel;
	
		String[][] data = {};
		String[] historyColumnName = {"Timestamp", "ID","Action"};
		ArrayList<AssetLogEntry> log;
			
		System.out.println("Id: " +id);
		try{
		log = new Database().getAssetActions(id);
		
		historyModel = new DefaultTableModel(data,historyColumnName){
			public boolean isCellEditable(int row, int col) {
				return false;
			}};
			
		Vector<String> row = new Vector();
			
		for (int i = 0; i < log.size(); i++) {
			row.add(null);
			historyModel.addRow(row);
			historyModel.setValueAt(log.get(i).getTimestamp().getTime().toString().replace("00:00:00 CST ", ""), i, 0);
		//	historyModel.setValueAt(log.get(i).getTimestamp().getTime(),i,0);
			historyModel.setValueAt(log.get(i).getAssetID(), i, 1);
			historyModel.setValueAt(log.get(i).getAction(), i, 2);
			System.out.println(log.get(i).getAction());

			row = new Vector();
		}
		JTable table = null;
		JScrollPane scrollPane = null;
		
		for (Component c : panel.getComponents()) {
		      
			if (c.getName()!=null) {
				if (c.getName().equals("Table")) {
					((JTable) c).setModel(historyModel);
					((JTable) c).removeColumn(((JTable) c).getColumnModel()
							.getColumn(1));
					table = ((JTable) c);
					System.out.println("SDA");
				}
				if (c instanceof JScrollPane) {
					if (c.getName().equals("Scroll Pane")) {
						scrollPane = ((JScrollPane) c);
						System.out.println("Hi");
					}
				}
			}
		}
		scrollPane.setViewportView(table);

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		//JOptionPane.showMessageDialog(null, "The selected asset has no log");
	}
		
		
		
		
		
	}

}
