package controllers.manageFunc;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import models.Reference;

import database.Database;

public class manageType implements manageInterface {

	@Override
	public void addToDB(String input) {
		try {
			new Database().insertType(input);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Error Occured in adding, Form is going to exit!");
		}
	}

	@Override
	public void editToDB(Reference oldReference, Reference newReference) {
		try {
			new Database().updateType(oldReference, newReference);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Failed to Edit, We don't know why!");
		}
	}

	@Override
	public ArrayList<Reference> getListFromDB() {
		try {
			return new Database().getAllType();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
