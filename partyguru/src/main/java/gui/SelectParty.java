package gui;

import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import db.Database;

/**
 * Testklasse zum Testen von TabellenLayout.
 * @author Bastian
 *
 */
public class SelectParty extends TabellenLayout 
{

	private static final long serialVersionUID = 1L;

	Database mDB;
	MutterLayout mParent;
	JButton mSubmit;
	JFrame mFrame;
	
	public SelectParty(Database db, MutterLayout parent) throws SQLException
	{
		super(db.executeQuery("SELECT * FROM PARTY"));
		mFrame = new JFrame("Datenbank auswählen");
		mFrame.add(this);
		mFrame.setSize(500, 500);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setVisible(true);
		mDB = db;
		mParent = parent;
		mSubmit = new JButton("Auswählen");
		mSubmit.addActionListener(this);
		this.mButtonPanel.add(mSubmit);
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if(e.getSource().equals(mSubmit))
		{
			mParent.mPID = (Integer) super.mTabelle.getValueAt(super.mTabelle.getSelectedRow(), 0);
			
			mFrame.dispose();		
		}
	}
	
	
	
	
}
