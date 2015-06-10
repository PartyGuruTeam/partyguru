package db;

import java.sql.*;

public class Database 
{
	Connection mCon;
	
	public Database() throws ClassNotFoundException, SQLException
	{
		//start db server
		Class.forName("org.h2.Driver");
		mCon = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
	}

	@Override
	protected void finalize() throws Throwable {
		mCon.close();
		super.finalize();
	}
	
	
	//for testing only
	public static void main(String[] args) {
		System.out.println("Hello");
		try {
			new Database();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
