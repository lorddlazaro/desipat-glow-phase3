package views;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AssetHistoryFrame extends JFrame{

	public AssetHistoryFrame(JPanel panel){
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(450,300);
		this.setLocationRelativeTo(null);
		setContentPane(panel);
	}
}
