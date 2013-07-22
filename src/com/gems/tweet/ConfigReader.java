package com.gems.tweet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	private Properties prop;
	
	public ConfigReader(InputStream is) throws FileNotFoundException, IOException {
            //InputStream is = MySqlProp.class.getResourceAsStream(path);
            prop = new Properties();                
            prop.load(is);		
	}
	
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
}
