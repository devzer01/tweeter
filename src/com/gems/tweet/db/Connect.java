package com.gems.tweet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	
	private Connection connect = null;
	
	private static Connect instance = null;
	
	private Connect() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager
		          .getConnection("jdbc:mysql://localhost/tweeter?"
		              + "user=root&password=");
	}
		
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		if (instance == null) instance = new Connect();
		return instance.connect;
	}

}
