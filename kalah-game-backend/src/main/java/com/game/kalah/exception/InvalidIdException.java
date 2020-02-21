package com.game.kalah.exception;

public class InvalidIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String id;

	private String message;

	public InvalidIdException(String id, String message) {
		super();
		this.id = id;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
