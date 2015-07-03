package gui.putz;

import java.awt.Window;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import db.Database;
import gui.MutterLayout;
import gui.TabellenLayout;
import gui.formdialog.FormDialog;
import gui.formdialog.FormElement;

public class PutzTemplate extends TabellenLayout {
	
private static final long serialVersionUID = 1L;
	
	Database mDB;
	MutterLayout mParent;
	
	public PutzTemplate(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT * FROM PUTZTEMPLATE"));
		mDB = db;
		mParent = parent;
	}

	@Override
	public void refreshTable() {
		try {
			refreshTable(mDB.executeQuery("SELECT * FROM PUTZTEMPLATE"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(int id) {
		try {
			mDB.executeUpdate("DELETE FROM PUTZTEMPLATE WHERE PTID="+id);
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
				Vector<String> result = FormDialog.getDialog("Neue Putzt�tigkeit anlegen", new FormElement[] {
						new FormElement("T�tigkeit", FormElement.TEXT_FIELD),
//						new FormElement("Geschlecht", FormElement.DROP_DOWN, new String[] {"m", "w"}),
//						new FormElement("Email", FormElement.TEXT_FIELD),
//						new FormElement("Handy", FormElement.TEXT_FIELD),
				}, w);
				//TODO verbessern
				if(result.size()==1)
				{
					try {
						mDB.executeUpdate("INSERT INTO PUTZTEMPLATE (ART) VALUES ('"+result.elementAt(0)+"')");
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
			mDB.executeUpdate("UPDATE PUTZTEMPLATE SET "
					+ "ART='"+modell.getValueAt(row, 1));
//					+ "GESCHLECHT='"+modell.getValueAt(row, 2)+"', "
//					+ "EMAIL='"+modell.getValueAt(row, 3)+"', "
//					+ "HANDY='"+modell.getValueAt(row, 4)+"' "
//					+ "WHERE PERSID="+modell.getValueAt(row, 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}

