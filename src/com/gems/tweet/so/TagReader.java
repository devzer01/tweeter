package com.gems.tweet.so;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gems.tweet.ConfigReader;

public class TagReader {

	private static String downloadTag()
	{
		URL soURL = null;
		String html = "";
		
		ConfigReader configReader = null;
		try {
			configReader = new ConfigReader(ConfigReader.class.getResourceAsStream("config.properties"));
		} catch (Exception e) {
			
		}
		
		try {
			soURL = new URL(configReader.getProperty("questionQueue"));
			InputStream is = soURL.openStream();
			html = IOUtils.toString(is, "UTF-8");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return html;
	}
	
	
	public static List<Question> getQuestions()
	{
		String html = TagReader.downloadTag();
		Document doc = Jsoup.parse(html, "UTF-8");
		Elements qs = doc.select("#questions .question-summary");
		
		Pattern pattern = Pattern.compile("(\\d+)");
		
		List<Question> questionList = new ArrayList<Question>();
		
		for (Element elm : qs) {
			
			String tid = elm.id();
			
			Matcher matcher = pattern.matcher(tid);
			
			matcher.find();
			
			String id = matcher.group(0);
			
			//System.out.println("Found Group " + id);
			
			Elements votes = elm.select(".vote-count-post ");
			String vote = votes.get(0).text();
			
			Elements titles = elm.select("div.summary h3 a");
			String question = titles.get(0).attr("href");
			String title = titles.get(0).text();
			
			Question q = new Question();
			q.setId(Integer.parseInt(id));
			q.setTitle(title);
			q.setVotes(Integer.parseInt(vote));
			q.setLink(question);
			questionList.add(q);
			
		}
		
		return questionList;
	}
}

