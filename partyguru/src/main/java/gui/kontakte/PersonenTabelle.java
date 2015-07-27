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
				//Dropdown-Optionen für das Geschlecht
				Vector<String> v = new Vector<String>();
				v.add("m");
				v.add("w");
				
				//Dropdown-Optionen für die Verfügbarkeit
				Vector<String> z = new Vector<String>();
				z.add("nicht verfügbar"); // = 1 in DB
				z.add("verfügbar für w"); // = 2 in DB
				z.add("verfügbar für m"); // = 3 in DB
				z.add("nicht bekannt");	  // = 0 in DB
				
				int mVerf = 0; 
				
				Vector<String> result = FormDialog.getDialog("Neue Person anlegen", new FormElement[] {
						new FormElement("Name", FormElement.TEXT_FIELD),
						new FormElement("Geschlecht", FormElement.DROP_DOWN, v),
						new FormElement("Email", FormElement.TEXT_FIELD),
						new FormElement("Handy", FormElement.TEXT_FIELD),
						new FormElement("Geburtsjahr", FormElement.TEXT_FIELD)
						new FormElement("Verfügbarkeit", FormElement.DROP_DOWN, z),
	
				}, w);
				if(result.size()==6)
				{
					try {
						int geb = -1;
						try
						{
							geb = Integer.parseInt(result.elementAt(4));
						} catch(NumberFormatException e1)
						{
						}
						
					/**
					 * @author Franzi
					 * mVerf übersetzt die Verfügbarkeit in eine Zahl für die DB
						1 --> nicht verfügbar
						2 --> verfügbar für w
						3 --> verfügbar für m
						0 --> wurde nicht eingetragen
					*/
					
					
					if(result.elementAt(4) == "nicht verfügbar")
					{
						mVerf = 1;
					}
					else if (result.elementAt(4) == "verfügbar für w")
					{
						mVerf = 2;
					}
					else if (result.elementAt(4) == "verfügbar für m")
					{
						mVerf = 3;
					}
					else
						mVerf = 0;
					
					

						mDB.executeUpdate("INSERT INTO PERSONEN (NAME, GESCHLECHT, EMAIL, HANDY, GEBURTSJAHR, VERFUEGBARKEIT) VALUES ('"+result.elementAt(0)+
								"', '"+result.elementAt(1)+"', '"+result.elementAt(2)+"', '"+result.elementAt(3)+"', "+geb+"', '" +mVerf+ "')");
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
