package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class MainFrame extends JFrame{
	
	public MainFrame(JPanel panel){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 750, 575);
		setContentPane(panel);
		setTitle("Asset Registry System");
		
		try{ 
			   UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch(Exception e){
			 e.printStackTrace();
		}
	}
	
}
