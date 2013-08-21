package views.formBuilders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class MainPanelBuilder implements PanelBuilder{

	JPanel panel;
	
	@Override
	public void construct() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel = new JPanel(gridBagLayout);
	}

	@Override
	public void setTitle() {
		JLabel title = new JLabel("Asset Registry System");
		title.setForeground(Color.BLACK);
		title.setFont(new Font("Forte", Font.PLAIN, 19));
		title.setName("Title");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		//gbc.weighty = 1;
		panel.add(title, gbc);
	}

	@Override
	public void setFields() {
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		ImageIcon mainBackground = new ImageIcon(MainPanelBuilder.class.getResource("/resources/B3.png"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setName("ScrollPane");
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		gbc_scrollPane.gridwidth = 10;
		gbc_scrollPane.gridheight = 9;
		gbc_scrollPane.weightx = 1;
		panel.add(scrollPane, gbc_scrollPane);
		
		/*JLabel lblBackgroundImage = new JLabel("");
		lblBackgroundImage.setBounds(0, 0, 669, 462);
		lblBackgroundImage.setIcon(mainBackground);
		panel.add(lblBackgroundImage);
		*/
	}

	@Override
	public void setButtons() {
		ImageIcon add = new ImageIcon(MainPanelBuilder.class.getResource("/resources/add.png"));
		ImageIcon search = new ImageIcon(MainPanelBuilder.class.getResource("/resources/search.png"));
		ImageIcon actionhistory = new ImageIcon(MainPanelBuilder.class.getResource("/resources/actionhistory.png"));
		ImageIcon manage = new ImageIcon(MainPanelBuilder.class.getResource("/resources/manage.png"));
		
		JButton btnAdd = new JButton(add);
		btnAdd.setIcon(add);
		btnAdd.setName("btnAdd");
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.weighty = 1;
		gbc_btnAdd.anchor = GridBagConstraints.SOUTH;
		gbc_btnAdd.gridx = 0;
		gbc_btnAdd.gridy = 5;
		btnAdd.setPreferredSize(new Dimension(125,36));
		panel.add(btnAdd, gbc_btnAdd);
		
		
		JButton btnSearch = new JButton(search);
		btnSearch.setIcon(search);
		btnSearch.setName("btnSearch");
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(0, 0, 5, 5);
		gbc_btnSearch.gridx = 0;
		gbc_btnSearch.gridy = 6;
		btnSearch.setPreferredSize(new Dimension(125,36));
		panel.add(btnSearch, gbc_btnSearch);
		
		
		JButton btnActionHistory = new JButton(actionhistory);
		btnActionHistory.setIcon(actionhistory);
		btnActionHistory.setName("btnActHistory");
		GridBagConstraints gbc_btnAction = new GridBagConstraints();
		gbc_btnAction.insets = new Insets(0, 0, 5, 5);
		gbc_btnAction.gridx = 0;
		gbc_btnAction.gridy = 7;
		btnActionHistory.setPreferredSize(new Dimension(125,36));
		panel.add(btnActionHistory, gbc_btnAction);
		
		
		JButton btnManage = new JButton(manage);
		btnManage.setIcon(manage);
		btnManage.setName("btnManage");
		GridBagConstraints gbc_btnManage = new GridBagConstraints();
		gbc_btnManage.insets = new Insets(0, 0, 5, 5);
		gbc_btnManage.gridx = 0;
		gbc_btnManage.gridy = 8;
		gbc_btnManage.weighty = 1;
		gbc_btnManage.anchor = GridBagConstraints.NORTH;
		btnManage.setPreferredSize(new Dimension(125,36));
		panel.add(btnManage, gbc_btnManage);
		
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
