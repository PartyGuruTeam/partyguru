package frontend.forms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

import frontend.MutterLayout;
import backend.Database;

/**
 * Backend von Stammdaten Formular. Stellt DB Verbindung her und ließt Attribute ein.
 * @author PartyGuru
 *
 */
public class PStammdaten extends FormularLayout
{
	private static final long serialVersionUID = 1L;
	Database mDB;
	MutterLayout mParent;
	int mPID;
	
	ResultSet result;
	String mPMotto;

	public PStammdaten(Database db, MutterLayout parent) throws SQLException
	{	
		super(db, parent);
		//Erstellung der lokalen Variablen für die Befüllung der Felder

		//int aPID;
		String aName = null;
		String aMotto = null;
		String aOrt = null;

		result = db.executeQuery("SELECT * FROM PARTY WHERE PID=" + parent.getPID());
		
		if(result.next())
		{
			aName = result.getString("NAME");
			aMotto = result.getString("MOTTO");
			aOrt = result.getString("ORT");
			
			mDB = db;
			mParent = parent;
			mPID = parent.getPID();
			//Befüllen der Textfelder
			tfArray.get(0).setText(aName);
			tfArray.get(1).setText(aOrt);
			tfArray.get(2).setText(aMotto);
			if(result.getDate("ZEIT")!=null)
			{
				mDate.setDate(result.getDate("ZEIT"));
				mTime.setTime(result.getTimestamp("ZEIT"));
			}
			
		} else
		{
			JOptionPane.showMessageDialog(parent, "Datenbankfehler");
		}
	}



	@Override
	public void refreshTable() 
	{
		try {
			refreshTable(mDB.executeQuery("SELECT * FROM PARTY WHERE PID =" + mPID));
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public void updateData() {

		String name = tfArray.elementAt(0).getText();
		String ort = tfArray.elementAt(1).getText();
		
		Date time = mTime.getTime();
		Calendar cTime = Calendar.getInstance();
		cTime.setTime(time);
		
		Date date = mDate.getDate();
		Calendar cDate = Calendar.getInstance();
		cDate.setTime(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.set(cDate.get(Calendar.YEAR), cDate.get(Calendar.MONTH), cDate.get(Calendar.DAY_OF_MONTH));

		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datum = sf.format(cal.getTime());
		String motto = tfArray.elementAt(2).getText();

		try {
			mDB.executeUpdate("UPDATE PARTY SET NAME='"+name+"', ZEIT='"+datum+"', ORT='"+ort+"', MOTTO='"+motto+"' WHERE PID =" + mPID);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}




}



