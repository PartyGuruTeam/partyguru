package gui.putz;

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

public class PutzListe extends TabellenLayout 
{	
	private static final long serialVersionUID = 1L;
	
	private Database mDB;
	private MutterLayout mParent;
	
	public PutzListe(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT * FROM PUTZ WHERE PID="+parent.getPID()));
		mDB = db;
		mParent = parent;
	}


	@Override
	public void printTable() {
		try {
			mDB.executeQuery("SELECT * FROM PUTZ WHERE PID="+mParent.getPID());
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
			ResultSet rs = mDB.executeQuery("SELECT PTID, ART FROM PUTZTEMPLATE");
			final Vector<String> elements = new Vector<String>();
			while(rs.next())
			{
				elements.add(rs.getString(1)+"-"+rs.getString(2));
			}
			new Thread(new Runnable(){

				public void run() {
					Vector<String> result = FormDialog.getDialog("Neue Tätigkeit hinzufügen", new FormElement[] {
							new FormElement("Tätigkeit", FormElement.DROP_DOWN, elements),
							new FormElement("Name", FormElement.TEXT_FIELD),
							new FormElement("Raum", FormElement.TEXT_FIELD),
							new FormElement("Dauer", FormElement.TEXT_FIELD),
							new FormElement("Notiz", FormElement.TEXT_FIELD),
							
					}, w);
					//TODO verbessern
					if(result.size()==5)
					{
						try {
							String ptid = result.elementAt(0).split("-")[0];
							mDB.executeUpdate("INSERT INTO PUTZ (PTID, PID, RAUM, DAUER, NOTIZ) VALUES ('"+ptid+"','"+mParent.getPID()+
									"', '"+result.elementAt(2)+"','"+result.elementAt(3)+"','"+result.elementAt(4)+"')");
							
						
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
		// TODO Auto-generated method stub

	}

}
