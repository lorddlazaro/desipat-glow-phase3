package views.formBuilders;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ManageFirstPagePanelBuilder implements PanelBuilder {

	JPanel panel;
	
	@Override
	public void construct() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel = new JPanel(gridBagLayout);
	}

	@Override
	public void setTitle() {
		JLabel lblManage = new JLabel("Manage");
		lblManage.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblManage.setName("Title");
		GridBagConstraints gbc_lblManage = new GridBagConstraints();
		gbc_lblManage.insets = new Insets(0, 0, 5, 5);
		gbc_lblManage.gridx = 0;
		gbc_lblManage.gridy = 0;
		gbc_lblManage.weighty = 1;
		gbc_lblManage.anchor = GridBagConstraints.NORTH;
		panel.add(lblManage, gbc_lblManage);
	}

	@Override
	public void setFields() {
		//panel.setBackground(new Color(153, 255, 204));
		//panel.setLayout(null);
		
	}

	@Override
	public void setButtons() {
		JButton btnManageTypes = new JButton("Manage Types");
		btnManageTypes.setName("Manage Types");
		GridBagConstraints types = new GridBagConstraints();
		types.insets = new Insets(0, 0, 5, 5);
		types.gridx = 1;
		types.gridy = 2;
		//types.fill = GridBagConstraints.HORIZONTAL;
		types.anchor = GridBagConstraints.NORTH;
		//types.weighty = 1;
		types.weightx = 1;
		panel.add(btnManageTypes, types);
		
		JButton btnManageClassifications = new JButton("Manage Classifications");
		btnManageClassifications.setName("Manage Classifications");
		GridBagConstraints classifications = new GridBagConstraints();
		classifications.insets = new Insets(2, 2, 5, 5);
		classifications.gridx = 1;
		classifications.gridy = 3;
		//classifications.fill = GridBagConstraints.HORIZONTAL;
		classifications.anchor = GridBagConstraints.NORTH;
		//classifications.weighty = 1;
		panel.add(btnManageClassifications, classifications);
		
		JButton btnManageValueTypes = new JButton("Manage Value Types");
		btnManageValueTypes.setName("Manage Value Types");
		GridBagConstraints valuetypes = new GridBagConstraints();
		valuetypes.insets = new Insets(2, 2, 5, 5);
		valuetypes.gridx = 1;
		valuetypes.gridy = 4;
		//valuetypes.fill = GridBagConstraints.HORIZONTAL;
		valuetypes.anchor = GridBagConstraints.NORTH;
		//valuetypes.weighty = 1;
		panel.add(btnManageValueTypes, valuetypes);
		
		JButton btnManageValueLevels = new JButton("Manage Value Levels");
		btnManageValueLevels.setName("Manage Value Levels");
		GridBagConstraints valuelevels = new GridBagConstraints();
		valuelevels.insets = new Insets(2, 2, 5, 5);
		valuelevels.gridx = 1;
		valuelevels.gridy = 5;
		//valuelevels.fill = GridBagConstraints.HORIZONTAL;
		valuelevels.anchor = GridBagConstraints.NORTH;
		//valuelevels.weighty = 1;
		panel.add(btnManageValueLevels, valuelevels);
		
		JButton btnManageMaintenanceSchedule = new JButton("Manage Maintenance Schedules");
		btnManageMaintenanceSchedule.setName("Manage Maintenance Schedules");
		GridBagConstraints mainsched = new GridBagConstraints();
		mainsched.insets = new Insets(2, 2, 5, 5);
		mainsched.gridx = 1;
		mainsched.gridy = 6;
		//mainsched.fill = GridBagConstraints.HORIZONTAL;
		mainsched.anchor = GridBagConstraints.NORTH;
		mainsched.weighty = 2;
		panel.add(btnManageMaintenanceSchedule, mainsched);
		
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
