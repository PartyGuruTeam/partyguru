package gui.kontakte;

import java.awt.Window;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

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
		super(db.executeQuery("SELECT * FROM PERSONEN"), new Boolean[]{ });
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
	public void updateRow(int row, DefaultTableModel modell) {
		try {
			mDB.executeUpdate("UPDATE PERSONEN SET "
					+ "NAME='"+modell.getValueAt(row, 1)+"', "
					+ "GESCHLECHT='"+modell.getValueAt(row, 2)+"', "
					+ "EMAIL='"+modell.getValueAt(row, 3)+"', "
					+ "HANDY='"+modell.getValueAt(row, 4)+"' "			
					+ "WHERE PERSID="+modell.getValueAt(row, 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
