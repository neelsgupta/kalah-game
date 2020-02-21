package com.game.kalah.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.kalah.domain.GameStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class KalahErrorResponse {

	@JsonProperty
	private String id;

	@JsonProperty
	private String message;

	@JsonProperty
	private GameStatus gameStatus;

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

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
}
