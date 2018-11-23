package com.belatrix.technical_exercise.model;

/**
 * 
 * @author Eriksson Tapia
 *
 */
public class LogValue {

	private int id;
	private String message;
	private String type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "LogValue [id=" + id + ", message=" + message + ", type=" + type + "]";
	}	
		
}
