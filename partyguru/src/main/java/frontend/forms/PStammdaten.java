package frontend.forms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

import frontend.MutterLayout;
import backend.Database;

//TODO Tabs entfernen

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

		//TODO Parties eingrenzen mit ID

		result = db.executeQuery("SELECT * FROM PARTY WHERE PID=" + parent.getPID());
		
		if(result.next())
		{
			//aPID = result.getInt("PID");
			aName = result.getString("NAME");
			aMotto = result.getString("MOTTO");
			aOrt = result.getString("ORT");
			//TODO Zeit auslesen lassen
			
			mDB = db;
			mParent = parent;
			mPID = parent.getPID();
			//Befüllen der Textfelder
			tfArray.get(0).setText(aName);
			//tfArray.get(1).setText();			//TODO GASTGEBER einfügen
			tfArray.get(2).setText(aOrt);
			//tfArray.get(3)				//TODO Datum / Uhrzeit Lukas 3: Datum
			//tfArray.get(4)												4: Uhrzeit
			tfArray.get(3).setText(aMotto);
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
		// TODO Auto-generated method stub

		String name = tfArray.elementAt(0).getText();
		//String gastgeber = tfArray.elementAt(1).getText();		//TODO noch nicht berücksichtigt
		String ort = tfArray.elementAt(2).getText();
		
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
		String motto = tfArray.elementAt(3).getText();

		try {
			mDB.executeUpdate("UPDATE PARTY SET NAME='"+name+"', ZEIT='"+datum+"', ORT='"+ort+"', MOTTO='"+motto+"' WHERE PID =" + mPID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}




}



