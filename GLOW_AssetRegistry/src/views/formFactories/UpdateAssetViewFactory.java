package views.formFactories;

import javax.swing.JPanel;

import views.formBuilders.AddAssetPanelBuilder;
import views.formBuilders.EditAssetPanelBuilder;
import views.formBuilders.PanelBuilder;
import views.formBuilders.PanelBuilderDirector;





public class UpdateAssetViewFactory implements ViewFactory{

	
	@Override
	public JPanel producePanel(String type) {     //used to produce Add and Edit Forms built by their Builders
		PanelBuilder viewBuilder = null;
		if (type.equalsIgnoreCase("Add"))
			viewBuilder = new AddAssetPanelBuilder();
		else if(type.equalsIgnoreCase("Edit"))
			viewBuilder = new EditAssetPanelBuilder();
		
		PanelBuilderDirector viewBuilderDirector = new PanelBuilderDirector(viewBuilder);
		JPanel panel = viewBuilderDirector.makePanel();
		
		
		
		return panel;
		
	}

}
