package com.gems.tweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gems.tweet.so.Question;
import com.gems.tweet.so.QuestionBag;
import com.gems.tweet.so.TagReader;

import twitter4j.IDs;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

public class TweetRunner {
	
	private class TagReaderRunnable implements Runnable {

		private boolean stop = false;
		
		@Override
		public void run() {
			QuestionBag qb = new QuestionBag();
			
			ConfigReader configReader = null;
			try {
				configReader = new ConfigReader(ConfigReader.class.getResourceAsStream("config.properties"));
			} catch (Exception e) {
				
			}
			
			int voteLimit = Integer.parseInt(configReader.getProperty("voteLimit"));
			
			while (stop == false) {
				System.out.println("Checking Questions ...");
				List<Question> questionList = TagReader.getQuestions();				
				for (Question q : questionList) {
					if (q.getVotes() >= voteLimit) qb.addQuestion(q);
				}
				try {
					Thread.sleep(60 * 5 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
	}
	
	private class TweeterRunnable implements Runnable {

		private boolean stop = false;
		
		@Override
		public void run() {
			QuestionBag qb = new QuestionBag();
			while (stop == false) {
				System.out.println("Checking Unsent Questions ...");
				Question q = qb.getUnsentQuestion();
				if (q.getId() != 0) {
					String message = "#PHP http://stackoverflow.com" + q.getLink();
					TweetRunner.sendTweet(message);
					qb.markQuestionSent(q);
				} else {
					System.out.println("No Intresting Questions to be Sent");
				}
				try {
					Thread.sleep(60 * 5 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private class UnfollowUnfollwer implements Runnable {

		@Override
		public void run()  {
			IDs ids = null;
			IDs friends = null;
			Twitter twitter = Engine.getInstance().getTwitter();
			try {
				friends = twitter.getFollowersIDs(-1); //those who are following me
				System.out.println("Followers Count : " + friends.getIDs().length);
				ids = twitter.getFriendsIDs(-1); //those who i am following
				System.out.println("Friends Count : " + ids.getIDs().length);
			
				long friendsArray[] = friends.getIDs();
				
				Map<Long, Long> friendsList = new HashMap<Long, Long>();
				
				for (long fid : friendsArray) {
					friendsList.put(fid, fid);
				}
				
				for (long id : ids.getIDs()) {
					if (friendsList.get(id) == null) {
						twitter.destroyFriendship(id);
					}
				}
				
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
	}

	public TweetRunner()
	{
		TweetRunner.runTweeter();		
	}
	
	public void init()
	{
		String datetime = "";
		
		TagReaderRunnable tgr = new TagReaderRunnable();
		Thread tgrt = new Thread(tgr);
		tgrt.start();
		
		TweeterRunnable tr = new TweeterRunnable();
		Thread trt = new Thread(tr);
		trt.start();
		
		UnfollowUnfollwer uu = new UnfollowUnfollwer();
		Thread tuu = new Thread(uu);
		tuu.start();
		
		
		while (tgrt.isAlive()) {
			try {
				Thread.sleep(1000 * 60);
			} catch (Exception e) {
				
			}
			System.out.println("Time Check " + datetime);
		}
	}
	
	public static void main(String[] args) 
	{
		TweetRunner tr = new TweetRunner();
		tr.init();
	}

	public static void runTweeter()
	{
		ConfigReader configReader = null;
		try {
			configReader = new ConfigReader(ConfigReader.class.getResourceAsStream("config.properties"));
		} catch (Exception e) {
			
		}
		
		System.setProperty("twitter4j.oauth.consumerKey", configReader.getProperty("consumerKey"));
    	System.setProperty("twitter4j.oauth.consumerSecret", configReader.getProperty("consumerSecret"));
		
		// TODO Auto-generated method stub
		DataStore dataStore = new DataStore();
		AccessToken accessToken = dataStore.getAccessToken();
		
		if (accessToken == null) {
			accessToken = AuthorizeApp.getAuthorization();
			dataStore.storeAccessToken(accessToken);
		}
		
		Engine engine = Engine.getInstance();
		Twitter twitter = engine.getTwitter();		
		twitter.setOAuthAccessToken(accessToken);
	}
	
	public static void sendTweet(String tweet)
	{
		Engine engine = Engine.getInstance();
		Twitter twitter = engine.getTwitter();

		Status status = null;
		try {
			status = twitter.updateStatus(tweet);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("Successfully updated the status to [" + status.getText() + "].");
	}

}
