package com.gems.tweet;

import twitter4j.auth.AccessToken;

public class TweetRunner {

	public static void main(String[] args) {
		
		System.setProperty("twitter4j.oauth.consumerKey", "4C11x9v4rNMslIePfvKEA");
    	System.setProperty("twitter4j.oauth.consumerSecret", "nFeKbAwCVvd8vWyoQi3vGqOldn8d8lja25f7q5eWI");
		
		// TODO Auto-generated method stub
		DataStore dataStore = new DataStore();
		AccessToken accessToken = dataStore.getAccessToken();
		
		if (accessToken == null) {
			
		}
	}
	
	public static AccessToken getAccessToken()
	{
		return null;
	}

}
