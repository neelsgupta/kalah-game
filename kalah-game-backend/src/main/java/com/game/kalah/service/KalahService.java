package com.game.kalah.service;

import com.game.kalah.domain.Game;
import com.game.kalah.exception.GameEndedException;
import com.game.kalah.exception.InvalidIdException;

public interface KalahService {

	/**
	 * Creates a new Game
	 * 
	 * @return new Game
	 */
	public Game create();

	/**
	 * Based on given unique gameId it return the game if it exists
	 * 
	 * @param gameId
	 *            is a unique id of a game
	 * @return
	 * @throws InvalidIdException
	 *             if game does not exist or game is terminated.
	 */
	public Game get(String gameId);

	/**
	 * Returns the game after making a move
	 * 
	 * @param gameId
	 *            is a unique id of a game
	 * @param pitId
	 *            is the id of selected pit of game board
	 * @return the updated moved game
	 * @throws GameEndedException
	 *             if game is terminated by the status of either one of the player won the game or it is drawn
	 * @throws InvalidIdException
	 *             if selected pitId belongs to other player or selected pitId is an empty pit
	 */
	public Game play(String gameId, Integer pitId);

}
