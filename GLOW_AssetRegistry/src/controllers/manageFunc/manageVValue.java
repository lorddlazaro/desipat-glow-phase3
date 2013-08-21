package controllers.manageFunc;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import models.Reference;

import database.Database;

public class manageVValue implements manageInterface{

	@Override
	public void addToDB(String input) {
		try {
			new Database().insertValueLevel(input);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Error Occured in adding, Form is going to exit!");
		}
	}

	@Override
	public void editToDB(Reference oldReference, Reference newReference) {
		try {
			new Database().updateValueLevel(oldReference, newReference);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Failed to Edit, We don't know why!");
		}
	}

	@Override
	public ArrayList<Reference> getListFromDB() {
		try {
			return new Database().getAllValueLevel();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
