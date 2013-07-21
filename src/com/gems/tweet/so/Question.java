package com.gems.tweet.so;

public class Question {

	private int id = 0;
	
	private String title;
	
	private int votes;
	
	private String link;
	
	private boolean tweet_sent;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isTweet_sent() {
		return tweet_sent;
	}

	public void setTweet_sent(boolean tweet_sent) {
		this.tweet_sent = tweet_sent;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
}
