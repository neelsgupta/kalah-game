package com.game.kalah.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Game {

	@Id
	private String id;

	@ElementCollection
	@MapKeyColumn(name = "pitId")
	@Column(name = "value")
	private Map<Integer, Integer> scoreBoard;

	@Enumerated(value = EnumType.STRING)
	private GameStatus gameStatus;

	@Enumerated(value = EnumType.STRING)
	private Player player;

	@Column
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<Integer, Integer> getScoreBoard() {
		if (scoreBoard == null) {
			new LinkedHashMap<>();
		}
		return scoreBoard;
	}

	public void setScoreBoard(Map<Integer, Integer> scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
