package com.game.kalah.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.game.kalah.domain.GameStatus;

public class KalahMovedResponse {

	@JsonProperty
	private String id;

	@JsonProperty
	private Map<Integer, Integer> score;

	@JsonProperty
	private String nextPlayer;

	@JsonProperty
	private String uri;

	@JsonProperty
	private GameStatus gameStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Map<Integer, Integer> getScore() {
		return score;
	}

	public void setScore(Map<Integer, Integer> score) {
		this.score = score;
	}

	public String getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(String nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

}
