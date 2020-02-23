package com.game.kalah.exception;

public class InvalidIdException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String id;

	private final String message;

	/**
	 * Maps the InvalidIdException for respective id and message
	 * 
	 * @param id
	 *            is gameId or pitId for failure
	 * @param message
	 *            message for respective failure
	 */
	public InvalidIdException(String id, String message) {
		super();
		this.id = id;
		this.message = message;
	}

	public String getId() {
		return id;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
