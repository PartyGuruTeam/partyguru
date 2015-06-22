package gui;

import java.sql.SQLException;

import javax.swing.JPanel;

import db.Database;

public class MutterLayout extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	Database db;
	
	public MutterLayout() throws SQLException
	{
		db=null;
		try {
			db = new Database("~/party");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TabellenLayoutImpl layout=new TabellenLayoutImpl(db, this);
		this.add(layout);
	}
	
	public Database getDB()
	{
		return db;
	}

}
