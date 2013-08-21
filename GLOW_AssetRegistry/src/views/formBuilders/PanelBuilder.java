package views.formBuilders;

import javax.swing.JPanel;


public interface PanelBuilder {
	
	public void construct();
	public void setTitle();
	public void setFields();
	public void setButtons();
	public JPanel getPanel();
	
}
