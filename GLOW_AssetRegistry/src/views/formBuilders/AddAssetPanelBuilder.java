package views.formBuilders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import models.Reference;

import com.toedter.calendar.JDateChooser;

import database.Database;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;


public class AddAssetPanelBuilder implements PanelBuilder{

	JPanel panel;
	int y = 10; 
	GridBagConstraints gbc = new GridBagConstraints();
	
	@Override
	public void construct() {
		panel = new JPanel(new GridBagLayout());
	}
	
	@Override
	public void setTitle() {
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5,5,5,5);
		gbc.weightx = 1;
		gbc.weighty = 8;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		JLabel title = new JLabel();
		title.setText("Add Asset");
		title.setFont(new Font("Tahoma", Font.BOLD, 15));
		title.setName("Title");
		panel.add(title, gbc);
		
	}

	@Override
	public void setFields(){
		//panel.setBackground(new Color(153, 255, 204));
		gbc.insets = new Insets(2,2,2,2);
		gbc.weighty = 0;
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		JLabel lblAssetName = new JLabel("Asset Name:");
		panel.add(lblAssetName, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		TxtFieldWithErrorCheck txtAssetName = new TxtFieldWithErrorCheck();
		txtAssetName.setErrorCheck(new containLetters());
		txtAssetName.getErrorCheck().setTextField(txtAssetName);
		txtAssetName.setColumns(10);
		txtAssetName.setName("Asset Name");
		panel.add(txtAssetName, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		JLabel lblOwner = new JLabel("Owner:");
		panel.add(lblOwner, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		TxtFieldWithErrorCheck txtOwner = new TxtFieldWithErrorCheck();
		txtOwner.setErrorCheck(new containLetters());
		txtOwner.getErrorCheck().setTextField(txtOwner);
		txtOwner.setName("Owner");
		panel.add(txtOwner, gbc);
		txtOwner.setColumns(10);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		JLabel lblCustodian = new JLabel("Custodian:");
		panel.add(lblCustodian, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		TxtFieldWithErrorCheck txtCustodian = new TxtFieldWithErrorCheck();
		txtCustodian.setErrorCheck(new containLetters());
		txtCustodian.getErrorCheck().setTextField(txtCustodian);
		txtCustodian.setName("Custodian");
		panel.add(txtCustodian, gbc);
		txtCustodian.setColumns(10);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.NONE;
		JLabel lblType = new JLabel("Type:");
		panel.add(lblType, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JComboBox cmbType = new JComboBox();
		cmbType.setName("Type");
		panel.add(cmbType, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.NONE;
		JLabel lblDateAcquired = new JLabel("Date Acquired:");
		panel.add(lblDateAcquired, gbc);
		
		Calendar cal = Calendar.getInstance();
		
		gbc.gridx = 3;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JDateChooser dtcDateAcquired = new JDateChooser();
		dtcDateAcquired.setDate(cal.getTime());
		dtcDateAcquired.setName("Date Acquired");
		panel.add(dtcDateAcquired, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.fill = GridBagConstraints.NONE;
		JLabel lblRetentionPeriod = new JLabel("Retention Period:");
		panel.add(lblRetentionPeriod, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 6;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JDateChooser dtcRetentionPeriod = new JDateChooser();
		dtcRetentionPeriod.setDate(cal.getTime());
		dtcRetentionPeriod.setName("Retention Period");
		panel.add(dtcRetentionPeriod, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.fill = GridBagConstraints.NONE;
		JLabel lblMaintenanceSchedule = new JLabel("Maintenance Schedule:");
		panel.add(lblMaintenanceSchedule, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 7;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JComboBox cmbMaintenanceSched = new JComboBox();
		cmbMaintenanceSched.setName("Maintenance Schedule");
		panel.add(cmbMaintenanceSched, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.fill = GridBagConstraints.NONE;
		JLabel lblClassification = new JLabel("Classification:");
		panel.add(lblClassification, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 8;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		JComboBox cmbClassification = new JComboBox();
		cmbClassification.setName("Classification");
		panel.add(cmbClassification, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.fill = GridBagConstraints.NONE;
		JLabel lblStorageLocation = new JLabel("Storage Location:");
		panel.add(lblStorageLocation, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 9;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		TxtFieldWithErrorCheck txtStorageLocation = new TxtFieldWithErrorCheck();
		txtStorageLocation.setErrorCheck(new containLetters());
		txtStorageLocation.getErrorCheck().setTextField(txtStorageLocation);
		txtStorageLocation.setName("Storage Location");
		panel.add(txtStorageLocation, gbc);
		
		
		setValueTypesFields(); // for dynamic adding of comboboxes for valuetypes
		gbc.weighty = 10;
		
	}

	@Override
	public void setButtons() {
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		
		gbc.gridx = 4;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.NONE;
		ImageIcon submit = new ImageIcon(AddAssetPanelBuilder.class.getResource("/resources/submit.png"));
		JButton btnAdd = new JButton(submit);
		btnAdd.setName("btnAdd");
		btnAdd.setPreferredSize(new Dimension(125,36));
		panel.add(btnAdd, gbc);
		
		gbc.anchor = GridBagConstraints.SOUTHWEST;
		gbc.gridx = 0;
		gbc.gridy = y;
		ImageIcon cancel = new ImageIcon(AddAssetPanelBuilder.class.getResource("/resources/cancel.png"));
		JButton btnCancel = new JButton(cancel);
		btnCancel.setName("btnCancel");
		btnCancel.setPreferredSize(new Dimension(125,36));
		panel.add(btnCancel, gbc);
	}

	@Override
	public JPanel getPanel() {
		
		return panel;
	}

	
	protected void setValueTypesFields(){
		try {
			ArrayList<Reference> valueTypes = new Database().getAllValueType();
			ArrayList<Reference> valueLevels = new Database().getAllValueLevel();
			ArrayList<String> arrValueLevels = new ArrayList<String>();
			for(int i = 0 ; i <valueLevels.size();i++)
				arrValueLevels.add(valueLevels.get(i).getValue());
			for(int i=0;i<valueTypes.size();i++){
				JLabel newLabel = new JLabel(valueTypes.get(i).getValue());
				JComboBox newCmb = new JComboBox(arrValueLevels.toArray()); //adds dynamically the number of valuetypes into the AddAssetpanel
				gbc.gridx = 1;
				gbc.gridy = y;
				gbc.fill = GridBagConstraints.NONE;
				panel.add(newLabel, gbc);
				gbc.gridx = 3;
				gbc.gridy = y;
				gbc.fill = GridBagConstraints.HORIZONTAL;
				panel.add(newCmb, gbc);
				newCmb.setName(valueTypes.get(i).getValue());
				y++;
			}
			panel.setPreferredSize(panel.getPreferredSize());
			
		} catch (Exception e) {}
	}
	

}
