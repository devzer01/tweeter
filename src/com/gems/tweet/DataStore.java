package com.gems.tweet;

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
		return new AccessToken(map.get("Token"), map.get("Secret"), Integer.parseInt(map.get("UserId")));
	}
	
	public void storeAccessToken(AccessToken accessToken) 
	{
//		  System.out.println(accessToken.getToken());
//		  System.out.println(accessToken.getTokenSecret());
//		  System.out.println("User Id: " + accessToken.getUserId());
//	    //store accessToken.getTokenSecret()
	}
	
	private Map<String, String> getCredentials()
	{
		Connection con;
		PreparedStatement ps;
		ResultSet rs;
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			con = Connect.getConnection();
			ps = con.prepareStatement("SELECT user_id, token, secret FROM auth WHERE user_id = ?");
			ps.setInt(1, 20946759);
			rs = ps.executeQuery();		
			
			rs.next();
			map.put("UserId", String.valueOf(rs.getInt(1)));
			map.put("Token", rs.getString("token"));
			map.put("Secret", rs.getString("secret"));
			
			
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
