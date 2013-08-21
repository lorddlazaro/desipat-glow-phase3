package views.formBuilders;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;


public class EditAssetPanelBuilder extends AddAssetPanelBuilder {  //since the two forms look alike and only has a 
																 // slight difference which can be changed after 
																 //overriding some part builder functions

	@Override
	public void setTitle() {
		JLabel title = new JLabel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 8;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		title.setText("View/Edit Asset");
		title.setFont(new Font("Tahoma", Font.BOLD, 15));
		//title.setBounds(10, 11, 300, 14);
		title.setName("Title");
		panel.add(title, gbc);
	}
	
	@Override
	public void setButtons() {
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.gridx = 4;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		ImageIcon edit = new ImageIcon(EditAssetPanelBuilder.class.getResource("/resources/edit.png"));
		JButton btnEdit = new JButton(edit);
		btnEdit.setPreferredSize(new Dimension(125,36));
		btnEdit.setName("btnEdit");
		panel.add(btnEdit, gbc);
		
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.gridx = 0;
		gbc.gridy = y;
		ImageIcon cancel = new ImageIcon(EditAssetPanelBuilder.class.getResource("/resources/cancel.png"));
		JButton btnCancel = new JButton(cancel);
		btnCancel.setPreferredSize(new Dimension(125,36));
		btnCancel.setName("btnCancel");
		panel.add(btnCancel, gbc);
	}
	
	
}
