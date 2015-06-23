package gui;

import java.awt.BorderLayout;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
		this.setLayout(new BorderLayout());
		//TODO Verschiedene Seiten einfügen
		
		//!!!only temporary!!!
		try {
			db = new Database("~/party");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP);
		this.add(tp, BorderLayout.CENTER);
		
		//!!!only temporary!!!
		TabellenLayoutImpl layout=null;
		try {
			layout = new TabellenLayoutImpl(db, this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tp.addTab("TabellenLayout", layout);
	}

}
