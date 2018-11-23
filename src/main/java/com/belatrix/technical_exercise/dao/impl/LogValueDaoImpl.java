package com.belatrix.technical_exercise.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.belatrix.technical_exercise.dao.LogValueDao;
import com.belatrix.technical_exercise.model.LogValue;

/**
 * 
 * @author Eriksson Tapia
 *
 */
public class LogValueDaoImpl extends ConnectionDB implements LogValueDao {

	public LogValueDaoImpl() {

	}
	
	/**
	 * 
	 * @param logValue
	 * @throws Exception
	 * 
	 * Method for save log to database
	 */
	public void saveLogValue(LogValue logValue) throws Exception {
		try {
			this.open();
			PreparedStatement ps = this.getConnection()
					.prepareStatement("INSERT INTO APP.LOG_VALUES(MESSAGE, TYPE) VALUES(?, ?)");
			ps.setString(1, logValue.getMessage());
			ps.setString(2, logValue.getType());
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			throw e;
		} finally {
			this.close();
		}
	}

	/**
	 * 
	 * @param message
	 * @param type
	 * @return
	 * @throws Exception
	 * 
	 * Method to find row by message and type 
	 */
	public LogValue findByMessageAndType(String message, String type) throws Exception {

		LogValue logValue = new LogValue();

		try {
			this.open();

			PreparedStatement ps = this.getConnection()
					.prepareStatement("SELECT ID, MESSAGE, TYPE FROM APP.LOG_VALUES WHERE MESSAGE LIKE ? AND TYPE = ?");
			ps.setString(1, "%" + message + "%");
			ps.setString(2, type);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				logValue.setId(rs.getInt("ID"));
				logValue.setMessage(rs.getString("MESSAGE"));
				logValue.setType(rs.getString("TYPE"));
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			throw e;
		} finally {
			this.close();
		}

		return logValue;
	}

}
