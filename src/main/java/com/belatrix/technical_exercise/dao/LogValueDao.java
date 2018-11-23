package com.belatrix.technical_exercise.dao;

import com.belatrix.technical_exercise.model.LogValue;

public interface LogValueDao {

	/**
	 * 
	 * @param logValue
	 * @throws Exception
	 * 
	 * Method for save log to database
	 */
	public void saveLogValue(LogValue logValue) throws Exception;
	
	/**
	 * 
	 * @param message
	 * @param type
	 * @return
	 * @throws Exception
	 * 
	 * Method to find row by message and type 
	 */
	public LogValue findByMessageAndType(String message, String type) throws Exception;
}
