package com.game.kalah.service;

import com.game.kalah.domain.Game;
import com.game.kalah.exception.InvalidIdException;

public interface KalahService {

	public Game create();

	public Game play(String gameId, Integer pitId) throws Exception;

	public Game get(String gameId) throws InvalidIdException;

}
