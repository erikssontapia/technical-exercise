package com.belatrix.technical_exercise.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author Eriksson Tapia
 *
 */
public class ConfigProperty {

	private static ConfigProperty instance;
	
	private static String CONFIG_PROPERTIES_FILE = "config.properties";
	private Properties prop;
	
	private ConfigProperty() {
		readPropertiesFile();
	}
	
	/**
	 * Method for get instance - singleton
	 * @return
	 */
	public static ConfigProperty getInstance() {
		if(instance == null)
			instance = new ConfigProperty();
		
		return instance;
	}

	/**
	 * Method for read properties file
	 */
	public void readPropertiesFile() {

		prop = new Properties();
		try {
			InputStream inputStream = new FileInputStream(CONFIG_PROPERTIES_FILE);
		
			if (inputStream != null) {
				prop.load(inputStream);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * 
	 * Method for get string value
	 */
	public String getStringValue(String key) {
		return prop.getProperty(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 * 
	 * Method for get boolean value
	 */
	public boolean getBooleanValue(String key) {
		boolean value = new Boolean(prop.getProperty(key)).booleanValue();
		return value;
	}

}
