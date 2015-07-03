package gui.kontakte;

import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import db.Database;
import gui.MutterLayout;
import gui.TabellenLayout;

public class Gaesteliste extends TabellenLayout 
{	
	private static final long serialVersionUID = 1L;
	
	private Database mDB;
	private MutterLayout mParent;
	
	public Gaesteliste(Database db, MutterLayout parent) {
		super(db.executeQuery("SELECT * FROM GAESTELISTE WHERE PID="+parent.getP));
		
	}


	@Override
	public void refreshTable() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRow(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addRow() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateRow(int row, DefaultTableModel modell) {
		// TODO Auto-generated method stub

	}

}
