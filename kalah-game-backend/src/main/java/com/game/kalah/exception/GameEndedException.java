package com.game.kalah.exception;

import com.game.kalah.domain.GameStatus;

public class GameEndedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String gameId;
	private String message;
	private GameStatus gameStatus;

	public GameEndedException(String gameId, String message, GameStatus gameStatus) {
		super();
		this.gameId = gameId;
		this.message = message;
		this.gameStatus = gameStatus;
	}

	public String getGameId() {
		return gameId;
	}

	public String getMessage() {
		return message;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}
}
