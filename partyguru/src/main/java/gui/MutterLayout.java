package gui;

import java.awt.BorderLayout;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	
	private Database db;
		
	/**
	 * Konstruktor von MutterLayout. Initialisiert die verschiedenen Views des Programms.
	 */
	public MutterLayout()
	{		
		this.setLayout(new BorderLayout());
		//TODO Verschiedene Seiten einfügen
		
		//!!!only temporary!!!
		String path = JOptionPane.showInputDialog("Bitte Pfad zur Datenbank eingeben", System.getProperty("user.home")+"/party");
		File f = new File(path+".h2.db");
		if(f.exists())
		{
			try {
				db = new Database(path);
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
		} else 
		{
			JOptionPane.showMessageDialog(this, "Datenbank nicht gefunden.");
			System.exit(1);
		}
		
	}

}
