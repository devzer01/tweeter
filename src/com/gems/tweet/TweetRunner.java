package com.gems.tweet;

import java.util.List;

import com.gems.tweet.so.Question;
import com.gems.tweet.so.QuestionBag;
import com.gems.tweet.so.TagReader;

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
			while (stop == false) {
				System.out.println("Checking Questions ...");
				List<Question> questionList = TagReader.getQuestions();				
				for (Question q : questionList) {
					if (q.getVotes() >= 2) qb.addQuestion(q);
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
		System.setProperty("twitter4j.oauth.consumerKey", "4C11x9v4rNMslIePfvKEA");
    	System.setProperty("twitter4j.oauth.consumerSecret", "nFeKbAwCVvd8vWyoQi3vGqOldn8d8lja25f7q5eWI");
		
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
