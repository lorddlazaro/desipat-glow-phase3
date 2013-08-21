package views.formBuilders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

public class ActionHistoryPanelBuilder implements PanelBuilder{

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
		JLabel lblActionHistory = new JLabel("Action History");
		lblActionHistory.setName("Title");		
		lblActionHistory.setFont(new Font("Verdana", Font.BOLD, 18));
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(lblActionHistory, gbc);
	}

	@Override
	public void setFields() {
		//panel.setBackground(new Color(153, 255, 204));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
		scrollPane.setName("Scroll Pane");
		gbc.gridwidth = 7;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		panel.add(scrollPane, gbc);
		
		JTable table = new JTable();
            String col[] = { "Date", "Action"};
            String[][] data = {}; //Change to array list to display all logs
    		DefaultTableModel model = new DefaultTableModel(data,col){
                public boolean isCellEditable(int row, int col) {
                        return false;
                         }
                    };
    		table.setModel(model);
           
    	table.setName("Table");
    	scrollPane.setPreferredSize(new Dimension(480,400));
		scrollPane.setColumnHeaderView(table);
		scrollPane.setViewportView(table);
		
		
		JLabel lblFrom = new JLabel("From:");
		lblFrom.setFont(new Font("Tahoma", Font.BOLD, 12));
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		panel.add(lblFrom, gbc);
		
		JDateChooser dtcFrom = new JDateChooser();
		dtcFrom.setName("Date From");
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		dtcFrom.setName("Date From");
		panel.add(dtcFrom, gbc);
		
		JLabel lblTo = new JLabel("To:");
		lblTo.setFont(new Font("Verdana", Font.PLAIN, 14));
		lblTo.setHorizontalAlignment(SwingConstants.CENTER);
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 2;
		gbc.gridy = 2;
		panel.add(lblTo, gbc);
		
		JDateChooser dtcTo = new JDateChooser();
		dtcTo.setName("Date To");
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 3;
		gbc.gridy = 2;
		dtcTo.setName("Date To");
		panel.add(dtcTo, gbc);
		
		Calendar cal = Calendar.getInstance();
		dtcTo.setDate(cal.getTime());
		dtcFrom.setDate(cal.getTime());
		
		
	}

	@Override
	public void setButtons() {
		ImageIcon searchIcon = new ImageIcon(ActionHistoryPanelBuilder.class.getResource("/resources/blue.PNG"));
		
		JButton btnSearch = new JButton(searchIcon);
		btnSearch.setName("btnSearch");
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 6;
		gbc.gridy = 2;
		btnSearch.setPreferredSize(new Dimension(102, 23));
		panel.add(btnSearch, gbc);
		
	}

	@Override
	public JPanel getPanel() {
		return panel;
	}

}
