package gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Database;



public class PStammdaten extends FormularLayout{
	
	/**
	 * 
	 */
	
	
	private static final long serialVersionUID = 1L;
	Database mDB;
	MutterLayout mParent;
	int mPID;
	
	ResultSet result;
	String mPMotto;
	
	public PStammdaten(Database db, MutterLayout parent) throws SQLException
	{	
		
		 //Erstellung der lokalen Variablen für die Befüllung der Felder
		 
		//int aPID;
		String aName = null;
		String aMotto = null;
		String aOrt = null;
		
		result = null;
		
		
		//TODO Parties eingrenzen mit ID
		
		result = db.executeQuery("SELECT * FROM PARTY WHERE PID = " + parent.getPID());
		
		
			//aPID = result.getInt("PID");
			aName = result.getString("NAME");
			aMotto = result.getString("MOTTO");
			aOrt = result.getString("ORT");
			//TODO Zeit auslesen lassen
			result.next();
		
		
		mDB = db;
		mParent = parent;
		mPID = parent.getPID();
		
		//Befüllen der Textfelder
		tfArray.get(0).setText(aName);
		//tfArray.get(1).setText();			//TODO GASTGEBER einfügen
		tfArray.get(2).setText(aOrt);
		//tfArray.get(3)				//TODO Datum / Uhrzeit Lukas 3: Datum
		//tfArray.get(4)												4: Uhrzeit
		tfArray.get(5).setText(aMotto);
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
		//String datum = tfArray.elementAt(3).getText();			//TODO noch nicht berücksichtigt
		String startzeit = tfArray.elementAt(4).getText();
		String motto = tfArray.elementAt(5).getText();
		
		
		//TODO Datenbankeinträge fertig machen
		
		try {
			mDB.executeUpdate("UPDATE PARTY (NAME, ZEIT, ORT, MOTTO) VALUES ('"+name+"', '"+startzeit+"', '"+ort+"', '"+motto+"') WHERE PID =" + mPID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	
}
	


