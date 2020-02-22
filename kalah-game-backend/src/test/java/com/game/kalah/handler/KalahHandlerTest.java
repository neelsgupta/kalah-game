package com.game.kalah.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
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
	private Class<? extends KalahHandler> handlerClass = handler.getClass();

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

	@Test(expected = InvalidIdException.class)
	public void testValidatePitIdForOtherPlayer() {
		int pitId = 9;
		Game game = new Game();
		game.setPlayer(Player.FIRST_PLAYER);
		handler.makeMove(game, pitId);
	}

	@Test(expected = InvalidIdException.class)
	public void testValidatePitIdForEmptyPit() {
		int pitId = 1;
		scoreBoard.replace(1, 0);
		handler.makeMove(game, pitId);
	}

	@Test
	public void testIsGameEndedFalse() throws Exception {
		Method isGameEnded = handlerClass.getDeclaredMethod("isGameEnded", Game.class);
		setMethodAccessibleTrue(isGameEnded);
		boolean result = (boolean) isGameEnded.invoke(handler, game);
		assertFalse(result);
	}

	@Test
	public void testIsGameEndedTruePlayerOneSideGoneOutOfStone() throws Exception {
		Method isGameEnded = handlerClass.getDeclaredMethod("isGameEnded", Game.class);
		setMethodAccessibleTrue(isGameEnded);
		game.getScoreBoard().entrySet().stream().filter(k -> k.getKey().intValue() < 7).forEach(l -> l.setValue(0));

		boolean result = (boolean) isGameEnded.invoke(handler, game);
		assertTrue(result);
	}

	@Test
	public void testIsGameEndedTruePlayerTwoSideGoneOutOfStone() throws Exception {
		Method isGameEnded = handlerClass.getDeclaredMethod("isGameEnded", Game.class);
		setMethodAccessibleTrue(isGameEnded);
		game.getScoreBoard().entrySet().stream().filter(k -> k.getKey().intValue() > 7).forEach(l -> l.setValue(0));

		boolean result = (boolean) isGameEnded.invoke(handler, game);
		assertTrue(result);
	}

	@Test
	public void testCheckWinnerIfFirstPlayerWins() throws Exception {
		Method checkWinner = handlerClass.getDeclaredMethod("checkWinner", Game.class);
		setMethodAccessibleTrue(checkWinner);
		game.getScoreBoard().entrySet().stream().filter(firstPlayerHome -> firstPlayerHome.getKey().intValue() == 7)
				.forEach(firstPlayerHomeStoneCount -> firstPlayerHomeStoneCount.setValue(48));

		GameStatus gameStatus = (GameStatus) checkWinner.invoke(handler, game);
		assertEquals(GameStatus.FIRST_PLAYER_WON, gameStatus);
	}

	@Test
	public void testCheckWinnerIfSecoundPlayerWins() throws Exception {
		Method checkWinner = handlerClass.getDeclaredMethod("checkWinner", Game.class);
		setMethodAccessibleTrue(checkWinner);
		game.getScoreBoard().entrySet().stream().filter(firstPlayerHome -> firstPlayerHome.getKey().intValue() == 14)
				.forEach(firstPlayerHomeStoneCount -> firstPlayerHomeStoneCount.setValue(52));

		GameStatus gameStatus = (GameStatus) checkWinner.invoke(handler, game);
		assertEquals(GameStatus.SECOND_PLAYER_WON, gameStatus);
	}

	@Test
	public void testCheckWinnerIfGameDraws() throws Exception {
		Method checkWinner = handlerClass.getDeclaredMethod("checkWinner", Game.class);
		setMethodAccessibleTrue(checkWinner);
		game.getScoreBoard().entrySet().stream()
				.filter(firstPlayerHome -> (firstPlayerHome.getKey().intValue() == 7
						|| firstPlayerHome.getKey().intValue() == 14))
				.forEach(firstPlayerHomeStoneCount -> firstPlayerHomeStoneCount.setValue(36));

		GameStatus gameStatus = (GameStatus) checkWinner.invoke(handler, game);
		assertEquals(GameStatus.DRAW, gameStatus);
	}
	
	@Test
	public void testCheckPitLastStoneLandedInEmptyOwnPit() throws Exception {
		Method checkPitLastStoneLanded = handlerClass.getDeclaredMethod("checkPitLastStoneLanded", int.class, Game.class);
		setMethodAccessibleTrue(checkPitLastStoneLanded);
		game.getScoreBoard().replace(5, 1);
		checkPitLastStoneLanded.invoke(handler, 5, game);
		
		assertEquals(game.getScoreBoard().get(7),Integer.valueOf(7));
		assertEquals(game.getScoreBoard().get(5),Integer.valueOf(0));
		assertEquals(game.getScoreBoard().get(9),Integer.valueOf(0));
	}
	
	@Test
	public void testCheckPitLastStoneLandedInNonEmptyOwnPit() throws Exception {
		Method checkPitLastStoneLanded = handlerClass.getDeclaredMethod("checkPitLastStoneLanded", int.class, Game.class);
		setMethodAccessibleTrue(checkPitLastStoneLanded);
		game.getScoreBoard().replace(5, 4);
		checkPitLastStoneLanded.invoke(handler, 5, game);
		
		assertEquals(game.getScoreBoard().get(7),Integer.valueOf(0));
		assertEquals(game.getScoreBoard().get(5),Integer.valueOf(4));
		assertEquals(game.getScoreBoard().get(9),Integer.valueOf(6));
	}
	
	@Test
	public void testCheckPitLastStoneLandedInEmptyOwnPitWithEmptyOppositePit() throws Exception {
		Method checkPitLastStoneLanded = handlerClass.getDeclaredMethod("checkPitLastStoneLanded", int.class, Game.class);
		setMethodAccessibleTrue(checkPitLastStoneLanded);
		game.getScoreBoard().replace(5, 1);
		game.getScoreBoard().replace(9, 0);
		checkPitLastStoneLanded.invoke(handler, 5, game);
		
		assertEquals(game.getScoreBoard().get(7),Integer.valueOf(0));
		assertEquals(game.getScoreBoard().get(5),Integer.valueOf(1));
		assertEquals(game.getScoreBoard().get(9),Integer.valueOf(0));
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

	private void setMethodAccessibleTrue(Method method) {
		method.setAccessible(true);
	}

}
