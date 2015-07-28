package frontend.tables;

import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import backend.Database;
import frontend.MutterLayout;
import frontend.formdialog.FormDialog;
import frontend.formdialog.FormElement;

/**
 * Kommunikation mit der DB für die Datensätze der Gästeliste.
 * @author PartyGuru
 *
 */
public class Gaesteliste extends TabellenLayout 
{	
	private static final long serialVersionUID = 1L;

	private Database mDB;
	private MutterLayout mParent;
	int mpid;

	public Gaesteliste(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT G.PERSID, P.NAME, G.KOMMT FROM GAESTELISTE G, "
				+ "PERSONEN P WHERE G.PERSID=P.PERSID AND G.PID="+parent.getPID()),
				new Boolean[]{ false, false, true });
		mDB = db;
		mParent = parent;
		mpid = parent.getPID();
	}


	@Override
	public void printTable() {
		try {
			printTable(mDB.executeQuery("SELECT G.PERSID, P.NAME, G.KOMMT FROM GAESTELISTE G, "
				+ "PERSONEN P WHERE G.PERSID=P.PERSID AND G.PID="+mParent.getPID()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(Vector<String> v) {
		try {
			mDB.executeUpdate("DELETE FROM GAESTELISTE WHERE PID="+mParent.getPID()+"AND "
					+ "PERSID="+v.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow() 
	{
		final Window w = SwingUtilities.getWindowAncestor(this);
		try {
			ResultSet rs = mDB.executeQuery("SELECT PERSID, NAME FROM PERSONEN");
			final Vector<String> elements = new Vector<String>();
			while(rs.next())
			{
				elements.add(rs.getString(1)+"-"+rs.getString(2));
			}
			Vector<String> v = new Vector<String>();
			v.add("true");
			v.add("false");
			new Thread(new Runnable(){

				public void run() {
					Vector<String> result = FormDialog.getDialog("Neuen Gast hinzufügen", new FormElement[] {
							new FormElement("Person", FormElement.DROP_DOWN, elements),
							new FormElement("Kommt", FormElement.DROP_DOWN, v)
					}, w);
					if(result.size()==2)
					{
						String persid = result.elementAt(0).split("-")[0];
						String kommt = result.elementAt(1);
						try {
							mDB.executeUpdate("INSERT INTO GAESTELISTE (PID, PERSID, KOMMT) "
									+ "VALUES ('"+mParent.getPID()+"', '"+persid+"', '"+kommt+"')");
						} catch (SQLException e) {
							JOptionPane.showMessageDialog(mParent, "Gast ist bereits auf der Gästeliste!");
						}
					}
					printTable();
				}

			}).start();	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void updateRow(Vector<String> row) {
		try {
			mDB.executeUpdate("UPDATE GAESTELISTE SET"
					+ " KOMMT="+row.elementAt(2)
					+ " WHERE PID="+mParent.getPID()
					+ " AND PERSID="+row.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
