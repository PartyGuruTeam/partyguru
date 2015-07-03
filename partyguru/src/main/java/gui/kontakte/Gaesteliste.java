package gui.kontakte;

import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import db.Database;
import gui.MutterLayout;
import gui.TabellenLayout;
import gui.formdialog.FormDialog;
import gui.formdialog.FormElement;

public class Gaesteliste extends TabellenLayout 
{	
	private static final long serialVersionUID = 1L;
	
	private Database mDB;
	private MutterLayout mParent;
	
	public Gaesteliste(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT * FROM GAESTELISTE WHERE PID="+parent.getPID()));
		mDB = db;
		mParent = parent;
	}


	@Override
	public void refreshTable() {
		try {
			mDB.executeQuery("SELECT * FROM GAESTELISTE WHERE PID="+mParent.getPID());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(int id) {
		
	}

	@Override
	public void addRow() 
	{
		final Window w = SwingUtilities.getWindowAncestor(this);
		try {
			ResultSet rs = mDB.executeQuery("SELECT PERSID, NAME FROM PERSONEN");
			Vector<String> elements = new Vector<String>();
			while(rs.next())
			{
				elements.add(rs.getString(1)+"-"+rs.getString(2));
			}
			new Thread(new Runnable(){

				@Override
				public void run() {
					Vector<String> result = FormDialog.getDialog("Neuen Gast hinzufügen", new FormElement[] {
							new FormElement("Person", FormElement.DROP_DOWN, elements),
					}, w);
					//TODO verbessern
					if(result.size()==1)
					{
						try {
							String persid = result.elementAt(0).split("-")[0];
							mDB.executeUpdate("INSERT INTO GAESTELISTE (PID, PERSID) VALUES ('"+mParent.getPID()+
									"', '"+persid+"')");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					refreshTable();
				}
				
			}).start();	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}	
	}

	@Override
	public void updateRow(int row, DefaultTableModel modell) {
		// TODO Auto-generated method stub

	}

}
