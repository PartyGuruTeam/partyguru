package db;

import java.sql.*;

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
	 */
	public ResultSet executeQuery(String sql)
	{
		Statement stmt=null;
		ResultSet rs=null;
		try {
			stmt = mCon.createStatement();
			if(stmt!=null)
				rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
		}
		return rs;
	}
	
	/**
	 * 
	 */
	public void initDB()
	{
		//TODO neue Datenbank aufsetzen
	}

}
