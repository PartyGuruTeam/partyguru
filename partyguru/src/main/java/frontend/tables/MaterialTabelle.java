package frontend.tables;

import java.awt.Window;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.SwingUtilities;

import backend.Database;
import frontend.MutterLayout;
import frontend.formdialog.FormDialog;
import frontend.formdialog.FormElement;

/**
 * Kommunikation mit der DB für die Datensätze der Materialien.
 * @author PartyGuru
 *
 */
public class MaterialTabelle extends TabellenLayout {

	private static final long serialVersionUID = 1L;
	
	Database mDB;
	MutterLayout mParent;
	
	public MaterialTabelle(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT * FROM MaterialTemplate"), 
				new Boolean[]{false, false, true});
		mDB = db;
		mParent = parent;
	}

	@Override
	public void printTable() {
		try {
			printTable(mDB.executeQuery("SELECT * FROM MaterialTemplate"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(Vector<String> v) {
		try {
			mDB.executeUpdate("DELETE FROM MaterialTemplate WHERE MTID="+v.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow() {
		final Window w = SwingUtilities.getWindowAncestor(this);
		new Thread(new Runnable() {
			
			public void run() {
				Vector<String> v = new Vector<String>();
				v.add("Essen");
				v.add("Getränke");
				v.add("Partyutensilien");
				
				Vector<String> result = FormDialog.getDialog("Neues Material anlegen", new FormElement[] {
						new FormElement("Name", FormElement.TEXT_FIELD),
						new FormElement("Art", FormElement.DROP_DOWN, v)						
				}, w);
				if(result.size()==2)
				{
					try {
						mDB.executeUpdate("INSERT INTO MaterialTemplate (MATERIALNAME, Art) VALUES ('"+result.elementAt(0)+
								"', '"+result.elementAt(1)+"')");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				printTable();
			}
			
		}).start();		
	}

	@Override
	public void updateRow(Vector<String> row) {
		try {
			mDB.executeUpdate("UPDATE MaterialTemplate SET "
					+ "ART='"+row.elementAt(1)+"', "
					+ "MATERIALNAME='"+row.elementAt(2)+"' "
					+ "WHERE MTID="+row.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
