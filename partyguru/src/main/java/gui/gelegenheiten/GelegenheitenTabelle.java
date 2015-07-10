package gui.gelegenheiten;

import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import gui.TabellenLayout;
import db.Database;
import gui.MutterLayout;
import gui.formdialog.FormDialog;
import gui.formdialog.FormElement;

public class GelegenheitenTabelle extends TabellenLayout {

	private static final long serialVersionUID = 1L;
	
	Database mDB;
	MutterLayout mParent;
	
	public GelegenheitenTabelle(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT * FROM GELEGENHEITEN WHERE PID="+parent.getPID()),
				new Boolean[]{ });
		mDB = db;
		mParent = parent;
	}

	@Override
	public void printTable() {
		try {
			printTable(mDB.executeQuery("SELECT * FROM GELEGENHEITEN WHERE PID="+mParent.getPID()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(Vector<String> v) {
		try {
			mDB.executeUpdate("DELETE FROM GELEGENHEITEN WHERE GELEGENID="+v.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow() {
		final Window w = SwingUtilities.getWindowAncestor(this);
		try {
			ResultSet rs = mDB.executeQuery("SELECT PERSID, NAME FROM PERSONEN");
			final Vector<String> element = new Vector<String>();
			while(rs.next())
			{
				element.add(rs.getString(1)+"-"+rs.getString(2));
			}
		
		new Thread(new Runnable(){

			public void run() {
				Vector<String> art = new Vector<String>();
				art.add("Mitfahrgelegenheit");
				art.add("Übernachtung");
				
				Vector<String> anz = new Vector<String>();
				for(Integer i=1; i<=10;i++)
					anz.add(i.toString());
				Vector<String> result = FormDialog.getDialog("Neue Gelegenheit anlegen", new FormElement[] {
						new FormElement("Ort", FormElement.TEXT_FIELD),
						new FormElement("Art", FormElement.DROP_DOWN, art),
						new FormElement("Anzahl Plaetze", FormElement.DROP_DOWN, anz),
						new FormElement("Anbieter", FormElement.DROP_DOWN,element),
				}, w);
				if(result.size()==4)
				{
					try {
						String persid = result.elementAt(3).split("-")[0];
						mDB.executeUpdate("INSERT INTO GELEGENHEITEN (ORT, ART, ANZPLAETZE, PERSID, PID) VALUES ('"+result.elementAt(0)+
								"', '"+result.elementAt(1)+"', '"+result.elementAt(2)+"', '"+persid+"','"+mParent.getPID()+"')");
					} catch (SQLException e) {
						e.printStackTrace();
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
	public void updateRow(int row, DefaultTableModel modell) {
		try {
			mDB.executeUpdate("UPDATE GELEGENHEITEN SET "
					+ "ORT='"+modell.getValueAt(row, 1)+"', "
					+ "ART='"+modell.getValueAt(row, 2)+"', "
					+ "ANZPLAETZE='"+modell.getValueAt(row, 3)+"', "
					+ "PERSID='"+modell.getValueAt(row, 4)+"' "
					+ "WHERE GELEGENID="+modell.getValueAt(row, 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
