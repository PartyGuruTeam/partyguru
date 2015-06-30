package gui;

import java.awt.BorderLayout;
import java.io.File;
import java.sql.SQLException;

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
	int mPID;
		
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
			SelectParty selection=null;
			try {
				selection = new SelectParty(db, this);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			mPID = -1;
			while(mPID==-1)
			{
				if(!selection.isVisible())
					System.exit(1); //TODO anders lösen
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//JOptionPane.showMessageDialog(this, "PID: "+mPID);
		} else 
		{
			JOptionPane.showMessageDialog(this, "Datenbank nicht gefunden.");
			System.exit(1);
		}
		
	}

}
