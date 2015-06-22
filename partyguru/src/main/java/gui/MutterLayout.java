package gui;

import java.sql.SQLException;

import javax.swing.JPanel;

import db.Database;

/**
 * 
 * @author Bastian
 *
 */
public class MutterLayout extends JPanel 
{
	private static final long serialVersionUID = 1L;
	
	Database db;
	
	/**
	 * Konstruktor von MutterLayout. Initialisiert die verschiedenen Views des Programms.
	 */
	public MutterLayout()
	{
		//TODO Verschiedene Seiten einfügen
		//TODO Tabs einfügen
		
		//!!!only temporary!!!
		try {
			db = new Database("~/party");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//!!!only temporary!!!
		TabellenLayoutImpl layout=null;
		try {
			layout = new TabellenLayoutImpl(db, this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.add(layout);
	}

}
