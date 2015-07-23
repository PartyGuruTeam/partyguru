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
		super(db.executeQuery("SELECT * FROM PERSONEN"));
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
	public void deleteRow(int id) {
		try {
			mDB.executeUpdate("DELETE FROM PARTY WHERE PERSID="+id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow() {
		final Window w = SwingUtilities.getWindowAncestor(this);
		new Thread(new Runnable(){

			public void run() {
				//Dropdown-Optionen f�r das Geschlecht
				Vector<String> v = new Vector<String>();
				v.add("m");
				v.add("w");
				
				//Dropdown-Optionen f�r die Verf�gbarkeit
				Vector<String> z = new Vector<String>();
				z.add("nicht verf�gbar"); // = 1 in DB
				z.add("verf�gbar f�r w"); // = 2 in DB
				z.add("verf�gbar f�r m"); // = 3 in DB
				z.add("nicht bekannt");	  // = 0 in DB
				
				int mVerf = 0; 
				
				Vector<String> result = FormDialog.getDialog("Neue Person anlegen", new FormElement[] {
						new FormElement("Name", FormElement.TEXT_FIELD),
						new FormElement("Geschlecht", FormElement.DROP_DOWN, v),
						new FormElement("Email", FormElement.TEXT_FIELD),
						new FormElement("Handy", FormElement.TEXT_FIELD),
						new FormElement("Verf�gbarkeit", FormElement.DROP_DOWN, z),
	
				}, w);
				//TODO verbessern
				if(result.size()==5)
				{
					
					try {
						
					/**
					 * @author Franzi
					 * mVerf �bersetzt die Verf�gbarkeit in eine Zahl f�r die DB
						1 --> nicht verf�gbar
						2 --> verf�gbar f�r w
						3 --> verf�gbar f�r m
						0 --> wurde nicht eingetragen
					*/
					
					
					if(result.elementAt(4) == "nicht verf�gbar")
					{
						mVerf = 1;
					}
					else if (result.elementAt(4) == "verf�gbar f�r w")
					{
						mVerf = 2;
					}
					else if (result.elementAt(4) == "verf�gbar f�r m")
					{
						mVerf = 3;
					}
					else
						mVerf = 0;
					
					
						mDB.executeUpdate("INSERT INTO PERSONEN (NAME, GESCHLECHT, EMAIL, HANDY, VERFUEGBARKEIT) VALUES ('"+result.elementAt(0)+
								"', '"+result.elementAt(1)+"', '"+result.elementAt(2)+"', '"+result.elementAt(3)+"', '" +mVerf+ "')");
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
					+ "HANDY='"+modell.getValueAt(row, 4)+"', "
					+ "VERFUEGBARKEIT = '" + modell.getValueAt(row, 6) + "' "
					+ "WHERE PERSID="+modell.getValueAt(row, 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
