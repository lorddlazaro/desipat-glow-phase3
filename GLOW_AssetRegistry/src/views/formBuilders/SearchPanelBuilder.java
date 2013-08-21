package views.formBuilders;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.toedter.calendar.JDateChooser;

public class SearchPanelBuilder implements PanelBuilder{

	JPanel panel;
	GridBagConstraints gbc = new GridBagConstraints();
	
	@Override
	public void construct() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel = new JPanel(gridBagLayout);
	}

	@Override
	public void setTitle() {
		JLabel lblViewAssets = new JLabel("View Assets");
		lblViewAssets.setFont(new Font("Verdana", Font.BOLD, 18));
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(lblViewAssets, gbc);
		
	}

	@Override
	public void setFields() {
		
		/*ImageIcon bg = new ImageIcon(ViewAsset.class.getResource("/resources/B3.png"));
		
		JLabel background = new JLabel();
		background.setBounds(0, 0, 512, 440);
		panel.add(background);
		background.setIcon(new ImageIcon(bg.getImage().getScaledInstance(background.getWidth(), background.getHeight(), Image.SCALE_SMOOTH)));
		*/
		
		JLabel lblKeyword = new JLabel("Keyword:");
		lblKeyword.setFont(new Font("Tahoma", Font.BOLD, 12));
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = 0;
		gbc.gridy = 1;
		panel.add(lblKeyword, gbc);
		
		JLabel lblDateRange = new JLabel("Date Range:");
		lblDateRange.setFont(new Font("Tahoma", Font.BOLD, 12));
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		panel.add(lblDateRange, gbc);	
		
		JTextField searchBox = new JTextField();
		gbc.gridwidth = 4;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		searchBox.setColumns(10);
		searchBox.setName("Search Field");
		panel.add(searchBox, gbc);
	
		Calendar cal = Calendar.getInstance();
		
		JDateChooser dateFrom = new JDateChooser();
		dateFrom.setDate(cal.getTime());
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		dateFrom.setName("Date From");
		panel.add(dateFrom, gbc);
		
		JDateChooser dateTo = new JDateChooser();
		dateTo.setDate(cal.getTime());
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 3;
		gbc.gridy = 2;
		dateTo.setName("Date To");
		panel.add(dateTo, gbc);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 2;
		gbc.gridy = 2;
		panel.add(lblTo, gbc);
		
		String[] dateTypeArray = {"Date Acquired","Retention Period","None"};
		JComboBox dateTypeComboBox = new JComboBox(dateTypeArray);
		dateTypeComboBox.setSelectedIndex(0);
		dateTypeComboBox.setName("Date Type Combo Box");
		gbc.gridwidth = 2;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 5;
		gbc.gridy = 2;
		panel.add(dateTypeComboBox, gbc);
		
		//JTable
		gbc.fill = GridBagConstraints.NONE;
		JTable table = new JTable();
		table.setFillsViewportHeight(true);
		table.getTableHeader().setReorderingAllowed(false); //disable dragging of columns
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setName("Table");
		
		//ScrollPane for table
		JScrollPane scrollPane = new JScrollPane(table);
		gbc.gridwidth = 7;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		scrollPane.setName("Scroll Pane");
		scrollPane.setPreferredSize(new Dimension(480,350));
		panel.add(scrollPane, gbc);
		panel.add(table,gbc);
		
		
		
		
	}

	@Override
	public void setButtons() {
		
		ImageIcon searchIcon = new ImageIcon(SearchPanelBuilder.class.getResource("/resources/blue.PNG"));
		ImageIcon edit = new ImageIcon(SearchPanelBuilder.class.getResource("/resources/edit.PNG"));
		ImageIcon refresh = new ImageIcon(SearchPanelBuilder.class.getResource("/resources/refresh.PNG"));
		ImageIcon assethistory = new ImageIcon(SearchPanelBuilder.class.getResource("/resources/assethistory.PNG"));
		
		JButton btnSearch = new JButton("");
		btnSearch = new JButton(searchIcon);
		btnSearch.setName("btnSearch");
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 6;
		gbc.gridy = 1;
		btnSearch.setPreferredSize(new Dimension(102, 23));
		panel.add(btnSearch, gbc);
	//	btnSearch.setIcon(new ImageIcon(searchIcon.getImage().getScaledInstance(btnSearch.getWidth(), btnSearch.getHeight(), Image.SCALE_SMOOTH)));
	   
		
	    JButton btnRefresh = new JButton(refresh);
	//	btnRefresh.setIcon(new ImageIcon(refresh.getImage().getScaledInstance(btnRefresh.getWidth(), btnRefresh.getHeight(), Image.SCALE_SMOOTH)));
	
		JButton btnEdit = new JButton(edit);
		btnEdit.setName("btnEdit");
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 0;
		gbc.gridy = 5;
		btnEdit.setPreferredSize(new Dimension(125,36));
		panel.add(btnEdit, gbc);
		
		JButton btnViewAssetHistory = new JButton(assethistory);
		btnViewAssetHistory.setName("btnAssetHistory");
		gbc.weightx = 0;
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 2;
		gbc.gridy = 5;
		btnViewAssetHistory.setPreferredSize(new Dimension(125,36));
		panel.add(btnViewAssetHistory, gbc);
		
		btnRefresh.setName("btnRefresh");
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.anchor = GridBagConstraints.SOUTHEAST;
		gbc.gridx = 6;
		gbc.gridy = 5;
		btnRefresh.setPreferredSize(new Dimension(125,36));
		panel.add(btnRefresh, gbc);
		
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
