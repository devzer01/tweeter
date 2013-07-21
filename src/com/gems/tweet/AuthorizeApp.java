package com.gems.tweet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class AuthorizeApp 
{
	public static AccessToken getAuthorization()
	{
		Engine engine = Engine.getInstance();
		Twitter twitter = engine.getTwitter();
		RequestToken requestToken = null;
		AccessToken accessToken = null;
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
        
        return accessToken;
	}
}
