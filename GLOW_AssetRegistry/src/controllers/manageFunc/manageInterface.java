package controllers.manageFunc;

import java.util.ArrayList;

import models.Reference;

public interface manageInterface {

		public ArrayList<Reference> getListFromDB();
		public void addToDB(String input);
		public void editToDB(Reference oldReference, Reference newReference);
	
}
