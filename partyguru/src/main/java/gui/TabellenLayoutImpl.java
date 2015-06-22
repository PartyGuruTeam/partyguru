package gui;

import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import db.Database;

public class TabellenLayoutImpl extends TabellenLayout 
{

	private static final long serialVersionUID = 1L;

	Database mDB;
	
	public TabellenLayoutImpl(Database db, MutterLayout parent) throws SQLException
	{
		super(db.executeQuery("SELECT * FROM PARTY"), parent);
		mDB = db;
	}

	@Override
	public void deleteRow(int id) {
		try {
			mDB.executeUpdate("DELETE FROM PARTY WHERE PID="+id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void refreshTable()
	{
		
		try {
			refreshTable(mDB.executeQuery("SELECT * FROM PARTY"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addRow()
	{
		String name = JOptionPane.showInputDialog("Name eingeben:");
		String ort = JOptionPane.showInputDialog("Ort eingeben:");
		String motto = JOptionPane.showInputDialog("Motto eingeben:");
		
		try {
			mDB.executeUpdate("INSERT INTO PARTY (NAME, ORT, MOTTO) VALUES ('"+name+"', '"+ort+"', '"+motto+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateRow(int row, DefaultTableModel modell) 
	{
		try {
			mDB.executeUpdate("UPDATE PARTY SET "
					+ "NAME='"+modell.getValueAt(row, 1)+"', "
					//+ "ZEIT='"+modell.getValueAt(row, 2)+"', "
					+ "ORT='"+modell.getValueAt(row, 3)+"', "
					+ "MOTTO='"+modell.getValueAt(row, 4)+"'"
					+ "WHERE PID="+modell.getValueAt(row, 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
