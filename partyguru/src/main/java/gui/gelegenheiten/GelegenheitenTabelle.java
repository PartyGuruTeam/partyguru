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
		super(db.executeQuery("SELECT * FROM GELEGENHEITEN WHERE PID="+parent.getPID()));
		mDB = db;
		mParent = parent;
	}

	@Override
	public void refreshTable() {
		try {
			refreshTable(mDB.executeQuery("SELECT * FROM GELEGENHEITEN WHERE PID="+parent.getPID()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(int id) {
		try {
			mDB.executeUpdate("DELETE FROM GELEGENHEITEN WHERE GELEGENID="+id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow() {
		final Window w = SwingUtilities.getWindowAncestor(this);
		new Thread(new Runnable(){

			@Override
			public void run() {
				Vector<String> result = FormDialog.getDialog("Neue Gelegenheit anlegen", new FormElement[] {
						new FormElement("Ort", FormElement.TEXT_FIELD),
						new FormElement("Art", FormElement.DROP_DOWN, new String[] {"Mitfahrgelegenheit", "Übernachtung"}),
						new FormElement("Anzahl Plaetze", FormElement.DROP_DOWN, new String[] {"1","2","3","4","5","6","7","8","9","10"}),
						new FormElement("Anbieter", FormElement.TEXT_FIELD),
				}, w);
				//TODO verbessern
				if(result.size()==4)
				{
					try {
						mDB.executeUpdate("INSERT INTO GELEGENHEITEN (ORT, ART, ANZPLAETZE, PERSID, PID) VALUES ('"+result.elementAt(0)+
								"', '"+result.elementAt(1)+"', '"+result.elementAt(2)+"', '"+result.elementAt(3)+"','"+parent.getPID()+"')");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				refreshTable();
			}
			
		}).start();		
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
