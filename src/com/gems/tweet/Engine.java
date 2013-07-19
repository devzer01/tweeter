package com.gems.tweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class Engine 
{
	private static Engine engine = null;
	
	private Twitter twitter = null;
	
	private RequestToken requestToken;
	
	private AccessToken accessToken;
	
	private Engine()
	{
		twitter = TwitterFactory.getSingleton();
	}
	
	public static Engine getInstance()
	{
		if (Engine.engine == null) Engine.engine = new Engine();
		return Engine.engine;
	}
	
	public boolean showAuthUrl()
	{
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Open the following URL and grant access to your account:");
        System.out.println(requestToken.getAuthorizationURL());
        System.out.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
        String pin = null;
        try {
        	pin = br.readLine();
        } catch (IOException e) {}
        
        try{
            if(pin.length() > 0){
              accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            }else{
              accessToken = twitter.getOAuthAccessToken();
            }
         } catch (TwitterException te) {
           if(401 == te.getStatusCode()){
             System.out.println("Unable to get the access token.");
           }else{
             te.printStackTrace();
           }
         }
	
		return true;
	}
	
//	twitter.setOAuthAccessToken(accessToken);
//    //persist to the accessToken for future reference.
//    //storeAccessToken((int) twitter.verifyCredentials().getId() , accessToken);
//    Status status = twitter.updateStatus("Where is #snowden now? ");
//    System.out.println("Successfully updated the status to [" + status.getText() + "].");
//    System.exit(0);
//  }
//
//  private static void storeAccessToken(int useId, AccessToken accessToken){
//    //store accessToken.getToken()
//	  System.out.println(accessToken.getToken());
//	  System.out.println(accessToken.getTokenSecret());
//	  System.out.println("User Id: " + accessToken.getUserId());
//    //store accessToken.getTokenSecret()
//  }
}
