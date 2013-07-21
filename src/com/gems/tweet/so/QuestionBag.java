package com.gems.tweet.so;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gems.tweet.db.Connect;

public class QuestionBag {

	public void addQuestion(Question q) 
	{
		if (isQuestionInBag(q) == false) addQuestionToBag(q);
	}
	
	public void markQuestionSent(Question q)
	{
		try {
			Connection connection = Connect.getConnection();
			PreparedStatement ps = connection.prepareStatement("UPDATE q SET tweet_sent = NOW() WHERE id = ?");
			ps.setInt(1, q.getId());
			
			ps.executeUpdate();
						
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Question getUnsentQuestion()
	{
		Question q = new Question();
		try {
			Connection connection = Connect.getConnection();
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM q WHERE tweet_sent IS NULL LIMIT 1");
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				q.setId(rs.getInt(1));
				q.setTitle(rs.getString(2));
				q.setVotes(rs.getInt(3));
				q.setLink(rs.getString(4));
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return q;
	}
	
	private void addQuestionToBag(Question q)
	{
		try {
			Connection connection = Connect.getConnection();
			PreparedStatement ps = connection.prepareStatement("INSERT INTO q (id, title, votes, link) VALUES (?,?,?,?)");
			ps.setInt(1, q.getId());
			ps.setString(2, q.getTitle());
			ps.setInt(3, q.getVotes());
			ps.setString(4, q.getLink());
			
			ps.executeUpdate();
			//ResultSet rs = ps.executeQuery();
			//return rs.next();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isQuestionInBag(Question q)
	{
		try {
			Connection connection = Connect.getConnection();
			PreparedStatement ps = connection.prepareStatement("SELECT id FROM q WHERE id = ?");
			ps.setInt(1, q.getId());
			
			ResultSet rs = ps.executeQuery();
			return rs.next();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
