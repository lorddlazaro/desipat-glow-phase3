package views.formFactories;

import javax.swing.JPanel;

public interface ViewFactory {
	
	public JPanel producePanel(String type); //since all factories should return the object it is "manufacturing"
											//has the parameter "type" to serve as condition on what builder will be 
											// used by the factory in creating the form
}
