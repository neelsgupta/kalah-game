package com.game.kalah.exception;

import com.game.kalah.domain.GameStatus;

public class GameEndedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String gameId;
	private final String message;
	private final GameStatus gameStatus;

	/**
	 * Maps the GameEndedException with gameId, message and respective game status
	 * 
	 * @param gameId
	 * @param message
	 * @param gameStatus
	 */
	public GameEndedException(String gameId, String message, GameStatus gameStatus) {
		super();
		this.gameId = gameId;
		this.message = message;
		this.gameStatus = gameStatus;
	}

	public String getGameId() {
		return gameId;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}
}
