package gui;

import gui.formdialog.FormDialog;
import gui.formdialog.FormElement;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
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
		super(db.executeQuery("SELECT PID, NAME FROM PARTY"));
		mFrame = new JFrame("Party auswählen");
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
			refreshTable(mDB.executeQuery("SELECT PID, NAME FROM PARTY"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRow()
	{
		final Window w = SwingUtilities.getWindowAncestor(this);
		new Thread(new Runnable(){

			public void run() {
				Vector<String> result = FormDialog.getDialog("Neue Party erstellen", new FormElement[] {
						new FormElement("Name", FormElement.TEXT_FIELD)
				}, w);
				if(result.size()==1)
				{
					try {
						mDB.executeUpdate("INSERT INTO PARTY"
								+ " (NAME) VALUES ('"+result.elementAt(0)+"')");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				refreshTable();
			}
			
		}).start();
		
	}

	@Override
	public void updateRow(int row, DefaultTableModel modell) 
	{
		try {
			mDB.executeUpdate("UPDATE PARTY SET "
					+ "NAME='"+modell.getValueAt(row, 1)+"', "
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
			if(super.mTabelle.getSelectedRow()>=0)
			{
				//TODO BUG: wenn Tabelle verändert wurde, richtigen Wert auslesen
				Integer result = (Integer) super.mTabelle.getValueAt(super.mTabelle.getSelectedRow(), 0);
				if(result!=null)
				{
					mParent.mPID = result;
					mFrame.dispose();
				}
			} else
			{
				JOptionPane.showMessageDialog(this, "Wählen Sie eine Party aus.");
			}
		}
	}




}
