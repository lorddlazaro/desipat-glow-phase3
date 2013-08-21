package views.formBuilders;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AssetHistoryPanelBuilder implements PanelBuilder{

	JPanel panel;
	
	@Override
	public void construct() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{64, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{14, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel = new JPanel(gridBagLayout);
	}

	@Override
	public void setTitle() {
		JLabel lblViewAssetHistory = new JLabel("View Asset History");
		lblViewAssetHistory.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblAssetHistory = new GridBagConstraints();
		gbc_lblAssetHistory.anchor = GridBagConstraints.WEST;
		gbc_lblAssetHistory.insets = new Insets(0, 0, 5, 5);
		gbc_lblAssetHistory.gridx = 0;
		gbc_lblAssetHistory.gridy = 0;
		panel.add(lblViewAssetHistory, gbc_lblAssetHistory);
	}

	@Override
	public void setFields() {
		//panel.setBackground(Color.CYAN);
		
		JTable historyTable = new JTable();
		historyTable.setName("Table");
		historyTable.getTableHeader().setReorderingAllowed(false);
		//historyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		//scrollPane.setViewportView(historyTable);

		JScrollPane scrollPane = new JScrollPane(historyTable);
		scrollPane.setName("Scroll Pane");	
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		gbc_scrollPane.weightx = 1;
		gbc_scrollPane.weighty = 1;
		panel.add(scrollPane, gbc_scrollPane);
		panel.add(historyTable,gbc_scrollPane);
		
		//ImageIcon bg = new ImageIcon(ViewAsset.class.getResource("/resources/B3.png"));
		
		// background picture
		//JLabel background = new JLabel();
		//background.setBounds(0, 0, 512, 440);
		//panel.add(background);
		//background.setIcon(new ImageIcon(bg.getImage().getScaledInstance(background.getWidth(), background.getHeight(), Image.SCALE_SMOOTH)));
	}

	@Override
	public void setButtons() {
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
