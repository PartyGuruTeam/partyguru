package db;

import java.sql.*;

public class Database 
{
	Connection mCon;
	
	public Database(String db) throws ClassNotFoundException, SQLException
	{
		if(db!=null && db!="")
		{
			Class.forName("org.h2.Driver");
			mCon = DriverManager.getConnection("jdbc:h2:"+db, "sa", "");
		}
	}

	@Override
	protected void finalize() throws Throwable {
		mCon.close();
		super.finalize();
	}
	
	
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
	
	
	//for testing only
	public static void main(String[] args) {
		try {
			Database db = new Database("C:/Users/Bastian/test");

			ResultSet rs = db.executeQuery("SELECT * FROM TEST");
			while(rs.next())
			{
				System.out.println(rs.getString(1));
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
