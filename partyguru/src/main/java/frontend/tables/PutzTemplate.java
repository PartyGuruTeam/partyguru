package frontend.tables;

import java.awt.Window;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.SwingUtilities;

import backend.Database;
import frontend.MutterLayout;
import frontend.formdialog.FormDialog;
import frontend.formdialog.FormElement;

public class PutzTemplate extends TabellenLayout {
	
private static final long serialVersionUID = 1L;
	
	Database mDB;
	MutterLayout mParent;
	
	/**
	 * Der Konstruktor ruft die Eintr�ge aus der Relation Putztemplate der DB ab.
	 * @param db
	 * @param parent
	 * @throws SQLException
	 */
	public PutzTemplate(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT PTID, ART FROM PUTZTEMPLATE"), 
				new Boolean[] { false, true });
		mDB = db;
		mParent = parent;
	}


	/**
	 * printTable
	 * Aktualisiert die Tabellenansicht.
	 */
	@Override
	public void printTable() {
		try {
			printTable(mDB.executeQuery("SELECT PTID, ART FROM PUTZTEMPLATE"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * deleteRow
	 * L�scht die angegebene Zeile aus der Datenbank.
	 */
	@Override
	public void deleteRow(Vector<String> v) {
		try {
			mDB.executeUpdate("DELETE FROM PUTZTEMPLATE WHERE PTID="+v.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * addRow
	 * F�gt eine Zeile zur Datenbank hinzu und fordert den Nutzer zur Eingabe
	 * der Feldinhalte auf. Die eingegebenen Werte werden der DB hinzugef�gt.
	 */
	@Override
	public void addRow() {
		final Window w = SwingUtilities.getWindowAncestor(this);
		new Thread(new Runnable(){

			public void run() {
				Vector<String> result = FormDialog.getDialog("Neue Putzt�tigkeit anlegen", new FormElement[] {
						new FormElement("T�tigkeit", FormElement.TEXT_FIELD)			
				}, w);
				if(result.size()==1)
				{
					try {
						mDB.executeUpdate("INSERT INTO PUTZTEMPLATE (ART) VALUES ('"+result.elementAt(0)+"')");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				printTable();
			}
			
		}).start();		
	}

	/**
	 * updateRow
	 * F�gt eine Zeile zur Datenbank hinzu und fordert den Nutzer zur Eingabe
	 * der Feldinhalte auf. Die eingegebenen Werte werden der DB hinzugef�gt.
	 */
	@Override
	public void updateRow(Vector<String> row) {
		try {
			mDB.executeUpdate("UPDATE PUTZTEMPLATE SET "
					+ "ART='"+row.elementAt(1)+"' "
					+ "WHERE PTID="+row.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}


