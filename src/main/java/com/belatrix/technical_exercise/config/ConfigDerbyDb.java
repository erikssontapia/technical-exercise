package com.belatrix.technical_exercise.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfigDerbyDb {
	
	Connection connection;
	
	public ConfigDerbyDb() throws Exception{
		connectionToDerby();
	}
	
	public void connectionToDerby() throws SQLException {
		String dbUrl = "jdbc:derby:technical-exercise-db;create=true";
		connection = DriverManager.getConnection(dbUrl);
	}
	
	public void initDatabase() throws SQLException {
		Statement stmt = connection.createStatement();
		
		try {
			// drop table
			stmt.executeUpdate("Drop table Log_Values");
		}catch(Exception e) {
			System.out.println("Not found Log_Values");
		}
		// create table
		stmt.executeUpdate("Create table Log_Values (id integer primary key not null generated always as identity (start with 1, increment by 1), message varchar(500), type varchar(1))");
		
		stmt.close();
		connection.close();
	}

}
