package views.formBuilders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class ManageSecondPanelBuilder implements PanelBuilder {

	JPanel panel;
	
	@Override
	public void construct() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		scrollPane.setName("Scroll Pane");
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 4;
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		gbc_scrollPane.weightx = 1;
		gbc_scrollPane.weighty = 1;
		panel.add(scrollPane, gbc_scrollPane);
		
		
		JTable table = new JTable();
            String col[] = {""};
            String[][] data = {}; //Change to array list to display all logs
    		DefaultTableModel model = new DefaultTableModel(data,col){
                public boolean isCellEditable(int row, int col) {
                        return false;
                         }
                    };
    		table.setModel(model);
           
    	table.setName("Table");
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		panel.add(table);
		panel.add(scrollPane, gbc_scrollPane);
		
		Dimension d = table.getPreferredSize();
		scrollPane.setPreferredSize(
		    new Dimension(d.width,table.getRowHeight()*table.getRowCount()+1));
		
	}

	@Override
	public void setButtons() {
		//panel.setBackground(Color.WHITE);
		//panel.setLayout(null);
		
		JButton btnAddNew = new JButton("Add new");
		btnAddNew.setName("btnAdd");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 1;
		gbc_btnAdd.anchor = GridBagConstraints.WEST;
		panel.add(btnAddNew, gbc_btnAdd);
		
		JButton btnEditSelected = new JButton("Edit Selected");
		btnEditSelected.setName("btnEdit");
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(0, 0, 5, 0);
		gbc_btnEdit.gridx = 6;
		gbc_btnEdit.gridy = 1;
		gbc_btnEdit.anchor = GridBagConstraints.EAST;
		panel.add(btnEditSelected, gbc_btnEdit);
		
		JButton btnBack = new JButton("Back");
		btnBack.setName("btnBack");
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 0;
		gbc_btnBack.gridy = 6;
		gbc_btnBack.anchor = GridBagConstraints.WEST;
		gbc_btnBack.weighty = 3;
		panel.add(btnBack, gbc_btnBack);
		
		JButton btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.setName("btnDelete");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.gridx = 6;
		gbc_btnDelete.gridy = 6;
		gbc_btnDelete.anchor = GridBagConstraints.EAST;
		gbc_btnDelete.weighty = 3;
		panel.add(btnDeleteSelected, gbc_btnDelete);
		
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
