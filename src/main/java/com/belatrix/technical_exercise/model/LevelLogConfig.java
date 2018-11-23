package com.belatrix.technical_exercise.model;

/**
 * 
 * @author Eriksson Tapia
 *
 */
public class LevelLogConfig {
	
	public static final int LEVEL_LOG_MESSAGE = 1;
	public static final int LEVEL_LOG_WARNING = 2;
	public static final int LEVEL_LOG_ERROR = 3;
	
	public static final String LEVEL_LOG_MESSAGE_TEXT = "message";
	public static final String LEVEL_LOG_WARNING_TEXT = "warning";
	public static final String LEVEL_LOG_ERROR_TEXT = "error";
	
	private boolean logMessage;
	private boolean logWarning;
	private boolean logError;
	
	/**
	 * 
	 * @param logMessage
	 * @param logWarning
	 * @param logError
	 */
	public LevelLogConfig(boolean logMessage, boolean logWarning, boolean logError) {
		super();
		this.logMessage = logMessage;
		this.logWarning = logWarning;
		this.logError = logError;
	}
	public boolean isLogMessage() {
		return logMessage;
	}
	public void setLogMessage(boolean logMessage) {
		this.logMessage = logMessage;
	}
	public boolean isLogWarning() {
		return logWarning;
	}
	public void setLogWarning(boolean logWarning) {
		this.logWarning = logWarning;
	}
	public boolean isLogError() {
		return logError;
	}
	public void setLogError(boolean logError) {
		this.logError = logError;
	}
}
