package com.gems.tweet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.gems.tweet.db.Connect;

import twitter4j.auth.AccessToken;

public class DataStore 
{
	public AccessToken getAccessToken()
	{
		HashMap<String, String>  map = (HashMap<String, String>) getCredentials();
		System.out.println("Getting System Credentials");
		if (map.get("Token") != null) {
			return new AccessToken(map.get("Token"), map.get("Secret"), Integer.parseInt(map.get("UserId")));
		} 
		return null;
	}
	
	public void storeAccessToken(AccessToken accessToken) 
	{
		Connection con = null;
		try {
			con = Connect.getConnection();
			PreparedStatement ps = con.prepareStatement("INSERT INTO auth (user_id, token, secret) VALUES (?,?,?)");
			ps.setInt(1, (int)accessToken.getUserId());
			ps.setString(2, accessToken.getToken());
			ps.setString(3, accessToken.getTokenSecret());
			
			ps.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			System.err.println("Database Drivers Not Found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error Connecting to Database");
			e.printStackTrace();
		}
	}
	
	private Map<String, String> getCredentials()
	{
		ConfigReader configReader = null;
		try {
			configReader = new ConfigReader(ConfigReader.class.getResourceAsStream("config.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Connection con;
		PreparedStatement ps;
		ResultSet rs;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			con = Connect.getConnection();
			ps = con.prepareStatement("SELECT user_id, token, secret FROM auth WHERE user_id = ?");
			ps.setInt(1, Integer.parseInt(configReader.getProperty("twitterId")));
			rs = ps.executeQuery();		
			
			if (rs.next()) {
				map.put("UserId", String.valueOf(rs.getInt(1)));
				map.put("Token", rs.getString("token"));
				map.put("Secret", rs.getString("secret"));
			}
			
			
		} catch (ClassNotFoundException e) {
			System.err.println("Database Drivers Not Found");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Error Connecting to Database");
			e.printStackTrace();
		}
		
		return map;
	}
}
