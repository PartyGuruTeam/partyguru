package gui.material;

import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import db.Database;
import gui.MutterLayout;
import gui.TabellenLayout;
import gui.formdialog.FormDialog;
import gui.formdialog.FormElement;

public class Mitbringliste extends TabellenLayout 
{	
	private static final long serialVersionUID = 1L;

	private Database mDB;
	private MutterLayout mParent;
	int mpid;

	public Mitbringliste(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT MID, PERSID, ANZAHL, EINHEIT, NOTIZ FROM Material WHERE PID="+parent.getPID()), 
				new Boolean[] {false, false, true, true, true } );
		mDB = db;
		mParent = parent;
		mpid=parent.getPID();
	}


	@Override
	public void printTable() {
		try {
			printTable(mDB.executeQuery("SELECT MID, PERSID, ANZAHL, EINHEIT, NOTIZ FROM Material WHERE PID="+mParent.getPID()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRow(Vector<String> v) {
		JOptionPane.showMessageDialog(this, "TODO");
	}

	@Override
	public void addRow() 
	{
		final Window w = SwingUtilities.getWindowAncestor(this);
		try {
			ResultSet rs = mDB.executeQuery("SELECT MTID, NAME FROM MaterialTemplate");
			final Vector<String> elements = new Vector<String>();
			while(rs.next())
			{
				elements.add(rs.getString(1)+"-"+rs.getString(2));
			}
			ResultSet rs1 = mDB.executeQuery("SELECT NAME FROM GAESTELISTE LEFT JOIN PERSONEN ON GAESTELISTE.PERSID=PERSONEN.PERSID WHERE PID="+mpid);
			final Vector<String> elements1 = new Vector<String>();
			while(rs1.next())
			{
				elements1.add(rs1.getString(1));
			}
			new Thread(new Runnable(){

				public void run() {
					Vector<String> result = FormDialog.getDialog("Material zur Mitbringliste hinzufügen", new FormElement[] {
							new FormElement("Material", FormElement.DROP_DOWN, elements),
							new FormElement("Person", FormElement.DROP_DOWN, elements1),
							new FormElement("Anzahl", FormElement.TEXT_FIELD),
							new FormElement("Einheit", FormElement.TEXT_FIELD),
							new FormElement("Notiz", FormElement.TEXT_FIELD)

					}, w);
					//TODO verbessern
					if(result.size()==5)
					{
						String mtid = result.elementAt(0).split("-")[0];
						String persid = result.elementAt(1).split("-")[0];
						try {
							mDB.executeUpdate("INSERT INTO Material (MTID, PID, PERSID, ANZAHL, EINHEIT, NOTIZ) VALUES ('"+mtid+
																		"','"+mParent.getPID()+
									"', '"+persid+"', '"+result.elementAt(2)+"','"+result.elementAt(3)+"','"+result.elementAt(4)+"')");
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
		try {
			mDB.executeUpdate("UPDATE Material SET "
					+ "PERSID='"+modell.getValueAt(row, 1)+"', "
					+ "ANZAHL='"+modell.getValueAt(row, 2)+"', "
					+ "EINHEIT='"+modell.getValueAt(row, 3)+"', "
					+ "NOTIZ='"+modell.getValueAt(row, 4)+"' "
					+ "WHERE MID="+modell.getValueAt(row, 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

}
