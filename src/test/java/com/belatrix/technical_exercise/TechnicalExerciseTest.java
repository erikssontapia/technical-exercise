package com.belatrix.technical_exercise;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.StreamHandler;

import org.junit.Before;
import org.junit.Test;

import com.belatrix.technical_exercise.config.ConfigDerbyDb;
import com.belatrix.technical_exercise.model.LevelLogConfig;
import com.belatrix.technical_exercise.model.LogValue;
import com.belatrix.technical_exercise.model.OutputLogConfig;

public class TechnicalExerciseTest {

	private OutputStream logCapturingStream = new ByteArrayOutputStream();
	private StreamHandler customLogHandler;

	@Before
	public void init() throws Exception {
		
		ConfigDerbyDb configDerbyDb = new ConfigDerbyDb();
		configDerbyDb.initDatabase();
		logCapturingStream = new ByteArrayOutputStream();
		new File("logs").mkdirs();
	}

	/**
	 * Test for show Invalid configuration
	 */
	@Test
	public void unitTestInvalidConfiguration() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, false, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, false, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		try {
			jobLogger.LogMessage("This is a message", LevelLogConfig.LEVEL_LOG_MESSAGE);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Invalid configuration"));
		}
	}

	/**
	 * Test for Error or Warning or Message must be specified
	 */
	@Test
	public void unitTestMustBeSpecified() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, false, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(true, true, true);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		try {
			jobLogger.LogMessage("This is a message", 0);
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Error or Warning or Message must be specified"));
		}
	}

	/**
	 * Test for Level log message to console
	 */
	@Test
	public void unitTestMessageToConsole() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(true, false, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, true, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		Handler[] handlers = jobLogger.getLogger().getParent().getHandlers();
		customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

		jobLogger.getLogger().addHandler(customLogHandler);

		String message = "This is a message " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_MESSAGE);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertTrue(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log message to file
	 */
	@Test
	public void unitTestMessageToFile() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(true, false, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(true, false, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a message " + System.currentTimeMillis();

		try {
			Handler[] handlers = jobLogger.getLogger().getHandlers();
			customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

			jobLogger.getLogger().addHandler(customLogHandler);

			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_MESSAGE);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertTrue(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log message to DB
	 */
	@Test
	public void unitTestMessageToDb() {
		LevelLogConfig levelLogConfig = new LevelLogConfig(true, false, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, false, true);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a message " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_MESSAGE);

			LogValue logValue = jobLogger.getLogValueDao().findByMessageAndType(message,
					String.valueOf(LevelLogConfig.LEVEL_LOG_MESSAGE));

			assertTrue(logValue.getMessage().contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log warning to console
	 */
	@Test
	public void unitTestWarningToConsole() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, true, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, true, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		Handler[] handlers = jobLogger.getLogger().getParent().getHandlers();
		customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

		jobLogger.getLogger().addHandler(customLogHandler);

		String message = "This is a warning " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_WARNING);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertTrue(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log warning to file
	 */
	@Test
	public void unitTestWarningToFile() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, true, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(true, false, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a warning " + System.currentTimeMillis();

		try {
			Handler[] handlers = jobLogger.getLogger().getHandlers();
			customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

			jobLogger.getLogger().addHandler(customLogHandler);

			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_WARNING);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertTrue(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log warning to DB
	 */
	@Test
	public void unitTestWarningToDb() {
		LevelLogConfig levelLogConfig = new LevelLogConfig(false, true, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, false, true);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a warning " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_WARNING);

			LogValue logValue = jobLogger.getLogValueDao().findByMessageAndType(message,
					String.valueOf(LevelLogConfig.LEVEL_LOG_WARNING));

			assertTrue(logValue.getMessage().contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log error to console
	 */
	@Test
	public void unitTestErrorToConsole() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, false, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, true, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		Handler[] handlers = jobLogger.getLogger().getParent().getHandlers();
		customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

		jobLogger.getLogger().addHandler(customLogHandler);

		String message = "This is a error " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_ERROR);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertTrue(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log error to file
	 */
	@Test
	public void unitTestErrorToFile() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, false, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(true, false, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a error " + System.currentTimeMillis();

		try {
			Handler[] handlers = jobLogger.getLogger().getHandlers();
			customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

			jobLogger.getLogger().addHandler(customLogHandler);

			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_ERROR);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertTrue(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log error to DB
	 */
	@Test
	public void unitTestErrorToDb() {
		LevelLogConfig levelLogConfig = new LevelLogConfig(false, false, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, false, true);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a error " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_ERROR);

			LogValue logValue = jobLogger.getLogValueDao().findByMessageAndType(message,
					String.valueOf(LevelLogConfig.LEVEL_LOG_ERROR));

			assertTrue(logValue.getMessage().contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log non message to console
	 */
	@Test
	public void unitTestNonMessageToConsole() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, true, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, true, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		Handler[] handlers = jobLogger.getLogger().getParent().getHandlers();
		customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

		jobLogger.getLogger().addHandler(customLogHandler);

		String message = "This is a message " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_MESSAGE);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertFalse(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log non message to file
	 */
	@Test
	public void unitTestNonMessageToFile() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, true, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(true, false, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a message " + System.currentTimeMillis();

		try {
			Handler[] handlers = jobLogger.getLogger().getHandlers();
			customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

			jobLogger.getLogger().addHandler(customLogHandler);

			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_MESSAGE);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertFalse(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log non message to DB
	 */
	@Test
	public void unitTestNonMessageToDb() {
		LevelLogConfig levelLogConfig = new LevelLogConfig(false, true, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, false, true);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a message " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_MESSAGE);

			LogValue logValue = jobLogger.getLogValueDao().findByMessageAndType(message,
					String.valueOf(LevelLogConfig.LEVEL_LOG_MESSAGE));

			assertTrue(logValue.getMessage() == null);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log non warning to console
	 */
	@Test
	public void unitTestNonWarningToConsole() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(true, false, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, true, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		Handler[] handlers = jobLogger.getLogger().getParent().getHandlers();
		customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

		jobLogger.getLogger().addHandler(customLogHandler);

		String message = "This is a warning " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_WARNING);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertFalse(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log non warning to file
	 */
	@Test
	public void unitTestNonWarningToFile() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(true, false, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(true, false, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a warning " + System.currentTimeMillis();

		try {
			Handler[] handlers = jobLogger.getLogger().getHandlers();
			customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

			jobLogger.getLogger().addHandler(customLogHandler);

			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_WARNING);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertFalse(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level non log warning to DB
	 */
	@Test
	public void unitTestNonWarningToDb() {
		LevelLogConfig levelLogConfig = new LevelLogConfig(true, false, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, false, true);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a warning " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_WARNING);

			LogValue logValue = jobLogger.getLogValueDao().findByMessageAndType(message,
					String.valueOf(LevelLogConfig.LEVEL_LOG_WARNING));

			assertTrue(logValue.getMessage() == null);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log non error to console
	 */
	@Test
	public void unitTestNonErrorToConsole() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(true, true, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, true, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		Handler[] handlers = jobLogger.getLogger().getParent().getHandlers();
		customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

		jobLogger.getLogger().addHandler(customLogHandler);

		String message = "This is a error " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_ERROR);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertFalse(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log non error to file
	 */
	@Test
	public void unitTestNonErrorToFile() {

		LevelLogConfig levelLogConfig = new LevelLogConfig(false, false, true);
		OutputLogConfig outputLogConfig = new OutputLogConfig(true, false, false);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a error " + System.currentTimeMillis();

		try {
			Handler[] handlers = jobLogger.getLogger().getHandlers();
			customLogHandler = new StreamHandler(logCapturingStream, handlers[0].getFormatter());

			jobLogger.getLogger().addHandler(customLogHandler);

			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_ERROR);

			String currentMessage = getTestCapturedLog(customLogHandler, logCapturingStream);

			assertTrue(currentMessage.contains(message));

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	/**
	 * Test for Level log non error to DB
	 */
	@Test
	public void unitTestNonErrorToDb() {
		LevelLogConfig levelLogConfig = new LevelLogConfig(true, true, false);
		OutputLogConfig outputLogConfig = new OutputLogConfig(false, false, true);

		JobLogger jobLogger = new JobLogger(levelLogConfig, outputLogConfig);

		String message = "This is a error " + System.currentTimeMillis();

		try {
			jobLogger.LogMessage(message, LevelLogConfig.LEVEL_LOG_ERROR);

			LogValue logValue = jobLogger.getLogValueDao().findByMessageAndType(message,
					String.valueOf(LevelLogConfig.LEVEL_LOG_ERROR));

			assertTrue(logValue.getMessage() == null);

		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	

	public String getTestCapturedLog(StreamHandler customLogHandler, OutputStream logCapturingStream)
			throws IOException {
		customLogHandler.flush();
		return logCapturingStream.toString();
	}
}
