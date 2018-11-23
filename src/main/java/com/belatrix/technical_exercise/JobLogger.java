package com.belatrix.technical_exercise;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.belatrix.technical_exercise.config.ConfigProperty;
import com.belatrix.technical_exercise.dao.LogValueDao;
import com.belatrix.technical_exercise.dao.impl.LogValueDaoImpl;
import com.belatrix.technical_exercise.model.LevelLogConfig;
import com.belatrix.technical_exercise.model.LogValue;
import com.belatrix.technical_exercise.model.OutputLogConfig;
import com.belatrix.technical_exercise.util.Utils;

public class JobLogger {
	
	private static LevelLogConfig levelLogConfig;
	private static OutputLogConfig outputLogConfig;
	private static String logFileFolder;
	private static String logFileName;
	private Logger logger;

	private LogValueDao logValueDao;

	/**
	 * 
	 * @param levelLogConfig
	 * @param outputLogConfig
	 */
	public JobLogger(LevelLogConfig levelLogConfig, OutputLogConfig outputLogConfig) {

		ConfigProperty configProperty = ConfigProperty.getInstance();

		logger = Logger.getLogger("MyLog");

		this.levelLogConfig = levelLogConfig;
		this.outputLogConfig = outputLogConfig;

		logFileFolder = configProperty.getStringValue("log.file.folder");
		logFileName = configProperty.getStringValue("log.file.name");
		
		configure();
	}

	/**
	 * Method for handler configuration
	 */
	public void configure() {
		if (outputLogConfig.isLogToFile()) {
			logger.addHandler(Utils.getFileHandler(logFileFolder + logFileName));
		}

		if (outputLogConfig.isLogToConsole()) {
			logger.addHandler(Utils.getConsoleHandler());
		}

		if (outputLogConfig.isLogToDatabase()) {
			logValueDao = new LogValueDaoImpl();
		}
	}
	
	/**
	 * Method for dispatch message
	 * @param messageText
	 * @param levelLog
	 * @throws Exception
	 */
	public void LogMessage(String messageText, int levelLog) throws Exception {

		int type = 0;
		String log = "";

		messageText = messageText.trim();
		if (messageText == null || messageText.length() == 0) {
			return;
		}
		if (!outputLogConfig.isLogToFile() && !outputLogConfig.isLogToConsole() && !outputLogConfig.isLogToDatabase()) {
			throw new Exception("Invalid configuration");
		}
		if ((!levelLogConfig.isLogMessage() && !levelLogConfig.isLogWarning() && !levelLogConfig.isLogError())
				|| (levelLog != LevelLogConfig.LEVEL_LOG_MESSAGE && levelLog != LevelLogConfig.LEVEL_LOG_WARNING) && levelLog != LevelLogConfig.LEVEL_LOG_ERROR) {
			throw new Exception("Error or Warning or Message must be specified");
		}

		if ((levelLog == LevelLogConfig.LEVEL_LOG_MESSAGE) && levelLogConfig.isLogMessage()) {
			type = LevelLogConfig.LEVEL_LOG_MESSAGE;
			log = createLog(messageText,  LevelLogConfig.LEVEL_LOG_MESSAGE_TEXT);
		}
		
		if ((levelLog == LevelLogConfig.LEVEL_LOG_WARNING) && levelLogConfig.isLogWarning()) {
			type = LevelLogConfig.LEVEL_LOG_WARNING;
			log = createLog(messageText,  LevelLogConfig.LEVEL_LOG_WARNING_TEXT);
		}		

		if ((levelLog == LevelLogConfig.LEVEL_LOG_ERROR) && levelLogConfig.isLogError()) {
			type = LevelLogConfig.LEVEL_LOG_ERROR;
			log = createLog(messageText,  LevelLogConfig.LEVEL_LOG_ERROR_TEXT);
		}

		if(!log.isEmpty()) {
			if (outputLogConfig.isLogToFile() || outputLogConfig.isLogToConsole()) {
				logger.log(Level.INFO, log);
			}
	
			if (outputLogConfig.isLogToDatabase()) {
				saveLogValue(log, type);
			}
		}
	}

	/**
	 * Method for save LogValue
	 * @param log
	 * @param type
	 * @throws Exception
	 */
	private void saveLogValue(String log, int type) throws Exception {
		LogValue logValue = new LogValue();
		logValue.setMessage(log);
		logValue.setType(String.valueOf(type));
		logValueDao.saveLogValue(logValue);
	}
	
	/**
	 * Method for create log line
	 * @param messageText
	 * @param levelLogText
	 * @return
	 */
	private String createLog(String messageText, String levelLogText) {
		StringBuilder log =  new StringBuilder();
		
		log
			.append(levelLogText)
			.append(" ")
			.append(DateFormat.getDateInstance(DateFormat.LONG).format(new Date()))
			.append(" ")
			.append(messageText);
		
		return log.toString();		
	}

	public Logger getLogger() {
		return logger;
	}

	public LogValueDao getLogValueDao() {
		return logValueDao;
	}

}
