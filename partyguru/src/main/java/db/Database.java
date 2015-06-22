package db;

import java.sql.*;

/**
 * Klasse zur Herstellung der Verbindung zur Datenbank und um DB-Anfragen zu stellen
 * @author Bastian
 *
 */
public class Database 
{
	Connection mCon;
	
	/**
	 * Konstruktor <br>
	 * Erstellt Verbindung zur Datenbank, die �bergeben wurde.
	 * @param db Pfad zur Datenbank
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public Database(String db) throws ClassNotFoundException, SQLException
	{
		if(db!=null && db!="")
		{
			Class.forName("org.h2.Driver");
			mCon = DriverManager.getConnection("jdbc:h2:"+db, "sa", "");
			if(checkDB()==false)
			{
				initDB();
			}
		}
	}

	/**
	 * "Destruktor" <br>
	 * Schlie�t Verbindung zur Datenbank
	 */
	@Override
	protected void finalize() throws Throwable {
		mCon.close();
		super.finalize();
	}
	
	/**
	 * F�hrt Datenbankanfrage aus und gibt ResultSet zur�ck
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet executeQuery(String sql) throws SQLException
	{
		Statement stmt=null;
		ResultSet rs=null;
		stmt = mCon.createStatement();
		if(stmt!=null)
			rs = stmt.executeQuery(sql);
		return rs;
	}
	
	/**
	 * F�hrt Datenbankanfragen aus, die etwas in der Datenbank ver�ndern
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate(String sql) throws SQLException
	{
		Statement stmt=null;
		int result=0;
		stmt = mCon.createStatement();
		if(stmt!=null)
			result = stmt.executeUpdate(sql);
		return result;
	}
	
	
	/**
	 * Datenbank intial aufsetzen
	 */
	public void initDB()
	{
		//TODO neue Datenbank aufsetzen
	}
	
	/**
	 * Pr�fen, ob Datenbank die ben�tigten Tabellen und Spalten enth�lt. 
	 * @return true, wenn Pr�fung erfolgreich
	 */
	private boolean checkDB()
	{
		//TODO DB checken
		return true;
	}

}
