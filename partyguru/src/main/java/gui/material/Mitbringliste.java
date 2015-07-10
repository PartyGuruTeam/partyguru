package gui.material;

import java.awt.Window;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public Mitbringliste(Database db, MutterLayout parent) throws SQLException {
		super(db.executeQuery("SELECT * FROM Material WHERE PID="+parent.getPID()), new Boolean[]{ });
		mDB = db;
		mParent = parent;
	}


	@Override
	public void printTable() {
		try {
			mDB.executeQuery("SELECT * FROM Material WHERE PID="+mParent.getPID());
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
			new Thread(new Runnable(){

				public void run() {
					Vector<String> result = FormDialog.getDialog("Material zur Mitbringliste hinzufügen", new FormElement[] {
							new FormElement("Material", FormElement.DROP_DOWN, elements),

					}, w);
					//TODO verbessern
					if(result.size()==1)
					{
						try {
							
							String mtid = result.elementAt(0).split("-")[0];
							mDB.executeUpdate("INSERT INTO Material (MTID, PID) VALUES ('"+mtid+
									"','"+mParent.getPID()+
									"')");
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
