package views.formBuilders;

import javax.swing.JPanel;


public class PanelBuilderDirector {
	PanelBuilder viewBuilder;
	
	public PanelBuilderDirector(PanelBuilder viewBuilder){
		this.viewBuilder = viewBuilder;
	}
	
	public JPanel makePanel(){
		viewBuilder.construct();
		viewBuilder.setTitle();
		viewBuilder.setFields();
		viewBuilder.setButtons();
		
		return viewBuilder.getPanel();
	}
	
}
