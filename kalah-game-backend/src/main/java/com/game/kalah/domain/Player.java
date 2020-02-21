package com.game.kalah.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Player {

	FIRST_PLAYER(1, 7, Arrays.asList(1, 2, 3, 4, 5, 6)),

	SECOND_PLAYER(2, 14, Arrays.asList(8, 9, 10, 11, 12, 13));

	private final int playerId;

	private final int homeId;

	private final List<Integer> pits;

	Player(int playerId, int homeId, List<Integer> pits) {
		this.playerId = playerId;
		this.homeId = homeId;
		this.pits = Collections.unmodifiableList(pits);
	}

	public int getPlayerId() {
		return playerId;
	}

	public int getHomeId() {
		return homeId;
	}

	public List<Integer> getPits() {
		return pits;
	}

	public Player getNextPlayer() {
		return this == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
	}

}
