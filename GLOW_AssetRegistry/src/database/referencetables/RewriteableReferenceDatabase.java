// Author ( Sharmaine Lim )

package database.referencetables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Reference;

import database.abstracts.RewriteableDatabase;
import database.builders.CustomStatementDirector;
import database.builders.references.rewriteable.RewriteableReferenceCategoryInsertStatementBuilder;
import database.builders.references.rewriteable.RewriteableReferenceInsertStatementBuilder;
import database.builders.references.rewriteable.RewriteableReferenceQueryStatementBuilder;
import database.builders.references.rewriteable.RewriteableReferenceUpdateStatementBuilder;

public class RewriteableReferenceDatabase extends RewriteableDatabase {
	
	private String table;
	private Reference reference;
	
	public RewriteableReferenceDatabase() {
		table = null;
		reference = null;
	}
	
	@Override
	public ArrayList<Object> select() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		RewriteableReferenceQueryStatementBuilder builder = new RewriteableReferenceQueryStatementBuilder();
		builder.setConnection(conn);
		builder.setTable(table);
		
		director.setBuilder(builder);
		director.constructCustomStatement();
		
		ArrayList<Object> references = new ArrayList<Object>();
		
		PreparedStatement ps = director.getCustomStatement().prepareGivenStatement();
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Reference currentReference = new Reference();
			currentReference.setIdentifier(rs.getInt("ID"));
			currentReference.setValue(rs.getString("Value"));
			currentReference.setHidden(rs.getBoolean("Hidden"));
			references.add(currentReference);
		}
		
		rs.close();
		ps.close();
		
		return references;
	}
	
	@Override
	public int insert() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		RewriteableReferenceInsertStatementBuilder builder = new RewriteableReferenceInsertStatementBuilder();
		if ( !(reference.getValue() == null || reference.getValue().isEmpty()) ) {
			builder.setValue(reference.getValue());
		}
		builder.setConnection(conn);
		director.setBuilder(builder);
		director.constructCustomStatement();
		
		int resultingID = 0;
		
		PreparedStatement ps = director.getCustomStatement().prepareGivenStatement();
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			resultingID = rs.getInt(1);
		}
		rs.close();
		ps.close();
		
		RewriteableReferenceCategoryInsertStatementBuilder categoryBuilder = new RewriteableReferenceCategoryInsertStatementBuilder();
		categoryBuilder.setConnection(conn);
		categoryBuilder.setTable(table);
		categoryBuilder.setID(resultingID);
		director.setBuilder(categoryBuilder);
		director.constructCustomStatement();
		
		ps = director.getCustomStatement().prepareGivenStatement();
		ps.executeUpdate();
		ps.close();
		
		return resultingID;
	}
	
	@Override
	public void update() throws SQLException {
		CustomStatementDirector director = new CustomStatementDirector();
		RewriteableReferenceUpdateStatementBuilder builder = new RewriteableReferenceUpdateStatementBuilder();
		if ( reference != null ) {
			builder.setReference(reference);
		}
		
		builder.setConnection(conn);
		director.setBuilder(builder);
		director.constructCustomStatement();
		
		PreparedStatement ps = director.getCustomStatement().prepareGivenStatement();
		ps.executeUpdate();
		ps.close();
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public void setReference(Reference reference) {
		this.reference = reference;
	}
	
}
