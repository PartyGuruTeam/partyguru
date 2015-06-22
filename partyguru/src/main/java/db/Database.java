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
	 * Erstellt Verbindung zur Datenbank, die übergeben wurde.
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
	 * Schließt Verbindung zur Datenbank
	 */
	@Override
	protected void finalize() throws Throwable {
		mCon.close();
		super.finalize();
	}
	
	/**
	 * Führt Datenbankanfrage aus und gibt ResultSet zurück
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
	 * Führt Datenbankanfragen aus, die etwas in der Datenbank verändern
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
	 * Prüfen, ob Datenbank die benötigten Tabellen und Spalten enthält. 
	 * @return true, wenn Prüfung erfolgreich
	 */
	private boolean checkDB()
	{
		//TODO DB checken
		return true;
	}

}
