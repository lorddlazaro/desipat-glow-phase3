// Author ( Sharmaine Lim )

package database.builders.references.rewriteable;

import models.Reference;
import database.builders.CustomStatementBuilder;
import database.builders.Update;
import database.builders.UpdateDirector;

public class RewriteableReferenceUpdateStatementBuilder extends CustomStatementBuilder {
	
	private Reference reference;
	
	public RewriteableReferenceUpdateStatementBuilder() {
		super();
		reference = null;
	}
	
	@Override
	public void setStatement() {
		UpdateDirector updateDirector = new UpdateDirector();
		RewriteableReferenceUpdateBuilder updateBuilder = new RewriteableReferenceUpdateBuilder();
		updateDirector.setBuilder(updateBuilder);
		updateDirector.constructUpdate();
		Update update = updateDirector.getUpdate();
		customStatement.setStatement(update.toString());
	}
	
	@Override
	public void populateValues() {
		if (reference.getIdentifier() > 0 &&
			!(reference.getValue() == null || reference.getValue().isEmpty())) {
			customStatement.addValue("Value", reference.getValue());
			customStatement.addValue("Hidden", reference.isHidden());
			customStatement.addValue("ID", reference.getIdentifier());
		}
	}
	
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	
}
