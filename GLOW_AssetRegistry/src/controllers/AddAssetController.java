package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



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
}
