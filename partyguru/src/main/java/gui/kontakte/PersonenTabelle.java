package gui.kontakte;

import java.awt.Window;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.SwingUtilities;

import gui.TabellenLayout;
import db.Database;
import gui.MutterLayout;
import gui.formdialog.FormDialog;
import gui.formdialog.FormElement;

public class PersonenTabelle extends TabellenLayout {

	private static final long serialVersionUID = 1L;
	
	Database mDB;
	MutterLayout mParent;
	
	public PersonenTabelle(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT * FROM PERSONEN"), new Boolean[]{
			false, true, false, true, true, true, false
		});
		mDB = db;
		mParent = parent;
	}

	@Override
	public void printTable() {
		try {
			printTable(mDB.executeQuery("SELECT * FROM PERSONEN"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(Vector<String> v) {
		try {
			mDB.executeUpdate("DELETE FROM PERSONEN WHERE PERSID="+v.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow() {
		final Window w = SwingUtilities.getWindowAncestor(this);
		new Thread(new Runnable(){

			public void run() {
				Vector<String> v = new Vector<String>();
				v.add("m");
				v.add("w");
				Vector<String> result = FormDialog.getDialog("Neue Person anlegen", new FormElement[] {
						new FormElement("Name", FormElement.TEXT_FIELD),
						new FormElement("Geschlecht", FormElement.DROP_DOWN, v),
						new FormElement("Email", FormElement.TEXT_FIELD),
						new FormElement("Handy", FormElement.TEXT_FIELD),
				}, w);
				if(result.size()==4)
				{
					try {
						mDB.executeUpdate("INSERT INTO PERSONEN (NAME, GESCHLECHT, EMAIL, HANDY) VALUES ('"+result.elementAt(0)+
								"', '"+result.elementAt(1)+"', '"+result.elementAt(2)+"', '"+result.elementAt(3)+"')");
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
			mDB.executeUpdate("UPDATE PERSONEN SET "
					+ "NAME='"+row.elementAt(1)+"', "
					+ "GESCHLECHT='"+row.elementAt(2)+"', "
					+ "EMAIL='"+row.elementAt(3)+"', "
					+ "HANDY='"+row.elementAt(4)+"', "
					+ "GEBURTSJAHR='"+row.elementAt(5)+"',"
					+ "VERFUEGBARKEIT'"+row.elementAt(6)+"' "
					+ "WHERE PERSID="+row.elementAt(0));
			//TODO Geburtsjahr + Verf�gbarkeit pr�fen Darstellung
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
