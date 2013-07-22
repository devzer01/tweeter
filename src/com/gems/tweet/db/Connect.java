package com.gems.tweet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gems.tweet.ConfigReader;

public class Connect {
	
	private Connection connect = null;
	
	private static Connect instance = null;
	
	private Connect() throws ClassNotFoundException, SQLException
	{
		ConfigReader configReader = null;
		try {
			configReader = new ConfigReader(ConfigReader.class.getResourceAsStream("config.properties"));
		} catch (Exception e) {
			
		}
		
		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager
		          .getConnection(configReader.getProperty("connectString") + "?"
		              + "user=" + configReader.getProperty("username") 
		              + "&password=" + configReader.getProperty("password"));
	}
		
	public static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		if (instance == null) instance = new Connect();
		return instance.connect;
	}

}
