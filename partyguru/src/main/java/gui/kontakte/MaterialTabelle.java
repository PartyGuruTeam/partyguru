package gui.kontakte;

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

public class MaterialTabelle extends TabellenLayout {

	private static final long serialVersionUID = 1L;
	
	Database mDB;
	MutterLayout mParent;
	
	public MaterialTabelle(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT * FROM MaterialTemplate"));
		mDB = db;
		mParent = parent;
	}

	@Override
	public void refreshTable() {
		try {
			refreshTable(mDB.executeQuery("SELECT * FROM MaterialTemplate"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(int id) {
		try {
			mDB.executeUpdate("DELETE FROM MaterialTemplate WHERE MTID="+id);
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
				Vector<String> result = FormDialog.getDialog("Neues Material anlegen", new FormElement[] {
						new FormElement("Name", FormElement.TEXT_FIELD),
						new FormElement("Art", FormElement.DROP_DOWN, new String[] {"Essen", "Getr�nke", "Partyutensilien"}),
						
				}, w);
				//TODO verbessern
				if(result.size()==2)
				{
					try {
						mDB.executeUpdate("INSERT INTO MaterialTemplate (NAME, Art) VALUES ('"+result.elementAt(0)+
								"', '"+result.elementAt(1)+"')");
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
			mDB.executeUpdate("UPDATE MaterialTemplate SET "
					+ "NAME='"+modell.getValueAt(row, 1)+"', "
					+ "ART='"+modell.getValueAt(row, 2)+"', "
					+ "WHERE MTID="+modell.getValueAt(row, 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
