package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controllers.manageFunc.manageClassifications;
import controllers.manageFunc.manageMaintSched;
import controllers.manageFunc.manageType;
import controllers.manageFunc.manageVType;
import controllers.manageFunc.manageVValue;

import views.formBuilders.MainPanelBuilder;
import views.formBuilders.ManageSecondPanelBuilder;
import views.formBuilders.PanelBuilder;
import views.formBuilders.PanelBuilderDirector;





public class ManageFirstPageController {

	final private JPanel firstPanel;
	private JScrollPane mainScrollPane;
	public ManageFirstPageController(JPanel firstPanel, JScrollPane mainScrollPane){
		this.firstPanel = firstPanel;
		this.mainScrollPane = mainScrollPane;
		addListenersTo1stForm();
		
		
	}
	//depending on the button that will be clicked, it passes their respective manageFunctions (containing
																		//the sql functions to be called
	public void addListenersTo1stForm(){
		JButton btnType = null,btnClassification = null,btnVType = null,btnVValue = null	,btnMaintSched = null;
		
		for (Component c : firstPanel.getComponents()) {
			
		    if (c instanceof JButton) { 
		    	if(c.getName().equals("Manage Types"))
		    		btnType = (JButton)c;
		    	else if(c.getName().equals("Manage Classifications"))
		    		btnClassification = (JButton)c;
		    	else if(c.getName().equals("Manage Value Types"))
		    		btnVType = (JButton)c;
		    	else if(c.getName().equals("Manage Value Levels"))
		    		btnVValue = (JButton)c;
		    	else if(c.getName().equals("Manage Maintenance Schedules"))
		    		btnMaintSched = (JButton)c;
		    }
		}
		PanelBuilder viewBuilder;
		viewBuilder = new ManageSecondPanelBuilder();
		final PanelBuilderDirector viewBuilderDirector = new PanelBuilderDirector(viewBuilder);
		
		
		btnType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = viewBuilderDirector.makePanel();
				for (Component c : panel.getComponents()) {
				    if (c instanceof JLabel) { 
				       if(c.getName().equals("Title")){
				    	   ((JLabel)c).setText("Manage Types");
				       }
				    }
				}
				ManageSecondPageController secPageController = new ManageSecondPageController(panel,
																		mainScrollPane,firstPanel,
																		new manageType());
				mainScrollPane.setColumnHeaderView(panel);
	            mainScrollPane.setViewportView(panel);
	            mainScrollPane.setVisible(true);
			}
		});
		btnClassification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = viewBuilderDirector.makePanel();
				for (Component c : panel.getComponents()) {
				    if (c instanceof JLabel) { 
				       if(c.getName().equals("Title"))
				    	   ((JLabel)c).setText("Manage Classifications");
				    }
				}
				
				ManageSecondPageController secPageController = new ManageSecondPageController(panel,
																		mainScrollPane,firstPanel,
																		new manageClassifications());
				mainScrollPane.setColumnHeaderView(panel);
	            mainScrollPane.setViewportView(panel);
	            mainScrollPane.setVisible(true);
			}
		});
		btnVType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = viewBuilderDirector.makePanel();
				for (Component c : panel.getComponents()) {
				    if (c instanceof JLabel) { 
				       if(c.getName().equals("Title"))
				    	   ((JLabel)c).setText("Manage Value Types");
				    }
				}
				ManageSecondPageController secPageController = new ManageSecondPageController(panel,
																		mainScrollPane,firstPanel
																		,new manageVType());
				mainScrollPane.setColumnHeaderView(panel);
	            mainScrollPane.setViewportView(panel);
	            mainScrollPane.setVisible(true);
			}
		});
		btnVValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = viewBuilderDirector.makePanel();
				for (Component c : panel.getComponents()) {
				    if (c instanceof JLabel) { 
				       if(c.getName().equals("Title"))
				    	   ((JLabel)c).setText("Manage Value Levels");
				    }
				}
				ManageSecondPageController secPageController = new ManageSecondPageController(panel,
																		mainScrollPane,firstPanel
																		,new manageVValue());
				mainScrollPane.setColumnHeaderView(panel);
	            mainScrollPane.setViewportView(panel);
	            mainScrollPane.setVisible(true);
			}
		});
		btnMaintSched.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel panel = viewBuilderDirector.makePanel();
				for (Component c : panel.getComponents()) {
				    if (c instanceof JLabel) { 
				       if(c.getName().equals("Title"))
				    	   ((JLabel)c).setText("Manage Maintenance Schedules");
				    }
				}
				ManageSecondPageController secPageController = new ManageSecondPageController(panel,
																		mainScrollPane,firstPanel,
																		new manageMaintSched());
				mainScrollPane.setColumnHeaderView(panel);
	            mainScrollPane.setViewportView(panel);
	            mainScrollPane.setVisible(true);
				
			}
		});
		
		
	}
	
	
}
