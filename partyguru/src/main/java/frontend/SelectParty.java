package frontend;

import frontend.formdialog.FormDialog;
import frontend.formdialog.FormElement;
import frontend.tables.TabellenLayout;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import backend.Database;

/**
 * Klasse, um Party von Datenbank auszuwählen.
 * @author PartyGuru
 *
 */
public class SelectParty extends TabellenLayout 
{

	private static final long serialVersionUID = 1L;

	Database mDB;
	MutterLayout mParent;
	JButton mSubmit;
	JFrame mFrame;

	/**
	 * Fenster erstellen
	 * @param db
	 * @param parent
	 * @throws SQLException
	 */
	public SelectParty(Database db, MutterLayout parent) throws SQLException
	{
		super(db.executeQuery("SELECT PID, NAME FROM PARTY"), new Boolean[]{ false, true });
		mFrame = new JFrame("Party auswählen");
		mFrame.add(this);
		mFrame.setSize(500, 500);
		mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mFrame.setVisible(true);
		mDB = db;
		mParent = parent;
		mSubmit = new JButton("Auswählen");
		mSubmit.setBackground(MutterLayout.mittelrosa);
		mSubmit.setForeground(MutterLayout.schriftrosa);
		mSubmit.setFont(MutterLayout.knoepfe);
		mSubmit.addActionListener(this);
		this.mButtonPanel.add(mSubmit);

	}

	@Override
	public void deleteRow(Vector<String> v) {
		try {
			mDB.executeUpdate("DELETE FROM PARTY WHERE PID="+v.elementAt(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void printTable()
	{

		try {
			printTable(mDB.executeQuery("SELECT PID, NAME FROM PARTY"));
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
						
						ResultSet rs = mDB.executeQuery("SELECT PID FROM PARTY ORDER BY PID DESC LIMIT 1");
						int pid=-1;
						if(rs.next())
							pid = rs.getInt("PID");
						
						mDB.executeUpdate("INSERT INTO TASKS(NAME, STATUS, PID, KID) "
								+ "VALUES ('Ort/Datum/Uhrzeit/Motto', '0', '"+pid+"', '1'),"
										+ "('Schlaf-/Mitfahrgelegenheiten', '0', '"+pid+"', '1'),"
										+ "('Einladungen', '0', '"+pid+"', '1'),"
										+ "('Gästeliste', '0', '"+pid+"', '2'),"
										+ "('Mitbringliste', '0', '"+pid+"', '2'),"
										+ "('Pärchengenerierung', '0', '"+pid+"', '2'),"
										+ "('Partyfeedback', '0', '"+pid+"', '2'),"
										+ "('Putzplan', '0', '"+pid+"', '3')");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				printTable();
			}
			
		}).start();
		
	}

	@Override
	public void updateRow(Vector<String> row) 
	{
		try {
			mDB.executeUpdate("UPDATE PARTY SET "
					+ "NAME='"+row.elementAt(1)+"' "
					+ "WHERE PID="+row.elementAt(0));
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
