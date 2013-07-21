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
	
	private Engine()
	{
		twitter = TwitterFactory.getSingleton();
	}
	
	public static Engine getInstance()
	{
		if (Engine.engine == null) Engine.engine = new Engine();
		return Engine.engine;
	}
	
	public Twitter getTwitter()
	{
		return twitter;
	}
}
