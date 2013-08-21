/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.EventQueue;

import javax.swing.JPanel;

import views.MainFrame;
import views.formBuilders.MainPanelBuilder;
import views.formBuilders.PanelBuilder;
import views.formBuilders.PanelBuilderDirector;




/**
 *
 * @author Matthew
 */
public class MainController {

    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelBuilder viewBuilder;
					viewBuilder = new MainPanelBuilder();
					PanelBuilderDirector viewBuilderDirector = new PanelBuilderDirector(viewBuilder);
					JPanel panel = viewBuilderDirector.makePanel();
					MainFrame mainFrame= new MainFrame(panel);
					mainFrame.setVisible(true);
					new MainViewController(panel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
}
