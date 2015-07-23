package gui.putz;

import java.awt.Window;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.SwingUtilities;
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
		super(db.executeQuery("SELECT PTID, ART FROM PUTZTEMPLATE"), 
				new Boolean[] { false, true });
		mDB = db;
		mParent = parent;
	}

	@Override
	public void printTable() {
		try {
			printTable(mDB.executeQuery("SELECT PTID, ART FROM PUTZTEMPLATE"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(Vector<String> v) {
		try {
			mDB.executeUpdate("DELETE FROM PUTZTEMPLATE WHERE PTID="+v.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow() {
		final Window w = SwingUtilities.getWindowAncestor(this);
		new Thread(new Runnable(){

			public void run() {
				Vector<String> result = FormDialog.getDialog("Neue Putztätigkeit anlegen", new FormElement[] {
						new FormElement("Tätigkeit", FormElement.TEXT_FIELD)			
				}, w);
				if(result.size()==1)
				{
					try {
						mDB.executeUpdate("INSERT INTO PUTZTEMPLATE (ART) VALUES ('"+result.elementAt(0)+"')");
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
			mDB.executeUpdate("UPDATE PUTZTEMPLATE SET "
					+ "ART='"+row.elementAt(1)+"' "
					+ "WHERE PTID="+row.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}


