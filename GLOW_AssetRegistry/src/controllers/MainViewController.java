package controllers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import views.formBuilders.ActionHistoryPanelBuilder;
import views.formBuilders.ManageFirstPagePanelBuilder;
import views.formBuilders.PanelBuilder;
import views.formBuilders.PanelBuilderDirector;
import views.formBuilders.SearchPanelBuilder;
import views.formFactories.UpdateAssetViewFactory;
import views.formFactories.ViewFactory;





public class MainViewController {

	JPanel mainViewPanel;
	
	public MainViewController(JPanel panel){
		this.mainViewPanel = panel;
		addListenersToLabels();
	}
	
	public void addListenersToLabels(){
		JButton btnAdd = null,btnSearch = null,btnActHistory = null,btnManage = null;
		int i = 0;
		
		for (Component c : mainViewPanel.getComponents()) {
		    if (c instanceof JButton) { 
		    	if(c.getName().equals("btnAdd"))
		    		btnAdd = (JButton)c;
		    	else if(c.getName().equals("btnSearch"))
		    		btnSearch = (JButton)c;
		    	else if(c.getName().equals("btnActHistory"))
		    		btnActHistory = (JButton)c;
		    	else if(c.getName().equals("btnManage"))
		    		btnManage = (JButton)c;
		    }
		}
		//main menu.
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ViewFactory viewFact = new UpdateAssetViewFactory();
                JPanel panel= viewFact.producePanel("Add");
                JScrollPane mainScrollPane = getScrollPaneFromForm();
                
                mainScrollPane.setColumnHeaderView(panel);
                mainScrollPane.setViewportView(panel);
                mainScrollPane.setVisible(true);
                try {
						new AddAssetController(panel,mainScrollPane);
                } catch (Exception e) {
					e.printStackTrace();
					}
			}
		});
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelBuilder viewBuilder = new SearchPanelBuilder();
				PanelBuilderDirector viewBuilderDirector = new PanelBuilderDirector(viewBuilder);
				JPanel panel = viewBuilderDirector.makePanel();
				JScrollPane mainScrollPane = getScrollPaneFromForm();
				
				mainScrollPane.setColumnHeaderView(panel);
				mainScrollPane.setViewportView(panel);
				mainScrollPane.setVisible(true);
				
				new SearchAssetController(panel, mainScrollPane);
				
			}
		});
		
		btnActHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelBuilder viewBuilder = new ActionHistoryPanelBuilder();
				PanelBuilderDirector viewBuilderDirector = new PanelBuilderDirector(viewBuilder);
				JPanel panel = viewBuilderDirector.makePanel();
				
				JScrollPane mainScrollPane = getScrollPaneFromForm();
				
				mainScrollPane.setColumnHeaderView(panel);
				mainScrollPane.setViewportView(panel);
				mainScrollPane.setVisible(true);
				new ActionHistoryController(panel);
			}
		});
		
		btnManage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelBuilder viewBuilder = null;
				viewBuilder = new ManageFirstPagePanelBuilder();
				PanelBuilderDirector viewBuilderDirector = new PanelBuilderDirector(viewBuilder);
				JPanel panel = viewBuilderDirector.makePanel();
				JScrollPane mainScrollPane = getScrollPaneFromForm();
				
				mainScrollPane.setColumnHeaderView(panel);
				mainScrollPane.setViewportView(panel);
				mainScrollPane.setVisible(true);
				new ManageFirstPageController(panel,mainScrollPane);
			}
		});
	}
	
	public JScrollPane getScrollPaneFromForm(){
		
		JScrollPane mainScrollPane = null;
		
		for (Component c : mainViewPanel.getComponents()) {
		    if (c instanceof JScrollPane) { 
		    	if(c.getName().equals("ScrollPane"))
		    		mainScrollPane = (JScrollPane)c;
		    }
		}
		return mainScrollPane;
	}
}
