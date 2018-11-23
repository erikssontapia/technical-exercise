package com.belatrix.technical_exercise.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.belatrix.technical_exercise.config.ConfigProperty;

/**
 * 
 * @author Eriksson Tapia
 *
 */
public class ConnectionDB {

	public static ConnectionDB instance;

	private String username;
	private String password;
	private String dbms;
	private String servername;
	protected Connection connection;

	public ConnectionDB() {
		ConfigProperty configProperty = ConfigProperty.getInstance();

		setUsername(configProperty.getStringValue("db.username"));
		setPassword(configProperty.getStringValue("db.password"));
		setDbms(configProperty.getStringValue("db.dbms"));
		setServername(configProperty.getStringValue("db.servername"));
	}

	/**
	 * Method for open connection to database
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		StringBuilder dbUrl = new StringBuilder();

		dbUrl.append("jdbc:").append(dbms).append(":").append(servername);
		connection = DriverManager.getConnection(dbUrl.toString());
	}

	/**
	 * Method for close connection to database
	 * @throws SQLException
	 */
	public void close() throws SQLException {		
		if(connection != null && connection.isClosed())
			connection.close();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbms() {
		return dbms;
	}

	public void setDbms(String dbms) {
		this.dbms = dbms;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
