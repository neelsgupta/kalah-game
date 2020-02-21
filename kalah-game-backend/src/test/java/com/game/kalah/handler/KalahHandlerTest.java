package com.game.kalah.handler;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

import com.game.kalah.domain.Game;
import com.game.kalah.domain.GameStatus;
import com.game.kalah.domain.Player;
import com.game.kalah.exception.InvalidIdException;

public class KalahHandlerTest {

	private final KalahHandler handler = new KalahHandler();

	private Game game = new Game();
	private Map<Integer, Integer> scoreBoard;

	@Before
	public void setUp() throws Exception {
		game = setGameAttributes(game);
		scoreBoard = game.getScoreBoard();
	}

	@Test
	public void testMakeMoveForFisrtPlayerTurn() {
		int pitId = 1;
		int pitAmount = scoreBoard.get(pitId);
		Map<Integer, Integer> beforeMove = new HashMap<>(scoreBoard);
		handler.makeMove(game, pitId);

		IntStream.range(pitId + 1, pitAmount + 1).forEach(pit -> {
			int amount = scoreBoard.get(pit);
			assertEquals(amount, beforeMove.get(pit) + 1);
		});
	}

	@Test
	public void testMakeMoveForSecondPlayerTurn() {
		int pitId = 12;
		game.setPlayer(Player.SECOND_PLAYER);
		int pitAmount = scoreBoard.get(pitId);
		Map<Integer, Integer> beforeMove = new HashMap<>(scoreBoard);
		handler.makeMove(game, pitId);

		IntStream.range(pitId + 1, pitAmount + 1).forEach(pit -> {
			int amount = scoreBoard.get(pit);
			assertEquals(amount, beforeMove.get(pit) + 1);
		});
	}
	
	@Test(expected=InvalidIdException.class)
	public void testValidatePitIdForOtherPlayer() {
		int pitId = 9;
		Game game = new Game();
		game.setPlayer(Player.FIRST_PLAYER);
		handler.makeMove(game, pitId);
	}
	
	@Test(expected=InvalidIdException.class)
	public void testValidatePitIdForEmptyPit() {
		int pitId = 1;
		scoreBoard.replace(1, 0);
		handler.makeMove(game, pitId);
	}
	
	private Game setGameAttributes(Game game) {
		Map<Integer, Integer> scoreBoard = new LinkedHashMap<>();
		for (int i = 1; i <= 14; i++) {
			int value = (i != 7 && i != 14) ? 6 : 0;
			scoreBoard.put(i, value);
		}
		game.setPlayer(Player.FIRST_PLAYER);
		game.setGameStatus(GameStatus.IN_PROGRESS);
		game.setScoreBoard(scoreBoard);
		
		return game;
	}

}
