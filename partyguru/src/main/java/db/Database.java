package db;

import java.sql.*;

import javax.swing.JOptionPane;

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
			try {
				mCon = DriverManager.getConnection("jdbc:h2:"+db+";IFEXISTS=TRUE", "sa", "");
			} catch(SQLException e)
			{
				mCon = DriverManager.getConnection("jdbc:h2:"+db+";"
						+ "INIT=RUNSCRIPT FROM 'classpath:/resources/party.sql'", "sa", "");
				JOptionPane.showMessageDialog(null, "Neue Datenbank angelegt");
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

}
